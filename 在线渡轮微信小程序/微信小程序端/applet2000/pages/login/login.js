const app =getApp();
//引入工具类的promise。js
const request =require('../../utils/promise.js');
//引入工具类的isLogin.js
const isLogin =require('../../utils/isLogin.js');
Page({
    data: {
      phone:'',
      password:'',
    },
    onShow: function(){
    //   wx.setNavigationBarTitle({
    //     title: '用户登录'
    //   })
    },
    // 注册完成 进行登录
    goLogin(){
      const that = this;
      let userPassword = that.data.password;
      let userPhone = that.data.phone;
      request.POST(0,'/login',{    
        password:userPassword,
        username:userPhone,
      }).then(res=>{
        // console.log(res);
        if(res.data.code==1000){ 
              wx.showToast({
                title: "登录成功",
                icon:"success",
                duration:2000
              })
            //   console.log("获取的token值为"+res.data.data.token);
              app.globalData.token = res.data.data.token
              app.globalData.uid = res.data.data.uid
              isLogin.IS();
            // 返回首页
              wx.switchTab({
                url: '/pages/index/index',
              })
        }else{  
              wx.showToast({
                title: res.data.msg,
                icon:'error',
                duration:2000
              })
        }
    })
  }
  })