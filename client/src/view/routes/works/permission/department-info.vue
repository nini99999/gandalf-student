<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">{{id>0?'编辑部门':'添加部门'}}</div>
                <a class="btn normal" @click="onCancel">返回</a>
            </panel-title>
            <cform>
                <div class="option">
                    <span class="name">上级部门</span>
                    <div class="container">
                        <div class="value"><span class="text">{{pname}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">部门名称</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="name"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">备注</span>
                    <div class="container">
                        <div class="value"><textarea class="input" v-model="memo"></textarea></div>
                    </div>
                </div>
                <div class="submit">
                    <a class="btn normal" @click="onCancel">取消</a>
                    <a class="btn ok normal" @click="onSubmit">保存</a>
                </div>
            </cform>
        </panel>
    </div>
</template>

<script>
    import toolbar from '../toolbar'
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import cform from '../../../component/cform'
    import personController from '../../../../controller/person'

    export default {
        components: {toolbar, panel, panelTitle, cform},
        data() {
            return {
                name: '',
                memo: ''
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            },
            pid() {
                return this.$route.query.pid
            },
            pname() {
                return this.$route.query.pname
            }
        },
        methods: {
            async onSubmit() {
                if (!this.name) {
                    return this.$$ui.showError('请输入部门名称');
                }
                await personController.addDepartment(this.pid, this.name, this.memo);
                this.$$ui.showSuccess('添加成功');
                this.onCancel();
            },
            onCancel() {
                this.$router.back()
            }
        }
    }
</script>

<style scoped lang="scss">

</style>