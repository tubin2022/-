<?php
    header("Content-type:text/html;charset=utf-8");
    session_start();
    $file1 = $_FILES["file"];
    $name = $_FILES["file"]["name"];
    $type = strstr($name, ".");
    $fname = time().rand(1,9999).$type;//1970-1-1
    $uploadRes = move_uploaded_file($_FILES["file"]["tmp_name"],"../uploadImg/".$fname);
    $uid = $_SESSION['uid'];
    $newFace = "http://localhost/project/books/uploadImg/".$fname;
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $sql = "update user set userface = '$newFace' where uid = $uid";
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