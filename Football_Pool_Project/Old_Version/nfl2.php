<?php
//**********************************************************************
// Project     : NFL Pool
// Author      : gmcclannahan
// Description : Process picks
//**********************************************************************
?>
<?php
date_default_timezone_set("America/Chicago");

session_start();

// Get Configuration data and set page variables.
include ('./nfl_config.php');

$old_user = $_SESSION["valid_user"];
echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

if(!empty($old_user)) {

  // set up for error message printing
  $front = "<font color=red size=2 face=verdana><center><BR>";
  $end = " <BR><a href=javascript:history.go(-1)>Go Back</a></center></font>";

  //---------------------------------------------------
  // Grab form values using post var array
  //---------------------------------------------------

  $tiebreaker   = $_POST["tiebreaker"];
  $tiebreaker2  = $_POST["tiebreaker2"];
  $numgames     = $_POST["numgames"];
  $weeknum      = $_POST["weeknum"];
  $picksmade    = $_POST["picksmade"];

  for ($x=0;$x < $numgames;$x++) {
    $varname = "R".$x;
    $pick = $_POST[$varname];
    $picks .= $pick;
  }

  //---------------------------------------------------
  // Write Picks to Database
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

  $gamelow  = ($weeknum * 100) - 1;
  $gamehigh = ($weeknum + 1) * 100;

  $db_query = "SELECT * FROM games WHERE gamenum > $gamelow AND gamenum < $gamehigh";
  $query = mysql_query ($db_query,$connection);


  for ($x = 0;$x < $numgames;$x++) {
    $row         = mysql_fetch_row($query);
    $away       = $row[3];
    $home       = $row[4];
    if ($picks[$x] == "A") {
      echo "$teams[$away]<br>";
    }
    else {
      echo "$teams[$home]<br>";
    }
  }
  echo "<br>";

  if (($weeknum < $ww) || ($weeknum > $cc)) {
    echo "Tie Breaker is: $tiebreaker<br>";
  }

  if ($weeknum < $ww) {
    $weekfield = "week".$weeknum;
    $tbfield   = "tie".$weeknum;
  }

  $tbout = $tiebreaker;
  if (empty($tiebreaker)) {
    $tbout = 0;
  }

  $tbout2 = $tiebreaker2;
  if (empty($tiebreaker2)) {
    $tbout2 = 0;
  }

  if ($weeknum < $ww) {
    $db_query = "UPDATE players SET $weekfield = \"$picks\", $tbfield = $tbout, picksmade=\"$picksmade\"   WHERE userid=\"$old_user\"";
  }

  if (($weeknum > $wkseason) && ($weeknum < $sb)) {
    if ($weeknum == $ww) {
      $weekfield = "wildcard";
    }
    if ($weeknum == $dp) {
      $weekfield = "divplayoff";
    }
    if ($weeknum == $cc) {
      $weekfield = "confchamp";
    }

    $db_query = "UPDATE players SET $weekfield = \"$picks\", picksmade=\"$picksmade\"  WHERE userid=\"$old_user\"";
  }

  if ($weeknum == $sb) {
    $weekfield = "superbowl";
    $db_query = "UPDATE players SET $weekfield = \"$picks\", picksmade=\"$picksmade\", tieplayoff = $tbout, tieseason = $tbout2 WHERE userid=\"$old_user\"";
  }

  if (!(@ mysql_query ($db_query,$connection))) {
    die("Query Failed $db_key $db_query");
  }

  mysql_close($connection);

  if ($weeknum == $sb) {
    echo "Season Tie Breaker is: $tiebreaker2<br>";
  }

  //---------------------------------------------------
  // Check for proper form fields
  //---------------------------------------------------

  // Validate required form fields

  if(empty($tiebreaker)) {
    if (($weeknum < $ww) && ($weeknum > $cc)) {
      $errorv .= "<BR>Data Entry Error: <BR>A tie breaker must be entered!";
    }
  }

  if(($weeknum == $sb) && (empty($tiebreaker2))) {
    $errorv .= "<BR>Data Entry Error: <BR>A season tie breaker must be entered!";
  }

  // If an error exists, print error string and bail out of the script!
  if($errorv) {
    echo "$front $errorv $end";
    exit;
  }


}
else{
  echo "<p>You are not authorized to use this page because you are not logged in.</p><br><br>";
}

echo "<a href = \"index.php\">Return to Log in page</a><br>";

echo "</body></html>";

?>

