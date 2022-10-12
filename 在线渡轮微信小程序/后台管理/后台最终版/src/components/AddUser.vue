<template>
	<div style=" height: 100%;">
		<div class="top">
			添加用户
		</div>
		<div style="text-align: center;margin: 0 auto;">
			<el-card class="center" align="center" style="margin: auto;width: 820px;">
				<el-button type='success' @click="dialogVisible = true">添加用户</el-button>
				<br>
				<el-table ref="multipleTable" :data="tableData.data" stripe
					:default-sort="{prop:'date',order:'descsexing'}" tooltip-effect="dark"
					style="width: 100%;text-align: center;margin: auto;" @selection-change="handleSelectionChange"
					:header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
					<el-table-column sortable label="UID" width="90" prop="uid">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.uid }}
							</span>
							<el-input v-model="scope.row.uid" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="uname" label="姓名" sortable width="90">
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
					<el-table-column prop="email" label="邮箱" width="180px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.email }}
							</span>
							<el-input v-model="scope.row.email" style="width: 160px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="userface" label="用户头像" width="190px">
						<template slot-scope="scope">
							<!-- <img :src="require('{{ scope.row.userface }}')"> -->
							<el-popover placement="top-start" title="" trigger="hover">
								<img :src="scope.row.userface" alt="" style="width: 200px;height: 200px">
								<el-avatar slot="reference" :src="scope.row.userface" style="width: 70px;height: 70px"
									shape="circle" fit="cover">
								</el-avatar>
							</el-popover>
							<el-input v-model="scope.row.userface" style="width: 190px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
				</el-table>

				<el-dialog title="添加用户" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
					请输入用户姓名：
					<el-input type="text" placeholder="用户姓名" v-model="user.uname" style="width:200px;" clearable>
					</el-input>&nbsp;<br><br>
					请输入用户性别：
					<el-select v-model="user.gender" clearable placeholder="请选择性别" style="width: 200px;">
					    <el-option
					      v-for="item in options"
						  :key="item.value"
					      :label="item.label"
					      :value="item.value"
						  >
					    </el-option>
					  </el-select>&nbsp;<br><br>
					请输入电话号码：
					<el-input type="text" placeholder="电话号码" v-model="user.phone" style="width: 200px;" clearable>
					</el-input>&nbsp;<br><br>
					请输入用户密码：
					<el-input type="text" placeholder="用户密码" v-model="user.password" style="width: 200px;" clearable>
					</el-input>&nbsp;<br><br>
					请输入用户邮箱：
					<el-input type="text" placeholder="邮箱" v-model="user.email" style="width: 200px;" clearable>
					</el-input>&nbsp;<br><br>
					<span style="color: #000000;">头像</span>
					<el-upload style="margin: auto;width: 60px;" class="avatar-uploader"
						action="http://localhost:8888/file/upload" :show-file-list="false"
						:on-success="handleAvatarSuccess">
						<img v-if="imageUrl" :src="imageUrl" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload><br>
					<span  style="text-align: center;">
						<el-button @click="dialogVisible = false">取 消</el-button>
						<el-button type="primary" @click="addUser">确 定</el-button>
					</span>
				</el-dialog>
				<div style="text-align: center;margin-top: 20px;">
					<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
						:current-page="current_page" :page-sizes="[4,5,6,7]" :page-size.sync="page_size"
						layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
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
				imageUrl: '',
				visible: false,
				tableData: [],
				dialogVisible: false,
				current_page_data: [],
				current_page: 1,
				page_size: 5,
				multipleSelection: [],
				ids: [],
				totalCount: 1,
				options: [{
				          value: "男",
				          label: '男'
				        }, {
				          value: "女",
				          label: '女'
				        }],
				user: {
					uname: "",
					gender: "",
					phone: "",
					password: "",
					email: "",
					userface: "",
				}
			}
		},
		methods: {
			init(n1, n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page = n1,
					_this.page_size = n2,
					// 为给定 ID 的 user 创建请求
					this.myAxios.get("http://localhost:8888/user/findUser?pageNo=" + _this.current_page + "&pageSize=" +
						_this.page_size)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						_this.totalCount = response.data.data.total;
						console.log(_this.tableData);
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
					
			},
			handleClose(done) {
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
						this.user.uname = "";
						this.user.gender = "";
						this.user.phone = "";
						this.user.password = "";
						this.user.email = "";
						this.user.userface = "";
						this.imageUrl = "";
					})
					.catch(_ => {});
			},
			handleSizeChange(val) {
				// 改变每页显示的条数 
				this.page_size = val
				// 注意：在改变每页显示的条数时，要将页码显示到第一页
				this.current_page = 1
				// console.log(this.page_size);
				this.init(1, val)
			},
			// 显示第几页
			handleCurrentChange(val) {
				// 改变默认的页数
				this.current_page = val;
				this.init(this.current_page, this.page_size);
			},
			add() {
				this.visible = !this.visible;
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
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			async addUser() {
				if (this.user.uname == "" ||
					this.user.gender == "" ||
					this.user.phone == "" ||
					this.user.password == "" ||
					this.user.email == "" ||
					this.user.userface == "")
				{
					if(this.user.uname == "" &&
					this.user.gender == "" &&
					this.user.phone == "" &&
					this.user.password == "" &&
					this.user.email == "" &&
					this.user.userface == ""){
					this.$alert("请输入用户信息", {
						confirmButtonText: '确定',
						type: 'warning',
					});
					}
					else{
						this.$alert("请输入完整用户信息", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				}
				if(this.user.uname != "" &&
					this.user.gender != "" &&
					this.user.phone != "" &&
					this.user.password != "" &&
					this.user.email != "" &&
					this.user.userface != ""){
					let _this = this
				console.log(this.user.phone)
				// 
				await this.myAxios.post('http://localhost:8888/user/registe',
						qs.stringify(_this.user))
					.then(function(response) {
						_this.tableData.code = response.data.code;
						_this.tableData.msg = response.data.msg;
						console.log(response);
						if (response.data == "success") {
							_this.init();
						}
					})
					.catch(function(error) {
						console.log(error);
					});
				console.log(this.tableData.code)
				if (this.tableData.code == 1000) {
					this.$alert("添加成功", {
						confirmButtonText: '确定',
						type: 'success',
					});
					this.user.uname = "";
					this.user.gender = "";
					this.user.phone = "";
					this.user.password = "";
					this.user.email = "";
					this.user.userface = "";
					this.imageUrl = "";
					this.dialogVisible = false
				}
				if (this.tableData.code == 1003) {

					this.$alert("手机号存在", {
						confirmButtonText: '确定',
						type: 'warning',
					});

				}
				if (this.tableData.code == 4000) {
					this.$alert(this.tableData.msg, {
						confirmButtonText: '确定',
						type: 'warning',
					});
				}
				if (this.tableData.code == 4001) {
					this.$alert(this.tableData.msg, {
						confirmButtonText: '确定',
						type: 'warning',
					});
				}
				
				}
				
			},
		},
		created() {

			// this.onloads();
			this.init(this.current_page, this.page_size)
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

	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 78px;
		height: 78px;
		line-height: 78px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
