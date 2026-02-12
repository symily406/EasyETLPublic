<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage" />
		<el-table size="mini" border :data="tableData" :height="dialogHeight" :style="{width:dialogWidth+'px'}"
			:header-cell-style="{textAlign:'center'}" v-loading="loading">
			<el-table-column type="" width="30" align="center">
				<template scope="scope">
					<el-radio :label="scope.$index" v-model="radio"
						@change.native="getCurrentRow(scope.row)"></el-radio>
				</template>
			</el-table-column>
			<el-table-column prop="taskName" label="任务名称">
				<template slot-scope="scope">
					{{scope.row.taskName}}
				</template>
			</el-table-column>
			<el-table-column prop="updateTime" label="修改时间" width="150">
				<template slot-scope="scope">
					{{scope.row.updateTime}}
				</template>
			</el-table-column>
		</el-table>
		<div class="tcenter">
			<span slot="footer" class="dialog-footer">
				<el-button size="small" @click="closeWin">关 闭</el-button>
				<el-button size="small" type="primary" @click="onSubmit()">确 定</el-button>
			</span>
		</div>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin,
		indexMixin
	} from "@/mixins/mixins";
	import eHeader from './flowTaskHeader';
	import ePage from "@/components/page/page";
	export default {
		name: 'flowTaskList',
		components: {
			ePage,
			eHeader
		},
		props: {

		},
		mixins: [dialogMixin, indexMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				radio:'',
				row:null,
				queryParams: {
					keyword: '',
					projectId: '',
					workSpaceType: ''
				}
			}
		},
		//组件实例刚被创建,el和data并未初始化
		beforeCreate() {

		},
		//组件实例创建完成,完成了data数据的初始化,el没有
		created() {

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
			openWin() {
				this.initData(this.queryParams);
			},
			closeWin() {
				this.dialogVisible = false;
				this.radio='';
				this.row=null;
				this.queryParams= {
					keyword: '',
					projectId: '',
					workSpaceType: ''
				};
			},
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/queryPage", this.addPageParam(
					queryParams, this
					.page)).then(res => {
					this.loading = false;
					this.page = this.$app.extend(this.page, this.$app.setPage(res.data));
					this.tableData = res.data.records;
				}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.initData(this.queryParams);
			},
			refreshPage() {
				this.queryParams = this.$options.data().queryParams;
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			getCurrentRow(row) {
				this.row=row;
			},
			onSubmit() {
				if(!this.row){
					Message({
						message: '请选择一个任务',
						type: 'error',
						duration: 5 * 1000
					});
					return;
				}
				this.$emit("taskCallBack",this.row);
			}
		}
	}
</script>
<style scoped="scoped">
</style>