<template>
	<eForm ref="eForm" :dialogTitle="dialogTitle" :dialogWidth="dialogWidth" :dialogTop="dialogTop" :dialogHeight="dialogHeight" @eventCallBack="eventCallBack" />
</template>
<script>
	import {dialogMixin} from "@/mixins/mixins";
	import eForm from './form';
	export default{
		name: 'confEdit',
		components: {
			eForm
		},
		props: {
			
		},
		mixins: [dialogMixin],
		methods: {
			edit(row){
				const eForm=this.$refs['eForm'];
				eForm.dialogVisible=true;
				eForm.form.conf=this.$app.initFormData(eForm.form.conf,row);
			},
			eventCallBack(eventType) {
				this.$emit('eventCallBack',eventType);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
