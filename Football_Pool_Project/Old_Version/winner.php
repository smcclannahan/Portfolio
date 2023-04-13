<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Determine Winner
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

if ((isset($_SESSION['valid_user'])) and  (isset($_SESSION['commissioner']))) {
//if ((session_is_registered("valid_user")) and (session_is_registered("commissioner"))) {

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
    if ($week > $wkseason) {
      $week_name = "the Playoffs";
    }
  }

  $num_low  = $current_week * 100 - 1;
  $num_high = ($current_week + 1) * 100;
  $db_query = "SELECT * FROM games WHERE gamenum > $num_low AND gamenum < $num_high";
  $query = mysql_query ($db_query,$connection);
  $games_returned = mysql_num_rows($query);

  $week_done = true;

  for ($x=0;$x<$games_returned;$x++) {
    $row            = mysql_fetch_row($query);
    if ($row[8] == 0) {
      $week_done    = false;
    }
  }

  if (($current_week > $wkseason) and ($current_week < $sb)) {
    $week_done = false;
  }

  $awayscore  = $row[5];
  $totalscore = $row[5]+$row[6];
  if ($current_week == 21) {
    $totalscore = $row[5];
    $seasontie  = $row[5] + $row[6];
  }


  if ($week_done) {
    $db_query = "SELECT * FROM players WHERE 1";
    $query = mysql_query ($db_query,$connection);
    $num_returned = mysql_num_rows($query);

    $tbindex    = $current_week+$tbrk_weekly_offset;
    $rightindex = $current_week+$right_offset;
    if (week > $wkseason) {
      $tbindex = $tbrk_playoff_offset;
    }
    $seasontbindex = $tbrk_season_offset;

    $lowest = 99;

    for ($x=0;$x<$num_returned;$x++) {
      $row             = mysql_fetch_row($query);
      $pckname[$x]     = $row[$name_offset];
      $tb[$x]          = $row[$tbindex];
      $seasonright[$x] = 0;
      for ($n=$right_offset + 1;$n< $weeks_won_offset ;$n++) {
        $seasonright[$x] += $row[$n];
      }
      $seasontb[$x]    = $row[$seasontbindex];
      $ww_cnt[$x]      = $row[$weeks_won_offset];
      $valpicks        = $row[$pvalid_offset];
      $madepicks[$x]   = $valpicks[$current_week - 1];

      if ($current_week < $ww) {
        $right[$x] = $row[$rightindex];
        if ($madepicks[$x] == "Y") {
          if ($right[$x] < $lowest) {
            $lowest = $right[$x];
          }
        }
      }
      else { // Playoffs must be added up
        $right[$x] = $row[$rightindex - 3] + $row[$rightindex - 2] + $row[$rightindex - 1] + $row[$rightindex];
      }


    }

    $rightvar = "right".$current_week;
    if ($current_week < $ww) {
      for ($x=0;$x<$num_returned;$x++) {
        if ($madepicks[$x] == "N") {
          $db_query = "UPDATE players SET $rightvar = $lowest WHERE userid=\"$pckname[$x]\"";
          if (!(@ mysql_query ($db_query,$connection))) {
            die("Query Failed $db_key $db_query");
          }
        }
      }
    }

    array_multisort($right, SORT_DESC, $pckname, $tb, $ww_cnt, $seasonright, $seasontb, $madepicks);

    for ($x=0;$x<$num_returned;$x++) {

    }

    $winner_found = false;

    if ($right[0] > $right[1]) {
      echo "$pckname[0] is the winner of the pool for $week_name<br>";
      $winner_found = true;
    }
    else {
      $total_same = 1;
      for ($x=1;$x<$num_returned;$x++) {
        if ($right[$x] == $right[0]) {
          $total_same++;
        }
      }
      for ($x=0;$x<$num_returned;$x++) {
        if ($x < $total_same) {
          $ou[$x] = abs($totalscore - $tb[$x]);
        }
        else {
          $ou[$x] = 1000;
        }
      }
      array_multisort($ou, SORT_ASC, $pckname, $tb, $ww_cnt, $seasonright, $seasontb);
      if ($ou[0] < $ou[1]) {
        echo "$pckname[0] is the winner of the pool for $week_name<br>";
        $winner_found = true;
      }
      else {
        echo "A winner cannot be determined. Please get the coin out to flip.....<br>";
      }

    }

    if ($winner_found) {
      // Update a record into winners indicating the week and the player name
      $player = $pckname[0];
      if ($current_week > $wkseason) {
        for ($m=$ww;$m < $inv;$m++) {
          $db_query = "UPDATE winners SET winner = \"$player\" WHERE week=$m";
          if (!(@ mysql_query ($db_query,$connection))) {
            die("Query Failed $db_key $db_query");
          }
        }
      }
      else {
        $db_query = "UPDATE winners SET winner = \"$player\" WHERE week=$current_week";
        if (!(@ mysql_query ($db_query,$connection))) {
          die("Query Failed $db_key $db_query");
        }
      }
      // Need to increment player's weeks won field
      $weeks_won = $ww_cnt[0] + 1;
      $db_query = "UPDATE players SET weekswon = $weeks_won WHERE userid = \"$player\"";
      if (!(@ mysql_query ($db_query,$connection))) {
        die("Query Failed $db_key $db_query");
      }

      $swinner_found = false;
      if ($current_week == $sb) {
        array_multisort($seasonright, SORT_DESC, $pckname, $seasontb);

        if ($seasonright[0] > $seasonright[1]) {
          echo "$pckname[0] is the winner of the pool for this season<br>";
          $swinner_found = true;
        }
        else {
          $total_same = 1;
          for ($x=1;$x<$num_returned;$x++) {
            if ($seasonright[$x] == $seasonright[0]) {
              $total_same++;
            }
          }
          for ($x=0;$x<$num_returned;$x++) {
            if ($x < $total_same) {
              $ou[$x] = abs($seasontie - $seasontb[$x]);
            }
            else {
              $ou[$x] = 1000;
            }
          }
          array_multisort($ou, SORT_ASC, $pckname, $seasonright, $seasontb);
          if ($ou[0] < $ou[1]) {
            echo "$pckname[0] is the winner of the pool for the season<br>";
            $swinner_found = true;
          }
          else {
            echo "A season winner cannot be determined. Please get the coin out to flip.....<br>";
          }
        }
      }


      if ($current_week == $sb) {
        if ($swinner_found) {
          // Update a record into winners indicating the week and the player name
          $player = $pckname[0];
          $db_query = "UPDATE winners SET winner = \"$player\" WHERE week=25";
          if (!(@ mysql_query ($db_query,$connection))) {
            die("Query Failed $db_key $db_query");
          }
        }
        else {
          echo "Sorry, cannot figure out a winner. Get out a coin and do a flip.....<br>";
        }

      }
    }
  }
  else {
    echo "Sorry, you cannot declare a winner until all the games of the week are over......<br><br>";
    if ($current_week > 17) {
      echo "<br>And remember, all playoff games from Wildcard to Super Bowl are considered 1 week......<br><br>";
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