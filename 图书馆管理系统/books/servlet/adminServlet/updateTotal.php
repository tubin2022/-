<?php
    header("Content-type:text/html;charset=utf-8");
    $bid = $_POST['bookid'];
    $num = $_POST['total'];
    $sql = '';
    if($num > 0){
        //添加库存
        $sql = "update book set total = total +$num,existing = existing + $num where bid = $bid";
    }else{
        //减少库存
        $sql = "update book set total = total $num,existing = existing  $num where bid = $bid";
    }
     //连接数据库
     $conn = new mysqli("localhost","root","123456","book1");
     // 检测连接
     if ($conn->connect_error) {
         die("连接失败: " . $conn->connect_error);
     }else{
         //连接成功
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