// pages/Register/register.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        // 步骤条
        steps: [
            {
              desc: '验证身份',
              inactiveIcon: 'arrow',
              activeIcon: 'success',
            },
            {
              desc: '账号设置',
              inactiveIcon: 'arrow',
              activeIcon: 'success',
            },
            {
              desc: '个人详细信息',
              inactiveIcon: 'arrow',
              activeIcon: 'success',
            }
          ],
        // 第几步
        active:0,
        // 输入的验证码
        inputCode:'',
        // 自动生成的验证码
        code:'',
        // 验证码错误提示信息
        errMessage:'',
        // 用户名
        username:'',
        // 密码
        password:'',
        // 真实姓名
        realname:'',
        // 性别
        gender:'',
        // 电话号码
        telephone:'',
        // 地址
        address:'',
        // 错误提示
        errorName:false,
        errorPassword:false,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },
    onShow(){
        this.getIdentifyCode();
    },
    // 点击下一步按钮
    next(){
        let that = this;
        if(this.data.inputCode == this.data.code){
            // 验证成功
            this.setData({
                active:this.data.active+1
            })
        }else{
            // 验证失败
            this.setData({
                errMessage:'输入的验证码不匹配'
            })
            this.getIdentifyCode();
            setTimeout(function(){
                that.setData({
                    errMessage:'',
                    inputCode:''
                })
            },1500)
        }       
    },

    // 自动生成验证码
    getIdentifyCode(){
        let data = Math.random().toString(36).slice(2,6);
       this.setData({
           code:data
       })
    },

     // 用户名输入框输入时事件
     onChangeName(){
        this.setData({
            errorName:false
        })
    },
     // 密码输入框输入时事件
     onChangePassword(){
        this.setData({
            errorPassword:false
        })
    },

    // 点击第二步中的下一步按钮
    next_1(){
        let that = this;
        if(this.data.username == '' || this.data.password == ''){
            if(this.data.username == ''){
                this.setData({
                    errorName:true
                })
            }
            if(this.data.password == ''){
                this.setData({
                    errorPassword:true
                })
            }
        }else{
            that.setData({
                active:that.data.active+1
            })
        }
    },

    // 点击第二步中的上一步按钮
    previous(){
        this.setData({
            active:this.data.active-1,
            inputCode:''
        })
        this.getIdentifyCode();
    },

    // 点击第三步中的上一步按钮
    previous_1(){
        this.setData({
            active:this.data.active-1,
            username:'',
            password:''
        })
    },

    // 点击第三步中的注册按钮,发送网络请求,注册用户
    register(){
        let params = {
            username:this.data.username,
            password:this.data.password,
            telephone:this.data.telephone,
            realname:this.data.realname,
            gender:this.data.gender,
            address:this.data.address
        }
        wx.request({
          url: 'http://47.100.84.201:8888/user/register',
          data:params,
          method:'POST',
          header: {
            "Content-Type": "application/x-www-form-urlencoded",
         },
            success:(res)=>{
                if(res.data.status == 200){
                    wx.showModal({
                        content: res.data.message,
                        confirmText:'去登录',
                        success (res) {
                          if (res.confirm) {
                           wx.redirectTo({
                             url: '/pages/Login/login',
                           })
                          } else if (res.cancel) {
                          }
                        }
                      })
                }else{
                    wx.showToast({
                      title: '注册失败,请稍后重新再试',
                    })
                }
            }
        })
    }
})