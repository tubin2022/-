//vue.config.js
 module.exports = {
  devServer: {
	  host:'localhost',
	  port:8080,
    proxy: {
      '/amap': {
      　　    target: 'https://restapi.amap.com/v3/geocode',
      　　　　changeOrigin: true,
      		 secure: false,
			 ws:true,
      　　　　pathRewrite: {
      			'^/amap': ''
      　　	}
      }
    }
  }
}
 