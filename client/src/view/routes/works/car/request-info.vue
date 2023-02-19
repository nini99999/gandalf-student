<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">用车申请详情</div>
                <a class="btn normal ok2" @click="$router.back()">返回</a>
            </panel-title>
            <cform ref="applicant">
                <div class="option" v-if="id>0">
                    <span class="name">申请单编号</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.applicantCode}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">申请人姓名</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.applicantUserName}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">申请人部门</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.departmentName}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">车辆类型</span>
                    <div class="container">
                        <div class="value">
                            <span class="text">{{source.carTypeName}}</span>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">用途</span>
                    <div class="container">
                        <div class="value">
                            <span class="text">{{source.useTypeName}}</span>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">带车人</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.leadName}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">带车人联系方式</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.leadMobile}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">行车路线</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.routeStart}} - {{source.routeMiddle}} - {{source.routeEnd}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">时间</span>
                    <div class="container">
                        <div class="value">
                            <span class="text">{{source.startTime|datetime}} - {{source.endTime|datetime}}</span>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">派车事由</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.reason}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">车辆数量</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.carCount}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">进度情况</span>
                    <div class="container">
                        <div class="progress">
                            <div v-for="(item,i) in source.carApprovalVOS" :key="i"
                                 :class="{pass:item.status==2,err:item.status==3}">
                                <div class="circle">
                                    <div></div>
                                </div>
                                <div class="aname">{{item.approvalUserName}}</div>
                                <div class="astatus">{{item.status|status}}</div>
                                <div class="more">{{item.opinion}}</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="option" v-if="!isSelf && current && current.status==1">
                    <span class="name">审批意见</span>
                    <div class="container">
                        <div class="value"><textarea class="input" v-model="opinion"></textarea></div>
                    </div>
                </div>
                <div class="option" v-else-if="!isSelf && canSendCar && source.status==2">
                    <span class="name require">拒绝理由</span>
                    <div class="container">
                        <div class="value"><textarea class="input" v-model="refuseReason"></textarea></div>
                    </div>
                </div>
                <div class="option" v-else-if="source.status==6">
                    <span class="name require">拒绝理由</span>
                    <div class="container">
                        <div class="value"><span class="text">{{source.refuseReason}}</span></div>
                    </div>
                </div>
                <div class="more" v-if="source.status==4">
                    <div class="title">派车记录</div>
                    <ctable>
                        <div class="head row">
                            <span>派遣车辆</span>
                            <span>派遣司机</span>
                            <span>路线</span>
                            <span>上车地点</span>
                            <span>说明</span>
                        </div>
                        <div class="content">
                            <div class="row" v-for="(item,i) in control" :key="i">
                                <span>{{item.carNO}}</span>
                                <span>{{item.driverName}}</span>
                                <span>{{item.route}}</span>
                                <span>{{item.address}}</span>
                                <span>{{item.memo}}</span>
                            </div>
                        </div>
                    </ctable>

                </div>
                <div class="no-print">
                    <div class="submit ">
                        <a class="btn normal" @click="onBack">返回</a>
                        <template v-if="isSelf">
                            <a class="btn ok normal" @click="onPrint" v-if="canPrint">打印</a>
                            <a class="btn red normal" @click="onCancel" v-if="source.status!=7">撤销申请</a>
                        </template>
                        <template v-else-if="current && current.status==1">
                            <a class="btn ok normal" @click="onSubmit(2)">同意</a>
                            <a class="btn red normal" @click="onSubmit(3)">拒绝</a>
                        </template>
                        <template v-else-if="canSendCar && source.status==2">
                            <a class="btn ok normal" @click="onSend">派车</a>
                            <a class="btn red normal" @click="onRefused">拒绝派车</a>
                        </template>
                    </div>
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
    import ctable from '../../../component/ctable'
    import carController from '../../../../controller/car'

    export default {
        components: {panel, panelTitle, cform, ctable, cdatetime, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                source: {
                    applicantUserName: '',
                    departmentName: '',
                    carApprovalVOS: []
                },
                control: [],
                opinion: '',
                refuseReason: ''
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            },
            isSelf() {
                return this.user.id == this.source.applicantUserId
            },
            current() {
                return this.source.carApprovalVOS.find(item => item.approvalUserId == this.user.id)
            },
            canSendCar() {
                return this.user.permissions.indexOf('car_send-edit') >= 0
            },
            canPrint() {
                return this.source.status == 2 && Date.now() < this.source.endTime;
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
                    case 6:
                        return '拒绝派车';
                }
            },
        },
        async mounted() {
            this.source = await carController.getCarApplicant(this.id);
            if (this.source.status == 4) {
                let {data} = await carController.getCarControlList({
                    carApplicantId: this.id,
                    pageCount: 0,
                    pageSize: 100
                });
                this.control = data;
            }
        },
        methods: {
            async onSubmit(status) {
                let params = {
                    id: this.current.id,
                    status,
                    opinion: this.opinion
                };
                await carController.carApproval(params);
                this.$$ui.showSuccess('审批完成');
                this.onBack();
            },
            async onCancel() {
                await carController.cancelCarApplicant(this.id);
                this.$$ui.showSuccess('取消成功');
                this.onBack();
            },
            async onRefused() {
                if (!this.refuseReason) {
                    return this.$$ui.showError('请填写拒绝理由');
                }
                let params = {
                    id: this.id,
                    refuseReason: this.refuseReason
                };
                await carController.refuseCarApplicant(params);
                this.$$ui.showSuccess('提交成功');
                this.onBack();
            },
            onSend() {
                this.$router.push({
                    name: 'car-operation-add',
                    query: {
                        app: this.id
                    }
                })
            },
            onBack() {
                this.$router.back()
            },
            onPrint() {
                this.$print(this.$refs.applicant)
            }
        }
    }
</script>

<style scoped lang="scss">
    .progress {
        margin: 10px 0 10px 10px;
        border-left: 1px solid $color-line;

        > div {
            position: relative;
            left: -6px;
            display: flex;
            align-items: flex-start;
            margin-bottom: 42px;

            .circle {
                width: 12px;
                height: 22px;
                padding-top: 2px;
                margin-right: 32px;
                background-color: $color-white;

                > div {
                    display: inline-block;
                    width: 11px;
                    height: 11px;
                    border-radius: 50%;
                    border: 2px solid #D1D8E6;
                }
            }

            .aname {
                width: 120px;
                color: #132240;
            }

            .astatus {
                color: #919CB3;
            }

            .more {
                margin-left: 15px;
                max-width: 600px;
            }

            &:last-child {
                margin: 0;
                background-color: white;
            }
        }

        .pass {
            .circle {
                > div {
                    background-color: #1BBE6B;
                    border: 0;
                }
            }

            .astatus {
                color: #1BBE6B;
            }
        }

        .err {
            .circle {
                > div {
                    background-color: #ed4014;
                    border: 0;
                }
            }

            .astatus {
                color: #ed4014;
            }
        }
    }

    .more {
        padding: 0 100px 0;

        .title {
            font-size: 16px;
            font-weight: bold;
            line-height: 60px;
            border-bottom: $style-border;
        }
    }
</style>