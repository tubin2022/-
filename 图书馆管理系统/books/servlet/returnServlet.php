<?php
    header("Content-type:text/html;charset=utf-8");
    $id = $_POST['id'];
    $rdate = date('Y-m-d H:m:s');
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $sql = "update b_r set state=0,rdate='$rdate' where id =$id";
        $success = $conn->query($sql);
        if($success){
            //现存加1
            $sql2 = "update book set existing = existing + 1 where bid =(select bid from b_r where id = $id)";
            $conn->query($sql2);
            $resp = array("code"=>200);
            echo json_encode($resp);
        }else{
            $resp = array("code"=>500);
            echo json_encode($resp);
        }
    }
?>