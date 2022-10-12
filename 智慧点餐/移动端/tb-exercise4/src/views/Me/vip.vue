<template>
  <div class="myVip">
    <div class="navBar">
      <van-nav-bar
        fixed
        title="我的会员"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div style="height: 20px"></div>
    <div class="vip">
      <van-empty description="您还不是会员哦" image="error">
        <van-button round type="danger" class="bottom-button">去加入</van-button>
      </van-empty>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
  },
  created() {},
};
</script>

<style scoped>
.myVip {
  background-color: #e5e5e5;
  width: 100%;
  height: 100%;
  position: fixed;
}
.myVip .navBar /deep/.van-nav-bar__content {
  background-color: #e5e5e5;
}
.myVip .vip{
    background-color: #fff;
}
</style>