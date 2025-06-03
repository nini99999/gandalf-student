<template>
    <panel>
        <panel-title>
            <div class="title" v-if="isMyself">我的申请</div>
            <div class="title" v-else>请销假管理</div>
        </panel-title>
        <div class="content">
            <panel-search>
                <department-combobox v-model="departmentId" :all="true" v-if="!isMyself"></department-combobox>
                <combobox :source="status" v-model="statusId"></combobox>
                <div class="one">
                    <cdate class="startTime" v-model="startTime" placeholder="起始时间"></cdate>
                    到
                    <cdate class="endTime" v-model="endTime" placeholder="终止时间"></cdate>
                </div>
                <a class="btn normal ok2" @click="onSearch">搜索</a>
                <a class="btn normal ok2" @click="onExcel" v-if="!isMyself">导出</a>
            </panel-search>
            <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                <div class="head row">
                    <span>编号</span>
                    <span>姓名</span>
                    <span>外出时间</span>
                    <span>返回时间</span>
                    <span>状态</span>
                    <span>所属单位</span>
                    <span>操作</span>
                </div>
                <div class="content">
                    <div class="row" v-for="(item,i) in data" :key="i">
                        <span>{{item.id}}</span>
                        <span>{{item.studentName}}</span>
                        <span>{{item.estimateStartTime|datetime}}</span>
                        <span>{{item.estimateEndTime|datetime}}</span>
                        <span :class="{err:item.statusName=='超时未归'}">{{item.statusName}}</span>
                        <span>{{item.department}}</span>
                        <span>
                            <a @click="onEdit(item)">{{readonly?'查看':'编辑'}}</a>
                            <a @click="onRecord(item)">出入记录</a>
                            <a @click="onDelete(item.id)"> 删除</a>    
                        </span>
                    </div>
                </div>
            </ctable>
        </div>
        <div class="mask" v-if="current">
            <div class="dialog">
                <div class="title">
                    <span>出入记录: 请假单编号 {{current.id}}</span>
                    <a class="icon icon-close" @click="current=false"></a>
                </div>
                <ctable class="container" :ready="true" :total="records.length">
                    <div class="head row">
                        <span>出入时间</span>
                        <span>出入大门</span>
                        <span>类型</span>
                        <span>刷卡结果</span>
                    </div>
                    <div class="content" v-if="records.length>0">
                        <div class="row" v-for="(item,i) in records" :key="i">
                            <span>{{item.viaTime|datetime}}</span>
                            <span>{{item.gateInfo}}</span>
                            <span>{{item.viaType==0?'出':'入'}}</span>
                            <span>{{item.viaResult==0?'成功':'失败'}}</span>
                        </div>
                    </div>
                </ctable>
                  <div >
                 <a class="btn normal ok2" @click="current=false">关闭</a>
                </div>
            </div>
        </div>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import panelSearch from '../panel-search'
    import ctable from '../../../component/ctable'
    import combobox from '../../../component/combobox'
    import cdate from '../../../component/cdate'
    import DepartmentCombobox from '../combobox-department'
    import studentController, {status} from '../../../../controller/student'

    export default {
        components: {panelTitle, panelSearch, panel, ctable, cdate, combobox, DepartmentCombobox},
        props: {
            user: {}
        },
        data() {
            return {
                departmentId: undefined,
                statusId: undefined,
                startTime: '',
                endTime: '',
                status: [{name: '请选择状态查询'}, ...status],
                data: [],
                dataCount: 0,
                pageSize: 20,
                pageCount: 0,
                ready: false,
                current: false,
                records: []
            }
        },
        computed: {
            mode() {
                return this.$route.query.m
            },
            isMyself() {
                return this.mode == 'myself'
            },
            hasPermission() {
                return this.user.permissions.indexOf('vacation') >= 0
            },
            readonly() {
                return this.user.permissions.indexOf('vacation-edit') == -1
            }
        },
        watch: {
            departmentId() {
                this.loadPage()
            },
            statusId() {
                this.loadPage()
            }
        },
        mounted() {
            this.loadPage()
        },
        methods: {
            getParams(params) {
                if (this.departmentId > 0) {
                    params.departmentId = this.departmentId;
                }
                if (this.statusId != undefined) {
                    params.status = this.statusId;
                }
                if (this.startTime) {
                    params.queryStartTime = this.startTime;
                }
                if (this.endTime) {
                    params.queryEndTime = this.endTime;
                }
                return params;
            },
            async loadPage(pageCount = 0) {
                let params = this.getParams({pageCount, pageSize: this.pageSize});
                if (this.isMyself) {
                    params.creatUserId = this.user.id;
                } else {
                    if (!this.hasPermission) {
                        return;
                    }
                }
                let {data, dataCount} = await studentController.getLeaveList(params);
                this.data = data;
                this.ready = true;
                this.dataCount = dataCount;
                this.pageCount = pageCount;
            },
            onSearch() {
                this.loadPage()
            },
            onEdit(item) {
                this.$router.push({
                    name: 'vacation-info',
                    params: {
                        id: item.id
                    }
                })
            },
        onDelete(id) {
                this.$confirm('确认要删除吗？')
                .then(async() => {
            let params = {id:id};
            await  studentController.deleteLevel(params);
                this.$$ui.showSuccess('删除成功');
                this.loadPage();
        })      .catch(() => {
        })
               
            },
            async onRecord(item) {
                this.records = await studentController.getLeaveViaList(item.id);
                this.current = item;
            },
            async onExcel() {
                let params = this.getParams({});
                await studentController.exportVacations(params)
            }
        }
    }
</script>

<style scoped lang="scss">
    .err {
        color: $color-red;
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

    .dialog {
        width: 800px;
        height: 600px;
        display: flex;
        flex-direction: column;

        .title {
            display: flex;
            align-items: center;
            justify-content: space-between;
            height: 60px;
            padding: 0 20px;
            font-size: 18px;
            border-bottom: $style-border;

            a {
                font-size: 24px;
                color: $color-off;
            }
        }
        .container{
            flex: 1;
            height: 100%;
            overflow: hidden;
            .content{
                overflow: auto;
            }
        }
    }

</style>