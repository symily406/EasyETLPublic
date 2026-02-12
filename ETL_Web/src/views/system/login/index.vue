<template>
	<div class="login-page">
		<!-- 全局背景 -->
		<div class="global-bg"></div>

		<!-- 登录容器（左右分栏） -->
		<div class="login-container" v-loading="loading" :style="{opacity: loading ? 0.9 : 1}">
			<!-- 左侧图片区域 -->
			<div class="login-image">
				<img src="https://picsum.photos/id/48/600/800" alt="大数据科技背景" class="tech-image" loading="lazy">
				<!-- 图片叠加文字 -->
				<div class="image-overlay">
					<h2>EasyETL</h2>
					<div class="feature-tags">
						<span>数据同步</span>
						<span>数据治理</span>
						<span>元数据管理</span>
					</div>
				</div>
			</div>

			<!-- 右侧登录表单区域 -->
			<div class="login-form-wrapper">
				<div class="login-content">
					<!-- Logo -->
					<div class="login-logo">
						<i class="el-icon-database"></i>
						<span>EasyETL</span>
					</div>
					<!-- 登录表单 -->
					<el-form ref="loginForm" :model="loginForm" :rules="rules" class="form-container">
						<el-form-item label="" prop="userName" class="form-item">
							<el-input v-model="loginForm.userName" autocomplete="off" placeholder="请输入用户名"
								prefix-icon="el-icon-user" class="custom-input">
							</el-input>
						</el-form-item>

						<el-form-item label="" prop="inputPassword" class="form-item">
							<el-input v-model="loginForm.inputPassword" type="password" autocomplete="off"
								placeholder="请输入密码" prefix-icon="el-icon-lock" class="custom-input">
							</el-input>
						</el-form-item>

						<!-- 登录按钮 -->
						<el-row class="login-btn-row">
							<el-button type="primary" class="login-button" @click="onSubmit" :loading="loading">
								<i class="el-icon-loading" v-if="loading"></i>
								<span v-else>登录</span>
							</el-button>
						</el-row>
					</el-form>
				</div>
			</div>
		</div>

		<!-- 验证码组件（精致样式） -->
		<Verify captchaType="blockPuzzle" @success="verifySucced" :imgSize="{ width: '280px', height: '130px' }"
			ref="verify" :customStyle="{
	        '--verify-bg-color': 'rgba(255, 255, 255, 0.95)',
	        '--verify-border-radius': '6px',
	        '--verify-shadow': '0 4px 16px rgba(0, 122, 255, 0.15)',
	        '--verify-border': '1px solid rgba(0, 191, 255, 0.15)'
	      }"></Verify>
	</div>
</template>
<script>
	import Verify from "@/components/verifition/Verify";
	import {
		setMenuIndex,
		setToken,
		setRefreshToken,
		setName,
		setLeval,
		setRoutePath,
		setIsExternal,
		setUrl,
		setPlatformName
	} from "@/utils/localStore.js";
	export default {
		components: {
			Verify
		},
		props: {

		},
		data() {
			return {
				showCaptchaMask: false,
				rules: {},
				loading: false,
				captchaType: 'blockPuzzle',
				captchaTypes: ['blockPuzzle', 'clickWord'],
				loginForm: {
					userName: 'admin',
					password: '',
					inputPassword: '123456',
					checkCode: '',
				},
				image: '',
				code: '',
			}
		},
		beforeCreate() {
			document.querySelector("html").setAttribute("style", "background: #001529;");
			document.querySelector("body").setAttribute("style", "background: #001529;");
		},
		created() {
			let that = this;
			this.$store.commit("global/global/setTableWidth");
			if (window.innerWidth <= 1280) {
				this.$store.commit("global/global/setIsCollapse", true);
			}
			this.initCaptcha();
			document.onkeydown = function(e) { //事件对象兼容
				let e1 = e || event || window.event || arguments.callee.caller.arguments[0];
				if (e1 && e1.keyCode == 13) {
					that.onSubmit();
				}
			}
		},
		methods: {
			initCaptcha() {
				this.$nextTick(() => {
					this.$refs.verify.refresh();
				});
			},
			verifySucced(params) {
				this.$refs['loginForm'].validate((valid) => {
					if (valid) {
						let sendParams = {
							"userName": this.loginForm.userName,
							"password": this.$md5(this.loginForm.inputPassword),
							"captchaVerification": params.captchaVerification,
						}
						if (this.loading) {
							return;
						}
						this.loading = true;
						this.$store.dispatch('system/login/login', sendParams).then(res => {
							this.loading = false;
							let {
								token,
								refreshToken,
								name,
								leval,
								routePath,
								isExternal,
								url,
								platformName
							} = res.data;
							setToken(token);
							setRefreshToken(refreshToken);
							setName(name);
							setLeval(leval);
							if (routePath) {
								setRoutePath(routePath);
								setIsExternal(isExternal);
								setUrl(url);
							}
							setPlatformName(platformName);
							setMenuIndex(0);
							this.$store.commit("global/global/setPlatformName", platformName);
							this.$router.push({
								"path": "/index"
							});
						}).catch(error => {
							this.initCaptcha();
							this.loading = false;
						});
					} else {
						console.log('验证失败!');
						this.initCaptcha();
						return false;
					}
				});
			},
			onSubmit: function() {
				let that = this;
				this.$refs['loginForm'].validate((valid) => {
					if (valid) {
						this.$refs.verify.show();
					} else {
						console.log('验证失败!');
						this.initCaptcha();
						return false;
					}
				});
			}
		}
	}
</script>
<style lang="scss" scoped>
	.login-page {
		position: relative;
		width: 100%;
		height: 100vh;
		overflow: hidden;
		display: flex;
		align-items: center;
		justify-content: center;

		// 全局背景
		.global-bg {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-image:
				linear-gradient(rgba(0, 122, 255, 0.03) 1px, transparent 1px),
				linear-gradient(90deg, rgba(0, 122, 255, 0.03) 1px, transparent 1px);
			background-size: 40px 40px;
			z-index: 1;
		}

		// 登录容器（左右分栏）- 缩小尺寸
		.login-container {
			position: relative;
			z-index: 2;
			width: 85%;
			max-width: 960px;
			height: 520px;
			background: #ffffff;
			border-radius: 12px;
			box-shadow: 0 6px 30px rgba(0, 30, 60, 0.06);
			display: flex;
			overflow: hidden;
			transition: all 0.3s ease;
			border: 1px solid rgba(0, 122, 255, 0.08);

			&:hover {
				box-shadow: 0 10px 40px rgba(0, 30, 60, 0.09);
			}
		}

		// 左侧图片区域 - 缩小宽度比例
		.login-image {
			width: 40%;
			height: 100%;
			position: relative;
			overflow: hidden;

			.tech-image {
				width: 100%;
				height: 100%;
				object-fit: cover;
				transition: transform 6s ease-in-out;

				&:hover {
					transform: scale(1.03);
				}
			}

			// 图片叠加层 - 精致化文字
			.image-overlay {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				background: linear-gradient(135deg, rgba(0, 20, 40, 0.75) 0%, rgba(0, 50, 100, 0.6) 100%);
				display: flex;
				flex-direction: column;
				justify-content: center;
				padding: 0 40px;
				color: #ffffff;

				h2 {
					font-size: 24px;
					font-weight: 600;
					margin-bottom: 12px;
					line-height: 1.2;
					text-shadow: 0 1px 6px rgba(0, 0, 0, 0.15);
				}

				p {
					font-size: 15px;
					opacity: 0.85;
					margin-bottom: 25px;
					line-height: 1.5;
				}

				.feature-tags {
					display: flex;
					flex-wrap: wrap;
					gap: 8px;

					span {
						padding: 5px 12px;
						background: rgba(255, 255, 255, 0.12);
						border-radius: 20px;
						font-size: 12px;
						backdrop-filter: blur(3px);
						transition: all 0.2s ease;

						&:hover {
							background: rgba(255, 255, 255, 0.2);
							transform: translateY(-1px);
						}
					}
				}
			}
		}

		// 右侧登录表单区域 - 紧凑布局
		.login-form-wrapper {
			width: 60%;
			height: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
			padding: 30px;
		}

		.login-content {
			width: 100%;
			max-width: 340px;
		}

		// Logo样式 - 缩小尺寸
		.login-logo {
			display: flex;
			align-items: center;
			justify-content: center;
			margin-bottom: 28px;

			i {
				font-size: 30px;
				color: #1890ff;
				margin-right: 10px;
				text-shadow: 0 0 15px rgba(24, 144, 255, 0.15);
			}

			span {
				font-size: 22px;
				font-weight: 600;
				color: #1890ff;
				background: linear-gradient(90deg, #1890ff, #0056b3);
				-webkit-background-clip: text;
				background-clip: text;
				color: transparent;
			}
		}

		// 登录标题 - 精致化
		.login-title {
			font-size: 20px;
			color: #001529;
			text-align: center;
			margin-bottom: 25px;
			font-weight: 600;
			letter-spacing: 0.3px;
		}

		// 表单容器
		.form-container {
			width: 100%;
		}

		// 表单项 - 缩小间距
		.form-item {
			margin-bottom: 18px;
		}

		// 自定义输入框 - 小巧精致
		.custom-input {
			height: 42px;
			border-radius: 6px;
			border: 1px solid #e0e7ff;
			transition: all 0.2s ease;

			&:focus-within {
				border-color: #1890ff;
				box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.08);
				outline: none;
			}

			.el-input__prefix {
				color: #1890ff;
				font-size: 16px;
			}

			.el-input__inner {
				font-size: 14px;
				letter-spacing: 0.3px;
				padding: 0 12px;
			}
		}

		// 登录按钮行
		.login-btn-row {
			margin-top: 10px;
		}

		// 登录按钮 - 小巧精致
		.login-button {
			width: 100%;
			height: 42px;
			border-radius: 6px;
			font-size: 14px;
			background: linear-gradient(90deg, #1890ff, #0056b3);
			border: none;
			transition: all 0.2s ease;
			box-shadow: 0 3px 10px rgba(24, 144, 255, 0.15);

			&:hover {
				background: linear-gradient(90deg, #096dd9, #003a70);
				transform: translateY(-1px);
				box-shadow: 0 4px 12px rgba(24, 144, 255, 0.2);
			}

			&:active {
				transform: translateY(0);
				box-shadow: 0 2px 8px rgba(24, 144, 255, 0.15);
			}
		}
	}

	// 响应式设计 - 适配小屏幕
	@media (max-width: 992px) {
		.login-container {
			flex-direction: column;
			height: auto;
			max-width: 400px;
			margin: 30px auto;
		}

		.login-image {
			width: 100%;
			height: 220px;

			.image-overlay {
				padding: 0 25px;

				h2 {
					font-size: 20px;
					margin-bottom: 8px;
				}

				p {
					font-size: 14px;
					margin-bottom: 15px;
				}

				.feature-tags {
					gap: 6px;

					span {
						padding: 4px 10px;
						font-size: 11px;
					}
				}
			}
		}

		.login-form-wrapper {
			width: 100%;
			padding: 25px;
		}

		.login-logo {
			margin-bottom: 22px;

			i {
				font-size: 26px;
			}

			span {
				font-size: 19px;
			}
		}

		.login-title {
			font-size: 18px;
			margin-bottom: 20px;
		}
	}

	@media (max-width: 576px) {
		.login-container {
			width: 94%;
			margin: 20px auto;
			border-radius: 10px;
		}

		.login-image {
			height: 180px;
		}

		.login-form-wrapper {
			padding: 20px;
		}
	}
</style>