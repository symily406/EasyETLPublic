<template>
	<div class="step">
		<table border="0" width="100%" cellspacing="0" callpadding="5">
			<tr>
				<td width="100" align="center">通道控制</td>
				<td>&nbsp;</td>
				<td width="100" align="center" v-if="compare==false"><el-button type="text"
						v-if="taskStep==5||taskStep==50" @click="nextSetp(4)">修改</el-button></td>
			</tr>
		</table>
		<el-divider></el-divider>
		<div v-loading="loading">
			<table border="0" width="100%" cellspacing="0" callpadding="0">
				<tr>
					<td class="column-container">
						<div class="task-mask" v-if="taskStep==5||taskStep==50"></div>
						<el-form class="max-width-100-ratio" ref="form" :model="form" label-width="120px"
							:rules="rules">
							<el-form-item label="作业上限速率" prop="taskSpeed"
								:rules="[{required: true, message: '请选择作业上限速率', trigger: 'blur'}]">
								<el-select :disabled="isDisabled" v-model="form.taskSpeed" placeholder="请选择作业上限速率"
									size="small" class="max-width-100-ratio">
									<el-option v-for="item in taskSpeeds" :key="item.value" :label="item.name"
										:value="item.value">
									</el-option>
								</el-select>
							</el-form-item>
							<el-form-item label="作业并发数" prop="taskProcess"
								:rules="[{required: true, message: '请选择作业并发数', trigger: 'blur'}]">
								<el-select :disabled="isDisabled" v-model="form.taskProcess" placeholder="请选择作业并发数"
									size="small" class="max-width-100-ratio">
									<el-option v-for="item in taskProcess" :key="item.value" :label="item.name"
										:value="item.value">
									</el-option>
								</el-select>
							</el-form-item>
							<el-form-item label="错误记录数超过" prop="errorRecord"
								:rules="[{required: false, message: '数据过虑', trigger: 'blur'}]" class="tleft">
								<el-input-number :disabled="isDisabled" v-model="form.errorRecord" size="small"
									placeholder="错误记录超过则任务停止" controls-position="right" :min="0"
									:max="9999999"></el-input-number>条, 任务自动结束
							</el-form-item>
							<el-form-item label="错误记录比例配置" prop="percentage"
								:rules="[{required: false, message: '数据过虑', trigger: 'blur'}]" class="tleft">
								<table border="0" cellspacing="0" callpadding="0">
									<tr>
										<td>任务执行结束后,统计错误记录占比,大于</td>
										<td><el-input-number :disabled="isDisabled" v-model="form.percentage"
												size="small" placeholder="" controls-position="right" :min="1"
												:max="100"></el-input-number></td>
										<td>%时, 任务自动结束</td>
									</tr>
								</table>
							</el-form-item>
						</el-form>
					</td>
				</tr>
			</table>
			<table border="0" width="100%" cellspacing="0" callpadding="5">
				<tr>
					<td width="100">&nbsp;</td>
					<td align="center">
						<el-button v-show="taskStep==4" size="small" @click="nextSetp(3)">上一步</el-button>
						<el-button v-show="taskStep==4" type="primary" size="small"
							@click="onSubmit('form',5)">下一步</el-button>
						<el-button v-show="taskStep==5" size="small" @click="nextSetp(4)">上一步</el-button>
						<el-button v-show="taskStep==5" type="primary" size="small" @click="taskSubmit(50)">保
							存</el-button>
					</td>
					<td width="100">&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
</template>
<script>
	import {
		dicts
	} from "@/mixins/mixins";
	export default {
		name: 'dataxStep04',
		components: {},
		props: {
			isDisabled: {
				type: Boolean,
				default: false
			},
			taskStep: {
				type: Number,
				default: 1
			},
			compare: {
				type: Boolean,
				default: false
			},
			backup: {
				type: Boolean,
				default: false
			}
		},
		mixins: [dicts],
		data() {
			return {
				rules: {},
				loading: true,
				taskSpeeds: [],
				taskProcess: [],
				taskId: '',
				form: {
					//主键
					channelId: '',
					//归属任务
					taskId: '',
					projectId: '',
					projectWorkSpaceLayerRelId: '',
					projectWorkSpaceId: '',
					workSpaceType: '',
					taskFolderId: '',
					//速度
					taskSpeed: '-1',
					//作业并发数
					taskProcess: '1',
					//错误记录数
					errorRecord: '100',
					//错误比例
					percentage: '2',
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1,
					taskStep: 5
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
			destroy() {
				this.rules = {};
				this.loading = true;
				this.taskSpeeds = [];
				this.taskProcess = [];
				this.taskId = '';
				this.form = {
					//主键
					channelId: '',
					//归属任务
					taskId: '',
					projectId: '',
					projectWorkSpaceLayerRelId: '',
					projectWorkSpaceId: '',
					workSpaceType: '',
					taskFolderId: '',
					//速度
					taskSpeed: '-1',
					//作业并发数
					taskProcess: '1',
					//错误记录数
					errorRecord: '100',
					//错误比例
					percentage: '2',
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1,
					taskStep: 5
				}
			},
			init() {
				this.initTaskDict();
				this.initChannel();
			},
			initTaskDict() {
				let that = this;
				this.initDict("TASK_SPEED", function(res) {
					that.taskSpeeds = res.data;
				}, function(error) {

				});
				this.initDict("TASK_PROCESS", function(res) {
					that.taskProcess = res.data;
				}, function(error) {

				});
			},
			initChannel() {
				let taskId = this.taskId;
				if (!taskId) {
					return;
				}
				let url = "/admin/etl/batchTask/batchProjectLayerTaskChannel/getById";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryBatchProjectLayerTaskChannelBackupByTaskId";
				}
				this.loading = true;
				this.$ajax.get(this.getContentPath() + url, {
					"taskId": taskId
				}).then(
					res => {
						if (res.data) {
							this.form = res.data;
							this.form.taskSpeed = res.data.taskSpeed.toString();
							this.form.taskProcess = res.data.taskProcess.toString();
							this.form.errorRecord = res.data.errorRecord.toString();
							this.form.percentage = res.data.percentage.toString();
						}
						this.loading = false;
					}).catch(err => {
					this.loading = false;
				});
			},
			nextSetp(nextSetp) {
				this.$emit('nextSetp', nextSetp);
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			taskSubmit(nextSetp) {
				this.$emit("taskSubmit", nextSetp);
			},
			onSubmit(formName, nextSetp) {
				let taskId = this.$route.query.taskId;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.form.taskId = taskId;
						this.form.taskStep = nextSetp;
						this.loading = true;
						this.$ajax.post(this.getContentPath() +
							"/admin/etl/batchTask/batchProjectLayerTaskChannel/saveOrUpdate", this.form).then(
							res => {
								this.form = this.$options.data().form;
								this.nextSetp(nextSetp);
								this.loading = false;
							}).catch(err => {
							this.loading = false;
						});
					} else {
						return false;
					}
				});
			}
		}
	}
</script>
<style scoped="scoped">
</style>