<template>
	<div>
		 <div class="tinymce-container editor-container" :class="{fullscreen:fullscreen}">
			<textarea :id="tinymceId" class="tinymce-textarea" style="resize:none ;" />
		</div>
	</div>
</template>
<script>
	import toolbar from './toolbar'
	import plugins from './plugins'
	export default {
		props: {
			id: {
				type: String,
				default: function() {
					return 'vue-tinymce-' + +new Date() + ((Math.random() * 1000).toFixed(0) + '')
				}
			},
			height: {
				type: [Number, String],
				required: false,
				default: 300
			},
			tinymceEditContent: String
		},
		model: {
			prop: 'tinymceEditContent',
			event: 'tinymceEditCallBack'
		},
		data() {
			return {
				hasChange: false,
				hasInit: false,
				tinymceId: this.id,
				fullscreen: false,
			}
		},
		created() {
			this.$on('initTinymce', function() {
				this.initTinymce();
			});
		},
		mounted() {
			this.$nextTick(function() {
				this.$on('destroyTinymce', function() {
					this.destroyTinymce();
				});
				this.$on('insertEditContent', function(content) {
					this.setContent(content);
				});
			});
		},
		methods: {
			initTinymce() {
				let $this = this;
				tinymce.init({
					branding: false,
					menubar: false,
					fontsize_formats: "8pt 10pt 12pt 14pt 18pt 24pt 36pt", // 第二步
					language: 'zh_CN',
					selector: `#${this.tinymceId}`,
					height: this.height,
					body_class: 'panel-body ',
					object_resizing: true,
					toolbar: toolbar,
					plugins: plugins,
					end_container_on_empty_block: true,
					powerpaste_word_import: 'clean',
					code_dialog_height: 450,
					code_dialog_width: 1000,
					advlist_bullet_styles: 'square',
					advlist_number_styles: 'default',
					default_link_target: '_blank',
					link_title: false,
					paste_auto_cleanup_on_paste: true,
					paste_remove_styles: true,
					paste_remove_styles_if_webkit: true,
					paste_strip_class_attributes: true,
					init_instance_callback: editor => {
						if (this.tinymceEditContent) {
							editor.setContent(this.tinymceEditContent)
						}
						editor.on('input change undo redo execCommand KeyUp', function(e) {
							$this.$emit('tinymceEditCallBack', editor.getContent());
						});
					},
					setup(editor) {
						editor.on('FullscreenStateChanged', e => {
							this.fullscreen = e.state
						});

						editor.ui.registry.addButton('uploadImage', {
							tooltip: '上传图片',
							icon: 'image',
							onAction: function() {
								//$this.setContent("45344535435");
								$this.$emit("uploadImage");
							}
						});
						editor.ui.registry.addButton('uploadMedia', {
							tooltip: '上传音视频',
							icon: 'embed',
							onAction: function() {
								$this.$emit("uploadMedia");
							}
						});
						editor.ui.registry.addButton('uploadFile', {
							tooltip: '上传附件',
							icon: 'upload',
							onAction: function() {
								$this.$emit("uploadFile");
							}
						});
					}
				})
			},
			//销毁编辑器
			destroyTinymce() {
				if (window.tinymce.get(this.tinymceId)) {
					window.tinymce.get(this.tinymceId).destroy()
				}
			},
			// 设置编辑器内容
			setContent(value) {
				window.tinymce.get(this.tinymceId).setContent(value)
			},
			// 获取编辑器内容
			getContent() {
				window.tinymce.get(this.tinymceId).getContent()
			}
		}
	}
</script>
