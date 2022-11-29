<?php
require "conn.php";
$category = $_POST["category"];

$mysql_qry = "select * from Category where category_name like '$category';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$category_id =  $row["category_id"];

$mysql_qry = "SELECT DISTINCT type_name FROM Type where category_id like '$category_id';";
$result = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($result) > 0) {
    // read
    
    $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
    $categories = array();
    $i = 0;
    foreach ($rows as $row) {
        // printf("%s", $row["category_name"]);
        $categories[$i] = rtrim($row["category_name"])  ."++";
        $i ++;
    }
    
    for ($j = 0; $j < $i; $j++) {
        echo $categories[$j];
    }
}
else{
    echo "";
}
$conn->close();
?>