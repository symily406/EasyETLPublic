<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="" label-width="0">
				<el-select v-model="queryParams.executionStatus" placeholder="执行状态" clearable size="small"
					class="max-width-100-ratio">
					<el-option v-for="item in taskScheduleExecutionEnum" :key="item.value" :label="item.name"
						:value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="danger" size="small" icon="el-icon-delete-solid" @click="batchDelete"
					v-if="checkPermission('admin:system:taskSchedule:info:batchDeleteById')">批量删除</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import {
		headerMixin,dicts
	} from "@/mixins/mixins";
	export default {
		name: 'taskScheduleLogHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin,dicts],
		data() {
			return {
				taskScheduleExecutionEnum:[]
			}
		},
		created() {
			let that = this;
			this.initDict("TASK_SCHEDULE_EXECUTION_ENUM", function(res) {
				that.taskScheduleExecutionEnum = res.data;
			}, function() {
			
			});
		},
		methods: {
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>