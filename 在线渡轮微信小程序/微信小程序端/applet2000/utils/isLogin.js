const app =getApp();
//引入工具类的promise。js
const request =require('../utils/promise.js');
//判断是否登录
function IS(){
    const that =this;
    // console.log(app.globalData.token);
    request.GET(0,'/user/info',{
        token:app.globalData.token
    }).then(res=>{
        if(res.data.code==1000){
                app.globalData.uid=res.data.data.uid;
                app.globalData.uname=res.data.data.uname;
                app.globalData.userface=res.data.data.userface;
        }else{
            wx.showToast({
              title: res.data.msg+' 请先登录',
            })
            // 卸载当前页，前往登录
            wx.redirectTo({
              url: '/pages/login/login',
            })
            //保留当前页，前往登录
            // wx.navigateTo({
            //   url: '/pages/login/login',
            // })
        }
    })
}
module.exports ={
    IS:IS
}