<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Administrative use only, Authentication for a single
//               adminstrator using php sessions
//**********************************************************************
?>
<?php
date_default_timezone_set("America/Chicago");
ini_set("register_globals","1");
ini_set("session.bug_compat_warn","off");

session_start();
// Get Configuration data and set page variables.
include ('./nfl_config.php');

echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool Login</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

echo "<h1>NFL Pool Log In</h1>";

// grab form variables
$name = $_POST["name"];
$passcode = $_POST["passcode"];

if (isset($name)) {
  // load stored admin data from file
  // Open a connection to the database
  if (!($connection = @ mysql_connect($hostname,$user,$password))) {
    die("Could not connect to host");
  }

  if (!mysql_select_db($database, $connection)) {
    die("Could not connect to database");
  }

  $db_query = "SELECT * FROM players WHERE userid=\"$name\"";
  $query = mysql_query ($db_query,$connection);
  $row = mysql_fetch_object($query);
  $pw_compare    = $row->password;
  $real_name     = $row->realname;
  $_SESSION['rname'] = $real_name;
  $_SESSION['uname'] = $name;
  $commish       = $row->commissioner;
  mysql_close($connection);
}


// authenticate
if((isset($name)) && (md5($passcode) == $pw_compare)) {
  //$valid_user = $name;
  $_SESSION['valid_user'] = $name;//session_register("valid_user");
  if ($commish == 1) {
    $_SESSION['commissioner'] = "true";//session_register("commissioner");
  }
}


//if (session_is_registered("valid_user")) {
if (isset($_SESSION['valid_user'])) {
  echo "<BR>";
  if (isset($actual_name)) {
    $da_name = $_SESSION['rname'];
    echo "Welcome $da_name!<BR>";
  }
  $username_used = $_SESSION['uname'];
  echo "You are logged in as : $username_used<BR>";
 // if (session_is_registered("commissioner")) {
  if (isset($_SESSION['commissioner'])) {
    echo "You have commissioner level access.<BR>";
  }
  echo "<br>";
  echo "Proceed to <a href = \"nfl.php\">Making Picks</a><br>";
  echo "Statistics <a href = \"stats2.php\">Page</a><br>";
  echo "Change your <a href = \"password_change.php\">password</a><br>";
  if (isset($_SESSION['commissioner'])) {
//  if (session_is_registered("commissioner")) {
    echo "Set current <a href = \"setweek.php\">week</a><br>";
    echo "Set <a href = \"setspreads.php\">spreads</a> for the current week<br>";
    echo "Enter <a href = \"setscores.php\">scores</a> for the current week<br>";
    echo "Update <a href = \"results.php\">results</a> for the current week<br>";
    echo "Determine <a href = \"winner.php\">winner</a> for the current week<br>";
  }
  echo "<br><a href=\"logout.php\">Log out</a><br><br>";
} else {
  if(isset($name)) { //login failed
    echo "<font color = red size =3>Authentication failed</font><br>";
  }else{
    echo "You are not logged in<br>";
  }
  echo "<form action = \"index.php\" method =\"post\">";
  echo "<table>";
  echo "<tr><td>Userid:</td>";
  echo "<td><input type=text name = name></td></tr>";
  echo "<tr><td>Password:</td>";
  echo "<td><input type=password name=passcode></td></tr>";
  echo "<tr><td colspan=2 align=center>";
  echo "<input type=submit value=\"Log In\"></td></tr>";
  echo "</table></form>";
}


  echo "<br>";
  echo "<center>";

  echo "<h1>Current Standings</h1>";
  echo "</center>";
  echo "<table border=\"1\" width=\"100%\">";
  echo "<tr><td class=\"tabletitle\" colspan=\"3\">Player</td><td class=\"tabletitle\" colspan=\"22\">Week</td></tr>";
  echo "<tr><td class=\"tablehead\">Name</td><td class=\"tablehead\">Total</td><td class=\"tablehead\">Weeks Won</td>";
  for ($y=1;$y<$ww;$y++) {
    echo "<td class=\"tablehead\" width=\"4%\">$y</td>";
  }
  echo "<td class=\"tablehead\" width=\"4%\">WW</td>";
  echo "<td class=\"tablehead\" width=\"4%\">DP</td>";
  echo "<td class=\"tablehead\" width=\"4%\">CC</td>";
  echo "<td class=\"tablehead\" width=\"4%\">SB</td><tr>";

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
    if ($current_week < $ww) {
      $week_name = "Week ".$current_week;
    }
    if ($current_week == $ww) {
      $week_name = "Wildcard Weekend";
    }
    if ($current_week == $dp) {
      $week_name = "Divisional Playoff";
    }
    if ($current_week == $cc) {
      $week_name = "Conference Championship";
    }
    if ($current_week == $sb) {
      $week_name = "Super Bowl";
    }
  }

  $db_query = "SELECT * FROM winners WHERE 1";
  $query = mysql_query ($db_query,$connection);
  $winners_returned = mysql_num_rows($query);
  for ($x=0;$x<$winners_returned;$x++) {
    $row              = mysql_fetch_row($query);
    $weekwin          = $row[0];
    $winner[$weekwin] = $row[1];
  }

  $season_winner = $winner[25];

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

  for ($x=0;$x<$num_returned;$x++) {
    $row           = mysql_fetch_row($query);
    $pname         = strtoupper($row[$name_offset]);
    $pckname[$x]   = $pname;
    $picks[$x]     = $row[$current_week+$pick_offset];
    $pvalid        = $row[$pvalid_offset];
    $madepicks[$x] = $pvalid[$current_week - 1];
    if ($current_week < $ww) {
      $tb[$x]      = $row[$current_week+$tbrk_weekly_offset];
    }
    else {
      $tb[$x]      = $row[$tbrk_playoff_offset];
    }
    $tb2[$x]       = $row[$tbrk_season_offset];
    $ww_cnt[$x]    = $row[$weeks_won_offset];
//    $ww[$x]      = $ww;

    $rightarray[0] = 0;
    for ($yy=1;$yy < $inv;$yy++) {
      $rightarray[0] +=  $row[$right_offset+$yy];
      $rightarray[$yy] = $row[$right_offset+$yy];
    }

    $right[$x] = $rightarray[0];


    if ($season_winner == $pname) {
      $tablerow[$x] = "<tr><td class=\"winner\">$pname</td><td align=\"right\" class=\"names\">$rightarray[0]</td><td align=\"right\" class=\"names\">$ww_cnt[$x]</td>";
    }
    else {
     if ($madepicks[$x] == "N") {
     	 $tablerow[$x] = "<tr><td class=\"nonemade\">$pname</td><td align=\"right\" class=\"names\">$rightarray[0]</td><td align=\"right\" class=\"names\">$ww_cnt[$x]</td>";

     }
     else {
     	 $tablerow[$x] = "<tr><td class=\"names\">$pname</td><td align=\"right\" class=\"names\">$rightarray[0]</td><td align=\"right\" class=\"names\">$ww_cnt[$x]</td>";
     }
    }
    for ($z=1;$z < $inv;$z++) {
      if (strtoupper($winner[$z]) == $pname) {
        $tablerow[$x] .= "<td class=\"winner\" align=\"right\" width=\"4%\">$rightarray[$z]</td>";
      }
      else {
        if ($z < $current_week) {
          if (($rightarray[$z] < 3) && ($z < $wkseason)) {
           $tablerow[$x] .= "<td class=\"loser\" align=\"right\" width=\"4%\">$rightarray[$z]</td>";
 	      }
          else {
           $tablerow[$x] .= "<td class=\"names\" align=\"right\" width=\"4%\">$rightarray[$z]</td>";
          }
        }
        else {
          $tablerow[$x] .= "<td class=\"names\" align=\"right\" width=\"4%\">$rightarray[$z]</td>";
        }
      }
    }
    $tablerow[$x] .= "</tr>";
  }

  array_multisort($right, SORT_DESC, $ww_cnt, SORT_DESC, $tablerow, SORT_ASC);

  for ($z=0;$z<count($pckname);$z++) {
    echo $tablerow[$z];
  }
  echo "</table>";

  $game1 = $current_week*100 + 1;
  $db_query = "SELECT * FROM games WHERE gamenum = $game1";
  $query = mysql_query ($db_query,$connection);

  if (!($row = mysql_fetch_object($query))) {
    die("Unable to read Game 1 for week!");
  }
  else {
    $game1date = $row->date;
    $game1time = $row->time;
  }
  array_multisort($pckname, SORT_ASC);

  $datetime   = $game1date." ".$game1time;
  $timethen   = strtotime($datetime);
  $timenow    = strtotime("now");
  $timeleft   = $timethen - $timenow;
  $days       = floor($timeleft/86400);
  $timeleft   -= ($days*86400);
  $hours      = floor($timeleft/3600);
  $timeleft   -= ($hours*3600);
  $minutes    = floor($timeleft/60);
  $seconds    = $timeleft - ($minutes*60);

  if ($timenow < $timethen) {
    echo "<br><br>Picks for $week_name will be visible in $days days, $hours hours, $minutes minutes, and $seconds seconds.";
  }
  else {

  echo "<center>";
  echo "<h1>Picks for $week_name</h1>";
  echo "</center>";
  echo "<table border=\"1\" width=\"100%\">";
  echo "<tr><td class=\"tabletitle\" colspan=\"3\">Games</td><td class=\"tabletitle\" colspan=\"$num_returned\">Players</td></tr>";
  echo "<tr><td class=\"tablehead\">Away/Home</td><td class=\"tablehead\">Final Score</td><td class=\"tablehead\">Spread</td>";

  for ($x=0;$x<$num_returned;$x++) {
    echo "<td class=\"tablehead\">$pckname[$x]</td>";
  }

  echo "</tr>";

  for ($x=0;$x<$games_returned;$x++) {
    $a = $awaynum[$x];
    $h = $homenum[$x];
    echo "<tr><td class=\"names\">$abbrev[$a]/$abbrev[$h]</td>";
    if ($final[$x]) {
      echo "<td class=\"names\">$awayscore[$x]-$homescore[$x]</td>";
    }
    else {
      echo "<td class=\"names\">TBD</td>";
    }
    if ($spread[$x] < 0) {
      $absspread = abs($spread[$x]);
      echo"<td class=\"names\">$abbrev[$a] by $absspread</td>";
    }
    else {
      if ($spread[$x] == 0) {
        echo "<td class=\"names\">EVEN</td>";
      }
      else {
        echo"<td class=\"names\">$abbrev[$h] by $spread[$x]</td>";
      }
    }
    $realspread = $homescore[$x] - $awayscore[$x];

    $no_contest = false;
    if ($realspread == $spread[$x]) {
      $no_contest = true;
    }
    else {
      if ($spread[$x] > 0) {
        if ($realspread > $spread[$x]) {
          $right_pick = "H";
        }
        else {
          $right_pick = "A";
        }
      }

      if ($spread[$x] < 0) {
        if ($realspread < $spread[$x]) {
          $right_pick = "A";
        }
        else {
          $right_pick = "H";
        }
      }

      if ($spread[$x] == 0) {
        if ($homescore[$x] > $awayscore[$x]) {
          $right_pick = "H";
        }
        else {
          $right_pick = "A";
        }
      }
    }

    for ($y=0;$y<$num_returned;$y++) {
      $t = $picks[$y];
      if ($madepicks[$y] == "Y") {
        if ($t[$x] == "H") {
          if ($final[$x] == 0) {
            echo "<td class=home>$abbrev[$h]</td>";
          }
          else {
            if ($no_contest) {
              echo "<td class=nocontest>$abbrev[$h]</td>";
            }
            else {
              if ($right_pick == "H") {
                echo "<td class=correct>$abbrev[$h]</td>";
              }
              else {
                echo "<td class=wrong>$abbrev[$h]</td>";
              }
            }
          }
        }
        else {
          if ($final[$x] == 0) {
            echo "<td class=away>$abbrev[$a]</td>";
          }
          else {
            if ($no_contest) {
              echo "<td class=nocontest>$abbrev[$a]</td>";
            }
            else {
              if ($right_pick == "A") {
                echo "<td class=correct>$abbrev[$a]</td>";
              }
              else {
                echo "<td class=wrong>$abbrev[$a]</td>";
              }
            }
          }
        }
      }
      else {
        echo "<td class=nopick>NP</td>";
      }
    }
    echo "</tr>";
  }


  $tbreak = $homescore[$games_returned - 1] + $awayscore[$games_returned-1];
  $ou     = $overunder[$games_returned-1];

  if ($current_week < $ww) {
    if ($final[$games_returned - 1] == 0) {
      $tbreak = "TBD";
    }
    echo "<tr><td class=\"names\">Tie Breaker</td>";
    echo "<td class=\"names\">$tbreak</td><td class=\"names\">$ou</td>";

    for ($y=0;$y<$num_returned;$y++) {
      if ($final[$games_returned - 1] == 0) {
        echo "<td class=tiebreak>$tb[$y]</td>";
      }
      else {
        $diff = $tb[$y] - $tbreak;
        if ($diff > 0) {
          $diff = "+".$diff;
        }
        echo "<td class=tbresult>$diff</td>";
      }
    }

    echo "</tr>";
  }
  if ($current_week > $cc) {
    if ($final[$games_returned - 1] == 0) {
      $tbreak  = "TBD";
      $tbreak2 = "TBD";
    }
    echo "<tr><td class=\"names\">Tie Breaker Playoffs</td>";
    $temptb = $awayscore[$games_returned-1];
    echo "<td class=\"names\">$temptb</td><td class=\"names\">XXX</td>";

    for ($y=0;$y<$num_returned;$y++) {
      if ($final[$games_returned - 1] == 0) {
        echo "<td class=tiebreak>$tb[$y]</td>";
      }
      else {
        $diff = $tb[$y] - $awayscore[$games_returned-1];
        if ($diff > 0) {
          $diff = "+".$diff;
        }
        echo "<td class=tbresult>$diff</td>";
      }
    }

    echo "</tr>";
    echo "<tr><td class=\"names\">Tie Breaker Season</td>";
    echo "<td class=\"names\">$tbreak</td><td class=\"names\">$ou</td>";

    for ($y=0;$y<$num_returned;$y++) {
      if ($final[$games_returned - 1] == 0) {
        echo "<td class=tiebreak>$tb2[$y]</td>";
      }
      else {
        $diff = $tb2[$y] - $tbreak;
        if ($diff > 0) {
          $diff = "+".$diff;
        }
        echo "<td class=tbresult>$diff</td>";
      }
    }

    echo "</tr>";
  }
  echo "</table>";

  }


  if ($current_week > 1) {
    for ($pweek=$current_week - 1;$pweek > 0;$pweek--){
      if ($pweek < $ww) {
        $week_name = "Week ".$pweek;
      }
      if ($pweek == $ww) {
        $week_name = "Wildcard Weekend";
      }
      if ($pweek == $dp) {
        $week_name = "Divisional Playoff";
      }
      if ($pweek == $cc) {
        $week_name = "Conference Championship";
      }
      if ($pweek == $sb) {
        $week_name = "Super Bowl";
      }

      $num_low  = $pweek * 100 - 1;
      $num_high = ($pweek + 1) * 100;
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

      for ($x=0;$x<$num_returned;$x++) {
        $row           = mysql_fetch_row($query);
        $pname[$x]     = strtoupper($row[$name_offset]);
        $picks[$x]     = $row[$pweek+$pick_offset];
        $pvalid        = $row[$pvalid_offset];
        $madepicks[$x] = $pvalid[$pweek - 1];
        $tb[$x]        = $row[$pweek+$tbrk_weekly_offset];
      }

        echo "<center>";
        echo "<h1>Picks for $week_name</h1>";
        echo "</center>";
        echo "<table border=\"1\" width=\"100%\">";
        echo "<tr><td class=\"tabletitle\" colspan=\"3\">Games</td><td class=\"tabletitle\" colspan=\"$num_returned\">Players</td></tr>";
        echo "<tr><td class=\"tablehead\">Away/Home</td><td class=\"tablehead\">Final Score</td><td class=\"tablehead\">Spread</td>";

        for ($x=0;$x<$num_returned;$x++) {
          echo "<td class=\"tablehead\">$pckname[$x]</td>";
        }

        echo "</tr>";

        for ($x=0;$x<$games_returned;$x++) {
          $a = $awaynum[$x];
          $h = $homenum[$x];
          echo "<tr><td class=\"names\">$abbrev[$a]/$abbrev[$h]</td>";
          if ($final[$x]) {
            echo "<td class=\"names\">$awayscore[$x]-$homescore[$x]</td>";
          }
          else {
            echo "<td class=\"names\">TBD</td>";
          }
          if ($spread[$x] < 0) {
            $absspread = abs($spread[$x]);
            echo"<td class=\"names\">$abbrev[$a] by $absspread</td>";
          }
          else {
            if ($spread[$x] == 0) {
              echo "<td class=\"names\">EVEN</td>";
            }
            else {
              echo"<td class=\"names\">$abbrev[$h] by $spread[$x]</td>";
            }
          }
          $realspread = $homescore[$x] - $awayscore[$x];

          $no_contest = false;
          if ($realspread == $spread[$x]) {
            $no_contest = true;
          }
          else {
            if ($spread[$x] > 0) {
              if ($realspread > $spread[$x]) {
                $right_pick = "H";
              }
              else {
                $right_pick = "A";
              }
            }

            if ($spread[$x] < 0) {
              if ($realspread < $spread[$x]) {
                $right_pick = "A";
              }
              else {
                $right_pick = "H";
              }
            }

            if ($spread[$x] == 0) {
              if ($homescore[$x] > $awayscore[$x]) {
                $right_pick = "H";
              }
              else {
                $right_pick = "A";
              }
            }
          }

          for ($y=0;$y<$num_returned;$y++) {
            $t = $picks[$y];
            if ($madepicks[$y] == "Y") {
              if ($t[$x] == "H") {
                if ($final[$x] == 0) {
                  echo "<td class=home>$abbrev[$h]</td>";
                }
                else {
                  if ($no_contest) {
                    echo "<td class=nocontest>$abbrev[$h]</td>";
                  }
                  else {
                    if ($right_pick == "H") {
                      echo "<td class=correct>$abbrev[$h]</td>";
                    }
                    else {
                      echo "<td class=wrong>$abbrev[$h]</td>";
                    }
                  }
                }
              }
              else {
                if ($final[$x] == 0) {
                  echo "<td class=away>$abbrev[$a]</td>";
                }
                else {
                  if ($no_contest) {
                    echo "<td class=nocontest>$abbrev[$a]</td>";
                  }
                  else {
                    if ($right_pick == "A") {
                      echo "<td class=correct>$abbrev[$a]</td>";
                    }
                    else {
                      echo "<td class=wrong>$abbrev[$a]</td>";
                    }
                  }
                }
              }
            }
            else {
              echo "<td class=nopick>NP</td>";
            }
          }
          echo "</tr>";
        }


        $tbreak = $homescore[$games_returned - 1] + $awayscore[$games_returned-1];
        $ou     = $overunder[$games_returned-1];

        if (($pweek < $wkseason) | ($pweek > $cc)) {
          if ($final[$games_returned - 1] == 0) {
            $tbreak = "TBD";
          }
          echo "<tr><td class=\"names\">Tie Breaker</td>";
          echo "<td class=\"names\">$tbreak</td><td class=\"names\">$ou</td>";

          for ($y=0;$y<$num_returned;$y++) {
            if ($final[$games_returned - 1] == 0) {
              echo "<td class=tiebreak>$tb[$y]</td>";
            }
            else {
              $diff = $tb[$y] - $tbreak;
              if ($diff > 0) {
                $diff = "+".$diff;
              }
              echo "<td class=tbresult>$diff</td>";
            }
          }
        }

        echo "</tr></table>";

    }
  }

  mysql_close($connection);

  echo "</body></html>";


?>

