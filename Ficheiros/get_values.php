<?php
require "conn.php";

$category = $_POST["category"];
$type = $_POST["type"];

$mysql_qry = "select category_id from Category where category_name like '$category';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$category_id =  $row["category_id"];


$mysql_qry = "select type_id from Type where type_name like '$type' and category_id like '$category_id';";
$result = mysqli_query($conn, $mysql_qry);
    
$row = mysqli_fetch_assoc($result);
$type_id =  $row["type_id"];

$mysql_qry = "SELECT * FROM UserCategory where type_id like '$type_id' order by date DESC;";
$result = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($result) > 0) {
    // read
    
    $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
    $values = array();
    $i = 0;
    
    foreach ($rows as $row) {
        
        // list($year, $month, $day, $hour, $min, $sec) = print_r(date_parse($row["date"]));
        // $date =  $day . '/' . $month . '/' . $year . ' ' . $hour . ':' . $min;
        
        $date = date('d/m/Y H:i', strtotime($row["date"]));

        
        $values[$i] = rtrim($row["value"]) ." - " .$date ."ºº";
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