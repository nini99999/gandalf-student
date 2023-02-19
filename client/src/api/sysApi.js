import BaseApi from './BaseApi'

class SysApi extends BaseApi {
    getUser() {
        return this.httpGet(`/sys/getUser`, {}, '获取登录用户信息')
    }

    getUserList({userName, realName, roleId, departmentId, pageSize = 20, pageCount = 1}) {
        return this.httpPost(`/sys/getUserList`, {
            userName,
            realName,
            roleId,
            departmentId,
            pageSize,
            pageCount
        }, {}, '获取用户列表失败')
    }

    getUserById(id) {
        return this.httpPost('/sys/getUserById', {id}, {}, '获取用户失败')
    }

    getUserByName(userName) {
        return this.httpPost('/sys/getUserByName', {userName}, {}, '获取用户失败')
    }

    getParameters(type) {
        return this.httpPost(`/sys/getDictionary`, {type}, {}, '获取参数失败')
    }

    getRoleList() {
        return this.httpGet(`/sys/getRoleList`, {}, '获取角色列表失败')
    }

    getDepartment(id = 1) {
        return this.httpPost(`/sys/getDepartment`, {id}, {}, '获取部门失败')
    }

    addDepartment({parentId, name, memo}) {
        return this.httpPost(`/sys/addDepartment`, {parentId, name, memo}, {}, '新增部门失败')
    }

    addUser({userName, realName, roleId, departmentId, positionId, code, mobile, genderId, cardNo}) {
        return this.httpPost(`/sys/addUser`, {
            userName,
            realName,
            roleId,
            departmentId,
            positionId,
            code,
            mobile,
            genderId,
            cardNo
        }, {}, '新增用户失败')
    }

    updateUser({id, realName, roleId, departmentId, positionId, code, mobile, genderId, cardNo}) {
        return this.httpPost(`/sys/updateUser`, {
            id,
            realName,
            roleId,
            departmentId,
            positionId,
            code,
            mobile,
            genderId,
            cardNo
        }, {}, '更新用户失败')
    }

    changePassword({password, oldPassword}) {
        return this.httpPost(`/sys/changePassword`, {password, oldPassword}, {}, '修改密码失败')
    }

    resetPassword(id) {
        return this.httpPost('/sys/initPassword', {id}, {}, '重置密码失败')
    }
}

export default new SysApi()