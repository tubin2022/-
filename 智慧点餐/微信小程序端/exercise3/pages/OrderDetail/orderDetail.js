// pages/OrderDetail/orderDetail.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 当前页面要渲染的订单数据
        orderData:{},
        // 商品总数量
        count:0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let data = JSON.parse(options.order);
        this.setData({
            orderData:data
        })
        let sum=0;
        for(let i =0;i<this.data.orderData.orderLines.length;i++){
            sum=sum+this.data.orderData.orderLines[i].num;
        }
        this.setData({
            count:sum
        })
    },

    // 确认订单,返回订单页面
    toOrderPage(){
        wx.switchTab({
          url: '/pages/Order/order',
        })
    }
})