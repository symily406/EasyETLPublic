<template>
	<div class="rect-node"
		:style="{width: width + 'px',height: height + 'px',  border: borderWidth + 'px solid ' + borderColor,  background: backgroundColor}">
		<div
			:style="{paddingTop:'5px',paddingBottom:'5px', textAlign:textAlign,color:textColer,fontSize:textSize+'px',fontWeight:textFontWeight}">
			{{text}}
		</div>
		<div :style="{textAlign:labelAlign,color:labelColer,fontSize:labelSize+'px',fontWeight:labelFontWeight}">
			{{label}}
		</div>
		<div class="rect-icon" :style="{height: height + 'px',lineHeight:height+'px',color:iconColer}"><i :class="icon"></i></div>
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
		name: 'RectNode',
		inject: ['getNode', 'getGraph'],
		data() {
			return {
				width: 200,
				height: 80,
				borderWidth: 1,
				borderColor: "#000",
				backgroundColor: "#fff",
				text: '',
				textAlign: 'left',
				textColer: '#000',
				textSize: 12,
				textFontWeight: 'none',
				label: '',
				labelColer: '#000',
				labelSize: 12,
				labelFontWeight: 'none',
				labelAlign: 'left',
				icon:'',
				iconColer:'#ffffff',
				nodeStatus:999999
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
				if (properties.textAlign) {
					this.textAlign = properties.textAlign;
				}
				if (properties.textColer) {
					this.textColer = properties.textColer;
				}
				if (properties.textSize) {
					this.textSize = properties.textSize;
				}
				if (properties.textFontWeight) {
					this.textFontWeight = properties.textFontWeight;
				}
				if (properties.label) {
					this.label = properties.label;
				}
				if (properties.labelAlign) {
					this.labelAlign = properties.labelAlign;
				}
				if (properties.labelColer) {
					this.labelColer = properties.labelColer;
				}
				if (properties.labelSize) {
					this.labelSize = properties.labelSize;
				}
				if (properties.textFontWeight) {
					this.labelFontWeight = properties.labelFontWeight;
				}
				if (properties.icon) {
					this.icon = properties.icon;
				}
				if (properties.iconColer) {
					this.iconColer = properties.iconColer;
				}
				if (properties.nodeStatus) {
					this.nodeStatus = properties.nodeStatus;
				}
			}
		}
	}
</script>
<style scoped="scoped">
	.rect-node {
		position: relative;
		.rect-icon{
			position: absolute;
			top: 0px;
		}
	}
</style>