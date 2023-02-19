import sysApi from '../api/sysApi'

export default {
    getPositions() {
        return sysApi.getParameters('0000');
    },
    getGender() {
        return sysApi.getParameters('0001');
    },
    getCarTypes() {
        return sysApi.getParameters('1001');
    },
    getCarStatus() {
        return sysApi.getParameters('1002');
    },
    getCarUseTypes() {
        return sysApi.getParameters('1003');
    },
    getStudentTypes() {
        return sysApi.getParameters('1101');
    }
}