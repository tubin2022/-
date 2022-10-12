<template>
  <div class="shoppingCart">
    <!-- 顶部导航栏 -->
    <div class="nav">
      <van-nav-bar left-arrow @click-left="onClickLeft" placeholder fixed>
        <template #title>
          <span :class="{ choose: cart }" @click="toShoppingCart">购物车</span
          >&nbsp;&nbsp;&nbsp;<span
            :class="{ choose: order }"
            @click="toUnpaidOrder"
            >待支付</span
          >
        </template>
        <template #right v-if="cart">
          <van-icon name="delete-o" size="18" color="orange" @click="deleteCart"/>
        </template>
      </van-nav-bar>
    </div>
    <!-- 页面主体 -->
    <div class="shopping">
      <!-- 购物车 -->
      <div class="cart" v-show="cart">
        <van-checkbox-group v-model="result" @change="calculate">
          <van-cell-group>
            <div v-for="(item, index) in myCartInfo" :key="item.id">
              <van-cell clickable @click="toggle(index)">
                <template #title>
                  <van-image
                    width="90"
                    height="90"
                    :src="item.photo"
                    radius="5"
                  />
                  <van-tag type="warning">{{ item.name }}</van-tag>
                  <van-tag type="danger">￥{{ item.price }}</van-tag>
                </template>
                <template #right-icon>
                  <van-checkbox :name="item" ref="checkboxes" />
                </template>
              </van-cell>
              <div class="steper">
                <van-stepper v-model="item.num" @change="steperChange" />
              </div>
            </div>
          </van-cell-group>
        </van-checkbox-group>
        <!-- 如果购物车为空,展示空白面板 -->
        <van-empty
          v-if="myCartInfo == null || myCartInfo.length == 0"
          class="custom-image"
          image="https://img01.yzcdn.cn/vant/custom-empty-image.png"
          description="您的购物车空空如也"
        />
        <!-- 当购物车不为空,显示购物车商品列表,并展示提交订单栏 -->
        <van-submit-bar
          :price="total"
          button-text="提交订单"
          @submit="onSubmit"
          v-if="myCartInfo != null"
        />
      </div>
      <!-- 点击提交订单弹出动作面板 -->
      <van-action-sheet v-model="show" title="可用地址信息">
        <van-radio-group v-model="addressId">
          <van-cell-group>
            <van-cell
              v-for="item in address"
              :key="item.id"
              :title="item.province + item.city + item.area"
              :label="item.realname+'  '+item.telephone"
              clickable
              @click="chooseAddress"
            >
              <template #right-icon>
                <van-radio :name="item.id" />
              </template>
            </van-cell>
          </van-cell-group>
        </van-radio-group>
      </van-action-sheet>
      <!-- 待支付订单 -->
      <div class="unpaidOrder" v-show="order">
        <van-collapse v-model="activeNames" v-for="(orders,index) in unpaidOrders" :key="index" >
            <van-collapse-item :name="orders.id" size="large">
              <template #title>
                <div>
                  <van-image width="80" height="80" :src="orders.orderLines[0].product.photo" radius="5"/>
                  <van-tag color="red" size="large">￥{{orders.total}}</van-tag>
                </div>
              </template>
              <template #value>
                <div>
                  <van-tag color="orange" size="large">{{orders.status}}x{{orders.orderLines.length}}</van-tag>
                  <van-cell  :value="orders.orderTime | fmtDate_global" size="large"  />
                </div>
              </template>
              <van-card
                v-for="order in orders.orderLines"
                :key="order.id"
                :num="order.num"
                :price="order.product.price"
                :desc="order.product.description"
                :title="order.name"
                :thumb="order.product.photo"
              />
            </van-collapse-item>
          </van-collapse>
      </div>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/http/tokenUtils";
import { getByCheck, post_jsonByCheck } from "@/http/axios";
import { Toast } from "vant";
import { Dialog } from 'vant';
export default {
  data() {
    return {
      cart: true,
      order: false,
      result: [],
      activeNames: ["1"],
      //  我的购物车信息
      myCartInfo: [],
      //   总价
      total: 0,
      // 我的信息
      userInfo: {},
      // 我的地址
      address: [],
      // 动作面板弹出
      show: false,
      // 选择的地址id
      addressId: '',
      // 待支付订单信息
      unpaidOrders:[]
    };
  },
  methods: {
    // 返回上一页
    onClickLeft() {
      this.$router.go(-1);
    },
    // 点击购物车
    toShoppingCart() {
      this.cart = true;
      this.order = false;
    },
    // 点击待支付
    toUnpaidOrder() {
      this.order = true;
      this.cart = false;
      this.getUnpaidDrder();
    },
    // 复选框组事件
    toggle(index) {
      this.$refs.checkboxes[index].toggle();
    },
    // 加载自己购物车信息
    getMyCartInfo() {
      let myCart = JSON.parse(localStorage.getItem("myCart"));
      this.myCartInfo = myCart;
    },
    // 当result改变时,重新计算total
    calculate() {
      let temp = 0;
      for (let i = 0; i < this.result.length; i++) {
        temp = this.result[i].price * this.result[i].num + temp;
      }
      this.total = temp * 100;
    },
    // 步进器值改变时事件
    steperChange() {
      this.calculate();
    },
    // 获取登录用户信息,页面创建时自动获取
    async getUserInfo() {
      let token = getToken();
      let param = {
        token: token,
      };
      let user = await getByCheck("/user/info", param);
      if (typeof user != "undefined") {
        this.userInfo = user.data.data;
        let params = {
          page: 1,
          pageSize: 10,
          userId: user.data.data.id,
        };
        let res = await getByCheck("/address/pageQuery", params);
        if (typeof res != "undefined") {
          this.address = res.data.data.list;
          this.addressId = this.address[0].id;
        }
      }
    },
    // 提交订单
    onSubmit() {
      // 判断用户是否登录
      let token = getToken();
      if (token == null) {
        Toast.fail("您还没登录,请登录后再试");
        this.$router.push("/login");
      } else if (this.result.length == 0) {
        // 如果没有选中任何结果,则不进行任何操作
        return;
      } else {
        // 如果选中了产品，打开位置面板
        this.show = true;
      }
    },
    // 单选框点击事件
    chooseAddress() {
      Dialog.confirm({
        message: "是否提交订单",
      })
        .then(() => {
          // 确认
          let chooseData = JSON.parse(
            JSON.stringify(this.result).replace(/id/g, "productId")
          );
          let params = {
            addressId: this.addressId,
            buyerMessage: "string",
            customerId: this.userInfo.id,
            orderLines: chooseData,
          };
          console.log(params);
          // 发送网络请求,提交订单
          this.saveOrder(params);
          this.show = false;
        })
        .catch(() => {
          // 取消
          this.show = false;
        });
    },
    //发送网络请求,提交订单
    async saveOrder(params){
      let res = await post_jsonByCheck('/app/order/submitOrder',params);
      if(res.data.status == 200){
        this.deleteCart();
        Toast.success('提交成功');
      }else{
        Toast.fail('提交失败,请重新再试');
      }
    },
    // 发送网络请求,获取待支付订单信息
    async getUnpaidDrder(){
      let params ={
        page:1,
        pageSize:25,
        status:'待支付',
        customerId:this.userInfo.id
      }
      let res = await getByCheck('/order/pageQuery',params);
      this.unpaidOrders = res.data.data.list;
    },
    // 删除选中的购物车项
    deleteCart(){
      if(this.result.length !=0){
        let cart = JSON.parse(localStorage.getItem('myCart'));
        let newCart = cart.filter(item => !this.result.some(ele=>ele.id == item.id));
        localStorage.setItem('myCart',JSON.stringify(newCart));
        this.myCartInfo = newCart;
      }
    }
  },

  // 生命周期钩子函数
  created() {
    // 页面创建时自动加载我的购物车信息
    this.getMyCartInfo();
    // 页面创建时自动获取我的用户信息
    this.getUserInfo();
  },
};
</script>

<style scoped>
.choose {
  color: orangered;
}
.steper {
  text-align: right;
}
</style>