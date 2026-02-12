<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="100px" :rules="rules">
				<el-form-item label="名称" prop="workSpaceLayerName"
					:rules="[{required: true, message: '请输入名称', trigger: 'blur'},{min:1,max:64,message:'名称长度不能超过64个字符'}]">
					<el-input v-model="form.workSpaceLayerName" placeholder="请输入名称" size="small" maxlength="64"
						clearable></el-input>
				</el-form-item>
				<el-form-item label="排序" prop="showOrder" :rules="[{required: true, message: '请输入排序', trigger: 'blur'}]"
					class="el-input-number-left">
					<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1" :max="999999"
						:precision="0" controls-position="right" class="max-width-100-ratio"></el-input-number>
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
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin,
		dicts
	} from "@/mixins/mixins";
	export default {
		name: 'projectWorkSpaceLayerForm',
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
				dictTaskTypes: [],
				form: {
					//主键
					workSpaceLayerId: '',
					//名称
					workSpaceLayerName: '',
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1,
					dictTaskTypes: []
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
				this.initDict("TASK_TYPE", function(res) {
					that.dictTaskTypes = res.data;
				}, function(error) {

				});
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.form.taskTypes = this.form.dictTaskTypes.join(",");
						this.form.dictTaskTypes = [];
						this.$ajax.post(this.getContentPath() + "/admin/etl/projectWorkSpaceLayer/saveOrUpdate",
								this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.layerId == '' ? 1 : 2;
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