// pages/Me/me.js
const app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        user:{},
        username:'去登录',
        show: false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onShow: function (options) {
        let userInfo = app.globalData.userInfo;
        this.setData({
            user:userInfo,
            username:userInfo.realname
        })
    },

    // 预览头像
    privewImg(){
        this.setData({ show: true });
    },
    onClickHide() {
        this.setData({ show: false });
    },

    // 用户信息详情
    toUserInfoDetail(){
        let userMessage = JSON.stringify(this.data.user);
        if(userMessage == '{}'){
            // 未登录
            wx.showModal({
                content:'您还没登录,请登录后再试',
                confirmText:'去登录',
                success (res) {
                 if (res.confirm) {
                   wx.navigateTo({
                     url: '/pages/Login/login',
                   })
                 } else if (res.cancel) {
                 }
               }
              })
        }else{
            // 登录了
            wx.navigateTo({
              url: '/pages/UserInfo/userInfo',
            })
        }
    },

    // 点击我的订单,跳转到订单页面
    toOrderPage(){
       wx.switchTab({
         url: '/pages/Order/order',
       })
    },

    // 点击退出登录
    logout(){
        let that =this;
        let userMessage = JSON.stringify(app.globalData.userInfo);
        if(userMessage == '{}'){
            return
        }else{
            wx.setStorage({
                key:'token',
                data:'',
                success:(res)=>{
                    wx.setStorage({
                        key:'myCart',
                        data:'',
                        success:(res)=>{
                            app.globalData.userInfo = {};
                            that.setData({
                                username:'去登录'
                            })
                            wx.navigateTo({
                              url: '/pages/Login/login',
                            })
                        },
                        fail:(res)=>{
                            wx.showToast({
                              title: '退出失败，请重新再试',
                            })
                        }
                    })
                },
                fail:(res)=>{
                    wx.showToast({
                      title: '退出失败，请重新再试',
                    })
                }
            })
        }
       
    }
})