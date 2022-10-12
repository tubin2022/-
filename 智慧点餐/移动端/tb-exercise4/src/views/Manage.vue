<template>
  <div class="manage">
    <router-view></router-view>
    <van-tabbar v-model="active" placeholder>
      <van-tabbar-item  to="/manage/home" icon="wap-home-o">首页</van-tabbar-item>
      <van-tabbar-item  to="/manage/order" icon="orders-o">订单</van-tabbar-item>
      <van-tabbar-item  to="/manage/me" icon="user-circle-o">我</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active:0
    }
  },
};
</script>

<style scoped>
</style>