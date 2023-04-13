<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Process scores
//**********************************************************************
?>
<?php

session_start();

// Get Configuration data and set page variables.
include ('./nfl_config.php');

$old_user = $_SESSION["valid_user"];
echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

if ((isset($_SESSION['valid_user'])) and  (isset($_SESSION['commissioner']))) {
//if ((session_is_registered("valid_user")) and (session_is_registered("commissioner"))) {


  //---------------------------------------------------
  // Grab form values using post var array
  //---------------------------------------------------

  $numgames     = $_POST["numgames"];
  $weeknum      = $_POST["weeknum"];

  for ($x=1;$x <= $numgames;$x++) {
    $t = $weeknum*100+$x;
    $varname = "home".$t;
    $home[$x] = $_POST[$varname];
    $varname = "away".$t;
    $away[$x] = $_POST[$varname];
    $varname = "final".$t;
    $final[$x] = $_POST[$varname];
    if ($final[$x] == "ON") {
      $final[$x] = 1;
    }
    else {
      $final[$x] = 0;
    }
  }

  //---------------------------------------------------
  // Write Spreads to Database
  //---------------------------------------------------

  echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
  echo "<html><head><title>NFL Pool</title>";
  echo "<body>";

  // load stored schedule data from database
  // Open a connection to the database
  if (!($connection = @ mysql_connect($hostname,$user,$password))) {
    die("Could not connect to host");
  }

  if (!mysql_select_db($database, $connection)) {
    die("Could not connect to database");
  }

  for ($x=1;$x <= $numgames;$x++) {

    $gamenum  = $weeknum * 100 + $x;
    $db_query = "UPDATE games SET final = $final[$x], homescore = $home[$x], awayscore = $away[$x] WHERE gamenum=$gamenum";

    if (!(@ mysql_query ($db_query,$connection))) {
      die("Query Failed $db_key $db_query");
    }
  }

  echo "<br>Scores added to database successfully.<br>";

}
else{
  echo "<p>You are not authorized to use this page because you are not logged in or are not the commissioner.</p><br><br>";
}

echo "<a href = \"index.php\">Return to Log in page</a><br>";

echo "</body></html>";

?>

