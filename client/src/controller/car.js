import carApi from '../api/carApi'

function toString(params) {
    Object.keys(params).forEach(k => {
        params[k] = params[k] + ''
    });
}

export const status = [
    {name: '全部'},
    {name: '未审批', id: 0},
    {name: '审批中', id: 1},
    {name: '通过', id: 2},
    {name: '拒绝', id: 3},
    {name: '已派车', id: 4},
    {name: '超时', id: 5},
    {name: '拒绝派车', id: 6},
    {name: '撤销', id: 7}
];

export default {
    async getCarList(params) {
        params.pageCount++;
        toString(params);
        let result = await carApi.getCarList(params);
        for (let i = 0; i < result.data.length; i++) {
            result.data[i].pic = carApi.getCarImage(result.data[i].id)
        }
        return result;
    },
    getCarById(id) {
        return carApi.getCarById(id);
    },
    add(params) {
        return carApi.addCar(params);
    },
    update(params) {
        return carApi.updateCar(params);
    },
    updateImage(id, file) {
        return carApi.uploadCarImage(id, file);
    },
    getImage(id) {
        return carApi.getCarImage(id);
    },
    addCarOperation(params) {
        return carApi.addCarOperation(params);
    },
    getCarOperationList(params) {
        params.pageCount++;
        toString(params);
        return carApi.getCarOperationList(params);
    },
    getCarOperation(id) {
        return carApi.getCarOperation(id);
    },
    addCarApplicant(params) {
        return carApi.addCarApplicant(params);
    },
    updateCarApplicant(params) {
        return carApi.updateCarApplicant(params);
    },
    cancelCarApplicant(id) {
        return carApi.cancelCarApplicant(id);
    },
    getCarApplicants(params) {
        params.pageCount++;
        toString(params);
        return carApi.getCarApplicantList(params);
    },
    getCarApplicant(id) {
        return carApi.getCarApplicant(id)
    },
    getCarControlList(params) {
        params.pageCount++;
        toString(params);
        return carApi.getCarControlList(params)
    },
    getCarControlListExcel(params) {
        return carApi.getCarControlListExcel(params);
    },
    getCarControl(id) {
        return carApi.getCarControl(id)
    },
    addCarControl(params) {
        return carApi.addCarControl(params);
    },
    updateCarControl(params) {
        return carApi.updateCarControl(params);
    },
    getStandByCar(params) {
        return carApi.getStandByCar(params)
    },
    carReturn(params) {
        return carApi.carReturn(params)
    },
    getVALIDCarApprovalList(id) {
        return carApi.getVALIDCarApprovalList(id)
    },
    getCarApprovalById(id) {
        return carApi.getCarApprovalById(id);
    },
    carApproval(params) {
        return carApi.carApproval(params);
    },
    refuseCarApplicant(params) {
        return carApi.refuseCarApplicant(params);
    },
    getNowControlCar() {
        return carApi.getNowControlCar()
    },
    getNowStandByCar() {
        return carApi.getNowStandByCar();
    },
    getMaintainCar() {
        return carApi.getMaintainCar();
    },
    getRepairCar() {
        return carApi.getRepairCar()
    }
}