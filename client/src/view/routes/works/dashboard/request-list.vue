<template>
    <panel>
        <panel-title mode="small">
            <div class="title">
                <slot></slot>
                <span class="badge" v-if="total>0">{{total}}</span></div>
            <a class="link" @click="onAll">查看全部</a>
        </panel-title>
        <div class="list">
            <a class="item" v-for="(item,i) in data" :key="i" @click="onDetail(item)">
                <div>
                    <span v-if="type=='car'">用车事件：{{item.carApplicant.applicantUserName}}</span>
                    <span v-if="type=='vacation'">请假事件：{{item.studentName}}</span>
                    <span class="time" v-if="type=='car'"></span>
                    <span class="time" v-if="type=='vacation'">{{item.estimateStartTime|datetime}}</span>
                </div>
                <div class="blank"></div>
                <div class="status" :class="item.status|color" v-if="type=='car'">{{item.status|status}}</div>
                <div v-if="type=='vacation'"></div>
                <div class="icon icon-right"></div>
            </a>
            <div class="none" v-if="ready && data.length==0">暂无记录</div>
        </div>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import carController from '../../../../controller/car'
    import studentController from '../../../../controller/student'

    export default {
        components: {panel, panelTitle},
        props: {
            type: {},
            user: {},
            refresh: {},
        },
        data() {
            return {
                ready: false,
                data: [],
                total: 0
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
                }
            }
        },
        watch: {
            refresh() {
                this.doRefresh()
            }
        },
        mounted() {
            this.doRefresh()
        },
        methods: {
            doRefresh() {
                if (this.type == 'car') {
                    this.loadCarRequest()
                } else {
                    this.loadVacationRequest()
                }
            },
            async loadCarRequest() {
                this.data = await carController.getVALIDCarApprovalList(this.user.id);
                this.ready = true;
            },
            async loadVacationRequest() {
                let params = {pageSize: 4, pageCount: 0};
                let {data} = await studentController.getLeaveList(params);
                this.data = data;
                this.ready = true;
            },
            onDetail(item) {
                if (this.type == 'car') {
                    this.$router.push({
                        name: 'car-request-info',
                        params: {
                            id: item.carApplicantId
                        }
                    })
                } else if (this.type == 'vacation') {
                    this.$router.push({
                        name: 'vacation-info',
                        params: {
                            id: item.id
                        }
                    })
                }
            },
            onAll() {
                if (this.type == 'car') {
                    this.$router.push({
                        name: 'car-request-list',
                        query: {
                            m: 'valid'
                        }
                    })
                } else if (this.type == 'vacation') {
                    this.$router.push({
                        name: 'vacation',
                        query: {
                            m: 'valid'
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .list {
        padding: 0 32px;
    }

    .item {
        padding: 0 24px;
        margin-bottom: 12px;
        height: 54px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        background-color: #F7F9FC;

        .time {
            color: #808695;
            margin-left: 16px;
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
        }

        .icon {
            margin-left: 10px;
            font-size: 14px;
            color: #D1D8E6;
        }
    }

    .none {
        text-align: center;
        line-height: 200px;
        color: $color-off;
    }
</style>