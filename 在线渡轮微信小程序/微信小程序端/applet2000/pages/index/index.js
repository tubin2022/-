// index.js
// 获取应用实例
const app = getApp()
//引入工具类的promise。js
const request =require('../../utils/promise.js');
Page({
  data: {
    // 出发城市
    startName:'',
    // 到达城市
    endName:'',
    // 日期选择
    date: '',
    travelMsg:[
      {
        id: 1,
        city: '南昌',
        travelImg:'https://alifei03.cfp.cn/creative/vcg/nowater800/new/VCG21bdd4ac932.jpg?x-oss-process=image/format,webp'
      },
      {
        id: 2,
        city: '武汉',
        travelImg:'https://tenfei01.cfp.cn/creative/vcg/nowarter800/new/VCG211233534739.jpg?x-oss-process=image/format,webp'
      },
      {
        id: 3,
        city: '长沙',
        travelImg:'/static/警方提醒.png'
      }
    ],//轮播图图片
    region:[],//地区选择
    weather:"",//当地天气
    swiper:true,
  },
  //导航栏地区选择显示天气
  bindPickerChange: function(e) {
    let that =this;  
    // console.log(e.detail.value);
    that.setData({   
      region: e.detail.value,
      typej:1
    })
        request.POST(1,'/tianqi',{
          api_key:'be7bcc9cbc579f7e',
          city:that.data.region[1].slice(0,-1),
          type:1
        }).then(res=>{
          that.setData({
            weather: res.data.data.weather
          })
        })
    },
    //交换出发与到达城市
    exchange(){
      const that =this;
      let x =that.data.endName;
      that.setData({
        endName:that.data.startName,
        startName:x
      })
      wx.setStorage({
        key:"startCity",
        data: x
      })
      wx.setStorage({
        key:"endCity",
        data: that.data.startName
      })
      wx.getStorage({
        key: 'startCity',
        success(res){
          console.log(res);
          console.log("成功将出发城市信息存入缓存"+res.data);
        }
      })
    },
     // 日期选择器
     bindDateChange: function(e) {
      this.setData({
        date: e.detail.value
      })
      wx.setStorage({
        key:"startTime",
        data: e.detail.value
      })
      wx.getStorage({
        key: 'startTime',
        success(res){
          console.log("成功将出发时间存入缓存"+res.data);
        }
      })
    },
   // 进行跳转传参操作
    goSearch(){
      const that = this;
      console.log("点击查看搜索结果成功调用");
      wx.setStorage({
        key:"startCity",
        data: that.data.startName
      })
      console.log(that.data.startName+'gosearch中设立缓存成功');
      wx.setStorage({
        key:"endCity",
        data: that.data.endName
      })
      wx.navigateTo({
        url: '/pages/result/result',
      })
    },
    onShow: function(){
      wx.showShareMenu({
 
        withShareTicket:true,
         
        menus:['shareAppMessage','shareTimeline']
         
        })
    }
})
