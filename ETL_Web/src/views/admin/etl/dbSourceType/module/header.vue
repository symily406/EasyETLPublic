<template>
	<div class="headr-container">
		<el-form :inline="true" :model="queryParams">
			<el-form-item label="">
				<el-input v-model="queryParams.sourceTypeName" placeholder="名称" size="small" clearable></el-input>
			</el-form-item>
			<el-form-item label="">
				<el-select v-model="queryParams.sourceType" placeholder="数据源类型" clearable size="small">
					<el-option v-for="item in dbSourceType" :key="item.value" :label="item.name" :value="item.value">
					</el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button type="primary" size="small" icon="el-icon-search" @click="searchPage">查询</el-button>
				<el-button size="small" icon="el-icon-refresh" @click="refreshPage">刷新</el-button>
				<el-button type="warning" size="small" icon="el-icon-edit" @click="add"
					v-if="checkPermission('admin:etl:sourceType:dbSourceType:add')">新增类型</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import { headerMixin, dicts } from "@/mixins/mixins";
	export default {
		name: 'dbSourceTypeHeader',
		components: {

		},
		props: {

		},
		mixins: [headerMixin, dicts],
		data() {
			return {
				dbSourceType: []
			}
		},
		created() {
			let that = this;
			this.initDict("DB_TYPE", function(res) {
				that.dbSourceType = res.data;
			}, function(error) {

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