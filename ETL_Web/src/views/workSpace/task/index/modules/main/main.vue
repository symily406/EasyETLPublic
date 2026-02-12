<template>
	<div class="iframe-container" :style="{height:iframeHeight+'px'}">
		<!-- <div class="iframe-mask" v-show="this.$store.state.global.iframeMask"></div> -->
		<iframe :ref="'iframe_'+item.taskId" frameborder="0" v-show="item.taskId==taskActiveTab"
			v-for="(item, index) in iframeContents"></iframe>
		<iframe class="iframe-responsive" frameborder="0" v-show="this.$store.state.global.defaultTaskId==taskActiveTab"
			src="#/workSpace/index/dashboard"></iframe>
	</div>
</template>
<script>
	import {
		mapState
	} from 'vuex';
	export default {
		name: "workSpaceMainContainer",
		components: {

		},
		props: {

		},
		computed: {

		},
		data() {
			return {
				itemRefs: [],
				taskActiveTab: this.$store.state.global.defaultTaskId,
				iframeContents: [],
				projectId: '',
				projectWorkSpaceId: '',
				workSpaceType: '',
				iframeHeight: this.initIframeHeight(),
				iframeWidth: this.iFrameWidthCalculate(),
			}
		},
		created() {

		},
		mounted() {
			let that = this;
			window.addEventListener('resize', function() {
				that.iframeHeight = that.initIframeHeight();
				that.iframeWidth = that.iFrameWidthCalculate();
			});
		},
		methods: {
			init() {
				this.iframeContents = this.getIframeContents();
				this.taskActiveTab = this.getTaskActiveTab();
				this.open();
			},
			open() {
				let tab = this.iframeContents.filter(v => v.taskId == this.taskActiveTab)[0];
				if (tab) {
					let taskId = tab.taskId;
					this.$nextTick(() => {
						this.$refs['iframe_' + taskId][0].src = tab.url;
					});
				}
			},
			getIframeContents() {
				let tabs = [];
				let storeTabs = localStorage.getItem(this.getSessionTabKey(this.$store.state.global.taskTabName));
				if (storeTabs) {
					tabs = JSON.parse(storeTabs);
				}
				let iframeContent = [];
				tabs.forEach(item => {
					let url = "";
					switch (parseInt(item.workSpaceType)) {
						case 1:
							url = "#/workSpace/task/dataxTask";
							break;
						case 2:
							url = "#/workSpace/task/sqlTask";
							break;
						case 5:
							url = "#/workSpace/task/flowTask";
							break;
					};
					let param = "taskId=" + item.taskId + "&projectId=" + item.projectId +
						"&projectWorkSpaceLayerRelId=" + item.projectWorkSpaceLayerRelId + "&workSpaceType=" + item
						.workSpaceType;
					iframeContent.push({
						taskId: item.taskId,
						url: url + "?" + param
					});
					this.itemRefs.push("iframe_" + item.taskId);
				});
				return iframeContent;
			},
			getTaskActiveTab() {
				return localStorage.getItem(this.getSessionTabKey(this.$store.state.global.taskTableIndexName));
			},
			getSessionTabKey(keyName) {
				return keyName.concat("_").concat(this.projectId);
			},
			initIframeHeight() {
				return window.innerHeight - 105;
			},
			transferIFrameWidthCalculate() {
				this.iframeWidth = this.iFrameWidthCalculate();
			},
			iFrameWidthCalculate() {
				var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
				return screenWidth - this.$store.state.global.sideBarWidth - 100;
			},
			taskInfoRollBack(taskInfo) {
				let tab = this.iframeContents.find(v => v.taskId == taskInfo.backupTaskId);
				let index = this.iframeContents.findIndex(v => v.taskId == taskInfo.backupTaskId);
				let url = tab.url;
				if (url.indexOf("?") > -1) {
					url += "&reloadTime=" + new Date().getTime();
				}
				if(index>-1){
					let taskId = tab.taskId;
					this.$nextTick(() => {
						this.$refs['iframe_' + taskId][0].src = url;
					});
				}
			}
		}
	}
</script>