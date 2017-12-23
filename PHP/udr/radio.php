<?php
require_once 'config.php';
$conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);

   $res=array("Fetching songs","Error");
  $mysqliselect="Select *  from udrradio order by ID";
  $result=mysqli_query($conn,$mysqliselect);
  if(mysqli_num_rows($result)>0)
 { 
             $response=array();
             while($row=mysqli_fetch_array($result))
             {
               	array_push($response,array("title"=>$row[0],"language"=>$row[1],"url"=>$row[3]));
              }
             echo json_encode(array("server_response"=>$response));     
    }
else {
              echo json_encode($res[1]);
     }    
  mysqli_close($conn);
?>