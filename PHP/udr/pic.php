<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$email=$_POST["email"];
$password=$_POST["password"];

  $response=array();
  $mysqliselect="Select pic from bdsignup where email='$email' and password='$password'";
  $result=mysqli_query($conn,$mysqliselect);
              while($row=mysqli_fetch_array($result))
              {
                   echo $row[0];
              }        
  mysqli_close($conn);
?>