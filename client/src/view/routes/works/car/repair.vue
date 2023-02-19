<template>
    <panel>
        <panel-title>
            <div class="title">车辆养护</div>
        </panel-title>
        <cform>
            <div class="option">
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
                        <cdatetime class="component" v-model="startTime" :limit="true"
                                   :readonly="readonly"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">用车结束时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" v-model="endTime" :limit="true"
                                   :readonly="readonly"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">用车类型</span>
                <div class="container">
                    <div class="value">
                        <combobox class="component" v-model="useTypeId" :source="carStatus"
                                  :readonly="readonly"></combobox>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">养护车辆</span>
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
            <div class="submit">
                <a class="btn normal" @click="onCancel">取消</a>
                <a class="btn normal ok2" @click="onSubmit" v-if="!readonly">提交</a>
            </div>
        </cform>
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

    export default {
        components: {panel, panelTitle, cform, cdatetime, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                carStatus: [],
                useTypeId: '',
                startTime: '',
                endTime: '',
                driverName: '',
                carId: '',
                cars: [],
                temp: {}
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            },
            readonly() {
                return this.id > 0
            }
        },
        watch: {
            startTime() {
                this.loadCarAndDriver()
            },
            endTime() {
                this.loadCarAndDriver()
            }
        },
        async mounted() {
            this.carStatus = await parameterController.getCarStatus();
            if (this.id) {
                this.temp = await carController.getCarOperation(this.id);
                this.startTime = this.temp.startTime;
                this.endTime = this.temp.endTime;
                this.useTypeId = this.temp.typeId;
                this.driverName = this.temp.driverName;
                this.carId = this.temp.carId;
            }
        },
        methods: {
            async loadCarAndDriver() {
                if (this.startTime && this.endTime) {
                    let params = {
                        startTime: this.startTime,
                        endTime: this.endTime
                    };
                    this.cars = (await carController.getStandByCar(params)).map(d => {
                        d.name = d.carTypeName + ' ' + d.carNO;
                        return d
                    });
                } else {
                    this.carId = '';
                    this.cars = [];
                }
            },
            async onSubmit() {
                if (!this.startTime) {
                    return this.$$ui.showError('请选择用车开始时间')
                }
                if (!this.endTime) {
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
                let params = {
                    startTime: this.startTime,
                    endTime: this.endTime,
                    carId: this.carId,
                    typeId: this.useTypeId,
                    driverName: this.driverName,
                };
                if (this.id > 0) {
                } else {
                    await carController.addCarOperation(params);
                    this.$$ui.showSuccess('提交车辆养护成功');
                }
                this.onCancel();
            },
            onCancel() {
                this.$router.back()
            }
        }
    }
</script>

<style scoped lang="scss">

</style>