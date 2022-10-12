//vue.config.js
 module.exports = {
  devServer: {
	  host:'localhost',
	  port:8081,
    proxy: {
      '/api': {
      　　    target: 'http://localhost:8089',
      　　　　changeOrigin: true,
      		 secure: false,
			 ws:true,
      　　　　pathRewrite: {
      			'^/api': ''
      　　	}
      }
    }
  }
}
 