
<?php

       require "rb-mysql.php";

       if(!empty($_POST['student_id'])){
         $x   = urldecode($_POST['student_id']);
         R::setup("mysql:host=localhost;dbname=BusService","root", "");

         $sql = "SELECT * from businfo WHERE student_id='" . $x . "'";
         $rows = R::getALl($sql);

         $items = R::convertToBeans("businfo", $rows);

         print_r($items);
         R::trashAll($items);
         R::close();

        echo "success";
       }
       else {
         echo "fail"
       }
  
 ?>
