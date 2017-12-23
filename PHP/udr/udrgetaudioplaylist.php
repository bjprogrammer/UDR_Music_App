<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$playlistname=$_POST["playlistname"];
$email=$_POST["email"];


$mysqlq="Select musicid from udraudioplaylist where email='$email' AND playlistname='$playlistname'" ;
$result=mysqli_query($conn,$mysqlq);

$response=array();
while($row=mysqli_fetch_array($result))
{
	$mysqlselect="Select * from udrmusic where ID='$row[0]'" ;
        $result1=mysqli_query($conn,$mysqlselect);

        while($row1=mysqli_fetch_array($result1))
        {
          array_push($response,array("title"=>$row1[0],"year"=>$row1[1],"artist"=>$row1[2],"ID"=>$row1[3]));
        }
}
  echo json_encode(array("server_response"=>$response));
  
  
  mysqli_close($conn);
?>