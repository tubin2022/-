// pages/ProductDetail/productDetail.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        activeName: '',
        productData:{}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
       let Product = JSON.parse(options.product);
       this.setData({
           productData:Product
       })
    },

    onChange(event) {
        this.setData({
          activeName: event.detail,
        });
      },
})