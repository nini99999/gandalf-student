<template>
    <panel>
        <panel-title>
            <div class="title">部门请假限制</div>
        </panel-title>
        <div class="content">
            <ctable>
                <div class="head row">
                    <span>名称</span>
                    <span>请假限制</span>
                    <span>操作</span>
                </div>
                <div class="content">
                    <div class="row" v-for="(item,i) in data" :key="i">
                        <span>{{item.departmentName}}</span>
                        <span>{{item.limitValue}}%</span>
                        <span><a class="link" @click="onEdit(item)">编辑</a></span>
                    </div>
                </div>
            </ctable>
        </div>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import ctable from '../../../component/ctable'
    import studentController from '../../../../controller/student'

    export default {
        components: {panel, panelTitle, ctable},
        data() {
            return {
                data: []
            }
        },
        async mounted() {
            this.data = await studentController.getDepartmentLimits()
        },
        methods: {
            onEdit(item) {
                this.$router.push({
                    name: 'permission/department-limit-info',
                    params: {
                        id: item.departmentId
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">

</style>