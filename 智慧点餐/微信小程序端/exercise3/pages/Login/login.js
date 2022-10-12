// pages/Login/login.js
const request = require('../../utils/promise.js')
const token = require('../../utils/token.js')
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 用户名
        username:'',
        // 密码
        password:'',
        // 错误提示
        errorName:false,
        errorPassword:false,
        // 我的token信息
        myToken:''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    // 登录
    login(){
        let that = this;
        if(this.data.username == '' || this.data.password == ''){
            if(this.data.username == ''){
                this.setData({
                    errorName:true
                })
            }
            if(this.data.password == ''){
                this.setData({
                    errorPassword:true
                })
            }
        }else{
            // 登录
            let params = {
                "password": this.data.password,
                "username": this.data.username
              }
              wx.request({
                url: 'http://47.100.84.201:8888/user/login',
                data:params,
                header:{
                    "Content-Type": "application/json",
                },
                method:'POSt',
                success:(res)=>{
                    if(res.data.status == 200){
                        that.setData({
                            myToken:res.data.data.token
                        })
                        token.setToken(that.data.myToken);
                        wx.request({
                          url: 'http://47.100.84.201:8888/user/info',
                          method:'GET',
                          data:{token:res.data.data.token},
                          header: {
                            "Content-Type": "application/x-www-form-urlencoded",
                         },
                          success:(res)=>{
                              app.globalData.userInfo = res.data.data;
                              wx.navigateBack({
                                delta: 1,
                              })
                          }
                        })
                    }else{
                        wx:wx.showToast({
                          title: res.data.message,
                          duration: 2000,
                          icon: "error",
                        })
                    }
                    
                }
              })
        }
    },

    // 用户名输入框输入时事件
    onChangeName(){
        this.setData({
            errorName:false
        })
    },
     // 密码输入框输入时事件
     onChangePassword(){
        this.setData({
            errorPassword:false
        })
    },

    // 点击顶部添加账号,跳转到注册页面
    toRegisterPage(){
        wx.navigateTo({
          url: '/pages/Register/register',
        })
    }
})