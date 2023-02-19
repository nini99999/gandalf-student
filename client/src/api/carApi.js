import BaseApi from "./BaseApi";

class CarApi extends BaseApi {
    addCar({carNO, buyTime, insuranceTime, maintenanceTime, checkTime, mileage, userId, carTypeId, departmentId}) {
        return this.httpPost('/car/addCar', {
            carNO,
            buyTime,
            insuranceTime,
            maintenanceTime,
            checkTime,
            mileage,
            userId,
            carTypeId,
            departmentId
        }, {}, '添加车辆失败');
    }

    updateCar({id, carNO, buyTime, insuranceTime, maintenanceTime, checkTime, mileage, userId, carTypeId, departmentId}) {
        return this.httpPost('/car/updateCar', {
            id,
            carNO,
            buyTime,
            insuranceTime,
            maintenanceTime,
            checkTime,
            mileage,
            userId,
            carTypeId,
            departmentId
        }, {}, '更新车辆信息失败');
    }

    uploadCarImage(objectId, pic) {
        let form = new FormData();
        form.append('objectId', objectId);
        form.append('pic', pic);
        return this.httpPost('/file/upLoadCarPic', form, {}, '上传图片失败');
    }

    getCarImage(objectId) {
        return this.url('/file/getCarPic', {objectId}, true);
    }

    getCarList({carNO, carTypeId, statusId, pageSize = 1, pageCount = 20}) {
        return this.httpPost('/car/getCarList', {carNO, carTypeId, statusId, pageSize, pageCount}, {}, '获取车辆列表失败')
    }

    getCarById(id) {
        return this.httpPost('/car/getCar', {id}, {}, '获取车辆失败')
    }

    addCarApplicant({applicantUserId, leadName, leadMobile, carCount, isArea, startTime, endTime, reason, routeStart, routeMiddle, routeEnd, carTypeId, useTypeId}) {
        return this.httpPost('/car/addCarApplicant', {
            applicantUserId,
            leadName,
            leadMobile,
            carCount,
            isArea,
            startTime,
            endTime,
            reason,
            routeStart,
            routeMiddle,
            routeEnd,
            carTypeId,
            useTypeId
        }, {}, '提交车辆申请失败');
    }

    updateCarApplicant({id, leadName, leadMobile, carCount, isArea, startTime, endTime, reason, routeStart, routeMiddle, routeEnd, carTypeId, useTypeId}) {
        return this.httpPost('/car/updateCarApplicant', {
            id,
            leadName,
            leadMobile,
            carCount,
            isArea,
            startTime,
            endTime,
            reason,
            routeStart, routeMiddle, routeEnd,
            carTypeId,
            useTypeId
        }, {}, '修改车辆申请失败');
    }

    cancelCarApplicant(id) {
        return this.httpPost('/car/cancelCarApplicant', {id}, {}, '取消用车申请失败');
    }

    getCarApplicantList({applicantUserId, status, pageSize = 20, pageCount = 1}) {
        return this.httpPost('/car/getCarApplicantList', {
            applicantUserId,
            status,
            pageSize,
            pageCount
        }, {}, '获取车辆申请列表失败')
    }

    getCarApplicant(id) {
        return this.httpPost('/car/getCarApplicant', {id}, {}, '获取车辆申请失败')
    }

    getVALIDCarApprovalList(approvalUserId) {
        return this.httpPost('/car/getVALIDCarApprovalList', {approvalUserId}, {}, '获取待审批单据失败')
    }

    getCarApprovalById(id) {
        return this.httpPost('/car/getCarApprovalById', {id}, {}, '获取审批详情失败');
    }

    carApproval({id, status, opinion}) {
        return this.httpPost('/car/carApproval', {id, status, opinion}, {}, '提交审批失败');
    }

    getStandByCar({id, startTime, endTime}) {
        return this.httpPost('/car/getStandByCar', {id, startTime, endTime}, {}, '获取待命车辆失败');
    }

    getDriverList({id, startTime, endTime}) {
        return this.httpPost('/car/getDriverList', {id, startTime, endTime}, {}, '获取待命司机失败');
    }

    addCarControl({carApplicantId, driverName, alertTime, carId, memo, address, applicantUserId, routeStart, routeMiddle, routeEnd, estimateStartTime, estimateEndTime, carTypeId, useTypeId}) {
        return this.httpPost('/car/addCarControl', {
            carApplicantId,
            driverName,
            alertTime,
            carId,
            memo,
            address,
            applicantUserId,
            routeStart, routeMiddle, routeEnd,
            estimateStartTime,
            estimateEndTime,
            carTypeId,
            useTypeId
        }, {}, '新建派车单失败');
    }

    updateCarControl({id, carApplicantId, driverName, alertTime, carId, memo, address, applicantUserId, routeStart, routeMiddle, routeEnd, estimateStartTime, estimateEndTime, carTypeId, useTypeId}) {
        return this.httpPost('/car/updateCarControl', {
            id,
            carApplicantId,
            driverName,
            alertTime,
            carId,
            memo,
            address,
            applicantUserId,
            routeStart, routeMiddle, routeEnd,
            estimateStartTime,
            estimateEndTime,
            carTypeId,
            useTypeId
        }, {}, '修改派车单失败')
    }

    getCarControl(id) {
        return this.httpPost('/car/getCarControl', {id}, {}, '获取派车单失败')
    }

    getCarControlList({carApplicantId, carNO, useTypeId, driverName, startMaxTime, startMinTime, endMaxTime, endMinTime, pageCount = 1, pageSize = 20}) {
        return this.httpPost('/car/getCarControlList', {
            carApplicantId,
            carNO,
            useTypeId,
            driverName,
            startMaxTime,
            startMinTime,
            endMaxTime,
            endMinTime,
            pageCount,
            pageSize
        }, {}, '查询派车单失败')
    }

    getCarControlListExcel({carNO, useTypeId, driverName, startMaxTime, startMinTime, endMaxTime, endMinTime}) {
        return this.httpPost('/car/getCarControlListExcel', {
            carNO,
            useTypeId,
            driverName,
            startMaxTime,
            startMinTime,
            endMaxTime,
            endMinTime
        }, {}, '导出失败', true)
    }

    carReturn({id, startTime, endTime, mileage}) {
        return this.httpPost('/car/carReturn', {id, startTime, endTime, mileage}, {}, '车辆归队失败')
    }

    addCarOperation({typeId, driverName, carId, startTime, endTime}) {
        return this.httpPost('/car/addCarOperation', {
            typeId,
            driverName,
            carId,
            startTime,
            endTime
        }, {}, '新建车辆运营失败')
    }

    getCarOperationList({carNO, typeId, pageCount = 1, pageSize = 20}) {
        return this.httpPost('/car/getCarOperationList', {carNO, typeId, pageCount, pageSize}, {}, '查询运营单失败')
    }

    getCarOperation(id) {
        return this.httpPost('/car/getCarOperation', {id}, {}, '获取运营单失败')
    }

    refuseCarApplicant({id, refuseReason}) {
        return this.httpPost('/car/refuseCarApplicant', {id, refuseReason}, {}, '拒绝派车失败');
    }

    getNowControlCar() {
        return this.httpGet('/car/getNowControlCar', {}, '获取当日外派车辆失败')
    }

    getNowStandByCar() {
        return this.httpGet('/car/getNowStandByCar', {}, '获取当日待命车辆失败')
    }

    getMaintainCar() {
        return this.httpGet('/car/getMaintainCar', {}, '获取当日保养车辆失败')
    }

    getRepairCar() {
        return this.httpGet('/car/getRepairCar', {}, '获取当日维修车辆失败')
    }

}

export default new CarApi()