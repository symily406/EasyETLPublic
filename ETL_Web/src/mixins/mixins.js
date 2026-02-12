import {
	getLeval
} from "@/utils/localStore.js";
//=============上下分割pane=====
export const splitPane = {
	data() {
		return {
			topSize: 100,
			bottomSize: 0,
			minSize: 0,
			topHeight: 0,
			bottomHeight: 0
		}
	},
	created() {
		this.calculateTopHeight();
		this.calculateBottomHeight();
	},
	methods: {
		getBodyHeight() {
			return Math.max(
				document.body.scrollHeight,
				document.documentElement.scrollHeight,
				document.body.offsetHeight,
				document.documentElement.offsetHeight,
				document.body.clientHeight,
				document.documentElement.clientHeight
			);
		},
		calculateTopHeight() {
			this.topHeight = this.getBodyHeight() * (this.topSize / 100);
		},
		calculateBottomHeight() {
			this.bottomHeight = this.getBodyHeight() - this.topHeight;
		},
		onResize(sizes) {
			// 拖动时实时更新高度
			this.topSize = sizes[0].size;
			this.bottomSize = sizes[1].size;
			this.calculateTopHeight();
			this.calculateBottomHeight();
		},
		closeBottom() {
			this.topSize = 100;
			this.bottomSize = 0;
			this.calculateTopHeight();
			this.calculateBottomHeight();
			const element = document.getElementById('paneBottom');
			element.style.height = this.bottomSize + '%';
		},
		openBottom() {
			this.topSize = 60;
			this.bottomSize = 40;
			this.calculateTopHeight();
			this.calculateBottomHeight();
			const element = document.getElementById('paneBottom');
			element.style.height = this.bottomSize + '%';
		}
	}
};
//=============任务参数=========
export const taskForm = {
	data() {
		return {
			jobId: undefined,
			loading: false,
			runLoading: false,
			saveLoading: false,
			submitLoading: false,
			stopLoading: false,
			formatLoading: false,
			dialogVisible: false,
			isRun: 0,
			flowRunId: undefined,
			taskId: '',
			projectId: '',
			projectWorkSpaceLayerRelId: '',
			projectWorkSpaceId: '',
			workSpaceType: '',
			taskFolderId: '',
			compare: false,
			backup: false,
			evnContent: '',
			isEnableCluster:0,
			evnForm: {
				//主键
				id: '',
				//归属任务
				taskId: '',
				//归属项目
				projectId: '',
				//分层
				projectWorkSpaceLayerRelId: '',
				projectWorkSpaceId: '',
				workSpaceType: '',
				taskFolderId: '',
				evnContent: undefined,
				showOrder: 1,
				allowEdit: 1,
				allowDelete: 1,
				enable: 1
			},
			schedueForm: {
				cornStatus: "0",
				startDate: this.$moment().format('YYYY-MM-DD'),
				endDate: '2099-12-31',
				corn: '0 15 0 1/1 * ?',
				//失败结束
				failurePolicy: 0,
				scheduleType: "2",
				schedueHour: "00",
				schedueMinute: "15",
				schedueWeek: "0",
				schedueDay: '1',
				schedueMonth: '1',
				timeInterval: '5'
			},
			failurePolicy: [{
					"value": 0,
					"label": "失败结束"
				},
				{
					"value": 1,
					"label": "失败继续"
				}
			],
			isFlow: false,
			taskParameters: [],
			globParameters: [],
		}
	},
	methods: {
		queryRunnerJob(taskId, callBack) {
			this.$ajax.get(this.getContentPath() + "/admin/etl/runTaskJobLog/queryRunnerJob", {
				"taskId": this.taskId
			}).then(res => {
				if (res.data) {
					callBack(res.data.jobId, taskId);
				}
			}).catch(error => {

			});
		},
		queryRunnerFlow(taskId, callBack) {
			this.$ajax.get(this.getContentPath() + "/admin/etl/projectTaskFlowRun/queryRunnerFlow", {
				"taskId": this.taskId
			}).then(res => {
				if (res.data) {
					callBack(res.data.flowRunJobId, taskId);
				}
			}).catch(error => {

			});
		},
		openLog(taskId, taskType) {
			this.openBottom();
			this.$refs['log'].showLogContentSwitch();
			this.$refs['log'].taskId = taskId;
			this.$refs['log'].clearLog();
			this.$refs['log'].stopInterval();
			this.$refs['log'].initLogInfo = true;
			this.$refs['log'].logActiveTab = "1234567890";
			this.$refs['log'].taskType = taskType;
			this.$refs['log'].startInterval();
			this.isRun = true;
			this.runLoading = true;
		},
		queryTaskParameters(param, succCallBack, errorCallBack) {
			let url = "/admin/etl/projectTaskParameter/queryAll";
			if (param.backup) {
				url = "/admin/etl/projectTaskBackup/queryParametersBackupAll"
			}
			this.$ajax.get(this.getContentPath() + url, param).then(res => {
				let data = res.data;
				if (data) {
					let taskParameters = [];
					data.forEach(item => {
						taskParameters.push({
							taskId: item.taskId,
							projectId: item.projectId,
							projectWorkSpaceLayerRelId: item.projectWorkSpaceLayerRelId,
							projectWorkSpaceId: item.projectWorkSpaceId,
							workSpaceType: item.workSpaceType,
							taskFolderId: item.taskFolderId,
							paramKey: item.paramKey,
							paramValue: item.paramValue,
							paramType: item.paramType
						});
					});
					this.taskParameters = taskParameters;
				}
				succCallBack(res);
			}).catch(error => {
				errorCallBack(error);
			});
		},
		queryTaskSchedue(param, succCallBack, errorCallBack) {
			let url = "/admin/etl/projectTaskSchedue/getById";
			if (param.backup) {
				url = "/admin/etl/projectTaskBackup/queryProjectTaskSchedueBackupById"
			}
			this.$ajax.get(this.getContentPath() + url, param).then(res => {
				let data = res.data;
				if (data) {
					this.schedueForm.cornStatus = res.data.cornStatus.toString();
					this.schedueForm.startDate = res.data.startDate;
					this.schedueForm.endDate = res.data.endDate;
					this.schedueForm.corn = res.data.corn;
					this.schedueForm.failurePolicy = res.data.failurePolicy;
					this.schedueForm.scheduleType = res.data.scheduleType;
					this.schedueForm.schedueHour = res.data.schedueHour;
					this.schedueForm.schedueMinute = res.data.schedueMinute;
					this.schedueForm.schedueWeek = res.data.schedueWeek;
					this.schedueForm.schedueDay = res.data.schedueDay;
					this.schedueForm.schedueMonth = res.data.schedueMonth;
					this.schedueForm.timeInterval = res.data.timeInterval;
				}
				succCallBack(res);
			}).catch(error => {
				errorCallBack(error);
			});
		},
		queryTaskEvn(param, succCallBack, errorCallBack) {
			let url = "/admin/etl/projectTaskEvn/getById";
			if (param.backup) {
				url = "/admin/etl/projectTaskBackup/queryTaskEvnById"
			}
			this.$ajax.get(this.getContentPath() + url, param).then(res => {
				if (res.data) {
					this.evnContent = res.data.evnContent;
					this.evnForm.evnContent = res.data.evnContent;
				}
				succCallBack(res);
			}).catch(error => {
				errorCallBack(error);
			});
		}
	}
}

//=============字典=============
export const dicts = {
	methods: {
		initDict(dictCode, succCallBack, errorCallBack) {
			this.$ajax.get(this.getContentPath() +
				"/admin/system/dict/dictValues/queryDictValues", {
					"dictCode": dictCode
				}).then(res => {
				succCallBack(res);
			}).catch(error => {
				errorCallBack(error);
			});
		}
	}
}
//=============首页=============
export const indexMixin = {
	data() {
		return {
			loading: false,
			tableData: [],
			tableHeight: 400,
			page: {
				pageSizes: [1, 10, 20, 50],
				totalPage: 2,
				pageNumber: 1,
				pageSize: 20,
				total: 1
			},
			selectData: []
		}
	},
	methods: {
		checkPermission(role) {
			return this.$permission.checkPermission(role);
		},
		addPageParam(queryParams, page) {
			return this.$app.addPageParam(queryParams, page);
		},
		handleSelectionChange(data) {
			this.selectData = data;
		},
		handleSizeChange(pageSize) {
			this.page.pageSize = pageSize;
			this.page.pageNumber = 1;
			this.page.totalPage = 1;
			this.reloadPage();
		},
		handleCurrentChange(pageNumber) {
			this.page.pageNumber = pageNumber;
			this.reloadPage();
		},
		getLeval() {
			return getLeval();
		}
	}
};
//================header============
export const headerMixin = {
	props: {
		queryParams: {
			type: Object,
			required: true
		}
	},
	data() {
		return {

		}
	},
	methods: {
		searchPage() {
			this.$emit('searchPage');
		},
		refreshPage() {
			this.$emit('refreshPage');
		},
		add() {
			this.$emit("add");
		},
		batchDelete() {
			this.$emit("batchDelete");
		},
		checkPermission(role) {
			return this.$permission.checkPermission(role);
		},
		getLeval() {
			return getLeval();
		}
	}
};
//================弹窗===============
export const dialogMixin = {
	props: {
		dialogTop: {
			type: String,
			default: "10px"
		},
		dialogTitle: {
			type: String,
			default: '添加'
		},
		dialogWidth: {
			type: String,
			default: '50%'
		},
		dialogHeight: {
			type: String,
			default: window.innerHeight - 160 + "px"
		}
	},
	data() {
		return {
			rules: {},
			loading: false,
			dialogVisible: false
		}
	},
	methods: {
		getLeval() {
			return getLeval();
		}
	}
};