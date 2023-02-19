<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">设置部门请假限制</div>
                <a class="btn normal" @click="onCancel">返回</a>
            </panel-title>
            <cform>
                <div class="option">
                    <span class="name require">部门</span>
                    <div class="container">
                        <div class="value"><span class="text">{{temp.departmentName}}</span></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">请假限制</span>
                    <div class="container">
                        <div class="value"><input type="number" class="input" v-model="temp.limitValue"></div>
                    </div>
                </div>
                <div class="submit">
                    <a class="btn normal" @click="onCancel">取消</a>
                    <a class="btn ok normal" @click="onSubmit">提交</a>
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

    import studentController from '../../../../controller/student'

    export default {
        components: {toolbar, panel, panelTitle, cform},
        data() {
            return {
                temp: {}
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            }
        },
        async mounted() {
            if (this.id > 0) {
                this.temp = await studentController.getDepartmentLimit(this.id);
            }
        },
        methods: {
            async onSubmit() {
                if (this.temp.limitValue > 0) {
                    await studentController.setDepartmentLimit(this.temp.id, this.temp.limitValue);
                    this.$$ui.showSuccess('修改成功');
                    this.onCancel();
                } else {
                    return this.$$ui.showError('请正确设置请假限制')
                }
            },
            onCancel() {
                this.$router.back()
            }
        }
    }
</script>

<style scoped lang="scss">

</style>