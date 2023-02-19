<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">人员管理</div>
                <a class="btn normal" @click="onEdit(0)">添加人员</a>
            </panel-title>
            <div class="content">
                <panel-search>
                    <role-combobox v-model="roleId" :all="true"></role-combobox>
                    <input class="input" v-model="realName" placeholder="请输入姓名">
                    <a class="btn normal ok2" @click="onSearch">搜索</a>
                </panel-search>
                <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                    <div class="head row">
                        <span class="custom tiny">编号</span>
                        <span>登录名</span>
                        <span>姓名</span>
                        <span>教职工编号</span>
                        <span>职务</span>
                        <span>所属部门</span>
                        <span class="custom">操作</span>
                    </div>
                    <div class="content">
                        <div class="row" v-for="(item,i) in data" :key="i">
                            <span class="custom tiny">{{item.id}}</span>
                            <span>{{item.userName}}</span>
                            <span>{{item.realName}}</span>
                            <span>{{item.code}}</span>
                            <span>{{item.roleName}}</span>
                            <span>{{item.departmentName}}</span>
                            <span class="custom">
                                <a @click="onEdit(item.id)">编辑</a>
                                <a @click="onReset(item.id)">重置密码</a>
                            </span>
                        </div>
                    </div>
                </ctable>
            </div>
        </panel>
    </div>
</template>

<script>
    import toolbar from '../toolbar'
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import panelSearch from '../panel-search'
    import ctable from '../../../component/ctable'
    import roleCombobox from '../combobox-role'
    import personController from '../../../../controller/person'

    export default {
        components: {toolbar, panel, panelTitle, panelSearch, ctable, roleCombobox},
        data() {
            return {
                realName: '',
                roleId: undefined,
                data: [],
                dataCount: 0,
                pageSize: 20,
                pageCount: 0,
                ready: false
            }
        },
        watch: {
            roleId() {
                this.loadPage();
            }
        },
        async mounted() {
            this.loadPage();
        },
        methods: {
            async loadPage(pageCount = 0) {
                let params = {pageCount, pageSize: this.pageSize};
                if (this.realName) {
                    params.realName = this.realName;
                }
                if (this.roleId) {
                    params.roleId = this.roleId
                }
                let {data, dataCount} = await personController.getUserList(params);
                this.data = data;
                this.ready = true;
                this.dataCount = dataCount;
                this.pageCount = pageCount;
            },
            onSearch() {
                this.loadPage(0);
            },
            onEdit(id) {
                this.$router.push({
                    name: 'permission/person-info',
                    params: {
                        id
                    }
                })
            },
            async onReset(id) {
                await personController.resetPassword(id)
                this.$$ui.showSuccess('重置成功')
            }
        }
    }
</script>

<style scoped lang="scss">
    .tiny {
        width: 80px;
    }

    .custom {
        width: 150px;
        flex: auto 0;
    }
</style>