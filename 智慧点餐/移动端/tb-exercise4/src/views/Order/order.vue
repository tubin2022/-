<template>
  <div class="order">
    <div class="placehoder">
      <van-nav-bar title="我的订单"/>
    </div>
    <!-- 顶部标签导航标签栏 -->
    <div class="nav">
      <van-tabs v-model="active" @change="getOrderByStatus" sticky>
        <van-tab title="全部">
          <!-- 对应标签全部的订单列表 -->
          <van-collapse v-model="activeNames" v-for="(orders,index) in allOrder" :key="index" >
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
        </van-tab>
        <van-tab title="派送中">
          <!-- 对应标签派送中的订单列表 -->
          <van-collapse v-model="activeNames" v-for="(orders,index) in DeliveryOrder" :key="index" >
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
        </van-tab>
        <van-tab title="待评价">
          <!-- 对应标签待评价的订单列表 -->
          <van-collapse v-model="activeNames" v-for="(orders,index) in appraiseOrder" :key="index" >
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
        </van-tab>
        <van-tab>
          <template #title>
            <van-icon name="cart-circle-o" size="15">待支付</van-icon>
          </template>
        </van-tab>
        <van-tab>
          <template #title><van-icon name="search" size="25" /></template>
          <!-- 搜索标签的内容展示 -->
          <van-field
            v-model="searchInfo"
            center
            clearable
            placeholder="请输入六个月内的订单记录"
          >
            <template #button>
              <van-button size="small" type="primary" @click="searchOrder">查询</van-button>
            </template>
          </van-field>
          <div class="searchContext" v-show="show">
            <van-empty image="search" description="没有找到相关订单信息" />
          </div>
        </van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script>
import { getToken } from "@/http/tokenUtils";
import { get, getByCheck } from "@/http/axios";
export default {
  data() {
    return {
      active: 0,
      searchInfo: "",
      activeNames: ["1"],
      // 用户信息
      user: {},
      // 全部订单信息
      allOrder: [],
      // 待派送订单信息
      DeliveryOrder:[],
      // 待评价订单信息(对应状态为已完成)
      appraiseOrder:[],
      // 搜索内容控制展示属性
      show:false
    };
  },
  methods: {
    // 获取用户全部订单信息
    async getAllOrders() {
      // 获取用户信息
      let token = getToken();
      let param = {
        token: token,
      };
      let user = await getByCheck("/user/info", param);
      if (typeof user != "undefined") {
        this.user = user.data.data;
        // 获取全部订单信息
        let params = {
          page: 1,
          pageSize: 200,
          customerId: user.data.data.id,
        };
        let allOrders = await get("/order/pageQuery", params);
        this.allOrder = allOrders.data.data.list;
      }
    },
    // 获取用户待派送的订单信息
    async getOrdersByDelivery(userInfo){
      let params = {
        page:1,
        pageSize:200,
        status:'待派送',
        customerId:userInfo.id
      }
      let orders = await get('/order/pageQuery',params);
      this.DeliveryOrder = orders.data.data.list;
    },
    // 获取用户已完成的订单信息(即待评价)
    async getOrdersByAppraise(userInfo){
      let params = {
        page:1,
        pageSize:200,
        status:'已完成',
        customerId:userInfo.id
      }
      let orders = await get('/order/pageQuery',params);
      this.appraiseOrder = orders.data.data.list;
    },
    // 导航栏对应标签改变时触发事件
    getOrderByStatus(title){
      let userInfo = this.user
      if(title ==1){
        // 待派送
        this.getOrdersByDelivery(userInfo);
      }
      if(title == 2){
        // 待评价
        this.getOrdersByAppraise(userInfo);
      }
      if(title == 3){
        // 待支付
        this.$router.push('/manage/shoppingCart')
      }
    },
    // 导航栏为搜索按钮时,点击搜索按钮事件
    searchOrder(){
      if(this.searchInfo == ''){
        return
      }else{
        // 进行搜索(由于接口问题,这里全部展示空白面板)
        this.show = true;
      }
    },
  },

  // 生命周期钩子函数
  created() {
    // 自动获取全部订单信息
    this.getAllOrders();
  },
};
</script>

<style scoped>
</style>