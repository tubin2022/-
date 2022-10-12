// pages/search/search.js
const request = require('../../utils/promise.js')
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 搜索信息
        searchInfo:'',
        //  搜索结果
        searchResult:[],
        // 相似商品结果
        otherProduct:[],
        myCart:[]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let that = this;
        this.setData({
            searchInfo:options.searchInfo
        })
        this.getProductBySearchInfo();
        wx.setNavigationBarTitle({
          title: '与'+that.data.searchInfo+'有关',
        })
    },

    // 通过传过来的搜索信息查找商品
    getProductBySearchInfo(){
        let that = this;
        let params = {
            page:1,
            pageSize:6,
            name:this.data.searchInfo
        }
        request.GET('/product/pageQuery',params).then(res =>{
            that.setData({
                searchResult:res.data.data.list
            })
            that.getOtherProduct();
        })
    },

    // 获取相似商品信息
    getOtherProduct(){
        let that = this;
        let params = {
            page:1,
            pageSize:6,
            productCategoryId:this.data.searchResult[0].category.id
        }
        request.GET('/product/pageQuery',params).then(res =>{
            let arr = res.data.data.list;
            let dataArr = arr.filter((item)=>{
                return !that.data.searchResult .some(ele=>ele.id == item.id)
            });
            that.setData({
                otherProduct:dataArr
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