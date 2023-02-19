<template>
    <div>
        <audio ref="audio" src="static/alert.wav" width="0" height="0"></audio>
        <div class="mask" v-if="visible">
            <div class="dialog">
                <div class="title">派车提醒</div>
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
                            <div class="text">{{temp.route}}</div>
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
                            <div class="text">{{temp.estimateStartTime|datetime}} -- {{temp.estimateEndTime|datetime}}
                            </div>
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
                <div>
                    <a class="btn ok normal" @click="close">我知道了</a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                temp: {},
                visible: false
            }
        },
        methods: {
            show(item) {
                this.temp = item;
                this.visible = true;
                this.$refs.audio.play().catch(e => {
                    console.error(e)
                })
            },
            close() {
                this.visible = false;
            }
        }
    }
</script>

<style scoped lang="scss">
    .dialog {
        padding: 30px 50px;
        width: 640px;
    }

    .title {
        font-size: 18px;
        font-weight: bold;
        line-height: 40px;
        text-align: center;
    }

    .option {
        display: flex;
        margin: 15px 0;

        .name {
            width: 100px;
        }

        .container {
            flex: 1;
        }
    }

    .btn {
        margin-top: 50px;
    }
</style>