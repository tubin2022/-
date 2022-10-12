// pages/feedback/feedback.js
const app =getApp();
//引入工具类的promise。js
const request =require('../../utils/promise.js');
//引入工具类的isLogin.js 判断用户是否登录
import { IS } from '../../utils/isLogin.js';
Page({
    data: {
        message:'',
        feedbackTid:'',
        showT:false,
    },
    onLoad: function(options){
      var that = this;
      // 某次行程的tid
      let feedbackTid = options.item;
      // 控制对于某次行程的反馈
      let showT = options.show;
      that.setData({
        showT :showT,
        feedbackTid: feedbackTid
      })

    },
    onShow: function(){
      wx.setNavigationBarTitle({
        title: '用户反馈'
      })
      IS();
    },
    getInput(event){
        var message = event.detail;
      if (message != "") {
        this.setData({ 
          message: message 
        });
    }
    },
    feedback(){
        const that =this;
        console.log(app.globalData.uid);
        // 用户的反馈信息 
        if(that.data.showT){
          var Fmessage = '来自tid为'+ that.data.feedbackTid + '的票务反馈:' +that.data.message
        }else{
          var Fmessage = that.data.message
        };
        request.POST(0,'/feedback/fed',{
            context:Fmessage,
            uid:app.globalData.uid
        }).then(res=>{
          console.log(res);
          if(res.data.code==1000){ 
                wx.showToast({
                  title: '反馈成功',
                  icon:"success",
                  duration:2000
                })
                that.setData({
                  message:''
                })
          }else{  
                wx.showToast({
                  title: '请联系客服',
                  icon:'error',
                  duration:2000
                })
          }
        })
    },
})