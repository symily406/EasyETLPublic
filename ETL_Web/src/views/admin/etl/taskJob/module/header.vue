<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="所属项目">
				<el-select filterable v-model="queryParams.projectId" placeholder="所属项目" clearable size="small">
					<el-option v-for="item in projectSelectData" :key="item.projectId" :label="item.projectName"
						:value="item.projectId">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="任务名称">
				<el-input v-model="queryParams.taskName" placeholder="任务名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item label="计划执行时间">
				<el-date-picker class="el-date-picker-width" v-model="queryParams.planeDate" type="date" placeholder="计划执行时间" format="yyyy-MM-dd"
					value-format="yyyy-MM-dd" size="small" clearable>
				</el-date-picker>
			</el-form-item>
			<el-form-item label="调度状态">
				<el-select class="el-select-width" filterable v-model="queryParams.cornStatus" placeholder="调度状态"
					clearable size="small">
					<el-option v-for="item in cornStatus" :key="item.value" :label="item.name" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item label="任务状态">
				<el-select class="el-select-width" filterable v-model="queryParams.status" placeholder="任务状态" clearable
					size="small">
					<el-option v-for="item in status" :key="item.value" :label="item.name" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import {
		headerMixin,
		dicts
	} from "@/mixins/mixins";
	export default {
		name: 'taskJobHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin, dicts],
		data() {
			return {
				dateRange: [],
				projectSelectData: [],
				cornStatus: [],
				status: []
			}
		},
		created() {
			let that = this;
			this.queryAllProject();
			this.initDict("ENABLE", function(res) {
				that.cornStatus = res.data;
			}, function() {

			});
			this.initDict("TASK_STATUS_ENUM", function(res) {
				that.status = res.data;
			}, function() {

			});
		},
		methods: {
			queryAllProject() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/project/queryAll", {}).then(res => {
					this.projectSelectData = res.data;
				}).catch(error => {

				});
			},
			dateRangeChange() {
				let dateRange = this.dateRange;
				// console.log(dateRange);
				this.queryParams.startDate = dateRange[0];
				this.queryParams.endDate = dateRange[1];
			},
			refreshPage() {
				this.dateRange = [];
				this.$emit("refreshPage");
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.headr-container {
		.el-select-width {
			width: 120px;
		}

		.el-date-picker-width {
			width: 150px;
		}
	}
</style>