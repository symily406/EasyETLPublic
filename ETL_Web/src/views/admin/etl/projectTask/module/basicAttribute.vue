<template>
	<el-drawer title="基础属性" :visible.sync="drawer" :direction="direction"  :size="compare?'50%':'30%'" @close="closeDrawer">
		<el-card class="box-card" style="width: 100%;">
			<div slot="header" class="clearfix">
				<span>任务属性</span>
			</div>
			<table width="100%" border="0" class="task-table" cellspacing="0" callpadding="0">
				<tr>
					<td width="20%" align="left" class="text">任务名称</td>
					<td align="left" class="text">{{projectTaskInfo.taskName}}</td>
				</tr>
				<tr>
					<td width="20%" align="left" class="text">任务类型</td>
					<td align="left" class="text">{{projectTaskInfo.workSpaceTypeDesc}}</td>
				</tr>
				<tr>
					<td width="20%" align="left" class="text">创建人</td>
					<td align="left" class="text">{{projectTaskInfo.userName}}</td>
				</tr>
				<tr>
					<td width="20%" align="left" class="text">创建时间</td>
					<td align="left" class="text">{{projectTaskInfo.addTime}}</td>
				</tr>
				<tr>
					<td width="20%" align="left" class="text">修改时间</td>
					<td align="left" class="text">{{projectTaskInfo.updateTime}}</td>
				</tr>
				<tr>
					<td width="20%" align="left" class="text">描述</td>
					<td align="left" class="text">{{projectTaskInfo.remark}}</td>
				</tr>
			</table>
		</el-card>

		<el-card class="box-card" v-if="compare==false">
			<div slot="header" class="clearfix">
				<span>历史版本</span>
			</div>
			<el-table :data="tableData" class="task-table" :header-cell-style="{textAlign:'center'}" height="400">
				<el-table-column prop="addTime" label="提交时间" width="150" align="center">
				</el-table-column>
				<el-table-column prop="name" label="提交人" width="120" >
				</el-table-column>
				<el-table-column prop="remark" label="描述" width="250">
				</el-table-column>
				<el-table-column prop="opt" label="操作" width="100" fixed="right" align="center">
					<template slot-scope="scope">
						<el-button type="text" size="small" @click="openTaskCompare(scope.row)">版本对比</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
	</el-drawer>
</template>
<script>
	export default {
		name: 'basicAttribute',
		components: {

		},
		props: {
			projectTaskInfo: {
				type: Object,
				default: {}
			},
			compare: {
				type: Boolean,
				default: false
			},
		},
		mixins: [],
		data() {
			return {
				drawer: false,
				tabPosition: 'right',
				direction: 'rtl',
				tableData: []
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
			openTaskCompare(row) {
				let taskInfo = {
					taskId: row.taskId,
					backupTaskId:row.backupTaskId,
					projectId: this.$route.query.projectId,
					projectWorkSpaceLayerRelId: this.$route.query.projectWorkSpaceLayerRelId,
					projectWorkSpaceId: this.$route.query.projectWorkSpaceId,
					workSpaceType: this.$route.query.workSpaceType
				}
				window.parent.openTaskCompare(taskInfo);
			},
			initBackup() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskBackup/queryBackups", {
					backupTaskId: this.$route.query.taskId
				}).then(res => {
					this.tableData=res.data;
				}).catch(err => {
					this.loading = false;
				});
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			closeDrawer(){
				this.$emit("closeDrawer");
			}
		}
	}
</script>
<style scoped="scoped">
	::v-deep .el-card__body,
	.el-main {
		padding: 10px;
	}
</style>