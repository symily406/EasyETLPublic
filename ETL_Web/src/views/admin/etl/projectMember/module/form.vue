<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="100px" :rules="rules">
				<el-form-item label="归属项目" prop="projectId"
					:rules="[{required: true, message: '请选择归属项目', trigger: 'blur'}]">
					<el-select v-model="form.projectId" placeholder="归属项目" clearable size="small"
						class="max-width-100-ratio" @change="projectHandleChange" disabled="true">
						<el-option v-for="item in projectSelectData" :key="item.projectId" :label="item.projectName"
							:value="item.projectId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="用户" prop="staffId" :rules="[{required: true, message: '请选择用户', trigger: 'blur'}]">
					<el-select v-model="form.staffId" placeholder="用户" clearable size="small"
						class="max-width-100-ratio" :disabled="isEdit" filterable>
						<el-option v-for="item in staffSelectData" :key="item.staffId" :label="item.name"
							:value="item.staffId">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="工作空间">
					<el-col :span="24">
						<el-card class="box-card form-box-card" style="height: 450px;">
							<el-scrollbar wrap-class="scrollbar-wrapper" style="height: 450px;">
								<el-tree ref="workSpaceTree" :data="workSpaceData" node-key="id"
									:default-expanded-keys="openWorkSpaceIds"
									:default-checked-keys="projectMemberSpaceLayers" show-checkbox>
								</el-tree>
							</el-scrollbar>
						</el-card>
					</el-col>
				</el-form-item>
			</el-form>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'projectMemberForm',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				projectSelectData: [],
				staffSelectData: [],
				workSpaceData: [],
				projectMemberSpaceLayers: [],
				openWorkSpaceIds: [],
				isEdit: false,
				form: {
					//主键
					memberId: '',
					//归属项目
					projectId: '',
					//用户ID
					staffId: ''
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
				this.queryAllProject();
				this.queryAllStaff();
				if (this.form.memberId) {
					this.queryProjectWorkSpace();
				} else {
					this.initWorkSpaceLayerTree();
				}
			},
			closeWin() {
				this.workSpaceData = [];
				this.projectMemberSpaceLayers = [];
				this.openWorkSpaceIds = [];
				this.dialogVisible = false;
				this.isEdit = false;
				this.form = this.$options.data().form;
			},
			projectHandleChange(val) {
				if (val) {
					this.form.projectId = val;
					this.initWorkSpaceLayerTree();
					return;
				}
				this.form.projectId = null;
				this.workSpaceData = [];
			},
			queryAllProject() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/project/queryAll", {}).then(res => {
					this.projectSelectData = res.data;
				}).catch(error => {

				});
			},
			queryAllStaff() {
				this.$ajax.get(this.getContentPath() + "/admin/system/staff/queryAll", {
					"isEdit": this.isEdit ? 1 : 0,
					"projectId": this.form.projectId
				}).then(res => {
					this.staffSelectData = res.data;
				}).catch(error => {

				});
			},
			initWorkSpaceLayerTree() {
				if (this.form.projectId) {
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectWorkSpace/projectWorkSpaceLayerMemberTree", {
						"projectId": this.form.projectId
					}).then(res => {
						this.workSpaceData = res.data;
					}).catch(err => {

					});
				}
			},
			queryProjectWorkSpace() {
				if (this.form.memberId) {
					this.$ajax.get(this.getContentPath() + "/admin/etl/projectMember/queryProjectWorkSpace", {
						"memberId": this.form.memberId
					}).then(res => {
						res.data.forEach(item => {
							this.projectMemberSpaceLayers.push(item.projectWorkSpaceLayerRelId);
						});
						this.initWorkSpaceLayerTree();
					}).catch(err => {

					});
				}
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			getWorkSpaceLayerTreeData() {
				let nodes = [];
				const nodeIds = this.$refs['workSpaceTree'].getCheckedKeys();
				nodeIds.forEach(item => {
					const node = this.$refs['workSpaceTree'].getNode(item);
					if (node.data.workSpaceLayerId) {
						nodes.push({
							projectWorkSpaceLayerRelId: node.data.id,
							projectWorkSpaceId: node.data.projectWorkSpaceId,
							workSpaceId: node.data.workSpaceId,
							workSpaceLayerId: node.data.workSpaceLayerId,
							workSpaceType: node.data.workSpaceType
						});
					}
				});
				return nodes;
			},
			onSubmit(formName) {
				let memberArrs = this.getWorkSpaceLayerTreeData();
				this.$refs[formName].validate((valid) => {
					if (valid) {
						if (memberArrs.length == 0) {
							this.$message.error('请配置工作空间!');
							return false;
						}
						this.form['memberArrs'] = JSON.stringify(memberArrs);
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/etl/projectMember/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.memberId == '' ? 1 : 2;
								this.$emit('eventCallBack', eventType);
								this.closeWin();
							}).catch(err => {
								this.loading = false;
							});
					} else {
						return false;
					}
				});
			}
		}
	}
</script>
<style scoped="scoped">
</style>