// pages/ProductList/productList.js
const request = require('../../utils/promise.js')
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 左边侧边栏
        activeKey:0,
        // 左边侧边栏信息
        categoryData:[],
        // 右边商品列表信息
        productList:[],
        myCart:[]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            activeKey:options.num-1
        })
        this.getCategory();
    },

    // 获取左边侧边栏的信息
    getCategory(){
        let that = this;
        let params = {
            page:1,
            pageSize:10
        }
        request.GET('/productCategory/pageQuery',params).then(res =>{
            that.setData({
                categoryData:res.data.data.list
            })
            that.getProductList();
        })
    },

    // 左边侧边栏切换事件
    onChange(event) {
       this.setData({
           activeKey:event.detail
       })
       this.getProductList();
      },
    // 获取右边产品信息
    getProductList(){
        let that = this;
        let params = {
            page:1,
            pageSize:7,
            productCategoryId:this.data.categoryData[this.data.activeKey].id
        }
        request.GET('/product/pageQuery',params).then(res =>{
            that.setData({
                productList:res.data.data.list
            })
        })
    },

     // 点击商品卡片跳转到商品详情页面,查看详细信息
     toProductDetalPage(product){
        let Product = product.target.dataset.product;
        wx.navigateTo({
          url: '/pages/ProductDetail/productDetail?product='+JSON.stringify(Product),
        })
    },

     // 点击添加到购物车按钮,添加到我的购物车
     addCart(product){
        let that = this;
         // 选中添加的商品信息
         let productMessage = product.target.dataset.product
         that.data.myCart.push(productMessage)
        let userMessage = JSON.stringify(app.globalData.userInfo);
        if(userMessage == '{}'){
          // 未登录
          wx.showModal({
            content:'您还没登录,请登录后再试',
            confirmText:'去登录',
            success (res) {
             if (res.confirm) {
               wx.navigateTo({
                 url: '/pages/Login/login',
               })
             } else if (res.cancel) {
             }
           }
          })
        }else{
          // 登录了
          wx.showModal({
            content:'是否将'+productMessage.name+'加入我的购物车',
            success (res) {
             if (res.confirm) {
               wx.getStorage({
                 key:'myCart',
                 success:(res)=>{
                   let data;
                   if(res.data == ''){
                      data = new Array();
                   }else{
                     data = JSON.parse(res.data);
                   }
                    data[data.length] = that.data.myCart[0];
                    wx.setStorage({
                        key:'myCart',
                        data:JSON.stringify(data)
                      })
                      that.setData({
                        myCart:[]
                    })
                      wx.showToast({
                        title: '添加成功',
                      })
                 },
                 fail:(res)=>{
  
                 }
               })
             } else if (res.cancel) {
             }
           }
          })
        }
      },
})