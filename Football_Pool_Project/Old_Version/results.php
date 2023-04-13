<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Update Results
//**********************************************************************
?>
<?php

session_start();

// Get Configuration data and set page variables.
include ('./nfl_config.php');
include ('./nfl_functions.php');

$old_user = $HTTP_SESSION_VARS["valid_user"];
echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

//if ((session_is_registered("valid_user")) and (session_is_registered("commissioner"))) {
if ((isset($_SESSION['valid_user'])) and  (isset($_SESSION['commissioner']))) {

  echo "<h1>NFL Pool Results</h1>";


  if (!($connection = @ mysql_connect($hostname,$user,$password))) {
    die("Could not connect to host");
  }

  if (!mysql_select_db($database, $connection)) {
    die("Could not connect to database");
  }

  $db_query = "SELECT * FROM control WHERE 1";
  $query = mysql_query ($db_query,$connection);

  if (!($row = mysql_fetch_object($query))) {
    die("Unable to read current week!");
  }
  else {
    $current_week = $row->weeknow;
    if ($week < $ww) {
      $week_name = "Week ".$current_week;
    }
    if ($week == $ww) {
      $week_name = "Wildcard Weekend";
    }
    if ($week == $dp) {
      $week_name = "Divisional Playoff";
    }
    if ($week == $cc) {
      $week_name = "Conference Championship";
    }
    if ($week == $sb) {
      $week_name = "Super Bowl";
    }
  }

  $num_low  = $current_week * 100 - 1;
  $num_high = ($current_week + 1) * 100;
  $db_query = "SELECT * FROM games WHERE gamenum > $num_low AND gamenum < $num_high";
  $query = mysql_query ($db_query,$connection);
  $games_returned = mysql_num_rows($query);

  for ($x=0;$x<$games_returned;$x++) {
    $row           = mysql_fetch_row($query);
    $awaynum[$x]   = $row[3];
    $homenum[$x]   = $row[4];
    $awayscore[$x] = $row[5];
    $homescore[$x] = $row[6];
    $spread[$x]    = $row[7];
    $final[$x]     = $row[8];
    $overunder[$x] = $row[9];
  }

  $db_query = "SELECT * FROM players WHERE 1";
  $query = mysql_query ($db_query,$connection);
  $num_returned = mysql_num_rows($query);

  $pickindex  = $current_week+$pick_offset;
  $tbindex    = $current_week+$tbrk_weekly_offset;

  for ($x=0;$x<$num_returned;$x++) {
    $row          = mysql_fetch_row($query);
    $pckname[$x]  = $row[$name_offset];
    $picks[$x]    = $row[$pickindex];
    $tb[$x]       = $row[$tbindex];
    $pickvalid    = $row[$pvalid_offset];
    $madepick[$x] = $pickvalid[$current_week - 1];
  }

  $right_picks = "";

  for ($x=0;$x<$games_returned;$x++) {
    $a = $awaynum[$x];
    $h = $homenum[$x];
    $realspread = $homescore[$x] - $awayscore[$x];

    if (($realspread == $spread[$x]) or ($final[$x] == 0)) {
      $right_picks .= "N";
    }
    else {
      if ($spread[$x] > 0) {
        if ($realspread > $spread[$x]) {
          $right_picks .= "H";
        }
        else {
          $right_picks .= "A";
        }
      }

      if ($spread[$x] < 0) {
        if ($realspread < $spread[$x]) {
          $right_picks .= "A";
        }
        else {
          $right_picks .= "H";
        }
      }

      if ($spread[$x] == 0) {
        if ($homescore[$x] > $awayscore[$x]) {
          $right_picks .= "H";
        }
        else {
          $right_picks .= "A";
        }
      }
    }
  }

  $rightvar = "right".$current_week;
  if ($current_week > $wkseason) {
    if ($current_week == $ww) {
      $rightvar = "right"."wild";
    }
    if ($current_week == $dp) {
      $rightvar = "right"."div";
    }
    if ($current_week == $cc) {
      $rightvar = "right"."conf";
    }
    if ($current_week == $sb) {
      $rightvar = "right"."sb";
    }
  }

  for ($y=0;$y<$num_returned;$y++) {
    $t     = $picks[$y];
    $right = 0;
    if ($madepick[$y] == "Y") {
      for ($x=0;$x<$games_returned;$x++) {
        if ($final[$x] == 1) {
          if ($t[$x] == $right_picks[$x]) {
            $right++;
          }
        }
      }
    }

    $player = $pckname[$y];
    $db_query = "UPDATE players SET $rightvar = $right WHERE userid=\"$player\"";

    if (!(@ mysql_query ($db_query,$connection))) {
      die("Query Failed $db_key $db_query");
    }

  }

  mysql_close($connection);


}


else{
  echo "<p>You are not authorized to use this page because you are not logged in or are not the commissioner.</p><br><br>";
}

echo "<a href = \"index.php\">Return to Log in page</a><br>";
echo "</body></html>";


?>

