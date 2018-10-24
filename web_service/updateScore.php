
<?php

       require "rb-mysql.php";
       if(!empty($_POST['user_info']) && !empty($_POST['score'])){
         $x   = urldecode($_POST['user_info']);
         $y   = urldecode($_POST['score']);

       R::setup("mysql:host=localhost;dbname=BusService","root", "");


       //1.0 add rows to database[insert into]
       $sql = "SELECT * from userscore WHERE user_info='" . $x . "'";
       // get the rows from the db
       $rows = R::getAll($sql);

       // cover the rows to an array
       $scores = R::convertToBeans("userscore",$rows);



       foreach ($rows as $value) {
           // code...

           // get the id of the item in the array
           $id = $value["id"];

           // get the "OBJECT" from the database with that id
           $score = R::load("userscore", $id);
           // update the OBJECT
           $score->score = $y;

           // save object back todb
           R::store($score);

         }
       echo "SUCCESS";
       }
      else {
        echo "fail";
      }



 ?>
