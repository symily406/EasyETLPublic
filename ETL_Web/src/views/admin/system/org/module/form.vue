<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="机构全称" prop="orgFullName"
						:rules="[{required: true, message: '请输入机构全称', trigger: 'blur'},{min:1,max:128,message:'机构全称长度不能超过128个字符'}]">
						<el-input v-model="form.orgFullName" placeholder="请输入机构全称" size="small" maxlength="128">
						</el-input>
					</el-form-item>
					<el-form-item label="机构简称" prop="orgName"
						:rules="[{required: true, message: '请输入机构简称', trigger: 'blur'},{min:1,max:128,message:'机构简称长度不能超过128个字符'}]">
						<el-input v-model="form.orgName" placeholder="请输入机构简称" size="small" maxlength="128"></el-input>
					</el-form-item>
					<el-form-item label="机构编码" prop="orgCode"
						:rules="[{required: true, message: '请输入机构编码', trigger: 'blur'},{min:1,max:32,message:'机构编码长度不能超过32个字符'}]">
						<el-input v-model="form.orgCode" placeholder="请输入机构编码" size="small" maxlength="32"></el-input>
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
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'orgForm',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				form: {
					//主键
					orgId: '',
					//归属企业
					companyId: '',
					//机构全称
					orgFullName: '',
					//机构简称
					orgName: '',
					//机构编码
					orgCode: '',
					//上级机构
					pOrgId: '',
					orgPath: '',

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
				
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/org/saveOrUpdate", this.form).then(
							res => {
								this.loading = false;
								const eventType = this.form.orgId == '' ? 1 : 2;
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
