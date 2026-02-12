<template>
	<div class="card-container" v-loading="loading">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height: contentHeight+'px'}">
			<el-row>
				<el-col :span="6" v-for="(item,index) in tableData" :key="item.id">
					<el-card class="box-card" shadow="hover" :body-style="{padding:'0'}">
						<div slot="header" class="box-card-header">

							<el-tooltip>
								<div slot="content">
									<div v-for="(memberWorkSpace,memberWorkSpaceIndex) in item.memberWorkSpaceVos">
										{{memberWorkSpace.workSpaceName}}：{{memberWorkSpace.failTaskNum}}
									</div>
								</div>
								<span>今日失败：<i>{{item.failTask}}</i></span>
							</el-tooltip>

							{{item.projectName}}
						</div>
						<div class="box-card-item">
							<el-row>
								<el-col :span="12">总任务量：{{item.totalTask}}</el-col>
								<el-col :span="12">数据源：{{item.dbSourceNum}}</el-col>
							</el-row>
						</div>
						<div class="box-card-item">
							<el-row>
								<el-col :span="6"
									v-for="(memberWorkSpace,memberWorkSpaceIndex) in item.memberWorkSpaceVos">{{memberWorkSpace.workSpaceName}}：{{memberWorkSpace.taskNum}}</el-col>
							</el-row>
						</div>
						<div class="box-card-footer">
							<el-button type="text" @click.native.prevent="openWin(memberWorkSpace)"
								v-for="(memberWorkSpace,memberWorkSpaceIndex) in item.memberWorkSpaceVos">{{memberWorkSpace.workSpaceName}}</el-button>
						</div>
					</el-card>
				</el-col>
			</el-row>
		</el-scrollbar>
	</div>
</template>
<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import {
		mapState
	} from 'vuex'
	export default {
		name: "dashboard",
		components: {

		},
		props: {

		},
		computed: {
			...mapState({
				contentHeight: state => state.global.contentHeight
			}),
		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					searchKey: ''
				}
			}
		},
		mounted() {

		},
		created() {
			this.initData(this.queryParams);
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/project/queryIndexPage", this.addPageParam(
					queryParams, this
					.page)).then(res => {
					this.loading = false;
					this.page = this.$app.extend(this.page, this.$app.setPage(res.data));
					this.tableData = res.data.records;
				}).catch(error => {
					this.loading = false;
				});
			},
			handleRoute(projectId, calculateTypes) {
				this.$router.push({
					path: "/workSpace/dataIntegration/",
					query: {
						"projectId": projectId,
						"calculateTypes": calculateTypes
					}
				});
			},
			openWin(row) {
				const {
					href
				} = this.$router.resolve({
					path: '/workSpace/index',
					query: {
						projectId: row.projectId,
						projectWorkSpaceId: row.projectWorkSpaceId,
						workSpaceType: row.workSpaceType
					}
				});
				window.open(href, '_blank');
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped>
	.box-card-footer {
		.el-button {
			padding: 0px;
		}
	}
</style>