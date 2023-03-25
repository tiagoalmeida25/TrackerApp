<?php
require "conn.php";
$user = $_POST["user_name"];
$value = rtrim($_POST["value"]);
$original_date = $_POST["date"];
$type = $_POST["type"];
$category = $_POST["category"];

$mysql_qry = "select user_id from User where user_name like '$user';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$user_id =  $row["user_id"];

$mysql_qry = "select category_id from Category where category_name like '$category' and user_id like $user_id;";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$category_id =  $row["category_id"];


$mysql_qry = "select type_id from Type where type_name like '$type' and category_id like '$category_id';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$type_id =  $row["type_id"];

$new_date = DateTime::createFromFormat(" d/m/Y H:i", $original_date)->format("Y-m-d H:i");
$date_2 = strtotime($new_date);
$date_3 = date("Y-m-d H:i:00", $date_2);

// echo " Original Date: " .$original_date ." New Date: " .$new_date ." Date 2: " .$date_2 ." Date 3: " .$date_3;

$mysql_qry = "DELETE FROM UserCategory WHERE value like '$value' and type_id like '$type_id' and date like '$date_3';";
$result = mysqli_query($conn, $mysql_qry);

if($result) {
    echo "Deleted";
} else {
    echo "Error: " . mysqli_error($conn);
}

$conn->close();
?>