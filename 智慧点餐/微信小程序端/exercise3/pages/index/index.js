// index.js
// 获取应用实例
const app = getApp()
const request = require('../../utils/promise.js')
const token = require('../../utils/token.js')
Page({
  data: {
    // 位置信息
    location:'获取位置失败',
    // 搜索信息
    searchInfo:'',
    // 我的token
    myToken:'',
    // 我的信息
    user:{},
    // 轮播图数据
    swipperData:[],
    // 产品分类信息
    categoryData:[],
    // 产品信息
    productData:[],
    // 购物车产品数量
    cartCount:0,
    // 我的购物车信息
    myCart:[]
  },
  // 生命周期函数
  onShow(){
    token.getToken().then(res =>{
     this.setData({
       myToken:res.data
     })
     if(this.data.myToken !=''){
       this.getLocation(this.data.myToken);
     }
    })
    this.getSwipperData();
    this.getCategoryData();
    this.getProductData();
    this.getMyCartCount();
  },
  // 搜索事件
  search(){
   if(this.data.searchInfo !=''){
    wx.navigateTo({
      url: '/pages/search/search?searchInfo='+this.data.searchInfo,
    })
   }else{
     return;
   }
  },
  //顶部获取位置信息
  getLocation(token){
    let that = this;
    let params = {
      token:token
    }
    request.GET('/user/info',params).then(res =>{
      that.setData({
        location:res.data.data.address,
        user:res.data.data
      })
      app.globalData.userInfo = that.data.user;
    })
  },
  // 轮播图数据
  getSwipperData(){
    let that = this;
    request.GET('/carousel/findAll').then(res =>{
      that.setData({
        swipperData:res.data.data
      })
    })
  },
  // 产品分类数据
  getCategoryData(){
    let that = this;
    let params = {
      page:1,
      pageSize:3
    }
    request.GET('/productCategory/pageQuery',params).then(res =>{
      that.setData({
        categoryData:res.data.data.list
      })
    })
  },
  //产品信息
  getProductData(){
    let that = this;
    let params = {
      page:1,
      pageSize:10,
      status:'正常'
    }
    request.GET('/product/pageQuery',params).then(res =>{
      let productList = that.group(res.data.data.list,2)
      that.setData({
        productData:productList
      })
    })
  },
  // 产品工具类、
   group(array, subGroupLength) {
    let index = 0;
    let newArray = [];
    while(index < array.length) {
      newArray.push(array.slice(index, index += subGroupLength));
    }
    return newArray;
    },

    // 点击定位图标，跳转到我的地址页面
    toLocation(){
      let userMessage = JSON.stringify(this.data.user);
      if(userMessage == '{}'){
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
        wx.navigateTo({
          url: '/pages/Location/location',
        })
      }
    },

    // 点击菜单分类图标，跳转到产品列表页面
    toProductList(category){
      wx.navigateTo({
        url: '/pages/ProductList/productList?num='+category.target.dataset.category.num,
      })
    },

    // 点击更多图标，跳转到产品列表页面
    moreAction(category){
      wx.navigateTo({
        url: '/pages/ProductList/productList?num='+category.target.dataset.category,
      })
    },

    //点击产品下面的购物车图标，添加到购物车
    addCart(product){
      let that = this;
       // 选中添加的商品信息
       let productMessage = product.target.dataset.product
       that.data.myCart.push(productMessage)
      let userMessage = JSON.stringify(this.data.user);
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
                  that.data.myCart.concat(data);
               },
               fail:(res)=>{

               }
             })
             wx.setStorage({
               key:'myCart',
               data:JSON.stringify(that.data.myCart)
             })
             that.setData({
               cartCount:that.data.cartCount+1
             })
           } else if (res.cancel) {
           }
         }
        })
      }
    },

    // 获取我的购物车数量
    getMyCartCount(){
      let that =this;
      wx.getStorage({
        key:'myCart',
        success:(res)=>{
          let data;
          if(res.data == ''){
            data = new Array();
          }else{
            data = JSON.parse(res.data);
          }
          that.setData({
            cartCount:data.length
          })
        },
        fail:(res)=>{

        }
      })
    },
    
    // 点击大的购物车图标，跳转到我的购物车页面
    toMyCartPage(){
      let userMessage = JSON.stringify(this.data.user);
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
        wx.navigateTo({
          url: '/pages/MyCart/myCart',
        })
      }
    },

    // 点击每个是商品卡片，跳转到商品详情页面，查看商品详细信息
    toProductDetalPage(data){
      wx.navigateTo({
        url: '/pages/ProductDetail/productDetail?product='+JSON.stringify(data.target.dataset.product),
      })
    },

    // 点击首页新人注册广告,跳转到注册页面
    toRegisterPage(){
      wx.navigateTo({
        url: '/pages/Register/register',
      })
    }
})
