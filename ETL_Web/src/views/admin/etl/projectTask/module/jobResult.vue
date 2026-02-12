<template>
	<div class="tleft f12" :style="{height:(logContainerHeight)+'px'}">
		<div class="run-sql-container"><span class="cgreen" v-show="runSql">运行的sql：</span>{{runSql}}<span class="cgreen"
				v-show="usageTime">，用时：</span>{{usageTime}}ms</div>
		<el-table :style="{width: (tableWidth-5)+'px'}" :data="tableDate" border stripe :height="tableHeight"
			:fit="false" class="task-table " :header-cell-style="{textAlign:'center'}">
			<el-table-column label="序号" width="60" align="center">
				<template slot-scope="scope">
					{{ (currentPage - 1) * pageSize + scope.$index + 1 }}
				</template>
			</el-table-column>
			<el-table-column v-for="column in jobResultTableHeader" :key="column.prop" :prop="column.prop"
				:label="column.label" :width="column.width">
			</el-table-column>
		</el-table>
		<div class="page-container">
			<el-pagination class="fright" @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:current-page="currentPage" :page-sizes="[50, 100, 500, 1000]" :page-size="pageSize"
				layout="total, sizes, prev, pager, next, jumper" :total="total">
			</el-pagination>
			<span class="fleft " v-if="jobResultTableHeader.length>0">
				<table border="0" cellspacing="0" callpadding="0">
					<tr>
						<td><download-excel :data="jobResultTableData" :name="fileName" class="btn btn-primary">
								<el-link :underline="false" type="primary" class="f12">[导出运行结果]</el-link>
							</download-excel></td>
						<td>默认返回1000条记录</td>
					</tr>
				</table>
			</span>
		</div>

	</div>
</template>
<script>
	import DownloadExcel from 'vue-json-excel';
	export default {
		name: 'jobResult',
		components: {
			DownloadExcel
		},
		mixins: [],
		props: {
			logContainerHeight: {
				type: Number,
				default: 0
			},
		},
		watch: {
			logContainerHeight(nval, oldval) {
				this.initTableWidth();
			}
		},
		computed: {

		},
		data() {
			return {
				tableDate: [],
				jobResultTableHeader: [{
					"prop": "result",
					"label": "结果",
					"width": "100%"
				}],
				jobResultTableData: [{
					"result": "无结果"
				}],
				fileName:"运行结果.xls",
				runSql: undefined,
				usageTime: undefined,
				tableMaxHeight: 480,
				tableWidth: 800,
				tableHeight: 0,
				total: 0,
				currentPage: 1, // 当前页码
				pageSize: 50, // 每页显示的条数
			}
		},
		//组件实例刚被创建,el和data并未初始化
		beforeCreate() {

		},
		//组件实例创建完成,完成了data数据的初始化,el没有
		created() {
			window.addEventListener('resize', this.initTableWidth);
		},
		//完成了el和data 初始化
		beforeMount() {

		},
		//完成挂载
		mounted() {

		},
		//更新前状态
		beforeUpdate() {

		},
		//更新完成状态
		updated() {

		},
		//销毁前状态
		beforeDestroy() {

		},
		//销毁完成状态
		destroyed() {

		},
		methods: {
			initTableWidth() {
				this.tableWidth = window.innerWidth;
				this.tableHeight = this.logContainerHeight - 85;
			},
			handleSizeChange(val) {
				this.pageSize = val;
				this.fetchData();
			},
			handleCurrentChange(val) {
				this.currentPage = val;
				this.fetchData();
			},
			fetchData() {
				this.tableDate = this.jobResultTableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage *
					this
					.pageSize);
				this.total = this.jobResultTableData.length;
			},
			initJobResult(jobId) {
				this.reset();
				this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJob/queryJobResult", {
						"jobId": jobId
					})
					.then(res => {
						if (res.data.headers && res.data.result) {
							this.jobResultTableHeader = res.data.headers;
							this.jobResultTableData = res.data.result;
						}
						this.runSql = res.data.runSql;
						this.usageTime = res.data.usageTime;
						this.fetchData();
						this.fileName=jobId+".xls";
					}).catch(err => {

					});
			},
			reset() {
				this.jobResultTableHeader = [{
					"prop": "result",
					"label": "结果",
					"width": this.tableWidth - 70
				}];
				this.jobResultTableData = [];
				this.tableDate = [];
				this.total = 0;
				this.currentPage = 1;
				this.pageSize = 50;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.page-container {
		line-height: 35px;
		padding-left: 5px;
		width: 100%;
		background: #fff;
		overflow: hidden;
	}

	.run-sql-container {
		width: 100%;
		height: 50px;
		overflow: auto;
		padding: 5px;
		border: solid 1px #E8E8E8;
		font-size: 14px;
	}
</style>