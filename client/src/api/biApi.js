import BaseApi from "./BaseApi";

class BIApi extends BaseApi {
    getStudentCount() {
        return this.httpGet('/BI/getStudentCount', {}, '获取在读学生数量失败')
    }

    getCountStudentOutSchool() {
        return this.httpGet('/BI/getCountStudentOutSchool', {}, '获取外出学员数量失败')
    }

    getCountCarControl() {
        return this.httpGet('/BI/getCountCarControl', {}, '获取外勤车辆数量失败')
    }

    getCountCarOperation() {
        return this.httpGet('/BI/getCountCarOperation', {}, '获取保养车辆数量失败')
    }

    getCountCarControlDaily({minTime, maxTime}) {
        return this.httpPost('/BI/getCountCarControlDaily', {minTime, maxTime}, {}, '获取学生每日请假失败')
    }

    getCountCarControlDepartment({minTime, maxTime}) {
        return this.httpPost('/BI/getCountCarControlDepartment', {minTime, maxTime}, {}, '获取学生每日请假失败')
    }

    getCountStudentDaily({minTime, maxTime}) {
        return this.httpPost('/BI/getCountStudentDaily', {minTime, maxTime}, {}, '获取学生每日请假失败')
    }

    getCountStudentDepartment({minTime, maxTime}) {
        return this.httpPost('/BI/getCountStudentDepartment', {minTime, maxTime}, {}, '获取学生每日请假失败')
    }
}

export default new BIApi();