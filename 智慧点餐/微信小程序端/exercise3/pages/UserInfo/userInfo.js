// pages/UserInfo/userInfo.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo:{}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },
    onShow(){
        this.getUserInfo();
    },
    // 获取用户信息
    getUserInfo(){
        let data = app.globalData.userInfo;
        this.setData({
            userInfo:data
        })
    },
    // 点击我的地址，跳转到我的地址页面
    toLocationPage(){
        wx.navigateTo({
          url: '/pages/Location/location',
        })
    }
})