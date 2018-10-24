
<?php

       require "rb-mysql.php";
       
         if(!empty($_POST['student_id']) && !empty($_POST['password']) && !empty($_POST['security_number']))
         {
           print_r($_POST);

           $x   = urldecode($_POST['student_id']);
           $y   = urldecode($_POST['password']);
           $z  =  urldecode($_POST['security_number']);

           // if data is correct, THEN connect to db and do stuff


           R::setup("mysql:host=localhost;dbname=BusService","root", "");

           //1.0 add rows to database[insert into]

           $w = R::dispense("userinfo");

           $w->student_id = $x;
           $w->password = $y;
           $w->security_number = $z;
           R::store($w);

           echo "SUCCESS";
         }
         else{
           print_r($_POST);
           echo "Fail";
         }

 ?>
