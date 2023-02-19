<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">{{id>0?'修改人员信息':'添加人员'}}</div>
                <a class="btn normal" @click="onCancel">返回</a>
            </panel-title>
            <cform>
                <div class="option">
                    <span class="name require">登录名</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="userName"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">真实姓名</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="realName"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">性别</span>
                    <div class="container">
                        <div class="value">
                            <combobox class="component" :source="gender" v-model="genderId"></combobox>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">职务</span>
                    <div class="container">
                        <div class="value">
                            <role-combobox class="component" v-model="roleId"></role-combobox>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">所属部门</span>
                    <div class="container">
                        <div class="value">
                            <department-combobox class="component" v-model="departmentId"></department-combobox>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">人员编号</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="code"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">联系电话</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="mobile"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">一卡通号码</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="cardNo"></div>
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
    import combobox from '../../../component/combobox'
    import roleCombobox from '../combobox-role'
    import departmentCombobox from '../combobox-department'

    import personController from '../../../../controller/person'
    import parameterController from '../../../../controller/parameter'

    export default {
        components: {toolbar, panel, panelTitle, cform, roleCombobox, departmentCombobox, combobox},
        data() {
            return {
                positions: [],
                gender: [],
                userName: '',
                realName: '',
                roleId: undefined,
                departmentId: undefined,
                positionId: '',
                genderId: '',
                code: '',
                mobile: '',
                cardNo: ''
            }
        },
        computed: {
            id() {
                return this.$route.params.id
            }
        },
        async mounted() {
            this.positions = await parameterController.getPositions();
            this.gender = await parameterController.getGender();
            if (this.id > 0) {
                let user = await personController.getUserById(this.id);
                this.userName = user.userName;
                this.realName = user.realName;
                this.roleId = user.roleId;
                this.departmentId = user.departmentId;
                this.positionId = user.positionId;
                this.genderId = user.genderId;
                this.code = user.code;
                this.mobile = user.mobile;
                this.cardNo = user.cardNo;
            }
        },
        methods: {
            async onSubmit() {
                if (!this.userName) {
                    return this.$$ui.showError('请输入登录名')
                }
                if (!this.realName) {
                    return this.$$ui.showError('请输入真实姓名')
                }
                if (!this.genderId) {
                    return this.$$ui.showError('请选择性别')
                }
                if (!this.roleId) {
                    return this.$$ui.showError('请选择用户角色')
                }
                if (!this.departmentId) {
                    return this.$$ui.showError('请选择所属部门')
                }
                // if (!this.positionId) {
                //     return this.$$ui.showError('请选择职务')
                // }
                let params = {
                    userName: this.userName,
                    realName: this.realName,
                    genderId: this.genderId,
                    roleId: this.roleId,
                    departmentId: this.departmentId,
                    positionId: this.positionId,
                    code: this.code,
                    mobile: this.mobile,
                    cardNo: this.cardNo
                };
                if (this.id) {
                    params.id = this.id;
                    await personController.updateUser(params);
                    this.$$ui.showSuccess('修改成功');
                } else {
                    await personController.addUser(params);
                    this.$$ui.showSuccess('添加成功');
                }
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