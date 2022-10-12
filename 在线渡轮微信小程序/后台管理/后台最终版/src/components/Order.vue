<template>
	<div style=" height: 100%;">
		<div class="top">
			订单管理
		</div>
		<el-card style="text-align: center;">
			<el-input
			  placeholder="请输入船票编号"
			  v-model="input"
			  clearable
			  style="width: 300px;">
			</el-input>&nbsp;
			<el-button type="primary" icon="el-icon-search" @click="query">查询</el-button><br>
			<el-table ref="multipleTable" :data="tableData.data" stripe
				:default-sort="{prop:'date',order:'descending'}" tooltip-effect="dark" style="width: 100%"
				@selection-change="handleSelectionChange"
				:header-cell-style="{'text-align':'center'}"
				:cell-style="{'text-align':'center'}">
				<el-table-column sortable label="订单号" width="120" prop="oid">
					<template slot-scope="scope">
						<span>
							{{ scope.row.oid }}
						</span>
					</template>
				</el-table-column>
				<el-table-column sortable label="船票编号" width="120" prop="tid">
					<template slot-scope="scope">
						<span>
							{{ scope.row.tid }}
						</span>
					</template>
				</el-table-column>
				<el-table-column label="乘客姓名" width="120" prop="user.uname">
					<template slot-scope="scope">
						<span>
							{{ scope.row.user.uname }}
						</span>
					</template>
				</el-table-column>
				<el-table-column sortable label="票价" width="120" prop="price">
					<template slot-scope="scope">
						<span>
							{{ scope.row.price }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="startCity" label="始发站" width="120px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.startCity }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="endCity" label="终点站" width="120px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.endCity }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="startTime" label="出发时间" width="220px">
					<template slot-scope="scope">
						<span>
							{{ scope.row.startTime }}
						</span>
					</template>
				</el-table-column>
				<el-table-column prop="endTime" label="到达时间" width="220px">
					<template slot-scope="scope">
						<span>
							{{scope.row.endTime}}
						</span>
					</template>
				</el-table-column>
				
			</el-table>
			<div style="text-align: center;margin-top: 20px;">
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
					:current-page="current_page" :page-sizes="[9,10,11,12]" :page-size.sync="page_size"
					layout="total, sizes, prev, pager, next, jumper" :total="totalCount">
				</el-pagination>
			</div>
		</el-card>
	</div>
</template>

<script>
	export default {
		name:"dataList",
		data() {
			return {
				visible: false,
				tableData: [],
				current_page_data: [],
				current_page: 1,
				page_size: 10,
				multipleSelection: [],
				ids: [],
				totalCount: 1,
				input:''
			}
		},
		
		methods: {
			
			init(n1, n2) {
				let _this = this; //把vue对象 先保存到第三方变量中
				_this.current_page = n1,
					_this.page_size = n2,
					// 为给定 ID 的 user 创建请求
					this.myAxios.get("http://localhost:8888/order/pageQuery?pageNo="+_this.current_page+"&pageSize="+_this.page_size)
					.then(function(response) {
						// console.log(response);
						// console.log(_this.page_size);
						_this.tableData = response.data.data;
						console.log(_this.tableData)
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
			
			handleBatchDelete() {
				if (this.multipleSelection.length) {
					for (let i = 0; i < this.multipleSelection.length; i++) {
						this.ids[i] = this.multipleSelection[i].id;
					}
					this.delete();

				} else {
					this.$alert("请选择数据", "提示", {
						confirmButtonText: '确定',
						type: 'info',
					});
				}
			},
			query(){
				// console.log(this.input);
				if(this.input == "")
				{
					this.$alert("请输入船票编号", {
						confirmButtonText: '确定',
						type: 'warning',
					});
					
				}
				let _this = this; //把vue对象 先保存到第三方变量中
				let qurryn="";
					// 为给定 ID 的 user 创建请求
					if(_this.input == null)
					{
						qurryn="http://localhost:8888/order/pageQuery?pageNo="+_this.current_page+"&pageSize="+_this.page_size;
					}else{
						qurryn="http://localhost:8888/order/pageQuery?pageNo="+_this.current_page+"&pageSize="+_this.page_size+"&tid="+_this.input;
					}
					_this.myAxios.get(qurryn).then(function(response) {
						// console.log(response);
						// console.log(_this.page_size);
						_this.tableData = response.data.data;
						console.log(_this.tableData)
						_this.totalCount = response.data.data.total;
						console.log(_this.tableData);
						// _this.onloads();
						if(_this.totalCount==0)
						{						
							_this.$alert("未查询到该班次", {
								confirmButtonText: '确定',
								type: 'warning',
							});
							
						}
					}).catch(function(error) {
						console.log(error);
					});
					_this.$forceUpdate();
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
