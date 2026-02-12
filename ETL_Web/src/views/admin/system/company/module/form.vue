<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop">
		<el-row v-loading="loading">
			<el-col :span="20">
				<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
					<div class="form-container">
						<el-form ref="form" :model="form" label-width="100px" :rules="rules">
							<el-form-item label="企业名称" prop="companyName"
								:rules="[{required: true, message: '请输入企业名称', trigger: 'blur'},{min:1,max:128,message:'企业名称长度不能超过128个字符'}]">
								<el-input v-model="form.companyName" placeholder="请输入企业名称" size="small" maxlength="128">
								</el-input>
							</el-form-item>
							<el-form-item label="平台名称" prop="platformName"
								:rules="[{required: true, message: '请输入平台名称', trigger: 'blur'},{min:1,max:128,message:'平台名称长度不能超过128个字符'}]">
								<el-input v-model="form.platformName" placeholder="请输入平台名称" size="small"
									maxlength="128">
								</el-input>
							</el-form-item>
							<el-form-item label="地区" required style="margin-bottom: 0px;">
								<el-col :span="8">
									<el-form-item prop="province"
										:rules="[{required: true, message: '请选择省', trigger: 'blur'}]">
										<el-select v-model="form.province" placeholder="请选择省" size="small"
											@change="initCityArea" style="width: 99%;">
											<el-option v-for="item in provinceData" :key="item.areaId"
												:label="item.areaName" :value="item.areaId">
											</el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="8">
									<el-form-item prop="city"
										:rules="[{required: true, message: '请选择市', trigger: 'blur'}]">
										<el-select v-model="form.city" placeholder="请选择市" size="small"
											@change="initCountyArea" style="width: 99%;">
											<el-option v-for="item in cityData" :key="item.areaId"
												:label="item.areaName" :value="item.areaId">
											</el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="8">
									<el-form-item prop="county"
										:rules="[{required: true, message: '请选择县', trigger: 'blur'}]">
										<el-select v-model="form.county" placeholder="请选择县" size="small"
											style="width: 99%;">
											<el-option v-for="item in countyData" :key="item.areaId"
												:label="item.areaName" :value="item.areaId">
											</el-option>
										</el-select>
									</el-form-item>
								</el-col>
							</el-form-item>
							<el-form-item label="地址" prop="address"
								:rules="[{required: true, message: '请输入地址', trigger: 'blur'},{min:1,max:128,message:'地址长度不能超过128个字符'}]">
								<el-input v-model="form.address" placeholder="请输入地址" size="small" maxlength="128">
								</el-input>
							</el-form-item>
							<el-form-item label="简介" prop="content"
								:rules="[{required: true, message: '请输入简介', trigger: 'blur'}]">
								<editor v-model="form.content" ref="formContent" @uploadImage="uploadImage"
									@uploadMedia="uploadMedia" @uploadFile="uploadFile"></editor>
							</el-form-item>
							<el-form-item label="系统首页" prop="indexPath">
								<el-select v-model="form.indexPath" placeholder="系统首页" size="small" style=
									"width:99%">
								    <el-option
								      v-for="item in indexPages"
								      :key="item.menuId"
								      :label="item.menuName"
								      :value="item.menuId">
								    </el-option>
								  </el-select>
							</el-form-item>

							<el-form-item label="排序" prop="showOrder"
								:rules="[{required: true, message: '请输入排序', trigger: 'blur'}]"
								class="el-input-number-left">
								<el-input-number v-model="form.showOrder" placeholder="请输入排序" size="small" :min="1"
									:max="999999" :precision="0" controls-position="right" class="max-width-100-ratio">
								</el-input-number>
							</el-form-item>
							<el-form-item label="配置" v-show="true">
								<el-checkbox v-model="form.allowEdit" :true-label="1" :false-label="0" label="可修改">
								</el-checkbox>
								<el-checkbox v-model="form.allowDelete" :true-label="1" :false-label="0" label="可删除">
								</el-checkbox>
								<el-checkbox v-model="form.enable" :true-label="1" :false-label="0" label="启用">
								</el-checkbox>
							</el-form-item>
						</el-form>
					</div>
				</el-scrollbar>
			</el-col>
			<el-col :span="4">
				<el-card class="box-card form-box-card" :style="{height:dialogHeight}">
					<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
						<el-tree ref="menuTree" :data="menuTreeData" node-key="id" :default-checked-keys="funs"
							:default-expanded-keys="openFuns" show-checkbox>
						</el-tree>
					</el-scrollbar>
				</el-card>
			</el-col>
		</el-row>

		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
		<fileSelect ref="fileSelect" dialogTitle="文件选择" dialogWidth="95%"
			:dialogTop="this.$store.state.global.dialogTop" @fileSelectCallBack="fileSelectCallBack"></fileSelect>
	</el-dialog>

</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	//富文本编辑器
	import editor from '@/components/Editor';
	import fileSelect from '@/components/file/fileSelect';
	export default {
		name: 'companyForm',
		components: {
			editor,
			fileSelect
		},
		props: {
			defaultProps: {
				children: 'children',
				label: 'name'
			}
		},
		mixins: [dialogMixin],
		data() {
			return {
				provinceData: [],
				province: '',
				cityData: [],
				county: '',
				countyData: [],
				city: '',
				menuTreeData: [],
				funs: [],
				openFuns: [],
				indexPages:[],
				form: {
					//主键
					companyId: '',
					//企业名称
					companyName: '',
					//平台名称
					platformName: '',
					//省
					province: '',
					//市
					city: '',
					//县
					county: '',
					//地址
					address: '',
					//简介
					content: '',
					menus: '',
					//后台首页路径
					indexPath: '',
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
			initProvinceArea() {
				this.$ajax.get(this.getContentPath() + "/admin/system/area/query", { "areaLevel": 1 }).then(res => {
					this.provinceData = res.data;
					if (this.province == this.form.province) {
						this.form.province = this.province;
					} else {
						this.form.province = null;
					}
				}).catch(err => {

				});
			},
			initCityArea(val) {
				this.$ajax.get(this.getContentPath() + "/admin/system/area/query", { "pAreaId": val }).then(res => {
					this.cityData = res.data;
				}).catch(err => {

				});
			},
			initCountyArea(val) {
				this.$ajax.get(this.getContentPath() + "/admin/system/area/query", { "pAreaId": val }).then(res => {
					this.countyData = res.data;
					if (this.city == this.form.city) {
						this.form.county = this.county;
					} else {
						this.form.county = null;
					}
				}).catch(err => {

				});
			},
			initMenuCompanyTree() {
				this.$ajax.get(this.getContentPath() + "/admin/system/company/menuCompanyTree", {
					"companyId": this.form.companyId
				}).then(res => {
					this.menuTreeData = res.data.menus;
					let funs = res.data.funs.filter(v => v.leval == 2);
					funs.forEach(item => {
						this.funs.push(item.fId);
					});
					let openFuns = res.data.funs.filter(v => v.leval == 0);
					openFuns.forEach(item => {
						this.openFuns.push(item.fId);
					});
				}).catch(err => {

				});
			},
			getTreeData() {
				let nodes = [];
				const nodeIds = this.$refs['menuTree'].getCheckedKeys().concat(this.$refs['menuTree']
					.getHalfCheckedKeys());
				nodeIds.forEach(item => {
					const node = this.$refs['menuTree'].getNode(item);
					nodes.push({
						fId: node.data.id,
						leval: node.data.leval
					})
				});
				return nodes;
			},
			initIndexPages(){
				this.$ajax.get(this.getContentPath() + "/admin/system/indexPage/queryList",{}).then(res => {
					this.indexPages=res.data;
				}).catch(err => {
				
				});
			},
			openWin() {
				//富文本编辑器
				this.$refs['formContent'].$emit("initTinymce");
				this.initProvinceArea();
				this.initCityArea(this.form.province);
				this.initCountyArea(this.form.city);
				this.initMenuCompanyTree();
				this.initIndexPages();
			},
			closeWin() {
				//富文本编辑器
				this.$refs['formContent'].$emit("destroyTinymce");
				this.form = this.$options.data().form;
				this.menuTreeData = [];
				this.funs = [];
				this.openFuns=[];
				this.dialogVisible = false;
			},
			//编辑器上传图片
			uploadImage() {
				this.$refs['fileSelect'].dialogVisible = true;
				this.$refs['fileSelect'].multiSelect = true;
				this.$refs['fileSelect'].fileTypes = "1";
				this.$refs['fileSelect'].fileSelectType = "1";
			},
			//编辑器上传多媒体
			uploadMedia() {
				this.$refs['fileSelect'].dialogVisible = true;
				this.$refs['fileSelect'].multiSelect = true;
				this.$refs['fileSelect'].fileTypes = "2,3";
				this.$refs['fileSelect'].fileSelectType = "2";
			},
			//编辑器上传文件
			uploadFile() {
				this.$refs['fileSelect'].dialogVisible = true;
				this.$refs['fileSelect'].multiSelect = true;
				this.$refs['fileSelect'].fileTypes = "4";
				this.$refs['fileSelect'].fileSelectType = "3";
			},
			//文件选择回调
			fileSelectCallBack(fileSelectType, selectFiles) {
				if (selectFiles.length == 0) {
					return;
				}
				let html = "";
				switch (parseInt(fileSelectType)) {
					case 1: //图片
						selectFiles.forEach(item => {
							html += "<p><img src=\"" + item.filePath + "\" alt=\"" + item.name + "\" width=\"50%\"/></p>";
						});
						break;
					case 2: //mp3/mp4
						selectFiles.forEach(item => {
							if (item.suffix == 'mp3') {
								html += "<p><audio controls><source src=\"" + item.filePath +
									"\" type=\"audio/mpeg\"></audio></p>"
							}
							if (item.suffix == 'mp4') {
								html += "<p>";
								html += "<video controls=\"controls\" width=\"50%\">";
								html += "<source src=\"" + item.filePath + "\" type=\"video/mp4\">";
								html += "</video>";
								html += "</p>";
							}
						});
						break;
					case 3: //附件
						selectFiles.forEach(item => {
							html += "<p><a href=\"" + item.filePath + "\" title=\"下载\">" + item.name + "</a></p>";
						});
						break;
				}
				let content = this.form.content;
				content += html;
				this.form.content = content;
				this.$refs['formContent'].setContent(content);
			},
			onSubmit(formName) {
				this.form.menus = JSON.stringify(this.getTreeData());
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/company/saveOrUpdate", this.form)
							.then(res => {
								this.loading = false;
								const eventType = this.form.companyId == '' ? 1 : 2;
								this.$emit('eventCallBack', eventType);
								this.closeWin();
							}).catch(err => {
								this.loading = false;
							});
					} else {
						return false;
					}
				});
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
