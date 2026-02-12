import store from "@/store/index.js";

function checkPermission(role) {
	if (role) {
		const roles = store.state.global.roles;
		const permissionRoles = role.split(",");
		const hasPermission = roles.some(role => {
			return permissionRoles.includes(role)
		});
		if (!hasPermission) {
			return false;
		}
		return true;
	}
	return false;
}
export default {
	checkPermission
}
