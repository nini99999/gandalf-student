<template>
    <panel>
        <panel-title>
            <div class="title" v-if="isMyself">我的申请</div>
            <div class="title" v-else-if="isWaitFor">待派车申请</div>
            <div class="title" v-else-if="isValid">待审批申请</div>
            <div class="title" v-else>用车申请管理</div>
        </panel-title>
        <div class="content">
            <panel-search v-if="!isWaitFor">
                <combobox :source="statuses" v-model="status"></combobox>
            </panel-search>
            <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                <div class="head row">
                    <span class="custom p0">申请单号</span>
                    <span>申请人</span>
                    <span>所属部门</span>
                    <span>用车类型</span>
                    <span>车辆类型</span>
                    <span>用车时间</span>
                    <span v-if="!isWaitFor">状态</span>
                    <span class="custom p1" v-if="isWaitFor">操作</span>
                    <span v-else>操作</span>
                </div>
                <div class="content">
                    <div class="row" v-for="(item,i) in data" :key="i">
                        <span class="custom p0">{{item.applicantCode}}</span>
                        <span>{{item.applicantUserName}}</span>
                        <span>{{item.departmentName}}</span>
                        <span>{{item.useTypeName}}</span>
                        <span>{{item.carTypeName}}</span>
                        <span>{{item.startTime|date}}</span>
                        <span v-if="!isWaitFor">{{item.status|status}}</span>
                        <span v-if="isMyself"><a @click="onEdit(item)">编辑</a></span>
                        <span class="custom p1" v-else-if="isWaitFor">
                            <a @click="onAddOperation(item)">派车</a>
                            <a @click="onEdit(item)">拒绝</a>
                        </span>
                        <span v-else-if="isValid"><a @click="onAppro(item)">审批</a></span>
                        <span v-else><a @click="onEdit(item)">查看</a></span>
                    </div>
                </div>
            </ctable>
        </div>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import panelSearch from '../panel-search'
    import ctable from '../../../component/ctable'
    import combobox from '../../../component/combobox'
    import carController, {status} from '../../../../controller/car'

    export default {
        components: {panel, panelTitle, panelSearch, ctable, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                statuses: status,
                status: undefined,
                pageCount: 0,
                pageSize: 10,
                dataCount: 0,
                data: [],
                ready: false
            }
        },
        computed: {
            mode() {
                return this.$route.query.m
            },
            isMyself() {
                return this.mode == 'myself'
            },
            isValid() {
                return this.mode == 'valid'
            },
            isWaitFor() {
                return this.mode == 'wait'
            }
        },
        filters: {
            status(value) {
                let item = status.find(d => d.id == value)
                if (item) {
                    return item.name
                } else {
                    return value
                }
            }
        },
        watch: {
            status() {
                this.loadPage()
            },
            mode() {
                this.loadPage()
            }
        },
        async mounted() {
            this.loadPage();
        },
        methods: {
            async loadPage(pageCount = 0) {
                let params = {pageCount, pageSize: this.pageSize};
                if (this.status != undefined) {
                    params.status = this.status;
                }
                if (this.isMyself) {
                    params.applicantUserId = this.user.id;
                }
                if (this.isWaitFor) {
                    params.status = 2;
                }
                if (this.isValid) {
                    this.data = await carController.getVALIDCarApprovalList(this.user.id);
                    this.data = this.data.map(item => {
                        item.carApplicant.status = item.status;
                        return item.carApplicant
                    });
                    this.dataCount = this.data.length;
                    this.pageSize = this.dataCount;
                    this.pageCount = 0;
                } else {
                    let {data, dataCount} = await carController.getCarApplicants(params);
                    this.data = data;
                    this.dataCount = dataCount;
                    this.pageCount = pageCount;
                }
                this.ready = true;
            },
            onSearch() {
                this.loadPage()
            },
            onEdit(item) {
                this.$router.push({
                    name: 'car-request-info',
                    params: {
                        id: item.id
                    }
                })
            },
            onAppro(item) {
                this.$router.push({
                    name: 'car-request-info',
                    params: {
                        id: item.id
                    }
                })
            },
            onAddOperation(item) {
                this.$router.push({
                    name: 'car-operation-add',
                    query: {
                        app: item.id
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">
    .p0 {
        width: 150px;
    }

    .p1 {
        width: 150px;
    }
</style>