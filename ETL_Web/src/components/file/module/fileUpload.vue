<template>
	<div id="global-uploader" v-show="panelShow">
		<el-card class="box-card">
			<div slot="header" class="clearfix">
				<span>传输列表</span>
				<span class="fright">
					<el-link :underline="false" type="primary" @click="panelShow = false"><i class="el-icon-close"></i>
					</el-link>
					<el-link v-show="false" :underline="false" type="primary" @click="clearToken">清空token
					</el-link>
				</span>
			</div>
			<uploader ref="uploader" :options="options" :autoStart="false" @file-added="onFileAdded"
				@file-success="onFileSuccess" @file-removed="onFileRemoved" @file-progress="onFileProgress"
				@file-error="onFileError">
				<uploader-unsupport></uploader-unsupport>
				<uploader-btn id="global-uploader-btn" :attrs="attrs" ref="uploadBtn" v-show="true">选择文件
				</uploader-btn>
				<uploader-list>
					<div class="file-panel" slot-scope="props">
						<ul class="file-list">
							<li v-for="file in props.fileList" :key="file.id">
								<uploader-file :class="'file_' + file.id" :id="'file_'+file.id" :file="file"
									:list="true">
								</uploader-file>
							</li>
						</ul>
					</div>
				</uploader-list>
			</uploader>
		</el-card>
	</div>
</template>

<script>
	import SparkMD5 from 'spark-md5';
	import {getToken} from "@/utils/localStore.js";
	export default {
		mounted() {

		},
		watch: {
			uploadFile() {
				let that = this;
				if (this.$refs.uploadBtn) {
					setTimeout(function() {
						let fileDom = document.getElementById("global-uploader-btn").querySelectorAll("input");
						if (fileDom) {
							fileDom[0].setAttribute("accept", that.fileAccept);
						}
						that.$refs.uploadBtn.$el.click();
					}, 100);
				}
			},
			fileNum(val, old) {
				this.uploadNum = val;
			}
		},
		computed: {
			uploadFile() {
				return this.$store.state.global.uploadFileIndex;
			},
			uploadNum: {
				get() { return this.$store.state.global.uploadNum },
				set(val) {
					this.$store.commit("global/global/uploadNum", val);
				}
			}
		},
		data() {
			return {
				files: [],
				fileNum: 0,
				direction: 'rtl',
				options: {
					target: process.env.VUE_APP_BASE_API + this.$app.global.systemPath +
						"/admin/system/file/fileChunk/upload",
					chunkSize: '2048000',
					maxChunkRetries: 3,
					forceChunkSize: true,
					withCredentials: false,
					testChunks: true, //是否开启服务器分片校验
					// 服务器分片校验函数，秒传及断点续传基础
					checkChunkUploadedByResponse: function(chunk, message) {
						let re = JSON.parse(message);
						if (!re.succed) {
							return false;
						}
						let objMessage = re.data;
						if (objMessage.skip) {
							return true;
						}
						return (objMessage.uploadedChunks || []).indexOf(chunk.offset + 1) >= 0
					},
					headers: {

					},
					query: (file, chunk) => {
						return {

						}
					},
				},
				attrs: {
					accept: "*.*"
				},
				panelShow: false, //选择文件后，展示上传panel
				collapse: false,
				parentId: '0',
				fileAccept: "*.*",
				fileTypes: ""
			}
		},
		created() {

		},
		methods: {
			onFileAdded(file) {
				let suffix = file.name.substring(file.name.lastIndexOf(".") + 1);
				if (!suffix) {
					this.$message({
						message: '不允许的文件格式!',
						type: 'warning'
					});
					return;
				}
				suffix = suffix.toLowerCase();
				if (this.fileAccept.indexOf(suffix) == -1) {
					this.$message({
						message: '不允许的文件格式!',
						type: 'warning'
					});
					return;
				}

				file.uploader.opts.headers[this.$store.state.global.tokenName] =getToken();
				file.uploader.opts.headers['parentId'] = this.parentId;
				this.panelShow = true;
				this.computeMD5(file);
				this.files.push(file);
				this.fileNum = this.files.length;
			},
			onFileRemoved(file) {
				let files = [];
				for (let i = 0; i < this.files.length; i++) {
					if (this.files[i].uniqueIdentifier == file.uniqueIdentifier) {
						continue;
					}
					files.push(this.files[i]);
				}
				this.files = files;
				this.fileNum = this.files.length;
				this.$ajax.get(this.getContentPath() + "/admin/system/file/fileChunk/fileRemoved", {
					"identifier": file.uniqueIdentifier
				}).then(res => {

				}).catch(err => {

				});
			},
			onFileProgress(rootFile, file, chunk) {
				console.log(`上传中 ${file.name}，chunk：${chunk.startByte / 1024 / 1024} ~ ${chunk.endByte / 1024 / 1024}`)
			},
			onFileSuccess(rootFile, file, response, chunk) {
				// let res = JSON.parse(response);
				// console.log("==上传完成" + res);
				//this.uploadRefresh = file.id;
				let that = this;
				const { dom, text } = this.statusHtml(file.id, 'merging');
				if (dom) {
					dom[0].innerHTML = text;
				}
				this.$ajax.get(this.getContentPath() + "/admin/system/file/fileChunk/merge", {
					"identifier": file.uniqueIdentifier,
					"parentId": that.parentId
				}).then(res => {
					const { dom, text } = this.statusHtml(file.id, res.data.speedPass ? 'speedPass' : 'succed');
					if (dom) {
						dom[0].innerHTML = text;
					}
					this.$emit("eventCallBack", res.data, 100);
				}).catch(err => {

				});
			},
			onFileError(rootFile, file, response, chunk) {
				let res = JSON.parse(response);
				if (res.code == 60001) {
					this.statusHtml(file.id, 'error');
					return;
				}
				this.statusHtml(file.id, 'failed');
				this.$store.dispatch('system/login/refreshToken', { "fileTypes": this.fileTypes }).then(res => {
					this.files.forEach(item => {
						item.uploader.opts.headers[this.$store.state.global.tokenName] =getToken();
						item.retry();
						this.statusHtml(file.id, 'push');
					});
				}).catch(error => {

				});
			},
			clearToken() {
				this.files.forEach(item => {
					item.uploader.opts.headers[this.$store.state.global.tokenName] = "";
				});
			},
			/**
			 * 计算md5，实现断点续传及秒传
			 * @param file
			 */
			computeMD5(file) {
				let that = this;
				let fileReader = new FileReader();
				let time = new Date().getTime();
				let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
				let currentChunk = 0;
				const chunkSize = 10 * 1024 * 1000;
				let chunks = Math.ceil(file.size / chunkSize);
				let spark = new SparkMD5.ArrayBuffer();
				// 文件状态设为"计算MD5"
				this.statusSet(file.id, 'md5', function() {
					file.pause();
					loadNext();
					fileReader.onload = (e => {
						spark.append(e.target.result);
						if (currentChunk < chunks) {
							currentChunk++;
							loadNext();
							// 实时展示MD5的计算进度
							that.$nextTick(() => {
								const { dom, text } = that.statusHtml(file.id, 'md5');
								if (dom) {
									dom[0].innerHTML = text + "-" + ((currentChunk / chunks) * 100)
										.toFixed(0) + "%";
								}
							});
						} else {
							let md5 = spark.end();
							that.computeMD5Success(md5, file);

							let uploadButtons = document.querySelector("#file_" + file.id).querySelector(
									".uploader-file-info").querySelector(".uploader-file-actions")
								.querySelectorAll("span");
							if (uploadButtons) {
								uploadButtons[1].style.display = "";
								uploadButtons[3].style.display = "";
							}
							that.statusHtml(file.id, 'push');
							// console.log(
							// 	`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`
							// );
						}
					});
					fileReader.onerror = function() {
						that.error(`文件${file.name}读取出错，请检查该文件`)
						file.cancel();
					};
				});

				function loadNext() {
					let start = currentChunk * chunkSize;
					let end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
					fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end));
				}
			},
			computeMD5Success(md5, file) {
				file.uniqueIdentifier = md5;
				file.resume();
			},
			/**
			 * 新增的自定义的状态: 'md5'、'transcoding'、'failed'
			 * @param id
			 * @param status
			 */
			statusSet(id, status, callBack) {
				let that = this;
				this.$nextTick(() => {
					setTimeout(function() {
						that.statusHtml(id, status);
						let uploadButtons = document.querySelector("#file_" + id).querySelector(
								".uploader-file-info").querySelector(".uploader-file-actions")
							.querySelectorAll("span");
						if (uploadButtons) {
							uploadButtons[1].style.display = "none";
							uploadButtons[3].style.display = "none";
						}
						callBack();
					}, 100);
				});
			},
			statusHtml(id, status) {
				let statusMap = {
					succed: {
						text: '上传成功',
						bgc: '#fff'
					},
					speedPass: {
						text: '极速秒传',
						bgc: '#fff'
					},
					md5: {
						text: '校验MD5',
						bgc: '#fff'
					},
					merging: {
						text: '合并中',
						bgc: '#e2eeff'
					},
					transcoding: {
						text: '转码中',
						bgc: '#e2eeff'
					},
					failed: {
						text: '上传失败,自动重试',
						bgc: '#ffe0e0'
					},
					push: {
						text: 'push',
						bgc: '#fff'
					},
					error: {
						text: '不允许的文件格式',
						bgc: '#fff'
					}
				};
				let text = statusMap[status].text;
				let dom = document.querySelector("#file_" + id).querySelector(
						".uploader-file-info").querySelector(".uploader-file-status")
					.querySelectorAll("span");
				if (dom) {
					dom[0].innerHTML = text;
					//dom[0].style.backgroundColor=statusMap[status].bgc;
				}
				return { dom, text };
			},
			error(msg) {
				this.$notify({
					title: '错误',
					message: msg,
					type: 'error',
					duration: 2000
				})
			},
			getContentPath() {
				return this.$app.global.systemPath;
			}
		}
	}
</script>

<style scoped lang="scss">
	#global-uploader {
		width: 700px;
		position: fixed;
		right: 0px;
		bottom: 0px;
	}

	.uploader-panle-btn {
		font-size: 14px;
		font-weight: bold;
		padding-right: 5px;
	}

	.file-panel {
		background-color: #fff;
		border: solid 1px #ccc;

		.file-list {
			position: relative;
			height: 350px;
			overflow-x: hidden;
			overflow-y: auto;
			background-color: #fff;
			list-style: none;
			margin: 0px;
			padding-inline-start: 0px;

			>li {
				background-color: #fff;
				font-size: 14px;
			}
		}
	}

	/*隐藏上传按钮 */
	#global-uploader-btn {
		position: absolute;
		clip: rect(0, 0, 0, 0);
	}
</style>
