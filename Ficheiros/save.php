<?php
require "conn.php";

$user = $_POST["user_name"];
$category = $_POST["category"];
$type = $_POST["type"];
$value = $_POST["value"];

function get_category_id(){
    if (empty($category)){
        echo "Category can't be empty";
        return null;
    }

    if(empty($value)){
        echo "Value can't be empty";
        return null;
    }
    
    $mysql_qry = "select * from Category where category_name like '$category';";
    $result = mysqli_query($conn, $mysql_qry);
    
    if (mysqli_num_rows($result)) {
        // read
        $row = mysqli_fetch_assoc($result);
        return $row["category_id"];
    }
    else{
        // insert
        $mysql_query_category = "insert into Category(category_name) values ($category)";
        
        if($conn->query($mysql_query_category) === TRUE){
            // echo "Registration Successful";
            
            // QUESTAO - PRECISA DESTA PARTE
            $mysql_qry = "select * from Category where category_name like '$category';";
            $result = mysqli_query($conn, $mysql_qry);
            
            if (mysqli_num_rows($result)) {
                // read
                $row = mysqli_fetch_assoc($result);
                return $row["category_id"];
            }
        }
        else{
            echo "Error in registration of category: " . $mysql_query_category . "<br>" . $conn->error;
        }
    }
    return null;
}


function get_user_id(){
    // ja sabemos que existe
    $mysql_qry = "select * from User where user_name like '$user';";
    $result = mysqli_query($conn, $mysql_qry);
    
    $row = mysqli_fetch_assoc($result);
    return $row["user_id"];
}

function get_type_id($cat_id){
    $mysql_qry = "select * from Type where type_name like '$type';";
    $result = mysqli_query($conn, $mysql_qry);
    
    if (mysqli_num_rows($result)) {
        // read
        $row = mysqli_fetch_assoc($result);
        return $row["type_id"];
    }
    else{
        // insert type_name and the respectively category_id
        $mysql_query_type = "insert into Type(type_name, category_id) values ($type, $category_id)";
        
        if($conn->query($mysql_query_type) === TRUE){
            // echo "Registration Successful";
            
            // QUESTAO - PRECISA DESTA PARTE v2
            $mysql_qry = "select * from Type where type_name like '$type';";
            $result = mysqli_query($conn, $mysql_qry);
            
            if (mysqli_num_rows($result)) {
                // read
                $row = mysqli_fetch_assoc($result);
                return $row["type_id"];
            }
        }
        else{
            echo "Error in registration of type: " . $mysql_query_category . "<br>" . $conn->error;
        }
    }
    return null;
    
}

$category_id = get_category_id();
$user_id = get_user_id();
$type_id = get_type_id($category_id);

$error = false;

if(is_null($category_id) or is_null($type_id)){
    $error = true;
}

if(!$error){
    $now = date("H:i:s d-m-Y");
    $mysql_query = "insert into UserCategory(value, date, type_id, user_id) values ($value, $now , $type_id, $user_id)";
    
    if($conn->query($mysql_qry) === TRUE){
        echo "Saved!";
    }
    else{
        echo "Error in saving: " . $mysql_qry . "<br>" . $conn->error;
    }
}

$conn->close();
?>