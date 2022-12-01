<?php
$host = "162.241.226.49";
$mysql_username = "tiagoal1_WPVHZ";
$mysql_password = "database.Almeida5";
$db_name = "tiagoal1_tracker_app";
$conn = mysqli_connect($host, $mysql_username, $mysql_password, $db_name);

if (mysqli_connect_errno()) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
    exit();
  }

?>