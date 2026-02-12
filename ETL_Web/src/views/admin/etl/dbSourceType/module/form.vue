<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" append-to-body>
			<div class="form-container" v-loading="loading">
				<el-form ref="form" :model="form" label-width="100px" :rules="rules">
					<el-form-item label="数据源类型" prop="sourceType"
						:rules="[{required: false, message: '请选择数据源类型', trigger: 'blur'}]">
						<el-select v-model="form.sourceType" placeholder="数据源类型" clearable size="small"
							class="max-width-100-ratio">
							<el-option v-for="item in dbType" :key="item.value" :label="item.name" :value="item.value">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="名称" prop="sourceTypeCode"
						:rules="[{required: true, message: '请输入名称', trigger: 'blur'},{min:1,max:64,message:'名称长度不能超过64个字符'}]">
						<el-select v-model="form.sourceTypeCode" placeholder="数据源名称" clearable size="small"
							class="max-width-100-ratio" filterable>
							<el-option v-for="item in dbSourceTypes" :key="item.value" :label="item.name"
								:value="item.value">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="图标" prop="sourceTypeImage"
						:rules="[{required: true, message: '请选择数库图标', trigger: 'blur'},{min:1,max:1024,message:'数库图标不能超过500个字符'}]">
						<el-input v-model="form.sourceTypeImage" placeholder="请选择数库图标" size="small" maxlength="1024"
							v-show="false"></el-input>
						<div class="db-icon-lable">
							<a href="javascript:void(0)" @click="dbIconUpload">
								<el-image class="db-icon" :src="form.sourceTypeImage">
									<div slot="error" class="image-slot">
										<i class="el-icon-plus"></i>
									</div>
								</el-image>
							</a>
							<div class="db-icon-remove" v-show="dbIconRemove"><a href="javascript:void(0)"
									@click="dbIconRemoveEvent"><i class="el-icon-close"></i></a></div>
						</div>
					</el-form-item>
					<el-form-item label="驱动" prop="jdbcDrive"
						:rules="[{required: true, message: '请输入驱动', trigger: 'blur'},{min:1,max:64,message:'驱动长度不能超过64个字符'}]">
						<el-input v-model="form.jdbcDrive" placeholder="请输入驱动" size="small" maxlength="64"
							clearable></el-input>
					</el-form-item>
					<el-row>
						<el-col :span="8"><el-form-item label="catalog" prop="isCatalog"
								:rules="[{required: false, message: '是否有catalog', trigger: 'blur'}]">
								<el-switch v-model="form.isCatalog" active-color="#13ce66" inactive-color="#ff4949"
									:active-value="1" :inactive-value="0">
								</el-switch>
							</el-form-item></el-col>
						<el-col :span="8"><el-form-item label="scheam" prop="isSchema"
								:rules="[{required: false, message: '是否有scheam', trigger: 'blur'}]">
								<el-switch v-model="form.isSchema" active-color="#13ce66" inactive-color="#ff4949"
									:active-value="1" :inactive-value="0">
								</el-switch>
							</el-form-item></el-col>
						<el-col :span="8"><el-form-item label="生成建表语句" prop="enableCreateDdl"
								:rules="[{required: false, message: '是否有可以生成建表语句', trigger: 'blur'}]">
								<el-switch v-model="form.enableCreateDdl" active-color="#13ce66"
									inactive-color="#ff4949" :active-value="1" :inactive-value="0">
								</el-switch>
							</el-form-item></el-col>
					</el-row>
					<el-row>
						<el-col :span="8"><el-form-item label="是否有帐号" prop="isAccount"
								:rules="[{required: false, message: '是否有帐号', trigger: 'blur'}]">
								<el-switch v-model="form.isAccount" active-color="#13ce66" inactive-color="#ff4949"
									:active-value="1" :inactive-value="0">
								</el-switch>
							</el-form-item></el-col>
						<el-col :span="8"><el-form-item label="是否有密码" prop="isPassword"
								:rules="[{required: false, message: '是否有密码', trigger: 'blur'}]">
								<el-switch v-model="form.isPassword" active-color="#13ce66" inactive-color="#ff4949"
									:active-value="1" :inactive-value="0">
								</el-switch>
							</el-form-item></el-col>
					</el-row>
					<el-form-item label="排序" prop="showOrder"
						:rules="[{required: true, message: '请输入排序', trigger: 'blur'}]" class="el-input-number-left">
						<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1"
							:max="999999" :precision="0" controls-position="right"
							class="max-width-100-ratio"></el-input-number>
					</el-form-item>
					<el-form-item label="配置" v-show="true">
						<el-checkbox v-model="form.allowEdit" :true-label="1" :false-label="0" label="可修改">
						</el-checkbox>
						<el-checkbox v-model="form.allowDelete" :true-label="1" :false-label="0" label="可删除">
						</el-checkbox>
						<el-checkbox v-model="form.enable" :true-label="1" :false-label="0" label="启用"></el-checkbox>
					</el-form-item>
				</el-form>
			</div>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
		<fileSelect ref="fileSelect" dialogTitle="文件选择" dialogWidth="95%"
			:dialogTop="this.$store.state.global.dialogTop" @fileSelectCallBack="fileSelectCallBack"></fileSelect>
	</el-dialog>
</template>
<script>
	//富文本编辑器
	import {
		dialogMixin,
		dicts
	} from "@/mixins/mixins";
	import fileSelect from '@/components/file/fileSelect';
	export default {
		name: 'dbSourceTypeForm',
		components: {
			fileSelect
		},
		props: {

		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				rules: {},
				loading: false,
				dialogVisible: false,
				dbIconRemove: false,
				dbType: [],
				dbSourceTypes: [],
				form: {
					//主键
					sourceTypeId: '',
					sourceType: '',
					sourceTypeCode: '',
					//名称
					sourceTypeName: '',
					//图片
					sourceTypeImage: '',
					isCatalog: 0,
					isSchema: 0,
					isAccount: 0,
					isPassword: 0,
					enableCreateDdl: 0,
					//驱动
					jdbcDrive: '',
					showOrder: 1,
					allowEdit: 1,
					allowDelete: 1,
					enable: 1
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
				let that = this;
				this.initDict("DB_TYPE", function(res) {
					that.dbType = res.data;
				}, function(error) {

				});
				this.initDict("DB_SOURCE_TYPE", function(res) {
					that.dbSourceTypes = res.data;
				}, function(error) {

				});
			},
			closeWin() {
				this.dialogVisible = false;
				this.form = this.$options.data().form;
				this.dbIconRemove = false;
			},
			dbIconUpload() {
				this.$refs['fileSelect'].dialogVisible = true;
				this.$refs['fileSelect'].multiSelect = false;
				this.$refs['fileSelect'].fileTypes = "1";
				this.$refs['fileSelect'].fileSelectType = "1";
			},
			fileSelectCallBack(fileSelectType, selectFiles) {
				if (selectFiles.length == 0) {
					return;
				}
				this.form.sourceTypeImage = selectFiles[0].filePath;
				this.dbIconRemove = true;
			},
			dbIconRemoveEvent() {
				this.form.sourceTypeImage = '';
				this.dbIconRemove = false;
			},
			getContentPath() {
				return this.$app.global.systemPath;
			},
			onSubmit(formName) {
				const dbSourceType=this.dbSourceTypes.find(item=>item.value==this.form.sourceTypeCode);
				if(dbSourceType){
					this.form.sourceTypeName=dbSourceType.name;
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/etl/dbSourceType/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.sourceTypeId == '' ? 1 : 2;
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
	.db-icon-lable {
		width: 150px;
		height: 100px;
		position: relative;

		.db-icon {
			width: 100%;
			height: 100%;
			text-align: center;
			line-height: 100px;
			border: solid 1px #eff2f6;
		}

		.db-icon-remove {
			width: 10px;
			height: 10px;
			position: absolute;
			top: 10px;
			right: 10px;
			line-height: 10px;
		}
	}

</style>