<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$email=$_POST["email"];
$password=$_POST["password"];


   $res=array("logged in","Check your email/password");
  $mysqliselect="Select email from udrsignup where email='$email' and password='$password'";
  $result=mysqli_query($conn,$mysqliselect);
  if(mysqli_num_rows($result)>0)
 { 
              echo json_encode($res[0]);
               
 }
else {
              echo json_encode($res[1]);
     }    
  mysqli_close($conn);
?>