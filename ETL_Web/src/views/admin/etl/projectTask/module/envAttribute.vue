<template>
	<el-drawer title="环境参数" :visible.sync="drawer" :direction="direction" :modal="true" :size="compare?'50%':'30%'"
		@close="closeDrawer">
		<el-card class="box-card">
			<codeEditor ref="codeEditorRef" style="height: 400px;" @change="codeChange" v-model="evnContent"
				language="plaintext" :fontSize="12" :readOnly="compare"></codeEditor>
		</el-card>
	</el-drawer>
</template>
<script>
	import codeEditor from "@/components/monaco/monaco.vue";
	export default {
		name: 'basicAttribute',
		components: {
			codeEditor
		},
		props: {
			evnForm: {
				type: Object,
				default: []
			},
			taskStep: {
				type: Number,
				default: 0
			},
			compare: {
				type: Boolean,
				default: false
			},
			evnContent: {
				type: String,
				default: ''
			}
		},
		mixins: [],
		data() {
			return {
				drawer: false,
				direction: 'rtl',
				content: '',
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
			closeDrawer() {
				this.$emit("closeDrawer");
			},
			codeChange(code) {
				this.evnForm.evnContent = code;
			}
		}
	}
</script>
<style scoped="scoped">

</style>