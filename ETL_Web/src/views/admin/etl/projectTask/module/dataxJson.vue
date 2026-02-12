<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body class="datax-json">
		<vueJsonEditor v-model="dataxJson" :show-btns="false" mode="code" lang="zh" :expandedOnStart="true"
			:style="{height:dialogHeight}" @has-error="hasError" v-loading="loading"/> 
	</el-dialog>
</template>
<script>
	import vueJsonEditor from 'vue-json-editor'
	import { dialogMixin } from "@/mixins/mixins";
	export default {
		name: 'dataxJson',
		components: {
			vueJsonEditor
		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				disabled: false,
				taskId: '',
				dataxJson: ''
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
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTask/createDataxJson", { "taskId": this.taskId })
					.then(res => {
						this.loading = false;
						if (res.data) {
							this.dataxJson = JSON.parse(res.data);
						}
					}).catch(err => {
						this.loading = false;
					});
			},
			hasError() {
				console.log("JSON格式错误");
			},
			closeWin() {
				this.dialogVisible = false;
				this.dataxJson = "";
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style>
	.jsoneditor-vue {
		height: 100%;
	}
</style>