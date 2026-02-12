<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="100px" :rules="rules">
				<el-form-item label="任务等级" prop="level" v-if="getLeval()==100"
					:rules="[{required: true, message: '请选择任务等级', trigger: 'blur'}]">
					<el-select v-model="form.level" placeholder="请选择任务等级" clearable size="small"
						class="max-width-100-ratio">
						<el-option v-for="item in taskLevals" :key="item.value" :label="item.name" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="任务名称" prop="name"
					:rules="[{required: true, message: '请输入任务名称', trigger: 'blur'},{min:1,max:64,message:'任务名称长度不能超过64个字符'}]">
					<el-input v-model="form.name" placeholder="请输入任务名称" size="small" maxlength="64"
						clearable></el-input>
				</el-form-item>
				<el-form-item label="类路径" prop="classPath"
					:rules="[{required: true, message: '请输入类路径', trigger: 'blur'},{min:1,max:128,message:'类路径长度不能超过128个字符'}]">
					<el-input v-model="form.classPath" placeholder="请输入类路径" size="small" maxlength="128"
						clearable></el-input>
				</el-form-item>
				<el-form-item label="调度时间" prop="corn" :rules="[{required: true, message: '请选择调度时间', trigger: 'blur'}]">
					<el-input v-model="form.corn" placeholder="调度时间" readonly="" size="small">
						<el-button slot="append" @click="showDialog">corn</el-button>
					</el-input>
				</el-form-item>
				<el-form-item label="参数" prop="parameter"
					:rules="[{required: false, message: '请输入参数', trigger: 'blur'},{min:1,max:128,message:'参数长度不能超过128个字符'}]">
					<el-input type="textarea" rows="5" v-model="form.parameter" placeholder="请输入参数" size="small"
						maxlength="128" clearable></el-input>
				</el-form-item>
				<el-form-item label="备注说明" prop="remarks"
					:rules="[{required: false, message: '请输入备注说明', trigger: 'blur'}]">
					<el-input type="textarea" rows="5" v-model="form.remarks" placeholder="请输入备注说明" size="small"
						maxlength="256" clearable></el-input>
				</el-form-item>
				<el-row>
					<el-col :span="8">
						<el-form-item label="随系统启动" prop="bootUp">
							<el-switch v-model="form.bootUp" active-value="1" inactive-value="0" active-color="#13ce66"
								inactive-color="#ff4949">
							</el-switch>

						</el-form-item>
					</el-col>
					<el-col :span="8"> <el-form-item label="执行节点" prop="runNode"
							:rules="[{required: true, message: '请输入执行节点', trigger: 'blur'}]">
							<el-select v-model="form.runNode" placeholder="请选择执行节点" clearable size="small"
								class="max-width-100-ratio">
								<el-option v-for="item in runNodeEnum" :key="item.value" :label="item.name"
									:value="item.value">
								</el-option>
							</el-select>
						</el-form-item></el-col>
					<el-col :span="8" v-show="form.runNode=='2'"><el-form-item label="执行节点IP" prop="runNodeIp"
							:rules="[{required: form.runNode=='2'?true:false, message: '请输入执行节点IP', trigger: 'blur'},{min:1,max:32,message:'执行节点IP长度不能超过32个字符'}]">
							<el-input v-model="form.runNodeIp" placeholder="请输入执行节点IP" size="small" maxlength="32"
								clearable></el-input>
						</el-form-item></el-col>
				</el-row>

				<el-form-item label="排序" prop="showOrder" :rules="[{required: true, message: '请输入排序', trigger: 'blur'}]"
					class="el-input-number-left">
					<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1" :max="999999"
						:precision="0" controls-position="right" class="max-width-100-ratio"></el-input-number>
				</el-form-item>
			</el-form>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
		<el-dialog class="schedue-dialog" :title="dialogTitle" :visible.sync="showCron" :close-on-click-modal="false"
			:top="dialogTop" append-to-body>
			<vcrontab @hide="showCron=false" @fill="crontabFill" hideComponent="['year']" :expression="expression">
			</vcrontab>
		</el-dialog>
	</el-dialog>
</template>
<script>
	import {
		dialogMixin,
		dicts
	} from "@/mixins/mixins";
	import {
		getLeval
	} from "@/utils/localStore";
	import vcrontab from "vcrontab";
	export default {
		name: 'taskScheduleForm',
		components: {
			vcrontab
		},
		props: {

		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				showCron: false,
				expression: undefined,
				rules: {},
				loading: false,
				dialogVisible: false,
				runNodeEnum: [],
				taskLevals: [],
				form: {
					//主键
					taskScheduleId: undefined,
					//任务名称
					name: undefined,
					//执行周期配置
					corn: undefined,
					//任务类的全路径
					classPath: undefined,
					//参数
					parameter: undefined,
					//是否随系统启动立即执行
					bootUp: "1",
					//备注说明
					remarks: undefined,
					//执行节点
					runNode: "1",
					//执行节点IP
					runNodeIp: undefined,
					//任务等级
					level: "1",
					status: "-1",
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1
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
			openWin() {
				this.loading = false;
				let that = this;
				this.initDict("TASK_SCHEDULE_RUN_NODE_ENUM", function(res) {
					that.runNodeEnum = res.data;
				}, function() {

				});
				this.initDict("TASK_SCHEDULE_LEVAL_ENUM", function(res) {
					that.taskLevals = res.data;
				}, function() {

				});
			},
			closeWin() {
				//富文本编辑器
				//this.$refs['formContent'].$emit("destroyTinymce");
				this.dialogVisible = false;
				this.form = this.$options.data().form;
			},
			showDialog() {
				this.expression = this.form.corn; //传入的 cron 表达式，可以反解析到 UI 上
				this.showCron = true;
			},
			crontabFill(value) {
				this.form.corn = value;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			onSubmit(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/taskSchedule/saveOrUpdate", this
							.form).then(res => {
							this.loading = false;
							const eventType = this.form.taskScheduleId == '' ? 1 : 2;
							this.$emit('eventCallBack', eventType);
							this.closeWin();
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