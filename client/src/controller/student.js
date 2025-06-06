import studentApi from '../api/studentApi'

function toString(params) {
    Object.keys(params).forEach(k => {
        params[k] = params[k] + ''
    });
}

export const status = [
    {name: '未外出', id: 0},
    {name: '已外出', id: 1},
    {name: '已返回', id: 2},
    {name: '超时未归', id: 3},
    {name: '超时返回', id: 4},
    {name: '非假外出', id: 5}
];
export const synacStatus = [
    {name: '成功', id: true},
    {name: '未成功', id: false},
];
export default {
    getList(params) {
        params.pageCount++;
        toString(params);
        params.departmentId = parseInt(params.departmentId);
        return studentApi.getList(params)
    },
    getOne(id) {
        return studentApi.getOne(id)
    },
    add(params) {
        return studentApi.add(params)
    },
    delete(params) {
        return studentApi.delete(params)
    },
    deleteLevel(params) {
        return studentApi.deleteLevel(params)
    },
    update(params) {
        return studentApi.update(params)
    },
    updateImage(id, file) {
        return studentApi.updateImage(id, file)
    },
    getImage(id) {
        return studentApi.getImage(id);
    },
    getLeave(id) {
        return studentApi.getLeave(id)
    },
    getLeaveList(params) {
        params.pageCount++;
        toString(params);
        return studentApi.getLeaveList(params)
    },
    addLeaveApplicant(params) {
        return studentApi.addApplicant(params);
    },
    importStudents(params) {
        return studentApi.importStudents(params)
    },
    importStudentPic(params) {
        return studentApi.importStudentPic(params)
    },
    getDepartmentLimits() {
        return studentApi.getDepartmentLimits()
    },
    getDepartmentLimit(id) {
        return studentApi.getDepartmentLimit(id)
    },
    setDepartmentLimit(id, limit) {
        return studentApi.setDepartmentLimit(id, limit)
    },
    getLeaveViaList(id) {
        return studentApi.getLeaveViaList(id)
    },
    updateLeaveStatus(id, status) {
        return studentApi.updateLeaveStatus(id, status)
    },
    exportVacations(params) {
        return studentApi.exportVacations(params)
    }
}