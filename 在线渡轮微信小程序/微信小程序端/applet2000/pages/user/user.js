// pages/user/user.js
const app =getApp();
//引入工具类的promise。js
import { GET, POST } from '../../utils/promise.js';
Page({
    data: {
        user:{
            // 用户id
            uid:'',
            // 用户名
            uname:'',
            // 用户电话
            phone:'',
            // 性别
            gender:'',
            // 邮箱
            email:'',
            // 头像
            userface:app.globalData.userface,
        },
        array: ['保密', '男', '女', ],//性别选择器
        index: 0,//性别选择器坐标
        type:0,//性别选择器内容显示隐藏切换
        type1:false,//姓名输入框显示隐藏控制
        type2:false,//性别单选框显示隐藏控制
        type3:false,//邮箱输入框显示隐藏控制
        type4:false,//手机输入框显示隐藏控制
        input1:'' ,//输入框内容
        input2:'' ,//输入框内容
        input3:'' ,//输入框内容
        input4:'' //输入框内容
        // isShowConfirm:false,//控制弹窗显隐
        // toastbg:'???',//输入框显示内容
        // toasttext:''//输入框获取内容
    },
    onShow:function(){
        this.getuserData()
    },
    // 获取用户数据
    getuserData(){
        const that=this;
        GET(0,'/user/info',{
            token:app.globalData.token
        }).then(res=>{
            if(res.data.code==1000){
                console.log("成功发送获取用户的信息");
                console.log(res.data.data);
                that.setData({
                    user:res.data.data
                });
                app.globalData.uid=res.data.data.uid;
                app.globalData.uname=res.data.data.uname;
                app.globalData.userface=res.data.data.userface;
                that.setData({
                    uname:app.globalData.uname,
                    userface:app.globalData.userface,
                    phone:res.data.data.phone,
                    email:res.data.data.email,
                    gender:res.data.data.gender
                })
            }else{
                wx.showToast({
                  title: '网络错误',
                  icon:'none'
                })
                that.setData({
                    // 用户id
               'user.uid':'',
                    // 用户名
                 'user.uname':'',
                    // 用户电话
                 'user.phone':'',
                    // 性别
                  'user.gender':'',
                    // 邮箱
                'user.email':'',
                    // 头像
                'user.userface':app.globalData.userface,
                type1:false,//姓名输入框显示隐藏控制
                type2:false,//性别单选框显示隐藏控制
                type3:false,//邮箱输入框显示隐藏控制
                type4:false,//手机输入框显示隐藏控制
                input1:'' ,//输入框内容
                input2:'' ,//输入框内容
                input3:'' ,//输入框内容
                input4:'' //输入框内容
                })
            }
            
        })
    },
    // 修改用户信息 typej =1 是说明用户修改过用户信息，等于0则不发送修改用户信息请求
    modifyuname(){
        const that =this;
        if(that.data.type1==false){//判断输入框状态
            that.setData({
                // toastbg:'姓名',
                // isShowConfirm:true,
                input1:'',//清空输入框
                type1:true//若输入框隐藏，点击箭头则显示
                
            })
        }else{
            if(that.data.input1==''||that.data.input1==null||that.data.input1==undefined){//若输入框为空
                that.setData({
                    type1:false//若输入框显示，点击输入框则隐藏
                })
                wx.showToast({
                  title: '输入值为空，不予修改',
                  icon:'none'
                })
            }else{
                that.setData({
                    type1:false,//若输入框显示，点击输入框则隐藏
                    'user.uname':that.data.input1//赋值
                });
                that.modify();//修改
            }
        }
        
    },//修改用户名
    bindPickerChange: function(e) {
        
        let input2 ='';
        if(e.detail.value==0){
            input2 ='保密'
        }else if(e.detail.value==1){
            input2 ='男'
        }else if(e.detail.value==2){
            input2 ='女'
        }else{
            input2 ='我也不知道选的啥'
        }
        this.setData({
          index:e.detail.value,
          input2: input2,
          type:1
        })
      },//性别选择器
    modifygender(){
        const that =this;
        if(that.data.type2==false){//判断输入框状态
            that.setData({
                // toastbg:'性别',
                // isShowConfirm:true,
                type2:true,//若输入框隐藏，点击箭头则显示
                input2:'',//清空
                type:0
            })
        }else{
            if(that.data.input2 !='男'&&that.data.input2 !='女'&&that.data.input2 !='保密'){//若输入框值不为固定值
                that.setData({
                type2:false//若输入框显示，点击输入框则隐藏
                })
                wx.showToast({
                  title: '输入格式错误，不予修改',
                  icon:'none'
                })
            }else{
                that.setData({
                    type2:false,//若输入框显示，点击输入框则隐藏
                    'user.gender':that.data.input2//赋值
                });
                that.modify();//修改
            }
        }
    },//修改性别
    modifyemail(){
        const that =this;
        if(that.data.type3==false){//判断输入框状态
            that.setData({
                // toastbg:'邮箱',
                // isShowConfirm:true,
                input3:'', //清空输入框
                type3:true//若输入框隐藏，点击箭头则显示
                
            })
        }else{
            if(that.data.input3==''||that.data.input3==null||that.data.input3==undefined){//若输入框为空
                that.setData({
                type3:false//若输入框显示，点击输入框则隐藏
                })
                wx.showToast({
                  title: '输入值为空，不予修改',
                  icon:'none'
                })
            }else{
                that.setData({
                    type3:false,//若输入框显示，点击输入框则隐藏
                    'user.email':that.data.input3//赋值
                });
                that.modify();//修改
            }
        }
    },//修改邮箱
    modifyuserface(){
        console.log('修改头像');
        const that =this;
        wx.chooseMedia({
            count:1,//选择文件个数
            mediaType:['image'],//文件是图片类型
            sourceType:['album','camera'],//来源于相册和相机
            success:function(res) {
              let tempFile = res.tempFiles;
                let temppath =tempFile[0].tempFilePath;
                console.log(temppath);
                       wx.uploadFile({
                        url:'http://127.0.0.1:8888/file/upload',
                            filePath: temppath,
                            name: 'file',
                            header: {
                            "Content-Type": "multipart/form-data",
                            },
                            success:res=> {
                                // console.log(res);
                               let data =  JSON.parse(res.data);//将后台传来的json字符串转化为js对象
                               if(data.data.url!=undefined && data.data.url!="" &&data.data.url!=null){//若改对象内有不为空的url，则修改头像
                                   that.setData({
                                   'user.userface':data.data.url
                               })
                               wx.showToast({
                                 title: data.data.success,
                                 icon:'none'
                               })
                               that.modify();
                            //    console.log(that.data.user.userface);
                               }else{
                                   wx.showToast({
                                     title: data.data.error,
                                     icon:'none'
                                   })
                               }
                            },
                            fail:err=>{
                                console.log(err);
                                wx.showToast({
                                  title: '未知错误err',
                                  icon:'none'
                                })
                            }
                        })
                    },
            error: function(res){
                console.log(res);
                wx.showToast({
                  title: '用户取消上传',
                  icon:'none'
                })
            }
                })
    },//改头像
    modifyPhone(){
        const that =this;
        if(that.data.type4==false){//判断输入框状态
            that.setData({
                // toastbg:'手机',
                // isShowConfirm:true,
                type4:true,//若输入框隐藏，点击箭头则显示
                input4:'' //清空输入框
            })
        }else{
            if(that.data.input4==''||that.data.input4==null||that.data.input4==undefined){//若输入框为空
                that.setData({
                type4:false//若输入框显示，点击输入框则隐藏
                })
                wx.showToast({
                  title: '输入值为空，不予修改',
                  icon:'none'
                })
            }else{
                that.setData({
                    type4:false,//若输入框显示，点击输入框则隐藏
                    'user.phone':that.data.input4//赋值
                });
                POST(0,'/user/modifyPhone',{
                    id:app.globalData.uid,
                    newPhone: that.data.user.phone
                }).then(res=>{
                    if(res.data.code==1000){//更换成功
                        wx.showToast({
                          title: '更换成功，稍后将退出账号',
                          icon:'none'
                        })
                        // 提示框
                        wx.showModal({
                        title: '提示',
                        content: '修改手机号成功，即将退出账号？',
                        success: function (res) {
                        if (res.confirm) {
                            //退出登录
                            app.globalData.uid ='';
                            app.globalData.uname='点击头像进行登录';
                            app.globalData.userface='/static/photo.png';
                            app.globalData.token='';
                            app.globalData.money=0;
                            } else {
                                wx.showToast({
                                    title: '取消不了的哦',
                                    icon:'none'
                                })
                                //退出登录
                                // app.globalData.uid ='';
                                // app.globalData.uname='点击头像进行登录';
                                // app.globalData.userface='/static/photo.png';
                                // app.globalData.token='';
                                // app.globalData.money=0;
                        }
                        }
                        })
                    }else{
                        wx.showToast({
                          title: res.data.msg,
                          icon:'none'
                        })
                    }
                    // 更新数据
                    that.getuserData();
                })
            }
        }
    },////修改手机
    modify(){
        const that =this ;
        POST(0,'/user/modifyInfo',{
            email:that.data.user.email,
            gender:that.data.user.gender,
            id:that.data.user.uid,
            uname:that.data.user.uname,
            userface:that.data.user.userface
        }).then(res=>{
            // console.log(res);
            if(res.data.code==1000){
                app.globalData.uname=that.data.user.uname;
                app.globalData.userface=that.data.user.userface;
                wx.showToast({
                    title: '修改成功',
                    icon:'none'
                  })
            }else{
                that.getuserData();
                wx.showToast({
                    title: res.data.msg,
                    icon:'none'
                  })
            }
        });
    },//发送修改请求
    onClickback(){
        wx.switchTab({
          url: '/pages/about/about',
        })
    },//返回
    onChange(event){
        this.setData({
            input: event.detail,
          });
    },
//     // 弹窗
//   cancel: function () {
//     var that = this
//     that.setData({
//       isShowConfirm: false,
//       toasttext:'',
//       typei:0
//     })
//   },//取消
//   confirmAcceptance:function(){
//     var that = this;
//     let typei=that.data.typei;
//     if(typei==1)
//     {
//         if(that.data.toasttext!=''){
//             that.setData({
//                 'user.uname':that.data.toasttext,
//                 toasttext:''
                
//             })
//             that.modify();
//         }else{
//             wx.showToast({
//               title: '未输入数据',
//               icon:'none'
//             })
//         }
//     }else if(typei==2)
//     {
//         if(that.data.toasttext!=''){
//             that.setData({
//                 'user.gender':that.data.toasttext,
//                 toasttext:''
//             })
//             that.modify();
//         }else{
//             wx.showToast({
//               title: '未输入数据',
//               icon:'none'
//             })
//         }
//     }else if(typei==3)
//     {
//         if(that.data.toasttext!=''){
//             that.setData({
//                 'user.email':that.data.toasttext,
//                 toasttext:''
//             })
//             that.modify();
//         }else{
//             wx.showToast({
//               title: '未输入数据',
//               icon:'none'
//             })
//         }
//     }else if(typei==4)
//     {
//         if(that.data.toasttext!=''){
//             that.setData({
//                 'user.phone':that.data.toasttext,
//                 toasttext:''
//             })
//             POST(0,'/user/modifyPhone',{
//                 id:that.data.user.uid,
//                 newPhone:that.data.user.phone,
//             }).then(res=>{
//                 if(res.data.code==1000)
//                 {
//                     wx.showToast({
//                         title: '更改成功,将退出账号',
//                         icon:'none'
//                       })
//                 }else{
//                     wx.showToast({
//                         title: '格式不规范',
//                         icon:'none'
//                       })
//                 }
//             })
//         }else{
//             wx.showToast({
//               title: '未输入数据',
//               icon:'none'
//             })
//         } 
//     }
//     that.setData({
//       isShowConfirm: false,
//       typei:0
//     })
//   },//具体值在弹窗中通过typei判断修改的是啥，然后赋值，发送修改数据
})