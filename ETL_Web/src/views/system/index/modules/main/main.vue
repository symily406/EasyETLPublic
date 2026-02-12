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
		name: "main_container",
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
