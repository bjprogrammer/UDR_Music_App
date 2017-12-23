<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$email=$_POST["email"];
  
 $mysqlq="Select DISTINCT playlistname from udraudioplaylist where email='$email'" ;
$result=mysqli_query($conn,$mysqlq);

$response=array();
while($row=mysqli_fetch_array($result))
{
	array_push($response,array("playlistname"=>$row[0]));
}
  echo json_encode(array("server_response"=>$response));
  mysqli_close($conn);
?>