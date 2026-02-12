<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="运行时间">
				<el-date-picker size="small" class="el-date-picker-width" v-model="dateRange" type="datetimerange"
					start-placeholder="开始运行时间" end-placeholder="结束运行时间" format="yyyy-MM-dd HH:mm:ss"
					value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']"  @change="dateRangeChange">
				</el-date-picker>
			</el-form-item>
			<el-form-item label="任务状态">
				<el-select class="el-select-width" filterable v-model="queryParams.status" placeholder="任务状态" clearable
					size="small">
					<el-option v-for="item in status" :key="item.value" :label="item.name" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="添加方式">
				<el-select class="el-select-width" filterable v-model="queryParams.addType" placeholder="任务状态" clearable
					size="small">
					<el-option v-for="item in taskAddTypes" :key="item.value" :label="item.name" :value="item.value">
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
		name: 'runTaskJobHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin, dicts],
		data() {
			return {
				dateRange: [this.$moment().format('YYYY-MM-DD') + " 00:00:00",this.$moment().format('YYYY-MM-DD') + " 23:59:59"],
				projectSelectData: [],
				cornStatus: [],
				taskAddTypes:[],
				status: []
			}
		},
		created() {
			let that = this;
			this.initDict("TASK_STATUS_ENUM", function(res) {
				that.status = res.data;
			}, function() {
			
			});
			this.initDict("TASK_ADD_TYPE_ENUM", function(res) {
				that.taskAddTypes = res.data;
			}, function() {

			});
		},
		methods: {
			getContentPath() {
				return this.$app.global.etlPath;
			},
			refreshPage() {
				dateRange: [this.$moment().format('YYYY-MM-DD') + " 00:00:00",this.$moment().format('YYYY-MM-DD') + " 23:59:59"];
				this.$emit("refreshPage");
			},
			dateRangeChange() {
				this.queryParams.queryStartTime = undefined;
				this.queryParams.queryEndTime =undefined;
				let dateRange = this.dateRange;
				if(dateRange){
					this.queryParams.queryStartTime = dateRange[0];
					this.queryParams.queryEndTime = dateRange[1];
				}
			}
		}
	}
</script>
<style scoped="scoped">
</style>