<?php
     header("Content-type:text/html;charset=utf-8");
     $cname = $_POST["cname"];
     $desc = $_POST["description"];
      //连接数据库
      $conn = new mysqli("localhost","root","123456","book1");
      // 检测连接
      if ($conn->connect_error) {
          die("连接失败: " . $conn->connect_error);
      }else{
          //连接成功
        $sql = "insert into category values(null,'$cname','$desc')";
        $success = $conn->query($sql);
        if($success){
            $resp = array("code"=>200);
            echo json_encode($resp);
        }else{
            $resp = array("code"=>500);
            echo json_encode($resp);
        }
      }
?>