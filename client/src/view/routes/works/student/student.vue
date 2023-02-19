<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">学生档案管理</div>
                <a class="btn normal" @click="onEdit" v-if="permissionStudentEdit">添加新学生</a>
                <a class="btn normal" href="static/template.xls" v-if="permissionStudentEdit">下载模板</a>
                <a class="upload btn normal" v-if="permissionStudentEdit">
                    <input type="file" @change="onImport">
                    导入学生信息
                </a>
                <a class="upload btn normal" v-if="permissionStudentEdit">
                    <input type="file" accept="application/zip" @change="onImportPic">
                    导入学生照片
                </a>
            </panel-title>
            <div class="content">
                <panel-search>
                    <combobox class="component" mode="tree" :source="department"
                              v-model="departmentId"></combobox>
                    <input class="input" v-model="search1" placeholder="请输入学籍号">
                    <input class="input" v-model="name" placeholder="请输入姓名">
                    <a class="btn normal ok2" @click="onSearch">搜索</a>
                </panel-search>
                <ctable :total="dataCount" :value="pageCount" :size="pageSize" :ready="ready" @input="loadPage">
                    <div class="head row">
                        <span class="custom tiny">编号</span>
                        <span>学籍号</span>
                        <span>姓名</span>
                        <span>所属班级</span>
                        <span>入学年份</span>
                        <span class="custom tiny">操作</span>
                    </div>
                    <div class="content">
                        <div class="row" v-for="(item,i) in data" :key="i">
                            <span class="custom tiny">{{item.id}}</span>
                            <span>{{item.code}}</span>
                            <span>{{item.name}}</span>
                            <span>{{item.departmentName}}</span>
                            <span>{{item.startTime|date}}</span>
                            <span class="custom tiny">
                                <a @click="onEdit(item.id)">{{permissionStudentEdit?'编辑':'查看'}}</a>
                            </span>
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
    import studentController from '../../../../controller/student'
    import personController from '../../../../controller/person'

    export default {
        components: {panel, panelTitle, panelSearch, ctable, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                search1: '',
                name: '',
                departmentId: '',
                department: [{name: '全部', id: 0}],
                data: [],
                dataCount: 0,
                pageSize: 20,
                pageCount: 0,
                ready: false
            }
        },
        computed: {
            permissionStudentEdit() {
                return this.user.permissions.indexOf('student-edit') >= 0
            }
        },
        watch: {
            departmentId() {
                this.loadPage()
            }
        },
        async mounted() {
            let root = await personController.getDepartmentList();
            this.department.push(root);
            this.loadPage();
        },
        methods: {
            async loadPage(pageCount = 0) {
                let params = {pageCount, pageSize: this.pageSize};
                if (this.departmentId > 0) {
                    params.departmentId = this.departmentId;
                }
                if (this.name) {
                    params.name = this.name;
                }
                let {data, dataCount} = await studentController.getList(params);
                this.data = data;
                this.ready = true;
                this.dataCount = dataCount;
                this.pageCount = pageCount;
            },
            onSearch() {
                this.loadPage()
            },
            onEdit(id) {
                this.$router.push({
                    name: 'student-info',
                    params: {
                        id
                    }
                })
            },
            async onImport(e) {
                let file = e.target.files[0];
                if (file) {
                    await studentController.importStudents(file);
                    this.$$ui.showSuccess('导入成功')
                }
            },
            async onImportPic(e) {
                let file = e.target.files[0];
                if (file) {
                    await studentController.importStudentPic(file);
                    this.$$ui.showSuccess('导入成功')
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .tiny {
        width: 80px;
    }

    .upload {
        position: relative;

        input {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }
    }
</style>