<?php

  // 1. connect to databse.
  $dbhost = "localhost";
  $dbuser = "root";
  $dbpassword = "";
  $dbname = "BusService";

  $conn = mysqli_connect($dbhost,$dbuser,$dbpassword,$dbname);

  $query = "SELECT * from bustable_towards";

  //2. grab all the locations

  $results = mysqli_query($conn, $query);
  $stores = array();
  while($item = mysqli_fetch_assoc($results)){
    // add all items to array
    array_push($stores, $item);
  }

  // 3. return all the locations as JSON

  // tell the browser we're sending JSON
  header("Content-Type: application/json");

  //convert our array to json dictionary
  $json = json_encode($stores);

  //deal with any errors during the conversion
  if($json === false){
    $errorMessage = array("error"=>json_last_error_msg());
    $json = json_encode($json);
    http_response_code(500);
    }

    // send json dictionary to the browser
    echo $json;

?>
