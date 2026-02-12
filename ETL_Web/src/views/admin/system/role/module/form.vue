<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop">
		<div class="form-container" v-loading="loading">
			<el-form ref="form" :model="form" label-width="100px" :rules="rules">
				<el-form-item label="角色编码" prop="code" :rules="[{required: true, message: '请选择角色编码', trigger: 'blur'}]">
					<el-select v-model="form.code" placeholder="角色编码" size="small" maxlength="32" style="width: 100%;">
						<el-option v-for="item in dicts" :key="item.value" :label="item.name" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="角色名称" prop="name"
					:rules="[{required: true, message: '请输入角色名称', trigger: 'blur'},{min:1,max:64,message:'角色名称长度不能超过64个字符'}]">
					<el-input v-model="form.name" placeholder="请输入角色名称" size="small" maxlength="64"></el-input>
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
				<el-form-item label="权限" prop="menuIds">
					<el-col :span="24">
						<el-card class="box-card form-box-card" style="height: 500px;">
							<el-scrollbar wrap-class="scrollbar-wrapper" style="height: 500px;">
								<el-tree ref="menuTree" :data="menuTreeData" node-key="id"
									:default-expanded-keys="openFuns" :default-checked-keys="funs" show-checkbox>
								</el-tree>
							</el-scrollbar>
						</el-card>
					</el-col>
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
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
			<el-button size="small" type="primary" @click="onSubmit('form')">确 定</el-button>
		</span>
	</el-dialog>
</template>
<script>
	import { dialogMixin,dicts } from "@/mixins/mixins";
	export default {
		name: 'roleForm',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin, dicts],
		data() {
			return {
				dicts: [],
				menuTreeData: [],
				funs: [],
				openFuns: [],
				indexPages:[],
				form: {
					//主键
					rId: '',
					//角色名称
					name: '',
					code:'',
					//归属企业
					companyId: '',
					orgId: '',
					//状态
					state: '',
					//首页路径
					indexPath: '',
					menuIds: [],
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
			this.initDict("role_code", res => {
				this.dicts=res.data;
			}, error => {

			});
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
			initIndexPages(){
				this.$ajax.get(this.getContentPath() + "/admin/system/indexPage/queryList",{}).then(res => {
					this.indexPages=res.data;
				}).catch(err => {
				
				});
			},
			openWin() {
				//富文本编辑器
				this.menuTreeData = [];
				this.funs = [];
				this.initRoleTree();
				this.initIndexPages();

			},
			closeWin() {
				//富文本编辑器
				//this.$refs['formContent'].$emit("destroyTinymce");
				this.dialogVisible = false;
				this.form = this.$options.data().form;
				this.menuTreeData = [];
				this.funs = [];
			},
			initRoleTree() {
				let companyId = this.form.companyId;
				this.$ajax.get(this.getContentPath() + "/admin/system/role/queryRoleTree", {
					"companyId": companyId,
					"rId": this
						.form.rId
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
			onSubmit(formName) {
				this.form.menus = JSON.stringify(this.getTreeData());
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.loading = true;
						this.$ajax.post(this.getContentPath() + "/admin/system/role/saveOrUpdate", this.form).then(
							res => {
								this.loading = false;
								const eventType = this.form.rId == '' ? 1 : 2;
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
