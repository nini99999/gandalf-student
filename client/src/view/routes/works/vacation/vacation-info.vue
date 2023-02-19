<template>
    <panel>
        <panel-title>
            <div class="title">请假详情</div>
            <a class="btn normal ok2" @click="onCancel">返回</a>
        </panel-title>
        <cform>
            <div class="option">
                <div class="name">申请ID</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.applicantId}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">申请人姓名</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.creatUserName}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">学员</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.studentName}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">所属部门</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.department}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">申请时间</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.estimateStartTime|datetime}} - {{source.estimateEndTime|datetime}}</span>
                    </div>
                </div>
            </div>
            <div class="option">
                <div class="name">外出地点</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.address}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">外出事由</div>
                <div class="container">
                    <div class="value"><span class="text">{{source.reason}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">实际时间</div>
                <div class="container">
                    <div class="value"><span
                            class="text">{{source.startTime|datetime}} - {{source.endTime|datetime}}</span></div>
                </div>
            </div>
            <div class="option">
                <div class="name">状态</div>
                <div class="container">
                    <div class="value">
                        <combobox :readonly="readonly" :source="status" v-model="source.status"></combobox>
                    </div>
                </div>
            </div>
            <div class="submit" v-if="!readonly">
                <a class="btn normal" @click="onCancel">取消</a>
                <a class="btn ok normal" @click="onSubmit">保存</a>
            </div>
        </cform>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import cform from '../../../component/cform'
    import combobox from '../../../component/combobox'
    import studentController, {status} from '../../../../controller/student'

    export default {
        components: {panel, panelTitle, cform, combobox},
        props: {
            user: {}
        },
        data() {
            return {
                source: {},
                status
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            },
            readonly() {
                return this.user.permissions.indexOf('vacation-edit') == -1
            }
        },
        async mounted() {
            this.source = await studentController.getLeave(this.id);
        },
        methods: {
            onCancel() {
                this.$router.back()
            },
            async onSubmit() {
                await studentController.updateLeaveStatus(this.id, this.source.status)
                this.$$ui.showSuccess('修改成功');
                this.onCancel();
            }
        }
    }
</script>

<style scoped lang="scss">

</style>