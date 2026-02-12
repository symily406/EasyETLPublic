<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" append-to-body :close-on-click-modal="false"
		:width="dialogWidth" @opened="openWin" @close="closeWin">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container"  v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="权限名称" prop="roleName"
						:rules="[{required: true, message: '请输入权限名称', trigger: 'blur'},{min:1,max:64,message:'权限名称长度不能超过64个字符'}]">
						<el-input v-model="form.roleName" placeholder="请输入权限名称" size="small" maxlength="64"></el-input>
					</el-form-item>
					<el-form-item label="权限" prop="role"
						:rules="[{required: true, message: '请输入权限', trigger: 'blur'},{min:1,max:64,message:'权限长度不能超过64个字符'}]">
						<el-input v-model="form.role" placeholder="请输入权限" size="small" maxlength="64"></el-input>
					</el-form-item>

					<el-form-item label="排序" prop="showOrder"
						:rules="[{required: true, message: '请输入排序', trigger: 'blur'}]" class="el-input-number-left">
						<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1"
							:max="999999" :precision="0" controls-position="right" class="max-width-100-ratio">
						</el-input-number>
					</el-form-item>
					<el-form-item label="配置" v-show="false">
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
	import {dialogMixin} from "@/mixins/mixins";
	export default {
		name: 'menuRoleForm',
		components: {
			
		},
		props: {
			
		},
		mixins: [dialogMixin],
		data() {
			return {
				form: {
					//主键
					roleId: '',
					//所属菜单
					menuId: '',
					menuPath: '',
					//权限名称
					roleName: '',
					//权限
					role: '',
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
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/menu/menuRole/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.roleId == '' ? 1 : 2;
								this.$emit('eventCallBack', eventType);
								this.closeWin();
							}).catch(err => {
								this.loading = false;
							});
					} else {
						return false;
					}
				});
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
