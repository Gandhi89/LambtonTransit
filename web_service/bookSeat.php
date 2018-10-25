
<?php

       require "rb-mysql.php";

       if(!empty($_POST['student_id']) && !empty($_POST['status'])){
         $x   = urldecode($_POST['student_id']);
         $y   = urldecode($_POST['status']);

         R::setup("mysql:host=localhost;dbname=BusService","root", "");

         //1.0 add rows to database[insert into]

         $w = R::dispense("businfo");
         $w->student_id = $x;
         $w->status = $y ;

         R::store($w);

         echo "success";
       }

      else{
        echo "fail";
      }


 ?>
