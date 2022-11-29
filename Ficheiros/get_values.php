<?php
require "conn.php";

$type = $_POST["type"];

$mysql_qry = "select type_id from Type where type_name like '$type';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$type_id =  $row["type_id"];

$mysql_qry = "SELECT value FROM UserCategory where type_id like '$type_id';";
$result = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($result) > 0) {
    // read
    
    $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
    $values = array();
    $i = 0;
    foreach ($rows as $row) {
        $values[$i] = rtrim($row["value"])  ."..";
        $i++;
    }
    
    for ($j = 0; $j < $i; $j++) {
        echo $values[$j];
    }
}
else{
    echo "This Type has no values";
}

$conn->close();
?>