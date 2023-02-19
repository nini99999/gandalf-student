<template>
    <panel>
        <panel-title>
            <div class="title">请假审批</div>
        </panel-title>
        <cform>
            <div class="option">
                <span class="name">申请人姓名</span>
                <div class="container">
                    <div class="value">
                        <span class="text">{{user.realName}}</span>
                    </div>
                </div>
            </div>
            <div class="option other">
                <span class="name">外出人员</span>
                <div class="container">
                    <div class="value">
                        <input class="input" v-model="search" placeholder="请输入姓名搜索...">
                    </div>
                    <div class="value">
                        <div class="component">
                            <div>
                                <template v-for="(item,i) in students">
                                    <a :key="i" @click="onToggle(item)" v-if="item.name.indexOf(search)>=0">
                                        {{item.name}}
                                        <checkbox :value="isSelected(item)"></checkbox>
                                    </a>
                                </template>
                                <div class="none" v-if="students.length==0">该申请人所在部门暂无学员</div>
                            </div>
                            <div>
                                <a v-for="(item,i) in selected" :key="i" @click="onToggle(item)">
                                    {{item.name}}<i class="icon icon-remove"></i>
                                </a>
                                <div class="none" v-if="selected.length==0">请从左侧选择外出学员</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">外出开始时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" :limit="true" v-model="startTime"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">外出结束时间</span>
                <div class="container">
                    <div class="value">
                        <cdatetime class="component" :limit="true" v-model="endTime"></cdatetime>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name">外出地点</span>
                <div class="container">
                    <div class="value">
                        <input class="input" v-model="address"/>
                    </div>
                </div>
            </div>
            <div class="option">
                <span class="name require">外出事由</span>
                <div class="container">
                    <div class="value">
                        <textarea class="input" v-model="reason"></textarea>
                    </div>
                </div>
            </div>
            <div class="submit">
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
    import cdatetime from '../../../component/cdatetime'
    import checkbox from '../../../component/checkbox'
    import studentController from '../../../../controller/student'

    export default {
        components: {panelTitle, panel, cform, checkbox, cdatetime},
        props: {
            user: {}
        },
        data() {
            return {
                startTime: '',
                endTime: '',
                address: '',
                reason: '',
                students: [],
                selected: [],
                search: ''
            }
        },
        watch: {
            search() {
                if (search) {

                } else {

                }
            }
        },
        async mounted() {
            let {data} = await studentController.getList({
                pageCount: 0,
                pageSize: 1000,
                departmentId: this.user.departmentId
            });
            this.students = data;
        },
        methods: {
            isSelected(item) {
                return this.selected.some(d => d.id == item.id);
            },
            onToggle(item) {
                let i = this.selected.findIndex(d => d.id == item.id)
                if (i >= 0) {
                    this.selected.splice(i, 1)
                } else {
                    this.selected.push(item)
                }
            },
            onCancel() {
                this.$router.back()
            },
            async onSubmit() {
                if (!this.startTime) {
                    return this.$$ui.showError('请选择外出开始时间')
                }
                if (this.startTime < Date.now()) {
                    return this.$$ui.showError('开始时间不能早于当前时间')
                }
                if (!this.endTime) {
                    return this.$$ui.showError('请选择外出结束时间')
                }
                if (this.endTime <= this.startTime) {
                    return this.$$ui.showError('结束时间不能早于开始时间')
                }
                if (!this.reason) {
                    return this.$$ui.showError('请填写外出事由')
                }
                let ids = this.selected.map(d => d.id).join(',');
                let params = {
                    startTime: this.startTime,
                    endTime: this.endTime,
                    reason: this.reason,
                    address: this.address,
                    applicantUserId: ids
                };
                await studentController.addLeaveApplicant(params);
                this.$$ui.showSuccess('提交申请成功');
                this.$router.back();
            }
        }
    }
</script>

<style scoped lang="scss">
    .other {
        .value {
            &:first-child {
                margin-bottom: 20px;
            }

            .component {
                display: flex;

                > div {
                    flex: 1;
                    height: 300px;
                    border: $style-border;
                    overflow: auto;

                    &:first-child {
                        margin-right: 20px;
                    }

                    a {
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        height: 30px;
                        padding: 0 20px;

                        &:hover {
                            background-color: $color-back-hover;
                        }

                        .icon {
                            color: $color-off;
                        }
                    }

                    .none {
                        line-height: 100px;
                        text-align: center;
                        color: $color-off;
                    }
                }
            }

        }
    }
</style>