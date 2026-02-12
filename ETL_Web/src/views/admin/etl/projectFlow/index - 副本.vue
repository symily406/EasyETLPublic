<template>
	<div class="flow-main-container bg-white">
		<table border="0" cellspacing="0" callpadding="0" width="100%">
			<tr>
				<td>
					<div ref="flowContainer" class="container" :style="{height:flowContainerHeight+'px'}"></div>
				</td>
				<td width="40" valign="top" :style="{height:flowContainerHeight+'px'}">
					<flowSetting />
				</td>
			</tr>
		</table>

	</div>
</template>

<script>
	import LogicFlow from "@logicflow/core";
	import {
		register
	} from '@logicflow/vue-node-registry'
	import "@logicflow/core/lib/style/index.css";
	import {
		DndPanel,
		SelectionSelect,
		Menu,
		Control,
		Highlight
	} from '@logicflow/extension';
	import '@logicflow/extension/lib/style/index.css';
	import flowSetting from './module/flowSetting';
	import ProgressNode from './module/ProgressNode';
	export default {
		name: "projectFlow",
		components: {
			flowSetting
		},
		props: {

		},
		mixins: [],
		data() {
			return {
				rules: {},
				loading: false,
				//流程
				lf: null,
				flowContainerHeight: 600,
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
			this.initContentHeight();
			window.addEventListener('resize', this.initContentHeight);
			LogicFlow.use(DndPanel);
			LogicFlow.use(SelectionSelect);
			LogicFlow.use(Menu);
			LogicFlow.use(Control);
			LogicFlow.use(Highlight);
			this.lf = new LogicFlow({
				container: this.$refs.flowContainer,
				grid: true,
				style: {
					progress: {
						fill: "#FFFFFF",
						stroke: "#000000"
					},
				},
			});
			this.lf.extension.highlight.setMode('single');
			this.lf.extension.dndPanel.setPatternItems([{
					type: 'circle',
					text: '开始',
					label: '开始节点',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAAH6ji2bAAAABGdBTUEAALGPC/xhBQAAAnBJREFUOBGdVL1rU1EcPfdGBddmaZLiEhdx1MHZQXApraCzQ7GKLgoRBxMfcRELuihWKcXFRcEWF8HBf0DdDCKYRZpnl7p0svLe9Zzbd29eQhTbC8nv+9zf130AT63jvooOGS8Vf9Nt5zxba7sXQwODfkWpkbjTQfCGUd9gIp3uuPP8bZ946g56dYQvnBg+b1HB8VIQmMFrazKcKSvFW2dQTxJnJdQ77urmXWOMBCmXM2Rke4S7UAW+/8ywwFoewmBps2tu7mbTdp8VMOkIRAkKfrVawalJTtIliclFbaOBqa0M2xImHeVIfd/nKAfVq/LGnPss5Kh00VEdSzfwnBXPUpmykNss4lUI9C1ga+8PNrBD5YeqRY2Zz8PhjooIbfJXjowvQJBqkmEkVnktWhwu2SM7SMx7Cj0N9IC0oQXRo8xwAGzQms+xrB/nNSUWVveI48ayrFGyC2+E2C+aWrZHXvOuz+CiV6iycWe1Rd1Q6+QUG07nb5SbPrL4426d+9E1axKjY3AoRrlEeSQo2Eu0T6BWAAr6COhTcWjRaYfKG5csnvytvUr/WY4rrPMB53Uo7jZRjXaG6/CFfNMaXEu75nG47X+oepU7PKJvvzGDY1YLSKHJrK7vFUwXKkaxwhCW3u+sDFMVrIju54RYYbFKpALZAo7sB6wcKyyrd+aBMryMT2gPyD6GsQoRFkGHr14TthZni9ck0z+Pnmee460mHXbRAypKNy3nuMdrWgVKj8YVV8E7PSzp1BZ9SJnJAsXdryw/h5ctboUVi4AFiCd+lQaYMw5z3LGTBKjLQOeUF35k89f58Vv/tGh+l+PE/wG0rgfIUbZK5AAAAABJRU5ErkJggg==',
				},
				{
					type: 'progress',
					label: '数据同步',
					text: '数据同步',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAABGdJREFUWEftl01MXFUUx//33jefTAtDZ0qlTamlUsPUqWg1pdoE47crY4ILde1CV41WgUEgKQNKE9Ro3JjoQt2UfRc2DWobQUsRa6ZAkbS2IA5IoTDf7717zJuKATrMPIZZENO7mczce8753f8599w7DJtssE3Gg7tAuTLyP1OorY0/Pf9a7axVe95pd85pCXm6v6tiBGCUS4m15vNS6P7jYRJWD2JgYJAoYgIkAU0AOpNQSIXDFsFgs2fd/tdlUNW0kOSwWIlZ0ijG4AyQugRjS644iBEYEZJcg1tNqBc/KLGaVcw0UEVTkhzEQUQgQeAJQNjDCTkzvbesfHHmu7Y6/Wjj7x7NXnJjPlVsAym3GZgGDhWhYJGpWKYW7Ts+rltsuzkkg5GbexdH/Kc/9f+WbdcvvHmuasz18KhVGOIYJaUj1GHPGS/ngqWghxrGuiHdxwa61lcX1c1/kdS9MDLKORBqF1ljmgYyWwOZ1vlaZlWpFysgDq8tEv+hdauzoKcsHzhfQ5R04Ug3hOEO4yhkHmtO7Gm5oZdSKjR4otKfD8Bqm9pjpxx/O+pjViLY7bdSF1vdtkx+MwI92Bj+Osm8rxIY3KxvV1/wyGQhoA426pQCA2nAyMnMKt0JVNer7H38sGqTtnSvYUxHKKgUpNYeaZ6KRvTtTglgtDNzcd8RyPdekqQujJYH+nfWFo/j125zfSSbkjVvDVakLA9cC89dwb7YaHX/Vy8Nr16/AuhAa4J01WhoHICxD+MT4MTg4LM0EPTc/iHPUdfWq1z5063aCNimXwsMfPFiR1agpcknmkKvXJ1OfcMksKM47uz78Eg8T4a02YGGGU3jLsE1CWlVACagSA5VMkhlDmMnSv8TJmNt1AaGds6L/RMLE9fhpUuPDX1Z/+NGgAzbqkCMOKzpZi8goEsNjKIY7SxZwbBmsfoCGqlEENHrGP64siBFXdkQJatiBxnFyVQU3/ql+KdPDi8s3+yage4LSOLG+WQ6RoOOggAdbRz0zpJ/WgoOl2Xu24G2bc+aqiFj0aGW8GREd5dLYiiLnS0/99FzUxtNm2Ff1zhxZlJzPTV2cmWqlnxn3fnuAFERVHCSpm7qQgBnBfK9e3MWwlVq3D9cRHGp3V2Q1GUDzxlgf3OCFKmAjOOBGC6/vyWnzfKANU1RUhKxzy90e183o6Ap59WNWvpggEnEiPDo/Pc1PZ89M5QtQN0bva5w0UOL3OJASuEoS8zR+S5vzsZqCsgIXB2QpJEOThzCeE87JFg8EnEisufnzl0369p6hYp7tjPNenUqYrfanR7IdKfniHHCHzkeZqaKerUCB9+ZUuOKR2HMuOuQfsxzAmT6OcgAFWDpB75Mfxekg3hSu9y+xWImXcYa0wotd+h/e5JSth1pEOOeE9KAWvorxiBlCltFHBeCxXy9/9HyAlqCq68/JcYr/D7Y5ZOuJCvXRNGZneP9Z3t6XtbNKmK6MebrcKN2G1Joo8Ez2d8FyqXqplPoH6kvnzTy96ZoAAAAAElFTkSuQmCC',
					className: 'important-node',
					properties: {
						width: 200,
						height: 100
					}
				},
				{
					type: 'rect',
					label: '数据脚本',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAAA3NJREFUWEftVs9PVFcYPd+98+bNAIkUFaGjddKCLkaqNbbWhtqFTSHGGPEHIlHRuOnWhbropv9BuzDGXZuCDQEaTYxNmqY7SY1NJZWREYoS+WGxxqAOmWHmvXu/5j4EJ03DvCHTxJiZt5l57/veOfecc+83hFfsQ68YH5QI5XOkpFBJoXwK5Hv+emSImWlL14h27DAUKTAkLK1BgqAUYWV6GtdPfrCsxRbcFL38J1coG6wJOqebiUE8f4M0g4hhu1ncaq8vCKOg4rd7H3KIXTARPGwmENh8MTQW42EIGbICAiwySBx8xzeO78KWq3dHEumyeiLfLYsEO3etrdxWRc/yBdpT10/RQs2GvgcsGJAMaBJLtpo6ky7HcnCv5X9Q6NjFHx93fr57dX3vBAfZWGJsWpqUpTO4fbiOfprmTU01FPezeN8KHf5heDbuoPxO20b6ZoxDX/fH01lZCUHlSFkpSFawoCB1GazUJAZObPPevaN7nM82r6trqaR7RSX01dhM9MJvyTFhtrnM4rMy+9r55uie/wJp7vr10ZSMVCvJUMQYPhT1vXDfhQb4vc77rIIhaChASC8jgIaXFwF4Lpo9J1xIl6AtiarMc/QfifnG8V3Y0jM4fbm1oabtyuhHA7D77ayAQ3qeAOWQIUALwHZSOLDpjdovY9XTPX9zXWs1jRbVsv19Q8kRJ1QhiHG77eWu2Xnpd34+mwSEhUh5OHutfau9ALy9O8GzVIkzTTV1J4qdIQMS65lgR7sIvDiLQgxQ6jHCVgCksnADK/AMDGEF4FAYLBnBjEC8PeLbCd+F398YXPPthHtuUkdOE2VejA1jWe7Wn/9tMmXOKWZC41v2jo7a2eGP16+fKaple3sHZwQ/Gr3S+un7jd8N8F/hWoQ4642N3ENSsoa5Iukp/NzRSJ9cGs6catqw5fgqShSVUGvfH+NDasU6IoFo4OnQ1f3vxpYC2N51i5NVa+CmgC8+fHNVx1p6UlRC5mUNfSOstQ1JBAUF4UookQGJIDQb6xS0ZARYwpUM6QqQNYc7+/xPfN8ZWlhdrDvBEhXQQkMtzrOXWTJHQIAJWZpDcC6N+NGGgjAKKs6VfGfXzclkcGUkI6X338gbuqQgSaIq8HTgl32bt/qx6N81yya0HDA/PSVC+VQqKVRSKJ8C+Z6XMpRPoX8AK2hFNMuKtmQAAAAASUVORK5CYII='
				},
				{
					type: 'circle',
					text: '结束',
					label: '结束节点',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAAH6ji2bAAAABGdBTUEAALGPC/xhBQAAA1BJREFUOBFtVE1IVUEYPXOf+tq40Y3vPcmFIdSjIorWoRG0ERWUgnb5FwVhYQSl72oUoZAboxKNFtWiwKRN0M+jpfSzqJAQclHo001tKkjl3emc8V69igP3znzfnO/M9zcDcKT67azmjYWTwl9Vn7Vumeqzj1DVb6cleQY4oAVnIOPb+mKAGxQmKI5CWNJ2aLPatxWa3aB9K7/fB+/Z0jUF6TmMlFLQqrkECWQzOZxYGjTlOl8eeKaIY5yHnFn486xBustDjWT6dG7pmjHOJd+33t0iitTPkK6tEvjxq4h2MozQ6WFSX/LkDUGfFwfhEZj1Auz/U4pyAi5Sznd7uKzznXeVHlI/Aywmk6j7fsUsEuCGADrWARXXwjxWQsUbIupDHJI7kF5dRktg0eN81IbiZXiTESic50iwS+t1oJgL83jAiBupLDCQqwziaWSoAFSeIR3P5Xv5az00wyIn35QRYTwdSYbz8pH8fxUUAtxnFvYmEmgI0wYXUXcCCSpeEVpXlsRhBnCEATxWylL9+EKCAYhe1NGstUa6356kS9NVvt3DU2fd+Wtbm/+lSbylJqsqkSm9CRhvoJVlvKPvF1RKY/FcPn5j4UfIMLn8D4UYb54BNsilTDXKnF4CfTobA0FpoW/LSp306wkXM+XaOJhZaFkcNM82ASNAWMrhrUbRfmyeI1FvRBTpN06WKxa9BK0o2E4Pd3zfBBEwPsv9sQBnmLVbLEIZ/Xe9LYwJu/Er17W6HYVBc7vmuk0xUQ+pqxdom5Fnp55SiytXLPYoMXNM4u4SNSCFWnrVIzKG3EGyMXo6n/BQOe+bX3FClY4PwydVhthOZ9NnS+ntiLh0fxtlUJHAuGaFoVmttpVMeum0p3WEXbcll94l1wM/gZ0Ccczop77VvN2I7TlsZCsuXf1WHvWEhjO8DPtyOVg2/mvK9QqboEth+7pD6NUQC1HN/TwvydGBARi9MZSzLE4b8Ru3XhX2PBxf8E1er2A6516o0w4sIA+lwURhAON82Kwe2iDAC1Watq4XHaGQ7skLcFOtI5lDxuM2gZe6WFIotPAhbaeYlU4to5cuarF1QrcZ/lwrLaCJl66JBocYZnrNlvm2+MBCTmUymPrYZVbjdlr/BxlMjmNmNI3SAAAAAElFTkSuQmCC',
				}
			]);
			this.lf.extension.menu.addMenuConfig({
				nodeMenu: [{
					text: '属性',
					callback() {

					}
				}]
			});
			register({
					type: 'progress',
					component: ProgressNode,
				},
				this.lf,
			)
			this.lf.on("node:dnd-add", (data) => {
				let that=this;
				setTimeout(function(){
					that.lf.setProperties(data.data.id,{
						progress:100
					});
					console.log("00000000000000");
				},5000);
			});
			this.lf.render();
			const node1 = this.lf.addNode({
				id: 'vue-node-1',
				type: 'progress',
				x: 80,
				y: 80,
				label:'测式测式',
				text: '测式测式',
				properties: {
					progress: 60,
					width: 200,
					height: 100
				},
			})
		},
		//更新前状态
		beforeUpdate() {

		},
		//更新完成状态
		updated() {

		},
		//销毁前状态
		beforeDestroy() {
			window.removeEventListener('resize', this.initContentHeight);
		},
		//销毁完成状态
		destroyed() {

		},
		watch: {

		},
		methods: {

			initContentHeight() {
				this.$nextTick(() => {
					this.flowContainerHeight = window.innerHeight;
				});
			},
			getContentPath() {
				return this.$app.global.etlPath;
			}
		}
	}
</script>
<style scoped="scoped">
	.container {
		width: 100%;
	}
</style>