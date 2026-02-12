<template>
	<div class="scrollable-container tleft f12" :style="{height:(logContainerHeight)+'px',paddingLeft:'5px'}"
		id="logContainer" ref="logContainer">
		<p v-for="item in logInfo" style="white-space: pre-wrap;">
			<span
				v-if="item.status==0||item.status==1||item.status==2||item.status==10||item.status==100||item.status==20"><span
					class="cgreen">[{{item.addTime}}]</span>&nbsp;&nbsp;&lt;info&gt;&nbsp;&nbsp;{{item.logContent}}
			</span>
			<span v-if="item.status==30||item.status==40"
				class="cyellow">[{{item.addTime}}]&nbsp;&nbsp;&lt;info&gt;&nbsp;&nbsp;{{item.logContent}}</span>
			<span v-if="item.status==-1"
				class="cred">[{{item.addTime}}]&nbsp;&nbsp;&lt;error&gt;&nbsp;&nbsp;{{item.logContent}}</span>

			<span v-if="item.status==0&&isFlow&&item.isEndNode==0"><a href="javascript:void(0)"
					@click="showLogInfo(item.jobId)"><span class="cgreen">[查看日志]</span></a></span>
			<span v-if="item.status==10&&isFlow&&item.isEndNode==0"><a href="javascript:void(0)"
					@click="showLogInfo(item.jobId)"><span class="cgreen">[查看日志]</span></a></span>
			<span v-if="item.status==-1&&isFlow&&item.isEndNode==0"><a href="javascript:void(0)"
					@click="showLogInfo(item.jobId)"><span class="cred">[查看日志]</span></a></span>
		</p>
	</div>
</template>
<script>
	export default {
		name: 'logContent',
		components: {

		},
		mixins: [],
		props: {
			logContainerHeight: {
				type: Number,
				default: 0
			},
			logInfo: {
				type: Array,
				default: () => []
			},
			isFlow: {
				type: Boolean,
				default: false
			}
		},
		watch: {
			logContainerHeight(nval, oldval) {

			}
		},
		computed: {

		},
		data() {
			return {

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
			showLogInfo(jobId) {
				this.$emit("showLogInfo", jobId);
			}
		}
	}
</script>
<style scoped="scoped">

</style>