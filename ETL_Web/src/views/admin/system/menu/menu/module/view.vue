<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" v-loading="loading">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container">
				<el-form ref="form" :model="form" label-width="100px">
					<el-form-item label="上级ID" prop="pid">
{{form.pid}}
</el-form-item>
<el-form-item label="菜单名称" prop="menuName">
{{form.menuName}}
</el-form-item>
<el-form-item label="菜单显示名称" prop="showMenuName">
{{form.showMenuName}}
</el-form-item>
<el-form-item label="标题" prop="title">
{{form.title}}
</el-form-item>
<el-form-item label="路由地址" prop="routPath">
{{form.routPath}}
</el-form-item>
<el-form-item label="页面名称" prop="pageName">
{{form.pageName}}
</el-form-item>
<el-form-item label="访问地址" prop="url">
{{form.url}}
</el-form-item>
<el-form-item label="图标" prop="icon">
{{form.icon}}
</el-form-item>
<el-form-item label="是否菜单" prop="isMenu">
{{form.isMenu}}
</el-form-item>
<el-form-item label="是否可以关闭" prop="isClose">
{{form.isClose}}
</el-form-item>
<el-form-item label="是否外部地址" prop="isExternal">
{{form.isExternal}}
</el-form-item>
<el-form-item label="是否隐藏" prop="isHidden">
{{form.isHidden}}
</el-form-item>

				</el-form>
			</div>
		</el-scrollbar>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="dialogVisible = false">关 闭</el-button>
		</span>
	</el-dialog>
</template>
<script>
	export default {
		name: 'menuView',
		components: {

		},
		props: {
			dialogTop: {
				type: String,
				default: "10px"
			},
			dialogTitle: {
				type: String,
				default: '查年'
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
				loading: false,
				dialogVisible: false,
				form: {
					//上级ID
pid:'',
//菜单名称
menuName:'',
//菜单显示名称
showMenuName:'',
//标题
title:'',
//路由地址
routPath:'',
//页面名称
pageName:'',
//访问地址
url:'',
//图标
icon:'',
//是否菜单
isMenu:'',
//是否可以关闭
isClose:'',
//是否外部地址
isExternal:'',
//是否隐藏
isHidden:''
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

			},
			closeWin() {
				this.dialogVisible = false;
			}
		}
	}
</script>
<style scoped="scoped">
</style>
