// app.js
App({
  globalData: {
    uid:'',
    uname:'点击头像进行登录',
    userface:'/static/photo.png',
    token:'',
    // 钱包
    money:0,
    // 用户选择的船票
    tid:'',
    // 用户选择船票对应的id
    bphoto:'',
    // 线路 开始与结束
    // detailStart:'',
    // detailEnd:'',
    // 船票价格
    // tprice:'',
    // 剩余票数
    // surplus:'',
    // 总票数
    // total:""
    },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    //获取系统信息
    wx.getSystemInfo({
      success: res => {
        this.system = res
      }
    })
    //获取胶囊信息
    this.menu = wx.getMenuButtonBoundingClientRect()
    //打印数据
  },

})
