<template>
    <div class="dialog vacation">
        <div class="title">请假事件</div>
        <div class="op">
            <span>申请人姓名</span>
            <span>{{source.creatUserName}}</span>
        </div>
        <div class="op">
            <span>学员</span>
            <span>{{source.studentName}}</span>
        </div>
        <div class="op">
            <span>申请外出时间</span>
            <span>{{source.estimateStartTime|datetime}} - {{source.estimateEndTime|datetime}}</span>
        </div>
        <div class="op">
            <span>外出地点</span>
            <span>{{source.address}}</span>
        </div>
        <div class="op">
            <span>外出事由</span>
            <span>{{source.reason}}</span>
        </div>
        <div class="tools">
            <a class="btn normal" @click="onClose">关闭</a>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            source: {}
        },
        mounted() {
        },
        methods: {
            onClose() {
                this.$emit('close')
            }
        }
    }
</script>

<style scoped lang="scss">
    .vacation {
        width: 562px;
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
                &:first-child {
                    width: 100px;
                    text-align: right;
                    margin-right: 20px;

                    &:after {
                        content: ':';
                    }
                }

                &:last-child {
                    flex: 1;
                }
            }
        }

        .tools {
            margin-top: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
</style>