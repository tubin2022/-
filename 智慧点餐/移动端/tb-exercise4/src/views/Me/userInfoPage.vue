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
              <van-image
                width="80"
                height="80"
                :src="userInfo.userFace"
                radius="5"
              />
            </template>
          </van-cell>
          <van-cell title="名字" is-link center :value="userInfo.realname"> </van-cell>
          <van-cell title="账号" is-link center :value="userInfo.username"> </van-cell>
          <van-cell title="电话" is-link center :value="userInfo.telephone"> </van-cell>
        </van-cell-group>
        <div style="height:25px"></div>
        <van-cell title="我的地址" is-link center :value="userInfo.address"></van-cell>
        <div style="height:25px"></div>
        <van-cell title="更多信息" is-link center> </van-cell>
      </div>
    </div>
  </div>
</template>

<script>
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
  },
  created() {
    this.userInfo = this.$route.query.user;
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
.userInfo .navBar /deep/.van-nav-bar__content {
  background-color: #e5e5e5;
}
</style>