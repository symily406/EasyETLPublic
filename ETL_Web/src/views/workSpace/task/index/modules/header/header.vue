<template>
	<div>
		<el-row>
			<el-col :span="3" class="log">
				<img src="~@/assets/logo.png" width="60">
			</el-col>
			<el-col :span="19">
				<el-menu :default-active="activeIndex" mode="horizontal" :background-color="menuBackgroundColor"
					:text-color="menuTextColor" :active-text-color="menuActiveTextColor">
					<el-menu-item :index="`${item.projectWorkSpaceId}`" v-for="(item,index) in headerMenus"
						:key="item.projectWorkSpaceId" @click="handleRoute(item)">{{item.workSpaceName}}</el-menu-item>
				</el-menu>
			</el-col>
			<el-col :span="2"></el-col>
		</el-row>
	</div>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	export default {
		name: "workSpaceHeaderContainer",
		components: {

		},
		props: {

		},
		computed: {
			...mapState({
				menuBackgroundColor: state => state.global.menuBackgroundColor,
				menuTextColor: state => state.global.menuTextColor,
				menuActiveTextColor: state => state.global.menuActiveTextColor
			}),
		},
		data() {
			return {
				activeIndex: "0",
				headerMenus: [],
				menuBg: this.variables.menuBg,
				projectId: '',
				projectWorkSpaceId: '',
				workSpaceType: ''
			}
		},
		watch: {

		},
		created() {
			this.initWorkSpace();
		},
		methods: {
			setActiveIndex(projectWorkSpaceId) {
				this.activeIndex = projectWorkSpaceId;
			},
			initWorkSpace() {
				this.projectId = this.$route.query.projectId;
				this.projectWorkSpaceId = this.$route.query.projectWorkSpaceId;
				this.workSpaceType = this.$route.query.workSpaceType;
				this.activeIndex = this.projectWorkSpaceId;
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectMember/queryProjectMemberWorkSpace", {
					"projectId": this.projectId
				}).then(res => {
					this.headerMenus = res.data;
				}).catch(error => {

				});
			},
			handleRoute(menu) {
				this.activeIndex = menu.projectWorkSpaceId;
				let params = {
					projectId: menu.projectId,
					projectWorkSpaceId: menu.projectWorkSpaceId,
					workSpaceType: menu.workSpaceType
				};
				this.$emit("menuRoute", params);
			},
			variables() {
				return variables;
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>

<style scoped>
	.el-menu.el-menu--horizontal {
		border-bottom: none;
	}
</style>