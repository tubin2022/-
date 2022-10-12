// Components/navigation-bar/navigation-bar.js
const app = getApp()
Component({
    /**
     * 组件的属性列表
     */
    properties: {
      //标题默认值
        title:{
            type:String,
            value:"在线轮渡购票小程序"
        },
        typei:{
            type: Number,
            value:0
        }
    },
    /**
     * 组件的初始数据
     */
    data: {
        s: app.system.statusBarHeight, //状态栏高度
        n: (app.menu.top - app.system.statusBarHeight) * 2 + app.menu.height, //导航栏高度
        h: app.menu.height ,//胶囊高度  
    },
    /**
     * 组件的方法列表
     */
    methods: {
        onClickLeft(){
        
        wx.navigateBack({
          delta: 1,
        })
        },
        onClickHome(){
        wx.switchTab({
          url: '/pages/index/index',
        })
        },
        onClickPerson(){
          wx.switchTab({
            url:'/pages/about/about'
          })
        }
    }
    
})
