<?php
    $username = $_POST['username'];
    $password = $_POST['password'];
    $tel = $_POST['tel'];
    $role = $_POST['role'];
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
 
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        $sql = "insert into user values(null,'$username','$password',null,'$tel',$role)";
        $result = $conn->query($sql);
        echo $result;
    }
        // $result = $conn->query($sql);
?>