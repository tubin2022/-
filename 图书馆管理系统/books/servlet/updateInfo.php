<?php
     header("Content-type:text/html;charset=utf-8");
     session_start();
     $name = $_POST['name'];
     $phone = $_POST['phone'];
     $uid = $_SESSION['uid'];
      //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
    $sql = '';
    if($phone == ''){
        $sql = "update user set uname = '$name' where uid = $uid";
    }else if($name == ''){
        $sql = "update user set phone = '$phone' where uid = $uid";
    }else{
        $sql = "update user set uname ='$name',phone='$phone' where uid = $uid";
    }
    //检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $success = $conn->query($sql);
        if($success){
            $resp = array("code"=>200);
            echo json_encode($resp);
        }else{
            $resp = array("code"=>200);
            echo json_encode($resp);
        }
    }

?>