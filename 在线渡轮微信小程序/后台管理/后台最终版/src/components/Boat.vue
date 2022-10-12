<template>
	<div style=" height: 100%;">
		<div class="top">
			渡轮管理
		</div>
		<div style="text-align: center;margin: 0 auto;">
			<el-card class="center" align="center" style="margin: auto;width: 100%;">
					<el-input
				  placeholder="请输入渡轮编号"
				  v-model="boatId"
				  clearable
				  style="width: 300px;">
				</el-input>&nbsp;&nbsp;
				<el-button type="primary" icon="el-icon-search" @click="queryBoat">查询</el-button>
				<el-button  @click="dialogVisible = true">添加渡轮</el-button><br>
				
				<el-dialog
				  title="添加渡轮信息"
				  :visible.sync="dialogVisible"
				  width="40%"
				  :before-close="handleClose">
				  请输入渡船名称：
				  <el-input type="text" placeholder="渡轮名称" v-model="boat.bname" style="width:200px;" clearable>
				  </el-input>&nbsp;<br><br>
					请输入核载人数：
					<el-input type="text" placeholder="核载人数" v-model="boat.maxPeople" style="width:200px;" clearable>
					</el-input>&nbsp;<br><br>
					请选择出厂时间：
					<el-date-picker v-model="boat.produceDate" type="date" placeholder="选择出厂时间"
						style="width: 200px;" value-format="yyyy-MM-dd" clearable>
					</el-date-picker>&nbsp;<br><br>
					请输入渡轮简介：
					<el-input type="textarea" placeholder="渡轮简介" v-model="boat.profile" style="width: 200px;" clearable :autosize="{ minRows: 2, maxRows: 5}">
					</el-input>&nbsp;<br>
					<span style="color: #000000;">渡轮图片</span>
					<el-upload style="margin: auto;width: 100px;" class="avatar-uploader" action="http://localhost:8888/file/upload"
						:show-file-list="false" :on-success="handleAvatarSuccess">
						<img v-if="imageUrl" :src="imageUrl" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload><br>
				  <span style="text-align: center;">
				    <el-button @click="dialogVisible = false">取 消</el-button>
				    <el-button type="primary" @click="addBoat">确 定</el-button>
				  </span>
				</el-dialog>
				<el-table ref="multipleTable" :data="tableData.data" stripe
					:default-sort="{prop:'date',order:'descsexing'}" tooltip-effect="dark"
					style="width: 100%;text-align: center;margin: auto;" @selection-change="handleSelectionChange"
					:header-cell-style="{'text-align':'center'}"
					    :cell-style="{'text-align':'center'}">
					<el-table-column sortable label="渡轮编号" width="110" prop="bid">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.bid }}
							</span>
							<el-input v-model="scope.row.bid" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="bname" label="渡轮名称" sortable width="110">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.bname }}
							</span>
							<el-input v-model="scope.row.bname" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="produceDate" label="出厂时间" width="160px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.produceDate | dateFormat('YYYY-MM-DD')}}
							</span>
							<el-input v-model="scope.row.produceDate" style="width: 80px;" v-if="scope.row.visible">
							</el-input>
						</template>

					</el-table-column>

					<el-table-column prop="maxPeople" label="核载人数" width="100px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.maxPeople }}
							</span>
							<el-input v-model="scope.row.maxPeople" style="width: 120px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					
					<el-table-column prop="bphoto" label="渡轮图片" width="140px">
						<template slot-scope="scope">
							<!-- <img :src="require('{{ scope.row.bphoto }}')"> -->
							<el-popover placement="top-start" title="" trigger="hover">
								<img :src="scope.row.bphoto" alt="" style="width: 200px;height: 200px">
								<el-avatar slot="reference" :src="scope.row.bphoto" style="width: 70px;height: 70px" shape="square"
								fit="cover">
								</el-avatar>
							</el-popover>
							<el-input v-model="scope.row.bphoto" style="width: 190px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column prop="profile" label="渡轮简介" width="300px">
						<template slot-scope="scope">
							<span v-if="!scope.row.visible">
								{{ scope.row.profile }}
							</span>
							<el-input v-model="scope.row.profile" style="width: 160px;" v-if="scope.row.visible">
							</el-input>
						</template>
					</el-table-column>
					<el-table-column label="操作" width="100" fixed="right" style="text-align: center;">
						<template slot-scope="scope">
							<el-button size="mini" type="danger" @click="delBoat(scope.row.bid)">删除</el-button>
						</template>
					</el-table-column>
				</el-table><br>
				<div style="text-align: center;margin-top: 20px;">
					<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
						:current-page="current_page" :page-sizes="[3,4,5,6]" :page-size.sync="page_size"
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
				boatId:'',
				imageUrl: '',
				visible: false,
				tableData: [],
				dialogVisible: false,
				current_page_data: [],
				current_page: 1,
				page_size: 4,
				multipleSelection: [],
				ids: [],
				totalCount: 1,
				user: {
					uname: "",
					gender: "",
					phone: "",
					password:"",
					email: "",
					userface: "",
				},
				boat:{
					bname:"",
					bphoto:"",
					maxPeople:"",
					produceDate:"",
					profile:"",
				}
			}
		},
		methods: {
			init(n1, n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page = n1,
					_this.page_size = n2,
					// 为给定 ID 的 user 创建请求
					this.myAxios.get("http://localhost:8888/boat/pageQuery?pageNo=" + _this.current_page + "&pageSize=" +
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
						this.boat.bname="";
						this.boat.bphoto="";
						this.boat.maxPeople="";
						this.boat.produceDate="";
						this.boat.profile="";
						this.imageUrl="";
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
			queryBoat(){
				let _this = this; //把vue对象 先保存到第三方变量中
				// 为给定 ID 的 user 创建请求
				if(this.boatId == "")
				{
					this.$alert("请输入轮船编号", {
						confirmButtonText: '确定',
						type: 'warning',
					});
					
				}
				this.myAxios.get("http://localhost:8888/boat/pageQuery?bid="+_this.boatId+"&pageNo="+_this.current_page+"&pageSize="+_this.page_size)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						_this.totalCount=response.data.data.total;
						if(_this.totalCount==0)
						{						
							_this.$alert("未查询到该渡轮", {
								confirmButtonText: '确定',
								type: 'warning',
							});	
						}
						console.log("总数"+_this.totalCount)
						console.log(_this.tableData);
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
					this.$forceUpdate()
			},
			add() {
				this.visible = !this.visible;
			},
			delBoat(bid) {
				this.$confirm('是否删除'+bid+"号渡轮", '提示', {
				         confirmButtonText: '确定',
				         cancelButtonText: '取消',
				         type: 'warning'
				       }).then(() => {
											// console.log(this.ids);
											// this.$message({
				       //       type: 'success',
				       //       message: '删除成功!'
					   let _this = this
					   // console.log(sid)
					   this.myAxios.post('http://localhost:8888/boat/deleteById?id='+bid)
					   	.then(function(response) {
					   		// console.log(response);
					   		// console.log(sid);
					   		_this.msg = response.data.msg
					   		// console.log(_this.msg)
					   		alert(_this.msg)
					   		if (response.data.code == "1000") {
					   			_this.init(_this.current_page, _this.page_size);
					   			// console.log(2222);
					   		}
					   	})
					   	.catch(function(error) {
					   		console.log(error);
					   		// console.log(sid);
					   	});
					   	this.$forceUpdate()
				         });
				
			},
			handleAvatarSuccess(res, file) {
				this.imageUrl = URL.createObjectURL(file.raw);
				// console.log(imageUrl)
				console.log(res.data.url)
				this.boat.bphoto = res.data.url
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
			addBoat() {
				let _this = this
					if(this.boat.bname=="" || this.boat.bphoto=="" || this.boat.maxPeople=="" || this.boat.produceDate=="" || this.boat.profile=="")
					{
						if(this.boat.bname=="" && this.boat.bphoto=="" && this.boat.maxPeople=="" && this.boat.produceDate=="" && this.boat.profile=="")
						{
							this.myAxios.post('http://localhost:8888/boat/addBoat',
							qs.stringify(_this.boat))
								.then(function(response) {
									console.log(response.data.code)
									if(response.data.code=="1006"){
										_this.$alert("添加信息为空", {
											confirmButtonText: '确定',
											type: 'warning',
										});
									}
									console.log(response);
									if (response.data == "success") {
										_this.init();
									}
								})
								.catch(function(error) {
									console.log(error);
								});
						}
						else{
							this.$alert("请输入渡轮完整信息", {
						confirmButtonText: '确定',
						type: 'warning',
					});
						}
				}
				else if(this.boat.bname!="" && this.boat.bphoto!="" && this.boat.maxPeople!="" && this.boat.produceDate!="" && this.boat.profile!="")
				{
					this.myAxios.post('http://localhost:8888/boat/addBoat',
					qs.stringify(_this.boat))
						.then(function(response) {
							console.log(response);
							if (response.data == "success") {
								_this.init();
							}
						})
						.catch(function(error) {
							console.log(error);
						});
					this.boat.bname="";
				this.boat.bphoto="";
				this.boat.maxPeople="";
				this.boat.produceDate="";
				this.boat.profile="";
				this.imageUrl="";
				this.$alert("添加成功！", {
					confirmButtonText: '确定',
					type: 'success',
				});
				this.dialogVisible = false}
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
