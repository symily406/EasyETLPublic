<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="100px" :rules="rules">
				<el-form-item label="模板类型" prop="templateCode"
					:rules="[{required: true, message: '请输入模板类型', trigger: 'blur'},{min:1,max:32,message:'模板类型长度不能超过32个字符'}]">
					<el-select v-model="form.templateCode" placeholder="模板类型" size="small" class="max-width-100-ratio">
						<el-option v-for="item in templateCodes" :key="item.value" :label="item.name"
							:value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="名称" prop="name"
					:rules="[{required: true, message: '请输入名称', trigger: 'blur'},{min:1,max:32,message:'名称长度不能超过32个字符'}]">
					<el-input v-model="form.name" placeholder="请输入名称" size="small" maxlength="32" clearable></el-input>
				</el-form-item>
				<el-form-item label="模板内容" prop="jsonCode">
					<vueJsonEditor v-model="jsonCode" :show-btns="false" mode="code" lang="zh"
						:expandedOnStart="true" style="height:500px" @has-error="hasError" /> 
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
	import vueJsonEditor from 'vue-json-editor'
	import { dialogMixin, dicts } from "@/mixins/mixins";
	export default {
		name: 'dbSourceDataxForm',
		components: {
			vueJsonEditor
		},
		props: {

		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				templateCodes: [],
				jsonCode: '',
				form: {
					//主键
					templateId: '',
					//模板类型
					templateCode: '',
					//名称
					name: '',
					//模板内容
					content: '',
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
				let that = this;
				this.initDict("DATAX_SYNC_TEMPLATE", function(res) {
					that.templateCodes = res.data;
				}, function(error) {
					
				});
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			hasError() {
				console.log("JSON格式错误");
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit(formName) {
				this.form.content=JSON.stringify(this.jsonCode);
				if(!this.form.content){
					this.$message.error('模板内容不能为空');
					return;
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/etl/dbSourceDatax/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.templateId == '' ? 1 : 2;
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
<style>
	.jsoneditor-vue {
		height: 100%;
	}
</style>