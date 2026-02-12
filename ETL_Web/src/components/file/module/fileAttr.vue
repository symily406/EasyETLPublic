<template>
	<el-drawer title="属性" :visible.sync="drawer" :direction="direction">
		<div class="drawer-panel">
			<div class="image-panel">
				<el-image :src="fileInfo.src">
				</el-image>
				<div class="line-padding" v-if="fileInfo.sourceType!=1">{{fileInfo.fileName}}</div>
				<div class="line-padding" v-if="fileInfo.sourceType!=1">{{fileInfo.createTime}}</div>
			</div>

			<div class="line-padding" v-if="fileInfo.sourceType==1">名称:{{fileInfo.fileName}}</div>
			<div class="line-padding" v-if="fileInfo.sourceType!=0">大小:{{fileInfo.fileSize|fileSize}}</div>
			<div class="line-padding" v-if="fileInfo.sourceType!=0">md5:{{fileInfo.md5}}</div>
			<div class="line-padding" v-if="fileInfo.sourceType!=0">创建时间:{{fileInfo.createTime}}</div>
			<div class="line-padding" v-if="fileInfo.sourceType!=0">
				<div class="file-path-panel">
					链接地址:{{fileInfo.filePath}}
				</div>
				<i title="复制地址" class="el-icon-document-copy file-path-copy-btn" @click="onCopy(fileInfo.filePath)"></i>
			</div>
		</div>
	</el-drawer>
</template>

<script>
	export default {
		data() {
			return {
				drawer: false,
				direction: 'rtl',
				fileInfo: {
					src: '',
					fileName: '',
					fileSize: '',
					createTime: '',
					sourceType: 0,
					filePath: ''
				}
			};
		},
		methods: {
			onCopy(txt) {
				this.$copyText(txt).then(() => {
					this.$message.success({ message: "已成功复制到剪切板" })
				}).catch(() => {
					this.$message.error({ message: "复制失败" })
				});
			}
		}
	};
</script>

<style scoped="scoped" lang="scss">
	.drawer-panel {
		padding: 5px;
	}

	.image-panel {
		text-align: center;
	}

	.file-path-panel {
		width: 90%;
		float: left;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.file-path-copy-btn {
		float: right;
		cursor: pointer;
		margin-right: 20px;
	}
	.line-padding{
		padding: 5px;
	}
</style>
