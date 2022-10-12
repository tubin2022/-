<template>
	<div style=" height: 100%;">
		<div class="top">
			反馈信息
		</div>
		<el-card style="width: 81%;margin: auto;">
			<div style="text-align: center;">
				<el-input type="text" placeholder="反馈用户ID" v-model="queryFeedback.uid" style="width:120px;" clearable>
			</el-input>&nbsp;
			
			<el-date-picker v-model="queryFeedback.fdate" type="datetime" placeholder="反馈时间"
				style="width: 200px" value-format="yyyy-MM-dd HH:mm:ss">
			</el-date-picker>&nbsp;
			<el-input type="text" placeholder="反馈信息" v-model="queryFeedback.context" style="width:120px;" clearable>
			</el-input>&nbsp;
			<el-button type="primary" icon="el-icon-search" @click="searchFeedback">查询</el-button>
			<el-button type="danger" icon="el-icon-delete" @click="deleteFeedback">删除</el-button>
			</div>
			<el-table ref="multipleTable" :data="tableData.data" stripe :default-sort="{prop:'date',order:'descending'}"
				tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange"
				:header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
				<el-table-column sortable label="反馈用户ID" width="150" prop="uid" >
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.uid }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="fid" label="信息编号" width="90px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.fid }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="fdate" label="反馈时间" width="260px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.fdate | dateFormat('YYYY-MM-DD HH:mm:ss')}}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="context" label="反馈信息" width="100px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.context}}
						</span>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="200" fixed="right">
					<template slot-scope="scope">
						<el-button size="mini" type="danger" @click="delFeedback(scope.row.fid)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="text-align: center;margin-top: 20px;">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="current_page" :page-sizes="[4,5,6,7]" :page-size.sync="page_size"
					layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
				</el-pagination>
			</div>
		</el-card>
	</div>
</template>

<script>
	import qs from 'qs';
	export default {
		name: "dataList",
		data() {
			return {
				visible: false,
				tableData1: [],
				tableData: [],
				multipleSelection: [],
				current_page_data: [],
				current_page: 1,
				page_size: 5,
				ids: [],
				msg:'',
				totalCount: 1,
				queryFeedback:{
					uid:"",
					fdate:"",
					context:""
				}
			}
		},
		methods: {
			init(n1, n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page = n1,
					_this.page_size = n2,
					// 为给定 ID 的 user 创建请求
					this.myAxios.get("http://localhost:8888/feedback/pageQuery?pageNo=" + _this.current_page + "&pageSize=" +
						_this.page_size)
					.then(function(response) {
						// console.log(response);
						// console.log(_this.page_size);
						_this.tableData = response.data.data;

						for (var i = 0; i < _this.tableData.data.length; i++) {
							_this.tableData.data[i].visible = false
						}
						_this.totalCount = response.data.data.total;
						console.log(_this.tableData);
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});


			},
			handleSizeChange(val) {
				// 改变每页显示的条数 
				this.page_size = val
				// 注意：在改变每页显示的条数时，要将页码显示到第一页
				this.current_page = 1
				// console.log(this.page_size);
				this.init(1, val)
			},
			searchFeedback(){
				let typei=0;
				let qurry ="http://localhost:8888/feedback/pageQuery?"+"pageNo="+this.current_page+"&pageSize="+this.page_size;
				if(this.queryFeedback.context!=""){
					qurry+="&context="+this.queryFeedback.context;
					typei = 1;
					console.log("sb1");
				}
				if(this.queryFeedback.uid!="" ){
					qurry+="&uid="+this.queryFeedback.uid;
					typei = 1;
					console.log("sb2");
				}
				if(this.queryFeedback.fdate!="" &&this.queryFeedback.fdate!=undefined){
					qurry+="&fdate="+this.queryFeedback.fdate;
					typei = 1;
					console.log("sb3");
				}
				console.log(this.queryFeedback.context);
				console.log(this.queryFeedback.uid);
				console.log(this.queryFeedback.fdate);
				
				console.log("typei="+typei);
				if(typei==0){
					this.$alert("请输入查询条件", {
						confirmButtonText: '确定',
						type: 'warning',
					});
					typei =0;
				}
				let _this = this; //把vue对象 先保存到第三方变量中
				// 为给定 ID 的 user 创建请求
				this.myAxios.get(qurry)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						_this.totalCount=response.data.data.total;
						if(_this.totalCount==0)
						{						
							_this.$alert("未查询到该反馈信息", {
								confirmButtonText: '确定',
								type: 'warning',
							});	
						}
						console.log(_this.totalCount)
						console.log(_this.tableData);
						// if (response.data.code == "1000") {
						// 	_this.init(_this.current_page, _this.page_size);
						// 	// console.log(2222);
						// }
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
					
					this.$forceUpdate()
			},
			deleteFeedback(){
				let typei=0;
				let qurry ="http://localhost:8888/feedback/deleteByCondition?";
				if(this.queryFeedback.context!=""){
					qurry+="&context="+this.queryFeedback.context;
					typei = 1;
				}
				if(this.queryFeedback.uid!="" ){
					qurry+="&uid="+this.queryFeedback.uid;
					typei = 1;
				}
				if(this.queryFeedback.fdate!="" &&this.queryFeedback.fdate!=undefined){
					qurry+="&fdate="+this.queryFeedback.fdate;
					typei = 1;
				}
				console.log(this.queryFeedback.context);
				console.log(this.queryFeedback.uid);
				console.log(this.queryFeedback.fdate);
				console.log("typei="+typei);
				if(typei==0){
					this.$alert("请输入删除条件", {
						confirmButtonText: '确定',
						type: 'warning',
					});
					typei =0;
				}
				let _this = this; //把vue对象 先保存到第三方变量中
				// 为给定 ID 的 user 创建请求
				_this.myAxios.post(qurry)
					.then(function(response) {
						console.log(response);
						_this.$alert("删除成功", {
							confirmButtonText: '确定',
							type: 'success',
						});
						typei =0;
						// if (response.data.code == "1000") {
						// 	_this.init(_this.current_page, _this.page_size);
						// 	// console.log(2222);
						// }
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
					
					_this.$forceUpdate();
					this.init(this.current_page, this.page_size);
			},
			// 显示第几页
			handleCurrentChange(val) {
				// 改变默认的页数
				this.current_page = val;
				this.init(this.current_page, this.page_size);
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
			handleSelectionChange(val) {
				this.multipleSelection = val;
				console.log(this.multipleSelection.tableData)
				
			},
			handleEdit(index, val) {
				// console.log(index);
				// console.log(val);
				// console.log(val.visible);
				// val.visible = !val.visible;

				// console.log(this.tableData.data[index]);
				this.tableData.data[index].visible = !this.tableData.data[index].visible
				this.basicUpdata.sid = this.tableData.data[index].sid
				this.basicUpdata.startCity = this.tableData.data[index].startCity
				this.basicUpdata.arriveCity = this.tableData.data[index].endCity
				this.basicUpdata.startTime = this.tableData.data[index].startTime
				this.basicUpdata.arriveTime = this.tableData.data[index].endTime
				// console.log(this.basicUpdata);
				this.$forceUpdate()

			},
			handleBatchDelete() {
				if (this.multipleSelection.length) {
					for (let i = 0; i < this.multipleSelection.length; i++) {
						this.ids[i] = this.multipleSelection[i].sid;
					}
				} else {
					this.$alert("请选择数据", "提示", {
						confirmButtonText: '确定',
						type: 'info',
					});
				}
				for(let i = 0; i < this.multipleSelection.length; i++){
				    console.log(this.ids[i]);
					let _this = this
					// console.log(sid)
					this.myAxios.post('http://localhost:8888/shift/deleteShift?sid='+this.ids[i])
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
				}
			},
			delFeedback(fid) {
				let _this = this
				// console.log(sid)
				this.myAxios.post('http://localhost:8888/feedback/deleteById?fid='+fid)
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
			},
			handleDelete(index, val) {
				this.ids[0] = val.id;
				this.delete();
			}
			
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
</style>
