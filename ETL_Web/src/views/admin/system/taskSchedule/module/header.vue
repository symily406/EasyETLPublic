<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="任务名称">
				<el-input v-model="queryParams.name" placeholder="任务名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item label="任务状态">
				<el-select v-model="queryParams.enable" placeholder="任务状态" clearable size="small"
					class="max-width-100-ratio">
					<el-option v-for="item in enable" :key="item.value" :label="item.name" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="运行状态">
				<el-select v-model="queryParams.status" placeholder="运行状态" clearable size="small"
					class="max-width-100-ratio">
					<el-option v-for="item in taskScheduleStatusEnum" :key="item.value" :label="item.name"
						:value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:system:taskSchedule:add')">新增</el-button>
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
		name: 'taskScheduleHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin, dicts],
		data() {
			return {
				enable: [],
				taskScheduleStatusEnum: []
			}
		},
		created() {
			let that = this;
			this.initDict("ENABLE", function(res) {
				that.enable = res.data;
			}, function() {

			});
			this.initDict("TASK_SCHEDULE_STATUS_ENUM", function(res) {
				that.taskScheduleStatusEnum = res.data;
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