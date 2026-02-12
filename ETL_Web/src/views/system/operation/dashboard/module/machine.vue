<template>
	<el-table size="mini" :data="datas" :height="contentHeight/2-50"
		:style="{width:this.$store.state.global.tableWidth+'px'}" :header-cell-style="{textAlign:'center'}"
		v-loading="loading">
		<el-table-column type="expand">
			<template slot-scope="props">
				<el-row>
					<el-col :span="12">
						<el-card class="box-card">
							<div slot="header" class="clearfix">
								<span>CPU信息</span>
							</div>
							<table border="0" cellspacing="0" callpadding="0" width="100%" class="table">
								<tr style="background-color: #F2F2F2;">
									<th width="60%">属性</th>
									<th width="40%">值</th>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">核心数</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.cpu.cpuNum }}</td>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">用户使用率</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.cpu.used }}%</td>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">系统使用率</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.cpu.sys }}%</td>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">当前空闲率</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.cpu.free }}%</td>
								</tr>
							</table>
						</el-card>
					</el-col>
					<el-col :span="12">
						<el-card class="box-card">
							<div slot="header" class="clearfix">
								<span>内存信息</span>
							</div>
							<table border="0" cellspacing="0" callpadding="0" width="100%" class="table">
								<tr style="background-color: #F2F2F2;">
									<th width="60%">属性</th>
									<th width="40%">内存</th>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">总内存</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.memory.total }}G</td>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">已用内存</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.memory.used }}G</td>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">剩余内存</td>
									<td :class="[props.row.status==0?'cgrey':'']">{{ props.row.memory.free  }}G</td>
								</tr>
								<tr>
									<td :class="[props.row.status==0?'cgrey':'']">使用率</td>
									<td :class="[props.row.status==0?'cgrey':'']">
										{{ (props.row.memory.used/props.row.memory.total*100).toFixed(2) }}%
									</td>
								</tr>
							</table>
						</el-card>
					</el-col>
				</el-row>
				<el-card class="box-card">
					<div slot="header" class="clearfix">
						<span>磁盘信息</span>
					</div>
					<table border="0" cellspacing="0" callpadding="0" width="100%" class="table">
						<tr style="background-color: #F2F2F2;">
							<th>盘符路径</th>
							<th width="200">文件系统 </th>
							<th width="200">盘符类型</th>
							<th width="200">总大小</th>
							<th width="200">可用大小</th>
							<th width="200">已用大小</th>
							<th width="200">已用百分比</th>
						</tr>
						<tr v-for="(item, index) in props.row.disks" :key="index">
							<td :class="[props.row.status==0?'cgrey':'']">{{item.dirName}}</td>
							<td :class="[props.row.status==0?'cgrey':'']">{{item.sysTypeName}}</td>
							<td :class="[props.row.status==0?'cgrey':'']">{{item.typeName}}</td>
							<td :class="[props.row.status==0?'cgrey':'']">{{item.total}}</td>
							<td :class="[props.row.status==0?'cgrey':'']">{{item.free}}</td>
							<td :class="[props.row.status==0?'cgrey':'']">{{item.used}}</td>
							<td :class="[props.row.status==0?'cgrey':'']">{{item.usageRate}}%</td>
						</tr>
					</table>
				</el-card>
			</template>
		</el-table-column>
		<el-table-column prop="computerName" label="服务器名称">
			<template slot-scope="scope">
				<span :class="[scope.row.status==0?'cgrey':'']">{{scope.row.computerName}}</span>
				<span class="cblue f10 pointer" v-if="scope.row.isMaster==1" title="主节点">[主]</span>
			</template>
		</el-table-column>
		<el-table-column prop="computerIp" label="服务器IP" width="100" align="center">
			<template slot-scope="scope">
				<span :class="[scope.row.status==0?'cgrey':'']">{{scope.row.computerIp}}</span>
			</template>
		</el-table-column>
		<el-table-column prop="osName" label="操作系统" width="200" align="center">
			<template slot-scope="scope">
				<span :class="[scope.row.status==0?'cgrey':'']">{{scope.row.osName}}</span>
			</template>
		</el-table-column>

		<el-table-column prop="reportTime" label="上报时间" width="150" align="center">
			<template slot-scope="scope">
				<span :class="[scope.row.status==0?'cgrey':'']">{{scope.row.reportTime}}</span>
			</template>
		</el-table-column>
		<el-table-column prop="status" label="状态" width="100" align="center">
			<template slot-scope="scope">
				<span v-if="scope.row.status==1" class="cblue">正常</span>
				<span v-if="scope.row.status==0" class="cred">离线</span>
			</template>
		</el-table-column>

		<el-table-column prop="status" label="操作" width="100" align="center">
			<template slot-scope="scope">
				<el-button type="text" @click="removeMachine(scope.row)">删除</el-button>
			</template>
		</el-table-column>
	</el-table>
</template>

<script>
	import {
		indexMixin
	} from "@/mixins/mixins";
	import {
		mapState
	} from 'vuex';
	export default {
		name: 'machine',
		components: {

		},
		props: {

		},
		mixins: [indexMixin],
		data() {
			return {
				datas: []
			}
		},
		computed: {
			...mapState({
				contentHeight: state => state.global.contentHeight
			}),
		},
		methods: {
			removeMachine(row) {
				this.$confirm('您确定要删除服务器信息吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					console.log("1111111111111111");
					let index = this.datas.findIndex(item => item.machineId == row.machineId);
					console.log("2222222");
					this.$ajax.get(this.getContentPath() + "/admin/etl/machine/deleteById", {
						"machineId": row.machineId
					}).then(res => {
						if (index > -1) {
							this.datas.splice(index, 1);
						}
					}).catch(error => {

					});
				}).catch(() => {

				});
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>