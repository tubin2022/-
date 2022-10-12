<template>
  <div class="me">
    <!-- 顶部区域 -->
    <div class="navBar">
      <div class="show">
        <van-row type="flex" justify="center" gutter="15">
          <!-- 左部用户头像 -->
          <van-col span="6">
            <div class="userface">
              <van-image
                show-error
                show-loading
                radius="5"
                width="100"
                height="100"
                :src="userFace"
                @click="previewImg"
              />
            </div>
          </van-col>
          <!-- 右部展示个人信息 -->
          <van-col span="14">
            <div class="userInfo">
              <van-cell-group :border="false">
                <van-cell :title="realName" :label="'用户名:' + userName" @click="userInformation">
                  <template #right-icon>
                    <van-icon name="qr-invalid" size="20px" />
                  </template>
                </van-cell>
              </van-cell-group>
            </div>
          </van-col>
        </van-row>
      </div>
    </div>
    <div style="height: 25px"></div>
    <!-- 下部功能区域 -->
    <div class="service">
      <div class="serve">
        <van-cell title="我的资产" is-link @click="myCash">
          <template #icon>
            <van-icon name="gold-coin" size="20" />
          </template>
        </van-cell>
        <van-cell title="我的会员" is-link @click="myvip">
          <template #icon>
            <van-icon name="vip-card" size="20" />
          </template>
        </van-cell>
      </div>
      <div style="height: 25px"></div>
      <div class="other">
        <van-cell title="我的订单" is-link @click="toOrder">
          <template #icon>
            <van-icon name="balance-list" size="20" />
          </template>
        </van-cell>
        <van-cell title="我的地址" is-link @click="toUserInfo">
          <template #icon>
            <van-icon name="map-marked" size="20" />
          </template>
        </van-cell>
        <van-cell title="店铺关注" is-link>
          <template #icon>
            <van-icon name="like" size="20" />
          </template>
        </van-cell>
        <van-cell title="购物车" is-link @click="toshoppingCart">
          <template #icon>
            <van-icon name="shopping-cart" size="20" />
          </template>
        </van-cell>
        <van-cell title="客服" is-link>
          <template #icon>
            <van-icon name="service" size="20" />
          </template>
        </van-cell>
      </div>
      <div style="height: 25px"></div>
      <div class="setting">
        <van-cell title="设置" is-link>
          <template #icon>
            <van-icon name="setting" size="20" />
          </template>
        </van-cell>
      </div>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/http/tokenUtils";
import { getByCheck } from "@/http/axios";
import { ImagePreview } from 'vant';
export default {
  data() {
    return {
      userFace: "",
      userName: "立即登录",
      realName: "立即登录",
      me:{}
    };
  },
  methods: {
    // 获取用户信息
    async getUserInfo() {
      let token = getToken();
      let param = {
        token: token,
      };
      let user = await getByCheck("/user/info", param);
      if (typeof user != "undefined") {
        this.userFace = user.data.data.userFace;
        this.userName = user.data.data.username;
        this.realName = user.data.data.realname;
        this.me = user.data.data;
      }
    },
    // 点击头像,预览图片
    previewImg() {
      ImagePreview([this.userFace]);
    },
    // 点击用户信息事件
    userInformation(){
        if(this.userName =='立即登录'){
            this.$router.push('/login');
        }else{
            this.$router.push({
                path:'/manage/userInfoPage',
                query:{
                    user:this.me
                }
            })
        }
    },
    // 点击我的订单
    toOrder(){
        this.$router.push('/manage/order');
    },
    // 点击我的地址
    toUserInfo(){
        this.userInformation();
    },
    // 点击购物车
    toshoppingCart(){
        this.$router.push('/manage/shoppingCart')
    },
    // 点击我的会员
    myvip(){
        this.$router.push('/manage/vip');
    },
    // 点击我的资产
    myCash(){
        this.$router.push('/manage/cash');
    }
  },
  created() {
    this.getUserInfo();
  },
};
</script>

<style scoped>
.me {
  background-color: #e5e5e5;
  width: 100%;
  height: 100%;
  position: fixed;
}
.me .navBar {
  background-color: #ffff;
  height: 180px;
  width: 100%;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: center;
}
.me .navBar .show {
  width: 100%;
}
.me .service .serve {
  background-color: #ffff;
  width: 100%;
}
.me .service .other {
  background-color: #ffff;
  width: 100%;
}
.me .service .setting {
  background-color: #ffff;
  width: 100%;
}
</style>