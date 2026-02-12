<template>
	<div class="task-setting">
		<el-tabs :tab-position="tabPosition" @tab-click="tabHandleClick">
			<el-tab-pane v-for="(item, index) in tabs" :label="item.label" :name="item.name"></el-tab-pane>
		</el-tabs>
		<basicAttribute ref="basicAttribute" :projectTaskInfo="projectTaskInfo" :compare="compare"
			@closeDrawer="closeDrawer" />
		<schedue ref="schedue" :projectTaskInfo="projectTaskInfo" :schedueForm="schedueForm"
			:failurePolicy="failurePolicy" :isFlow="isFlow" @closeDrawer="closeDrawer" />
		<envAttribute ref="envAttribute" :evnContent="evnContent" :evnForm="evnForm" :taskStep="taskStep"
			:compare="compare" @closeDrawer="closeDrawer" />
		<parameter ref="parameter" :taskParameters="taskParameters" :globParameters="globParameters" :compare="compare"
			@closeDrawer="closeDrawer" />
	</div>
</template>

<script>
	import basicAttribute from './module/basicAttribute.vue';
	import envAttribute from './module/envAttribute.vue';
	import schedue from './module/schedue.vue';
	import parameter from './module/parameter.vue';
	export default {
		name: 'dataxTask',
		components: {
			basicAttribute,
			envAttribute,
			schedue,
			parameter
		},
		props: {
			evnContent: {
				type: String,
				default: ''
			},
			evnForm: {
				type: Object,
				default: []
			},
			schedueForm: {
				type: Object,
				default: []
			},
			taskStep: {
				type: Number,
				default: 0
			},
			taskParameters: {
				type: Array,
				default: []
			},
			globParameters: {
				type: Array,
				default: []
			},
			isFlow: {
				type: Boolean,
				default: false
			},
			failurePolicy: {
				type: Array,
				default: []
			},
			projectTaskInfo: {
				type: Object,
				default: {}
			},
			compare: {
				type: Boolean,
				default: false
			},
			backup: {
				type: Boolean,
				default: false
			},
			isEnableCluster: {
				type: Number,
				default: 0
			}
		},
		mixins: [],
		data() {
			return {
				tabPosition: 'right',
				allTabs: [{
					"label": "基础属性",
					"name": "1",
					"id": 1
				}, {
					"label": "调度属性",
					"name": "2",
					"id": 2
				}, {
					"label": "环境参数",
					"name": "3",
					"id": 3
				}, {
					"label": "任务参数",
					"name": "4",
					"id": 4
				}],
				tabs: []
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
				switch (tab.name) {
					case "1":
						this.$refs.basicAttribute.drawer = true;
						break;
					case "2":
						this.$refs.schedue.drawer = true;
						break;
					case "3":
						this.$refs.envAttribute.drawer = true;
						break;
					case "4":
						this.$refs.parameter.drawer = true;
						break;
				}
			},
			setTabs(id) {
				this.tabs = this.removeEvnTab(id);
			},
			removeEvnTab(id) {
				let newTabs = [];
				this.allTabs.forEach(item => {
					if (item.id != id) {
						newTabs.push(item);
					}
				});
				return newTabs;
			},
			initBackup() {
				this.$refs['basicAttribute'].initBackup();
			},
			closeDrawer() {
				this.$emit("closeDrawer");
			}
		}
	}
</script>
<style scoped="scoped">

</style>