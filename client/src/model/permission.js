import DEF from './student'
export const permissions = [
    {name: '系统首页-关键指标', key: 'home_total'},
    {name: '系统首页-请假审批', key: 'home_vacation_request'},
    {name: '系统首页-我的请假审批', key: 'home_vacation_my'},
    {name: '系统首页-请销假管理', key: 'home_vacation_mgr'},
    {name: '系统首页-考勤统计', key: 'home_vacation_data'},
    {name: '系统首页-用车申请', key: 'home_car_request'},
    {name: '系统首页-我的用车申请', key: 'home_car_my'},
    {name: '系统首页-用车申请管理', key: 'home_car_mgr'},
    {name: '系统首页-车辆运营统计', key: 'home_car_data'},
    {name: '请销假管理', key: 'vacation'},
    {name: '请销假管理-编辑', key: 'vacation-edit'},
    {name: '车辆运营-用车申请管理', key: 'car_request'},
    {name: '车辆运营-派车管理', key: 'car_send'},
    {name: '车辆运营-派车管理-编辑', key: 'car_send-edit'},
    {name: '车辆运营-数据统计', key: 'car_total'},
    {name: '车辆信息管理', key: 'car'},
    {name: '车辆信息管理-编辑', key: 'car-edit'},
    {name: '学生档案管理', key: 'student'},
    {name: '学生档案管理-编辑', key: 'student-edit'},
    {name: '权限管理-人员', key: 'user'},
    {name: '权限管理-部门', key: 'department'},
    {name: '权限管理-部门请假限制', key: 'department-limit'},
    {name: '权限管理-角色', key: 'role'}
];

let roles = DEF;

let value = window.localStorage.getItem('permission');
if (value) {
    roles = JSON.parse(value);
}

export const mgr = {
    get(role) {
        return roles[role];
    },
    set(role, value) {
        roles[role] = value;
        window.localStorage.setItem('permission', JSON.stringify(roles));
    },
    reset() {
        window.localStorage.removeItem('permission');
        roles = DEF;
    },
    getAll() {
        return roles
    }
};