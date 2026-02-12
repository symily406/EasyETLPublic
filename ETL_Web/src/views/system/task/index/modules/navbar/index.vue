<template>
	<div>
		<el-header>
			<div class="tagbar">
				<table border="0" width="100%" cellspacing="0" callpadding="0">
					<tr>
						<td width="10"><i class="el-icon-arrow-left"></i></td>
						<td>
							<el-tabs v-model="taskActiveTab" type="border-card" @tab-click="handleClick"
								@tab-remove="tabRemoveClick">
								<el-tab-pane :key="item.taskId" v-for="(item, index) in taskTabs" :label="item.taskName"
									:name="item.taskId" :closable="item.isClose">
									{{item.taskName}}
								</el-tab-pane>
							</el-tabs>
						</td>
						<td width="10"><i class="el-icon-arrow-right"></i></td>
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
	import { mapState } from 'vuex';
	export default {
		name: "task_navbar",
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
				get() { return this.$store.state.global.taskMenuShow },
				set(val) { this.$store.commit("global/global/setTaskMenuShow", val) }
			}
		},
		data() {
			return {
				taskActiveTab: '0',
				taskTabs: [],
				taskId: '',
				projectId:'',
				calculateTypes:'',
			}
		},
		mounted() {
			this.init();
		},
		watch: {
			$route: 'handleRoute',
			taskId(newVal, oldVal) {
				this.setTaskActiveTab(newVal);
			},
			taskActiveTab(newVal, oldVal){
				this.routeView(newVal);
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
				this.init();
			},
			init() {
				this.projectId =this.$route.query.projectId;
				this.calculateTypes = this.$route.query.calculateTypes;
				this.taskId=this.$route.query.taskId;
				this.taskTabs = this.getTaskTabs();
			},
			destroy(){
				this.taskActiveTab='0';
				this.taskTabs= [];
				this.taskId='';
				this.projectId='';
				this.calculateTypes='';
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
						this.setTaskActiveTab("0");
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
				let taskActiveTab = '0';
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
				this.setTaskTabs(tabs);
				this.setTaskActiveTab(taskActiveTab);
				this.routeView(taskActiveTab);
			},
			routeView(id) {
				let task = this.taskTabs.filter(item => item.taskId === id)[0];
				if (task) {
					let routerPath = {};
					switch (parseInt(task.taskType)) {
						case 1:
							routerPath = {
								path: "/dataxTask",
								query: {
									"projectId": task.projectId,
									"calculateTypes": task.calculateTypes,
									"taskId": task.taskId
								}
							};
							break;
						case 2:
							routerPath = {
								path: "/sqlTask",
								query: {
									"projectId": task.projectId,
									"calculateTypes": task.calculateTypes,
									"taskId": task.taskId
								}
							};
							break;
					}
					this.$router.push(routerPath);
				} else {
					let projectId = this.projectId;
					let calculateTypes = this.calculateTypes;
					this.$router.push({
						path: "/task",
						query: {
							"projectId": projectId,
							"calculateTypes": calculateTypes
						}
					});
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
				let projectId = this.projectId;
				let calculateTypes = this.calculateTypes;
				return keyName.concat("_").concat(projectId).concat("_").concat(calculateTypes);
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