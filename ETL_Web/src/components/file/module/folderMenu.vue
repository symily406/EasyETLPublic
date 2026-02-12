<template>
	<el-collapse-transition>
		<div id="folderTreeMenu" v-if="display" class="tree_menu" :style="{...pos}">
			<ul>
				<li><el-link :underline="false" @click="createFolder" icon="el-icon-folder">新建文件夹</el-link></li>
				<li v-if="nodeData.isFolder==1"><el-link :underline="false" @click="modifyFolder" icon="el-icon-edit">重命名</el-link></li>
				<li v-if="nodeData.isFolder==1"><el-link :underline="false" @click="deleteFolder" icon="el-icon-delete">删除</el-link></li>
			</ul>
		</div>
	</el-collapse-transition>
</template>
<script>
	export default {
		name: 'folderMenu',
		components: {

		},
		props: {

		},
		data() {
			return {
				display: false,
				nodeData: null,
				pos:{}
			}
		},
		//组件实例刚被创建,el和data并未初始化
		beforeCreate() {

		},
		//组件实例创建完成,完成了data数据的初始化,el没有
		created() {
			let that=this;
			document.onclick = function(ev) {
				if (ev.target !== document.getElementById('folderTreeMenu')) {
					that.display= false
				}
			}
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
			createFolder(){
				this.$emit("createFolder",this.nodeData);
			},
			modifyFolder(){
				this.$emit("modifyFolder",this.nodeData);
			},
			deleteFolder(){
				this.$emit("deleteFolder",this.nodeData);
			}
		}
	}
</script>
<style scoped="scoped">
	.tree_menu {
		position: fixed;
		display: block;
		z-index: 20000;
		background-color: #fff;
		padding: 5px 0;
		border: 1px solid #ebeef5;
		border-radius: 4px;
		box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
	}

	.tree_menu ul {
		margin: 0;
		padding: 0;
	}

	.tree_menu ul li {
		list-style: none;
		margin: 0;
		padding: 0 15px;
		font-size: 14px;
		line-height: 30px;
		cursor: pointer;
	}

	.tree_menu ul li:hover {
		background-color: #ebeef5
	}
</style>
