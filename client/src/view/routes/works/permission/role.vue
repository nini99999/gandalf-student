<template>
    <panel>
        <panel-title>
            <div class="title">角色管理</div>
            <a class="btn normal" @click="onReset">重置出厂设置</a>
            <a class="btn normal" @click="onView">导出配置信息</a>
        </panel-title>
        <div class="content">
            <ctable>
                <div class="head row">
                    <span>ID</span>
                    <span>名称</span>
                    <span>值</span>
                    <span>操作</span>
                </div>
                <div class="content">
                    <div class="row" v-for="(item,i) in data" :key="i">
                        <span>{{item.id}}</span>
                        <span>{{item.name}}</span>
                        <span>{{item.memo}}</span>
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
    import personController from '../../../../controller/person'
    import {mgr} from '../../../../model/permission'

    export default {
        components: {panel, panelTitle, ctable},
        data() {
            return {
                data: []
            }
        },
        async mounted() {
            this.data = await personController.getRoleList();
        },
        methods: {
            onEdit(item) {
                this.$router.push({
                    name: 'permission/role-info',
                    params: {
                        key: item.memo
                    },
                    query: {
                        name: item.name
                    }
                })
            },
            onReset() {
                mgr.reset();
                this.$$ui.showSuccess('重置成功')
            },
            onView() {
                let text = JSON.stringify(mgr.getAll());
                const blob = new Blob([text]);
                const link = document.createElement('a');
                link.download = 'permission.json';
                link.style.display = 'none';
                link.href = URL.createObjectURL(blob);
                document.body.appendChild(link);
                link.click();
                URL.revokeObjectURL(link.href);
                document.body.removeChild(link);
            }
        }
    }
</script>

<style scoped lang="scss">

</style>