<template>
	<div class="parameter-container">
		<el-drawer :title="isFlow==true?'流程参数':'任务参数'" :visible.sync="drawer" :direction="direction" :modal="true"
			:size="compare?'50%':'30%'" @close="closeDrawer">
			<el-card class="box-card" v-show="isFlow==true">
				<div slot="header" class="clearfix">
					<span>全局参数
						<el-tooltip effect="dark" placement="top">
							<div slot="content">
								<div class="tooltip-content">全局参数优选级最高，如果参数键同时出现在全局参数和任务参数中，将会优先使用全局参数中的键</div>
							</div>
							<i class="el-icon-question pointer"></i>
						</el-tooltip></span>
					<el-button size="mini" type="text" class="fright" icon="el-icon-circle-plus-outline"
						@click="addGlobParameters(false)" v-if="!compare">添加参数</el-button>
				</div>
				<el-table :data="taskParameters" class="task-table" height="250">
					<el-table-column prop="key" label="键" width="100">
					</el-table-column>
					<el-table-column prop="name" label="值">
					</el-table-column>
					<el-table-column prop="opt" label="操作" width="100" align="center"
						v-show="isFlow==false||compare==false">
						<template slot-scope="scope">
							<el-button type="text" size="small"
								@click.native.prevent="editGlobParametersRow(scope.$index)">修改</el-button>
							<el-button type="text" size="small"
								@click.native.prevent="removeGlobParametersRow(scope.$index)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-card>
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span>任务参数
						<el-tooltip effect="dark" placement="top" v-show="isFlow==true">
							<div slot="content">
								<div class="tooltip-content">所有任务参数中如果出现键相同，只会出现一个</div>
							</div>
							<i class="el-icon-question pointer"></i>
						</el-tooltip>
					</span>
				</div>
				<el-table :data="taskParameters" class="task-table" :header-cell-style="{textAlign:'center'}">
					<el-table-column prop="paramKey" label="键" width="150">
					</el-table-column>
					<el-table-column prop="paramValue" label="值">
						<template slot-scope="scope">
							<el-input size="mini" v-model="scope.row.paramValue" placeholder="值">
								<template #suffix>
									<el-tooltip effect="dark" placement="top">
										<div slot="content">
											<div class="tooltip-content">
												<div>内置函数:</div>
												<div v-for="item in tooltipContent">
													{{item}}
												</div>
											</div>
										</div>
										<i class="el-icon-question pointer"></i>
									</el-tooltip>
								</template>
							</el-input>
						</template>
					</el-table-column>
				</el-table>
			</el-card>
		</el-drawer>
		<el-dialog title="参数" :visible.sync="dialogVisible" :close-on-click-modal="false" width="30%" @close="closeWin"
			:top="dialogTop" append-to-body>
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="键" prop="paramKey"
						:rules="[{required: true, message: '参数键不能为空', trigger: 'blur'},{min:1,max:32,message:'参数键不能为空长度不能超过32个字符'}]">
						<el-input v-model="form.paramKey" :disabled="isEdit||compare" placeholder="参数键" size="small"
							maxlength="32" clearable></el-input>
					</el-form-item>
					<el-form-item label="值" prop="paramValue"
						:rules="[{required: true, message: '参数值不能为空', trigger: 'blur'},{min:1,max:64,message:'参数值不能超过64个字符'}]">
						<el-input v-model="form.paramValue" placeholder="请输参数值" size="small" maxlength="64" clearable>
							<template #suffix>
								<el-tooltip effect="dark" placement="top">
									<div slot="content">
										<div class="tooltip-content">
											<div>内置函数:</div>
											<div v-for="item in tooltipContent">
												{{item}}
											</div>
										</div>
									</div>
									<i class="el-icon-question pointer"></i>
								</el-tooltip>
							</template>
						</el-input>
					</el-form-item>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button size="small" @click="closeWin">关 闭</el-button>
				<el-button size="small" type="primary" @click="saveGlobParameters" v-show="isFlow==true">确 定</el-button>
				<el-button size="small" type="primary" @click="saveTaskParameters" v-show="isFlow==false">确
					定</el-button>
			</span>
		</el-dialog>
	</div>

</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'parameter',
		components: {

		},
		props: {
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
			compare: {
				type: Boolean,
				default: false
			}
		},
		mixins: [dialogMixin],
		data() {
			return {
				tooltipContent: ["${dateFormat('yyyy-MM-dd')}", "${addYears(1,'yyyy-MM-dd')}",
					"${addMonths(1,'yyyy-MM-dd')}", "${addWeeks(1,'yyyy-MM-dd')}", "${addDays(1,'yyyy-MM-dd')}",
					"${addHours(1,'yyyy-MM-dd HH:mm:ss')}", "${addMinutes(1,'yyyy-MM-dd HH:mm:ss')}", "${uuid()}",
					"${nextId()}"
				],
				rules: {},
				drawer: false,
				tabPosition: 'right',
				direction: 'rtl',
				tableData: [],
				isEdit: false,
				form: {
					key: '',
					value: '',
					type: this.isFlow == true ? 1 : 0
				}
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
			addGlobParameters(isEdit) {
				this.isEdit = isEdit;
				this.dialogVisible = true;
			},
			saveGlobParameters() {
				let parameters = [];
				if (this.isEdit) {
					for (let i = 0; i < this.globParameters.length; i++) {
						let item = this.globParameters[i];
						if (item.key == this.form.key) {
							parameters.push(this.form);
							continue;
						}
						parameters.push(item);
					}
					this.globParameters = parameters;
					this.closeWin();
					return;
				}
				parameters = this.globParameters;
				let parameter = parameters.filter(item => item.key == this.form.key)[0];
				if (parameter && this.isEdit == false) {
					this.$message.error("键为:" + this.form.key + "已存在!");
					return;
				}
				this.globParameters.push(this.form);
				this.closeWin();
			},
			editGlobParametersRow(index) {
				const row = this.globParameters[index];
				this.form = row;
				this.addGlobParameters(true);
			},
			removeGlobParametersRow(index) {
				this.globParameters.splice(index, 1);
			},

			addTaskParameters(isEdit) {
				this.isEdit = isEdit;
				this.dialogVisible = true;
			},
			saveTaskParameters() {
				let parameters = [];
				if (this.isEdit) {
					for (let i = 0; i < this.taskParameters.length; i++) {
						let item = this.taskParameters[i];
						if (item.key == this.form.key) {
							parameters.push(this.form);
							continue;
						}
						parameters.push(item);
					}
					this.taskParameters = parameters;
					this.closeWin();
					return;
				}
				parameters = this.taskParameters;
				let parameter = parameters.filter(item => item.key == this.form.key)[0];
				if (parameter && this.isEdit == false) {
					this.$message.error("键为:" + this.form.key + "已存在!");
					return;
				}
				this.taskParameters.push(this.form);
				this.closeWin();
			},
			editTaskParametersRow(index) {
				const row = this.taskParameters[index];
				this.form = row;
				this.addTaskParameters(true);
			},
			removeTaskParametersRow(index) {
				this.taskParameters.splice(index, 1);
			},

			closeWin() {
				this.dialogVisible = false;
				this.form = {
					key: '',
					value: '',
					type: this.isFlow == true ? 1 : 0
				}
			},
			closeDrawer() {
				this.closeWin();
				this.$emit("closeDrawer");
			},
		}
	}
</script>
<style scoped="scoped">
	.parameter-container {

		.el-card__body,
		.el-main {
			padding: 5px;
		}
	}

	.tooltip-content {
		width: 200px;
	}
</style>