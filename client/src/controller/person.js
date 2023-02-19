import sysApi from '../api/sysApi'

export default {
    getDepartmentList() {
        return sysApi.getDepartment();
    },
    addDepartment(parentId, name, memo) {
        return sysApi.addDepartment({parentId, name, memo});
    },
    getRoleList() {
        return sysApi.getRoleList();
    },
    getUserList(params) {
        params.pageCount++;
        return sysApi.getUserList(params);
    },
    getUserById(id) {
        return sysApi.getUserById(id);
    },
    getUserByName(name) {
        return sysApi.getUserByName(name);
    },
    addUser(params) {
        return sysApi.addUser(params);
    },
    updateUser(params) {
        return sysApi.updateUser(params);
    },
    resetPassword(id) {
        return sysApi.resetPassword(id)
    }
}