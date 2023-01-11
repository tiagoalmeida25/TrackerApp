<?php
require "conn.php";

$name = $_POST["user_name"];
$password = $_POST["password"];

$name = rtrim($name);
$password = rtrim($password);

$error = false;

if (empty($name)){
    echo "Name can't be empty";
    $error = true;
}
if(empty($password)){
    echo "Password can't be empty";
    $error = true;
}

if(!$error){
    $mysql_qry = "SELECT * FROM `User` where user_name like '$name';";
    $result = mysqli_query($conn, $mysql_qry);
    
    if (mysqli_num_rows($result)) {
        $row = mysqli_fetch_assoc($result);
        $name = $row["user_name"];
        echo "Name " .$name ." already exists!";
    }
    else{
        $mysql_qry = "insert into User (user_name,password) values ('$name', '$password')";
        
        if($conn->query($mysql_qry) === TRUE){
            echo "Registration Successful";
        }
        else{
            echo "Error in Registration: " . $mysql_qry . "<br>" . $conn->error;
        }
    }
}

$conn->close();
?>