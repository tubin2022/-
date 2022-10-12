// pages/Message/message.js
const app = getApp();
const request = require('../../utils/promise.js')
const token = require('../../utils/token.js')
Page({

    // 页面的初始数据
    data: {
        option1: [
            { text: '全部消息', value: 0 },
            { text: '未读消息', value: 1 },
            { text: '已读消息', value: 2 },
          ],
          value1: 0,
        //   订单信息
        orderMessage:[],
        // 页面是否为空
        empty:false,
        // 我的token
        myToken:'',
        // 我的信息
        user:{}
    },

    // 生命周期函数--监听页面加载
    onShow: function (options) {
        let userInfo = app.globalData.userInfo;
        this.setData({
            user:userInfo
        })
        let userMessage = JSON.stringify(this.data.user);
        if(userMessage == '{}'){
             // 展示空页面
             this.setData({
                empty:true
            })
        }else{
            this.setData({
                empty:false
            })
            this.getAllOrder();
        }
    },

    // 获取所有订单
    getAllOrder(){
        let user = app.globalData.userInfo;
        let that = this;
        let params ={
            page:1,
            pageSize:6,
            customerId:user.id
        }
        request.GET('/app/order/pageQuery',params).then(res =>{
            // console.log(res);
            that.setData({
                orderMessage:res.data.data.list
            })
        })
    },
    // 登录按钮,跳转到登录页面
    login(){
        wx.navigateTo({
          url: '/pages/Login/login',
        })
    },

    // 点击消息详情按钮，查看订单详情信息
    toOrderDetailPage(order){
        wx.navigateTo({
            url: '/pages/OrderDetail/orderDetail?order='+JSON.stringify(order.target.dataset.order),
          })
    }
})