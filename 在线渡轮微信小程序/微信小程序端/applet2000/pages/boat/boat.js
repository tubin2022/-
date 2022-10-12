// 获取应用实例
const app = getApp()
//引入工具类的promise。js
const request =require('../../utils/promise.js');
// 引入二维码工具类
import QRCode from '../../utils/weapp-qrcode.js';
Page({
    data: {
        shopping:[],
        boatPhoto:'/static/轮船.png',
        // 控制购买确定栏
        buyShow:false,
        // 购买数量
        num:'',
        // 票务id
        tid:'',
        price:0,
        // 是否显示  1为待出行页面传值 2为已完成 0为查询船票
        showOrder:0,
        // 二维码
        ewmImg:'',
        showEwmIndex:'',
        // 控制popup层
        showCode:false
    },

    onLoad: function(options) {
        var that = this;
        let shopping = JSON.parse(options.item);
        let isFinish = options.isFinish;
        console.log(isFinish+'查看finish的值');
        console.log(shopping);
        that.setData({
            shopping: shopping,
            boatPhoto : shopping.boat.bphoto,
            showOrder:isFinish
        })
    },
    onShow: function(){
        wx.setNavigationBarTitle({
            title: '详细情况'
          })
    },
    // 选择数量
    openNum(){
        var that = this;
        that.setData({
            buyShow:true,
            price:that.data.shopping.price,
          })
    },
    // 购买票务
    buyTicket(){
        var that = this;
        // 购票总价
        var sumPrice = Number(that.data.num)*Number(that.data.shopping.price)
          if(app.globalData.money<sumPrice){
            wx.showToast({
              title: '余额不足，请氪金',
              icon:'error'
            })
        }else{
            //  发送请求进行购票
            request.POST(0,'/consume/buyTicket',{    
            number:that.data.num,
            tid:that.data.shopping.tid,
            uid:app.globalData.uid
            }).then(res=>{
            console.log(res);
            if(res.data.code==1000){ 
              app.globalData.money=Number(app.globalData.money) - Number( sumPrice);
              console.log("结算金额"+app.globalData.money);
                wx.showToast({
                title: '购买成功',
                icon:"success",
                duration:2000
                })
                var newNum = that.data.shopping.surplus - that.data.num
                that.setData({
                    'shopping.surplus': newNum
                })
                console.log("显示新的剩余量"+newNum);
                // 异常处理
            }else{  
                if(res.data.code==3002){
                wx.showToast({
                    title: res.data.msg,
                    icon:'error',
                    duration:2000
                })
                // 异常处理
                }else{
                wx.showToast({
                title: '该班次已经出发',
                icon:'error',
                duration:2000
                });}     
            }
            })
            }
        },
    goFeedback(){
        var that = this;
     // 将反馈的票务信息存入feedbackTid并传参
     var feedbackTid = that.data.shopping.tid;
     var ticketShow = true;
     wx.navigateTo({
      url:'/pages/feedback/feedback?item='+ feedbackTid
      +'&show='+ticketShow,
      })
    },
    // 使用船票并展示二维码
    useTicket(){
        const that = this;
        that.setData({
            showCode:true
        })
        new QRCode('myQrcode',{
            text: '票的id为：'+that.data.shopping.tid+'，乘客的id为：'+that.data.shopping.uid+'，乘坐人为：'+that.data.shopping.user.uname,   
            width: 141,	//canvas 画布的宽
            height: 141,	//canvas 画布的高
            padding: 0, // 生成二维码四周自动留边宽度，不传入默认为0
            correctLevel: QRCode.CorrectLevel.L, // 二维码可辨识度
            callback: (res) => {
                //工具回调数据
                // 接下来就可以直接调用微信小程序的api保存到本地或者将这张二维码直接画在海报上面去，看各自需求
                wx.hideLoading()
                console.log("生成二维码",res)
                //将图片路径放入data中，显示在wxml的image标签上
                that.setData({
                    ewmImg:res.path
                })
            }
        })
    },
    //  关闭弹窗
    closeCode(){
        const that = this;
        that.setData({
            showCode:false
        })
    }
})