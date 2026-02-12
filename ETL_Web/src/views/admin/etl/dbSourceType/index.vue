<template>
	<div class="app-container" ref="appContainer">
		<eHeader ref="headerContainer" :queryParams="queryParams" @searchPage="searchPage" @refreshPage="refreshPage"
			@add="add" />
		<el-table size="mini" border :data="tableData" :height="tableHeight"
			:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
			v-loading="loading">
			<el-table-column prop="sourceTypeName" label="名称" width="180">
				<template slot-scope="scope">
					{{scope.row.sourceTypeName}}
				</template>
			</el-table-column>
			<el-table-column prop="sourceTypeImage" label="图片" width="180" align="center">
				<template slot-scope="scope">
					 <img :src="scope.row.sourceTypeImage" height="50"/>
				</template>
			</el-table-column>
			<el-table-column prop="jdbcDrive" label="驱动">
				<template slot-scope="scope">
					{{scope.row.jdbcDrive}}
				</template>
			</el-table-column>


			<el-table-column prop="addTime" label="添加时间" width="150" align="center">
				<template slot-scope="scope">
					{{scope.row.addTime}}
				</template>
			</el-table-column>
			<el-table-column label="配置" width="150">
				<template slot-scope="scope">
					<span style="color: #F56C6C; padding-right: 5px;">{{scope.row.allowEdit==1?'可编辑':'锁定'}}</span>
					<span style="color: #409EFF; padding-right: 5px;">{{scope.row.allowDelete==1?'可删除':'不可删'}}</span>
					<span style="color: #E6A23C;">{{scope.row.enable==1?'启用':'未启用'}}</span>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="150" align="center">
				<template slot-scope="scope">
					<el-button @click.native.prevent="editRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:sourceType:dbSourceType:edit')&&scope.row.allowEdit==1">
						修改
					</el-button>
					<el-button @click.native.prevent="deleteRow(scope.$index)" type="text" size="small"
						v-if="checkPermission('admin:etl:sourceType:dbSourceType:getById')&&scope.row.allowDelete==1">
						删除
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<eForm ref="eForm" dialogTitle="添加数据源类型" dialogWidth="800px" dialogHeight="450px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
		<edit ref="edit" dialogTitle="修改数据源类型" dialogWidth="800px" dialogHeight="450px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import { indexMixin } from "@/mixins/mixins";
	import eHeader from './module/header';
	import eForm from './module/form';
	import edit from './module/edit';
	export default {
		name: 'dbSourceTypeIndex',
		components: {
			eHeader,
			eForm,
			edit
		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				queryParams: {
					//名称
					sourceTypeName: '',
					//图片
					sourceTypeImage: '',
					//驱动
					jdbcDrive: '',
					//归属企业
					companyId: '',
					//归属机构
					orgId: '',
					//允许编辑
					allowEdit: '',
					//允许删除
					allowDelete: '',
					//启用
					enable: '',
					//删除
					isDel: '',
					//添加时间
					addTime: '',
					//添加人员编号
					addUserId: '',
					//修改时间
					updateTime: '',
					//修改人员编号
					updateUserId: '',
					//版本
					version: '',
					//显示排序
					showOrder: ''
				}
			}
		},
		mounted() {
			this.$nextTick(() => {
				this.initTableHeight();
			});
		},
		created() {
			window.addEventListener('resize', this.initTableHeight);

			this.initData(this.queryParams);
		},
		destroyed() {
			window.removeEventListener('resize', this.initTableHeight)
		},
		methods: {
			initData(queryParams) {
				this.loading = true;
				this.$ajax.get(this.getContentPath() + "/admin/etl/dbSourceType/queryAll", this.queryParams).then(res => {
					this.loading = false;
					this.tableData = res.data;
				}).catch(error => {
					this.loading = false;
				});
			},
			reloadPage() {
				this.initData(this.queryParams);
			},
			refreshPage() {
				this.queryParams = this.$options.data().queryParams;
				this.initData(this.queryParams);
			},
			searchPage() {
				this.initData(this.queryParams);
			},
			add() {
				this.$refs['eForm'].dialogVisible = true;
			},
			editRow(index) {
				const row = this.tableData[index];
				if (row.allowEdit == 0) {
					this.$message.error('当前数据源类型不能修改!');
					return false;
				}
				this.$refs['edit'].edit(row);
			},
			deleteRow(index) {
				const row = this.tableData[index];
				if (row.allowDelete == 0) {
					this.$message.error('当前数据源类型不能删除!');
					return false;
				}
				this.$confirm('您确定是否删除一行数据源类型吗?<br>该操作不可恢复!!', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					dangerouslyUseHTMLString: true
				}).then(() => {
					this.$ajax.get(this.getContentPath() +
						"/admin/etl/dbSourceType/deleteById", { "sourceTypeId": row.sourceTypeId })
						.then(
							res => {
								this.eventCallBack(3);
							}).catch(err => {

						});
				});
			},
			eventCallBack(eventType) {
				switch (eventType) {
					case 1: //添加
						this.refreshPage();
						this.$message({
							message: '数据源类型添加成功!',
							type: 'success'
						});
						break;
					case 2: //修改
						this.reloadPage();
						this.$message({
							message: '数据源类型修改成功!',
							type: 'success'
						});
						break;
					case 3: //删除
						this.reloadPage();
						this.$message({
							message: '数据源类型删除成功!',
							type: 'success'
						});
						break;
				}
			},
			initTableHeight() {
				this.tableHeight = this.$refs['appContainer'].offsetHeight - this.$refs['headerContainer'].$el
					.offsetHeight-10;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>