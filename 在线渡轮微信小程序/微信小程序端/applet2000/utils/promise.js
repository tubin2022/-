let baseAPI = ''
let baseAPI1 = 'http://localhost:8888'
let baseAPI2 = 'https://api.muxiaoguo.cn/api'
function POST(typei,url, params) {
  //加载loading
if(typei==0)
{
    baseAPI=baseAPI1;
}else if(typei==1)
{
    baseAPI=baseAPI2;
}
else
{
    console.log("类型错误!");
}
  wx.showLoading({
      title: '加载中...',
      mask: true
  })
  let promise = new Promise(function (resolve, reject) {
      wx.request({
          url: baseAPI + url,
          data: params,
          header: {
              "Content-Type": "application/x-www-form-urlencoded",
          },
          method: 'POST',
          success: (res) => {
              resolve(res)
          },
          fail: (res) => {
              reject(res)
          },
          complete: () => {
              wx.hideLoading()
          }
      })
  });
  return promise;
}
function GET(typei,url, params) {
    if(typei==0)
{
    baseAPI=baseAPI1;
}else if(typei==1)
{
    baseAPI=baseAPI2;
}
else
{
    console.log("类型错误!");
}
  let promise = new Promise(function (resolve, reject) {
      
      wx.request({
          url: baseAPI + url,
          data: params,
          header: {
              "Content-Type": "application/x-www-form-urlencoded",
          },
          method: 'GET',
          success: (res) => {
              resolve(res)
          },
          fail: (res) => {
              reject(res)
          }
      })
  });
  return promise
}
module.exports = {
    POST: POST,
    GET: GET,
}