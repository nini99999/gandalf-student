import BaseApi from "./BaseApi";

class StudentApi extends BaseApi {
    getList({name, code, departmentId, pageSize = 20, pageCount = 1}) {
        return this.httpPost('/student/getStudentList', {
            name,
            code,
            departmentId,
            pageSize,
            pageCount
        }, {}, '获取学员列表失败')
    }

    getOne(id) {
        return this.httpPost('/student/getStudent', {id}, {}, '获取学员失败')
    }

    add({name, code, nativePlace, identityCode, cardCode, startTime, endTime, typeId, departmentId, genderId}) {
        return this.httpPost('/student/addStudent', {
            name,
            code,
            nativePlace,
            identityCode,
            cardCode,
            startTime,
            endTime,
            typeId,
            departmentId,
            genderId
        }, {}, '新增学员失败')
    }
    delete({id, ids}) {
        return this.httpPost('/student/deleteStudent', {
            id,
            ids,
        }, {}, '删除学员失败')
    }
    update({id, name, code, nativePlace, identityCode, cardCode, startTime, endTime, typeId, departmentId, genderId}) {
        return this.httpPost('/student/updateStudent', {
            id,
            name,
            code,
            nativePlace,
            identityCode,
            cardCode,
            startTime,
            endTime,
            typeId,
            departmentId,
            genderId
        }, {}, '更新学员失败')
    }

    updateImage(objectId, pic) {
        let form = new FormData();
        form.append('objectId', objectId);
        form.append('pic', pic);
        return this.httpPost('/file/upLoadStudentPic', form, {}, '上传图片失败');
    }

    getImage(objectId) {
        return this.url('/file/getStudentPic', {objectId}, true);
    }

    getLeave(id) {
        return this.httpPost('/student/getLeave', {id}, {}, '获取请假单失败')
    }

    getLeaveList({pageSize, pageCount, status, departmentId, creatUserId, queryStartTime, queryEndTime}) {
        return this.httpPost('/student/getLeaveList', {
            pageSize,
            pageCount,
            status: parseInt(status),
            departmentId,
            creatUserId,
            queryStartTime,
            queryEndTime
        }, {}, '查询请假单失败')
    }

    addApplicant({startTime, endTime, address, reason, applicantUserId}) {
        return this.httpPost('/student/addApplicant', {
            startTime, endTime, address, reason, applicantUserId
        }, {}, '提交请假审批失败')
    }

    importStudents(studentFile) {
        let form = new FormData();
        form.append('studentFile', studentFile);
        return this.httpPost('/student/importStudent', form, {}, '导入学生信息失败', true);
    }

    importStudentPic(picZip) {
        let form = new FormData();
        form.append('picZip', picZip);
        return this.httpPost('/student/upLoadStudentsPic', form, {}, '导入学生图片失败');
    }

    getDepartmentLimits() {
        return this.httpGet('/student/getLeaveLimitList', {}, '获取部门请假限制失败')
    }

    getDepartmentLimit(departmentId) {
        return this.httpPost('/student/getLeaveLimit', {departmentId}, {}, '获取部门请假限制失败')
    }

    setDepartmentLimit(id, limitValue) {
        return this.httpPost('/student/updateLeaveLimit', {id, limitValue}, {}, '修改请假限制失败')
    }

    getLeaveViaList(id) {
        return this.httpPost('/student/getLeaveViaList', {id}, {}, '获取出入记录失败')
    }

    updateLeaveStatus(id, status) {
        return this.httpPost('/student/changeStatus', {id, status}, {}, '修改请假状态失败')
    }

    exportVacations({status, departmentId, creatUserId, queryStartTime, queryEndTime}) {
        return this.httpPost('/student/getLeaveListExcel', {
            status,
            departmentId,
            creatUserId,
            queryStartTime,
            queryEndTime
        }, {}, '导出失败', true)
    }
}

export default new StudentApi();