<?php
//**********************************************************************
// Project     : LSI NFL Pool Website
// Author      : gmcclannahan
// Description : Change password.
//**********************************************************************
?>
<?php
session_start();
// Get Configuration data and set page variables.
include ('./nfl_config.php');

$old_user = $_SESSION["valid_user"];
echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>Player Change Password</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

echo"<h1>Player Password Change</h1>";


if(!empty($old_user)) {
  echo "<form method=post action = \"password_change2.php\">";
  echo "<table>";
  echo "<tr><td>Changing password for Userid: $old_user</td>";
  echo "<tr><td>Current Password:</td>";
  echo "<td><input type=password name=passcode></td></tr>";
  echo "<tr><td>New Password:</td>";
  echo "<td><input type=password name=passcode_new></td></tr>";
  echo "<tr><td>Retype New Password:</td>";
  echo "<td><input type=password name=passcode_new2></td></tr>";
  echo "<tr><td colspan=2 align=left>";
  echo "<input type=submit value=\"Change Password\"></td></tr>";
  echo "</table></form>";
}
else{
  echo "<p>You are not authorized to use this page because you are not logged in.</p><br><br>";
}


echo "<a href = \"index.php\">Return to the NFL Pool Main Page</a><br>";
echo "</body></html>";

?>


