// pages/result/result.js
const app =getApp();
//引入工具类的promise.js
const request =require('../../utils/promise.js');
//引入工具类的isLogin.js 判断用户是否登录
import { IS } from '../../utils/isLogin.js';
Page({
    data: {
    // 用户输入的筛选信息
    startCity:'',
    endCity:'',
    startTime:'',  
    // 判断页面是否为空
    failShow:true,
    // 搜索结果放入该数组
    search:[],
    // 分页的必要参数
    pageNo:'1',
    // 上拉加载
    searchLoading: false, //"上拉加载"的变量，默认false，隐藏
    searchLoadingComplete: false, //“没有数据”的变量，默认false，隐藏
    },
    // 进入页面时填入的相关信息
    onShow: function () {
      const that = this;
      // 获取上一页传入的数据
      wx.getStorage({
        key: 'startTime',
        success(res){
          console.log("成功获取出发时间缓存"+res.data);
          that.setData({
            startTime:res.data
          })
        }
      }),
      wx.getStorage({
        key: 'startCity',
        success(res){
          console.log("成功获取出发城市缓存"+res.data);
          that.setData({
            startCity:res.data
          })
        }
      }),
      wx.getStorage({
        key: 'endCity',
        success(res){
          console.log("成功获取到达城市缓存"+res.data);
          that.setData({
            endCity:res.data
          })
          that.getResultInfo();
        }
      }),
      wx.setNavigationBarTitle({
        title: '搜索结果'
      })
      console.log(that.data.endCity);
      
  },
  // 获取船票信息
  getResultInfo(){
    const that = this;
    request.GET(0,'/ticket/pageQueryTicket',{
      arriveCity:that.data.endCity,
      arriveTime:'',
      pageNo:that.data.pageNo,
      pageSize:'8',
      startCity:that.data.startCity,
      startTime:that.data.startTime
    }).then(res=>{
      console.log(res);
      if(res.data.code==1000)
      { 
        // 当前的总数量
        var sumTotal = Number(res.data.data.total)+Number(that.data.total)
        that.setData({
          search:res.data.data.data,
          total:sumTotal,
        })
        if(res.data.data.total!==0){
          that.setData({
            failShow:false
          })
        }
        console.log(that.data.search);
      }else{
        wx.showToast({
          title: '查询成功',
          icon:'none'
        })
      }
    });
  },
  // 填入船票
  addTicket(item){
    var that = this;
    console.log("购买车票");
    // 传入船票的id
    app.globalData.tid = item.currentTarget.dataset.ticketinfo.tid
    var shopping = item.currentTarget.dataset.ticketinfo;
    wx.navigateTo({
      url: '/pages/boat/boat?item='+ JSON.stringify(shopping)+'&isFinish='+0,
      })
    // 判断用户是否登录
    // IS();
  },
  // 下滑加载 search[] + 新的数据
  onReachBottom: function () {
    const that = this;
    // 触发加载中效果
    this.setData({
      searchLoading: true,
      searchLoadingComplete: true,
    });
    // 页数 +1
    that.data.pageNo++;
    // 修改页数
    // that.setData({
    //   pageNo:nowPageNo
    // })
    console.log('查看是否更新为2？'+that.data.pageNo);
    request.GET(0,'/ticket/pageQueryTicket',{
      arriveCity:that.data.endCity,
      arriveTime:'',
      pageNo: that.data.pageNo,
      pageSize:'8',
      startCity:that.data.startCity,
      startTime:that.data.startTime
    }).then(res=>{
      if(res.data.code==1000)
      {
        // 当前的总数量
        var sumTotal = Number(res.data.data.total)+Number(that.data.total)
        // 新加载的数据
        var newInfo = res.data.data.data;
        //把新请求到的数据拼接到一个新数组中
        var newSearch = [...this.data.search, ...newInfo];
        that.setData({
          search:newSearch,
          total:sumTotal,
          searchLoading: false,
          searchLoadingComplete: false,
        })
        // 如果信息不为零则不展示空状态
        if(res.data.data.total!==0){
          that.setData({
            failShow:false
          })
        }else{wx.showToast({
          title: '查询成功',
          icon:'none'
        })}
        console.log(that.data.search);
      }else{
        wx.showToast({
          title: '加载数据失败',
          icon:'none'
        })
      }
    });
  },
})