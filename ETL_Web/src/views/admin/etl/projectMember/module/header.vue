<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="" prop="projectId">
				<el-select :disabled="projectId?true:false" filterable v-model="queryParams.projectId"
					placeholder="归属项目" clearable size="small" class="max-width-100-ratio">
					<el-option v-for="item in projectSelectData" :key="item.projectId" :label="item.projectName"
						:value="item.projectId">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:etl:projectMember:add')&&projectId">新增</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import {
		headerMixin
	} from "@/mixins/mixins";
	export default {
		name: 'projectMemberHeader',
		components: {

		},
		props: {
			projectId: {
				type: String,
				default: undefined
			}
		},
		mixins: [headerMixin],
		data() {
			return {
				projectSelectData: []
			}
		},
		created() {
			this.queryAllProject();
		},
		methods: {
			queryAllProject() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/project/queryAll", {}).then(res => {
					this.projectSelectData = res.data;
				}).catch(error => {

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