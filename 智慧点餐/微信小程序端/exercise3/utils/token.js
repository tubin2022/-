function getToken() {
    let promise = new Promise(function (resolve, reject) {
       wx.getStorage({
           key:'token',
           success: (res)=>{
               resolve(res)
           },
           fail: (res) =>{
               reject(res)
           }
       })
    })
  return promise;
}
function setToken(token) {
    let promise = new Promise(function(resolve,reject){
        wx.setStorage({
            key:'token',
            data:token,
            success: (res)=>{
                resolve(res)
            },
            fail: (res) =>{
                reject(res)
            }
        })
    })
    return promise;
}
function removeToken() {
   let promise = new Promise(function(resolve,reject){
       wx.removeStorage({
         key: 'token',
         success: (res) =>{
             resolve(res)
         },
         fail:(res) =>{
             reject(res)
         }
       })
   })
   return promise;
}
module.exports = {
    getToken:getToken,
    setToken:setToken,
    removeToken:removeToken
}