<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$playlistname=$_POST["playlistname"];
$email=$_POST["email"];
$musicid=$_POST["musicid"];
  
  $response=array("Muisc added to playlist","Error");
  $mysqlinsert="Insert into udraudioplaylist(email,playlistname,created_at,musicid) values('$email','$playlistname',Now(),'$musicid')" ;
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