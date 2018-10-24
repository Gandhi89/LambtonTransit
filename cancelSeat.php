
<?php

       require "rb-mysql.php";
        $x   = urldecode($_POST['student_id']);





      // if data is correct, THEN connect to db and do stuff

    //  require_once "rb-mysql.php";



      // if data is correct, THEN connect to db and do stuff


      R::setup("mysql:host=localhost;dbname=BusService","root", "");

      $sql = "SELECT * from businfo WHERE student_id='" . $x . "'";
      $rows = R::getALl($sql);

      $items = R::convertToBeans("businfo", $rows);

      print_r($items);
      R::trashAll($items);
      //echo "finished deleting";

      /*
      $book = R::load( "businfo", $id ); //reloads our book
      $d = R::find("businfo", "student_id = ' '.$x");
      //$d = R::load('businfo',"student_id = 'c0123'");
      //delete record
      foreach ($d as $value) {
        // code...
        //echo $value->name = "newName"."<br>";
        R::trash( $value );
      }
      */
      //close connection
      R::close();


    //  R::store($w);

 echo "SUCCESS";


 ?>
