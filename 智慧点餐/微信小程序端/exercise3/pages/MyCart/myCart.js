// pages/MyCart/myCart.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
      // 选中的购物车数据
        result: [],
        // 页面是否为空
        empty:false,
        // 已经添加到购物车的数据
        myCartInfo:[],
        // 全选按钮
        checked:false,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        wx.setNavigationBarTitle({
          title: '我的购物车',
        })
    },
    onShow(){
      this.getCartData();
    },

    // 复选框改变事件
    onChange(event) {
        this.setData({
          result: event.detail,
        });
    },

    // 获取购物车数据
    getCartData(){
      let that = this;
      wx.getStorage({
        key:'myCart',
        success:(res)=>{
          let myCart;
          if(res.data == ''){
            myCart = new Array();
          }else{
            let data = JSON.parse(res.data);
            myCart = data;
          }
          that.setData({
            myCartInfo:myCart
          })
          if(that.data.myCartInfo.length == 0){
            that.setData({
              empty:true
            })
          }else{
            that.setData({
              empty:false
            })
          }
        },
        fail:(res)=>{
        }
      })
    },

    // 全选事件
    checkAll(){
      this.setData({
        checked:!this.data.checked
      })
      if(this.data.checked){
        for(let i=0; i<this.data.myCartInfo.length;i++){
          this.data.result.push(this.data.myCartInfo[i].id);
        }
      }
    },

    // 步进器改变事件
    stepOnChange(){
    },

   
})