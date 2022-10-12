<?php
    session_start();
    if(isset($_SESSION['uname'])){
        $uname = $_SESSION['uname'];
        $uid = $_SESSION['uid'];
        $resp = array("uname"=>$uname,"uid"=>$uid);
        echo json_encode($resp);
    }else{
        $resp = array("uname"=>"","uid"=>"");
        echo json_encode($resp);
    }
   
?>