<?php
    header("Content-type:text/html;charset=utf-8");
    $uid = $_POST['uid'];
    $bid = $_POST['bid'];
    $bdate = date('Y-m-d H:m:s');
     //连接数据库
     $conn = new mysqli("localhost","root","123456","book1");
     // 检测连接
     if ($conn->connect_error) {
         die("连接失败: " . $conn->connect_error);
     }else{
         //连接成功
         $sql ="insert into b_r values(null,$uid,$bid,'$bdate',null,1)";
         $success = $conn->query($sql);
         if($success){
             $resp = array("code"=>200);
             echo json_encode($resp);
             //现存减1
             $sql2 = "update book set existing = existing - 1 where bid =$bid";
             $conn->query($sql2);
         }else{
             $resp = array("code"=>500);
             echo json_encode($resp);
         }
     }
?>