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


if ((isset($_SESSION['valid_user'])) and (isset($_SESSION['commissioner']))) {
//if ((session_is_registered("valid_user")) and (session_is_registered("commissioner"))) {
  echo "<BR>";
  echo "<form method=post action = \"setweek2.php\">";

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
  }

  echo "<select size=\"1\" name=\"week\">";

  for ($x = 1; $x < $inv;$x++) {
    if ($x == $current_week) {
      $selected = "selected";
    }
    else {
      $selected = "";
    }
    if ($x <$ww) {
      echo"<option $selected>Week $x</option>";
    }
    if ($x == $ww) {
      echo"<option $selected>Wildcard Weekend</option>";
    }
    if ($x == $dp) {
      echo"<option $selected>Divisional Playoffs</option>";
    }
    if ($x == $cc) {
      echo"<option $selected>Conference Championships</option>";
    }
    if ($x == $sb) {
      echo"<option $selected>Super Bowl</option>";
    }
  }
    echo "<br>";
    echo "<input type=submit value=\"Pick Week\">";
    echo "</form>";

}
else{
  echo "<p>You are not authorized to use this page because you are not logged in or are not the commissioner.</p><br><br>";
  echo "<a href = \"index.php\">Return to Log in page</a><br>";
}


echo "</body></html>";

?>

