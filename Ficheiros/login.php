<?php
require "conn.php";

$name = $_POST["user_name"];
$password = $_POST["password"];

$mysql_qry = "select * from User where user_name like '$name' and password like '$password';";

$result = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $username = $row["user_name"];
    echo "Login Successfull! Welcome, " .$username;
}
else{
    echo "Login not Successful";
}
?>