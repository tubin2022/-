<template>
  <div class="login">
    <!-- 登录页面的头部区域 -->
    <div class="header">
      <!-- App的名称或主题 -->
      <div class="title">干饭了</div>
    </div>
    <!-- 登录表单的区域 -->
    <div class="loginArea">
      <!-- 用户名 密码 按钮的表单结构 -->
      <van-form @submit="onSubmit">
        <van-field
          v-model="username"
          name="用户名"
          label="用户名"
          placeholder="用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
        />
        <van-field
          v-model="password"
          type="password"
          name="密码"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
        />
        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit"
            >提交</van-button
          >
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import { setToken } from '@/http/tokenUtils';
import { Toast } from 'vant';
import axios from "axios";
export default {
  data() {
    return {
      //用户名
      username: "",
      //密码
      password: "",
    };
  },
  methods: {
    // 登录事件
    onSubmit() {
        axios({
            method:'post',
            url:'http://47.100.84.201:8888/user/login',
            data:{
                username:this.username,
                password:this.password
            }
        }).then(res =>{
            if(res.data.status == 200){
                // 登录成功
                // 保存token
                setToken(res.data.data.token);
                // 返回上一页
                this.$router.go(-1);
            }
            if(res.data.status == 500){
                // 登录失败
                Toast.fail(res.data.message)
            }
        })  
    },
  },
};
</script>

<style scoped>
/* 调整页面样式 */
/* 设置页面的整体样式 */
.login{
  width: 100%;
  /* vh视窗高度 */
  height: 100vh;
  /* 设置渐变色 */
  background-image: linear-gradient(#1fa2ff,#12d8fa,#a6ffcb);
  overflow: hidden;
}
/* 设置头部区域的样式 */
.header{
  width: 100%;
  text-align: center;
  /* 定位布局 */
  /* 声明为绝对定位布局 */
  position: absolute;
  /* 距离顶部120px */
  top: 120px;
}
/* 设置头部区域里的标题样式 */
.header .title{
  color: white;
  font-size: 40px;
}
/* 登录区域的样式 */
.loginArea{
  width: 90%;
  /* 通过外边距的形式 */
  margin: 260px auto;
  /* 卡片效果 */
  box-shadow: 0 4px 8px 0 rgba(255, 255, 255, 0.2);
  background-color: white;
  height: 200px;
  /* 设置圆角 */
  border-radius: 15px;
  /* 设置内边距 */
  padding: 10px;
}
</style>