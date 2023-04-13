<?php
//**********************************************************************
// Project     : NFL Pool Make Picks
// Author      : gmcclannahan
// Description : Set Spreads
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

if ((isset($_SESSION['valid_user'])) and (isset($_SESSION['commissioner']))) {
//if ((session_is_registered("valid_user")) and (session_is_registered("commissioner"))) {

  echo"<h1>Set Spreads</h1>";

  if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    $process = $_SERVER['PHP_SELF'];

    echo "<form method=post action=\"$process\">";

    // Determine the current week in schedule
    // Open a connection to the database
    if (!($connection = @ mysql_connect($hostname,$user,$password))) {
      die("Could not connect to host");
    }

    if (!mysql_select_db($database, $connection)) {
      die("Could not connect to database");
    }

    $db_query = "SELECT * FROM control WHERE 1";
    $query = mysql_query ($db_query,$connection);

    $row = mysql_fetch_object($query);
    $current_week = $row->weeknow;

    echo "<select size=\"1\" name=\"week\">";

    if ($current_week < $ww) {
      for ($x=$current_week;$x< $ww;$x++) {
        if ($x == $current_week) {
          $selected = " selected";
        }
        else {
          $selected = " ";
        }
        echo"<option $selected>Week $x</option>";
      }
    }
    if ($current_week < $dp) {
      if ($current_week == $ww) {
        $selected = " selected";
      }
      else {
        $selected = " ";
      }
      echo"<option $selected>Wildcard Weekend</option>";
    }
    if ($current_week < $cc) {
      if ($current_week == $dp) {
        $selected = " selected";
      }
      else {
        $selected = " ";
      }
      echo"<option $selected>Divisional Playoffs</option>";
    }
    if ($current_week < $sb) {
      if ($current_week == $cc) {
        $selected = " selected";
      }
      else {
        $selected = " ";
      }
      echo"<option $selected>Conference Championships</option>";
    }
    if ($current_week < $inv) {
      if ($current_week == $sb) {
        $selected = " selected";
      }
      else {
        $selected = " ";
      }
      echo"<option $selected>Super Bowl</option>";
    }
    echo "</select>";
    echo "<input type=submit value=\"Pick Week\">";
    echo "</form>";

  } else if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $week = $_POST["week"];
    $weeklen = strlen($week);
    $awayhead = "Away";
    $homehead = "Home";
    if ($weeklen < 8) {
      $weeknum = ($week[($weeklen-2)] * 10)+ $week[($weeklen-1)];
    }
    if ($week == "Wildcard Weekend") {
      $weeknum  = $ww;
    }
    if ($week == "Divisional Playoffs") {
      $weeknum  = $dp;
    }
    if ($week == "Conference Championships") {
      $weeknum  = $cc;
    }
    if ($week == "Super Bowl") {
      $weeknum  = $sb;
      $awayhead = "AFC";
      $homehead = "NFC";
    }

    $num_low  = ($weeknum * 100) - 1;
    $num_high = ($weeknum + 1) * 100;

    // load stored schedule data from database
    // Open a connection to the database
    if (!($connection = @ mysql_connect($hostname,$user,$password))) {
      die("Could not connect to host");
    }

    if (!mysql_select_db($database, $connection)) {
      die("Could not connect to database");
    }

    $db_query = "SELECT * FROM games WHERE gamenum > $num_low AND gamenum < $num_high";
    $query = mysql_query ($db_query,$connection);
    $num_returned = mysql_num_rows($query);

    for ($x = 0;$x < $num_returned;$x++) {
      $row        = mysql_fetch_row($query);
      if ($x == 0) {
        echo "<form method=post action = \"setspreads2.php\">";
        echo "<input type=hidden name=numgames value=$num_returned>";
        echo "<input type=hidden name=weeknum value=$weeknum>";
        echo "<br><br><table border = \"1\"  bordercolor=\"#000080\">";
        echo "<tr><td colspan=\"7\" class=\"tabletitle\">$week</font></td>";
        echo "<tr><td colspan=\"2\" class=\"tablehead\">Game Date/Time</font></td>";
        echo "<td colspan=\"2\" class=\"tablehead\">$awayhead</td>";
        echo "<td class=\"tablehead\">Spread</td>";
        echo "<td colspan=\"2\" class=\"tablehead\">$homehead</td><tr>";
      }
      $gamenum    = $row[0];
      $gamedate   = f_us_date($row[1]);
      $gametime   = f_us_time($row[2]);
      $away       = $row[3];
      $home       = $row[4];
      $spread     = $row[7];
      if ($x == ($num_returned - 1)) {
        $ounow      = $row[9];
      }
      echo "<tr><td>$gamedate</td>";
      echo "<td>$gametime</td>";
      echo "<td align=\"center\"><img src=\"$logos[$away]\"></td>";
      echo "<td align=\"left\" class=\"away\">$teams[$away]</td>";

      $spreadvar = "spread".$gamenum;
      echo "<td align=\"center\"><select size=\"1\" name=\"$spreadvar\">";
      for ($y=-30;$y<31;$y=$y+.5){
        $selected = "";
        if ($y == $spread) {
          $selected="selected";
        }
        echo"<option $selected>$y</option>";
      }
      echo "</select>";
      echo "</td>";


      echo "<td align=\"right\" class=\"home\">$teams[$home]</td>";
      echo "<td align=\"center\"><img src=\"$logos[$home]\"></td><tr>";
    }
    mysql_close($connection);

    echo "<tr><td colspan=\"7\" align=center>";
    echo "Tie Breaker <select size=\"1\" name=\"ou\">";
    for ($ouo=0;$ouo < 100;$ouo=$ouo+.5) {
      $selected = "";
      if ($ouo == $ounow) {
        $selected = "selected";
      }
      echo "<option $selected>$ouo</option>";
    }
    echo "</select>";
    echo "</td></tr>";
    echo "<tr><td colspan=\"7\" align=center>";
    echo "<input type=submit value=\"Submit Spreads\"></td></tr>";
    echo "</table>";

    echo "</form>";
  }
}
else{
  echo "<p>You are not authorized to use this page because you are not logged in or not the commissioner.</p><br><br>";
}

echo "<a href = \"index.php\">Return to Log in page</a><br>";
echo "</body></html>";


?>


