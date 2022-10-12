// pages/order/order.js
const app = getApp();
//引入工具类的promise.js
const request =require('../../utils/promise.js');
//引入工具类的isLogin.js
const isLogin =require('../../utils/isLogin.js');
//引入工具类的util.js
var util = require('../../utils/util.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
      // 待出行
      ready:[],
      // 已完成
      finish:[],
      // 对退票的显示管理
      backShow:false,   
      //判断用户当前所在列表
      option:"待出行",
    },
    // 初始化
    init(){
        this.setData({
      // 对退票的显示管理
      backShow:false,   
      //判断用户当前所在列表
      option:"待出行",
        })
    },//初始化
    // 退票
     refund(event){
         var that = this
        wx.showModal({
            title: '提示',
            content: '确认退票吗？',
            success: function (res) {
                if (res.confirm){
                    request.POST(0,'/consume/return',{
                    oid:event.currentTarget.dataset.oid
                    }).then(res=>{
                        if(res.data.code==1000){
                            wx.showToast({
                              title: '已退票',
                            })
                            // 刷新
                        that.getOrder();
                        }else{
                            wx.showToast({
                              title: res.data.msg,
                              icon:'none'
                            })
                        }
                    });
                    app.globalData.money =app.globalData.money+event.currentTarget.dataset.price;
                }else{
                    wx.showToast({
                      title: '用户取消退票',
                      icon:"none"
                    })
                }
            }
        })
        
     },
     gototicket(){
        wx.switchTab({
          url: '/pages/index/index',
        })
     },
     //切换页面
    changePage(event){
        const that =this;
        if(event.detail.name==0){
            that.setData({
                option:"待出行",
            })
        }else if(event.detail.name==1){
            that.setData({
                option:"已完成",
            })
        }
       that.getOrder();
    },
    // 获取订单信息
    getOrder(){
        const that =this;
        console.log(app.globalData.uid);
        console.log(that.data.option);
        if(app.globalData.uid!=''){
            console.log("进入if判断 uid为"+app.globalData.uid);
            request.GET(0,'/order/pageQuery',{
                uid:app.globalData.uid,
                pageNo:1,
                pageSize:15,
                status: that.data.option,
            }).then(res=>{
                // console.log(res);
                if(res.data.code==1000){
                    if(that.data.option=="待出行"){
                      console.log(res);
                        that.setData({
                          ready: res.data.data.data
                        })
                    }else if(that.data.option=="已完成"){
                        that.setData({
                          finish: res.data.data.data
                        })
                        // console.log(that.data.noneh.data);
                    }else{
                        console.log(that.data.option);
                    }
                }else{
                    wx.showToast({
                      title: res.data.msg,
                      icon:'none'
                    })
                }
            })
        }
    },
    goBoat(item){
        var that = this;
        // 传入船票的id
        console.log(item);
        app.globalData.tid = item.currentTarget.dataset.orderinfo.tid
        var shopping = item.currentTarget.dataset.orderinfo;
        wx.navigateTo({
          url: '/pages/boat/boat?item='+ JSON.stringify(shopping)+'&isFinish='+1,
          })
          
    },
    goBoatFinish(item){
        var that = this;
        // 传入船票的id
        console.log(item);
        app.globalData.tid = item.currentTarget.dataset.finishinfo.tid
        var shopping = item.currentTarget.dataset.finishinfo;
        wx.navigateTo({
          url: '/pages/boat/boat?item='+ JSON.stringify(shopping)+'&isFinish='+2,
          })
    },
    onReady: function () {
        var that = this; 
            isLogin.IS()
            that.getOrder();
    },
    onLoad: function(){
        
    },
    onShow: function(){
    }
})