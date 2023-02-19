<template>
    <div class="mask">
        <div class="dialog">
            <div class="page" ref="page">
                <div class="p1">
                    <div class="t t1"><span>No.<label>{{source.controlCode}}</label></span></div>
                    <div class="t t2">车辆派遣通知单存根</div>
                    <div class="t t3">
                        <span>车号：{{car.carNO}}</span>
                        <span>车型：{{car.carTypeName}}</span>
                    </div>
                    <div class="t t4">驾驶员：{{source.driverName}}</div>
                    <div class="table">
                        <table border="0">
                            <tr>
                                <td class="d1">用车单位</td>
                                <td class="d2">{{source.applicantUserDepartment}}</td>
                            </tr>
                            <tr>
                                <td class="d1">联 系 人</td>
                                <td class="d2">{{app.leadName}}</td>
                            </tr>
                            <tr>
                                <td class="d1">车辆用途</td>
                                <td class="d2">{{source.useTypeName}}</td>
                            </tr>
                            <tr>
                                <td class="d1">出车时间</td>
                                <td class="d2">{{source.estimateStartTime|datetime}}</td>
                            </tr>
                            <tr>
                                <td class="d1">报到地点</td>
                                <td class="d2">{{source.address}}</td>
                            </tr>
                            <tr>
                                <td class="d1 r2">行驶路线</td>
                                <td class="d2 r2">{{source.routeStart}}<br>{{source.routeMiddle}}<br>{{source.routeEnd}}
                                </td>
                            </tr>
                            <tr>
                                <td class="d1">填 写 人</td>
                                <td class="d2">{{source.applicantUserName}}</td>
                            </tr>
                            <tr>
                                <td class="d1 r2">报批领导</td>
                                <td class="d2">{{source.controlUserName}}</td>
                            </tr>
                            <tr>
                                <td class="d1">备 注</td>
                                <td class="d2"></td>
                            </tr>
                        </table>
                        <div class="ll">第一联<span></span>存根</div>
                    </div>
                    <div class="t t99"><span>年</span><span>月</span><span>日</span></div>
                </div>
                <div class="p2">
                    <div class="t t1"><span>No.<label>{{source.controlCode}}</label></span></div>
                    <div class="t t2">车 辆 派 遣 通 知 单</div>
                    <div class="t t3">
                        <span>车号：{{car.carNO}}</span>
                        <span>车型：{{car.carTypeName}}</span>
                    </div>
                    <div class="t t4">驾驶员：{{source.driverName}}</div>
                    <div class="table">
                        <table border="0">
                            <tr>
                                <td class="d1">用车单位</td>
                                <td class="d2">{{source.applicantUserDepartment}}</td>
                                <td class="d1" rowspan="2">车辆路码<br>表读数</td>
                                <td class="d0">起</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="d1">联 系 人</td>
                                <td class="d2">{{app.leadName}}</td>
                                <td class="d0">止</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="d1">车辆用途</td>
                                <td class="d2">{{source.useTypeName}}</td>
                                <td class="d1" rowspan="2">返回时间</td>
                                <td class="d2" colspan="2"><span>月</span><span>日</span></td>
                            </tr>
                            <tr>
                                <td class="d1">出车时间</td>
                                <td class="d2">{{source.estimateStartTime|datetime}}</td>
                                <td class="d2" colspan="2"><span>时</span><span>分</span></td>
                            </tr>
                            <tr>
                                <td class="d1">报到地点</td>
                                <td class="d2">{{source.address}}</td>
                                <td class="d1" rowspan="3">服<br>务<br>质<br>量</td>
                                <td class="d2" rowspan="3" colspan="2"></td>
                            </tr>
                            <tr>
                                <td class="d1 r2">行驶路线</td>
                                <td class="d2 r2">{{source.routeStart}}<br>{{source.routeMiddle}}<br>{{source.routeEnd}}
                                </td>
                            </tr>
                            <tr>
                                <td class="d1">填 写 人</td>
                                <td class="d2">{{source.applicantUserName}}</td>
                            </tr>
                            <tr>
                                <td class="d1 r2">批准领导</td>
                                <td class="d2">{{source.controlUserName}}</td>
                                <td class="d1">用车人<br>签名</td>
                                <td class="d2" colspan="2"></td>
                            </tr>
                            <tr>
                                <td class="d1">备 注</td>
                                <td class="d2"></td>
                                <td colspan="3"></td>
                            </tr>
                        </table>
                        <div class="ll">第二联<span></span>驾驶员执行任务依据<span></span>返回后上缴车场值班室</div>
                    </div>
                    <div class="t t99"><span>年</span><span>月</span><span>日</span></div>
                </div>
                <div class="p3">
                    <div class="t t1"><span>No.<label>{{source.controlCode}}</label></span></div>
                    <div class="t t2">车辆出营门凭证</div>
                    <div class="t t3">
                        <span>车号：{{car.carNO}}</span>
                        <span>车型：{{car.carTypeName}}</span>
                    </div>
                    <div class="t t4">驾驶员：{{source.driverName}}</div>
                    <div class="table">
                        <table border="0">
                            <tr>
                                <td class="d3 r3">用车单位<br>(或姓名)</td>
                                <td class="d4">{{source.applicantUserDepartment}}</td>
                            </tr>
                            <tr>
                                <td class="d3 r2" rowspan="2">出营门时间</td>
                                <td class="d4"><span>月</span><span>日</span></td>
                            </tr>
                            <tr>
                                <td class="d4"><span>时</span><span>分</span></td>
                            </tr>
                            <tr>
                                <td class="d3 r2" rowspan="2">进营门时间</td>
                                <td class="d4"><span>月</span><span>日</span></td>
                            </tr>
                            <tr>
                                <td class="d4"><span>时</span><span>分</span></td>
                            </tr>
                            <tr>
                                <td class="d3 r3">值班哨兵<br>(填写人)</td>
                                <td class="d4"></td>
                            </tr>
                            <tr>
                                <td class="d3">备 注</td>
                                <td class="d4"></td>
                            </tr>
                        </table>
                        <div class="ll">第三联<span></span>车辆出营门凭证<span></span>每日统一上缴车场值班室</div>
                    </div>
                    <div class="t t99"><span>年</span><span>月</span><span>日</span></div>
                </div>
            </div>
            <div class="tools">
                <a class="btn normal" @click="$emit('cancel')">取消</a>
                <a class="btn normal ok" @click="print">打印</a>
            </div>
        </div>
    </div>
</template>

<script>
    import carController from '../../../../controller/car'

    export default {
        props: {
            source: {}
        },
        data() {
            return {
                car: {},
                app: {leadName: ''}
            }
        },
        async mounted() {
            console.info(this.source);
            this.car = await carController.getCarById(this.source.carId);
            if (this.source.carApplicantId) {
                this.app = await carController.getCarApplicant(this.source.carApplicantId);
            }
        },
        methods: {
            print() {
                this.$print(this.$refs.page)
            }
        }
    }
</script>

<style scoped lang="scss">
    .dialog {
        padding: 20px;
    }

    .page {
        display: flex;

        .t1 {
            font-family: "Times New Roman";
            text-align: center;

            span {
                width: 160px;
                text-align: left;
                display: inline-block;

                label {
                    color: red;
                }
            }
        }

        .t2 {
            line-height: 50px;
            font-weight: bold;
            text-align: center;
        }

        .t3 {
            line-height: 30px;
            display: flex;
            align-items: center;

            > span {
                flex: 1;
            }
        }

        .t4 {
            margin-bottom: 10px;
        }

        .t99 {
            margin-top: 10px;
            padding-right: 30px;
            text-align: right;

            span {
                margin-left: 50px;
            }
        }

        .p1 {
            width: 260px;
        }

        .p2 {
            margin-left: 20px;
            width: 500px;

            .t2 {
                font-size: 18px;
            }
        }

        .p3 {
            margin-left: 20px;
            width: 250px;
        }

        .table {
            display: flex;
            align-items: center;
            font-size: 16px;

            .ll {
                margin-left: 4px;
                width: 12px;
                font-size: 10px;
                word-break: break-all;

                span {
                    display: block;
                    height: 14px;
                }
            }
        }

        table {
            border: 1px solid black;
            border-collapse: collapse;

            td {
                height: 38px;
                font-size: 16px;
                padding: 0 6px;
                border: 1px solid black;

                span {
                    display: inline-block;
                    width: 50%;
                    text-align: right;
                }
            }

            .d0 {
                width: 20px;
            }

            .d1 {
                width: 80px;
                text-align: center;
            }

            .d2 {
                width: 160px;
            }

            .d3 {
                text-align: center;
                width: 100px;
            }

            .d4 {
                width: 120px;
            }

            .r2 {
                height: 76px;
            }

            .r3 {
                height: 114px;
            }
        }
    }

    .tools {
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }
</style>