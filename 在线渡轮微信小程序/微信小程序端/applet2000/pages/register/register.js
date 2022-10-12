const app =getApp();
//引入工具类的promise。js
const request =require('../../utils/promise.js');
Page({
    data: {
      // disabled: true,
      // btnstate: "default"
      userName:'',
      phone:'',
      password:'',
      email:''
    },
    onShow: function(){
      wx.setNavigationBarTitle({
        title: '用户注册'
      })
    },
    // 注册完成 进行登录
    goRegister(){
      const that = this;
      console.log("查看注册成功调用");
      var user = new Object();
      user.phone = that.data.phone;
      user.password = that.data.password;
      user.email = that.data.email;
      user.uname = that.data.userName;
      console.log(that.data.email);
      request.POST(0,'/user/registe',{
        email:user.email,
        gender:'none',
        password:user.password,
        phone:user.phone,
        uname:user.uname,
        userface:'none'
      }).then(res=>{
        console.log(res);
        if(res.data.code==1000){ 
              wx.showToast({
                title: "注册成功",
                icon:"success",
                duration:2000
              })
              wx.navigateTo({
                url: '/pages/login/login',
              })
        }else{
          // 手机格式问题
          if(res.data.code==4000){
              wx.showToast({
                title: res.data.msg,
                icon:'error',
                duration:2000
              })
          }else{
            // 手机号重复
            if(res.data.code==1003){
              wx.showToast({
                title: "该手机已被注册",
                icon:'error',
                duration:2000
              })
            }else{
              // 邮箱格式问题
              wx.showToast({
                title: res.data.msg,
                icon:'error',
                duration:2000
              })
            }
            }
            
        }
      
    })
  }

  })