<template>
	<el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :destroy-on-close="true" :close-on-click-modal="false"
		:width="dialogWidth" @opened="openWin" @close="closeWin" :top="dialogTop" v-loading="loading" append-to-body>
		<file ref="file" :style="{height:filePanelHeight+'px'}" @selectFile="selectFile"></file>
		<span slot="footer" class="dialog-footer">
			<el-button size="small" @click="closeWin">关 闭</el-button>
			<el-button size="small" type="primary" @click="fileSelectCallBack">选 择</el-button>
		</span>
	</el-dialog>
</template>

<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	import file from "./index";
	export default {
		name: 'fileSelect',
		components: {
			file
		},
		props: {

		},
		mixins: [dialogMixin],
		data() {
			return {
				filePanelHeight: window.innerHeight - 200,
				dialogVisible: false,
				//是否多选
				multiSelect: false,
				fileTypes: '',
				fileSelectType: 0,
				selectFiles: []
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
				let file = this.$refs["file"];
				file.openSelect = true;
				file.multiSelect = this.multiSelect;
				file.fileTypes = this.fileTypes.concat(",0");
				file.tableData = [];
				file.queryParams.parentId = '0';
				file.queryParams.parentId = '0';
				file.parentId = '0';
				file.fullPath = '0';
				file.selectFiles = [];
				file.folderPath = [];
				this.selectFiles = [];
			},
			closeWin() {
				this.multiSelect = false;
				let file = this.$refs["file"];
				file.openSelect = false;
				file.tableData = [];
				file.queryParams.parentId = '0';
				file.parentId = '0';
				file.fullPath = '0';
				file.selectFiles = [];
				file.folderPath = [];
				this.selectFiles = [];
				this.dialogVisible = false;
			},
			selectFile(selectFiles) {
				// console.log(JSON.stringify(selectFiles));
				this.selectFiles = selectFiles;
			},
			fileSelectCallBack() {
				this.$emit("fileSelectCallBack", this.fileSelectType, this.selectFiles);
				this.closeWin();
			}
		}
	}
</script>

<style>
</style>