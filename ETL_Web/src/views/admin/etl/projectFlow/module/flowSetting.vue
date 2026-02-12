<template>
	<div class="task-setting">
		<el-tabs :tab-position="tabPosition"  @tab-click="tabHandleClick">
			<el-tab-pane label="基础属性" name="1"></el-tab-pane>
			<el-tab-pane label="调度性性" name="2"></el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
	export default {
		name: 'flowSetting',
		components: {
			
		},
		props: {
			
		},
		mixins: [],
		data() {
			return {
				tabPosition: 'right'
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
			tabHandleClick(tab, event) {
				// switch (tab.name) {
				// 	case "1":
				// 		this.$refs.basicAttribute.drawer = true;
				// 		break;
				// 	case "2":
				// 		break;
				// 	case "3":
				// 		this.$refs.envAttribute.drawer = true;
				// 		break;
				// }
			}
		}
	}
</script>
<style scoped="scoped">

</style>