<template>
	<div>
		<div class="title">
			<span class="fright">
				<el-link type="primary" :underline="false" @click="taskLocation" title="定位到文件树"><i
						class="el-icon-location-information"></i></el-link>
				<el-link :underline="false" @click="initTaskMenus" title="刷新目录"><i
						class="el-icon-refresh-left"></i></el-link>
			</span>任务中心
		</div>
		<div class="task-tabs">
			<el-tabs tab-position="left" type="border-card" v-model="activeName" @tab-click="handleClick">
				<el-tab-pane label="任务列表" name="first">
					<div class="scrollable-container f12" :style="{height: treeConnerHeight+'px'}">
						<el-tree id="taskTree" ref="taskTree" node-key="id" :data="taskMenus"
							:filter-node-method="filterNode" :highlight-current="true" :auto-expand-parent="true"
							:default-expanded-keys="expandedNodes" @contextmenu.native="handleContextMenu"
							@node-click="taskEdit">
							<template slot-scope="{ node, data }">
								<span style="display: block; width: 100%; text-align: left;" :data-id="data.id"
									:data-pid="data.pid" :data-name="data.name" :data-leval="data.leval"
									:data-taskType="data.taskType" :data-workSpaceType="data.workSpaceType"
									:data-projectId="data.projectId" :data-projectWorkSpaceId="data.projectWorkSpaceId"
									:data-childrenSize="data.childrenSize"
									:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId">
									<!-- el-icon-connection -->
									<template v-if="data.leval<=2">
										<i :id="`taskNode_${data.id}`" :data-id="data.id" :data-pid="data.pid"
											:data-name="data.name" :data-leval="data.leval"
											:data-taskType="data.taskType" :data-workSpaceType="data.workSpaceType"
											:data-projectId="data.projectId"
											:data-projectWorkSpaceId="data.projectWorkSpaceId"
											:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
											:data-childrenSize="data.childrenSize"
											:class="node.expanded?'cblue icon el-icon-folder-opened':'cblue icon el-icon-folder'"></i>
										{{ data.name }}
									</template>
									<template v-if="data.leval==3&&data.taskType==1">
										<i :id="`taskNode_${data.id}`" :data-id="data.id" :data-pid="data.pid"
											:data-name="data.name" :data-leval="data.leval"
											:data-taskType="data.taskType" :data-workSpaceType="data.workSpaceType"
											:data-projectId="data.projectId"
											:data-projectWorkSpaceId="data.projectWorkSpaceId"
											:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
											:data-childrenSize="data.childrenSize"
											class="cblue taskNode icon fa fa-refresh"></i>
										{{ data.name }}
									</template>
									<template v-if="data.leval==3&&data.taskType==2">
										<i :id="`taskNode_${data.id}`" :data-id="data.id" :data-pid="data.pid"
											:data-name="data.name" :data-leval="data.leval"
											:data-taskType="data.taskType" :data-workSpaceType="data.workSpaceType"
											:data-projectId="data.projectId"
											:data-projectWorkSpaceId="data.projectWorkSpaceId"
											:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
											:data-childrenSize="data.childrenSize"
											class="cblue taskNode icon el-icon-coin"></i>
										{{ data.name }}
									</template>
									<template v-if="data.leval==3&&data.taskType==5">
										<i :id="`taskNode_${data.id}`" :data-id="data.id" :data-pid="data.pid"
											:data-name="data.name" :data-leval="data.leval"
											:data-taskType="data.taskType" :data-workSpaceType="data.workSpaceType"
											:data-projectId="data.projectId"
											:data-projectWorkSpaceId="data.projectWorkSpaceId"
											:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
											:data-childrenSize="data.childrenSize"
											class="cblue taskNode icon el-icon-coin"></i>
										{{ data.name }}
									</template>
								</span>
							</template>
						</el-tree>
					</div>
				</el-tab-pane>
				<el-tab-pane label="任务查询" name="second">
					<div style="padding-right: 5px; padding-top: 5px; font-size: 12px;">
						<table width="100%" border="0" cellspacing="0" callpadding="0">
							<tr>
								<td><el-input placeholder="关键字" v-model="taskKeyWord" size="mini" clearable
										@change="clearTask">
										<i slot="prefix" class="el-input__icon el-icon-search"></i>
										<el-button slot="append" icon="el-icon-search" @click="queryTask"></el-button>
									</el-input></td>
							</tr>
						</table>
						<div :style="{height: treeConnerHeight+'px',overflowY: 'auto', overflowX: 'hidden'}">
							<table width="100%" border="0" cellspacing="0" callpadding="0">
								<tr v-for="(data, index) in tableData" :key="data.taskId">
									<td align="left">
										<el-link @click="taskEdit(data)" :underline="false" type="primary"
											v-if="data.workSpaceType==1">
											<i :id="`taskNode_${data.taskId}`" :data-id="data.taskId"
												:data-name="data.taskName" :data-taskType="data.workSpaceType"
												:data-workSpaceType="data.workSpaceType"
												:data-projectId="data.projectId"
												:data-projectWorkSpaceId="data.projectWorkSpaceId"
												:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
												class="cblue taskNode icon fa fa-refresh"></i>
											{{ data.taskName }}
										</el-link>
										<el-link @click="taskEdit(data)" :underline="false" type="primary"
											v-if="data.workSpaceType==2">
											<i :id="`taskNode_${data.taskId}`" :data-id="data.taskId"
												:data-name="data.taskName" :data-taskType="data.workSpaceType"
												:data-workSpaceType="data.workSpaceType"
												:data-projectId="data.projectId"
												:data-projectWorkSpaceId="data.projectWorkSpaceId"
												:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
												class="cblue taskNode icon el-icon-coin"></i>
											{{ data.taskName }}
										</el-link>
										<el-link @click="taskEdit(data)" :underline="false" type="primary"
											v-if="data.workSpaceType==5">
											<i :id="`taskNode_${data.taskId}`" :data-id="data.taskId"
												:data-name="data.taskName" :data-leval="data.leval"
												:data-taskType="data.workSpaceType"
												:data-workSpaceType="data.workSpaceType"
												:data-projectId="data.projectId"
												:data-projectWorkSpaceId="data.projectWorkSpaceId"
												:data-projectWorkSpaceLayerRelId="data.projectWorkSpaceLayerRelId"
												class="cblue taskNode icon el-icon-coin"></i>
											{{ data.taskName }}
										</el-link>
									</td>
								</tr>
								<tr>
									<td><br /><br /></td>
								</tr>
							</table>
						</div>
					</div>
				</el-tab-pane>
			</el-tabs>
		</div>
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
				<div class="text" v-if="menuCreateTask||menuCreateFolder||menuEdit||menuDel"><el-link :underline="false"
						@click="handleMenuItemClick(5)">复制名称</el-link>
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
		name: "workSpaceSidebarContainer",
		components: {
			eFolderForm,
			createTask
		},
		props: {

		},
		computed: {
			taskMenuShow: {
				get() {
					return this.$store.state.global.taskMenuShow
				},
				set(val) {
					this.$store.commit("global/global/setTaskMenuShow", val)
				}
			},
			taskMenus: {
				get() {
					return this.$store.state.global.taskMenus;
				},
				set(val) {
					this.$store.commit("global/global/setTaskMenu", val)
				}
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
					let task = tasks.find(item => item.taskId == this.getTaskActiveTab());
					if (!task) {
						task = tasks[tasks.length - 1];
					}
					this.routeView(task);
				}
			}
		},
		data() {
			return {
				tableData: [],
				taskKeyWord: undefined,
				activeName: 'first',
				pid: "0",
				name: "",
				leval: 0,
				workSpaceType: 0,
				projectId: "",
				projectWorkSpaceId: "",
				projectWorkSpaceLayerRelId: "",
				childrenSize: 0,
				menuDel: false,
				menuCreateTask: false,
				menuCreateFolder: false,
				menuEdit: false,
				filterText: '',
				menuTop: 0,
				menuLeft: 0,
				treeConnerHeight: 600,
				expandedNodes: [],
				taskType: 0,
				taskTabs: [],
				taskActiveTab: '0',
				taskNodes: []
			}
		},
		created() {
			this.initMenuDefaultStatus();
		},
		mounted() {
			this.projectId = this.$route.query.projectId;
			this.projectWorkSpaceId = this.$route.query.projectWorkSpaceId;
			this.workSpaceType = this.$route.query.workSpaceType;
			this.taskId = this.$route.query.taskId;
			this.initTreeHeight();
			this.initTaskMenus(this.projectId, this.projectWorkSpaceId, this.workSpaceType, this.taskId, () => {});
			window.addEventListener('resize', this.initTreeHeight);
		},
		beforeDestroy() {
			window.removeEventListener('resize', this.initTreeHeight);
		},
		methods: {
			handleClick(tab) {

			},
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
				this.name = el.getAttribute("data-name");
				this.leval = parseInt(el.getAttribute("data-leval"));
				this.workSpaceType = el.getAttribute("data-workSpaceType");
				this.projectId = el.getAttribute("data-projectId");
				this.projectWorkSpaceId = el.getAttribute("data-projectWorkSpaceId");
				this.projectWorkSpaceLayerRelId = el.getAttribute("data-projectWorkSpaceLayerRelId");
				this.childrenSize = parseInt(el.getAttribute("data-childrenSize"));

				let menuHeight = 130;

				if (this.leval == 1) {
					this.menuCreateFolder = true;
				}
				if (this.leval == 2) {
					menuHeight = 140;
					if (this.childrenSize == 0) {
						this.menuCreateTask = true;
						menuHeight = 150;
					}
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
				if (this.menuCreateTask || this.menuCreateFolder || this.menuEdit || this.menuDel) {
					this.taskMenuShow = true; // 显示菜单  
				}
			},
			handleMenuItemClick(index) {
				console.log(this.leval);
				if (index == 1) {
					this.$refs.createTask.form.projectId = this.projectId;
					this.$refs.createTask.form.projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
					this.$refs.createTask.form.taskFolderId = this.id;
					this.$refs.createTask.form.projectWorkSpaceId = this.projectWorkSpaceId;
					this.$refs.createTask.form.workSpaceType = this.workSpaceType;
					this.$refs.createTask.dialogVisible = true;
					this.taskMenuShow = false;
					return;
				}
				if (index == 2) {
					this.$refs.eFolderForm.form.taskFolderPid = this.taskFolderPid;
					this.$refs.eFolderForm.form.workSpaceType = this.workSpaceType;
					this.$refs.eFolderForm.form.projectId = this.projectId;
					this.$refs.eFolderForm.form.projectWorkSpaceId = this.projectWorkSpaceId;
					this.$refs.eFolderForm.form.projectWorkSpaceLayerRelId = this.projectWorkSpaceLayerRelId;
					if (this.leval == 2) {
						this.$refs.eFolderForm.form.taskFolderPid = this.id;
					}
					this.$refs.eFolderForm.dialogVisible = true;
					this.taskMenuShow = false;
					return;
				}
				if (index == 3) {
					switch (this.leval) {
						case 2:
							this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskFolder/getById", {
								"taskFolderId": this.id
							}).then(res => {
								this.$refs.eFolderForm.form.workSpaceType = res.data.workSpaceType;
								this.$refs.eFolderForm.form.projectId = res.data.projectId;
								this.$refs.eFolderForm.form.projectWorkSpaceId = res.data.projectWorkSpaceId;
								this.$refs.eFolderForm.form.projectWorkSpaceLayerRelId = res.data
									.projectWorkSpaceLayerRelId;
								this.$refs.eFolderForm.form.taskFolderPid = res.data.taskFolderPid;
								this.$refs.eFolderForm.form.taskFolderId = res.data.taskFolderId;
								this.$refs.eFolderForm.form.folderName = res.data.folderName;
								this.$refs.eFolderForm.form.name = res.data.folderName;
								this.$refs.eFolderForm.dialogVisible = true;
								this.taskMenuShow = false;
							}).catch(err => {

							});
							break;
						case 3:
							this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/getById", {
								"taskId": this.id
							}).then(
								res => {
									this.$refs.createTask.title = '编辑';
									this.$refs.createTask.form.taskId = res.data.taskId;
									this.$refs.createTask.form.taskFolderId = res.data.taskFolderId;
									this.$refs.createTask.form.projectId = res.data.projectId;
									this.$refs.createTask.form.projectWorkSpaceLayerRelId = res.data
										.projectWorkSpaceLayerRelId;
									this.$refs.createTask.form.taskName = res.data.taskName;
									this.$refs.createTask.form.remark = res.data.remark;
									this.$refs.createTask.form.workSpaceType = res.data.workSpaceType;
									this.$refs.createTask.form.projectWorkSpaceId = res.data.projectWorkSpaceId;
									this.$refs.createTask.dialogVisible = true;
									this.taskMenuShow = false;
								}).catch(err => {});
							break;
					}
					return;
				}
				if (index == 4) {
					this.deleteMenuById();
					return;
				}
				if (index == 5) {
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
				let eventType = 3;
				let params = {};
				switch (this.leval) {
					case 2:
						params = {
							"taskFolderId": this.id
						}
						break;
					case 3:
						params = {
							"taskId": this.id
						}
						tips = "您确定要删除任务吗?<br>该操作不可恢复!!";
						uri = "/admin/etl/projectTask/deleteById";
						eventType = 30;
						break;
				}

				this.$confirm(tips, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() + uri, params).then(
						res => {
							let node = {
								"projectId": res.data.projectId,
								"projectWorkSpaceId": res.data.projectWorkSpaceId,
								"workSpaceType": res.data.workSpaceType,
								"taskFolderId": this.pid,
							};
							if (eventType == 30) {
								node.taskFolderId = res.data.taskFolderId;
							}
							this.eventCallBack(eventType, node);
						}).catch(err => {});
				});
			},
			taskLocation() {
				let tabs = this.getTaskTabs();
				let taskActiveTab = this.getTaskActiveTab();
				if (tabs.length <= 0) {
					return;
				}
				let task = tabs.find(item => item.taskId == taskActiveTab);
				if (task) {
					this.projectId = task.projectId;
					this.projectWorkSpaceId = task.projectWorkSpaceId;
					this.workSpaceType = task.workSpaceType;
					this.taskId = task.taskId;
					this.initTaskMenus(this.projectId, this.projectWorkSpaceId, this.workSpaceType, this.taskId, () => {});
					this.$emit("sidebarCallBack", task.projectWorkSpaceId);
					this.activeName = "first";
				}
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
				return localStorage.getItem(this.getSessionTabKey(this.$store.state.global
					.taskTableIndexName));
			},
			taskEdit(data) {
				if (data.leval == 3) {
					let task = {
						"taskId": data.id ? data.id : data.taskId,
						"taskName": data.name ? data.name : data.taskName,
						"projectId": data.projectId,
						"projectWorkSpaceLayerRelId": data.projectWorkSpaceLayerRelId,
						"projectWorkSpaceId": data.projectWorkSpaceId,
						"workSpaceType": data.workSpaceType
					};
					this.addTabs(task);
					this.routeView(task);
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
							"projectWorkSpaceLayerRelId": task.projectWorkSpaceLayerRelId,
							"projectWorkSpaceId": task.projectWorkSpaceId,
							"workSpaceType": task.workSpaceType,
							"isClose": true
						};
						tabs.push(tab);
					}
					this.setTaskActiveTab(tab.taskId);
					this.setTaskTabs(tabs);
				}
			},
			routeView(task) {
				if (task) {
					let param = {
						taskId: task.taskId,
						projectId: task.projectId,
						projectWorkSpaceLayerRelId: task.projectWorkSpaceLayerRelId,
						projectWorkSpaceId: task.projectWorkSpaceId,
						workSpaceType: task.workSpaceType
					};
					this.$emit("openTaskNavbar", param);
				} else {
					let param = {
						taskId: this.$store.state.global.defaultTaskId,
						projectId: this.projectId,
						projectWorkSpaceLayerRelId: this.projectWorkSpaceLayerRelId,
						projectWorkSpaceId: this.projectWorkSpaceId,
						workSpaceType: this.workSpaceType
					};
					this.$emit("openTaskNavbar", param);
				}
			},
			getSessionTabKey(keyName) {
				let projectId = this.$route.query.projectId;
				return keyName.concat("_").concat(projectId);
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
			getTaskActiveTab() {
				this.taskActiveTab = localStorage.getItem(this.getSessionTabKey(this.$store.state.global
					.taskTableIndexName));
				return this.taskActiveTab;
			},
			setTaskActiveTab(val) {
				localStorage.setItem(this.getSessionTabKey(this.$store.state.global.taskTableIndexName), val);
				this.taskActiveTab = val;
			},
			eventCallBack(eventType, node) {
				switch (eventType) {
					case 1: //添加
						this.initTaskMenus(node.projectId, node.projectWorkSpaceId, node.workSpaceType, node.taskFolderId,
							() => {});
						this.$message({
							message: '文件夹添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.initTaskMenus(node.projectId, node.projectWorkSpaceId, node.workSpaceType, node.taskFolderId,
							() => {});
						this.$message({
							message: '文件夹修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.initTaskMenus(node.projectId, node.projectWorkSpaceId, node.workSpaceType, node.taskFolderId,
							() => {});
						this.$message({
							message: '文件夹删除成功!',
							type: 'success'
						});
						break;
					case 10: //添加
						this.initTaskMenus(node.projectId, node.projectWorkSpaceId, node.workSpaceType, node.taskId,
							() => {
								let data = {
									"taskId": node.taskId,
									"taskName": node.taskName,
									"projectId": node.projectId,
									"projectWorkSpaceLayerRelId": node.projectWorkSpaceLayerRelId,
									"projectWorkSpaceId": node.projectWorkSpaceId,
									"workSpaceType": node.workSpaceType,
									"leval": 3
								}
								this.taskEdit(data);
							});
						this.$message({
							message: '任务添加成功!',
							type: 'success'
						});
						break;
					case 20: //修改
						this.initTaskMenus(node.projectId, node.projectWorkSpaceId, node.workSpaceType, node.taskId,
							() => {});
						this.$message({
							message: '任务修改成功!',
							type: 'success'
						});
						break;
					case 30: //删除
						this.initTaskMenus(node.projectId, node.projectWorkSpaceId, node.workSpaceType, node.taskFolderId,
							() => {});
						this.$message({
							message: '任务删除成功!',
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
			initTaskMenus(projectId, projectWorkSpaceId, workSpaceType, taskId, callBack) {
				this.$store.dispatch(
					"etl/task/initProjectTree", {
						"projectId": projectId,
						"projectWorkSpaceId": projectWorkSpaceId,
						"workSpaceType": workSpaceType
					}).then(
					res => {
						this.taskMenus = res.data.taskTree;
						this.taskNodes = res.data.taskNodes;
						if (taskId) {
							this.openNode(taskId);
						}
						callBack();
					}).catch(error => {});
			},
			initTreeHeight() {
				this.treeConnerHeight = window.innerHeight - 100;
			},
			clearTask() {
				this.tableData = [];
			},
			queryTask() {
				if (!this.taskKeyWord) {
					this.$message.error('请输入关键字!');
					return;
				}
				this.tableData = [];
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/queryTask", {
					"keyword": this.taskKeyWord,
					"projectId": this.projectId
				}).then(res => {
					this.loading = false;
					this.tableData = res.data;
				}).catch(error => {
					this.loading = false;
				});
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
	::v-deep .el-tree {
		background: #F5F5F5;

		.el-tree-node {
			.el-tree-node__children {
				overflow: visible;
			}
		}
	}

	::v-deep .el-tabs__item {
		min-width: 10px;
		white-space: nowrap;
		text-align: center;
	}
</style>