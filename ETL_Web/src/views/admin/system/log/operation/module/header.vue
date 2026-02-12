<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="操作人">
				<el-input v-model="queryParams.staffName" placeholder="操作人" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item label="名称">
				<el-input v-model="queryParams.name" placeholder="名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item label="请求方式">
				<el-select v-model="queryParams.mode" placeholder="请求方式" size="small" clearable>
					<el-option v-for="item in httpModes" :key="item.value" :label="item.name" :value="item.value">
				 </el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="操作日期">
				<el-date-picker v-model="queryDate" type="daterange" size="small" value-format="yyyy-MM-dd"
					format="yyyy-MM-dd" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
					@change="changeDate" clearable>
				</el-date-picker>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="danger" size="small" icon="el-icon-delete-solid" @click="batchDelete"
					v-if="checkPermission('admin:system:operationLog:batchDeleteById')">批量删除</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin, dicts } from "@/mixins/mixins";
	export default {
		name: 'operationLogHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin, dicts],
		data() {
			return {
				queryDate: [],
				httpModes: [],
			}
		},
		created() {
			let that=this;
			this.initDict("http_mode", function(res) {
				that.httpModes = res.data;
			}, function(error) {

			});
		},
		methods: {
			changeDate(val) {
				this.queryParams.startDate = val[0];
				this.queryParams.endDate = val[1];
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
