import biApi from '../api/biApi'

const START_YEAR = 2019;
const START_MONTH = 6;

function generatorMonths() {
    let now = new Date();
    let endYear = now.getFullYear();
    let endMonth = now.getMonth() + 1;
    let arr = [];
    for (let i = START_YEAR; i <= endYear; i++) {
        let start = i == START_YEAR ? START_MONTH : 1;
        let end = i == endYear ? endMonth : 12;
        for (let j = start; j <= end; j++) {
            arr.push({id: arr.length, year: i, month: j, name: `${i}年${j}月`});
        }
    }
    return arr;
}

export default {
    months: generatorMonths(),
    async getTotal() {
        return await Promise.all([
            biApi.getStudentCount(),
            biApi.getCountStudentOutSchool(),
            biApi.getCountCarControl(),
            biApi.getCountCarOperation()
        ])
    },

    getCountCarControlDaily(params) {
        return biApi.getCountCarControlDaily(params);
    },

    getCountCarControlDepartment(params) {
        return biApi.getCountCarControlDepartment(params);
    },

    getCountStudentDaily(params) {
        return biApi.getCountStudentDaily(params);
    },

    getCountStudentDepartment(params) {
        return biApi.getCountStudentDepartment(params);
    }
}