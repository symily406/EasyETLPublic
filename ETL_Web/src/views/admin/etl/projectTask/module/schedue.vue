<template>
	<div>
		<el-drawer title="调度属性" :visible.sync="drawer" :direction="direction" :modal="true" :size="compare?'50%':'30%'"  @close="closeDrawer">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span>调度属性</span>
				</div>
				<div class="form-container tleft">
					<el-form ref="form" :model="schedueForm" label-width="120px" :rules="rules">
						<el-form-item label="调度状态" prop="taskName"
							:rules="[{required: true, message: '任务名称', trigger: 'blur'}]">
							<el-switch v-model="schedueForm.cornStatus" active-value="1" inactive-value="0"
								active-color="#13ce66" inactive-color="#ff4949" :disabled="compare">
							</el-switch>
						</el-form-item>
						<el-form-item label="生效时间" :rules="[{required: true}]">
							<el-col :span="11">
								<el-date-picker size="small" style="width: 100%;" v-model="schedueForm.startDate"
									:disabled="compare" type="date" placeholder="开时日期" format="yyyy-MM-dd"
									value-format="yyyy-MM-dd">
								</el-date-picker>
							</el-col>
							<el-col class="tcenter" :span="2">-</el-col>
							<el-col :span="11">
								<el-date-picker size="small" style="width: 100%;" v-model="schedueForm.endDate"
									:disabled="compare" type="date" placeholder="结束日期" format="yyyy-MM-dd"
									value-format="yyyy-MM-dd">
								</el-date-picker>
							</el-col>
						</el-form-item>
						<el-form-item label="调度方式" prop="scheduleType"
							:rules="[{required: true, message: '调度方式', trigger: 'blur'}]">
							<el-select @change="scheduleTypeChange" v-model="schedueForm.scheduleType"
								:disabled="compare" placeholder="调度方式" size="small" class="max-width-100-ratio">
								<el-option v-for="item in scheduleTypes" :key="item.value" :label="item.name"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="每周" prop="week" v-show="schedueForm.scheduleType=='3'"
							:rules="[{required: true, message: '每周', trigger: 'blur'}]">
							<el-select @change="cornGenerate" v-model="schedueForm.schedueDay" placeholder="每周"
								:disabled="compare" size="small" class="max-width-100-ratio">
								<el-option v-for="item in weeks" :key="item.value" :label="item.name"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item>

						<el-form-item label="每月" prop="month_day" v-show="schedueForm.scheduleType=='4'"
							:rules="[{required: true, message: '每月', trigger: 'blur'}]">
							<el-row>
								<el-col :span="10">
									<el-select @change="cornGenerate" v-model="schedueForm.schedueMonth" placeholder="月"
										:disabled="compare" size="small" class="max-width-100-ratio">
										<el-option v-for="item in months" :key="item.value" :label="item.name"
											:value="item.value">
										</el-option>
									</el-select>
								</el-col>
								<el-col :span="2" class="tcenter">&nbsp;</el-col>
								<el-col :span="10">
									<el-select @change="cornGenerate" v-model="schedueForm.schedueDay" placeholder="天"
										:disabled="compare" size="small" class="max-width-100-ratio">
										<el-option v-for="item in days" :key="item.value" :label="item.name"
											:value="item.value">
										</el-option>
									</el-select></el-col>
							</el-row>
						</el-form-item>
						<el-form-item label="每天" prop="hour_minute"
							v-show="schedueForm.scheduleType=='2'||schedueForm.scheduleType=='3'||schedueForm.scheduleType=='4'"
							:rules="[{required: true, message: '每天', trigger: 'blur'}]">
							<el-row>
								<el-col :span="10">
									<el-select @change="cornGenerate" v-model="schedueForm.schedueHour" placeholder="小时"
										:disabled="compare" size="small" class="max-width-100-ratio">
										<el-option v-for="item in hours" :key="item.value" :label="item.name"
											:value="item.value">
										</el-option>
									</el-select>
								</el-col>
								<el-col :span="2" class="tcenter">:</el-col>
								<el-col :span="10">
									<el-select @change="cornGenerate" v-model="schedueForm.schedueMinute"
										:disabled="compare" placeholder="分钟" size="small" class="max-width-100-ratio">
										<el-option v-for="item in minutes" :key="item.value" :label="item.name"
											:value="item.value">
										</el-option>
									</el-select></el-col>
							</el-row>
						</el-form-item>
						<el-form-item label="时间间隔" prop="timeInterval" v-show="schedueForm.scheduleType=='1'"
							:rules="[{required: true, message: '时间间隔', trigger: 'blur'}]">
							<el-select @change="cornGenerate" v-model="schedueForm.timeInterval" placeholder="时间间隔"
								:disabled="compare" size="small" class="max-width-100-ratio">
								<el-option v-for="item in timeIntervals" :key="item.value" :label="item.name"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item v-show="schedueForm.scheduleType=='5'" label="调度时间" :rules="[{required: true}]">
							<el-input v-model="schedueForm.corn" placeholder="调度时间" readonly="" size="small"
								:disabled="compare">
								<el-button slot="append" @click="showDialog">corn</el-button>
							</el-input>
						</el-form-item>

						<el-form-item label="失败策略" prop="failurePolicy"
							:rules="[{required: true, message: '失败策略', trigger: 'blur'}]">
							<el-select v-model="schedueForm.failurePolicy" placeholder="失败策略" size="small"
								:disabled="compare" class="max-width-100-ratio">
								<el-option v-for="item in failurePolicy" :key="item.value" :label="item.label"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item>
					</el-form>
				</div>
			</el-card>
			<div class="lazy-page">
				<el-card class="box-card" v-show="isFlow==false">
					<div slot="header" class="clearfix">
						<span>任务依懒</span>
						<span class="fright" v-if="!compare"><el-button type="text" class="button-padding-none"
								@click="openProjectTask"><i class="el-icon-circle-plus-outline"></i></el-button></span>
					</div>

					<el-table :data="tableData" class="task-table" height="300">
						<el-table-column prop="date" label="任务名称">
							<template slot-scope="scope">
								{{scope.row.taskName}}
							</template>
						</el-table-column>
						<el-table-column prop="opt" label="操作" width="100" fixed="right" align="center" v-if="!compare">
							<template slot-scope="scope">
								<el-button type="text" size="small"
									@click.native.prevent="deleteRow(scope.$index)">删除</el-button>
							</template>
						</el-table-column>
					</el-table>
					<ePage class="fright" :page="page" @handleSizeChange="handleSizeChange"
						@handleCurrentChange="handleCurrentChange" />
				</el-card>
			</div>
		</el-drawer>

		<el-dialog class="schedue-dialog" :title="dialogTitle" :visible.sync="showCron" :close-on-click-modal="false"
			:top="dialogTop" append-to-body>
			<vcrontab @hide="showCron=false" @fill="crontabFill" hideComponent="['second',','year']"
				:expression="expression"></vcrontab>
		</el-dialog>

		<projectTask ref="projectTask" :projectTaskInfo="projectTaskInfo" dialogTitle="任务列表" dialogWidth="1000px"
			dialogHeight="400px" :dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import {
		dialogMixin,
		dicts,
		indexMixin,

	} from "@/mixins/mixins";
	import ePage from "@/components/page/page";
	import vcrontab from "vcrontab";
	import projectTask from './projectTask';
	import {
		stringify
	} from "qs";
	export default {
		name: 'schedue',
		components: {
			vcrontab,
			projectTask,
			ePage
		},
		props: {
			schedueForm: {
				type: Object,
				default: []
			},
			failurePolicy: {
				type: Array,
				default: []
			},
			isFlow: {
				type: Boolean,
				default: false
			},
			projectTaskInfo: {
				type: Object,
				default: {}
			}
		},
		mixins: [dialogMixin, indexMixin, dicts],
		data() {
			return {
				rules: {},
				drawer: false,
				tabPosition: 'right',
				direction: 'rtl',
				expression: "",
				showCron: false,
				tableData: [],
				scheduleTypes: [],
				hours: [],
				minutes: [],
				weeks: [],
				days: [],
				months: [],
				timeIntervals: [],
				taskId: undefined,
				backup:false,
				compare:false,
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
			this.initDicts();
			this.cornGenerate();
			this.taskId = this.$route.query.taskId;
			this.backup = this.$route.query.backup == 'true' ? true : false;
			this.compare=this.$route.query.compare == 'true' ? true : false;
			this.initLazy();
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
			closeDrawer(){
				this.$emit("closeDrawer");
			},
			initDicts() {
				let that = this;
				this.initDict("SCHEDULE_TYPE", function(res) {
					that.scheduleTypes = res.data;
				}, function() {

				});
				this.initDict("SCHEDUE_HOURS", function(res) {
					that.hours = res.data;
				}, function() {

				});
				this.initDict("SCHEDUE_MINUTE", function(res) {
					that.minutes = res.data;
				}, function() {

				});
				this.initDict("SCHEDUE_WEEK", function(res) {
					that.weeks = res.data;
				}, function() {

				});
				this.initDict("SCHEDUE_MONTH", function(res) {
					that.months = res.data;
				}, function() {

				});
				this.initDict("SCHEDUE_DAY", function(res) {
					that.days = res.data;
				}, function() {

				});
				this.initDict("SCHEDUE_TIME_INTERVAL", function(res) {
					that.timeIntervals = res.data;
				}, function() {

				});
			},
			crontabFill(value) {
				this.schedueForm.corn = value;
			},
			showDialog() {
				this.expression = this.schedueForm.corn; //传入的 cron 表达式，可以反解析到 UI 上
				this.showCron = true;
			},
			initLazy() {
				let queryParams = {
					"taskId": this.taskId
				};
				let url = "/admin/etl/projectTaskLazy/queryPage";
				if (this.backup) {
					url = "/admin/etl/projectTaskBackup/queryProjectTaskLazyBackupPage";
				}
				this.$ajax.get(this.getContentPath() + url, this.addPageParam(
					queryParams, this.page)).then(res => {
					this.page = this.$app.extend(this.page, this.$app.setPage(res.data));
					this.tableData = res.data.records;
				}).catch(error => {
					this.loading = false;
				});
			},
			deleteRow(index) {
				const row = this.tableData[index];
				this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskLazy/deleteById", {
					"taskLazyId": row.taskLazyId
				}).then(res => {
					this.initLazy();
				}).catch(error => {

				});
			},
			eventCallBack(data) {
				let that = this;
				let form = [];
				if (data.length > 0) {
					data.forEach(item => {
						form.push({
							"taskId": this.taskId,
							"lazyTaskId": item.taskId
						})
					});
					this.$ajax.post(this.getContentPath() + "/admin/etl/projectTaskLazy/saveOrUpdate", {
							"lazy": JSON.stringify(form),
							"taskId": this.taskId
						})
						.then(res => {
							that.initLazy();
						}).catch(err => {

						});
				}
			},
			reloadPage() {
				this.initLazy();
			},
			getContentPath() {
				return this.$app.global.etlPath;
			},
			openProjectTask() {
				this.$refs['projectTask'].dialogVisible = true;
			},
			scheduleTypeChange() {
				switch (this.schedueForm.scheduleType) {
					case "1":
						this.schedueForm.timeInterval = "5";
						break;
					case "2":
						this.schedueForm.schedueHour = "00";
						this.schedueForm.schedueMinute = "15";
						break;
					case "3":
						this.schedueForm.schedueWeek = "0";
						this.schedueForm.schedueHour = "00";
						this.schedueForm.schedueMinute = "15";
						break;
					case "4":
						this.schedueForm.schedueDay = "1";
						this.schedueForm.schedueMonth = "1";
						this.schedueForm.schedueHour = "00";
						this.schedueForm.schedueMinute = "15";
						break;
					case "5":
						this.schedueForm.corn = '0 15 0 * * ?';
						break;
				}
				this.cornGenerate()
			},
			cornGenerate() {
				switch (this.schedueForm.scheduleType) {
					case "1":
						let timeIntervalCorn = "0 0/timeInterval * * * ? ";
						timeIntervalCorn = timeIntervalCorn.replace("timeInterval", parseInt(this.schedueForm
							.timeInterval));
						this.schedueForm.corn = timeIntervalCorn;
						break;
					case "2":
						let dayCorn = "0 minute hour * * ? ";
						dayCorn = dayCorn.replace("hour", parseInt(this.schedueForm.schedueHour));
						dayCorn = dayCorn.replace("minute", parseInt(this.schedueForm.schedueMinute));
						this.schedueForm.corn = dayCorn;
						break;
					case "3":
						let weekCorn = "0 minute hour 0 0 week ";
						weekCorn = weekCorn.replace("hour", parseInt(this.schedueForm.schedueHour));
						weekCorn = weekCorn.replace("minute", parseInt(this.schedueForm.schedueMinute));
						weekCorn = weekCorn.replace("week", parseInt(this.schedueForm.schedueWeek));
						this.schedueForm.corn = weekCorn;
						break;
					case "4":
						let monthCorn = "0 minute hour day month ? ";
						monthCorn = monthCorn.replace("hour", parseInt(this.schedueForm.schedueHour));
						monthCorn = monthCorn.replace("minute", parseInt(this.schedueForm.schedueMinute));
						monthCorn = monthCorn.replace("day", parseInt(this.schedueForm.schedueDay));
						monthCorn = monthCorn.replace("month", parseInt(this.schedueForm.schedueMonth));
						this.schedueForm.corn = monthCorn;
						break;
				}
			}
		}
	}
</script>
<style scoped="scoped">
	.schedue-dialog {
		.el-dialog__body {
			padding: 5px;
		}

		.el-form-item--mini.el-form-item,
		.el-form-item--small.el-form-item {
			margin-bottom: 5px;
		}

		.el-tabs__content {
			padding: 5px;
		}

		.popup-result {
			margin: 5px auto;
		}

		.el-dialog__header {
			padding: 10px 20px 10px;
		}

		.el-dialog__headerbtn {
			top: 15px;
		}
	}

	::v-deep .el-card__body,
	.el-main {
		padding: 10px;
	}

	.lazy-page {
		background-color: #F5F5F5;
		display: block;

		.page-container {
			text-align: right;
			display: block;
			margin-top: 10px;
			width: 100%;

			.el-pagination__sizes {
				display: none;
			}

			.el-pager li {
				font-size: 12px;
			}
		}
	}
</style>