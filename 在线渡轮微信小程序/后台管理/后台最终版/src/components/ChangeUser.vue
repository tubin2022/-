<template>
	<div style=" height: 100%;">
		<div class="top">
			更改用户
		</div>
		<div style="text-align: center;margin: 0 auto;">
			<el-card class="center" align="center" style="width: 820px;margin: auto">
				<el-table ref="multipleTable" :data="tableData.data" stripe :default-sort="{prop:'date',order:'descsexing'}"
					tooltip-effect="dark" style="width: 100%;text-align: center;margin: auto;"
					:header-cell-style="{'text-align':'center'}" 
					:cell-style="{'text-align':'center'}"
					@selection-change="handleSelectionChange">
					<el-table-column sortable label="UID" width="80" prop="uid">
						<template slot-scope="scope">
							<span>
								{{ scope.row.uid }}
							</span>
						</template>
								
					</el-table-column>
					<el-table-column prop="uname" label="姓名" width="90">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.uname }}
							</span>
							<el-input v-model="user.uname" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="gender" label="性别" width="90px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.gender }}
							</span>
							<el-input v-model="user.gender" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="username" label="电话号码" width="130px">
						<template slot-scope="scope">
							<span>
								{{ scope.row.username }}
							</span>
						</template>
					</el-table-column>
					<el-table-column prop="email" label="邮箱" width="180px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.email }}
							</span>
							<el-input v-model="user.email" style="width: 160px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="userface" label="用户头像" width="120px">
						<template slot-scope="scope">
							<!-- <img :src="require('{{ scope.row.userface }}')"> -->
							<el-popover placement="top-start" title="" trigger="hover" v-if="!scope.row.visible">
							      <img :src="scope.row.userface" alt="" style="width: 200px;height: 200px">
							      <el-avatar slot="reference" :src="scope.row.userface" style="width: 70px;height: 70px" shape="circle"
							      fit="cover">
							      </el-avatar>
							    </el-popover>
							
							<el-upload style="margin: auto;width: 90px;" class="avatar-uploader" action="http://localhost:8888/file/upload"
								:show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" v-if="scope.row.visible">
								<img v-if="imageUrl" :src="user.userface" class="avatar">
								<i v-else class="el-icon-plus avatar-uploader-icon"></i>
							</el-upload>
						</template>
					</el-table-column>
					<el-table-column label="操作" width="85" fixed="right">
						<template slot-scope="scope">
							<el-button type="success" v-if="scope.row.visible" size="mini"
								@click="updataSave(scope.$index, scope.row)">确定</el-button>
							<el-button size="mini" v-if="!scope.row.visible" @click="handleEdit(scope.$index, scope.row)">编辑
							</el-button>
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
	import qs from 'qs';
	export default {
		data() {
			return {
				visible: false,
				tableData: [],
				current_page_data: [],
				current_page: 1,
				page_size: 3,
				multipleSelection: [],
				ids: [],
				totalCount:1,
				imageUrl:'',
				user:{
					id:"",
					uname:"",
					gender:"",
					email:"",
					userface:""
				}
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
						for (var i = 0; i < _this.tableData.data.length; i++) {
							_this.tableData.data[i].visible = false
						}
						_this.totalCount=response.data.data.total;
						console.log(_this.tableData);
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			handleAvatarSuccess(res, file) {
				this.imageUrl = URL.createObjectURL(file.raw);
				// console.log(imageUrl)
				console.log(res.data.url)
				this.user.userface = res.data.url
				console.log(this.user.userface)
			},
			beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isLt2M = file.size / 1024 / 1024 < 2;
			
				if (!isJPG) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 2MB!');
				}
				return isJPG && isLt2M;
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
			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.multipleTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.multipleTable.clearSelection();
				}
			},
			handleEdit(index, val) {
				// console.log(index);
				// console.log(val);
				// console.log(val.visible);
				// val.visible = !val.visible;
			
				// console.log(this.tableData.data[index]);
				this.tableData.data[index].visible = !this.tableData.data[index].visible
				this.user.id = this.tableData.data[index].uid
				this.user.uname = this.tableData.data[index].uname
				this.user.gender = this.tableData.data[index].gender
				this.user.username = this.tableData.data[index].username
				this.user.email = this.tableData.data[index].email
				this.user.userface = this.tableData.data[index].userface
				// console.log(this.user);
				this.$forceUpdate()
			
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			updataSave(index, val) {
				// console.log(index);
				// console.log(val);
				// console.log(val.visible);
				// val.visible = !val.visible;
				// console.log(this.tableData.data[index]);
				this.tableData.data[index].visible = !this.tableData.data[index].visible
				let _this = this
				_this.user.id = this.tableData.data[index].uid
				this.myAxios.post('http://localhost:8888/user/modifyInfo',
					 qs.stringify(_this.user))
					.then(function(response) {
						// console.log("1")
						console.log(_this.user.uname)
						if (response.data.code == "1000") {
							_this.init(1, _this.page_size);
							// console.log(2222);
						}
					})
					.catch(function(error) {
						console.log(error);
					});
				this.$forceUpdate()
			
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
