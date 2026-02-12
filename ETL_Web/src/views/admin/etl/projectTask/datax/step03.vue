<template>
	<div class="step" v-loading="loading">
		<table border="0" width="100%" cellspacing="0" callpadding="5">
			<tr>
				<td width="100" align="center">字段映射</td>
				<td>&nbsp;</td>
				<td width="100" align="center" v-if="compare==false"><el-button type="text"
						v-if="taskStep==5||taskStep==50" @click="nextSetp(3)">修改</el-button></td>
			</tr>
		</table>
		<el-divider></el-divider>
		<table border="0" width="100%" cellspacing="0">
			<tr>
				<td align="center">您要配置来源表与目标表的字段映射关系，通过连线将待同步的字段左右相连，也可以通过同行映射、同名映射批量完成映射</td>
			</tr>
		</table>
		<table border="0" width="100%" cellspacing="0" callpadding="0">
			<tr>
				<td width="200" align="center" v-if="compare==false">&nbsp;</td>
				<td class="column-container">
					<div class="task-mask" v-if="taskStep==5||taskStep==50"></div>
					<div class="content">
						<div class="row dataCont" id="module">
							<div>
								<table border="0" cellspacing="0" callpadding="0" class="task-table">
									<tr>
										<td width="200">字段名称</td>
										<td width="100">类型</td>
										<td width="50">操作</td>
									</tr>
									<tbody>
										<tr v-for="(item, index) in readerColumns" :key="item.readFieldId">
											<td class="tleft"><span class="fright"><el-button type="text" size="mini"
														class="button-padding-none" v-clipboard:copy="item.fieldName"
														v-clipboard:success="copySucced"><i
															class="el-icon-document-copy"></i></el-button></span><span
													:title="`${item.comment}`">{{item.fieldName}}</span>
											</td>
											<td class="tleft">{{item.columnType}}</td>
											<td :id="'reader_' + item.readFieldId">
												<el-button v-if="item.isConstant==1" class="button-padding-none"
													type="text" size="mini" @click="constantFieldRemove(item)"><i
														class="el-icon-remove"></i></el-button>
												<el-button v-if="item.isNum==0" class="button-padding-none" type="text"
													size="mini" @click="fieldFormat(item)"><i
														class="el-icon-edit"></i></el-button>
											</td>
										</tr>
										<tr v-if="taskStep==3">
											<td colspan="3">
												<el-button type="text" size="mini"><i class="el-icon-plus"
														@click="addConstantFieldFormat">添加常量</i></el-button>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div>
								<table border="0" cellspacing="0" callpadding="0" class="task-table">
									<tr>
										<td width="200" align="center">字段名称</td>
										<td width="100" align="center">类型</td>
									</tr>
									<tbody>
										<tr v-for="(item, index) in writerColumns" :key="item.writeFieldId">
											<td class="tleft" :id="'writer_' + item.writeFieldId"><span
													class="fright"><el-button type="text" size="mini"
														class="button-padding-none" v-clipboard:copy="item.fieldName"
														v-clipboard:success="copySucced"><i
															class="el-icon-document-copy"></i></el-button></span>
												<span :title="`${item.comment}`">{{item.fieldName}}</span>
											</td>
											<td class="tleft">{{item.columnType}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</td>
				<td width="200" align="center" valign="top" v-if="compare==false">
					<p v-if="taskStep==3"><el-button type="primary" size="small"
							@click="fieldReflection(2)">同行映射</el-button></p>
					<p v-if="taskStep==3"><el-button size="small" type="info"
							@click="fieldReflection(1)">同名映射</el-button></p>
					<p v-if="taskStep==3"><el-button type="danger" size="small"
							@click="clearFieldReflection()">取消映射</el-button></p>
				</td>
			</tr>
		</table>
		<table border="0" width="100%" cellspacing="0" callpadding="5" v-if="taskStep==3">
			<tr>
				<td width="100">&nbsp;</td>
				<td align="center">
					<el-button size="small" @click="nextSetp(2)">上一步</el-button>
					<el-button type="primary" size="small" @click="onSubmit(4)">下一步</el-button>
				</td>
				<td width="100">&nbsp;</td>
			</tr>
		</table>
		<eFieldFormat ref="eFieldFormat" dialogTitle="修改字段" dialogWidth="600px" dialogHeight="210px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import jsPlumb from 'jsplumb';
	import eFieldFormat from './fieldFormat';
	let plumbIns = jsPlumb.jsPlumb.getInstance({
		ConnectionsDetachable: true, //连线是否可用鼠标分离
		ReattachConnections: true, //是否重新连接使用鼠标分离的线
		//父级元素id；假如页面元素所在上层不同，最外层父级一定要设置
		Container: "module",
		//DragOptions : { cursor: "pointer", zIndex:2000 },//拖动的时候
		PaintStyle: {
			stroke: '#BCBCBC'
		}, // 默认悬停样式  默认为null
		HoverPaintStyle: {
			stroke: '#1E90FF'
		}, // 默认悬停样式  默认为null
		EndpointStyle: {
			fill: '#BCBCBC',
			radius: 5
		},
		EndpointHoverStyle: {
			fill: '#1E90FF',
			radius: 5
		}, // 端点悬停样式
		Rectangle: {

		}
	});
	let common = null;
	export default {
		name: 'dataxStep03',
		components: {
			eFieldFormat
		},
		props: {
			isDisabled: {
				type: Boolean,
				default: false
			},
			taskStep: {
				type: Number,
				default: 1
			},
			compare: {
				type: Boolean,
				default: false
			},
			backup: {
				type: Boolean,
				default: false
			}
		},
		mixins: [],
		data() {
			return {
				rules: {},
				loading: true,
				sourceDatas: [],
				points: [],
				readerColumns: [],
				writerColumns: [],
				taskFields: [],
				taskId: '',
				projectId: '',
				projectWorkSpaceLayerRelId: '',
				projectWorkSpaceId: '',
				workSpaceType: '',
				taskFolderId: ''
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
			let that = this;
			common = {
				isSource: true,
				isTarget: true,
				endpoint: ['Dot', {
					radius: 5,
					fill: '#ff5722'
				}], // 端点的形状
				maxConnections: -1,
				connector: ['Straight', {
					gap: 2,
					cornerRadius: 5,
					alwaysRespectStubs: true
				}], // 连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
				connectorOverlays: [
					['Arrow', {
						width: 10,
						length: 10,
						location: 1
					}],
					[
						"Label",
						{
							location: 0.5,
							label: "",
							visible: true,
							id: "label",
							labelStyle: {
								color: '#FFF'
							},
							cssClass: "aLabel",
							events: {
								tap(labelOverlay, originalEvent) {
									let sourceId = labelOverlay.component.sourceId; // 起点
									let targetId = labelOverlay.component.targetId; // 目标点
									// 删除列表中的连线关系
									for (let i = 0; i < that.points.length; i++) {
										let item = that.points[i];
										if (('reader_' + item.sourceId) === sourceId && ('writer_' + item
												.targetId) === targetId) {
											that.points.splice(i, 1);
										};
									}
									plumbIns.deleteConnection(labelOverlay.component); // 删除当前映射线
								}
							}
						}
					]
				]
			};
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
			this.destroy();
		},
		//销毁完成状态
		destroyed() {

		},
		methods: {
			destroy() {
				this.rules = {};
				this.loading = true;
				this.sourceDatas = [];
				this.points = [];
				this.readerColumns = [];
				this.writerColumns = [];
				this.taskFields = [];
				this.taskId = '';
				this.projectId = '';
				this.projectWorkSpaceLayerRelId = '';
				this.projectWorkSpaceId = '';
				this.workSpaceType = '';
				this.taskFolderId = '';
			},
			initPlumbIns() {
				plumbIns.ready(() => {
					plumbIns.setContainer('module');
				});
			},
			initReadField() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				let url = "/admin/etl/batchTask/batchProjectLayerTaskReadField/getByTaskId";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryBatchProjectLayerTaskReadFieldBakeAll";
				}
				this.loading = true;
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": taskId
				}).then(
					res => {
						this.readerColumns = res.data;
						this.initWriteField();
					}).catch(err => {
					this.loading = false;
				});
			},
			initWriteField() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				let url = "/admin/etl/batchTask/batchProjectLayerTaskWriteField/getByTaskId";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryBatchProjectLayerTaskWriteFieldBackup";
				}
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": taskId
				}).then(
					res => {
						this.writerColumns = res.data;
						this.initTaskField();
						this.initPlumbInsSet();
					}).catch(err => {
					this.loading = false;
				});
			},
			initTaskField() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				let url = "/admin/etl/batchTask/batchProjectLayerTaskFieldMap/queryAll";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryBatchProjectLayerTaskFieldMapBackupAll";
				}
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": taskId
				}).then(
					res => {
						this.points = res.data;
						this.initPlumbInsSet();
						this.loading = false;
					}).catch(err => {
					this.loading = false;
				});
			},
			// 设置连接点
			setLinkPoint() {
				plumbIns.reset(); // 清除后再跟进后台传过来的值画线
				this.$nextTick(() => {
					this.readerColumns.forEach(item => {
						plumbIns.addEndpoint('reader_' + item.readFieldId, {
							anchors: ['Right'],
							uuid: 'reader_' + item.readFieldId
						}, common)
					});
					this.writerColumns.forEach(item => {
						plumbIns.addEndpoint('writer_' + item.writeFieldId, {
							anchors: ['Left'],
							uuid: 'writer_' + item.writeFieldId
						}, common)
					});
				});
			},
			setDefaultLine() {
				plumbIns.reset(); // 清除后再跟进后台传过来的值画线
				this.$nextTick(() => {
					this.drawConnectLine();
				});
			},
			beforeDrop() {
				plumbIns.bind('beforeDrop', (info) => {
					let sourceId = info.sourceId; // 起点
					let targetId = info.targetId; // 目标点
					let sourceKey = info.connection['endpoints'][0].anchor.type // 连接的位置获取
					let targetKey = info.dropEndpoint.anchor.type // 连接的位置获取
					if (sourceId == targetId) { // 判断是否是同一节点
						return false;
					}
					// 必须是左边连接右边
					if (info.targetId.indexOf('writer_') === -1) {
						return false;
					}
					let points = this.points;
					for (let i = 0; i < points.length; i++) {
						let item = points[i];
						if (item.sourceId == sourceId) {
							return false;
						};
						if (item.targetId == targetId) {
							return false;
						};
					}
					points.push({
						sourceId: sourceId.replace('reader_', ''),
						targetId: targetId.replace('writer_', '')
					});
					this.points = points;
					//console.log('points', JSON.stringify(this.points));
					return true;
				});
			},
			clearFieldReflection() {
				this.points = [];
				this.setLinkPoint();
			},
			fieldReflection(reflectionType) {
				let readerColumns = this.readerColumns;
				let writerColumns = this.writerColumns;
				this.points = [];
				plumbIns.deleteEveryConnection();
				switch (reflectionType) {
					case 1: //同名映射
						for (var i = 0; i < readerColumns.length; i++) {
							let readerCode = readerColumns[i];
							let writerCode = writerColumns.filter(item => item.fieldName == readerColumns[i].fieldName)[0];
							if (writerCode) {
								this.points.push({
									sourceId: readerCode.readFieldId,
									targetId: writerCode.writeFieldId
								});
							}
						}
						break;
					case 2: //同行映射
						for (var i = 0; i < readerColumns.length; i++) {
							if (writerColumns[i]) {
								this.points.push({
									sourceId: readerColumns[i].readFieldId,
									targetId: writerColumns[i].writeFieldId
								});
							}
						}
						break;
				}
				this.drawConnectLine();
			},
			drawConnectLine() {
				this.points.forEach((item, index) => {
					plumbIns.connect({
							uuids: ['reader_' + item.sourceId, 'writer_' + item.targetId]
						},
						common);
				});
			},
			copySucced() {
				return this.$message({
					message: '复制成功',
					type: 'success',
				});
			},
			fieldFormat(item) {
				const eFieldFormat = this.$refs['eFieldFormat'];
				this.$app.initFormData(eFieldFormat.form, item);
				eFieldFormat.disabled = true;
				eFieldFormat.dialogVisible = true;
			},
			addConstantFieldFormat() {
				const eFieldFormat = this.$refs['eFieldFormat'];
				this.$app.initFormData(eFieldFormat.form, this.readerColumns[0]);
				eFieldFormat.form.readFieldId = '';
				eFieldFormat.form.isConstant = 1;
				eFieldFormat.form.isPrimaryKey = 0;
				eFieldFormat.form.fieldName = '';
				eFieldFormat.form.fieldType = '';
				eFieldFormat.form.columnSize = '';
				eFieldFormat.form.fieldFormat = '';
				eFieldFormat.form.comment = '';
				eFieldFormat.form.showOrder = this.readerColumns.length + 1;
				eFieldFormat.disabled = false;
				eFieldFormat.dialogVisible = true;
			},
			constantFieldRemove(item) {
				this.$confirm('您确定要移除当前常量吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.loading = true;
					let readFieldId = item.readFieldId;
					this.$ajax.get(this.getContentPath() +
						"/admin/etl/batchTask/batchProjectLayerTaskReadField/deleteById", {
							"readFieldId": readFieldId
						}).then(
						res => {
							let readerColumns = [];
							this.readerColumns.forEach(item => {
								if (item.readFieldId != readFieldId) {
									readerColumns.push(item);
								}
							});
							this.readerColumns = readerColumns;
							let points = [];
							this.points.forEach(item => {
								if (item.sourceId != readFieldId) {
									points.push(item);
								}
							});
							this.points = points;
							this.initPlumbInsSet();
							this.loading = false;
						}).catch(err => {
						this.loading = false;
					});
				});
			},
			eventCallBack(readFields) {
				let readFieldId = readFields.readFieldId;
				let readerColumns = [];
				let oldReadFields = this.readerColumns.filter(item => item.readFieldId == readFieldId)[0];
				if (oldReadFields) {
					this.readerColumns.forEach(item => {
						if (item.readFieldId == readFieldId) {
							item.fieldFormat = readFields.fieldFormat;
						}
						readerColumns.push(item);
					});
					this.readerColumns = readerColumns;
					// 设置连接点
					this.initPlumbInsSet();
					this.setFormContent();
					return;
				}
				this.readerColumns = this.readerColumns.concat(readFields);
				// 设置连接点
				this.initPlumbInsSet();
				this.setFormContent();
			},
			initPlumbInsSet() {
				// 设置连接点
				this.setLinkPoint();
				// 设置默认连线
				this.setDefaultLine();
				// 连线前的判断
				this.beforeDrop();
			},
			nextSetp(nextSetp) {
				this.$emit('nextSetp', nextSetp);
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			getTaskFields() {
				let taskFields = [];
				this.points.forEach(item => {
					let sourceId = item.sourceId;
					let targetId = item.targetId;
					let readerField = this.readerColumns.filter(item => item.readFieldId == sourceId)[0];
					let writerField = this.writerColumns.filter(item => item.writeFieldId == targetId)[0];
					if (readerField && writerField) {
						taskFields.push({
							"taskId": this.taskId,
							"projectId": this.projectId,
							"projectWorkSpaceLayerRelId": this.projectWorkSpaceLayerRelId,
							"projectWorkSpaceId": this.projectWorkSpaceId,
							"workSpaceType": this.workSpaceType,
							"taskFolderId": this.taskFolderId,
							"readId": readerField.readId,
							"writeId": writerField.writeId,
							"readFieldId": readerField.readFieldId,
							"writeFieldId": writerField.writeFieldId,
							"isDel": 1,
							"enable": 1,
							"showOrder": readerField.showOrder
						});
					}
				});
				return taskFields;
			},
			onSubmit(nextSetp) {
				let taskId = this.taskId;
				let taskFields = this.getTaskFields();
				this.setFormContent();
				// this.points.forEach(item => {
				// 	let sourceId = item.sourceId;
				// 	let targetId = item.targetId;
				// 	let readerField = this.readerColumns.filter(item => item.readFieldId == sourceId)[0];
				// 	let writerField = this.writerColumns.filter(item => item.writeFieldId == targetId)[0];
				// 	if (readerField && writerField) {
				// 		taskFields.push({
				// 			"taskId": taskId,
				// 			"projectId":this.projectId,
				// 			"projectWorkSpaceLayerRelId":this.projectWorkSpaceLayerRelId,
				// 			"projectWorkSpaceId":this.projectWorkSpaceId,
				// 			"workSpaceType":this.workSpaceType,
				// 			"taskFolderId":this.taskFolderId,
				// 			"readId": readerField.readId,
				// 			"writeId": writerField.writeId,
				// 			"readFieldId": readerField.readFieldId,
				// 			"writeFieldId": writerField.writeFieldId,
				// 			"isDel":1,
				// 			"enable":1,
				// 			"showOrder": readerField.showOrder
				// 		});
				// 	}
				// });
				if (taskFields.length == 0) {
					this.$message.error('请配置同步字段映射');
					return;
				}
				this.loading = true;
				this.$ajax.post(this.getContentPath() +
					"/admin/etl/batchTask/batchProjectLayerTaskFieldMap/saveOrUpdate", {
						"taskId": taskId,
						"taskStep": nextSetp,
						"taskFields": JSON.stringify(taskFields)
					}).then(
					res => {
						this.nextSetp(nextSetp);
						this.loading = false;
					}).catch(err => {
					this.loading = false;
				});
			},
			setFormContent() {
				// sessionStorage.setItem("step03_"+this.taskId,JSON.stringify(this.getTaskFields()));
				// this.$emit("parameterCallBack");
			}
		}
	}
</script>
<style scoped="scoped">
	::v-deep .aLabel {
		display: inline-block;
		width: 8px;
		height: 8px;
		line-height: 8px;
		cursor: pointer;
		font-size: 12px;
		border-radius: 50%;
		border: 1px solid #BCBCBC;
		background: #BCBCBC;
		background-size: 100% 100%;
	}

	::v-deep .aLabel:hover {
		width: 16px;
		height: 16px;
		line-height: 16px;
		font-size: 14px;
		border: 1px solid #DC143C;
		background-size: 100% 100%;
	}
</style>