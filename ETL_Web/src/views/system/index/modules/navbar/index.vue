<template>
	<div>
		<el-header>
			<div class="navbar">
				<div class="menuMode">
					<el-button type="text"><i :class="[isCollapse?'el-icon-s-unfold':'el-icon-s-fold']"
							@click="toggleCollapse"></i></el-button>
				</div>
				<div class="breadcrumb">
					<el-breadcrumb separator="/">
						<el-breadcrumb-item :to="{path:'/index'}">首页</el-breadcrumb-item>
						<template v-for="item in navs">
							<el-breadcrumb-item>{{item.menuName}}</el-breadcrumb-item>
						</template>
					</el-breadcrumb>
				</div>
				<div class="toolbar">
					<span>
						<search class="header-search"></search>
					</span>
					<span>
						<el-button type="text" @click="handleFullScreen"><i class="el-icon-rank"></i></el-button>
					</span>
					<span><i class="el-icon-s-custom"></i></span>
					<span>
						<el-dropdown>
							<span class="el-dropdown-link">
								{{name}}<i class="el-icon-arrow-down el-icon--right"></i>
							</span>
							<el-dropdown-menu slot="dropdown">
								<el-dropdown-item @click.native="openPersonalCenter">个人中心</el-dropdown-item>
								<el-dropdown-item @click.native="loginOut">退出登陆</el-dropdown-item>
							</el-dropdown-menu>
						</el-dropdown>
					</span>
				</div>
			</div>
			<div class="tagbar">
				<el-row>
					<el-col :span="23">
						<el-tabs v-model="activeTab" type="border-card" @tab-click="handleClick"
							@tab-remove="tabRemoveClick">
							<el-tab-pane :key="item.id" v-for="(item, index) in mainTabs" :label="item.menuName"
								:name="item.id" :closable="item.isClose">
								{{item.menuName}}
							</el-tab-pane>
						</el-tabs>
					</el-col>
					<el-col :span="1">
						<div class="tagbar-dropdown">
							<el-dropdown @command="handleCommand">
								<span class="el-dropdown-link">
									操作<i class="el-icon-arrow-down el-icon--right"></i>
								</span>
								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item command="1">关闭其它</el-dropdown-item>
									<el-dropdown-item command="2">关闭全部</el-dropdown-item>
								</el-dropdown-menu>
							</el-dropdown>
						</div>
					</el-col>
				</el-row>
			</div>
		</el-header>
		<personalCenter ref="personalCenter" dialogTitle="个人中心" dialogWidth="50%" dialogHeight="605px"
			:dialogTop="this.$store.state.global.dialogTop" @eventCallBack="eventCallBack" />
	</div>
</template>
<script>
	import {
		mapState
	} from 'vuex';
	import screenfull from 'screenfull';
	import search from "./search.vue";
	import personalCenter from "../../../../admin/system/staff/module/personal_center.vue";
	import {
		geTabs,
		setTabs,
		getName,
		removeToken,
		removeRefreshToken,
		removeFileAccept
	} from "@/utils/localStore.js";
	export default {
		components: {
			search,
			personalCenter
		},
		props: {

		},
		computed: {
			...mapState({
				isCollapse: state => state.global.isCollapse,
				navs: state => state.global.navs.reverse()
			}),
			mainTabs: {
				get() {
					let tabs = this.$store.state.global.tabs;
					let storeTabs = geTabs();
					if (tabs.length == 0 && storeTabs) {
						tabs = JSON.parse(storeTabs);
						this.$store.commit("global/global/updateTabs", tabs)
					}
					return tabs;
				},
				set(val) {
					setTabs(JSON.stringify(val));
					this.$store.commit("global/global/updateTabs", val)
				}
			},
			activeTab: {
				get() {
					return this.$store.state.global.tabIndex;
				},
				set(val) {
					this.$store.commit("global/global/updateActiveTab", val)
				}
			}
		},
		data() {
			return {
				name: ''
			}
		},
		created() {
			this.name = getName();
		},
		methods: {
			handleCommand(index) {
				//console.log(index);
				let tabs = [];
				switch (parseInt(index)) {
					case 1:
						this.mainTabs.forEach(item => {
							if (item.id == '0' || item.id == this.activeTab) {
								tabs.push(item);
							}
						});
						break;
					case 2:
						this.activeTab = '0';
						tabs.push(this.mainTabs[0]);
						break;
				}
				this.mainTabs = tabs;
				this.routeView(this.activeTab);
			},
			handleFullScreen() {
				if (!screenfull.enabled) {
					this.$message({
						message: '您的浏览器不支持全屏!',
						type: 'warning'
					})
					return false;
				}
				screenfull.toggle();
			},
			toggleCollapse() {
				const isCollapse = !this.isCollapse;
				this.$store.commit("global/global/setIsCollapse", isCollapse);
			},
			handleClick(tab, event) {
				this.routeView(tab.name);
			},
			tabRemoveClick(id) {
				let tabs = [];
				this.mainTabs.forEach((item, index) => {
					if (item.id != id) {
						tabs.push(item);
					}
				});

				this.mainTabs = tabs;
				let len = tabs.length;
				if (len == 0) {
					this.activeTab = '0';
				} else {
					let lastTab = tabs[len - 1];
					this.activeTab = lastTab.id;
				}
				this.routeView(this.activeTab);
			},
			loginOut() {
				this.$store.dispatch('system/login/loginOut').then(res => {
					removeToken();
					removeRefreshToken();
					removeFileAccept();
					this.$router.push({
						"path": "/login"
					});
				}).catch(error => {

				});
			},
			routeView(id) {
				let menu = this.$store.getters['global/getTab'](id);
				if (menu) {
					if (menu.isExternal) {
						this.$router.push({
							path: menu.routePath,
							query: {
								url: encodeURIComponent(menu.url)
							}
						});
					} else {
						this.$router.push(menu.routePath);
					}
				}
			},
			openPersonalCenter() {
				this.$refs['personalCenter'].dialogVisible = true;
			},
			eventCallBack(eventType) {
				this.$message({
					message: '个人信息修改成功!',
					type: 'success'
				});
			}
		}
	}
</script>
<style scoped="scoped" language="scss">
	.tagbar {
		background: #FFF;
		border: 1px solid #DCDFE6;
		box-shadow: 0 2px 4px 0 rgba(0, 0, 0, .12), 0 0 6px 0 rgba(0, 0, 0, .04);
	}

	.tagbar-dropdown {
		background-color: #F5F7FA;
		border-bottom: 1px solid #E4E7ED;
		margin: 0;
		height: 40px;
		line-height: 40px;
	}
</style>