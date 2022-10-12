<template>
  <!-- 首页 -->
  <div class="homePage">
    <!-- 顶部导航栏 -->
    <div class="nav">
      <van-nav-bar left-arrow placeholder>
        <template #left>
          <van-icon name="location-o" @click="chooseLocation" /><span
            class="location"
            >{{ location }}</span
          >
        </template>
        <template #right>
          <van-icon
            name="shopping-cart-o"
            size="25"
            color="grey"
            :badge="orderCount == 0 ? '' : orderCount"
            @click="toShoppingCart"
          />
          <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <van-icon
            name="chat-o"
            size="25"
            color="grey"
            dot
            @click="toMessagePage"
          />
        </template>
      </van-nav-bar>
      <!-- 位置选择动作面板 -->
      <van-action-sheet v-model="show" title="获取位置">
        <div class="content">
          <van-area
            title="位置"
            :area-list="areaList"
            @cancel="cancel"
            @confirm="confirm"
          />
        </div>
      </van-action-sheet>
    </div>
    <!-- 顶部搜索栏 -->
    <div class="search">
      <van-search
        left-icon="scan"
        show-action
        clearable
        v-model="searchInfo"
        shape="round"
        background="#4fc08d"
        placeholder="鲜榨西瓜汁watermelon juice"
      >
        <template #action>
          <van-button
            icon="search"
            type="info"
            size="small"
            round
            @click="search"
            >搜索</van-button
          >
        </template>
      </van-search>
    </div>
    <!-- 产品分类菜单栏 -->
    <div class="menuGrid">
      <van-grid :column-num="3">
        <van-grid-item
          v-for="(menu, index) in menuDatas"
          :key="menu.id"
          :icon="menu.icon"
          :text="menu.name"
          @click="toDetail(index, menu)"
        />
        <van-grid-item text="全部" icon="apps-o" @click="toProductListPage" />
      </van-grid>
    </div>
    <!-- 中间消息通知栏 -->
    <div class="notice">
      <van-notice-bar left-icon="volume-o" :scrollable="false">
        <van-swipe
          vertical
          class="notice-swipe"
          :autoplay="2000"
          :show-indicators="false"
        >
          <van-swipe-item>今天下一单,立即领5元优惠券</van-swipe-item>
          <van-swipe-item>您有3次红包获取机会,最高100元</van-swipe-item>
          <van-swipe-item>会员畅享会员补贴,立即领取会员</van-swipe-item>
        </van-swipe>
      </van-notice-bar>
    </div>
    <!-- 产品展示栏 -->
    <div class="productList">
      <van-tabs v-model="activeMenu" @change="getProductList">
        <van-tab v-for="menu in menuDatas" :title="menu.name" :key="menu.id">
          <!-- 展示对应标签的产品列表 -->
          <van-card
            :price="product.price"
            :tag="product.status"
            :desc="product.description"
            :title="product.name"
            :thumb="product.photo"
            v-for="product in productList"
            :key="product.id"
          >
            <template #tags>
              <van-tag plain mark size="medium" type="danger">26减10</van-tag>
              <van-tag plain mark size="medium" type="warning"
                >满20免费配送</van-tag
              >
            </template>
            <template #footer>
              <van-button size="mini" color="#4FC08D" round icon="bag-o" @click="buy(product)"
                >购买</van-button
              >
              <van-button
                size="mini"
                color="#4FC08D"
                round
                icon="shopping-cart-o"
                @click="add(product)"
                >加入购物车</van-button
              >
            </template>
          </van-card>
        </van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script>
import { areaList } from "@vant/area-data";
import { Dialog } from "vant";
import { getToken } from "@/http/tokenUtils";
import { Toast } from "vant";
import { get, getByCheck, post } from "@/http/axios";
export default {
  data() {
    return {
      searchInfo: "",
      show: false,
      areaList,
      //我的位置
      location: "获取位置失败...",
      // 产品菜单信息
      menuDatas: [],
      // 选中的产品展示标签
      activeMenu: 0,
      // 产品列表信息
      productList: [],
      // 存储已登录用户信息
      user: {},
      // 用户购物车订单数量(未支付)
      orderCount: "",
      // 未支付订单信息
      unpaidOrder: [],
      // 我的购物车信息
      mycart: [],
    };
  },
  methods: {
    //点击位置图标选择我的位置,打开位置选择动作面板
    chooseLocation() {
      let token = getToken();
      if (token == null) {
        Toast({
          message: "您还没登录,无法进行相关操作",
          position: "top",
        });
      }
      this.show = true;
    },
    // 省市区选择组件按下取消事件
    cancel() {
      this.show = false;
    },
    // 省市区选择组件按下确认事件
    confirm(res) {
      let province = res[0].name;
      let city = res[1].name;
      let area = res[2].name;
      this.location = city + area;
      let userInfo = this.user;
      // 调用保存位置信息的方法
      this.saveLocation(province, city, area, userInfo);
      this.show = false;
    },
    // 保存位置信息
    saveLocation(province1, city1, area1, userInfo) {
      Dialog.confirm({
        message: "保存您当前的位置:" + province1 + city1 + area1,
      })
        .then(() => {
          // 确认,如果用户确认保存,需要判断是否登录
          let token = getToken();
          if (token == null) {
            //没有登录,先登录
            Toast.fail("请先登录");
            this.$router.push("/login");
          } else {
            //登录了,直接保存
            this.savelocationByAxios(province1, city1, area1, userInfo);
          }
        })
        .catch(() => {
          // 取消
          this.location = this.user.address;
        });
    },
    // 发送网络请求,保存位置信息
    async savelocationByAxios(province1, city1, area1, userInfo) {
      let data = {
        realname: userInfo.realname,
        telephone: userInfo.telephone,
        province: province1,
        city: city1,
        area: area1,
        userId: userInfo.id,
      };
      let success = await post("/address/saveOrUpdate", data);
      if (success.data.status == 200) {
        Toast.success("保存成功");
      } else {
        Toast.fail("保存失败");
      }
    },
    // 顶部搜索产品相关信息
    async search() {
      if (this.searchInfo == "") {
        return;
      } else {
        this.$router.push({
          path: "/manage/searchProduct",
          query: {
            searchInfo: this.searchInfo,
          },
        });
      }
    },
    // 产品分类菜单栏相关信息
    async getMenuInfo() {
      let data = {
        page: 1,
        pageSize: 8,
      };
      let menuData = await get("/productCategory/pageQuery", data);
      this.menuDatas = menuData.data.data.list;
    },
    // 点击菜单栏,下方对应标签跳转到相应的地方
    async toDetail(index, menu) {
      this.activeMenu = index;
      let data = {
        page: 1,
        pageSize: 6,
        productCategoryId: menu.id,
      };
      let products = await get("/product/pageQuery", data);
      this.productList = products.data.data.list;
    },
    // 标签改变时触发事件
    async getProductList() {
      let data = {
        page: 1,
        pageSize: 6,
        productCategoryId: this.menuDatas[this.activeMenu].id,
      };
      let products = await get("/product/pageQuery", data);
      this.productList = products.data.data.list;
    },
    // 加载第一个标签的产品
    async getFirstTageProductList() {
      // 获取产品分类信息
      let data1 = {
        page: 1,
        pageSize: 8,
      };
      let menuData = await get("/productCategory/pageQuery", data1);
      let firstTag = menuData.data.data.list[0].id;
      // 获取第一个标签的产品列表信息
      let data2 = {
        page: 1,
        pageSize: 6,
        productCategoryId: firstTag,
      };
      let products = await get("/product/pageQuery", data2);
      this.productList = products.data.data.list;
    },
    // 点击全部,跳转到产品列表页面
    toProductListPage() {
      this.$router.push("/manage/productList");
    },
    // 若用户已经登录,则自动更新location
    async getUserLocation() {
      let userToken = getToken();
      if (userToken != null) {
        let data1 = {
          token: userToken,
        };
        let userInfo = await get("/user/info", data1);
        this.user = userInfo.data.data;
        this.location = userInfo.data.data.address;
      }
    },
    // 点击右上角的消息通知图标,跳转到消息页面
    toMessagePage() {
      this.$router.push("/manage/message");
    },
    // 点击购物车图标,跳转到我的购物车页面
    toShoppingCart() {
      this.$router.push("/manage/shoppingCart");
    },
    // 获取登录用户未支付的订单
    async getUnpaidOrder() {
      // 根据token获取用户id
      let userToken = getToken();
      if (userToken != null) {
        let data1 = {
          token: userToken,
        };
        let userInfo = await get("/user/info", data1);
        let userId = userInfo.data.data.id;
        // 根据用户id获取未支付的订单
        let params = {
          page: 1,
          pageSize: 200,
          status: "待支付",
          customerId: userId,
        };
        let orders = await getByCheck("/order/pageQuery", params);
        if (typeof orders != "undefined") {
          this.unpaidOrder = orders.data.data.list;
          this.orderCount = this.unpaidOrder.length;
          let myCartLength = JSON.parse(localStorage.getItem("myCart"));
          if (myCartLength != null) {
            this.orderCount = this.orderCount + myCartLength.length;
          }
        }
      }
    },
    //点击添加到购物车,将该商品添加到我的购物车内
    add(item) {
      Dialog.confirm({
        message: "是否将" + item.name + "加入购物车",
      })
        .then(() => {
          // 确认
          this.mycart.push(item);
          localStorage.setItem("myCart", JSON.stringify(this.mycart));
          this.orderCount++;
          Toast.success("加入成功");
        })
        .catch(() => {
          // 取消
        });
    },
    // 点击购买按钮
    buy(item){
      Toast('请添加到购物车后购买');
    }
  },

  // 生命周期钩子函数
  created() {
    // 打开首页判断是否登录,没有登录则弹出提示框,提醒用户登录(用户可以选择不登录)
    let token = getToken();
    if (token == null) {
      Dialog.confirm({
        message: "您还没登录,位置信息不可用",
        theme: "round-button",
      })
        .then(() => {
          // 确认
          this.$router.push("/login");
        })
        .catch(() => {
          // 取消
        });
    }
    // 自动获取产品菜单栏的信息
    this.getMenuInfo();
    // 自动获取第一个标签的产品列表信息
    this.getFirstTageProductList();
    // 页面创建自动获取用户信息
    this.getUserLocation();
    // 自动获取登录用户未支付的订单
    this.getUnpaidOrder();
  },
};
</script>

<style scoped>
/* 滚动消息通知栏样式 */
.notice-swipe {
  height: 40px;
  line-height: 40px;
}
/* 位置选择动作面板 */
.content {
  padding: 16px 16px 160px;
}
/*位置信息字体样式  */
.location {
  font-weight: bold;
}
</style>