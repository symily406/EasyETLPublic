<template>
	<div>
		<el-header>
			<div class="tagbar">
				<table border="0" width="100%" cellspacing="0" callpadding="0">
					<tr>
						<td>
							<el-tabs v-model="taskActiveTab" type="border-card" @tab-click="handleClick"
								@tab-remove="tabRemoveClick" :style="{width:tabPanleWidth+'px'}">
								<el-tab-pane :key="item.taskId" v-for="(item, index) in taskTabs" :label="item.taskName"
									:name="item.taskId" :closable="item.isClose">
									<span slot="label" class="cblue">
										<i class="fa fa-refresh f12" v-if="item.workSpaceType==1"></i>
										<i class="el-icon-coin f12" v-if="item.workSpaceType==2"></i>
										&nbsp;{{item.taskName}}</span>
									{{item.taskName}}
								</el-tab-pane>
							</el-tabs>
						</td>
						<td width="100">
							<div class="tagbar-dropdown">
								<el-dropdown @command="handleCommand">
									<span class="el-dropdown-link">
										操作<i class="el-icon-arrow-down el-icon--right"></i>
									</span>
									<el-dropdown-menu slot="dropdown">
										<el-dropdown-item :command="item.taskId"
											v-for="(item, index) in taskTabs">{{item.taskName}}</el-dropdown-item>
										<el-dropdown-item command="closeOther">关闭其它</el-dropdown-item>
										<el-dropdown-item command="closeAll">关闭全部</el-dropdown-item>
									</el-dropdown-menu>
								</el-dropdown>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</el-header>
	</div>
</template>
<script>
	import {
		mapState
	} from 'vuex';
	import {
		getRunTaskJob,
		setRunTaskJob
	} from "@/utils/localStore.js";
	export default {
		name: "workSpaceNavbar",
		components: {

		},
		props: {

		},
		computed: {
			...mapState({
				isCollapse: state => state.global.isCollapse,
				navs: state => state.global.navs.reverse()
			}),
			taskMenuShow: {
				get() {
					return this.$store.state.global.taskMenuShow
				},
				set(val) {
					this.$store.commit("global/global/setTaskMenuShow", val)
				}
			}
		},
		data() {
			return {
				taskActiveTab: this.$store.state.global.defaultTaskId,
				taskTabs: [],
				taskId: '',
				projectId: '',
				projectWorkSpaceId: '',
				workSpaceType: '',
				tabPanleWidth: this.tabPanleCalculate()
			}
		},
		mounted() {
			let that = this;
			this.init();
			window.addEventListener('resize', function() {
				that.tabPanleWidth = that.tabPanleCalculate();
			});
		},
		watch: {
			$route: 'handleRoute',
			taskId(newVal, oldVal) {

			},
			taskActiveTab(newVal, oldVal) {

			}
		},
		created() {

		},
		//销毁前状态
		beforeDestroy() {
			this.destroy();
		},
		methods: {
			handleRoute(route) {

			},
			init() {
				this.taskTabs = this.getTaskTabs();
			},
			destroy() {
				this.taskActiveTab = this.$store.state.global.defaultTaskId;
				this.projectId = '';
				this.projectWorkSpaceId = '';
				this.workSpaceType = '';
				this.taskId = '';
			},
			handleCommand(event) {
				let tabs = [];
				switch (event) {
					case "closeOther":
						this.taskTabs.forEach(item => {
							if (item.taskId == this.taskActiveTab) {
								tabs.push(item);
							}
						});
						this.setTaskTabs(tabs);
						this.routeView(this.taskActiveTab);
						break;
					case "closeAll":
						this.setTaskActiveTab(this.$store.state.global.defaultTaskId);
						this.setTaskTabs([]);
						this.routeView(this.taskActiveTab);
						break;
					default:
						this.setTaskActiveTab(event);
						this.routeView(this.taskActiveTab);
				}
			},
			handleFullScreen() {
				if (!screenfull.enabled) {
					this.$message({
						message: '您的浏览器不支持全屏!',
						type: 'warning'
					})
					return false;
				}
				screenfull.toggle();
			},
			toggleCollapse() {
				const isCollapse = !this.isCollapse;
				this.$store.commit("global/global/setIsCollapse", isCollapse);
			},
			handleClick(tab, event) {
				this.routeView(tab.name);
			},
			tabRemoveClick(id) {
				let tabs = [];
				let taskActiveTab = this.$store.state.global.defaultTaskId;
				this.taskTabs.forEach((item, index) => {
					if (item.taskId != id) {
						tabs.push(item);
					}
				});
				let len = tabs.length;
				if (len > 0) {
					let lastTab = tabs[len - 1];
					taskActiveTab = lastTab.taskId;
				}
				this.removeStoreTaskJobResult(id);
				this.setTaskTabs(tabs);
				this.routeView(taskActiveTab);
			},
			removeStoreTaskJobResult(taskId) {
				let newRunTaskJobObj = [];
				let runTaskJobObj = [];
				let runTaskJob = getRunTaskJob();
				if (runTaskJob) {
					runTaskJobObj = JSON.parse(runTaskJob);
				}
				runTaskJobObj.forEach(item => {
					let job = runTaskJobObj.filter(item => item.taskId == taskId)[0];
					if (!job) {
						newRunTaskJobObj.push(item);
					}
				});
				setRunTaskJob(JSON.stringify(newRunTaskJobObj));
			},
			routeView(id) {
				let task = this.taskTabs.filter(item => item.taskId === id)[0];

				if (task) {
					let param = {
						taskId: task.taskId,
						projectId: task.projectId,
						projectWorkSpaceLayerRelId: task.projectWorkSpaceLayerRelId,
						projectWorkSpaceId: task.projectWorkSpaceId,
						workSpaceType: task.workSpaceType
					};
					this.setTaskActiveTab(param.taskId);
					this.$emit("openTaskContent", param);
				} else {
					let param = {
						taskId: this.$store.state.global.defaultTaskId,
						projectId: this.projectId,
						projectWorkSpaceLayerRelId: this.projectWorkSpaceLayerRelId,
						projectWorkSpaceId: this.projectWorkSpaceId,
						workSpaceType: this.workSpaceType
					};
					this.setTaskActiveTab(param.taskId);
					this.$emit("openTaskContent", param);
				}
			},
			setTaskTabs(tabs) {
				localStorage.setItem(this.getSessionTabKey(this.$store.state.global.taskTabName), JSON.stringify(tabs));
				this.taskTabs = tabs;
			},
			getTaskTabs() {
				let tabs = [];
				let storeTabs = localStorage.getItem(this.getSessionTabKey(this.$store.state.global.taskTabName));
				if (storeTabs) {
					tabs = JSON.parse(storeTabs);
				}
				return tabs;
			},
			getTaskActiveTab() {
				this.taskActiveTab = localStorage.getItem(this.getSessionTabKey(this.$store.state.global
					.taskTableIndexName));
			},
			setTaskActiveTab(val) {
				localStorage.setItem(this.getSessionTabKey(this.$store.state.global.taskTableIndexName), val);
				this.taskActiveTab = val;
			},
			openPersonalCenter() {
				this.$refs['personalCenter'].dialogVisible = true;
			},
			getSessionTabKey(keyName) {
				let projectId = this.$route.query.projectId;
				let projectWorkSpaceId = this.$route.query.projectWorkSpaceId;
				let workSpaceType = this.$route.query.workSpaceType;
				return keyName.concat("_").concat(projectId);
			},
			transferTabPanleCalculate() {
				this.tabPanleWidth = this.tabPanleCalculate();
			},
			tabPanleCalculate() {
				var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
				return screenWidth - this.$store.state.global.sideBarWidth - 100;
			}
		}
	}
</script>
<style scoped="scoped" language="scss">
	.tagbar {
		background: #FFF;
		border: 1px solid #DCDFE6;
		box-shadow: 0 2px 4px 0 rgba(0, 0, 0, .12), 0 0 6px 0 rgba(0, 0, 0, .04);
	}

	.tagbar-dropdown {
		background-color: #F5F7FA;
		border-bottom: 1px solid #E4E7ED;
		margin: 0;
		height: 40px;
		line-height: 40px;
	}
</style>