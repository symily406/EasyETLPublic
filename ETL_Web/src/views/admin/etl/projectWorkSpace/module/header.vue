<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="名称">
				<el-input v-model="queryParams.workSpaceName" placeholder="名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item label="任务类型">
				<el-select v-model="queryParams.workSpaceType" placeholder="任务类型" clearable size="small"
					class="max-width-100-ratio">
					<el-option v-for="item in workSpaceTypes" :key="item.value" :label="item.name" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:etl:projectWorkSpace:add')">新增</el-button>
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
		name: 'projectWorkSpaceHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin, dicts],
		data() {
			return {
				workSpaceTypes: []
			}
		},
		created() {
			this.initWorkSpaceTypes();
		},
		methods: {
			initWorkSpaceTypes() {
				let that = this;
				this.initDict("WORK_SPACE_TASK_TYPE", function(res) {
					that.workSpaceTypes = res.data;
				}, function(error) {

				});
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>