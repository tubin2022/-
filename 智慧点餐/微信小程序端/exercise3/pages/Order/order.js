// pages/Order/order.js
const request = require('../../utils/promise.js')
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        active:0,
        tabsArr:['全部','待支付','待派送','已完成'],
        orderList:[],
        empty:false,
        user:{}
    },

    /**
     * 生命周期函数--监听页面加载
     */
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
            this.getAllOrders();
        }
    },

    // 标签栏绑定的事件
    onClick(event) {
        this.setData({
            active:event.detail.name
        })
        if(event.detail.name == 0){
            // 获取全部的订单
            this.getAllOrders();
        }else{
            // 获取对应状态的订单
            this.getOrdersByStatus();
        }
        

      },

    //   默认加载第一个标签的订单,即全部订单
    getAllOrders(){
        let that = this;
        let params = {
            page:1,
            pageSize:12,
            customerId:app.globalData.userInfo.id
        }
        request.GET('/order/pageQuery',params).then(res =>{
            that.setData({
                orderList:res.data.data.list
            })
        })
    },
    // 加载对应标签状态的订单
    getOrdersByStatus(){
        let that = this;
        let params = {
            page:1,
            pageSize:6,
            status:this.data.tabsArr[this.data.active],
            customerId:app.globalData.userInfo.id
        }
        request.GET('/order/pageQuery',params).then(res =>{
            that.setData({
                orderList:res.data.data.list
            })
        })
    },
    // 登录
    login(){
        wx.navigateTo({
          url: '/pages/Login/login',
        })
    },

    // 点击每个订单详情,跳转到订单详情页面
    toOrderDetailPage(order){
        wx.navigateTo({
          url: '/pages/OrderDetail/orderDetail?order='+JSON.stringify(order.target.dataset.order),
        })
    }
})