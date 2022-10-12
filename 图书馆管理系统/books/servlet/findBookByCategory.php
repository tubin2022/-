<?php
     header("Content-type:text/html;charset=utf-8");
     $cname = $_GET['cname'];
     //连接数据库
     $conn = new mysqli("localhost","root","123456","book1");
     // 检测连接
     if ($conn->connect_error) {
         die("连接失败: " . $conn->connect_error);
     }else{
         //连接成功
         $sql = "select * from book where cid = (select cid from category where cname = '$cname')";
         $result = $conn->query($sql);
         if($result->num_rows > 0){
            $temp = array();
            while($arr = $result->fetch_assoc()){
                array_push($temp,$arr);
            }
            $resp = array("data"=>$temp);
            echo json_encode($resp);
        }else{
            $resp = array("data"=>null);
            echo json_encode($resp);
        }
     }
?>