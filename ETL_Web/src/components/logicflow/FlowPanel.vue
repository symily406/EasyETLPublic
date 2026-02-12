<template>
	<div ref="flowContainer" class="container" :style="{height:height+'px'}"></div>
</template>

<script>
	import LogicFlow from "@logicflow/core";
	import {
		register
	} from '@logicflow/vue-node-registry';
	import "@logicflow/core/lib/style/index.css";
	import {
		DndPanel,
		SelectionSelect,
		Menu,
		Control,
		Highlight
	} from '@logicflow/extension';
	import '@logicflow/extension/lib/style/index.css';
	import circleNode from './CircleNode';
	import rectNode from './RectNode';
	export default {
		name: "flowPanel",
		components: {

		},
		props: {
			height: {
				type: Number,
				default: 600
			},
			textEdit: {
				type: Boolean,
				default: true
			},
			compare: {
				type: Boolean,
				default: false
			},
		},
		mixins: [],
		data() {
			return {
				lf: null
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
			LogicFlow.use(DndPanel);
			LogicFlow.use(SelectionSelect);
			LogicFlow.use(Menu);
			LogicFlow.use(Control);
			LogicFlow.use(Highlight);
			this.lf = new LogicFlow({
				container: this.$refs.flowContainer,
				grid: true,
				textEdit: this.textEdit
			});
			this.lf.extension.highlight.setMode('single');
			this.lf.extension.dndPanel.setPatternItems([{
					type: 'circleNode',
					text: '开始',
					label: '开始',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAAH6ji2bAAAABGdBTUEAALGPC/xhBQAAAnBJREFUOBGdVL1rU1EcPfdGBddmaZLiEhdx1MHZQXApraCzQ7GKLgoRBxMfcRELuihWKcXFRcEWF8HBf0DdDCKYRZpnl7p0svLe9Zzbd29eQhTbC8nv+9zf130AT63jvooOGS8Vf9Nt5zxba7sXQwODfkWpkbjTQfCGUd9gIp3uuPP8bZ946g56dYQvnBg+b1HB8VIQmMFrazKcKSvFW2dQTxJnJdQ77urmXWOMBCmXM2Rke4S7UAW+/8ywwFoewmBps2tu7mbTdp8VMOkIRAkKfrVawalJTtIliclFbaOBqa0M2xImHeVIfd/nKAfVq/LGnPss5Kh00VEdSzfwnBXPUpmykNss4lUI9C1ga+8PNrBD5YeqRY2Zz8PhjooIbfJXjowvQJBqkmEkVnktWhwu2SM7SMx7Cj0N9IC0oQXRo8xwAGzQms+xrB/nNSUWVveI48ayrFGyC2+E2C+aWrZHXvOuz+CiV6iycWe1Rd1Q6+QUG07nb5SbPrL4426d+9E1axKjY3AoRrlEeSQo2Eu0T6BWAAr6COhTcWjRaYfKG5csnvytvUr/WY4rrPMB53Uo7jZRjXaG6/CFfNMaXEu75nG47X+oepU7PKJvvzGDY1YLSKHJrK7vFUwXKkaxwhCW3u+sDFMVrIju54RYYbFKpALZAo7sB6wcKyyrd+aBMryMT2gPyD6GsQoRFkGHr14TthZni9ck0z+Pnmee460mHXbRAypKNy3nuMdrWgVKj8YVV8E7PSzp1BZ9SJnJAsXdryw/h5ctboUVi4AFiCd+lQaYMw5z3LGTBKjLQOeUF35k89f58Vv/tGh+l+PE/wG0rgfIUbZK5AAAAABJRU5ErkJggg==',
					properties: {
						width: 50,
						height: 50,
						borderWidth: 2,
						borderColor: "#1B7FFF",
						text: '开始',
						textSize: 14,
						nodeType: this.$store.state.global.flowStartNodeType
					}
				}, {
					type: 'rectNode',
					label: '数据同步',
					text: '数据同步',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAABGdJREFUWEftl01MXFUUx//33jefTAtDZ0qlTamlUsPUqWg1pdoE47crY4ILde1CV41WgUEgKQNKE9Ro3JjoQt2UfRc2DWobQUsRa6ZAkbS2IA5IoTDf7717zJuKATrMPIZZENO7mczce8753f8599w7DJtssE3Gg7tAuTLyP1OorY0/Pf9a7axVe95pd85pCXm6v6tiBGCUS4m15vNS6P7jYRJWD2JgYJAoYgIkAU0AOpNQSIXDFsFgs2fd/tdlUNW0kOSwWIlZ0ijG4AyQugRjS644iBEYEZJcg1tNqBc/KLGaVcw0UEVTkhzEQUQgQeAJQNjDCTkzvbesfHHmu7Y6/Wjj7x7NXnJjPlVsAym3GZgGDhWhYJGpWKYW7Ts+rltsuzkkg5GbexdH/Kc/9f+WbdcvvHmuasz18KhVGOIYJaUj1GHPGS/ngqWghxrGuiHdxwa61lcX1c1/kdS9MDLKORBqF1ljmgYyWwOZ1vlaZlWpFysgDq8tEv+hdauzoKcsHzhfQ5R04Ug3hOEO4yhkHmtO7Gm5oZdSKjR4otKfD8Bqm9pjpxx/O+pjViLY7bdSF1vdtkx+MwI92Bj+Osm8rxIY3KxvV1/wyGQhoA426pQCA2nAyMnMKt0JVNer7H38sGqTtnSvYUxHKKgUpNYeaZ6KRvTtTglgtDNzcd8RyPdekqQujJYH+nfWFo/j125zfSSbkjVvDVakLA9cC89dwb7YaHX/Vy8Nr16/AuhAa4J01WhoHICxD+MT4MTg4LM0EPTc/iHPUdfWq1z5063aCNimXwsMfPFiR1agpcknmkKvXJ1OfcMksKM47uz78Eg8T4a02YGGGU3jLsE1CWlVACagSA5VMkhlDmMnSv8TJmNt1AaGds6L/RMLE9fhpUuPDX1Z/+NGgAzbqkCMOKzpZi8goEsNjKIY7SxZwbBmsfoCGqlEENHrGP64siBFXdkQJatiBxnFyVQU3/ql+KdPDi8s3+yage4LSOLG+WQ6RoOOggAdbRz0zpJ/WgoOl2Xu24G2bc+aqiFj0aGW8GREd5dLYiiLnS0/99FzUxtNm2Ff1zhxZlJzPTV2cmWqlnxn3fnuAFERVHCSpm7qQgBnBfK9e3MWwlVq3D9cRHGp3V2Q1GUDzxlgf3OCFKmAjOOBGC6/vyWnzfKANU1RUhKxzy90e183o6Ap59WNWvpggEnEiPDo/Pc1PZ89M5QtQN0bva5w0UOL3OJASuEoS8zR+S5vzsZqCsgIXB2QpJEOThzCeE87JFg8EnEisufnzl0369p6hYp7tjPNenUqYrfanR7IdKfniHHCHzkeZqaKerUCB9+ZUuOKR2HMuOuQfsxzAmT6OcgAFWDpB75Mfxekg3hSu9y+xWImXcYa0wotd+h/e5JSth1pEOOeE9KAWvorxiBlCltFHBeCxXy9/9HyAlqCq68/JcYr/D7Y5ZOuJCvXRNGZneP9Z3t6XtbNKmK6MebrcKN2G1Joo8Ez2d8FyqXqplPoH6kvnzTy96ZoAAAAAElFTkSuQmCC',
					properties: {
						width: 200,
						height: 60,
						borderWidth: 2,
						borderColor: "#1B7FFF",
						label: '数据同步',
						textSize: 14,
						textAlign: 'center',
						labelAlign: 'center',
						nodeType: this.$store.state.global.flowDataxNodeType
					}
				}, {
					type: 'rectNode',
					label: '数据脚本',
					text: '数据脚本',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACQAAAAkCAYAAADhAJiYAAAAAXNSR0IArs4c6QAAA3NJREFUWEftVs9PVFcYPd+98+bNAIkUFaGjddKCLkaqNbbWhtqFTSHGGPEHIlHRuOnWhbropv9BuzDGXZuCDQEaTYxNmqY7SY1NJZWREYoS+WGxxqAOmWHmvXu/5j4EJ03DvCHTxJiZt5l57/veOfecc+83hFfsQ68YH5QI5XOkpFBJoXwK5Hv+emSImWlL14h27DAUKTAkLK1BgqAUYWV6GtdPfrCsxRbcFL38J1coG6wJOqebiUE8f4M0g4hhu1ncaq8vCKOg4rd7H3KIXTARPGwmENh8MTQW42EIGbICAiwySBx8xzeO78KWq3dHEumyeiLfLYsEO3etrdxWRc/yBdpT10/RQs2GvgcsGJAMaBJLtpo6ky7HcnCv5X9Q6NjFHx93fr57dX3vBAfZWGJsWpqUpTO4fbiOfprmTU01FPezeN8KHf5heDbuoPxO20b6ZoxDX/fH01lZCUHlSFkpSFawoCB1GazUJAZObPPevaN7nM82r6trqaR7RSX01dhM9MJvyTFhtrnM4rMy+9r55uie/wJp7vr10ZSMVCvJUMQYPhT1vXDfhQb4vc77rIIhaChASC8jgIaXFwF4Lpo9J1xIl6AtiarMc/QfifnG8V3Y0jM4fbm1oabtyuhHA7D77ayAQ3qeAOWQIUALwHZSOLDpjdovY9XTPX9zXWs1jRbVsv19Q8kRJ1QhiHG77eWu2Xnpd34+mwSEhUh5OHutfau9ALy9O8GzVIkzTTV1J4qdIQMS65lgR7sIvDiLQgxQ6jHCVgCksnADK/AMDGEF4FAYLBnBjEC8PeLbCd+F398YXPPthHtuUkdOE2VejA1jWe7Wn/9tMmXOKWZC41v2jo7a2eGP16+fKaple3sHZwQ/Gr3S+un7jd8N8F/hWoQ4642N3ENSsoa5Iukp/NzRSJ9cGs6catqw5fgqShSVUGvfH+NDasU6IoFo4OnQ1f3vxpYC2N51i5NVa+CmgC8+fHNVx1p6UlRC5mUNfSOstQ1JBAUF4UookQGJIDQb6xS0ZARYwpUM6QqQNYc7+/xPfN8ZWlhdrDvBEhXQQkMtzrOXWTJHQIAJWZpDcC6N+NGGgjAKKs6VfGfXzclkcGUkI6X338gbuqQgSaIq8HTgl32bt/qx6N81yya0HDA/PSVC+VQqKVRSKJ8C+Z6XMpRPoX8AK2hFNMuKtmQAAAAASUVORK5CYII=',
					properties: {
						width: 200,
						height: 60,
						borderWidth: 2,
						borderColor: "#1B7FFF",
						label: '数据脚本',
						textSize: 14,
						textAlign: 'center',
						labelAlign: 'center',
						nodeType: this.$store.state.global.flowSqlNodeType
					}
				},
				{
					type: 'circleNode',
					text: '结束',
					label: '结束',
					icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAAH6ji2bAAAABGdBTUEAALGPC/xhBQAAA1BJREFUOBFtVE1IVUEYPXOf+tq40Y3vPcmFIdSjIorWoRG0ERWUgnb5FwVhYQSl72oUoZAboxKNFtWiwKRN0M+jpfSzqJAQclHo001tKkjl3emc8V69igP3znzfnO/M9zcDcKT67azmjYWTwl9Vn7Vumeqzj1DVb6cleQY4oAVnIOPb+mKAGxQmKI5CWNJ2aLPatxWa3aB9K7/fB+/Z0jUF6TmMlFLQqrkECWQzOZxYGjTlOl8eeKaIY5yHnFn486xBustDjWT6dG7pmjHOJd+33t0iitTPkK6tEvjxq4h2MozQ6WFSX/LkDUGfFwfhEZj1Auz/U4pyAi5Sznd7uKzznXeVHlI/Aywmk6j7fsUsEuCGADrWARXXwjxWQsUbIupDHJI7kF5dRktg0eN81IbiZXiTESic50iwS+t1oJgL83jAiBupLDCQqwziaWSoAFSeIR3P5Xv5az00wyIn35QRYTwdSYbz8pH8fxUUAtxnFvYmEmgI0wYXUXcCCSpeEVpXlsRhBnCEATxWylL9+EKCAYhe1NGstUa6356kS9NVvt3DU2fd+Wtbm/+lSbylJqsqkSm9CRhvoJVlvKPvF1RKY/FcPn5j4UfIMLn8D4UYb54BNsilTDXKnF4CfTobA0FpoW/LSp306wkXM+XaOJhZaFkcNM82ASNAWMrhrUbRfmyeI1FvRBTpN06WKxa9BK0o2E4Pd3zfBBEwPsv9sQBnmLVbLEIZ/Xe9LYwJu/Er17W6HYVBc7vmuk0xUQ+pqxdom5Fnp55SiytXLPYoMXNM4u4SNSCFWnrVIzKG3EGyMXo6n/BQOe+bX3FClY4PwydVhthOZ9NnS+ntiLh0fxtlUJHAuGaFoVmttpVMeum0p3WEXbcll94l1wM/gZ0Ccczop77VvN2I7TlsZCsuXf1WHvWEhjO8DPtyOVg2/mvK9QqboEth+7pD6NUQC1HN/TwvydGBARi9MZSzLE4b8Ru3XhX2PBxf8E1er2A6516o0w4sIA+lwURhAON82Kwe2iDAC1Watq4XHaGQ7skLcFOtI5lDxuM2gZe6WFIotPAhbaeYlU4to5cuarF1QrcZ/lwrLaCJl66JBocYZnrNlvm2+MBCTmUymPrYZVbjdlr/BxlMjmNmNI3SAAAAAElFTkSuQmCC',
					properties: {
						width: 50,
						height: 50,
						borderWidth: 2,
						borderColor: "#1B7FFF",
						text: '结束',
						textSize: 14,
						nodeType: this.$store.state.global.flowEndNodeType
					}
				}
			]);
			let nodes = [{
				type: 'circleNode',
				component: circleNode,
			}, {
				type: 'rectNode',
				component: rectNode,
			}];
			nodes.forEach(item => {
				register(item,
					this.lf
				);
			});
			this.lf.setTheme({
				polyline: {
					stroke: "#1B7FFF",
					strokeWidth: 2,
				},
			});
			let that = this;
			this.lf.extension.menu.setMenuConfig({
				nodeMenu: [{
					text: "编辑",
					callback(node) {
						that.dbClickCallBack(node);
					},
				}, {
					text: "复制",
					callback: (node) => {
						that.lf.graphModel.cloneNode(node.id);
					},
				}, {
					text: "删除",
					callback(node) {
						that.lf.deleteNode(node.id);
						that.$emit("nodeParamsCallBack");
					},
				}],
				edgeMenu: [{
					text: "删除",
					callback(node) {
						that.lf.deleteEdge(node.id);
					}
				}],
				graphMenu: [], // 覆盖默认的边右键菜单，与false表现一样
			});

			this.lf.on("node:dbclick", (data) => {
				this.dbClickCallBack(data.data);
			});
			this.lf.render();
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
		watch: {

		},
		methods: {
			updateNodeProperties(nodeId, params) {
				this.lf.setProperties(nodeId, params);
			},
			getNodeProperties(nodeId) {
				return this.lf.setProperties(nodeId);
			},
			dbClickCallBack(node) {
				this.$emit("dbClickCallBack", node);
			},
			getFlowNodes() {
				let flowData = this.lf.getGraphData();
				let nodes = flowData.nodes;
				let edges = flowData.edges;
				let result = this.flowNodeOrder(nodes, edges);
				if (!result) {
					return;
				}
				let indexNodes = [];
				result.forEach(item => {
					let node = nodes.filter(v => v.id == item.id)[0];
					if (node) {
						indexNodes.push({
							"id": node.id,
							"type": node.type,
							"x": node.x,
							"y": node.y,
							"properties": node.properties,
							"text": node.text,
							"index": item.index
						});
					}
				});
				flowData["nodes"]=indexNodes;
				return flowData;
				//this.$emit("submitFlowCallBack", flowData);
			},
			setMenu(){
				if(this.compare){
					this.lf.extension.menu.setMenuConfig({
						nodeMenu: [],
						edgeMenu: [],
					});
				}
			},
			flowNodeOrder(nodes, edges) {
				const adjList = [];
				const inDegree = [];
				const result = [];
				// 初始化邻接表和入度数组
				nodes.forEach(node => {
					adjList[node.id] = [];
					inDegree[node.id] = 0;
				});
				edges.forEach(edge => {
					adjList[edge.sourceNodeId].push(edge.targetNodeId);
					inDegree[edge.targetNodeId] = (inDegree[edge.targetNodeId] || 0) + 1;
				});
				// 创建一个队列，并将所有入度为0的节点加入队列
				const queue = [];
				for (const node in inDegree) {
					if (inDegree[node] === 0) {
						queue.push({
							node,
							index: result.length + 1
						}); // 添加序号
					}
				}
				// 拓扑排序
				while (queue.length > 0) {
					const {
						node,
						index
					} = queue.shift();
					result.push({
						id: node,
						index
					});
					adjList[node].forEach(neighbor => {
						inDegree[neighbor]--;
						if (inDegree[neighbor] === 0) {
							queue.push({
								node: neighbor,
								index: result.length + 1
							}); // 添加序号
						}
					});
				}
				if (result.length !== nodes.length) {
					this.$message.error('节点中出现循环!');
					return null;
				}
				return result;
			},
			render(data) {
				this.lf.render(data);
			}
		}
	}
</script>
<style scoped="scoped">
	.container {
		width: 100%;

		text {
			display: none;
		}
	}
</style>