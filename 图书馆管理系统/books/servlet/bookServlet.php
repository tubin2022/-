<?php
    header("Content-type:text/html;charset=utf-8");
    //获取页数
     $page=$_GET['page'];
     //连接数据库
     $conn = new mysqli("localhost","root","123456","book1");
     // 检测连接
     if ($conn->connect_error) {
         die("连接失败: " . $conn->connect_error);
     }else{
         //连接成功
         //查询总记录数
         $respResult = array();
         $sql = "select count(*) as total from book";
         $result = $conn->query($sql);
         if ($result->num_rows > 0) {
            // 有数据
            $row = $result->fetch_assoc();
            array_push($respResult,$row);
        } else {
            //没有数据
           $resp = array("total"=>"0");
           array_push($respResult,$row);
        }
        //分页查询
        $sql2 = "select b.*,cname from book b,category c where b.cid =c.cid limit $page,4";
        $result2 = $conn->query($sql2);
        if($result2->num_rows >0){
            $temp = array();
            while($arr = $result2->fetch_assoc()){
                array_push($temp,$arr);
            }
            array_push($respResult,$temp);
        }else{
            $resp = array("data"=>"null");
            array_push($respResult,$row);
        }
        echo json_encode($respResult);
     }
?>