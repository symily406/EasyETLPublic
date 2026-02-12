<template>
	<div class="app-container">
		<el-dialog v-dialogDrag :ref="zDialogRef" :top="top" class="ZDialog" :title="title" :fullscreen="isfullscreen"
			:visible.sync="visible" :append-to-body="appendToBody" :show-close="false" :show="show" :width="width"
			:close-on-click-modal="true" @close="handleClose" :class="isminimize ? 'isminimize' : ''">
			<div v-show="!isminimize" slot="title" class="medium" @dblclick="resetFull">
				<div class="lefts">
					<span>{{ title }}</span>
				</div>
				<div class="icons">
					<i title="缩小" class="iconfont icon-minus" style="font-size: 24px" @click="minimize"></i>
					<i title="放大" :class="
              isfullscreen
                ? 'iconfont icon-full-screen-exit'
                : 'iconfont icon-full-screen'
            " style="font-size: 24px" @click="IsFullscreen"></i>
					<i title="关闭" class="iconfont icon-close" style="font-size: 24px" @click="closeDialog"></i>
				</div>
			</div>
			<div v-show="isminimize" slot="title" class="horn" @dblclick="resetFull">
				<div class="lefts">
					<span>{{ title }}</span>
				</div>
				<div class="icons">
					<i title="放大" class="iconfont icon-full-screen" style="font-size: 24px" @click="minimize"></i>
				</div>
				<i title="关闭" class="iconfont icon-close" style="font-size: 24px" @click="closeDialog"></i>
			</div>
			<div v-show="!isminimize" class="dialogBody">
				<slot name="body" solt="body">
				</slot>
			</div>
			<div v-show="!isminimize" class="dialogFooter" v-if="isFooter">
				<slot name="footer" solt="footer">
				</slot>
			</div>
		</el-dialog>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				isfullscreen: false, // 全屏
				isminimize: false, // 最小化
				visible: this.show // 隐藏弹窗
			}
		},
		props: {
			show: {
				type: Boolean,
				default: false
			},
			title: {
				type: String,
				default: ''
			},
			zDialogRef: {
				type: String,
				default: 'zDialog'
			},
			isFooter: {
				type: Boolean,
				default: false
			},
			top:{
				type:String,
				default:"1vh"
			},
			appendToBody:{
				type:Boolean,
				default:false
			}
		},
		methods: {
			// 最小化
			minimize() {
				console.log("minimize");
				this.isminimize = !this.isminimize;
				if (this.isfullscreen) this.isfullscreen = !this.isfullscreen;
			},
			// 关闭弹窗
			closeDialog() {
				this.$emit('update:show', false)
				this.$emit('update:closeOnClickModal', false)
			},
			// 全屏
			IsFullscreen() {
				this.isfullscreen = !this.isfullscreen;
				if (this.isfullscreen) this.$emit("isfullscreen");
			},
			resetFull() {
				if (this.isfullscreen === true && this.isminimize === false) {
					this.minimize();
				}
				if (this.isfullscreen === false && this.isminimize === false) {
					this.IsFullscreen();
				}
				if (this.isfullscreen === false && this.isminimize === true) {
					this.minimize();
				}
			},
			handleClose() {
				this.$emit('update:show', false)
			}
		},
		watch: {
			visible(val) {
				if (val) {
					const el = this.$refs[this.zDialogRef].$el.querySelector(".el-dialog");
					el.style.left = 0;
					el.style.top = 0;
				}
			},
			show: {
				immediate: true,
				handler(show) {
					this.visible = this.show
				}
			}
		},
		directives: {
			dialogDrag: {
				bind(el, binding, vnode, oldVnode) {
					const dialogHeaderEl = el.querySelector(".el-dialog__header");
					const dragDom = el.querySelector(".el-dialog");
					dialogHeaderEl.style.cursor = "move";
					// 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
					const sty =
						dragDom.currentStyle || window.getComputedStyle(dragDom, null);
					// const fixedX =
					// const fixedY =
					dialogHeaderEl.onmousedown = e => {
						// 判断当前是否为全屏状态
						const path =
							event.path || (event.composedPath && event.composedPath());
						const isFull = path.find(s => {
							if (s.className === undefined) {
								return false;
							} else {
								return s.className.indexOf("is-fullscreen") > -1;
							}
						});
						if (isFull !== undefined) {
							return;
						}
						const isMinix = path.find(s => {
							if (s.className === undefined) {
								return false;
							} else {
								return s.className.indexOf("isminimize") > -1;
							}
						});
						if (isMinix !== undefined) {
							return;
						}
						// 鼠标按下，计算当前元素距离可视区的距离
						const disX = e.clientX - dialogHeaderEl.offsetLeft;
						const disY = e.clientY - dialogHeaderEl.offsetTop;

						// 获取到的值带px 正则匹配替换
						let styL, styT;

						// 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
						if (sty.left.includes("%")) {
							styL = +document.body.clientWidth * (+sty.left.replace(/\%/g, "") / 100);
							styT = +document.body.clientHeight * (+sty.top.replace(/\%/g, "") / 100);
						} else {
							styL = +sty.left.replace("px", "");
							styT = +sty.top.replace("px", "");
						}

						document.onmousemove = function(e) {
							// 通过事件委托，计算移动的距离
							const l = e.clientX - disX;
							const t = e.clientY - disY;
							// 移动当前元素
							dragDom.style.left = `${l + styL}px`;
							dragDom.style.top = `${t + styT}px`;
							// const dom = e.path.find(s => s.querySelector('.el-dialog')).children[0]
							//
							// if (dom.offsetTop < 0) {
							//   dragDom.style.top = `0px`
							// }
							// if (dom.offsetLeft < 0) {
							//   dragDom.style.left = `0px`
							// }

							// 将此时的位置传出去
							// binding.value({x:e.pageX,y:e.pageY})
						};

						document.onmouseup = function(e) {
							const dragDom = el.querySelector(".el-dialog");
							const offsetLeft = dragDom.offsetLeft;
							const offsetTop = dragDom.offsetTop;
							const left = Number(dragDom.style.left.replace("px", ""));
							const top = Number(dragDom.style.top.replace("px", ""));
							const windowWidth = window.innerWidth;
							const windowHeight = window.innerHeight - 50;
							const offsetRight = offsetLeft + dragDom.offsetWidth - windowWidth;
							const offsetBottom =
								offsetTop + dragDom.offsetHeight - windowHeight;
							if (offsetLeft < 0) {
								dragDom.style.left = left - offsetLeft + "px";
							}
							if (offsetTop < 0) {
								dragDom.style.top = top - offsetTop + "px";
							}
							if (offsetRight > 0) {
								dragDom.style.left = left - offsetRight + "px";
							}
							if (offsetBottom > 0) {
								dragDom.style.top = top - offsetBottom + "px";
							}
							document.onmousemove = null;
							document.onmouseup = null;
						};
					};
				}
			}
		}

	};
</script>
<style lang="scss">
	.el-dialog {
		margin-top: 10vh !important;
	}

	.no_select {
		-webkit-touch-callout: none;
		/* iOS Safari */
		-webkit-user-select: none;
		/* Chrome/Safari/Opera */
		-khtml-user-select: none;
		/* Konqueror */
		-moz-user-select: none;
		/* Firefox */
		-ms-user-select: none;
		/* Internet Explorer/Edge */
		user-select: none;
		/* Non-prefixed version, currently */
	}

	.isminimize {
		left: 20px;
		bottom: 20px;
		top: auto;
		right: auto;
		overflow: hidden;

		.el-dialog {
			margin: 0 !important;
			width: 240px !important;
			height: 43px;
			top: 0 !important;
			left: 0 !important;
		}

		.el-dialog__header {
			cursor: auto !important;

			.el-dialog__headerbtn {
				display: none;
			}
		}

		.dialogFooter {
			position: absolute;
			bottom: 0;
		}
	}

	.ZDialog {
		.is-fullscreen {
			width: 100% !important;
			left: 0 !important;
			top: 0 !important;
			margin-top: 0 !important;
			overflow: hidden;
			position: relative;

			.el-dialog__header {
				cursor: auto !important;
			}

			.el-dialog__body {
				height: 100%;
				padding: 0px !important;

				.dialogBody {
					height: 100% !important;
					max-height: none !important;
					padding-bottom: 120px !important;
				}
			}

			.dialogFooter {
				position: absolute;
				bottom: 0;
				width: 100%;
				background: #fff;
			}
		}

		.el-dialog {
			.el-dialog__header {
				width: 100%;
				padding: 10px 10px 10px !important;
				display: flex;
				border-bottom: 1px solid #ccc;
				@extend .no_select;
				cursor: auto;

				.medium {
					width: 100%;
					height: 100%;
					display: flex;

					div {
						flex: 1;
					}

					.lefts {
						margin-top: 3px;

						span {
							text-align: left;
							font-size: 16px;
							color: #606266;
						}
					}

					.icons {
						display: flex;
						justify-content: flex-end;

						i {
							color: #5f6368;
							font-size: 18px !important;
							display: block;
							padding: 3px;
						}

						i:hover {
							background: #dcdfe6;
							cursor: pointer;
						}

						.el-icon-close:hover {
							background: #f00;
							color: #fff;
						}
					}
				}

				.horn {
					width: 100%;
					height: 100%;
					display: flex;
					justify-content: space-between;

					div {
						i {
							color: #5f6368;
							font-size: 20px !important;
						}
					}

					.lefts {
						flex: 4;
						margin-top: 3px;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;

						span {
							font-size: 16px;
							color: #606266;
						}
					}

					.icons {
						display: flex;
						justify-content: flex-end;

						i {
							color: #5f6368;
							font-size: 18px !important;
							display: block;
							padding: 3px;
						}

						i:hover {
							background: #dcdfe6;
							cursor: pointer;
						}

						.el-icon-close:hover {
							background: #f00;
							color: #fff;
						}
					}

					// .centers {
					//   flex: 1;
					// }
					// .rights {
					//   flex: 1;
					// }
					i:hover {
						cursor: pointer;
						color: #000;
					}
				}

				.el-dialog__headerbtn {
					top: 0;
					font-size: 24px;
				}
			}

			.el-dialog__body {
				padding: 0px !important;

				.dialogBody {
					max-height: calc(80vh - 50px);
					// box-shadow: inset 0px -2px 10px 1px #b0b3b2;
					overflow: auto;
					padding: 20px 25px 20px;

					&::-webkit-scrollbar {
						width: 4px;
						height: 8px;
					}

					&::-webkit-scrollbar-thumb {
						background: transparent;
						border-radius: 4px;
					}

					&:hover::-webkit-scrollbar-thumb {
						background: hsla(0, 0%, 53%, 0.4);
					}

					&:hover::-webkit-scrollbar-track {
						background: hsla(0, 0%, 53%, 0.1);
					}
				}

				.dialogFooter {
					padding: 5px 15px;
					border-top: 1px solid #ccc;
					text-align: right;

					.el-button {
						padding: 7px 15px;
					}
				}
			}
		}

		.ZDialog {

			// display: flex;
			// justify-content: center;
			.el-select {
				width: 100%;
			}

			.el-date-editor {
				width: 100%;
			}
		}
	}
</style>