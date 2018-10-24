
<?php

       require "rb-mysql.php";
      $x   = urldecode($_POST['user_info']);
       //$y   = urldecode($_POST['score']);
       //$z  = urldecode($_POST['security_number']);





      // if data is correct, THEN connect to db and do stuff

    //  require_once "rb-mysql.php";



      // if data is correct, THEN connect to db and do stuff


      R::setup("mysql:host=localhost;dbname=BusService","root", "");

      //1.0 add rows to database[insert into]

      $w = R::dispense("userscore");

      $w->user_info = $x;
      $w->score = "300";

      R::store($w);

 echo "SUCCESS";


 ?>
