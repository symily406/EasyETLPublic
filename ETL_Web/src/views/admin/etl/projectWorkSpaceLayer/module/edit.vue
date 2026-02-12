<template>
	<eForm ref="eForm" :dialogTitle="dialogTitle" :dialogWidth="dialogWidth" :dialogTop="dialogTop" :dialogHeight="dialogHeight" @eventCallBack="eventCallBack" />
</template>
<script>
	import {dialogMixin} from "@/mixins/mixins";
	import eForm from './form';
	export default{
		name: 'projectWorkSpaceLayerEdit',
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
				eForm.form.dictTaskTypes=row.taskTypes.split(",");
				eForm.form.calculateTypes=row.calculateTypes.toString();
			},
			eventCallBack(eventType) {
				this.$emit('eventCallBack',eventType);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
