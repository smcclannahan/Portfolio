<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Administrative use only, Set current week
//**********************************************************************
?>
<?php
ini_set("register_globals","1");
ini_set("session.bug_compat_warn","off");

session_start();
// Get Configuration data and set page variables.
include ('./nfl_config.php');

echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

echo "<h1>Set Week of Season</h1>";


if ((isset($_SESSION['valid_user'])) and  (isset($_SESSION['commissioner']))) {
//if ((session_is_registered("valid_user")) and (session_is_registered("commissioner"))) {
  echo "<BR>";
  echo "<form method=post action = \"setweek2.php\">";

  $week = $_POST["week"];
  $weeklen = strlen($week);
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
  }

  if (!($connection = @ mysql_connect($hostname,$user,$password))) {
    die("Could not connect to host");
  }

  if (!mysql_select_db($database, $connection)) {
    die("Could not connect to database");
  }

  $db_query = "UPDATE control SET weeknow = $weeknum WHERE 1";

  if (!(@ mysql_query ($db_query,$connection))) {
    die("Query Failed $db_key $db_query");
  }

  echo "<br>Week set to $week successfully.<br>";

}
else{
  echo "<p>You are not authorized to use this page because you are not logged in or are not the commissioner.</p><br><br>";
}

echo "<a href = \"index.php\">Return to Log in page</a><br>";
echo "</body></html>";

?>

