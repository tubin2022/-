<template>
  <div class="myCash">
    <div class="navBar">
      <van-nav-bar
        fixed
        title="我的钱包"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <div style="height: 20px"></div>
    <div class="cash">
      <van-empty description="您的钱包空空如也" image="https://img01.yzcdn.cn/vant/custom-empty-image.png">
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
.myCash {
  background-color: #e5e5e5;
  width: 100%;
  height: 100%;
  position: fixed;
}
.myCash .navBar /deep/.van-nav-bar__content {
  background-color: #e5e5e5;
}
.myCash .cash{
    background-color: #fff;
}
</style>