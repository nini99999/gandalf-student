<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">车辆外派申请</div>
                <a class="btn normal ok2" @click="$router.back()">返回</a>
            </panel-title>
            <cform>
                <div class="option" v-if="id>0">
                    <span class="name">申请单编号</span>
                    <div class="container">
                        <div class="value">
                            <span class="text">{{source.applicantCode}}</span>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">申请人姓名</span>
                    <div class="container">
                        <div class="value">
                            <span class="text">{{source.applicantUserName}}</span>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">申请人部门</span>
                    <div class="container">
                        <div class="value">
                            <span class="text">{{source.departmentName}}</span>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">车辆类型</span>
                    <div class="container">
                        <div class="value">
                            <combobox class="component" :source="types" v-model="source.carTypeId"></combobox>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">用途</span>
                    <div class="container">
                        <div class="value">
                            <combobox class="component" :source="useTypes" v-model="source.useTypeId"></combobox>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">带车人</span>
                    <div class="container">
                        <div class="value">
                            <input class="input" v-model="source.leadName"/>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">带车人联系方式</span>
                    <div class="container">
                        <div class="value">
                            <input class="input" v-model="source.leadMobile"/>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">行车路线</span>
                    <div class="container">
                        <div class="value">
                            <div class="component i3">
                                <input class="input" v-model="source.routeStart"/>
                                <span>-</span>
                                <input class="input" v-model="source.routeMiddle"/>
                                <span>-</span>
                                <input class="input" v-model="source.routeEnd"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">开始时间</span>
                    <div class="container">
                        <div class="value">
                            <cdatetime class="component" v-model="source.startTime" :limit="true"></cdatetime>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">结束时间</span>
                    <div class="container">
                        <div class="value">
                            <cdatetime class="component" v-model="source.endTime" :limit="true"></cdatetime>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">派车事由</span>
                    <div class="container">
                        <div class="value">
                            <textarea class="input" v-model="source.reason"></textarea>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">车辆数量</span>
                    <div class="container">
                        <div class="value">
                            <input class="input" type="number" v-model="source.carCount"/>
                        </div>
                    </div>
                </div>
                <div class="submit">
                    <a class="btn normal" @click="onCancel">取消</a>
                    <a class="btn ok normal" @click="onSubmit">提交</a>
                </div>
            </cform>
        </panel>
    </div>
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
                yn: [{name: '是', id: '0'}, {name: '否', id: '1'}],
                types: [],
                useTypes: [],
                source: {
                    applicantUserName: '',
                    departmentName: ''
                }
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            }
        },
        filters: {
            status(value) {
                switch (value) {
                    case 0:
                        return '未审批';
                    case 1:
                        return '审批中';
                    case 2:
                        return '已通过';
                    case 3:
                        return '已拒绝';
                    case 4:
                        return '已派车';
                    case 5:
                        return '已超时';
                }
            },
        },
        async mounted() {
            this.types = await parameterController.getCarTypes();
            this.useTypes = await parameterController.getCarUseTypes();
            if (this.id > 0) {
                // this.source = await carController.getCarApplicant(this.id);
            } else {
                this.source.applicantUserName = this.user.realName;
                this.source.departmentName = this.user.departmentName;
            }
        },
        methods: {
            async onSubmit() {
                if (!this.source.carTypeId) {
                    return this.$$ui.showError('请选择车辆类型');
                }
                if (!this.source.useTypeId) {
                    return this.$$ui.showError('请选择用途');
                }
                if (!this.source.leadName) {
                    return this.$$ui.showError('请填写带车人');
                }
                if (!this.source.leadMobile) {
                    return this.$$ui.showError('请填写带车人联系方式');
                }
                this.source.isArea = '0';
                if (!this.source.startTime) {
                    return this.$$ui.showError('请输入开始时间');
                }
                if (this.source.startTime < Date.now()) {
                    return this.$$ui.showError('开始时间不能早于当前时间')
                }
                if (!this.source.endTime) {
                    return this.$$ui.showError('请输入结束时间');
                }
                if (this.source.endTime <= this.source.startTime) {
                    return this.$$ui.showError('结束时间不能早于开始时间')
                }
                if (!this.source.reason) {
                    return this.$$ui.showError('请填写派车事由')
                }
                if (!this.source.carCount) {
                    return this.$$ui.showError('请输入车辆数量');
                }
                if (this.id > 0) {
                    await carController.updateCarApplicant(this.source);
                    this.$$ui.showSuccess('修改申请成功');
                } else {
                    this.source.applicantUserId = this.user.id;
                    await carController.addCarApplicant(this.source);
                    this.$$ui.showSuccess('提交申请成功');
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