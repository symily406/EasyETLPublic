<template>
	<div class="app-container" ref="appContainer">
		<el-card class="box-card card-panel">
			<div slot="header">
				<el-button type="primary" @click="uploadFile" size="mini" icon="el-icon-upload2"
					v-if="checkPermission('admin:system:file:fileChunk:upload')">上传
				</el-button>
				<el-button type="primary" plain size="mini" @click="createFolder" icon="el-icon-folder"
					v-if="checkPermission('admin:system:file:userResources:createFoler')">新建文件夹
				</el-button>
				<span class="fright">
					<el-link :underline="false" style="margin-top: 5px; margin-right: 15px; font-size: 16px;"
						title="传输列表" @click="openUploadFile">
						<el-badge :value="uploadNum" v-show="uploadNum>0">
							<i class="el-icon-sort"></i>
						</el-badge>
						<i class="el-icon-sort" v-show="uploadNum==0"></i>
					</el-link>
					<el-link :underline="false" style="margin-top: 5px; margin-right: 15px; font-size: 16px;" title="刷新"
						@click="refreshPage">
						<i class="el-icon-refresh"></i>
					</el-link>
				</span>
			</div>
			<el-breadcrumb separator="/">
				<el-breadcrumb-item v-for="(item, index) in folderPath" :key="index">
					<el-link class="cursor" :underline="false" @click="navOpenFolder(item)">{{item.name}}</el-link>
				</el-breadcrumb-item>
			</el-breadcrumb>
			<el-scrollbar wrap-class="scrollbar-wrapper" v-loading="loading" :style="{height:panelHeight+'px'}"
				class="file-list-panel">
				<div class="infinite-list" v-infinite-scroll="scrollLoadFile">
					<el-row id="file-list-row">
						<el-col :xl="2" :lg="3" :md="4" :sm="6" :xs="8" v-for="(item, index) in tableData" :key="index">
							<div class="file-list-item cursor" @contextmenu="showMenu(item.id,item.sourceType)">
								<div :style="{height:filePanelHeight+'px',lineHeight:filePanelHeight+'px'}">
									<div class="file-image-panle tcenter">
										<el-image class="icon-image" v-if="item.sourceType==1"
											:src="fileIcon(item.filePath,item.sourceType,item.suffix)"
											:title="item.name" @click="fileIconClick(item.id,item.sourceType)"
											fit="contain"></el-image>
										<img class="icon-image" v-else
											:src="fileIcon(item.filePath,item.sourceType,item.suffix)"
											:title="item.name" @click="fileIconClick(item.id,item.sourceType)" />

									</div>
									<div :id="'file_item_'+item.id" class="file-select"
										@click="fileIconClick(item.id,item.sourceType)">
										<i class="el-icon-check"></i>
									</div>
								</div>
								<div class="tcenter file-title" :title="item.name">
									{{item.name}}
								</div>
								<vue-context-menu :id="'menu_'+item.id" :contextMenuData="contextMenuData"
									:transferIndex="transferIndex" @openFolder="openFolder(item.id)"
									@rename="rename(item.id)" @del="del(item.id)" @showAttr="showAttr(item.id)">
								</vue-context-menu>

							</div>
						</el-col>
					</el-row>
					<div class="file-list-empty" v-show="tableData.length==0">
						<img src="/static/images/file_automatic@3x.png" />
						<div>空空如也</div>
					</div>
				</div>
			</el-scrollbar>
		</el-card>
		<folderForm ref="folderForm" dialogTitle="文件夹" dialogWidth="400px" dialogHeight="300px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<fileRename ref="fileRename" dialogTitle="文件重命名" dialogWidth="400px" dialogHeight="300px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<fileUpload ref="fileUpload" @eventCallBack="eventCallBack"></fileUpload>
		<fileAttr ref="fileAttr"></fileAttr>

	</div>
</template>
<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import eHeader from './module/header';
	import folderForm from './module/folderForm';
	import fileRename from './module/fileRename';
	import fileUpload from './module/fileUpload';
	import fileAttr from './module/fileAttr';
	import {
		geFileAccept
	} from "@/utils/localStore.js";
	export default {
		components: {
			eHeader,
			folderForm,
			fileRename,
			fileUpload,
			fileAttr
		},
		props: {

		},
		mixins: [indexMixin],
		watch: {
			uploadRefresh() {
				console.log("刷新列表页");
			},
			tableWidth() {
				this.initFilePanelHeight();
			},
			openSelect(newVal, oldVal) {
				this.page.pageNumber = 1;
				this.page.totalPage = 1;
				this.fileSelectHide();
				if (newVal) {
					this.initData(this.queryParams);
				}
			}
		},
		computed: {
			uploadNum() {
				return this.$store.state.global.uploadNum;
			},
			uploadRefresh() {
				return this.$store.state.global.uploadRefresh;
			},
			tableWidth() {
				return this.$store.state.global.tableWidth;
			}
		},
		data() {
			return {
				transferIndex: null, // Show the menu that was clicked
				contextMenuData: {
					menuName: 'file_menu',
					//菜单显示的位置
					axis: {
						x: null,
						y: null
					},
					//菜单选项
					menulists: [{
						fnHandler: 'openFolder', //绑定事件
						icoName: 'el-icon-folder-opened',
						btnName: '打开' //菜单名称
					}, {
						fnHandler: 'rename',
						icoName: 'el-icon-edit',
						btnName: '重命名'
					}, {
						fnHandler: 'del',
						icoName: 'el-icon-delete',
						btnName: '删除'
					}, {
						fnHandler: 'showAttr',
						btnName: '属性'
					}]
				},
				menus: [],
				folderTreeMenuDisplay: false,
				id: '0',
				treeDate: null,
				panelHeight: 100,
				folderTree: [],
				parentId: '0',
				fullPath: '0',
				tableData: [],
				filePanelHeight: 184,
				clientWidth: 0,
				queryParams: {
					parentId: '0',
					//文件名
					fileName: '',
					fileTypes: ''
				},
				selectFiles: [],
				folderPath: [],
				//是否是选择窗
				openSelect: false,
				//是否多选
				multiSelect: false,
				fileTypes: ''
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initPanelHeight();
				this.initFilePanelHeight();
				this.tableData = [];
			});
		},
		created() {
			let that = this;
			window.addEventListener('resize', function() {
				that.initPanelHeight();
				that.initFilePanelHeight();
			});
		},
		destroyed() {
			let that = this;
			window.removeEventListener('resize', function() {
				that.initPanelHeight();
				that.initFilePanelHeight();
			});
		},
		methods: {
			scrollLoadFile() {
				if (!this.openSelect) {
					if (this.page.pageNumber < this.page.totalPage) {
						this.initData(this.queryParams);
					}
				}
			},
			initData(queryParams) {
				this.loading = true;
				queryParams.fileTypes = this.fileTypes;
				this.$ajax.get(this.getContentPath() + "/admin/system/file/userResources/queryPage", this.addPageParam(
					queryParams, this
					.page)).then(
					res => {
						this.loading = false;
						this.page = this.$app.extend(this.page, this.$app.setPage(res.data.data));
						this.tableData = this.tableData.concat(res.data.data.records);
						let folderPath = [{
							"id": "0",
							"name": "全部文件",
							"fullPath": "0"
						}];
						if (res.data.folderPath) {
							for (let i = 0; i < res.data.folderPath.length; i++) {
								folderPath.push({
									"id": res.data.folderPath[i].id,
									"name": res.data.folderPath[i].name,
									"fullPath": res.data.folderPath[i].fullPath
								});
							}
						}
						this.folderPath = folderPath;
					}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.tableData = [];
				this.initData(this.queryParams);
			},
			refreshPage() {
				this.tableData = [];
				this.queryParams = this.$options.data().queryParams;
				this.queryParams.parentId = this.parentId;
				this.initData(this.queryParams);
			},
			searchPage() {
				this.tableData = [];
				this.initData(this.queryParams);
			},
			uploadFile() {
				let that = this;
				this.$refs['fileUpload'].parentId = this.parentId;
				this.$refs['fileUpload'].fileTypes = this.fileTypes;
				this.$store.dispatch('system/login/refreshToken', {
					"fileTypes": this.fileTypes
				}).then(res => {
					this.$refs['fileUpload'].fileAccept = geFileAccept();
					let index = that.$store.state.global.uploadFileIndex + 1;
					that.$store.commit("global/global/uploadFile", index);
				}).catch(error => {

				});
			},
			openUploadFile() {
				this.$refs['fileUpload'].panelShow = true;
			},
			deleteFolder(nodeData) {
				this.$confirm('您确定要删除文件夹吗?<br>该操作将删除文件夹及文件夹下的所有文件!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
							"/admin/system/file/fileFolder/deleteById", {
								"folderId": nodeData.folderId
							})
						.then(res => {
							this.eventCallBack(30);
						}).catch(err => {});
				});
			},
			showMenu(id, sourceType) {
				this.transferIndex = this.getTransferIndex(id); // tranfer index to child component
				event.preventDefault();
				var x = event.clientX;
				var y = event.clientY;
				let menu = document.getElementById("menu_" + id);
				if (parseInt(sourceType) > 0) {
					if (menu) {
						menu.querySelectorAll('li')[0].style.display = "none";
					}
				}
				if (!this.checkPermission('admin:system:file:userResources:edit')) {
					if (menu) {
						menu.querySelectorAll('li')[1].style.display = "none";
					}
				}
				if (!this.checkPermission('admin:system:file:userResources:deleteById')) {
					if (menu) {
						menu.querySelectorAll('li')[2].style.display = "none";
					}
				}
				this.contextMenuData.axis = {
					x,
					y
				};
				this.menuHide();
				this.menus.push(id);
			},
			menuHide() {
				for (let i = 0; i < this.menus.length; i++) {
					let dom = document.getElementById("menu_" + this.menus[i]);
					if (dom) {
						dom.style.display = "none";
					}
				}
				this.menus = [];
			},
			getTransferIndex(id) {
				let index = 0;
				for (let i = 0; i < this.tableData.length; i++) {
					if (this.tableData[i].id == id) {
						index = i;
					}
				}
				return index;
			},
			createFolder() {
				let folderForm = this.$refs['folderForm'];
				folderForm.form.parentId = this.parentId;
				folderForm.form.fullPath = this.fullPath;
				folderForm.dialogVisible = true;
			},
			navOpenFolder(folder) {
				this.menuHide();
				this.fileSelectHide();
				this.queryParams.parentId = folder.id;
				this.queryParams.fileName = "";
				this.parentId = folder.id;
				this.fullPath = folder.fullPath;
				this.page.pageSize = 20;
				this.page.totalPage = 1;
				this.tableData = [];
				this.initData(this.queryParams)
			},
			fileIconClick(id, sourceType) {
				switch (parseInt(sourceType)) {
					case 0: //文件夹
						this.openFolder(id);
						break;
					default:
						this.fileSelect(id);
				}
			},
			fileSelect(id) {
				if (!this.openSelect) {
					return;
				}
				if (!this.multiSelect) {
					this.fileSelectHide();
				}
				let row = this.tableData.filter(item => item.id == id)[0];
				let dom = document.getElementById("file_item_" + id);
				if (dom) {
					if (dom.style.display) {
						dom.style.display = "";
						let selectFiles = [];
						for (let i = 0; i < this.selectFiles.length; i++) {
							if (this.selectFiles[i].id == row.id) {
								content;
							}
							selectFiles.push(this.selectFiles[i]);
						}
						this.selectFiles = selectFiles;
					} else {
						dom.style.display = "block";
						row.icon = this.fileIcon(row.filePath, row.sourceType, row.suffix);
						this.selectFiles.push(row);
					}
					//如果是弹窗选文件时调用
					if (this.openSelect) {
						// console.log(this.selectFiles);
						this.$emit("selectFile", this.selectFiles);
					}
				}
			},
			fileSelectHide() {
				for (let i = 0; i < this.selectFiles.length; i++) {
					let dom = document.getElementById("file_item_" + this.selectFiles[i].id);
					if (dom) {
						dom.style.display = "";
					}
				}
				this.selectFiles = [];
				//如果是弹窗选文件时调用
				if (this.openSelect) {
					this.$emit("selectFile", this.selectFiles);
				}
			},
			openFolder(id) {
				//console.log('index:', index, '选项1-1-1绑定事件执行');
				let row = this.tableData.filter(item => item.id == id)[0];
				if (row) {
					this.menuHide();
					this.fileSelectHide();
					this.queryParams.parentId = id;
					this.queryParams.fileName = "";
					this.parentId = id;
					this.fullPath = row.fullPath;
					this.page.pageSize = 20;
					this.page.totalPage = 1;
					this.tableData = [];
					this.initData(this.queryParams)
				}
			},
			rename(id) {
				// console.log('index:', index, '选项1-1-2绑定事件执行');
				let row = this.tableData.filter(item => item.id == id)[0];
				if (row) {
					let sourceType = parseInt(row.sourceType);
					if (sourceType == 0) {
						let folderForm = this.$refs['folderForm'];
						folderForm.form = this.$app.initFormData(folderForm.form, row);
						folderForm.dialogVisible = true;
					} else {
						let fileRename = this.$refs['fileRename'];
						fileRename.form = this.$app.initFormData(fileRename.form, row);
						fileRename.dialogVisible = true;
					}
				}
			},
			del(id) {
				let file = this.tableData.filter(item => item.id == id)[0];
				if (file) {
					let sourceType = parseInt(file.sourceType);
					let tipTitle = "您确定要删除文件夹吗?<br>该操作将删除文件夹及文件夹下的所有文件!!";
					if (sourceType > 0) {
						tipTitle = "您确定要删除文件吗?!!";
					}
					this.$confirm(tipTitle, '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning',
						dangerouslyUseHTMLString: true
					}).then(() => {
						this.$ajax.get(this.getContentPath() +
								"/admin/system/file/userResources/deleteById", {
									"id": id
								})
							.then(res => {
								this.eventCallBack(file, 30);
							}).catch(err => {});
					});
				}
			},
			showAttr(id) {
				//console.log('index:', index, '选项1-2-1绑定事件执行');
				let file = this.tableData.filter(item => item.id == id)[0];
				let fileAttr = this.$refs['fileAttr'];
				fileAttr.fileInfo.src = this.fileIcon(file.filePath, file.sourceType, file.suffix);
				fileAttr.fileInfo.fileName = file.name;
				fileAttr.fileInfo.fileSize = file.fileSize;
				fileAttr.fileInfo.sourceType = file.sourceType;
				fileAttr.fileInfo.createTime = file.addTime;
				fileAttr.fileInfo.md5 = file.md5;
				fileAttr.fileInfo.filePath = file.filePath;
				fileAttr.drawer = true;
			},
			fileIcon(filePath, sourceType, suffix) {
				let icon = "/static/images/folder@3x.png";
				switch (parseInt(sourceType)) {
					case 1:
						icon = filePath;
						break;
					case 2:
						icon = "/static/images/file_mp3@3x.png";
						break;
					case 3:
						icon = "/static/images/file_video@3x.png";
						break;
					case 4:
						if (suffix == 'doc' || suffix == 'docx') {
							icon = "/static/images/file_docx@3x.png";
						}
						if (suffix == 'xls' || suffix == 'xlsx') {
							icon = "/static/images/file_excel@3x.png";
						}
						if (suffix == 'pdf') {
							icon = "/static/images/file_pdf@3x.png";
						}
						if (suffix == 'ppt' || suffix == 'pptx') {
							icon = "/static/images/file_ppt@3x.png";
						}
						if (suffix == 'txt') {
							icon = "/static/images/file_txt@3x.png";
						}
						if (suffix == 'zip' || suffix == 'rar' || suffix == 'tar') {
							icon = "/static/images/file_zip@3x.png";
						}
						break;
				}
				return icon;
			},
			eventCallBack(file, eventType) {
				let data = [];
				switch (eventType) {
					case 10: //添加文件夹
						this.$message({
							message: '文件夹添加成功!',
							type: 'success'
						});
						this.tableData.unshift(file);
						break;
					case 20: //修改文件夹
						for (let i = 0; i < this.tableData.length; i++) {
							if (this.tableData[i].id == file.id) {
								data.push(file);
							} else {
								data.push(this.tableData[i]);
							}
						}
						this.tableData = data;
						this.$message({
							message: '文件夹修改成功!',
							type: 'success'
						});
						break;
					case 30: //删除文件夹或文件
						let sourceType = parseInt(file.sourceType);
						let msg = "文件夹删除成功!";
						if (sourceType > 0) {
							msg = "文件删除成功!";
						}
						this.$message({
							message: msg,
							type: 'success'
						});
						for (let i = 0; i < this.tableData.length; i++) {
							if (this.tableData[i].id == file.id) {
								continue;
							}
							data.push(this.tableData[i]);
						}
						this.tableData = data;
						break;
					case 100: //文件上传成功
						this.$message({
							message: '文件上传成功!',
							type: 'success'
						});
						this.tableData.unshift(file);
						break;
					case 200: //文件重命名成功
						for (let i = 0; i < this.tableData.length; i++) {
							if (this.tableData[i].id == file.id) {
								data.push(file);
							} else {
								data.push(this.tableData[i]);
							}
						}
						this.tableData = data;
						this.$message({
							message: '文件重命名成功!',
							type: 'success'
						});
						break;
				}
			},
			initFilePanelHeight() {
				let clientWidth = document.documentElement.clientWidth;
				if (clientWidth >= 1920) {
					this.filePanelHeight = clientWidth * 5 / 100;
					return;
				}
				if (clientWidth >= 1200) {
					this.filePanelHeight = clientWidth * 7 / 100;
					return;
				}
				if (clientWidth >= 992) {
					this.filePanelHeight = clientWidth * 8.5 / 100;
					return;
				}
				if (clientWidth >= 768) {
					this.filePanelHeight = clientWidth * 11.5 / 100;
					return;
				}
				this.filePanelHeight = clientWidth * 13.5 / 100;
			},
			initPanelHeight() {
				this.panelHeight = this.$refs['appContainer'].offsetHeight - 80;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped" lang="scss">
	::v-deep .el-scrollbar__wrap {
		overflow-x: hidden;
	}

	.file-list-panel {
		width: 100%;
		position: relative;

		ul {
			padding: 0px;
			margin: 0px;
		}

		.file-image-panle {
			width: 100%;
			height: 100%;
			overflow: hidden;
		}

		.upload-image {
			width: 100%;
			vertical-align: middle;
			border-radius: 5px;
		}

		.icon-image {
			vertical-align: middle;
		}

		.file-list-item {
			padding: 10px;
			position: relative;
		}


		.file-select {
			position: absolute;
			width: 100%;
			height: 100%;
			top: 0px;
			left: 0px;
			padding: 10px;
			z-index: 999999;
			text-align: center;
			font-size: 40px;
			display: none;
		}

		.tcenter {
			text-align: center;
		}

		.file-title {
			width: 100%;
			height: 20px;
			line-height: 20px;
			overflow: hidden;
			font-size: 12px;

		}

		.file-list-empty {
			width: 100%;
			top: 30%;
			text-align: center;
			position: absolute;
		}
	}

	.cursor {
		cursor: pointer;
	}

	::v-deep .vue-contextmenu-listWrapper .context-menu-list[data-v-1931f86e] {
		line-height: 25px;
		height: 25px;
		padding-left: 5px;
	}

	::v-deep .context-menu-list[data-v-1931f86e]:hover {
		background: #f5f6fa;
	}
</style>