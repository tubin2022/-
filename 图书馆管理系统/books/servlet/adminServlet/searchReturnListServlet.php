<?php
    header("Content-type:text/html;charset=utf-8");
    $info = $_GET["searchInfo"];
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $sql = "select u.uname,b.bname,b.author,b.photo,c.cname,br.bdate,br.rdate,br.id from b_r br,user u,book b,category c
        where br.uid = u.uid and br.bid = b.bid and b.cid = c.cid and br.state = 0";
        $result = $conn->query($sql);
        if($result->num_rows >0){
            //有结果
            $temp = array();
            while($arr = $result->fetch_assoc()){
                $uname = $arr["uname"];
                $bname = $arr["bname"];
                $bdate = $arr["bdate"];
                $rdate = $arr["rdate"];
                if(strpos($uname,$info) !==false || strpos($bname,$info) !==false || strpos($bdate,$info)!== false || strpos($rdate,$info)!==false){
                    array_push($temp,$arr);
                }  
            }
            if(sizeof($temp) == 0){
                $resp = array("data"=>null);
                echo json_encode($resp);
            }else{
                $resp = array("data"=>$temp);
                echo json_encode($resp);
            }
        }else{
            // 没有结果
            $resp = array("data"=>null);
            echo json_encode($resp);
        }
    }
?>