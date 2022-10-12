// pages/about/about.js
const app =getApp();
//引入工具类的isLogin.js 判断用户是否登录
import { IS } from '../../utils/isLogin.js';
//引入工具类的promise。js
const request =require('../../utils/promise.js');
Page({
    data: {
        username:'点击头像进行登录',
        userface:'/static/photo.png',
        // 钱包的余额
        money:0,
        // 控制充值弹窗
        show: false,
        // 充值的金额
        addMoney:'',
    },
    
    init(){
        let username=app.globalData.uname;
        let userface=app.globalData.userface;
        let money=app.globalData.money;
        this.setData({
        username:username,
        userface:userface,
        // 钱包的余额
        money:money,
        // 控制充值弹窗
        show: false,
        // 充值的金额
        addMoney:'',
        });
    },
    // 关闭弹窗且不做改动
    onClose() {
      this.setData({ show: false });
    },
    // 充值钱包
    saveMoney(){
      var that = this
      // 余额加上充值金额
      if (that.data.addMoney == '' || that.data.addMoney == undefined) {
        wx.showToast({
          title: '充值金额为空！',
          icon:'none'
        });
      }else if (!(/(^[0-9]*$)/.test(that.data.addMoney))) {
          wx.showToast({
            title: '充值金额只能是整数！',
            icon:'none'
          })
      }else{
        var saveMoney = Number(that.data.money) + Number(that.data.addMoney)
      app.globalData.money = saveMoney;
      this.setData({
        money:saveMoney,
        show:false
      })
      console.log("充值之后的钱包金额"+app.globalData.money);
      }
    },
    // 点击充值显示充值弹窗
    Recharge(){
        var that = this
        that.setData({
          show:true
        })
    },
    gotoUser(){
      // 判断是否登录  如果没有则跳转到登录页面
        IS();
    },
    
    onLoad:function(options){
        this.init();
    },
    onShow: function (options) {
        this.init();
    },
    // 退出登录
    outLogin(){
    var that = this;
      app.globalData.token = '';
      app.globalData.uname ='点击头像进行登录';
      app.globalData.userface ='/static/photo.png';
      app.globalData.money =80;
      //清除缓存
      wx.clearStorage(); 
      this.setData({
        username:'点击头像进行登录',
        userface:'/static/photo.png',
        money:0
    })
    }
})
