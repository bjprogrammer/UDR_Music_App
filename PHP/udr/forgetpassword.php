<?php
     require_once 'config.php';
     $conn = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
         
         $email=$_POST["email"];
         $to =$email;
         $subject = "Password for UDR music app";
         
         $header = "From:bobbyj.sixthsense@gmail.com \r\n";
         $header .= "Cc:".$email." \r\n";
         $header .= "MIME-Version: 1.0\r\n";
         $header .= "Content-type: text/html\r\n";
         
         function randomPassword() 
         {
               $alphabet = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
               $pass = array(); //remember to declare $pass as an array
               $alphaLength = strlen($alphabet) - 1; //put the length -1 in cache
               for ($i = 0; $i < 8; $i++) 
                {
                    $n = rand(0, $alphaLength);
                    $pass[] = $alphabet[$n];
                 }
                return implode($pass); //turn the array into a string
        }

         $mysqliselect1="Select email from udrsignup where email='$email' ";
         $result1=mysqli_query($conn,$mysqliselect1);
         if(mysqli_num_rows($result1)>0)
         {
               $OTP= randomPassword();
               $message = "<b>OTP for your account is-".$OTP."</b>";
           if(mail ($to,$subject,$message,$header))
           {
             
             $mysqliselect2="Select OTP from udrforgetpassword where email='$email' ";
             $result2=mysqli_query($conn,$mysqliselect2);
             if(mysqli_num_rows($result2)>0)
             {
             
               if(mysqli_query($conn, "UPDATE udrforgetpassword SET OTP='$OTP' WHERE email='$email'"))
                {
                  echo json_encode("OTP sent successfully");
                } 
                else 
                {
                   echo json_encode("Error");
                 }
                 }
             else
             {
                $mysqlinsert="Insert into udrforgetpassword(email,OTP,created_at) values('$email','$OTP',Now())" ;
                if(mysqli_query($conn,$mysqlinsert))
                {
                  echo json_encode("OTP sent successfully");
                } 
                else 
                {
                   echo json_encode("Error");
                }
             
             }
           }
           else 
           {
              echo "Message could not be sent...";
           }
         
           }
           else 
           {
              echo json_encode("email-id not registered");
           }
         
         
           mysqli_close($conn);
      ?>