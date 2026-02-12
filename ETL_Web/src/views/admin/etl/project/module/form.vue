<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="项目名称" prop="projectName"
						:rules="[{required: true, message: '请输入项目名称', trigger: 'blur'},{min:1,max:128,message:'项目名称长度不能超过128个字符'}]">
						<el-input v-model="form.projectName" placeholder="请输入项目名称" size="small" maxlength="128"
							clearable></el-input>
					</el-form-item>
					<el-form-item label="工作空间">
						<el-col :span="24">
							<el-card class="box-card form-box-card" style="height: 500px;">
								<el-scrollbar wrap-class="scrollbar-wrapper" style="height: 500px;">
									<el-tree ref="workSpaceTree" :data="workSpaceData" node-key="id"
										:default-expanded-keys="openWorkSpaceIds" :default-checked-keys="workSpaceIds"
										show-checkbox>
									</el-tree>
								</el-scrollbar>
							</el-card>
						</el-col>
					</el-form-item>
				</el-form>
			</div>
		</el-scrollbar>
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
		name: 'projectForm',
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
				workSpaceData: [],
				workSpaceIds: [],
				openWorkSpaceIds: [],
				isEdit: false,
				form: {
					//主键
					projectId: '',
					//项目名称
					projectName: '',
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1
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
				if (this.isEdit) {
					this.initWorkSpaceLayerRel();
				}
				if (this.isEdit==false) {
					this.initWorkSpaceLayerTree();
				}
			},
			closeWin() {
				this.dialogVisible = false;
				this.isEdit = false;
				this.form = this.$options.data().form;
				this.workSpaceData = [];
				this.workSpaceIds = [];
				this.openWorkSpaceIds = [];
			},
			initWorkSpaceLayerTree() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectWorkSpace/projectWorkSpaceLayerTree", {
					"projectId": this.form.projectId
				}).then(res => {
					this.workSpaceData = res.data;
				}).catch(err => {

				});
			},
			initWorkSpaceLayerRel() {
				this.$ajax.get(this.getContentPath() + "/admin/etl/project/queryAllByProjectId", {
					"projectId": this.form.projectId
				}).then(res => {
					res.data.forEach(item => {
						this.workSpaceIds.push(item.projectWorkSpaceLayerRelId);
					});
					this.initWorkSpaceLayerTree();
				}).catch(err => {

				});
			},
			getWorkSpaceLayerTreeData() {
				let nodes = [];
				const nodeIds = this.$refs['workSpaceTree'].getCheckedKeys();
				nodeIds.forEach(item => {
					const node = this.$refs['workSpaceTree'].getNode(item);
					if (node.data.workSpaceLayerId) {
						nodes.push({
							projectWorkSpaceLayerRelId: node.data.id,
							projectWorkSpaceId:node.data.projectWorkSpaceId,
							workSpaceId: node.data.workSpaceId,
							workSpaceLayerId: node.data.workSpaceLayerId,
							workSpaceType: node.data.workSpaceType
						});
					}
				});
				return nodes;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit(formName) {
				let workSpaces = this.getWorkSpaceLayerTreeData();
				if (workSpaces.length == 0) {
					this.$message.error('请配置工作空间!');
					return false;
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.form['workSpaces'] = JSON.stringify(workSpaces);
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/etl/project/saveOrUpdate", this.form).then(
							res => {
								this.loading = false;
								const eventType = this.form.projectId == '' ? 1 : 2;
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