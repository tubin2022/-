<?php
    header("Content-type:text/html;charset=utf-8");
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $sql = "select * from category";
        $result = $conn->query($sql);
        if($result->num_rows >0){
            //有结果
            $temp = array();
            while($arr = $result->fetch_assoc()){
                array_push($temp,$arr);
            }
            $resp = array("data"=>$temp);
            echo json_encode($resp);
        }else{
            // 没有结果
            $resp = array("data"=>null);
            echo json_encode($resp);
        }
    }
?>