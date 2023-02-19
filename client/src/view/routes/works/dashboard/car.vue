<template>
    <div class="dialog car">
        <div class="title">用车事件</div>
        <div class="op">
            <span>申请单编号</span>
            <span>{{source.applicantCode}}</span>
            <span>申请人部门</span>
            <span>{{source.departmentName}}</span>
        </div>
        <div class="op">
            <span>申请人姓名</span>
            <span>{{source.applicantUserName}}</span>
            <span>带车人</span>
            <span>{{source.leadName}}</span>
        </div>
        <div class="op">
            <span>车辆类型</span>
            <span>{{source.carTypeName}}</span>
            <span>用途</span>
            <span>{{source.useTypeName}}</span>
        </div>
        <div class="op">
            <span>行车路线</span>
            <span>{{source.route}}</span>
        </div>
        <div class="op">
            <span>时间</span>
            <span>{{source.startTime|datetime}} - {{source.endTime|datetime}}</span>
            <span>车辆数量</span>
            <span>{{source.carCount}}</span>
        </div>
        <div class="op">
            <span>派车事由</span>
            <span>{{source.reason}}</span>
        </div>
        <div class="op">
            <span>进度情况</span>
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
        <div class="tools">
            <a class="btn normal" @click="onClose">关闭</a>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            user: {},
            source: {}
        },
        mounted() {
            console.info(this.source)
        },
        methods: {
            onClose() {
                this.$emit('close')
            }
        }
    }
</script>

<style scoped lang="scss">
    .car {
        width: 900px;
        padding: 30px 40px;

        .title {
            font-size: 16px;
            color: $color-title;
            font-weight: bold;
            margin-bottom: 40px;
        }

        .op {
            display: flex;
            margin: 20px 0;

            > span {
                &:nth-child(2n+1) {
                    width: 100px;
                    text-align: right;
                    margin-right: 20px;

                    &:after {
                        content: ':';
                    }
                }

                &:nth-child(2n) {
                    flex: 1;
                }
            }

            .container {
                flex: 1;
            }
        }

        .tools {
            margin-top: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

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
    }
</style>