<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">
                    车辆信息维护
                </div>
                <a class="btn normal" @click="onAdd" v-if="!readonly">添加新车辆</a>
            </panel-title>
            <div class="content">
                <panel-search>
                    <combobox :source="carTypes" v-model="carTypeId" placeholder="请选择类型..."></combobox>
                    <combobox :source="carStatus" v-model="carStatusId" placeholder="请选择状态..."></combobox>
                    <input class="input" v-model="carNO" placeholder="请输入车牌号...">
                    <a class="btn normal ok2" @click="onSearch">搜索</a>
                </panel-search>
                <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                    <div class="head row">
                        <span>编号</span>
                        <span>车牌号</span>
                        <span>当前状态</span>
                        <span>车辆类型</span>
                        <span>所属部门</span>
                        <span>相应配件</span>
                        <span>操作</span>
                    </div>
                    <div class="content">
                        <div class="row" v-for="(item,i) in data" :key="i">
                            <span>{{item.id}}</span>
                            <span>{{item.carNO}}</span>
                            <span>{{item.statusName}}</span>
                            <span>{{item.carTypeName}}</span>
                            <span>{{item.departmentName}}</span>
                            <span></span>
                            <span><a @click="onEdit(item)">{{readonly?'查看':'编辑'}}</a></span>
                        </div>
                    </div>
                </ctable>
            </div>
        </panel>
    </div>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import panelSearch from '../panel-search'
    import ctable from '../../../component/ctable'
    import combobox from '../../../component/combobox'
    import carController from '../../../../controller/car'
    import parameterController from '../../../../controller/parameter'

    export default {
        components: {panel, panelTitle, panelSearch, ctable, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                carTypes: [{name: '所有类型', id: 0}],
                carStatus: [{name: '所有状态', id: 0}],
                carTypeId: 0,
                carStatusId: 0,
                carNO: '',
                pageCount: 0,
                pageSize: 20,
                dataCount: 0,
                data: [],
                ready: false
            }
        },
        computed: {
            readonly() {
                return this.user.permissions.indexOf('car-edit') == -1
            }
        },
        watch: {
            carTypeId() {
                this.loadPage()
            },
            carStatusId() {
                this.loadPage()
            }
        },
        async mounted() {
            let carTypes = await parameterController.getCarTypes();
            this.carTypes.push(...carTypes);
            let carStatus = await parameterController.getCarStatus();
            this.carStatus.push(...carStatus);
            this.loadPage()
        },
        methods: {
            async loadPage(pageCount = 0) {
                let params = {pageCount, pageSize: this.pageSize};
                if (this.carTypeId > 0) {
                    params.carTypeId = this.carTypeId;
                }
                if (this.carStatusId > 0) {
                    params.statusId = this.carStatusId;
                }
                if (this.carNO) {
                    params.carNO = this.carNO;
                }
                let {data, dataCount} = await carController.getCarList(params);
                this.data = data;
                this.ready = true;
                this.dataCount = dataCount;
                this.pageCount = pageCount;
            },
            onSearch() {
                this.loadPage()
            },
            onAdd() {
                this.$router.push({
                    name: 'car-info',
                    params: {
                        id: '0'
                    }
                })
            },
            onEdit(item) {
                this.$router.push({
                    name: 'car-info',
                    params: {
                        id: item.id
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">

</style>