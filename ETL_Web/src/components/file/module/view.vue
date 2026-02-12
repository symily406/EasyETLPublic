<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" :width="dialogWidth"
		@opened="openWin" @close="closeWin" :top="dialogTop" v-loading="loading">
		<el-scrollbar wrap-class="scrollbar-wrapper" :style="{height:dialogHeight}">
			<div class="form-container">
				<el-form ref="form" :model="form" label-width="100px">
					<el-form-item label="归属文件夹" prop="folderId">
						{{form.folderId}}
					</el-form-item>
					<el-form-item label="归属文件配置" prop="configId">
						{{form.configId}}
					</el-form-item>
					<el-form-item label="MD5" prop="文件md5">
						{{form.文件md5}}
					</el-form-item>
					<el-form-item label="文件名" prop="fileName">
						{{form.fileName}}
					</el-form-item>
					<el-form-item label="保存文件夹" prop="saveFolder">
						{{form.saveFolder}}
					</el-form-item>
					<el-form-item label="文件大小" prop="fileSize">
						{{form.fileSize}}
					</el-form-item>
					<el-form-item label="保存文件名" prop="saveName">
						{{form.saveName}}
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
	import {
		dialogMixin
	} from "@/mixins/mixins";
	export default {
		name: 'fileView',
		components: {

		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				loading: false,
				dialogVisible: false,
				form: {
					//归属文件夹
					folderId: '',
					//归属文件配置
					configId: '',
					//MD5
					文件md5: '',
					//文件名
					fileName: '',
					//保存文件夹
					saveFolder: '',
					//文件大小
					fileSize: '',
					//保存文件名
					saveName: ''
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