<template>
  <div class="message">
      <!-- 顶部导航栏 -->
    <div class="nav">
      <van-nav-bar
        placeholder
        title="我的消息"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
      />
    </div>
    <!-- 消息展示栏 -->
    <div class="notice">
        <van-empty description="您还没有收到任何消息哦" />
    </div>
  </div>
</template>

<script>
export default {
    data() {
        return {
            
        }
    },
    methods: {
        // 返回上一页
        onClickLeft(){
            this.$router.go(-1)
        }
    },
};
</script>

<style scoped>
</style>