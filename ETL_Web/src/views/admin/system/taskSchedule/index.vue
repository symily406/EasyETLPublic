<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading" @selection-change="handleSelectionChange">
			<el-table-column prop="name" label="任务名称">
				<template slot-scope="scope">
					{{scope.row.name}}
				</template>
			</el-table-column>
			<el-table-column prop="corn" label="执行周期" width="180">
				<template slot-scope="scope">
					{{scope.row.corn}}
				</template>
			</el-table-column>
			<el-table-column prop="classPath" label="任务类路径" width="350">
				<template slot-scope="scope">
					{{scope.row.classPath}}
				</template>
			</el-table-column>
			<el-table-column prop="parameter" label="参数" width="200">
				<template slot-scope="scope">
					{{scope.row.parameter}}
				</template>
			</el-table-column>
			<el-table-column prop="bootUp" label="起动方式" width="120" align="center">
				<template slot-scope="scope">
					<el-tag>{{scope.row.bootUpDesc}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="statusDesc" label="状态" width="100" align="center">
				<template slot-scope="scope">
					<span v-show="scope.row.status==-1||scope.row.status==2||scope.row.status==0">
						<el-tag>{{scope.row.statusDesc}}</el-tag>
					</span>
					<span v-show="scope.row.status==1">
						<el-tag type="info"
							v-show="scope.row.executionStatus==0">{{scope.row.executionStatusDesc}}</el-tag>
						<el-tag v-show="scope.row.executionStatus==1">{{scope.row.executionStatusDesc}}</el-tag>
						<el-tag type="success"
							v-show="scope.row.executionStatus==2">{{scope.row.executionStatusDesc}}</el-tag>
						<el-tag type="danger"
							v-show="scope.row.executionStatus==-1">{{scope.row.executionStatusDesc}}</el-tag>
					</span>
				</template>
			</el-table-column>
			<el-table-column prop="runNextTime" label="下次执行时间" width="180" align="center">
				<template slot-scope="scope">
					{{scope.row.runNextTime}}
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="200" align="left">
				<template slot-scope="scope">
					<el-button @click.native.prevent="logInfo(scope.row)" type="text" size="small"
						v-if="checkPermission('admin:system:taskSchedule:edit')&&scope.row.allowEdit==1">
						运行日志
					</el-button>
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:taskSchedule:edit')&&scope.row.allowEdit==1">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:system:taskSchedule:deleteById')&&scope.row.level!=9999&&scope.row.level!=99999">
						删除
					</el-button>
					<el-dropdown v-if="scope.row.level!=99999">
						<span class="el-dropdown-link">
							更多<i class="el-icon-arrow-down el-icon--right"></i>
						</span>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item @click.native.prevent="cancelTaskSchedule(scope.row)"
								v-if="checkPermission('admin:system:taskSchedule:cancelTaskSchedule')&&scope.row.status==1">停止任务</el-dropdown-item>
							<el-dropdown-item @click.native.prevent="addTaskSchedule(scope.row)"
								v-if="checkPermission('admin:system:taskSchedule:addTaskSchedule')&&scope.row.status==-1">启动任务</el-dropdown-item>
							<el-dropdown-item @click.native.prevent="manuallyOnceTaskSchedule(scope.row)"
								v-if="checkPermission('admin:system:taskSchedule:manuallyOnceTaskSchedule')">手动执行一次</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
				</template>
			</el-table-column>
		</el-table>
		<ePage :page="page" @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange" />
		<eForm ref="eForm" dialogTitle="添加任务调度" dialogWidth="80%" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改任务调度" dialogWidth="80%" dialogHeight="600px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<eLogInfo ref="eLogInfo" dialogTitle="运行日志" dialogWidth="80%" :dialogTop="this.$store.state.global.dialogTop" />
	</div>
</template>
<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	import eLogInfo from './logInfo/index';
	export default {
		name: 'taskScheduleIndex',
		components: {
			ePage,
			eHeader,
			eForm,
			edit,
			eLogInfo
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//任务名称
					name: undefined,
					//启用
					enable: undefined,
					//运行状态
					status: undefined
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initTableHeight();
			});
		},
		created() {
			window.addEventListener('resize', this.initTableHeight);

			this.initData(this.queryParams);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/queryPage", this.addPageParam(
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
			add() {
				this.$refs['eForm'].dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前任务调度不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			manuallyOnceTaskSchedule(row) {
				this.$confirm('您确定执行一次任务吧!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/manuallyOnceTaskSchedule", {
						"taskScheduleId": row.taskScheduleId
					}).then(
						res => {
							this.eventCallBack(30);
						}).catch(err => {

					});
				});
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前任务调度不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行任务调度吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/deleteById", {
						"taskScheduleId": row.taskScheduleId
					}).then(
						res => {
							this.eventCallBack(3);
						}).catch(err => {

					});
				});
			},
			logInfo(row) {
				this.$refs['eLogInfo'].queryParams.taskScheduleId = row.taskScheduleId;
				this.$refs['eLogInfo'].dialogVisible = true;
			},
			addTaskSchedule(row) {
				this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/addTaskSchedule", {
						"taskScheduleId": row.taskScheduleId
					})
					.then(res => {
						this.eventCallBack(40);
					}).catch(err => {

					});
			},
			cancelTaskSchedule(row) {
				this.$ajax.get(this.getContentPath() + "/admin/system/taskSchedule/cancelTaskSchedule", {
						"taskScheduleId": row.taskScheduleId
					})
					.then(res => {
						this.eventCallBack(50);
					}).catch(err => {

					});
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '任务调度添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '任务调度修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '任务调度删除成功!',
							type: 'success'
						});
						break;
					case 30: //删除
						this.$message({
							message: '任务手动执行成功!',
							type: 'success'
						});
						break;
					case 40: //定时任务起动
						this.reloadPage();
						this.$message({
							message: '定时任务起动成功!',
							type: 'success'
						});
						break;
					case 50: //定时任务停止
						this.reloadPage();
						this.$message({
							message: '定时任务停止成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
					.offsetHeight - 50;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
	::v-deep .el-dropdown {
		color: #409EFF;
		font-size: 12px;
		margin-left: 5px;
		cursor: pointer;
	}

	::v-deep .el-dropdown-menu__item {
		font-size: 12px;
		line-height: 30px;
	}
</style>