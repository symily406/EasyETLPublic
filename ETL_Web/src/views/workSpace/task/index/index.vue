<template>
	<div @click="hideTreeMenu">
		<el-container class="task-index-container">
			<table border="0" width="100%" cellspacing="0" callpadding="0">
				<tr>
					<td colspan="2" height="60">
						<workSpaceHeaderContainer ref="workSpaceHeaderContainerRef" class="header-container"
							@menuRoute="menuRoute">
						</workSpaceHeaderContainer>
					</td>
				</tr>
				<tr>
					<td class="bg-white" valign="top">
						<ResizeSplitPane ref="resizeSplitPanle" split-to="columns" :allow-resize="true"
							resizerColor="#2C3E50" :resizerBorderThickness="1" resizerBorderColor="#2C51DC"
							:resizerThickness="4" primary="first" :step="panelStep" :size="defaultSize"
							:min-size="minSize" :max-size="maxSize" @onMouseDown="onMouseDown" @onMouseUp="onMouseUp">
							<template slot="firstPane">
								<workSpaceSidebarContainer ref="workSpaceSidebarContainer"
									@sidebarCallBack="sidebarCallBack" @openTaskNavbar="openTaskNavbar"
									class="task-sidebar-container first-pane">
								</workSpaceSidebarContainer>
							</template>
							<template slot="secondPane">
								<table border="0" width="100%" cellspacing="0" callpadding="0">
									<tr>
										<td>
											<workSpaceNavbar ref="workSpaceNavbar" @openTaskContent="openTaskContent"
												class="task-nav-container"></workSpaceNavbar>
										</td>
									</tr>
									<tr>
										<td>
											<workSpaceMainContainer ref="workSpaceMainContainer"
												class="taks-main-container">
											</workSpaceMainContainer>
										</td>
									</tr>
								</table>
							</template>
						</ResizeSplitPane>
					</td>
				</tr>
			</table>
		</el-container>
		<eTaskCompare ref="eTaskCompareRef" dialogTitle="版本对比" dialogWidth="100%"
			:dialogTop="this.$store.state.global.dialogTop" @rollBack="taskInfoRollBack" />
	</div>
</template>
<script>
	import {
		mapState
	} from 'vuex'
	import workSpaceHeaderContainer from "./modules/header/header.vue";
	import workSpaceSidebarContainer from "./modules/sidebar/index.vue";
	import workSpaceMainContainer from "./modules/main/main.vue";
	import workSpaceNavbar from "./modules/navbar/index.vue";
	import ResizeSplitPane from "@/components/SplitPanle/ResizeSplitPane";
	import eTaskCompare from "../../../admin/etl/projectTask/taskCompare";
	export default {
		name: "workSpaceIndex",
		components: {
			workSpaceHeaderContainer,
			workSpaceSidebarContainer,
			workSpaceMainContainer,
			workSpaceNavbar,
			ResizeSplitPane,
			eTaskCompare
		},
		props: {

		},
		computed: {
			...mapState({
				sideBarWidth: state => state.global.sideBarWidth
			}),
			taskMenuShow: {
				get() {
					return this.$store.state.global.taskMenuShow
				},
				set(val) {
					this.$store.commit("global/global/setTaskMenuShow", val)
				}
			}
		},
		watch: {

		},
		data() {
			return {
				defaultSize: Number(this.$store.state.global.sideBarWidth),
				minSize: Number(this.$store.state.global.sideBarWidth),
				maxSize: Number(this.$store.state.global.sideBarWidth) + 300,
				panelStep: 0,
				orientation: 'horizontal', // 可以设置为 'horizontal' 或 'vertical'
			};
		},
		beforeCreate() {
			document.querySelector("html").setAttribute("style", "background: #fff;");
			document.querySelector("body").setAttribute("style", "background: #fff;");
		},
		mounted() {
			this.$nextTick(() => {
				this.pageResize();
			});
		},
		created() {
			this.$store.commit("global/global/setTableWidth");
			window.addEventListener('resize', this.pageResize);
		},
		destroyed() {
			window.removeEventListener('resize', this.pageResize)
		},
		beforeMount() {

		},
		mounted() {
			window.openTaskCompare = this.openTaskCompare;
		},
		methods: {
			hideTreeMenu() {
				this.taskMenuShow = false;
			},
			menuRoute(param) {
				this.$refs['workSpaceSidebarContainer'].projectId = param.projectId;
				this.$refs['workSpaceSidebarContainer'].projectWorkSpaceId = param.projectWorkSpaceId;
				this.$refs['workSpaceSidebarContainer'].workSpaceType = param.workSpaceType;
				this.$refs['workSpaceSidebarContainer'].initTaskMenus(param.projectId, param.projectWorkSpaceId, param
					.workSpaceType, undefined, () => {});
			},
			//顶部标签
			openTaskNavbar(task) {
				this.$refs['workSpaceNavbar'].projectId = task.projectId;
				this.$refs['workSpaceNavbar'].projectWorkSpaceId = task.projectWorkSpaceId;
				this.$refs['workSpaceNavbar'].workSpaceType = task.workSpaceType;
				this.$refs['workSpaceNavbar'].taskId = task.taskId;
				this.$refs['workSpaceNavbar'].setTaskActiveTab(task.taskId);
				this.$refs['workSpaceNavbar'].init();
				this.openTaskContent(task);
			},
			//内容页
			openTaskContent(task) {
				this.$refs['workSpaceMainContainer'].projectId = task.projectId;
				this.$refs['workSpaceMainContainer'].projectWorkSpaceId = task.projectWorkSpaceId;
				this.$refs['workSpaceMainContainer'].workSpaceType = task.workSpaceType;
				this.$refs['workSpaceMainContainer'].taskId = task.taskId;
				this.$refs['workSpaceMainContainer'].init();

			},
			pageResize() {
				this.$store.commit("global/global/setTableWidth");
			},
			onMouseUp(size) {
				this.$store.state.global.iframeMask = false;
				this.$store.state.global.sideBarWidth = size;
				this.$refs['workSpaceNavbar'].transferTabPanleCalculate();
				this.$refs['workSpaceMainContainer'].transferIFrameWidthCalculate();
			},
			onMouseDown() {
				this.$store.state.global.iframeMask = true;
			},
			openTaskCompare(taskInfo) {
				this.$refs['eTaskCompareRef'].init(taskInfo);
				this.$refs['eTaskCompareRef'].dialogVisible = true;
			},
			taskInfoRollBack(taskInfo) {
				this.$refs['workSpaceMainContainer'].taskInfoRollBack(taskInfo);
			},
			sidebarCallBack(projectWorkSpaceId) {
				this.$refs['workSpaceHeaderContainerRef'].setActiveIndex(projectWorkSpaceId);
			}
		}
	}
</script>
<style scoped="scoped">

</style>