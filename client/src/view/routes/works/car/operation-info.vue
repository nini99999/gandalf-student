<template>
    <panel>
        <panel-title>
            <div class="title">车辆运营详情</div>
            <a class="btn normal ok2" @click="onCancel">返回</a>
        </panel-title>
        <cfrom>
            <div class="option">
                <span class="name">申请单ID</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.carApplicantId}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">申请人姓名</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.applicantUserName}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">所属部门</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.applicantUserDepartment}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">用车类型</span>
                <div class="container">
                    <div class="value"><span class="text">{{temp.useTypeName}}</span></div>
                </div>
            </div>
            <div class="option">
                <span class="name">派遣车辆</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.carNO}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">派遣司机</span>
                <div class="container">
                    <div class="value">
                        <span class="text">{{temp.driverName}}</span>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">行车路线</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.routeStart}} - {{temp.routeMiddle}} - {{temp.routeEnd}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">上车地点</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.address}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">用车时间</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.estimateStartTime|datetime}} -- {{temp.estimateEndTime|datetime}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">实际开始时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" v-model="temp.startTime" :readonly="over || readonly"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">实际结束时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" v-model="temp.endTime" :readonly="over || readonly"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">行驶里程</span>
                <div class="container">
                    <div class="value">
                        <input class="input" type="number" v-model="temp.mileage" placeholder="请填入公里数"
                               :readonly="over || readonly">
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">备注</span>
                <div class="container">
                    <div class="value">
                        <span class="text">{{temp.memo}}</span>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">状态</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.status|status}}</div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">返回状态</span>
                <div class="container">
                    <div class="value">
                        <div class="text">{{temp.backTypeName}}</div>
                    </div>
                </div>
            </div>
            <div class="submit">
                <a class="btn normal" @click="onCancel">取消</a>
                <a class="btn normal ok2" @click="onSubmit" v-if="!over && !readonly">归队</a>
            </div>
        </cfrom>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import cfrom from '../../../component/cform'
    import cdatetime from '../../../component/cdatetime'
    import carController from '../../../../controller/car'

    export default {
        components: {panel, panelTitle, cfrom, cdatetime},
        props: {
            user: {}
        },
        data() {
            return {
                temp: {}
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            },
            over() {
                return this.temp.status == 1
            },
            readonly() {
                return this.user.permissions.indexOf('car_send-edit') == -1
            }
        },
        filters: {
            status(value) {
                return value == 1 ? '已结束' : '未结束'
            }
        },
        async mounted() {
            this.temp = await carController.getCarControl(this.id);
        },
        methods: {
            onCancel() {
                this.$router.back()
            },
            async onSubmit() {
                if (!this.temp.startTime) {
                    return this.$$ui.showError('请输入实际开始时间');
                }
                if (!this.temp.endTime) {
                    return this.$$ui.showError('请输入实际结束时间');
                }
                if (!this.temp.mileage) {
                    return this.$$ui.showError('请输入行驶里程');
                }
                let params = {
                    id: this.id,
                    startTime: this.temp.startTime,
                    endTime: this.temp.endTime,
                    mileage: this.temp.mileage
                };
                await carController.carReturn(params);
                this.$$ui.showSuccess('提交归队成功');
                this.onCancel();
            }
        }
    }
</script>

<style scoped lang="scss">

</style>