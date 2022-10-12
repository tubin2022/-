<template>
	<div style=" height: 100%;">
		<div class="top">
			查询用户
		</div>
		<div style="text-align: center;margin: 0 auto;">
			<el-card class="center" align="center" style="margin: auto;width: 820px;">
				<el-input
				  placeholder="请输入用户邮箱"
				  v-model="input4"
				  clearable
				  style="width: 300px;"
				  v-if="visible4">
				</el-input>
				<el-input
				  placeholder="请输入用户手机号"
				  v-model="input3"
				  clearable
				  style="width: 300px;"
				  v-if="visible3">
				</el-input>
				<el-input
				  placeholder="请输入用户ID"
				  v-model="input1"
				  clearable
				  style="width: 300px;"
				  v-if="visible1">
				</el-input>
				<el-input
				  placeholder="请输入用户姓名"
				  v-model="input2"
				  clearable
				  style="width: 300px;"
				  v-if="visible2">
				</el-input>&nbsp;&nbsp;&nbsp;
				<el-button  type="primary" icon="el-icon-search" @click="queryUser">查询</el-button><br>
				<el-dropdown  style="width: 200px;">
				  <span class="el-dropdown-link">
				    查询条件<i class="el-icon-arrow-down el-icon--right"></i>
				  </span>
				  <el-dropdown-menu slot="dropdown">&nbsp;
					<el-radio-group v-model="radio" @change="change">
					      <el-radio-button :label="1">UID</el-radio-button>
					      <el-radio-button :label="2">姓名</el-radio-button>
					      <el-radio-button :label="3">手机号</el-radio-button>
					      <el-radio-button :label="4">邮箱</el-radio-button>
					    </el-radio-group>&nbsp;&nbsp;
				  </el-dropdown-menu>
				</el-dropdown>
				
				<el-table ref="multipleTable" :data="tableData.data" stripe :default-sort="{prop:'date',order:'descsexing'}"
					tooltip-effect="dark" style="width: 100%;text-align: center;margin: auto;"
					@selection-change="handleSelectionChange"
					:header-cell-style="{'text-align':'center'}"
					    :cell-style="{'text-align':'center'}">
					<el-table-column sortable label="UID" width="90" prop="uid">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.uid }}
							</span>
							<el-input v-model="scope.row.uid" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
								
					</el-table-column>
					<el-table-column prop="uname" label="姓名" width="90">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.uname }}
							</span>
							<el-input v-model="scope.row.uname" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="gender" label="性别" width="90px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.gender }}
							</span>
							<el-input v-model="scope.row.gender" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="username" label="电话号码" width="140px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.username }}
							</span>
							<el-input v-model="scope.row.username" style="width: 130px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="email" label="邮箱" width="170px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.email }}
							</span>
							<el-input v-model="scope.row.email" style="width: 160px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="userface" label="用户头像" width="200px">
						<template slot-scope="scope">
							<!-- <img :src="require('{{ scope.row.userface }}')"> -->
							<el-popover placement="top-start" title="" trigger="hover">
							      <el-avatar :src="scope.row.userface" alt="" style="width: 200px;height: 200px" shape="circle">
								  </el-avatar>
							      <el-avatar slot="reference" :src="scope.row.userface" style="width: 70px;height: 70px" shape="circle"
							      fit="cover">
							      </el-avatar>
							    </el-popover>
							<el-input v-model="scope.row.userface" style="width: 190px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
				</el-table>
				<div style="text-align: center;margin-top: 20px;">
					<el-pagination @size-change="handleSizeChange"
					                          @current-change="handleCurrentChange" 
					                          :current-page="current_page" 
					                          :page-sizes="[2,3,4,5]" 
					                          :page-size.sync="page_size" layout="total, sizes, prev, pager, next, jumper" 
					                          :total="totalCount">
					</el-pagination>
				</div>
			</el-card>
		</div>
		
	</div>
</template>

<script>
	export default {
		data() {
			return {
				visible: false,
				visible1:true,
				visible2:false,
				visible3:false,
				visible4:false,
				tableData: [],
				current_page_data: [],
				current_page: 1,
				page_size: 3,
				multipleSelection: [],
				ids: [],
				totalCount:1,
				radio:1,
				input1:'',
				input2:'',
				input3:'',
				input4:'',
			}
		},
		methods: {
			init(n1,n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page=n1,
				_this.page_size=n2,
				// 为给定 ID 的 user 创建请求
				this.myAxios.get("http://localhost:8888/user/findUser?pageNo="+_this.current_page+"&pageSize="+_this.page_size)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						_this.totalCount=response.data.data.total;
						console.log(_this.tableData);
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			handleSizeChange(val) {
			           // 改变每页显示的条数 
			           this.page_size=val
			           // 注意：在改变每页显示的条数时，要将页码显示到第一页
			           this.current_page=1
					   // console.log(this.page_size);
					   this.init(1,val)
			       },
			         // 显示第几页
			       handleCurrentChange(val) {
			           // 改变默认的页数
			           this.current_page=val;
					   this.init(this.current_page,this.page_size);
			       },
				   change(){
					 // console.log(this.radio); 
					  if(this.radio==1){
						  this.visible1=true;
						  this.visible2=false;
						  this.visible3=false;
						  this.visible4=false;
					  }else if(this.radio==2){
						  this.visible1=false;
						  this.visible2=true;
						  this.visible3=false;
						  this.visible4=false;
					  }else if(this.radio==3){
						  this.visible1=false;
						  this.visible2=false;
						  this.visible3=true;
						  this.visible4=false;
					  }else if(this.radio==4){
						  this.visible1=false;
						  this.visible2=false;
						  this.visible3=false;
						  this.visible4=true;
					  }
				   },
			queryUser() {
				// console.log(this.radio); 
				let qurryn="";
				let qurryq="";
				let typei = 0;
				if(this.radio==1){
					qurryn="&uid=";
					qurryq=this.input1;
					if(qurryq=="")
					{
						this.$alert("请输入用户ID", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				}else if(this.radio==2){
					qurryn="&uname=";
					qurryq=this.input2
					if(qurryq=="")
					{
						this.$alert("请输入用户姓名", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				}else if(this.radio==3){
					qurryn="&phone=";
					qurryq=this.input3
					if(qurryq=="")
					{
						this.$alert("请输入用户手机号", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				}else if(this.radio==4){
					qurryn="&email=";
					qurryq=this.input4
					if(qurryq=="")
					{
						this.$alert("请输入用户邮箱", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				}
				let _this = this; //把vue对象 先保存到第三方变量中
				// 为给定 ID 的 user 创建请求
				this.myAxios.get("http://localhost:8888/user/findUser?pageNo="+"1"+"&pageSize="+"3"+qurryn+qurryq)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						_this.totalCount=response.data.data.total;
						console.log("总数"+_this.totalCount)
						console.log(_this.tableData);
						if(_this.totalCount==0)
						{						
							_this.$alert("未查询到该用户", {
								confirmButtonText: '确定',
								type: 'warning',
							});
							
						}
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
					this.$forceUpdate()
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;

			}
		},
		created() {
			 this.init(this.current_page,this.page_size)
		}
	}
</script>

<style>
	.top {
		/* margin-top: 10px; */
		margin: auto;
		font-size: 34px;
		color: rgb(154, 217, 217);
		font-weight: bold;
		text-align: center;
		height: 50px;
		/* background-color: rgb(253, 255, 254); */
	}
</style>
