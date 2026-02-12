<template>
	<eForm ref="eForm" :dialogTitle="dialogTitle" :dialogWidth="dialogWidth" :dialogTop="dialogTop"
		:dialogHeight="dialogHeight" @eventCallBack="eventCallBack" />
</template>
<script>
	import { dialogMixin } from "@/mixins/mixins";
	import eForm from './form';
	export default {
		name: 'orgEdit',
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
				const editRow = {
					//主键
					orgId: row.id,
					//归属企业
					companyId: row.companyId,
					//机构全称
					orgFullName: row.fullName,
					//机构简称
					orgName: row.name,
					//机构编码
					orgCode: row.orgCode,
					//上级机构
					pOrgId: row.pid,
					orgPath: row.fullPath,

					showOrder: row.showOrder,
					allowEdit: row.allowEdit,
					allowDelete: row.allowDelete,
					enable: row.enable
				}
				eForm.form = this.$app.initFormData(eForm.form, editRow);
			},
			eventCallBack(eventType) {
				this.$emit('eventCallBack', eventType);
			}
		}
	}
</script>
<style scoped="scoped">
</style>
