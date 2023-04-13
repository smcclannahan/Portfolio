<?php
//**********************************************************************
// Project     : NFL Pool Website
// Author      : gmcclannahan
// Description : Administrative use only, Authentication for a single
//               adminstrator using php sessions
//**********************************************************************
?>
<?php
session_start();

// Get Configuration data and set page variables.
include ('./nfl_config.php');

$old_user = $_SESSION["valid_user"];
unset($_SESSION[valid_user]); //$result = session_unregister("valid_user");
unset($valid_user);
session_destroy();

echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">";
echo "<html><head><title>NFL Pool</title>";
echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"nfl.css\"></head>";
echo "<body>";

echo"<h1>NFL Pool Log Out</h1>";


    echo "<p>You have successfully Logged Out</p>";


echo "<a href = \"index.php\">Return to Log in page</a><br>";
echo "</body></html>";

?>


