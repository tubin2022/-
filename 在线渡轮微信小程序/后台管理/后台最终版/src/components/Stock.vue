<template>
	<div style=" height: 100%;">
		<div class="top">
			票务库存信息
		</div>
		<el-card>
			<div style="text-align: center;">
				<el-input type="text" placeholder="始发站" v-model="queryTicket.startCity" style="width:120px;" clearable>
				</el-input>&nbsp;
				<el-input type="text" placeholder="终点站" v-model="queryTicket.endCity" style="width:120px;" clearable>
				</el-input>&nbsp;
				<el-date-picker v-model="queryTicket.startTime" type="date" placeholder="出发时间" style="width: 200px"
					value-format="yyyy-MM-dd">
				</el-date-picker>&nbsp;
				<el-date-picker v-model="queryTicket.endTime" type="date" placeholder="到达时间" style="width: 200px"
					value-format="yyyy-MM-dd">
				</el-date-picker>&nbsp;
				<el-button type="primary" icon="el-icon-search" @click="searchTicket">查询</el-button>
				<el-button @click="dialogVisible = true">添加船票</el-button>
				
			</div><br>
			<el-dialog title="添加渡轮班次" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
				请输入渡轮编号：
				<el-select v-model="ticket.bid" clearable placeholder="请选择渡轮" style="width: 150px;">
					<el-option v-for="item in qc1" :key="item.bid" :label="item.bname" :value="item.bid">
					</el-option>
					<!-- :key="item.boat.bid" -->
				</el-select>&nbsp;<br><br>
				请输入班次编号：
				<el-select v-model="ticket.sid" clearable placeholder="请选择班次编号" style="width: 150px;">
					<el-option v-for="item in qc2" :key="item.sid" :label="item.sid"
						:value="item.sid">
					</el-option>
				</el-select>&nbsp;<br><br>
				请输入船票价格：
				<el-input type="text" placeholder="船票价格" v-model="ticket.price" style="width:150px;" clearable>
				</el-input>&nbsp;<br><br>
				<span slot="footer" class="dialog-footer" style="text-align: center;">
					<el-button @click="dialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="addTicket">确 定</el-button>
				</span>
			</el-dialog>
			<el-table ref="multipleTable" :data="tableData.data" stripe :default-sort="{prop:'date',order:'descending'}"
				tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange"
				:header-cell-style="{'text-align':'center'}" :cell-style="{'text-align':'center'}">
				<el-table-column fixed="left" type="selection" width="55">
				</el-table-column>
				<el-table-column sortable label="班次" width="90" prop="shift.sid">
					<template slot-scope="scope">
						<span>
							{{ scope.row.shift.sid }}
						</span>
					</template>
				</el-table-column>
				<el-table-column sortable label="轮船编号" width="120" prop="boat.bid">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.boat.bid }}
						</span>
						<el-input v-model="stock.bid" style="width: 80px;" v-if="scope.row.visible">
						</el-input>
					</template>
				</el-table-column>
				<el-table-column sortable label="船票编号" width="120" prop="tid">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.tid }}
						</span>
						<el-input v-model="stock.tid" style="width: 80px;" v-if="scope.row.visible">
						</el-input>
					</template>
				</el-table-column>
				<el-table-column prop="shift.startCity" label="始发站" sortable width="90px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.shift.startCity }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="shift.endCity" label="终点站" width="90px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.shift.endCity }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="price" label="票价" width="90px">
					<template slot-scope="scope">
						<span v-if="!scope.row.visible">
							{{ scope.row.price }}
						</span>
						<el-input v-model="stock.price" style="width: 80px;" v-if="scope.row.visible">
						</el-input>
					</template>
				</el-table-column>
				<el-table-column prop="shift.startTime" label="出发时间" width="290px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.shift.startTime | dateFormat('YYYY-MM-DD HH:mm')}}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="shift.endTime" label="到达时间" width="290px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.shift.endTime | dateFormat('YYYY-MM-DD HH:mm')}}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="total" label="核载人数" width="90px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.total }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="surplus" label="余票" width="90px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.surplus }}
						</span>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="150" fixed="right">
					<template slot-scope="scope">
						<el-button type="success" v-if="scope.row.visible" size="mini"
							@click="updataSave(scope.$index, scope.row)">保存</el-button>
						<el-button size="mini" v-if="!scope.row.visible" @click="handleEdit(scope.$index, scope.row)">编辑
						</el-button>
						<el-button size="mini" type="danger" @click="delStock(scope.row.tid)">删除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div style="margin-top: 20px;text-align: center;">
				<el-button @click="toggleSelection()">取消选择</el-button>
				<el-button type="warning" @click="handleBatchDelete">批量删除</el-button>
			</div>
			<div style="text-align: center;margin-top: 20px;">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="current_page" :page-sizes="[3,4,5,6]" :page-size.sync="page_size"
					layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
				</el-pagination>
			</div>
		</el-card>
	</div>
</template>

<script>
	import qs from 'qs';
	export default {
		data() {
			return {
				visible: false,
				tableData: [],
				data: [],
				dialogVisible: false,
				current_page_data: [],
				current_page: 1,
				page_size: 4,
				multipleSelection: [],
				ids: [],
				totalCount: 1,
				totalCount1:1,
				totalCount2:1,
				options1: [],
				options2:[],
				qc1: [],
				qc2: [],
				stock: {
					tid: '',
					bid: '',
					price: ''
				},
				ticket: {
					bid: "",
					sid: "",
					price: ""
				},
				queryTicket: {
					startCity: "",
					endCity: "",
					startTime: "",
					endTime: ""
				}
			}
		},
		methods: {
			async init(n1, n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page = n1,
					_this.page_size = n2,
					await this.myAxios.get("http://localhost:8888/ticket/pageQueryTicket?pageNo=" + _this
						.current_page + "&pageSize=" + _this.page_size)
					.then(function(response) {
						console.log(response);
						console.log(_this.page_size);
						_this.tableData = response.data.data;
						// _this.options1 = response.data.data;
						console.log("测试")
						for (var i = 0; i < _this.tableData.data.length; i++) {
							_this.tableData.data[i].visible = false
						}
						_this.totalCount = response.data.data.total;
						console.log("柠檬" + _this.tableData);
						// _this.onloads();
					})
					.catch(function(error) {
						console.log(error);
					});
				_this.$forceUpdate()
			},
			async init2(){
				let _this =this;
				await this.myAxios.get("http://localhost:8888/boat/pageQuery?pageNo=1&pageSize=" + _this.page_size)
									.then(function(response) {
										_this.totalCount2 = response.data.data.total
										console.log("真的假的")
										console.log(_this.totalCount2)
									})
									.catch(function(error) {
										console.log(error);
									});
								this.myAxios.get("http://localhost:8888/boat/pageQuery?pageNo=1&pageSize=" + _this.totalCount2)
									.then(function(response) {
										console.log(response);
										console.log(_this.totalCount2);
										// _this.tableData = response.data.data;
										_this.options1 = response.data.data
										console.log("测试u")
										console.log(_this.options1.data)
										console.log(_this.options1.data[0].bid)
										for (var i = 0; i < _this.totalCount; i++) {
											console.log(_this.options1.data[i].bid)
										}
										const map1 = new Map()
										const qc1 = _this.options1.data.filter(key => !map1.has(key.bid) && map1.set(key.bid, 1)) // 这里对bid属性进行去重
										_this.qc1 = qc1;
										console.log('_this.qc1')
										console.log(_this.qc1)
										for (var i = 0; i < _this.tableData.data.length; i++) {
											_this.tableData.data[i].visible = false
										}
										_this.totalCount2 = response.data.data.total;
										console.log("柠檬" + _this.tableData);
										// _this.onloads();
									})
									.catch(function(error) {
										console.log(error);
									});
									await this.myAxios.get("http://localhost:8888/shift/pageQuery?pageNo=1&pageSize=" + _this.page_size)
									.then(function(response) {
										_this.totalCount1 = response.data.data.total
										console.log("真的假的")
										console.log(_this.totalCount1)
									})
									.catch(function(error) {
										console.log(error);
									});
									console.log(this.totalCount1)
									console.log(111)
									await this.myAxios.get("http://localhost:8888/shift/pageQuery?pageNo=1&pageSize=" + _this.totalCount1)
									.then(function(response) {
										_this.options2 = response.data.data
										console.log("起飞")
										console.log(_this.options2.data)
										const map2 = new Map()
										const qc2 = _this.options2.data.filter(key => !map2.has(key.sid) && map2.set(key
											.sid, 1))
										_this.qc2 = qc2;
										console.log('_this.qc2')
										console.log(_this.qc2)
									})
									.catch(function(error) {
										console.log(error);
									});
			},
			handleClose(done) {
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
						this.ticket.bid = "";
						this.ticket.sid = "";
						this.ticket.price = "";
					})
					.catch(_ => {});
			},
			add() {
				this.visible = !this.visible;
			},
			async addTicket() {
				// if()
				if (this.ticket.bid == "" ||
					this.ticket.sid == "" ||
					this.ticket.price == "") {
					if (this.ticket.bid == "" &&
						this.ticket.sid == "" &&
						this.ticket.price == "") {
							this.$alert("添加数据为空", "提示", {
								confirmButtonText: '确定',
								type: 'warning',
							});
					}
					else{
						this.$alert("请输入完整数据", "提示", {
							confirmButtonText: '确定',
							type: 'warning',
						});
					}
				}
				else if(this.ticket.bid != "" &&
						this.ticket.sid != "" &&
						this.ticket.price != ""){
							let _this = this
							await this.myAxios.post('http://localhost:8888/ticket/addTicket',
									qs.stringify(_this.ticket))
								.then(function(response) {
									_this.data = response.data;
									console.log(response);
									if (response.data == "success") {
										_this.init();
									}
								})
								.catch(function(error) {
									console.log(error);
								});
							console.log(this.data.msg)
							if (this.data.code == "3001") {
								this.$alert(this.data.msg, {
									confirmButtonText: '确定',
									type: 'info',
								});
								this.visible = !this.visible;
							} else if (this.data.code == "1000") {
								this.$alert(this.data.msg, {
									confirmButtonText: '确定',
									type: 'success',
								});
								this.dialogVisible = false
								this.ticket.bid = "";
								this.ticket.sid = "";
								this.ticket.price = "";
								this.visible = !this.visible;
							} else if (this.data.code == "1005") {
								this.$alert(this.data.msg, {
									confirmButtonText: '确定',
									type: 'warning',
								});
								this.visible = !this.visible;
							}
							this.$forceUpdate()
							
						}
				
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
			},
			handleEdit(index, val) {
				console.log(index);
				console.log(val);
				this.tableData.data[index].visible = !this.tableData.data[index].visible
				this.stock.tid = this.tableData.data[index].tid
				this.stock.bid = this.tableData.data[index].boat.bid
				this.stock.price = this.tableData.data[index].price
				this.$forceUpdate()
			},
			updataSave(index, val) {
				this.tableData.data[index].visible = !this.tableData.data[index].visible
				let _this = this
				this.myAxios.post('http://localhost:8888/ticket/updateTicket',
						qs.stringify(_this.stock))
					.then(function(response) {
						console.log("1")
						console.log(_this.stock.bid)
						console.log(_this.stock.sid)
						console.log(_this.stock.price)
						// console.log(_this.stock.endTime)
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
			handleBatchDelete() {
				if (this.multipleSelection.length) {
					for (let i = 0; i < this.multipleSelection.length; i++) {
						this.ids[i] = this.multipleSelection[i].tid;
					}
				} else {
					this.$alert("请选择数据", "提示", {
						confirmButtonText: '确定',
						type: 'warning',
					});
				}
				for (let i = 0; i < this.multipleSelection.length; i++) {
					console.log(this.ids[i]);
					let _this = this
					// console.log(sid)
					this.myAxios.post('http://localhost:8888/ticket/deleteTicket?tid=' + this.ids[i])
						.then(function(response) {
							// console.log(response);
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
			delStock(tid) {
				this.$confirm('是否删除船票编号为' + tid + "的船票", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					let _this = this
					// console.log(sid)
					this.myAxios.post('http://localhost:8888/ticket/deleteTicket?tid=' + tid)
						.then(function(response) {
							// console.log(response);
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
			searchTicket() {
				console.log(this.queryTicket);
				let typei = 0;
				let qurry = "http://localhost:8888/ticket/pageQueryTicket?" + "pageNo=" + this.current_page +
					"&pageSize=" + this.page_size;
				if (this.queryTicket.startCity != "") {
					qurry += "&startCity=" + this.queryTicket.startCity;
					typei = 1;
					console.log("sb1");
				}
				if (this.queryTicket.endCity != "") {
					qurry += "&arriveCity=" + this.queryTicket.endCity;
					typei = 1;
					console.log("sb2");
				}
				if (this.queryTicket.startTime != "" && this.queryTicket.startTime != undefined) {
					qurry += "&startTime=" + this.queryTicket.startTime;
					typei = 1;
					console.log("sb3");
				}
				if (this.queryTicket.endTime != "" && this.queryTicket.endTime != undefined) {
					qurry += "&arriveime=" + this.queryTicket.endTime;
					typei = 1;
					console.log("sb4");
				}
				console.log(this.queryTicket.startCity);
				console.log(this.queryTicket.endCity);
				console.log(this.queryTicket.startTime);
				console.log(this.queryTicket.endTime);
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
							_this.$alert("未查询到该船票", {
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
			this.init(this.current_page, this.page_size);
			this.init2();
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
