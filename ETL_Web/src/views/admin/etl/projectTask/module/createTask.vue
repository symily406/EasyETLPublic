<template>
	<el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="120px" :rules="rules">
				<el-form-item label="任务名称" prop="taskName"
					:rules="[{required: true, message: '任务名称', trigger: 'blur'}]">
					<el-input v-model="form.taskName" size="small" maxlength="64" placeholder="任务名称"></el-input>
				</el-form-item>
				<el-form-item label="备注" prop="remark" :rules="[{required: false, message: '请输入备注', trigger: 'blur'}]">
					<el-input rows="5" type="textarea" v-model="form.remark" placeholder="备注" size="small"
						maxlength="512"></el-input>
				</el-form-item>
				<el-form-item label="目录位置" prop="taskFolderId"
					:rules="[{required: true, message: '请选择目录位置', trigger: 'blur'}]" class="treeSelect">
					<treeSelect ref="treeSelect" v-model="form.taskFolderId" :options="folderTreeData"
						:normalizer="normalizer" :clearable="false" placeholder="目录位置" :flatten-search-results="true"
						size="small" :filterable="true" @open="handleOpen" @select="hamdleSelect"
						:load-options="loadOptionsMethod" :appendToBody="true" noChildrenText="没有子选项"
						noOptionsText="没有可选项" noResultsText="没有匹配的结果" z-index="2005"  :disable-branch-nodes="true">
						<p class="tree-select-item" slot="option-label" slot-scope="{node}" :title="node.label">
							<template> {{ node.label }}</template>
						</p>
					</treeSelect>
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
	import treeSelect from '@riophae/vue-treeselect'
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'createTask',
		components: {
			treeSelect
		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				folderTreeData: [],
				title: '',
				form: {
					taskId: '',
					taskName: '',
					projectId: '',
					projectWorkSpaceLayerRelId: '',
					taskFolderId: '',
					projectWorkSpaceId: '',
					workSpaceType: '',
					remark: '',
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
				let projectId = this.$route.query.projectId;
				let calculateTypes = this.$route.query.calculateTypes;
				this.$store.dispatch(
					"etl/task/initProjectTree", {
						"projectId": this.form.projectId,
						"projectWorkSpaceId": this.form.projectWorkSpaceId,
						"workSpaceType": this.form.workSpaceType,
						"showFolderOnly": 1
					}
				).then(
					res => {
						this.folderTreeData = res.data.taskTree;
					}).catch(error => {});
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			handleOpen() {
				let elements = document.getElementsByClassName("vue-treeselect");
				for (let i = 0; i < elements.length; i++) {
					elements[i].style.zIndex = '9999';
				}
			},
			hamdleSelect(node) {
				this.form.projectId = node.projectId;
				this.form.layerId = node.layerId;
				this.form.pid = node.id;
			},
			loadOptionsMethod({
				action,
				parentNode,
				callback
			}) {
				callback(null, []);
			},
			normalizer(node) {
				// console.log(node, "node");
				return {
					id: node.id,
					label: node.name,
					children: node.children
				};
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/etl/projectTask/saveOrUpdate", this
							.form).then(
							res => {
								this.loading = false;
								const eventType = this.form.taskid == '' ? 10 : 20;
								this.$emit('eventCallBack', eventType, res.data);
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