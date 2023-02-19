<template>
    <a class="applicant" @click="$emit('click')">
        <div class="type">{{type== 'car'?'用车申请':'外出申请'}}：</div>
        <div class="who" v-if="type=='car'">{{source.applicantUserName}}</div>
        <div class="who" v-if="type=='vacation'">{{source.studentName}}</div>
        <div class="time" v-if="type=='car'">
            {{source.startTime|datetime}}
        </div>
        <div class="time" v-if="type=='vacation'">
            {{source.estimateStartTime|datetime}}
        </div>
        <div class="blank"></div>
        <div class="status" :class="source.status|color" v-if="type=='car'">{{source.status|status}}</div>
        <div class="status" :class="source.status|color" v-else>{{source.statusName}}</div>
        <div class="icon icon-right"></div>
    </a>
</template>

<script>
    export default {
        props: {
            source: {},
            type: {}
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
                    case 7:
                        return '已撤销';
                }
            },
            color(value) {
                switch (value) {
                    case 0:
                    case 1:
                        return 'orange';
                    case 2:
                    case 4:
                        return 'green';
                    case 3:
                    case 5:
                        return 'red';
                    case 7:
                        return 'off';
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .applicant {
        display: flex;
        align-items: center;
        height: 54px;
        padding: 0 24px;
        margin-bottom: 12px;
        background-color: #F7F9FC;

        .time {
            margin-left: 16px;
            color: $color-tip;
        }

        .blank {
            flex: 1;
        }

        .status {
            padding: 1px 6px;
            border-radius: 100px;
            font-size: 12px;
            border: $style-border;

            &.red {
                color: #ED4013;
                border-color: #ED4013;
                background-color: #FDE3DC;
            }

            &.orange {
                color: #FF9900;
                border-color: #FF9900;
                background-color: #FFF0D9;
            }

            &.green {
                color: #1BBE6B;
                border-color: #1BBE6B;
                background-color: #DDF6E9;
            }

            &.off {
                color: $color-off;
                border-color: $color-off;
            }
        }

        .icon {
            margin-left: 10px;
            color: $color-border;
        }
    }
</style>