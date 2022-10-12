// pages/Location/location.js
const app = getApp();
const request = require('../../utils/promise.js')
import { areaList } from '@vant/area-data';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 我的个人信息
        user:{},
        // 我的地址信息
        allAddress:[],
        // 地址列表信息
        areaList,
        // 是否展示动作面板
        showActionSheet:false,
        // 动作面板展示内容（0表示展示地址编辑，1展示名字和电话号码的编辑）
        show:0,
        // 选中的地址
        clickAddress:[],
        // 动作面板第二步,底部设置确认按钮
        text:'',
        // 动作面板第二步,顶部标题栏
        title:'',
        // 地址中的名字
        realname:'',
        // 地址中的电话
        telephone:'',
        // 需要编辑地址项的id
        id:'',
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let userMessage = app.globalData.userInfo;
        this.setData({
            user:userMessage
        })
        wx.setNavigationBarTitle({
          title: '我的地址',
        })
    },
    onShow(){
        this.getAllAddress();
    },

    // 获取我的地址信息
    getAllAddress(){
        let that = this;
        let param = {
            userId:this.data.user.id
        }
        request.GET('/app/address/queryByUserId',param).then(res =>{
            that.setData({
                allAddress:res.data.data
            })
        })
    },

    // 点击编辑，编辑我的地址信息
    editAddress(address){
        let oldAddress = address.target.dataset.address;
        this.setData({
            showActionSheet:true,
            realname:oldAddress.realname,
            telephone:oldAddress.telephone,
            id:oldAddress.id
        })
    },

    // 关闭动作面板方法
    onClose() {
        let that = this;
        this.setData({ 
            showActionSheet: false,
            title:'',
            text:''
        });
        setTimeout(function(){
            that.setData({
                show:0
            })
        },1000)

    },

    // 点击动作面板中第一步编辑地址的下一步,
    next(result){
        this.setData({
            show:this.data.show+1,
            clickAddress:result.detail.values,
            text:'确认',
            title:'编辑个人信息'
        })
    },

    // 点击动作面板中第一步编辑地址的取消
    cancel(){
        this.setData({
            showActionSheet:false,
        })
    },

    // 点击动作面板第二步中的确认按钮, 发送网络请求，更新地址
    confirm(){
        let that = this;
        let params = {
            id:this.data.id,
            realname:this.data.realname,
            telephone:this.data.telephone,
            province:this.data.clickAddress[0].name,
            city:this.data.clickAddress[1].name,
            area:this.data.clickAddress[2].name,
            userId:this.data.user.id
        }
        request.POST('/app/address/saveOrUpdate',params).then(res =>{
            if(res.data.status == 200){
                wx.showToast({
                  title: '操作成功',
                });
                that.getAllAddress();
            }else{
                wx.showToast({
                  title: '操作失败',
                  icon:'error'
                })
            }
            that.setData({
                showActionSheet:false,
                show:0,
                text:'',
                title:''
            })
        })
    },

    // 点击滑块右部的删除按钮，删除该地址
    deleteAddress(address){
        let that = this;
        let oldAddress = address.target.dataset.address;
        wx.showModal({
            content: '是否删除该地址栏的信息',
            success (res) {
              if (res.confirm) {
                let param={
                    id:oldAddress.id
                }
                request.GET('/app/address/deleteById',param).then(res =>{
                    if(res.data.status == 200){
                        wx.showToast({
                          title: '删除成功',
                        });
                        that.getAllAddress();
                    }else{
                        wx.showToast({
                          title: '删除失败',
                          icon:'error'
                        })
                    }
                })
              } else if (res.cancel) {
              }
            }
          })
    },

     // 点击新增地址
     addAddress(){
        let that = this;
        this.setData({
            showActionSheet:true,
            realname:'',
            telephone:'',
            id:''
        })
    },
})