
<?php
/*
       require "rb-mysql.php";
      $x   =  'c0730001@mylambto.ca';
       $y   =  300;
*/

        $dbhost = "localhost";
        $dbuser = "root";
        $dbpass = "";
        $dbname = "LambtonTransit";

        $connection = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);

        if (mysqli_connect_errno())
          {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
            exit();
          }

          $sql   = "INSERT INTO ScoreTable (score, student_email)";
          $sql  .= " VALUES (300,'c0730001@mylambto.ca')";

          echo $sql."<br>";
        //  echo "";

          $result = $connection->query($sql);
          if ($results) {
			echo "OKAY! <br>";
		//	header("Location: show-products.php");
		}
		else {
			echo "BAD! <br>";
			echo mysqli_error($connection);
		}
      // if data is correct, THEN connect to db and do stuff

    //  require_once "rb-mysql.php";



      // if data is correct, THEN connect to db and do stuff

/*
      R::setup("mysql:host=localhost;dbname=LambtonTransit","root", "");

      //1.0 add rows to database[insert into]

      $w = R::dispense("ScoreTable");

      $w->score = $y ;
      $w->student_email = $x;

      R::store($w);
*/
 //echo "SUCCESS";


 ?>
