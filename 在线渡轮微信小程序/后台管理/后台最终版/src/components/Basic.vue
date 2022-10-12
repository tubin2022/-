<template>
	<div style=" height: 100%;">
		<div class="top">
			班次基础信息
		</div>
		<el-card>
			<div style="text-align: center;">
				<el-input type="text" placeholder="出发城市" v-model="queryShift.startCity" style="width:120px;" clearable>
				</el-input>&nbsp;
				<el-input type="text" placeholder="到达城市" v-model="queryShift.endCity" style="width:120px;" clearable>
				</el-input>&nbsp;
				<el-date-picker v-model="queryShift.startTime" type="date" placeholder="出发时间" style="width: 200px"
					value-format="yyyy-MM-dd">
				</el-date-picker>&nbsp;
				<el-date-picker v-model="queryShift.endTime" type="date" placeholder="到达时间" style="width: 200px"
					value-format="yyyy-MM-dd">
				</el-date-picker>&nbsp;
				<el-button type="primary" icon="el-icon-search" @click="searchShift">查询</el-button>
				<!-- </div>
			<div style="text-align: center;"> -->
				<el-button @click="dialogVisible = true">添加班次</el-button>
				</el-button>
			</div><br>
			<el-dialog title="添加渡轮班次" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
				请输入出发城市：
				<el-input type="text" placeholder="出发城市" v-model="shift.startCity" style="width:200px;" clearable>
				</el-input>&nbsp;<br><br>
				请输入到达城市：
				<el-input type="text" placeholder="到达城市" v-model="shift.endCity" style="width:200px;" clearable>
				</el-input>&nbsp;<br><br>
				请输入出发时间：
				<el-date-picker v-model="shift.startTime" type="datetime" placeholder="出发时间" style="width: 200px"
					value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>&nbsp;<br><br>
				请输入到达时间：
				<el-date-picker v-model="shift.endTime" type="datetime" placeholder="到达时间" style="width: 200px"
					value-format="yyyy-MM-dd HH:mm:ss">
				</el-date-picker>&nbsp;<br>
				<span slot="footer" class="dialog-footer" style="text-align: center;">
					<el-button @click="dialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="addShift">确 定</el-button>
				</span>
			</el-dialog>
			<el-table ref="multipleTable" :data="tableData.data" stripe :default-sort="{prop:'date',order:'descending'}"
				tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange"
				:header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">

				<el-table-column fixed="left" type="selection" width="55">
				</el-table-column>
				<el-table-column sortable label="班次" width="90" prop="sid">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.sid }}
						</span>
						<el-input v-model="basicUpdata.sid" style="width: 80px;" v-if="scope.row.visible">
						</el-input>
					</template>

				</el-table-column>
				<el-table-column prop="startCity" label="出发城市" width="100px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.startCity }}
						</span>
						<el-input v-model="basicUpdata.startCity" style="width: 80px;" v-if="scope.row.visible">
						</el-input>
					</template>
				</el-table-column>
				<el-table-column prop="endCity" label="到达城市" width="100px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.endCity }}
						</span>
						<el-input v-model="basicUpdata.arriveCity" style="width: 80px;" v-if="scope.row.visible">
						</el-input>
					</template>
				</el-table-column>
				<el-table-column prop="startTime" label="出发时间" width="250px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.startTime | dateFormat('YYYY-MM-DD HH:mm:ss')}}
						</span>
						<el-date-picker v-model="basicUpdata.startTime" type="datetime" placeholder="选择日期时间"
							style="width: 85%;" value-format="yyyy-MM-dd HH:mm:ss" v-if="scope.row.visible">
						</el-date-picker>
					</template>
				</el-table-column>
				<el-table-column prop="endTime" label="到达时间" width="250px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.endTime | dateFormat('YYYY-MM-DD HH:mm:ss')}}
						</span>
						<el-date-picker v-model="basicUpdata.arriveTime" type="datetime" placeholder="选择日期时间"
							style="width: 85%;" value-format="yyyy-MM-dd HH:mm:ss" v-if="scope.row.visible">
						</el-date-picker>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="150" fixed="right">
					<template slot-scope="scope">
						<el-button type="success" v-if="scope.row.visible" size="mini"
							@click="updataSave(scope.$index, scope.row)">确定</el-button>
						<el-button size="mini" v-if="!scope.row.visible" @click="handleEdit(scope.$index, scope.row)">编辑
						</el-button>
						<el-button size="mini" type="danger" @click="delShift(scope.row.sid)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="margin-top: 20px;text-align: center;">
				<el-button @click="toggleSelection()">取消选择</el-button>
				<el-button type="warning" @click="handleBatchDelete">批量删除</el-button>
			</div>
			<div style="text-align: center;margin-top: 20px;">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="current_page" :page-sizes="[8,9,10,11]" :page-size.sync="page_size"
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
				dialogVisible: false,
				current_page_data: [],
				current_page: 1,
				page_size: 9,
				ids: [],
				msg: '',
				totalCount: 1,
				basicUpdata: {
					sid: "",
					startCity: "",
					arriveCity: "",
					startTime: "",
					arriveTime: "",
				},
				shift: {
					startCity: "",
					endCity: "",
					startTime: "",
					endTime: ""
				},
				queryShift: {
					startCity: "",
					endCity: "",
					startTime: "",
					endTime: ""
				}
			}
		},
		methods: {
			init(n1, n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page = n1,
					_this.page_size = n2,
					this.myAxios.get("http://localhost:8888/shift/pageQuery?pageNo=" + _this.current_page + "&pageSize=" +
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
			handleClose(done) {
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
						this.shift.endCity = "";
						this.shift.startCity = "";
						this.shift.startTime = "";
						this.shift.endTime = "";
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
			add() {
				this.visible = !this.visible;

			},
			addShift() {
				if (this.shift.endCity == "" ||
					this.shift.startCity == "" ||
					this.shift.startTime == "" ||
					this.shift.endTime == "") {
					if (this.shift.endCity == "" &&
						this.shift.startCity == "" &&
						this.shift.startTime == "" &&
						this.shift.endTime == "") {
						this.$alert("添加数据为空", "提示", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					} else {
						this.$alert("请输入完整数据", "提示", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				} else if (this.shift.endCity != "" &&
					this.shift.startCity != "" &&
					this.shift.startTime != "" &&
					this.shift.endTime != "") {
						if(this.shift.startTime>this.shift.endTime){
							this.$alert("到达时间应晚于出发时间", "提示", {
								confirmButtonText: '确定',
								type: 'warning',
							});
						}
						else
						{
							let _this = this
					this.myAxios.post('http://localhost:8888/shift/addShift',
							qs.stringify(_this.shift))
						.then(function(response) {
							console.log(response);
							if (response.data == "success") {
								_this.init();
							}
							if (response.data.code == "1000") {
								_this.$alert("添加成功", "提示", {
									confirmButtonText: '确定',
									type: 'success',
								});
								_this.shift.endCity = "";
								_this.shift.startCity = "";
								_this.shift.startTime = "";
								_this.shift.endTime = "";
								_this.dialogVisible = false
							}
						})
						.catch(function(error) {
							console.log(error);
						});
					this.$forceUpdate()
						}
					

				}

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
				for (let i = 0; i < this.multipleSelection.length; i++) {
					console.log(this.ids[i]);
					let _this = this
					// console.log(sid)
					this.myAxios.post('http://localhost:8888/shift/deleteShift?sid=' + this.ids[i])
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
			delShift(sid) {
				this.$confirm('是否删除班次为' + sid + "的航班", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let _this = this
					// console.log(sid)
					this.myAxios.post('http://localhost:8888/shift/deleteShift?sid=' + sid)
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
			handleDelete(index, val) {
				this.ids[0] = val.id;
				this.delete();
			},
			updataSave(index, val) {
				// console.log(index);
				// console.log(val);
				// console.log(val.visible);
				// val.visible = !val.visible;

				// console.log(this.tableData.data[index]);
				this.tableData.data[index].visible = !this.tableData.data[index].visible
				let _this = this
				this.myAxios.post('http://localhost:8888/shift/updateShift',
						qs.stringify(_this.basicUpdata))
					.then(function(response) {
						// console.log("1")
						// console.log(_this.basicUpdata)
						// console.log(_this.basicUpdata.endTime)
						// console.log(response);
						if (response.data.code == "1000") {
							_this.init(_this.current_page, _this.page_size);
							// console.log(2222);
						}

					})
					.catch(function(error) {
						console.log(error);
					});
				this.$forceUpdate()

			},
			searchShift() {
				console.log(this.queryShift);
				let typei = 0;
				let qurry = "http://localhost:8888/shift/pageQuery?" + "pageNo=" + this.current_page + "&pageSize=" + this
					.page_size;
				if (this.queryShift.startCity != "") {
					qurry += "&startCity=" + this.queryShift.startCity;
					typei = 1;
					console.log("sb1");
				}
				if (this.queryShift.endCity != "") {
					qurry += "&arriveCity=" + this.queryShift.endCity;
					typei = 1;
					console.log("sb2");
				}
				if (this.queryShift.startTime != "" && this.queryShift.startTime != undefined) {
					qurry += "&startTime=" + this.queryShift.startTime;
					typei = 1;
					console.log("sb3");
				}
				if (this.queryShift.endTime != "" && this.queryShift.endTime != undefined) {
					qurry += "&arriveime=" + this.queryShift.endTime;
					typei = 1;
					console.log("sb4");
				}
				console.log(this.queryShift.startCity);
				console.log(this.queryShift.endCity);
				console.log(this.queryShift.startTime);
				console.log(this.queryShift.endTime);

				console.log("typei=" + typei);
				if (typei == 0) {
					this.$alert("请输入查询条件", {
						confirmButtonText: '确定',
						type: 'warning',
					});
					typei = 0;
				}
				let _this = this; //把vue对象 先保存到第三方变量中
				// 为给定 ID 的 user 创建请求
				this.myAxios.get(qurry)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						_this.totalCount = response.data.data.total;
						console.log(_this.totalCount)
						console.log(_this.tableData);
						if (_this.totalCount == 0) {
							_this.$alert("未查询到该班次", {
								confirmButtonText: '确定',
								type: 'warning',
							});

						}
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
