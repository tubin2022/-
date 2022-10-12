<template>
  <!-- 顶部导航栏 -->
  <div>
    <van-nav-bar
      placeholder
      fixed
      title="全部"
      left-arrow
      @click-left="backClick"
    />
    <!-- 商品列表 -->
    <div class="productContent">
      <!-- 左部 -->
      <div class="left">
        <van-sidebar v-model="activeKey" @change="onChange">
          <van-sidebar-item :title="category.name"  v-for="category in categoryArr" :key="category.id"/>
        </van-sidebar>
      </div>
      <!-- 右部 -->
      <div class="right">
        <van-card
          v-for="product in productList"
          :key="product.id"
          :price="product.price"
          :desc="product.description"
          :title="product.name"
          :thumb="product.photo"
          @click="showProductDetail(product)"
        />
      </div>
      <!-- 点击每个商品,展示详情 -->
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
  </div>
</template>

<script>
import { get } from '@/http/axios';
import { getToken } from '@/http/tokenUtils';
import { Toast } from 'vant';
import { ImagePreview } from 'vant';
export default {
  data() {
    return {
      activeKey: 0,
      categoryArr:[],
      productList:[],
      // 控制商品详情动作面板
      showActionSheet: false,
      // 点击的商品对象
      clickProduct: {},
    };
  },
  //生命周期钩子函数,当页面创建时会自动执行
  created() {
    //   进入页面,判断是否登录
    let token = getToken();
    if(token = null){
        Toast.fail('您还没登录');
        this.$router.push('/login');
    }else{
        // 自动加载左侧所有标签,和第一个标签下的产品
        this.getCategory();
    }
  },

  methods: {
    //顶部返回按钮
    backClick() {
      this.$router.go(-1);
    },
    //左侧商品导航栏发生改变触发事件
    onChange(index) {
      this.getProductListByCategory();
    },
    // 获取产品分类信息(左部侧边栏)
    async getCategory(){
        let params ={
            page:1,
            pageSize:100
        }
        let res = await get('/productCategory/pageQuery',params);
        this.categoryArr = res.data.data.list;
        // 还要加载第一个标签的产品
        let params2 ={
            page:1,
            pageSize:30,
            productCategoryId:this.categoryArr[0].id
        }
        let res2 = await get('/product/pageQuery',params2);
        this.productList = res2.data.data.list;
    },
    // 加载左部对应标签的产品
    async getProductListByCategory(){
        let params ={
            page:1,
            pageSize:30,
            productCategoryId:this.categoryArr[this.activeKey].id
        }
        let res = await get('/product/pageQuery',params);
        this.productList = res.data.data.list;
    },
    // 点击每个商品,展示详情
    showProductDetail(item){
      this.showActionSheet = true;
      this.clickProduct = item;
    },
    // 预览商品详情中的图片
    privewImg(){
      ImagePreview([this.clickProduct.photo]);
    }
  },

};
</script>

<style scoped>
.productContent {
  display: flex;
  height: calc(100vh - 100px);
}
.productContent .left {
  width: 20%;
  resize: horizontal;
  overflow: auto;
}
.productContent .right {
  width: 80%;
  overflow-y: auto;
}
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