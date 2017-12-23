<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
       
$email=$_POST["email"];
$playlistname=$_POST["playlistname"];

 $sql = "DELETE FROM udraudioplaylist WHERE email='$email' and playlistname='$playlistname'";

if (mysqli_query($conn,$sql)) {
    echo "Playlist deleted successfully";
} else {
    echo "Error" ;
}
           
  mysqli_close($conn);
?>