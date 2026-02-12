<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" v-loading="loading">
		<div class="form-container">
			<el-form ref="form" :model="form" :rules="rules">
				<el-form-item  prop="mainName"
					:rules="[{required: true, message: '请输入文件名', trigger: 'blur'},{min:1,max:32,message:'文件名长度不能超过32个字符'}]">
					<el-input v-model="form.mainName" placeholder="文件名" size="small" maxlength="32"></el-input>
				</el-form-item>
			</el-form>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	//富文本编辑器
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'folderForm',
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
				form: {
					id: '',
					mainName:'',
					parentId: '',
					fileId:'',
					fullPath:'',
					suffix:'',
					isFolder:'0',
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
						this.$ajax.post(this.getContentPath() + "/admin/system/file/userResources/fileRename",
							{
								"id":this.form.id,
								"mainName":this.form.mainName,
								"fileId":this.form.fileId
							}
						).then(
							res => {
								this.loading = false;
								const eventType = 200;
								this.$emit('eventCallBack',res.data, eventType);
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
