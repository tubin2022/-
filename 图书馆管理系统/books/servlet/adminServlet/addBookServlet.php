<?php
    header("Content-type:text/html;charset=utf-8");
    $bname = $_POST['bname'];
    $author = $_POST['author'];
    $pdate = $_POST['pdate'];
    $category =$_POST['category'];
    $total = $_POST['total'];
    $existing = $total;
    //上传书籍图片
    $file1 = $_FILES["bookPhoto"];
    $name = $_FILES["bookPhoto"]["name"];
    $type = strstr($name, ".");
    $fname = time().rand(1,9999).$type;//1970-1-1
    $uploadRes = move_uploaded_file($_FILES["bookPhoto"]["tmp_name"],"../../uploadImg/".$fname);
    $bookPhoto = "http://localhost/project/books/uploadImg/".$fname;
    //连接数据库
    $conn = new mysqli("localhost","root","123456","book1");
    // 检测连接
    if ($conn->connect_error) {
        die("连接失败: " . $conn->connect_error);
    }else{
        //连接成功
        //根据类别名称查找类别id
        $sql = "select cid from category where cname ='$category'";
        $result = $conn->query($sql);
        $cid = 0;
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $cid = $row['cid'];
            }
            //保存书籍数据
            $sql2 = "insert into book values(null,'$bname','$author','$bookPhoto','$pdate','$total','$existing',$cid)";
            $success = $conn->query($sql2);
            if($success){
                echo '<script>alert("添加成功");location.href="../../view/manage.html"</script>';
            }else{
                echo '<script>alert("添加失败");location.href="../../view/manage.html"</script>';
            }
        } else {
            echo '<script>alert("添加失败");location.href="../../view/manage.html"</script>';
        }
    }
?>