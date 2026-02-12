<template>
	<div class="app-container">
		<iframe :src="src" frameborder="0" width="100%" height="100%" scrolling="auto"  :onload="onloaded()"></iframe>
	</div>
</template>
<script>
	export default {
		name: "iframeContainer",
		components: {

		},
		props: {

		},
		watch: {
			$route: 'loadIframe'
		},
		data() {
			return {
				src: "",
				loading: null
			}
		},
		mounted() {
			this.loadIframe();
		},
		created() {

		},
		methods: {
			loadIframe() {
				this.src = decodeURIComponent(this.$route.query.url);
				this.load();
			},
			load: function() {
				this.loading = this.$loading({
					lock: true,
					text: "loading...",
					spinner: "el-icon-loading",
					background: "rgba(0, 0, 0, 0.5)",
				})
			},
			onloaded: function() {
				if (this.loading) {
					this.loading.close()
				}
			}
		}
	}
</script>
