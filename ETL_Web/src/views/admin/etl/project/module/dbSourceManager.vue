<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<projectDbSource ref="projectDbSourceRef"></projectDbSource>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	import projectDbSource from '../../projectDbSource'
	export default {
		name: 'dbSourceManager',
		components: {
			projectDbSource
		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				projectId: undefined
			}
		},
		methods: {
			openWin() {
				this.$refs['projectDbSourceRef'].projectId = this.projectId;
				this.$refs['projectDbSourceRef'].queryParams.projectId = this.projectId;
				this.$refs['projectDbSourceRef'].tableHeight = this.dialogHeight;
			},
			closeWin() {
				this.dialogVisible = false;
				this.$refs['projectDbSourceRef'].projectId = undefined;
				this.$refs['projectDbSourceRef'].queryParams.projectId = undefined;
			},
		}
	}
</script>
<style scoped="scoped">
</style>