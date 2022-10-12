// pages/weathe/weathe.js
const app = getApp();
//引入工具类的promise。js
const request =require('../../utils/promise.js');
Page({
  data: {
    region:[],
    now:'',
    typej:0,
    weatherImg:'https://alifei05.cfp.cn/creative/vcg/nowater800/new/VCG41165419154.jpg?x-oss-process=image/format,webp',
    // msgImg:'https://alifei01.cfp.cn/creative/vcg/material/nowarter800/VCG211239343498.jpg?x-oss-process=image/format,webp',
    Img:[{
      id:0,
      weathername:"晴",
      src:"https://tenfei01.cfp.cn/creative/vcg/nowarter800/new/VCG41N1049742604.jpg?x-oss-process=image/format,webp"
    },
    {
      id:1,
      weathername:"多云",
      src:"https://alifei03.cfp.cn/creative/vcg/nowater800/new/VCG21gic13919766.jpg?x-oss-process=image/format,webp"
    },
    {
      id:2,
      weathername:"中雨",
      src:"https://tenfei01.cfp.cn/creative/vcg/nowater800/new/VCG4177741099.jpg?x-oss-process=image/format,webp"
    },
    {
      id:3,
      weathername:"雷阵雨",
      src:"https://alifei02.cfp.cn/creative/vcg/nowater800/new/VCG211289781705.jpg?x-oss-process=image/format,webp"
    },
    {
      id:4,
      weathername:"雪",
      src:"https://alifei05.cfp.cn/creative/vcg/nowater800/new/VCG21gic13457102.jpg?x-oss-process=image/format,webp"
    },
    {
      id:5,
      weathername:"雾",
      src:"https://alifei02.cfp.cn/creative/vcg/nowater800/new/VCG211371293450.jpg?x-oss-process=image/format,webp"
    },
    {
      id:6,
      weathername:"雨",
      src:"https://tenfei04.cfp.cn/creative/vcg/nowater800/new/VCG21gic13806198.jpg?x-oss-process=image/format,webp"
    },
  ]
  },
  onLoad: function(){
    
  },
  change:function(e){

    this.setData({                 //获得detail.value，即地域信息
      region: e.detail.value,
      typej:1
    })
    this.getWeather();    //更新天气
    },
//通过util里封装的方法调用api获取天气
getWeather(){
  let that =this;
  request.POST(1,'/tianqi',{
    api_key:'be7bcc9cbc579f7e',
    city: that.data.region[1].slice(0,-1),
    type:1
  }).then(res=>{
        that.setData({
          now:res.data.data
        })
          var tp = that.data.Img;  
              tp.forEach(item => {
                if(res.data.data.weather==item.weathername)
                {
                  that.setData({
                  weatherImg:item.src
                  });
                }
              });  
  })
}
})