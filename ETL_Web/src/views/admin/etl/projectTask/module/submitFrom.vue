<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="0" :rules="rules">
					<el-form-item prop="remarks"
						:rules="[{required: false, message: '请输入数据源名称', trigger: 'blur'}]">
						<el-input type="textarea" rows="5" v-model="form.remarks" size="small" maxlength="512" placeholder="任务备注"></el-input>
					</el-form-item>
				</el-form>
			</div>
		<div class="tcenter">
			<span>
				<el-button size="small" type="primary" @click="callBack">确 定</el-button>
			</span>
		</div>
	</el-dialog>
</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'submitFrom',
		components: {},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				form: {
					taskId:undefined,
					remarks:undefined
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
			callBack(){
				this.$emit("callBack",this.form.remarks);
				this.closeWin();
			}
		}
	}
</script>
<style scoped="scoped">
</style>