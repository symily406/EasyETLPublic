<template>
	<div class="circle-node"
		:style="{fontWeight:fontWeight,fontSize:textSize+'px',color:textColer,lineHeight:(height-2)+'px',textAlign:'center', width: width + 'px',height: height + 'px',  border: borderWidth + 'px solid ' + borderColor,  background: backgroundColor}">
		{{text}}
	</div>
</template>

<script>
	import {
		EventType
	} from '@logicflow/core'
	import {
		vueNodesMap
	} from '@logicflow/vue-node-registry'
	export default {
		name: 'CircleNode',
		inject: ['getNode', 'getGraph'],
		data() {
			return {
				width: 50,
				height: 50,
				borderWidth: 1,
				borderColor: "#000",
				backgroundColor: "#fff",
				text: '',
				textColer:'#000',
				textSize:12,
				fontWeight:'none'
			}
		},
		mounted() {
			const node = this.getNode();
			const graph = this.getGraph();
			this.initProperties(node.properties);
			graph.eventCenter.on(EventType.NODE_PROPERTIES_CHANGE, eventData => {
				const keys = eventData.keys;
				const content = vueNodesMap[node.type]
				if (content && eventData.id === node.id) {
					const {
						effect
					} = content
					if (!effect || keys.some((key) => effect.includes(key))) {
						this.initProperties(eventData.properties);
					}
				}
			});
		},
		methods: {
			initProperties(properties) {
				if (properties.borderWidth) {
					this.borderWidth = properties.borderWidth;
				}
				if (properties.width) {
					this.width = properties.width;
				}
				if (properties.height) {
					this.height = properties.height;
				}
				if (properties.borderColor) {
					this.borderColor = properties.borderColor;
				}
				if (properties.backgroundColor) {
					this.backgroundColor = properties.backgroundColor;
				}
				if (properties.text) {
					this.text = properties.text;
				}
				if (properties.textColer) {
					this.textColer = properties.textColer;
				}
				if (properties.textSize) {
					this.textSize = properties.textSize;
				}
				if (properties.fontWeight) {
					this.fontWeight = properties.fontWeight;
				}
			}
		}
	}
</script>
<style scoped="scoped">
	.circle-node {
		border-radius: 50%;
	}
</style>