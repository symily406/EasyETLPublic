<template>
	<div>
		<div class="search">
			<el-input placeholder="关键字" v-model="filterText" size="mini" clearable></el-input>
		</div>
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height: treeConnerHeight+'px'}">
			<el-tree id="taskTree" ref="taskTree" node-key="id" :data="taskMenus" :filter-node-method="filterNode"
				:highlight-current="true" :auto-expand-parent="true" :default-expanded-keys="expandedNodes"
				@contextmenu.native="handleContextMenu" @node-click="taskEdit">
				<template slot-scope="{ node, data }">
					<span :data-id="data.id" :data-leval="data.leval" :data-taskType="data.taskType"
						:data-childrenSize="data.childrenSize" :data-projectId="data.projectId"
						:data-layerId="data.layerId" :data-name="data.name" :data-pid="data.pid"
						:data-calculateTypes="data.calculateTypes">
						<!-- el-icon-connection -->
						<template v-if="data.leval<=2">
							<i :data-id="data.id" :data-leval="data.leval" :data-taskType="data.taskType"
								:data-childrenSize="data.childrenSize" :data-name="data.name"
								:data-projectId="data.projectId" :data-layerId="data.layerId" :data-pid="data.pid"
								:data-calculateTypes="data.calculateTypes"
								:class="node.expanded?'icon el-icon-folder-opened':'icon el-icon-folder'"></i>
							{{ data.name }}
						</template>
						<template v-if="data.leval==3&&data.taskType==1">
							<i :id="`taskNode_${data.id}`" :data-id="data.id" :data-leval="data.leval"
								:data-taskType="data.taskType" :data-childrenSize="data.childrenSize"
								:data-name="data.name" :data-projectId="data.projectId" :data-layerId="data.layerId"
								:data-pid="data.pid" :data-calculateTypes="data.calculateTypes"
								class="taskNode icon fa fa-circle-o-notch"></i>
							{{ data.name }}
						</template>
						<template v-if="data.leval==3&&data.taskType==2">
							<i :id="`taskNode_${data.id}`" :data-id="data.id" :data-leval="data.leval"
								:data-taskType="data.taskType" :data-childrenSize="data.childrenSize"
								:data-name="data.name" :data-projectId="data.projectId" :data-layerId="data.layerId"
								:data-pid="data.pid" :data-calculateTypes="data.calculateTypes"
								class="taskNode icon fa fa-circle"></i>
							{{ data.name }}
						</template>
					</span>
				</template>
			</el-tree>
		</el-scrollbar>
		<div ref="treeMenu" class="task-tree-menu" v-show="taskMenuShow"
			:style="{ top: menuTop + 'px', left: menuLeft + 'px' }">
			<el-card class="box-card">
				<div class="text" v-if="menuCreateTask"><el-link :underline="false"
						@click="handleMenuItemClick(1)">新建任务</el-link></div>
				<div class="text" v-if="menuCreateFolder"><el-link :underline="false"
						@click="handleMenuItemClick(2)">新建文件夹</el-link></div>
				<div class="text" v-if="menuEdit"><el-link :underline="false"
						@click="handleMenuItemClick(3)">编辑</el-link>
				</div>
				<div class="text" v-if="menuDel"><el-link :underline="false"
						@click="handleMenuItemClick(4)">删除</el-link>
				</div>
			</el-card>
		</div>
		<eFolderForm ref="eFolderForm" dialogTitle="新建文件夹" dialogWidth="600px" dialogHeight="150px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<createTask ref="createTask" dialogTitle="新建同步任务" dialogWidth="600px" dialogHeight="250px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>

<script>
	import eFolderForm from '../../../../../admin/etl/projectFolder/module/folderForm.vue';
	import createTask from '../../../../../admin/etl/projectTask/module/createTask.vue';
	export default {
		name: "taskSidebarContainer",
		components: {
			eFolderForm,
			createTask
		},
		props: {

		},
		computed: {
			taskMenuShow: {
				get() { return this.$store.state.global.taskMenuShow },
				set(val) { this.$store.commit("global/global/setTaskMenuShow", val) }
			},
			taskMenus: {
				get() {
					return this.$store.state.global.taskMenus;
				},
				set(val) { this.$store.commit("global/global/setTaskMenu", val) }
			}
		},
		watch: {
			$route: 'handleRoute',
			filterText(val) {
				this.$refs.taskTree.filter(val);
			},
			taskMenuShow(val) {
				if (!val) {
					this.initMenuDefaultStatus();
				}
			},
			taskMenus(newVal, oldVal) {
				let tasks = this.getTaskTabs();
				if (tasks.length > 0) {
					let task = tasks[tasks.length - 1];
					this.routeView(task);
				}
			}
		},
		data() {
			return {
				id: '',
				pid: '',
				leval: '',
				taskType: '',
				childrenSize: 0,
				name: '',
				projectId: '',
				layerId: '',
				menuDel: false,
				menuCreateTask: false,
				menuCreateFolder: false,
				menuEdit: false,
				filterText: '',
				menuTop: 0,
				menuLeft: 0,
				treeConnerHeight: 600,
				expandedNodes: [],
				calculateTypes: 0,
				taskTabs: [],
				taskActiveTab: '0',
				taskNodes: []
			}
		},
		created() {
			this.initMenuDefaultStatus();
		},
		mounted() {
			this.initTreeHeight();
			this.initTaskMenus();
			window.addEventListener('resize', this.initTreeHeight);
		},
		beforeDestroy() {
			window.removeEventListener('resize', this.initTreeHeight);
		},
		methods: {
			handleRoute(route) {
				if (route.query.taskId) {
					this.openNode(route.query.taskId);
					return;
				}
			},
			activeMenu() {

			},
			handleContextMenu(event) {
				event.preventDefault();
				this.initMenuDefaultStatus();
				const el = event.target;
				this.id = el.getAttribute("data-id");
				if (!this.id) {
					return;
				}
				this.pid = el.getAttribute("data-pid");
				this.leval = parseInt(el.getAttribute("data-leval"));
				this.taskType = el.getAttribute("data-taskType");;
				this.childrenSize = parseInt(el.getAttribute("data-childrenSize"));
				this.projectId = el.getAttribute("data-projectId");
				this.layerId = el.getAttribute("data-layerId");
				this.name = el.getAttribute("data-name");
				this.calculateTypes = el.getAttribute("data-calculateTypes");
				let menuHeight = 130;

				if (this.leval == 1) {
					this.menuCreateTask = true;
					this.menuCreateFolder = true;
				}
				if (this.leval == 2) {
					menuHeight = 150;
					this.menuCreateTask = true;
					this.menuCreateFolder = true;
					this.menuEdit = true;
					this.menuDel = true;
				}
				if (this.leval == 3) {
					this.menuEdit = true;
					this.menuDel = true;
				}
				var offset = el.getBoundingClientRect();
				let clientY = offset.top - 40;
				if (window.innerHeight - clientY < menuHeight) {
					clientY = clientY - menuHeight;
				}
				this.menuTop = clientY;
				let clientX = event.clientX - 50;
				if (clientX < 20) {
					clientX = 20;
				}
				this.menuLeft = clientX;
				this.taskMenuShow = true; // 显示菜单  
			},
			handleMenuItemClick(index) {
				if (index == 1) {
					this.$refs.createTask.title = '新建';
					this.$refs.createTask.form.taskFolderId = this.id;
					this.$refs.createTask.form.projectId = this.projectId;
					this.$refs.createTask.form.layerId = this.layerId;
					this.$refs.createTask.form.calculateTypes = this.calculateTypes;
					this.$refs.createTask.dialogVisible = true;
					this.$refs.createTask.form.taskType = this.taskType;
				}
				if (index == 2) {
					this.$refs.eFolderForm.form.pid = this.id;
					this.$refs.eFolderForm.form.projectId = this.projectId;
					this.$refs.eFolderForm.form.layerId = this.layerId;
					this.$refs.eFolderForm.form.calculateTypes = this.calculateTypes;
					this.$refs.eFolderForm.dialogVisible = true;
					this.taskMenuShow = false;
					return;
				}
				if (index == 3) {
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/getById", { "taskId": this.id }).then(
						res => {
							this.$refs.createTask.title = '编辑';
							this.$refs.createTask.form.taskId = res.data.taskId;
							this.$refs.createTask.form.taskFolderId = res.data.taskFolderId;
							this.$refs.createTask.form.projectId = res.data.projectId;
							this.$refs.createTask.form.layerId = res.data.layerId;
							this.$refs.createTask.form.taskName = res.data.taskName;
							this.$refs.createTask.form.remark = res.data.remark;
							this.$refs.createTask.dialogVisible = true;
							this.$refs.createTask.form.taskType = this.taskType;
							this.taskMenuShow = false;
						}).catch(err => {});
					return;
				}
				if (index == 4) {
					this.deleteMenuById();
					return;
				}
			},
			initMenuDefaultStatus() {
				this.id = '';
				this.leval = '';
				this.taskType = '';
				this.childrenSize = 0;
				this.name = '';
				this.projectId = '';
				this.layerId = '';
				this.menuDel = false;
				this.calculateTypes = 0;
				this.menuCreateTask = false;
				this.menuCreateFolder = false;
				this.menuEdit = false;
			},
			deleteMenuById() {
				let tips = "您确定要删除文件夹吗?<br>该操作不可恢复!!";
				let uri = "/admin/etl/projectTaskFolder/deleteById";
				let params = {};
				if (this.leval == 2) {
					params = {
						"taskFolderId": this.id
					}
				}
				this.$confirm(tips, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + uri, params).then(
						res => {
							this.eventCallBack(3, this.pid);
						}).catch(err => {});
				});
			},
			taskEdit(data) {
				if (data.leval == 3) {
					let task = {
						taskId: data.id,
						taskName: data.name,
						projectId: data.projectId,
						calculateTypes: data.calculateTypes,
						taskType: data.taskType
					};
					this.routeView(task);
					this.addTabs(task);
				}
			},
			addTabs(task) {
				if (task) {
					let tabs = this.getTaskTabs();
					let tab = tabs.filter(item => item.taskId === task.taskId)[0];
					if (!tab) {
						tab = {
							"taskId": task.taskId,
							"taskName": task.taskName,
							"projectId": task.projectId,
							"calculateTypes": task.calculateTypes,
							"isClose": true,
							"taskType": task.taskType
						};
						tabs.push(tab);
						this.setTaskTabs(tabs);
					}
					this.setTaskActiveTab(tab.taskId);
				}
			},
			routeView(task) {
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
					let projectId = this.$route.query.projectId;
					let calculateTypes = this.$route.query.calculateTypes;
					this.$router.push({
						path: "/task",
						query: {
							"projectId": projectId,
							"calculateTypes": calculateTypes
						}
					});
				}
			},
			getSessionTabKey(keyName) {
				let projectId = this.$route.query.projectId;
				let calculateTypes = this.$route.query.calculateTypes;
				return keyName.concat("_").concat(projectId).concat("_").concat(calculateTypes);
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
				this.taskTabs = tabs;
				return tabs;
			},
			setTaskActiveTab(val) {
				localStorage.setItem(this.getSessionTabKey(this.$store.state.global.taskTableIndexName), val);
				this.taskActiveTab = val;
			},
			eventCallBack(eventType, nodeId) {
				switch (eventType) {
					case 1: //添加
						this.initTaskMenus();
						this.openNode(nodeId);
						this.$message({
							message: '文件夹添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.initTaskMenus();
						this.openNode(nodeId);
						this.$message({
							message: '文件夹修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.initTaskMenus();
						this.$message({
							message: '文件夹删除成功!',
							type: 'success'
						});
						break;
					case 10: //添加
						this.initTaskMenus();
						this.openNode(nodeId);
						this.$message({
							message: '同步任务添加成功!',
							type: 'success'
						});
						break;
					case 20: //修改
						this.initTaskMenus();
						this.openNode(nodeId);
						this.$message({
							message: '同步任务修改成功!',
							type: 'success'
						});
						break;
					case 30: //删除
						this.initTaskMenus();
						this.openNode(nodeId);
						this.$message({
							message: '同步任务删除成功!',
							type: 'success'
						});
						break;
				}
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			openNode(nodeId) {
				this.expandedNodes = [nodeId];
			},
			initTaskMenus() {
				let projectId = this.$route.query.projectId;
				let calculateTypes = this.$route.query.calculateTypes;
				let taskId = this.$route.query.taskId;
				this.$store.dispatch(
					"etl/task/initProjectTree", { "projectId": projectId, "calculateTypes": calculateTypes }).then(
					res => {
						this.taskMenus = res.data.taskTree;
						this.taskNodes = res.data.taskNodes;
						if (taskId) {
							this.openNode(taskId);
						}
					}).catch(error => {});
			},
			initTreeHeight() {
				this.treeConnerHeight = window.innerHeight - 100;
			},
			filterNode(value, data) {
				if (!value) {
					return true
				}
				return data.name.indexOf(value) !== -1;
			}
		}
	}
</script>
<style lang="scss" scoped>

</style>