<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin"  :top="dialogTop">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="120px" :rules="rules">
					<el-form-item label="名称" prop="menuName"
						:rules="[{required: true, message: '请输入菜单名称', trigger: 'blur'},{min:1,max:32,message:'菜单名称长度不能超过32个字符'}]">
						<el-input v-model="form.menuName" placeholder="请输入菜单名称" size="small" maxlength="32"></el-input>
					</el-form-item>

					<el-form-item label="菜单配置" v-show="form.menuId=='' || form.isMenu==1">
						<el-checkbox v-model="form.isExternal" v-show="form.isMenu==1" :true-label="1" :false-label="0"
							label="是否外部地址" @change="externalChange">
						</el-checkbox>
						<span class="fright cred">(注:选中为是)</span>
					</el-form-item>
					<el-form-item label="路由地址" prop="routePath" v-if="form.isMenu==1"
						:rules="[{required: true, message: '请输入路由地址', trigger: 'blur'},{min:1,max:64,message:'路由地址长度不能超过64个字符'}]">
						<el-input v-model="form.routePath" placeholder="请输入路由地址" size="small" maxlength="64"
							@change="routePathChange"></el-input>
					</el-form-item>
					<el-form-item label="页面名称" prop="pageName" v-if="form.isMenu==1"
						:rules="[{required: true, message: '请输入页面名称', trigger: 'blur'},{min:1,max:32,message:'页面名称长度不能超过32个字符'}]">
						<el-input v-model="form.pageName" placeholder="请输入页面名称" size="small" maxlength="32"></el-input>
					</el-form-item>
					<el-form-item label="访问地址" prop="url" v-if="form.isMenu==1&&form.isExternal==1"
						:rules="[{required: true, message: '请输入访问地址', trigger: 'blur'},{min:1,max:512,message:'访问地址长度不能超过512个字符'}]">
						<el-input v-model="form.url" placeholder="请输入访问地址" size="small" maxlength="512"></el-input>
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
	import {dialogMixin} from "@/mixins/mixins";
	export default {
		name: 'menuForm',
		components: {
			
		},
		props: {
			
		},
		mixins: [dialogMixin],
		data() {
			return {
				form: {
					//主键
					menuId: '',
					//菜单名称
					menuName: '',
					//标题
					title: '',
					//路由地址
					routePath: '',
					//页面名称
					pageName: '',
					//访问地址
					url: '',
					//图标
					icon: '',
					//是否菜单
					isMenu: 1,
					//是否外部地址
					isExternal: 0,
					//是否隐藏
					isHidden: 0,
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
			menuChange(val) {
				if (this.form.isMenu == 0) {
					this.form.routePath = '';
					this.form.pageName = '';
					this.form.isExternal = 0;
					this.form.url = '';
				}
			},
			externalChange(val) {
				if (this.form.isExternal == 0) {
					this.form.url = '';
				}
			},
			routePathChange(val) {
				if (val) {
					if (!this.form.pageName) {
						this.form.pageName = val.substr(val.lastIndexOf("/")).concat("Index").replace("/", "");
					}
				}
			},
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
						this.$ajax.post(this.getContentPath() + "/admin/system/indexPage/saveOrUpdate", this.form).then(
							res => {
								this.loading = false;
								const eventType = this.form.menuId == '' ? 1 : 2;
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
