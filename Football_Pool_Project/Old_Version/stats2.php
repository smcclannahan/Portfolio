<?php

ini_set("register_globals","1");
ini_set("session.bug_compat_warn","off");

session_start();
// Get Configuration data and set page variables.
include ('./nfl_config.php');
include ('./game.class');
include ('./schedule.class');


echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool Login</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

echo "<h1>NFL Pool Statistics Page</h1>";

// load stored admin data from file
// Open a connection to the database
if (!($connection = @ mysql_connect($hostname,$user,$password))) {
  die("Could not connect to host");
}

if (!mysql_select_db($database, $connection)) {
  die("Could not connect to database");
}

$db_query = "SELECT * FROM games WHERE final=1";
$query = mysql_query ($db_query,$connection);

$games_returned = mysql_num_rows($query);


for ($y=0; $y<count($teamlist)-1; $y++) {
  $team_schedules[$y] = new Schedule($y);
};

for ($x=0;$x<$games_returned;$x++) {
  $row  = mysql_fetch_row($query);
  $game = new Game();
  $game->num        = $row[0];
  $game->away       = $row[3];
  $game->home       = $row[4];
  $game->away_score = $row[5];
  $game->home_score = $row[6];
  $game->spread     = $row[7];

  $at = $game->away;
  $ht = $game->home;

  $team_schedules[$at]->addGame($game);
  $team_schedules[$ht]->addGame($game);
}


echo "<br><br><table border = \"1\"  bordercolor=\"#000080\" width = \"80%\">";
echo "<tr><td colspan=\"21\" class=\"tabletitle\">2014 NFL Standings</font></td>";

echo "<tr><td colspan=\"3\" class=\"tablehead\"></td>";
echo "<td colspan=\"3\" class=\"tablehead\">Record</td>";
echo "<td colspan=\"3\" class=\"tablehead\">Home Record</td>";
echo "<td colspan=\"3\" class=\"tablehead\">Away Record</td>";
echo "<td colspan=\"3\" class=\"tablehead\">Record Against Spread</td>";
echo "<td colspan=\"3\" class=\"tablehead\">Home Record Against Spread</td>";
echo "<td colspan=\"3\" class=\"tablehead\">Away Record Against Spread</td></tr>";

$my_tl = $teamlist;
$my_td = $team_div;

for ($i=0; $i<count($teamlist)-1; $i++) {
        $wl_diff[$i] = $team_schedules[$i]->getWins() - $team_schedules[$i]->getLosses();
};

array_multisort($my_td, SORT_ASC, $wl_diff, SORT_DESC, $team_schedules);

for ($y=0; $y<count($teamlist)-1; $y++) {
        $tid = $team_schedules[$y]->team_id;

    $rec     = $team_schedules[$y]->getRecord();
    $rec_AS  = $team_schedules[$y]->getRecordAS();
    $hrec    = $team_schedules[$y]->getHomeRecord();
    $hrec_AS = $team_schedules[$y]->getHomeRecordAS();
    $arec    = $team_schedules[$y]->getAwayRecord();
    $arec_AS = $team_schedules[$y]->getAwayRecordAS();

    if ($y % 4 == 0) {
        $div = $y / 4;
        echo "<tr><td colspan=\"3\" class=\"tablehead\">$divisions[$div]</td>";
                echo "<td class=\"tablehead\">W</td>";
                echo "<td class=\"tablehead\">L</td>";
                echo "<td class=\"tablehead\">T</td>";
                echo "<td class=\"tablehead\">W</td>";
                echo "<td class=\"tablehead\">L</td>";
                echo "<td class=\"tablehead\">T</td>";
                echo "<td class=\"tablehead\">W</td>";
                echo "<td class=\"tablehead\">L</td>";
                echo "<td class=\"tablehead\">T</td>";
                echo "<td class=\"tablehead\">W</td>";
                echo "<td class=\"tablehead\">L</td>";
                echo "<td class=\"tablehead\">T</td>";
                echo "<td class=\"tablehead\">W</td>";
                echo "<td class=\"tablehead\">L</td>";
                echo "<td class=\"tablehead\">T</td>";
                echo "<td class=\"tablehead\">W</td>";
                echo "<td class=\"tablehead\">L</td>";
                echo "<td class=\"tablehead\">T</td></tr>";
    };

        $theurl = $teamurl.$teamlist[$tid];

        echo "<tr><td width=\"5%\"><a href=\"$theurl\" target=\"_blank\"><img src=\"$logos[$tid]\"></a></td>";
        echo "<td colspan=\"2\" width=\"23%\"><a href=\"$theurl\" target=\"_blank\">$teams[$tid]</a></td>";

        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$rec[0]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$rec[1]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$rec[2]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$hrec[0]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$hrec[1]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$hrec[2]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$arec[0]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$arec[1]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$arec[2]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$rec_AS[0]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$rec_AS[1]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$rec_AS[2]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$hrec_AS[0]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$hrec_AS[1]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#fdf9a2\">$hrec_AS[2]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$arec_AS[0]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$arec_AS[1]</td>";
        echo "<td align=\"center\" width=\"4%\" bgcolor=\"#d2fefa\">$arec_AS[2]</td></tr>";

}
echo "</table>";

echo "</body>";
echo "</html>";

?>