<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$title=$_POST["title"];

$mysqlq="Select * from udrreviews where title='$title'" ;
$result=mysqli_query($conn,$mysqlq);

$response=array();
while($row=mysqli_fetch_array($result))
{
	array_push($response,array("name"=>$row[1],"comments"=>$row[4],"rating"=>$row[5],"url"=>$row[6],"timestamp"=>$row[8]));
}
  echo json_encode(array("server_response"=>$response));
  
  
  mysqli_close($conn);
?>