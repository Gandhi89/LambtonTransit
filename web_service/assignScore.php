
<?php

       require "rb-mysql.php";
       if(!empty($_POST['user_info'])){
           $x   = urldecode($_POST['user_info']);
           R::setup("mysql:host=localhost;dbname=BusService","root", "");

           //1.0 add rows to database[insert into]

           $w = R::dispense("userscore");

           $w->user_info = $x;
           $w->score = "300";

           R::store($w);

           echo "success";

       }
       else {
         print_r($_SERVER);
         echo "fail";
       }
 ?>
