<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$name=$_POST["name"];
$phone=$_POST["phone"];
$email=$_POST["email"];
$password=$_POST["password"];
  $pic=$_POST["pic"];
  
 
  $response=array("Data inserted","Already registered");
  $mysqlinsert="Insert into udrsignup(name,email,mobile,password,created_at,pic) values('$name','$email','$phone','$password',Now(),'$pic')" ;
  if(mysqli_query($conn,$mysqlinsert))
{
              echo json_encode($response[0]);
            } 
            else 
            {
              echo json_encode($response[1]);
        
            }
  mysqli_close($conn);
?>