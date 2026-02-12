<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<projectMember ref="projectMemberRef"></projectMember>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	import projectMember from '../../projectMember'
	export default {
		name: 'projectMemberManager',
		components: {
			projectMember
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
				this.$refs['projectMemberRef'].projectId = this.projectId;
				this.$refs['projectMemberRef'].queryParams.projectId = this.projectId;
				this.$refs['projectMemberRef'].tableHeight = this.dialogHeight;
			},
			closeWin() {
				this.dialogVisible = false;
				this.$refs['projectMemberRef'].projectId = undefined;
				this.$refs['projectMemberRef'].queryParams.projectId = undefined;
			},
		}
	}
</script>
<style scoped="scoped">
</style>