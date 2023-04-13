<?php
//**********************************************************************
// Project     : NFL Pool Website
// Author      : gmcclannahan
// Description : Change password.
//**********************************************************************
?>
<?php
session_start();
// Get Configuration data and set page variables.
include ('./nfl_config.php');

// grab form variables
$old_user      = $_SESSION["valid_user"];
$passcode      = $_POST["passcode"];
$passcode_new  = $_POST["passcode_new"];
$passcode_new2 = $_POST["passcode_new2"];

if (isset($old_user)) {
  // load stored admin data from file
  // Open a connection to the database
  if (!($connection = @ mysql_connect($hostname,$user,$password))) {
    die("Could not connect to host");
  }

  if (!mysql_select_db($database, $connection)) {
    die("Could not connect to database");
  }

  $db_query = "SELECT * FROM players WHERE userid=\"$old_user\"";
  $query = mysql_query ($db_query,$connection);
  $row = mysql_fetch_object($query);
  $pw_compare    = $row->password;
}


echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>Player Password Change</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

echo"<h1>Player Password Change</h1>";


if(!empty($old_user)) {
  if ($passcode_new != $passcode_new2) {
    echo "Password Change Failed! - The two entries for the new password do not match - <a href=javascript:history.go(-1)>try again</a><br><br>";
  }
  else if (md5($passcode) != $pw_compare) {
    echo "Password Change Failed! - Current password provided doesn\'t match entry in database - <a href=javascript:history.go(-1)>try again</a><br><br>";
  }
  else {
    $passnew = md5($passcode_new);

    $db_query = "UPDATE players SET password = \"$passnew\" WHERE userid=\"$old_user\"";

    if (!(@ mysql_query ($db_query,$connection))) {
      die("Password Change Failed due to Database Problem<br>");
    }

    echo "Password Change Successful!<br>";
  }
  mysql_close($connection);
}
else{
  echo "<p>You are not authorized to use this page because you are not logged in.</p><br><br>";
}

echo "<br>";
echo "<a href = \"index.php\">Return to NFL Pool Main Page</a><br>";
echo "</body></html>";

?>


