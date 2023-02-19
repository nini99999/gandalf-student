<template>
    <div>
        <div class="panels" v-if="!readonly">
            <panel>
                <div class="options">
                    <a @click="onWaitFor">待派车<span class="badge" v-if="waitCount>0">{{waitCount}}</span></a>
                    <a @click="onRepair">车辆养护</a>
                </div>
            </panel>
            <panel>
                <a class="btn normal ok2" @click="onAdd('direct')">直接派车</a>
            </panel>
        </div>
        <panel>
            <panel-title>
                <div class="title tabs">
                    <a class="btn normal" :class="{ok:tab==0}" @click="tab=0">派遣管理</a>
                    <a class="btn normal" :class="{ok:tab==1}" @click="tab=1">运营管理</a>
                </div>
            </panel-title>
            <div class="content" v-if="tab==0">
                <panel-search>
                    <combobox :source="types" v-model="useTypeId" placeholder="请选择状态..."></combobox>
                    <input class="input" v-model="carNO" placeholder="请输入车牌号...">
                    <input class="input" v-model="driverName" placeholder="请输入司机姓名...">
                    <div class="one">
                        <cdate class="startTime" v-model="startTime" placeholder="起始时间"></cdate>
                        到
                        <cdate class="endTime" v-model="endTime" placeholder="终止时间"></cdate>
                    </div>
                    <a class="btn normal ok2" @click="onSearch">搜索</a>
                    <a class="btn normal ok2" @click="onExcel">导出</a>
                </panel-search>
                <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                    <div class="head row">
                        <span class="my">申请时间</span>
                        <span>车辆牌照</span>
                        <span class="my">外出时间</span>
                        <span class="my">返回时间</span>
                        <span>外出类型</span>
                        <span>当前状态</span>
                        <span>司机</span>
                        <span>操作</span>
                    </div>
                    <div class="content">
                        <div class="row" v-for="(item,i) in data" :key="i">
                            <span class="my">{{item.estimateStartTime|date}}</span>
                            <span>{{item.carNO}}</span>
                            <span class="my">{{item.startTime|datetime}}</span>
                            <span class="my">{{item.endTime|datetime}}</span>
                            <span>{{item.useTypeName}}</span>
                            <span :class="{err:item.backTypeName=='超时返回'}">{{item.backTypeName}}</span>
                            <span>{{item.driverName}}</span>
                            <span>
                                <a @click="onEdit(item)" v-if="!readonly">编辑</a>
                                <a @click="onOver(item)" v-if="item.status!=1">{{readonly ?'查看':'归队'}}</a>
                                <a @click="print=item">打印</a>
                        </span>
                        </div>
                    </div>
                </ctable>
            </div>
            <div class="content" v-if="tab==1">
                <panel-search>
                    <combobox :source="status" v-model="carStatus" placeholder="请选择状态..."></combobox>
                    <input class="input" v-model="carNO" placeholder="请输入车牌号...">
                    <a class="btn normal ok2" @click="onSearch">搜索</a>
                </panel-search>
                <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                    <div class="head row">
                        <span>车辆牌照</span>
                        <span class="my">外出时间</span>
                        <span class="my">返回时间</span>
                        <span>用车类型</span>
                        <span>当前状态</span>
                        <span>司机</span>
                        <span>操作</span>
                    </div>
                    <div class="content">
                        <div class="row" v-for="(item,i) in data" :key="i">
                            <span>{{item.carNO}}</span>
                            <span class="my">{{item.startTime|datetime}}</span>
                            <span class="my">{{item.endTime|datetime}}</span>
                            <span>{{item.typeName}}</span>
                            <span :class="{err:item.backTypeName=='超时返回'}">{{item.backTypeName}}</span>
                            <span>{{item.driverName}}</span>
                            <span>
                                <a @click="onView(item)">查看</a>
                        </span>
                        </div>
                    </div>
                </ctable>
            </div>
        </panel>
        <print0 v-if="print" :source="print" @cancel="print=null"></print0>
    </div>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import panelSearch from '../panel-search'
    import ctable from '../../../component/ctable'
    import combobox from '../../../component/combobox'
    import cdate from '../../../component/cdate'
    import carController from '../../../../controller/car'
    import parameterController from '../../../../controller/parameter'
    import print0 from './print0'

    export default {
        components: {panel, print0, panelTitle, panelSearch, ctable, cdate, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                waitCount: 0,
                types: [{name: '所有类型'}],
                status: [{name: '所有状态'}],
                useTypeId: undefined,
                carStatus: undefined,
                carNO: '',
                driverName: '',
                startTime: '',
                endTime: '',
                pageCount: 0,
                pageSize: 10,
                dataCount: 0,
                data: [],
                ready: false,
                tab: 0,
                print: null
            }
        },
        computed: {
            readonly() {
                return this.user.permissions.indexOf('car_send-edit') == -1
            }
        },
        watch: {
            useTypeId() {
                this.loadPage()
            },
            carStatus() {
                this.loadPage()
            },
            tab() {
                this.loadPage()
            }
        },
        async mounted() {
            let {dataCount} = await carController.getCarApplicants({pageSize: 1, pageCount: 0, status: 2});
            this.waitCount = dataCount;
            let types = await parameterController.getCarUseTypes();
            this.types.push(...types);
            let status = await parameterController.getCarStatus();
            this.status.push(...status);
            this.loadPage()
        },
        methods: {
            getParams(params) {
                if (this.useTypeId > 0) {
                    params.useTypeId = this.useTypeId;
                }
                if (this.carStatus > 0) {
                    params.typeId = this.carStatus;
                }
                if (this.carNO) {
                    params.carNO = this.carNO;
                }
                if (this.driverName) {
                    params.driverName = this.driverName;
                }
                if (this.startTime) {
                    params.startMinTime = this.startTime;
                }
                if (this.endTime) {
                    params.endMaxTime = this.endTime;
                }
                return params;
            },
            async loadPage(pageCount = 0) {
                let params = this.getParams({pageCount, pageSize: this.pageSize});
                if (this.tab == 0) {
                    let {data, dataCount} = await carController.getCarControlList(params);
                    this.data = data;
                    this.dataCount = dataCount;
                    this.pageCount = pageCount;
                    this.ready = true;
                } else {
                    let {data, dataCount} = await carController.getCarOperationList(params);
                    this.data = data;
                    this.dataCount = dataCount;
                    this.pageCount = pageCount;
                    this.ready = true;
                }
            },
            onSearch() {
                this.loadPage();
            },
            onWaitFor() {
                this.$router.push({
                    name: 'car-request-list',
                    query: {
                        m: 'wait'
                    }
                })
            },
            onAdd(type) {
                this.$router.push({
                    name: 'car-operation-add',
                    query: {
                        type
                    }
                })
            },
            onRepair() {
                this.$router.push({
                    name: 'car-repair'
                })
            },
            onEdit(item) {
                this.$router.push({
                    name: 'car-operation-add',
                    params: {
                        id: item.id
                    }
                })
            },
            onOver(item) {
                this.$router.push({
                    name: 'car-operation-info',
                    params: {
                        id: item.id
                    }
                });
            },
            async onExcel() {
                let params = this.getParams({});
                await carController.getCarControlListExcel(params);
            },
            onView(item) {
                this.$router.push({
                    name: 'car-repair',
                    params: {
                        id: item.id
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">
    .panels {
        display: flex;
        align-items: center;
        margin-bottom: 24px;

        .panel {
            height: 160px;

            &:first-child {
                flex: 2;
                padding: 32px;
            }

            &:last-child {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-left: 24px;

                .btn {
                    width: 50%;
                }
            }
        }

        .options {
            display: flex;
            align-items: center;
            height: 100%;

            > a {
                height: 100%;
                flex: 1;
                background-color: #F7F9FC;
                font-size: 16px;
                font-weight: bold;
                padding-left: 40px;
                padding-top: 36px;
                background-size: 160px 96px;
                background-repeat: no-repeat;
                background-position: right bottom;


                &:first-child {
                    background-image: url("./assets/1.png");
                }

                &:last-child {
                    background-image: url("./assets/2.png");
                    margin-left: 24px;
                }
            }
        }
    }

    .tabs {
        display: flex;
    }

    .one {
        display: flex;
        align-items: center;
    }

    .startTime {
        margin-right: 10px !important;
    }

    .endTime {
        margin-left: 10px !important;
    }

    .my {
        width: 150px !important;
        flex: 0 1 auto !important;
    }

    .err {
        color: $color-red;
    }
</style>