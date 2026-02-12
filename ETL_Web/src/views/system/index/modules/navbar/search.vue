<template>
	<div>
		<span>
			<el-button type="text" @click="click"><i class="el-icon-search"></i></el-button>
		</span>
		<span>
			<el-select ref="searchSelect" v-model="refIndex" placeholder="请输入菜单名称" v-show="show" filterable remote
				reserve-keyword :remote-method="search" @blur="onBlur" @change=elSelectChange>
				<el-option v-for="item in result" :key="item.refIndex" :label="item.item.menuName"
					:value="item.refIndex">
				</el-option>
			</el-select>
		</span>
	</div>
</template>
<script>
	import Fuse from 'fuse.js'
	export default {
		name: "headerSearch",
		components: {

		},
		props: {

		},
		data() {
			return {
				refIndex: '',
				result: [],
				show: false,
				fuse: undefined,
			}
		},
		watch: {

		},
		created() {
			this.init();
		},
		methods: {
			click() {
				this.show=!this.show;
			},
			onBlur() {
				this.show = false;
			},
			search(query) {
				if (query) {
					this.result = this.fuse.search(query);
				}
			},
			elSelectChange() {
				let menu = this.result.filter(item => item.refIndex == this.refIndex)[0];
				if (menu) {
					this.routeView(menu.item);
				}
			},
			routeView(menu) {
				if (menu.isExternal) {
					this.$router.push({
						path: menu.routePath,
						query: { url: encodeURIComponent(menu.url) }
					});
				} else {
					this.$router.push(menu.routePath);
				}
			},
			init() {
				let options = {
					shouldSort: true, // 是否按分数对结果列表排序
					includeScore: true, //  是否应将分数包含在结果集中。0分表示完全匹配，1分表示完全不匹配。
					threshold: 0.6, // 匹配算法阈值。阈值为0.0需要完全匹配（字母和位置），阈值为1.0将匹配任何内容。
					/**
					 * 确定匹配与模糊位置（由位置指定）的距离。一个精确的字母匹配，即距离模糊位置很远的字符将被视为完全不匹配。
					 *  距离为0要求匹配位于指定的准确位置，距离为1000则要求完全匹配位于使用阈值0.8找到的位置的800个字符以内。
					 */
					location: 0, // 确定文本中预期找到的模式的大致位置。
					distance: 100,
					maxPatternLength: 32, // 模式的最大长度
					minMatchCharLength: 1, // 模式的最小字符长度
					keys: ["menuName", "title"]
				};

				this.fuse = new Fuse(this.$store.state.global.searchRouter, options);
			}
		}
	}
</script>
