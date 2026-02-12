<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="字段名" prop="fieldName"
						:rules="[{required: true, message: '请输入字段名', trigger: 'blur'},{min:1,max:64,message:'字段名长度不能超过64个字符'}]">
						<el-input v-model="form.fieldName" placeholder="请输入字段名" size="small" maxlength="64"
							:disabled="disabled">
							<template #suffix>
								<i class="el-icon-document-copy cblue pointer" v-clipboard:copy="form.fieldName"
									v-clipboard:success="copySucced"></i>
							</template>
						</el-input>
					</el-form-item>
					<el-form-item label="类型" prop="fieldType"
						:rules="[{required: true, message: '请输入字段类型', trigger: 'blur'},{min:1,max:64,message:'字段类型长度不能超过64个字符'}]">
						<el-input v-model="form.fieldType" placeholder="请输入字段类型" size="small" maxlength="64"
							:disabled="disabled"></el-input>
					</el-form-item>
					<el-form-item label="格式化" prop="fieldFormat"
						:rules="[{required: false, message: '请输入字段格式化', trigger: 'blur'}]">
						<el-input v-model="form.fieldFormat" placeholder="格式化,例如:yyyy-MM-dd" size="small" maxlength="64"
							clearable>
							<template #suffix>
								<el-tooltip effect="dark" placement="top">
									<div slot="content">
										<div class="tooltip-content">{{tooltipContent}}</div>
									</div>
									<i class="el-icon-question pointer"></i>
								</el-tooltip>
							</template>
						</el-input>
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
	//富文本编辑器
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'fieldFormat',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				tooltipContent: '支持数据库日期格式化，clob、blob、text转字符串，内置日期函数dateFormat、addYears、addMonths、addWeeks、addDays、addHours、addMinutes、uuid、nextId',
				rules: {},
				loading: false,
				dialogVisible: false,
				disabled: false,
				form: {
					//主键
					readFieldId: '',
					//批处理分层任务来源ID
					readId: '',
					//归属任务
					taskId: '',
					//归属项目
					projectId: '',
					projectWorkSpaceLayerRelId: '',
					projectWorkSpaceId: '',
					workSpaceType: '',
					taskFolderId: '',
					//字段名
					fieldName: '',
					//字段类型
					fieldType: '',
					//字段注释
					comment: '',
					//字段长度
					columnSize: '',
					//小数位数
					point: '',
					//是否主键
					isPrimaryKey: '',
					//字段格式化
					fieldFormat: '',
					isConstant:0,
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
				//富文本编辑器
			},
			closeWin() {
				//富文本编辑器
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			copySucced() {
				return this.$message({
					message: '复制成功',
					type: 'success',
				})
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() +
								"/admin/etl/batchTask/batchProjectLayerTaskReadField/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								this.$emit('eventCallBack', res.data);
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
	.tooltip-content {
		width: 200px;
	}
</style>