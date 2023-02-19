<template>
    <panel>
        <panel-title>
            <div class="title">{{type|type}}</div>
        </panel-title>
        <cform>
            <template v-if="isApp">
                <div class="option">
                    <span class="name">申请单号</span>
                    <div class="container">
                        <div class="value">
                            <div class="text">{{applicant.applicantCode}}</div>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">申请人</span>
                    <div class="container">
                        <div class="value">
                            <div class="text">{{applicant.applicantUserName}}</div>
                        </div>
                    </div>
                </div>
            </template>
            <div class="option" v-else>
                <span class="name">申请人</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{user.realName}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">用车开始时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" v-model="estimateStartTime" :limit="true"
                                   :readonly="isApp || readonly"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">用车结束时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" v-model="estimateEndTime" :limit="true"
                                   :readonly="isApp || readonly"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">用车类型</span>
                <div class="container">
                    <div class="value">
                        <combobox class="component" v-model="useTypeId" :source="useTypes"
                                  :readonly="isApp || readonly"></combobox>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">{{type === 'other'?'养护车辆':'派遣车辆'}}</span>
                <div class="container">
                    <div class="value">
                        <combobox class="component" v-model="carId" :source="cars" :readonly="readonly"></combobox>
                        <div class="tip">请先选择用车时间</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">派遣司机</span>
                <div class="container">
                    <div class="value">
                        <input class="input" v-model="driverName" :readonly="readonly">
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">提醒设置</span>
                <div class="container">
                    <div class="value">
                        <input class="input" type="number" v-model="alertTime" :readonly="readonly"
                               placeholder="请输入提前多少分钟提醒">
                    </div>
                </div>
            </div>
            <div class="option" v-if="!isApp">
                <span class="name">路线</span>
                <div class="container">
                    <div class="value">
                        <div class="component i3">
                            <input class="input" v-model="routeStart" :readonly="readonly"/>
                            <span>-</span>
                            <input class="input" v-model="routeMiddle" :readonly="readonly"/>
                            <span>-</span>
                            <input class="input" v-model="routeEnd" :readonly="readonly"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">上车地点</span>
                <div class="container">
                    <div class="value">
                        <input class="input" v-model="address" :readonly="readonly">
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">说明</span>
                <div class="container">
                    <div class="value">
                        <textarea class="input" v-model="memo" :readonly="readonly"></textarea>
                    </div>
                </div>
            </div>
            <div class="submit">
                <a class="btn normal" @click="onCancel">取消</a>
                <a class="btn normal" @click="printVisible=true" v-if="id">打印</a>
                <a class="btn normal ok2" @click="onSubmit" v-if="!readonly">提交</a>
            </div>
        </cform>
        <print0 v-if="printVisible" :source="temp" @cancel="printVisible=false"></print0>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import cform from '../../../component/cform'
    import cdatetime from '../../../component/cdatetime'
    import combobox from '../../../component/combobox'
    import parameterController from '../../../../controller/parameter'
    import carController from '../../../../controller/car'
    import print0 from './print0'

    export default {
        components: {panel, panelTitle, cform, cdatetime, combobox, print0},
        props: {
            user: {}
        },
        data() {
            return {
                carStatus: [],
                useTypes: [],
                useTypeId: '',
                estimateStartTime: '',
                estimateEndTime: '',
                driverName: '',
                carId: '',
                cars: [],
                alertTime: '',
                routeStart: '',
                routeMiddle: '',
                routeEnd: '',
                memo: '',
                address: '',
                applicant: {},
                temp: {},
                usedCar: null,
                printVisible: false
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            },
            type() {
                return this.$route.query.type
            },
            app() {
                return this.$route.query.app
            },
            isApp() {
                return !!(this.app || this.temp.carApplicantId)
            },
            readonly() {
                return this.user.permissions.indexOf('car_send-edit') == -1
            }
        },
        watch: {
            estimateStartTime() {
                this.loadCarAndDriver()
            },
            estimateEndTime() {
                this.loadCarAndDriver()
            }
        },
        filters: {
            type(value) {
                if (value === 'direct') {
                    return '直接派车'
                } else if (value === 'other') {
                    return '车辆养护'
                } else {
                    return '派车'
                }
            }
        },
        async mounted() {
            this.carStatus = await parameterController.getCarStatus();
            this.useTypes = await parameterController.getCarUseTypes();
            let app = this.app;
            if (this.id) {
                this.temp = await carController.getCarControl(this.id);
                this.estimateStartTime = this.temp.estimateStartTime;
                this.estimateEndTime = this.temp.estimateEndTime;
                this.useTypeId = this.temp.useTypeId;
                this.driverName = this.temp.driverName;
                this.carId = this.temp.carId;
                this.alertTime = this.temp.alertTime;
                this.routeStart = this.temp.routeStart;
                this.routeMiddle = this.temp.routeMiddle;
                this.routeEnd = this.temp.routeEnd;
                this.address = this.temp.address;
                this.memo = this.temp.memo;
                app = this.temp.carApplicantId
                this.usedCar = await carController.getCarById(this.carId);
                this.usedCar.name = this.usedCar.carTypeName + ' ' + this.usedCar.carNO;
                this.cars = [this.usedCar];
            }
            if (app) {
                this.applicant = await carController.getCarApplicant(app);
                this.estimateStartTime = this.applicant.startTime;
                this.estimateEndTime = this.applicant.endTime;
                this.useTypeId = this.applicant.useTypeId;
            }
        },
        methods: {
            async loadCarAndDriver() {
                if (this.estimateStartTime && this.estimateEndTime) {
                    let params = {
                        startTime: this.estimateStartTime,
                        endTime: this.estimateEndTime
                    };
                    this.cars = (await carController.getStandByCar(params)).map(d => {
                        d.name = d.carTypeName + ' ' + d.carNO;
                        return d
                    });
                } else {
                    this.cars = [];
                }
                if (this.usedCar) {
                    this.cars.unshift(this.usedCar);
                }
            },
            async onSubmit() {
                if (this.isApp) {
                    if (!this.carId) {
                        return this.$$ui.showError('请选择派遣车辆')
                    }
                    if (!this.driverName) {
                        return this.$$ui.showError('请填写派遣司机')
                    }
                    if (!this.alertTime) {
                        return this.$$ui.showError('请输入提醒设置')
                    }
                    let params = {
                        carApplicantId: this.app,
                        carId: this.carId,
                        alertTime: this.alertTime,
                        driverName: this.driverName,
                        memo: this.memo,
                        address: this.address
                    };
                    if (this.id > 0) {
                        params.id = this.id;
                        await carController.updateCarControl(params);
                        this.$$ui.showSuccess('修改派车成功');
                    } else {
                        await carController.addCarControl(params);
                        this.$$ui.showSuccess('提交派车成功');
                    }
                    this.onCancel();
                } else {
                    if (!this.estimateStartTime) {
                        return this.$$ui.showError('请选择用车开始时间')
                    }
                    if (!this.estimateEndTime) {
                        return this.$$ui.showError('请选择用车结束时间')
                    }
                    if (!this.useTypeId) {
                        return this.$$ui.showError('请选择用车类型')
                    }
                    if (!this.carId) {
                        return this.$$ui.showError('请选择派遣车辆')
                    }
                    if (!this.driverName) {
                        return this.$$ui.showError('请填写派遣司机')
                    }
                    if (!this.alertTime) {
                        return this.$$ui.showError('请填写提醒设置')
                    }
                    // if (!this.routeStart || !this.routeMiddle || !this.routeEnd) {
                    //     return this.$$ui.showError('请填写完整行车路线')
                    // }
                    let car = this.cars.find(item => item.id == this.carId);
                    let params = {
                        applicantUserId: this.user.id,
                        estimateStartTime: this.estimateStartTime,
                        estimateEndTime: this.estimateEndTime,
                        carId: this.carId,
                        useTypeId: this.useTypeId,
                        driverName: this.driverName,
                        alertTime: this.alertTime,
                        memo: this.memo,
                        address: this.address,
                        routeStart: this.routeStart,
                        routeMiddle: this.routeMiddle,
                        routeEnd: this.routeEnd,
                        carTypeId: car.carTypeId,
                    };
                    if (this.id > 0) {
                        params.id = this.id;
                        await carController.updateCarControl(params);
                        this.$$ui.showSuccess('修改派车成功');
                    } else {
                        await carController.addCarControl(params);
                        this.$$ui.showSuccess('提交派车成功');
                    }
                    this.onCancel();
                }
            },
            onCancel() {
                this.$router.back()
            }
        }
    }
</script>

<style scoped lang="scss">
    .i3 {
        display: flex;
        align-items: center;

        span {
            margin: 0 4px;
        }

        input {
            min-width: 100px;
            flex: 1;
        }
    }
</style>