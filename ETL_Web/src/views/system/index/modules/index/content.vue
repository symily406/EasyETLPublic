<template>
	<div>
		<keep-alive>
			<transition>
				<router-view></router-view>
			</transition>
		</keep-alive>
	</div>
</template>
<script>
	export default {
		name: "content_container",
		components: {

		},
		props: {

		},
		data() {
			return {

			}
		},
		created() {

		},
		methods: {

		}
	}
</script>
