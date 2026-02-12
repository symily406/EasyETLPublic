<template>
	<eForm ref="eForm" :dialogTitle="dialogTitle" :dialogWidth="dialogWidth" :dialogTop="dialogTop" :dialogHeight="dialogHeight" @eventCallBack="eventCallBack" />
</template>
<script>
	import {dialogMixin} from "@/mixins/mixins";
	import eForm from './form';
	export default{
		name: 'taskScheduleEdit',
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
				eForm.form=this.$app.initFormData(eForm.form,row);
				eForm.form.runNode=row.runNode.toString();
				eForm.form.level=row.level.toString();
				eForm.form.bootUp=row.bootUp.toString();
			},
			eventCallBack(eventType) {
				this.$emit('eventCallBack',eventType);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
