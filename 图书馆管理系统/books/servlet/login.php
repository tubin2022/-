<?php
    session_start();
    //获取登录的用户名和密码
    $username = $_POST['username'];
    $pwd = $_POST['password'];
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
 
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $sql = "select * from user where uname = '$username' and password = '$pwd'";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            //登录成功返回普通用户返回1，管理员返回2
            
            $row = $result->fetch_assoc();
            $_SESSION['uname']=$row["uname"];
            $_SESSION['uid'] =$row["uid"];
            if($row["role"] == 0){
                echo 1;
            }else{
                echo 2;
            }
        } else {
            //登录失败返回-1
            echo -1;
        }
        $conn->close();
    } 
    
?>