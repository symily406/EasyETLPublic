<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" v-loading="loading">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="归属文件夹" prop="folderId"
						:rules="[{required: true, message: '请输入归属文件夹', trigger: 'blur'},{min:1,max:32,message:'归属文件夹长度不能超过32个字符'}]">
						<el-input v-model="form.folderId" placeholder="请输入归属文件夹" size="small" maxlength="32"></el-input>
					</el-form-item>
					<el-form-item label="归属文件配置" prop="configId"
						:rules="[{required: true, message: '请输入归属文件配置', trigger: 'blur'},{min:1,max:32,message:'归属文件配置长度不能超过32个字符'}]">
						<el-input v-model="form.configId" placeholder="请输入归属文件配置" size="small" maxlength="32">
						</el-input>
					</el-form-item>
					<el-form-item label="MD5" prop="文件md5"
						:rules="[{required: true, message: '请输入MD5', trigger: 'blur'},{min:1,max:32,message:'MD5长度不能超过32个字符'}]">
						<el-input v-model="form.文件md5" placeholder="请输入MD5" size="small" maxlength="32"></el-input>
					</el-form-item>
					<el-form-item label="文件名" prop="fileName"
						:rules="[{required: true, message: '请输入文件名', trigger: 'blur'},{min:1,max:512,message:'文件名长度不能超过512个字符'}]">
						<el-input v-model="form.fileName" placeholder="请输入文件名" size="small" maxlength="512"></el-input>
					</el-form-item>
					<el-form-item label="保存文件夹" prop="saveFolder"
						:rules="[{required: true, message: '请输入保存文件夹', trigger: 'blur'},{min:1,max:32,message:'保存文件夹长度不能超过32个字符'}]">
						<el-input v-model="form.saveFolder" placeholder="请输入保存文件夹" size="small" maxlength="32">
						</el-input>
					</el-form-item>
					<el-form-item label="文件大小" prop="fileSize"
						:rules="[{required: false, message: '请输入文件大小', trigger: 'blur'},{min:1,max:0,message:'文件大小长度不能超过0个字符'}]">
						<el-input v-model="form.fileSize" placeholder="请输入文件大小" size="small" maxlength="0"></el-input>
					</el-form-item>
					<el-form-item label="保存文件名" prop="saveName"
						:rules="[{required: true, message: '请输入保存文件名', trigger: 'blur'},{min:1,max:64,message:'保存文件名长度不能超过64个字符'}]">
						<el-input v-model="form.saveName" placeholder="请输入保存文件名" size="small" maxlength="64"></el-input>
					</el-form-item>

					<el-form-item label="排序" prop="showOrder"
						:rules="[{required: true, message: '请输入排序', trigger: 'blur'}]" class="el-input-number-left">
						<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1"
							:max="999999" :precision="0" controls-position="right" class="max-width-100-ratio">
						</el-input-number>
					</el-form-item>
					<el-form-item label="配置" v-show="true">
						<el-checkbox v-model="form.allowEdit" :true-label="1" :false-label="0" label="可修改">
						</el-checkbox>
						<el-checkbox v-model="form.allowDelete" :true-label="1" :false-label="0" label="可删除">
						</el-checkbox>
						<el-checkbox v-model="form.enable" :true-label="1" :false-label="0" label="启用"></el-checkbox>
					</el-form-item>
				</el-form>
			</div>
		</el-scrollbar>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	//富文本编辑器
	//import editor from '@/components/Editor'
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'fileForm',
		components: {
			//editor
		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				form: {
					//主键
					fileId: '',
					//归属文件夹
					folderId: '',
					//归属文件配置
					configId: '',
					//MD5
					文件md5: '',
					//文件名
					fileName: '',
					//保存文件夹
					saveFolder: '',
					//文件大小
					fileSize: '',
					//保存文件名
					saveName: '',

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
				//this.$refs['formContent'].$emit("initTinymce");
			},
			closeWin() {
				//富文本编辑器
				//this.$refs['formContent'].$emit("destroyTinymce");
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/file/saveOrUpdate", this.form).then(
							res => {
								this.loading = false;
								const eventType = this.form.fileId == '' ? 1 : 2;
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
