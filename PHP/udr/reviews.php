<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

$name=$_POST["name"];
$email=$_POST["email"];
$mobile=$_POST["mobile"];
$comments=$_POST["comments"];
$rating=$_POST["rating"];
$url=$_POST["url"];
$title=$_POST["title"];
  
  $response=array("Thank you for your feedback","Error");
  $mysqlinsert="Insert into udrreviews(name,email,mobile,comments,rating,url,title,timestamp) values('$name','$email','$mobile','$comments','$rating','$url','$title',Now())" ;
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