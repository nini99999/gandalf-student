<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">部门管理</div>
            </panel-title>
            <div class="content">
                <department-render :source="head" type="head"></department-render>
                <department-render v-for="(item,i) in data" :key="i" :source="item"></department-render>
            </div>
        </panel>
    </div>
</template>

<script>
    import toolbar from '../toolbar'
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import departmentRender from './department-render'
    import personController from '../../../../controller/person'

    export default {
        components: {toolbar, panel, panelTitle, departmentRender},
        data() {
            return {
                head: {
                    id: '序号',
                    name: '名称',
                    code: '编号',
                    memo: '备注'
                },
                data: [],
            }
        },
        async mounted() {
            let item = await personController.getDepartmentList();
            this.data = [item]
        }
    }
</script>

<style scoped lang="scss">

</style>