<template>
  <div class="searchPage">
    <!-- 顶部导航 -->
    <div class="nav">
      <van-nav-bar
        :title="'与' + searchInfo + '有关'"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
        placeholder
      >
        <template #right>
          <van-icon name="search" size="18" />
        </template>
      </van-nav-bar>
    </div>
    <!-- 列表展示搜索内容 -->
    <div class="searchList">
      <van-list>
        <van-swipe-cell>
          <van-card
            v-for="product in productListBySearch"
            :key="product.id"
            :desc="product.description"
            :title="product.name"
            :price="product.price"
            class="goods-card"
            :thumb="product.photo"
            @click="showProductDetai(product)"
          >
            <template #footer>
              <van-button size="mini" color="#4FC08D" round icon="bag-o"
                >购买</van-button
              >
              <van-button
                size="mini"
                color="#4FC08D"
                round
                icon="shopping-cart-o"
                >加入购物车</van-button
              >
            </template>
          </van-card>
          <template #right>
            <van-button
              square
              text="分享"
              type="info"
              icon="share-o"
              block
              size="large"
            />
            <van-button type="warning" block square icon="star-o" size="large"
              >收藏</van-button
            >
          </template>
        </van-swipe-cell>
      </van-list>
      <!-- 没有对应关键字的产品展示空白面板 -->
      <van-empty description="没有找到相似的产品" v-if="productListBySearch.length ==0" image-size="100px"/>
    </div>
    <!-- 分割线 -->
    <div class="divide">
      <van-divider
        :style="{ color: '#1989fa', borderColor: '#1989fa', padding: '0 16px' }"
        content-position="left"
      >
        其它
      </van-divider>
    </div>
    <!-- 展示一些其它产品信息 -->
    <div class="other">
      <van-card
        v-for="product in otherProductList"
        :key="product.id"
        :price="product.price"
        :desc="product.description"
        :title="product.name"
        :thumb="product.photo"
        @click="showProductDetai(product)"
      >
        <template #tags>
          <van-tag plain round size="medium" type="danger" text-color="#ad0000"
            >全场低至9.9</van-tag
          >
          <van-tag plain round size="medium" type="danger" text-color="#ad0000"
            >回头客10W+</van-tag
          >
        </template>
        <template #footer>
          <van-button
            color="linear-gradient(to right, #02AAB0,#00CDAC)"
            size="mini"
            round
          >
            去看看
          </van-button>
        </template>
      </van-card>
    </div>
    <!-- 点击每个商品,展示动作面板,展示商品详情 -->
      <van-action-sheet v-model="showActionSheet" title="商品详情">
        <div class="content">
          <!-- 商品图片 -->
          <van-row type="flex" justify="center">
            <van-col span="6">
              <van-image
                radius="5"
                width="100"
                height="100"
                :src="clickProduct.photo"
                @click="privewImg"
              />
            </van-col>
          </van-row>
          <van-row type="flex" justify="center">
            <van-col span="10" offset="2">
              <span class="productName">{{clickProduct.name}}<span class="productPrice">￥{{clickProduct.price}}</span></span>
            </van-col>
          </van-row>
          <van-row type="flex" justify="center">
            <van-col span="24"><span class="productDesc">{{clickProduct.description}}</span></van-col>
          </van-row>
        </div>
      </van-action-sheet>
  </div>
</template>

<script>
import { getByCheck } from '@/http/axios';
import { ImagePreview } from 'vant';
export default {
  data() {
    return {
      // 用户输入的查询信息
      searchInfo: "",
      // 对应查询信息查询的结果
      productListBySearch:[],
      // 其它产品信息,用于推荐给用户
      otherProductList:[],
       // 控制商品详情动作面板
      showActionSheet: false,
      // 点击的商品对象
      clickProduct: {}
    };
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    // 查询上一个页面用户查询的产品信息
    async getProductListBySearchInfo(){
      let data1 = {
        page:1,
        pageSize:8,
        name:this.searchInfo
      }
      let productList = await getByCheck('/product/pageQuery',data1);
      if(typeof(productList) != "undefined"){
        this.productListBySearch = productList.data.data.list;
      }  
    },
    // 查询一些其它产品的信息,推荐给用户
    async getOtherProductList(){
      let randomPage = Math.floor(Math.random()*20);
      let data2 = {
        page:randomPage,
        pageSize:4
      }
      let otherProducts = await getByCheck('/product/pageQuery',data2);
      if(typeof(otherProducts) != "undefined"){
         this.otherProductList = otherProducts.data.data.list;
      }
    },
    // 点击每个商品,展示详情
    showProductDetai(item) {
      this.showActionSheet = true;
      this.clickProduct = item;
    },
    // 预览商品详情中的图片
    privewImg(){
      ImagePreview([this.clickProduct.photo]);
    }
  },
  // 生命周期钩子函数
  created() {
    // 将路由传过来的搜索信息存到本地
    this.searchInfo = this.$route.query.searchInfo;
    // 页面创建时,自动查询用户搜索的对应产品信息
    this.getProductListBySearchInfo();
    // 页面创建时,自动推荐其它产品信息给用户
    this.getOtherProductList();
  },
};
</script>

<style scoped>
/* 商品名称样式 */
.productName{
  font-weight: bolder;
  font-size: 18px;
}
/* 商品价格样式 */
.productPrice{
  color: red;
  font-size: 8px;
}
/* 商品描述样式 */
.productDesc{
  font-style: italic;
  font-size: 8px;
}
</style>