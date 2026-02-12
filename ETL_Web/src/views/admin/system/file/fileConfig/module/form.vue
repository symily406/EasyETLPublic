<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="150px" :rules="rules">
					<el-form-item label="名称" prop="name"
						:rules="[{required: true, message: '请输入名称', trigger: 'blur'},{min:1,max:128,message:'名称长度不能超过128个字符'}]">
						<el-input v-model="form.name" placeholder="请输入名称" size="small" maxlength="128"></el-input>
					</el-form-item>
					<el-form-item label="文件类型" prop="fileType"
						:rules="[{required: true, message: '请选择文件类型', trigger: 'blur'}]">
						<el-select v-model="form.fileType" placeholder="请选择文件类型" size="small" class="max-width-100-ratio" clearable>
							<el-option v-for="item in fileTypes" :key="item.value" :label="item.name" 
						 	:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>

					<el-form-item label="允许上传类型" prop="accepts"
						:rules="[{required: true, message: '请选择允许上传类型', trigger: 'blur'}]">
						<el-select v-model="form.accepts" placeholder="请选择允许上传类型" size="small" class="max-width-100-ratio" clearable multiple>
							<el-option v-for="item in accepts" :key="item.value" :label="item.name" 
						 	:value="item.value">
							</el-option>
						</el-select>
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
	import { dialogMixin, dicts } from "@/mixins/mixins";
	export default {
		name: 'fileConfigForm',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				fileTypes:[],
				saveTypes:[],
				accepts:[],
				form: {
					//主键
					configId: '',
					//文件类型
					fileType: '',
					//文件存储方式
					saveType: '',
					//名称
					name: '',
					//允许上传类型
					accepts:[],
					accept:'',
					//分片上传大小
					chunkSize: 1,

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
				this.initFileTypes();
				this.initSaveTypes();
				this.initAccepts();
			},
			closeWin() {
				//富文本编辑器
				//this.$refs['formContent'].$emit("destroyTinymce");
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			initFileTypes(){
				this.initDict("file_type_code",res=>{
					this.fileTypes=res.data;
				},err=>{
					
				});
			},
			initSaveTypes(){
				this.initDict("file_save_type",res=>{
					this.saveTypes=res.data;
				},err=>{
					
				});
			},
			initAccepts(){
				this.initDict("file_accept_code",res=>{
					this.accepts=res.data;
				},err=>{
					
				});
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.form.accept=this.form.accepts.join(",");
						//this.form.accepts=[];
						this.$ajax.post(this.getContentPath() + "/admin/system/file/fileConfig/saveOrUpdate", this
								.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.configId == '' ? 1 : 2;
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
