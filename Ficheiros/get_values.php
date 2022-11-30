<?php
require "conn.php";

$type = $_POST["type"];


$mysql_qry = "select type_id from Type where type_name like '$type';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$type_id =  $row["type_id"];

$mysql_qry = "SELECT * FROM UserCategory where type_id like '$type_id';";
$result = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($result) > 0) {
    // read
    
    $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
    $values = array();
    $i = 0;
    foreach ($rows as $row) {
        
        list($day, $month, $year, $hour, $min, $sec) = explode("/", date('d/m/Y/h/i/s'));
        $date =  $day . '/' . $month . '/' . $year . ' ' . $hour . ':' . $min;
        
        $values[$i] = rtrim($row["value"]) ." - " .$date ."..";
        $i++;
    }
    
    for ($j = 0; $j < $i; $j++) {
        echo $values[$j];
    }
}
else{
    echo "Type not found";
}

$conn->close();
?>