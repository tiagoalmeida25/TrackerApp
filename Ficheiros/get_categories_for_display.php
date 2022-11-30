<?php
require "conn.php";
$user = $_POST["user_name"];

$mysql_qry = "select user_id from User where user_name like '$user';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$user_id =  $row["user_id"];

$mysql_qry = "SELECT DISTINCT category_name FROM Category where user_id like '$user_id';";
$result = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($result) > 0) {
    // read
    
    $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
    $categories = array();
    $i = 0;
    foreach ($rows as $row) {
        $categories[$i] = rtrim($row["category_name"])  ."»»";
        $i ++;
    }
    
    for ($j = 0; $j < $i; $j++) {
        echo $categories[$j];
    }
}
else{
    echo "No categories added";
}

$conn->close();
?>