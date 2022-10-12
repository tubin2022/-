<template>
  <div class="userInfo">
    <div class="navBar">
      <van-nav-bar
        fixed
        title="个人信息"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div style="height: 60px"></div>
    <div class="infoList">
      <div class="list">
        <van-cell-group>
          <van-cell title="头像" is-link center>
            <template #default>
				<van-uploader :after-read="afterRead">
              <van-image
                width="60"
                height="60"
                :src="userInfo.nowFace"
                radius="5"
              />
			  </van-uploader>
            </template>
          </van-cell>
          <van-cell title="名字" is-link center :value="userInfo.userName"> </van-cell>
          <van-cell title="账号" is-link center :value="userInfo.userAccount"> </van-cell>
          <van-cell title="电话" is-link center :value="userInfo.userPhone"> </van-cell>
		  <van-cell title="出生年月" is-link center :value="userInfo.userBirthday"></van-cell>
		  <van-cell title="性别" is-link center :value="userInfo.userGender"></van-cell>
        </van-cell-group>
        <div style="height:25px"></div>
        <van-cell title="更多信息" is-link center> </van-cell>
      </div>
    </div>
  </div>
</template>

<script>
import {post_json,get} from '../../http/axios'
import http from 'axios'
export default {
  data() {
    return {
      userInfo: {},
    };
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
	async afterRead(file) {
	  // 此时可以自行将文件上传至服务器
		let formData = new FormData()
		formData.append('file', file.file)
		const res = await http({
		  url: 'http://localhost:8088/file/uploadFile',
		  method: 'POST',
		  data: formData
		})
		let newUserface = res.data.data
		let param = {
			nowFace:newUserface,
			userId:this.userInfo.userId
		}
		this.updateUserInfo(param)
	},
   async updateUserInfo(param){
		let result = await post_json('/user/updateUserInfo',param)
		this.getUserInfo()
	},
	async getUserInfo(){
		let token = localStorage.getItem('token')
		let param = {
			userToken:token
		}
		let result = await get('/user/userInfo',param)
		this.userInfo = result.data.data
	}
  },
  created() {
    this.userInfo = this.$route.query.userInfo;
  },
};
</script>

<style scoped>
.userInfo {
  background-color: #e5e5e5;
  width: 100%;
  height: 100%;
  position: fixed;
}
.userInfo .list{
	text-align: left;
}
.userInfo .navBar /deep/.van-nav-bar__content {
  background-color: #e5e5e5;
}
</style>