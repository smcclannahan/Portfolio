<?php
//**********************************************************************
// Project     : NFL Pool Make Picks
// Author      : gmcclannahan
// Description : Make player picks.
//**********************************************************************
?>
<?php
date_default_timezone_set("America/Chicago");

session_start();

// Get Configuration data and set page variables.
include ('./nfl_config.php');
include ('./nfl_functions.php');

$old_user = $_SESSION["valid_user"];
echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body  link=\"#FFFFFF\" vlink=\"#808080\">";

if(!empty($old_user)) {

  echo"<h1>Make Picks</h1>";

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
      for ($x=$current_week;$x<$ww;$x++) {
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

    $db_query = "SELECT * FROM players WHERE userid = \"$old_user\"";
    $query = mysql_query ($db_query,$connection);

    if (!($row = mysql_fetch_object($query))) {
      die("Unable to locate player!");
    }
    else {
      $picksmade  = $row->picksmade;
      $madeindex = $weeknum - 1;
      $picksmade[$madeindex] = "Y";
      if ($weeknum < $ww) {
        $entry = "week".$weeknum;
        $home_away = $row->$entry;
        $entry = "tie".$weeknum;
        $tbkr  = $row->$entry;
        if ($tbkr == 0) {
          unset($tbkr);
        }
      }
      else {
        if ($weeknum == $ww) {
          $home_away = $row->wildcard;
        }
        if ($weeknum == $dp) {
          $home_away = $row->divplayoff;
        }
        if ($weeknum == $cc) {
          $home_away = $row->confchamp;
        }
        if ($weeknum == $sb) {
          $home_away = $row->superbowl;
          $tbkr  = $row->tieplayoff;
          if ($tbkr == 0) {
            unset($tbkr);
          }
          $tbkr2  = $row->tieseason;
          if ($tbkr2 == 0) {
            unset($tbkr2);
          }
        }
      }
    }

    $db_query = "SELECT * FROM games WHERE gamenum > $num_low AND gamenum < $num_high";
    $query = mysql_query ($db_query,$connection);
    $num_returned = mysql_num_rows($query);

    for ($x = 0;$x < $num_returned;$x++) {

      if ($home_away[$x] == "H") {
        $checkh = "checked";
        $checka = "";
      }
      else {
        $checka = "checked";
        $checkh = "";
      }
      $row        = mysql_fetch_row($query);
      if ($x == ($num_returned-1)) {
        $ou_pred = $row[9];
      }
      if ($x == 0) {
        $datetime   = $row[1]." ".$row[2];
        $timethen   = strtotime($datetime);
        $timenow    = strtotime("now");
        $timeleft   = $timethen - $timenow;
        $tleft      = $timeleft;
        $days       = floor($timeleft/86400);
        $timeleft   -= ($days*86400);
        $hours      = floor($timeleft/3600);
        $timeleft   -= ($hours*3600);
        $minutes    = floor($timeleft/60);
        $seconds    = $timeleft - ($minutes*60);
        if ($tleft > 0) {
          echo "There are $days days, $hours hours, $minutes minutes, and $seconds seconds until the picks for this week are locked.";
          echo "<form method=post action = \"nfl2.php\">";
        }
        else {
          echo "You may only browse these picks. Any changes are ignored.";
          echo "<form method=post action = \"index.php\">";
        }
        echo "<input type=hidden name=picksmade value=\"$picksmade\">";
        echo "<input type=hidden name=numgames value=$num_returned>";
        echo "<input type=hidden name=weeknum value=$weeknum>";
        echo "<br><br><table border = \"1\"  bordercolor=\"#000080\">";
        echo "<tr><td colspan=\"7\" class=\"tabletitle\">$week</font></td>";
        echo "<tr><td colspan=\"2\" class=\"tablehead\">Game Date/Time</font></td>";
        echo "<td colspan=\"2\" class=\"tablehead\">$awayhead</td>";
        echo "<td class=\"tablehead\">Spread</td>";
        echo "<td colspan=\"2\" class=\"tablehead\">$homehead</td><tr>";
      }
      $gamedate   = f_us_date($row[1]);
      $gametime   = f_us_time($row[2]);
      $away       = $row[3];
      $home       = $row[4];
      $spread     = $row[7];
      $favor_away = false;
      $favor_home = false;
      if ($spread > 0) {
        $favor_home = true;
      }
      if ($spread < 0) {
        $favor_away  = true;
        $spread = abs($spread);
      }
      if ($spread == 0) {
        $spread = "EVEN";
      }
      echo "<tr><td>$gamedate</td>";
      echo "<td>$gametime</td>";
      $rowbutton = "R".$x;
      $theurl = $teamurl.$teamlist[$away];
      echo "<td align=\"center\"><img src=\"$logos[$away]\"></td>";
      echo "<td align=\"left\" class=\"away\"><input type=\"radio\" value=\"A\" name=\"$rowbutton\" $checka><a href=\"$theurl\" target=\"_blank\">$teams[$away]</a></td>";

      if ($favor_away) {
        echo "<td align=\"center\" class=\"away\">$spread</td>";
      }
      if ($favor_home) {
        echo "<td align=\"center\" class=\"home\">$spread</td>";
      }
      if ($spread == "EVEN") {
        echo "<td align=\"center\" class=\"even\">$spread</td>";
      }
      $theurl = $teamurl.$teamlist[$home];
      echo "<td align=\"right\" class=\"home\"><a href=\"$theurl\" target=\"_blank\">$teams[$home]</a><input type=\"radio\" value=\"H\" name=\"$rowbutton\" $checkh></td>";
      echo "<td align=\"center\"><img src=\"$logos[$home]\"></td></tr>";
    }
    $db_query = "SELECT * FROM idle WHERE week = $weeknum";
    $query = mysql_query ($db_query,$connection);
    if (($row = mysql_fetch_row($query))) {
      $idle_team = false;
      for ($z=1;$z<7;$z++) {
        $idle = $row[$z];
        if ($idle != 32) {
          if (!$idle_team) {
            echo "<tr><td colspan=\"7\">Idle teams: ";
            $idle_team = true;
            echo "$teams[$idle]";
          }
          else {
            echo ". $teams[$idle]";
          }
        }
      }
      if (idle_team) {
        echo "</tr>";
      }
    }
    mysql_close($connection);

    if ($weeknum < $ww) {
      echo "<tr><td colspan=\"7\" class=\"tablehead\">Tie Breaker</td></tr>";
      echo "<tr><td colspan=\"6\">Total Score of the Monday Night Football Game ($teams[$away] vs $teams[$home] - Line is $ou_pred):</td>";
      echo "<td><input type=text name=tiebreaker size=3 value=$tbkr ></td></tr>";
    }
    #if ($weeknum == 17) {
    #  echo "<tr><td colspan=\"7\" class=\"tablehead\">Tie Breaker</td></tr>";
    #  echo "<tr><td colspan=\"6\">Total Score of ESPN Sunday Night Football Game ($teams[$away] vs $teams[$home] - Line is $ou_pred):</td>";
    #  echo "<td><input type=text name=tiebreaker size=3 value=$tbkr ></td></tr>";
    #}
    if ($weeknum == $sb) {
      echo "<tr><td colspan=\"7\" class=\"tablehead\">Tie Breakers</td></tr>";
      echo "<tr><td colspan=\"6\">Playoff Tie Breaker - Score of the $teams[$away]:</td>";
      echo "<td><input type=text name=tiebreaker size=3 value=$tbkr></td></tr>";
      echo "<tr><td colspan=\"6\">Season Tie Breaker - Total Score of the Super Bowl - Line is $ou_pred:</td>";
      echo "<td><input type=text name=tiebreaker2 size=3 value=$tbkr2></td></tr>";
    }
    echo "<tr><td colspan=\"7\" align=center>";
    echo "<input type=submit value=\"Submit Picks\"></td></tr>";
    echo "</table>";

    echo "</form>";
  }
}
else{
  echo "<p>You are not authorized to use this page because you are not logged in.</p><br><br>";
}

echo "<br><br><a href = \"index.php\">Return to Log in page</a><br>";
echo "</body></html>";


?>


