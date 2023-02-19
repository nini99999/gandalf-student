<template>
    <panel>
        <panel-title mode="small">
            <div class="title">
                <slot></slot>
            </div>
            <a class="link" @click="onAll">查看全部</a>
        </panel-title>
        <div class="content">
            <applicant v-for="(item,i) in data" :key="i" :type="type" :source="item"
                       @click="onDetail(item)"></applicant>
            <div class="none" v-if="ready && data.length==0">暂无记录</div>
        </div>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import applicant from './applicant'
    import studentController from '../../../../controller/student'
    import carController from '../../../../controller/car'

    export default {
        components: {panel, panelTitle, applicant},
        props: {
            user: {},
            type: {},
            refresh: {},
        },
        data() {
            return {
                data: [],
                ready: false
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
            async loadVacationRequest() {
                let params = {pageSize: 4, pageCount: 0, creatUserId: this.user.id};
                let {data} = await studentController.getLeaveList(params);
                this.data = data;
                this.ready = true;
            },
            async loadCarRequest() {
                let params = {pageSize: 4, pageCount: 0, applicantUserId: this.user.id};
                let {data} = await carController.getCarApplicants(params);
                this.data = data;
                this.ready = true;
            },
            onDetail(item) {
                if (this.type == 'car') {
                    this.$router.push({
                        name: 'car-request-info',
                        params: {
                            id: item.id
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
                            m: 'myself'
                        }
                    })
                } else if (this.type == 'vacation') {
                    this.$router.push({
                        name: 'vacation',
                        query: {
                            m: 'myself'
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .none {
        text-align: center;
        line-height: 200px;
        color: $color-off;
    }
</style>