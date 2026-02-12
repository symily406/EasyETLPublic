<template>
	<eForm ref="eForm" :dialogTitle="dialogTitle" :dialogWidth="dialogWidth" :dialogTop="dialogTop"
		:dialogHeight="dialogHeight" @eventCallBack="eventCallBack" />
</template>
<script>
	import {
		dialogMixin
	} from "@/mixins/mixins";
	import eForm from './form';
	export default {
		name: 'dbSourceEdit',
		components: {
			eForm
		},
		props: {

		},
		mixins: [dialogMixin],
		methods: {
			edit(row) {
				const eForm = this.$refs['eForm'];
				eForm.dialogVisible = true;
				eForm.form = this.$app.initFormData(eForm.form, row);
				eForm.form.isConn = 0;
				eForm.dbCatalogs=[];
				eForm.dbScheams=[];
				let catalog = row.dbCatalog;
				if (catalog) {
					eForm.dbCatalogs.push({
						"catalog": catalog
					});
				}
				let schema = row.dbSchema;
				if (schema) {
					eForm.dbSchemas.push({
						"schema": schema
					});
				}
			},
			eventCallBack(eventType) {
				this.$emit('eventCallBack', eventType);
			}
		}
	}
</script>
<style scoped="scoped">
</style>