<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">学生基础信息</div>
                <a class="btn normal" @click="onCancel">返回</a>
            </panel-title>
            <cform>
                <div class="option">
                    <span class="name require">姓名</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="name" :readonly="readonly"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">性别</span>
                    <div class="container">
                        <div class="value">
                            <combobox class="component" :source="genders" v-model="genderId" :readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">身份证号</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="identityCode" :readonly="readonly"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">籍贯</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="nativePlace" :readonly="readonly"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name">照片</span>
                    <div class="container">
                        <div class="value">
                            <file class="component" v-model="photo" :readonly="readonly"></file>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">类型</span>
                    <div class="container">
                        <div class="value">
                            <combobox class="component" :source="types" v-model="typeId" :readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">所属部门</span>
                    <div class="container">
                        <div class="value">
                            <department-combobox class="component" v-model="departmentId" :readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">学籍号</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="code" :readonly="readonly"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">一卡通号</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="cardCode" :readonly="readonly"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">入学时间</span>
                    <div class="container">
                        <div class="value">
                            <cdate class="input" v-model="startTime" :readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">毕业时间</span>
                    <div class="container">
                        <div class="value">
                            <cdate class="input" v-model="endTime" :readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="submit" v-if="!readonly">
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
    import cdate from '../../../component/cdate'
    import file from '../../../component/file'
    import combobox from '../../../component/combobox'
    import departmentCombobox from '../combobox-department'
    import parameterController from '../../../../controller/parameter'
    import studentController from '../../../../controller/student'

    export default {
        components: {toolbar, panel, panelTitle, cform, cdate, file, combobox, departmentCombobox},
        props: {
            user: {}
        },
        data() {
            return {
                types: [],
                genders: [],
                name: '',
                code: '',
                nativePlace: '',
                identityCode: '',
                cardCode: '',
                startTime: '',
                endTime: '',
                typeId: '',
                departmentId: '',
                genderId: '',
                photo: {
                    url: '',
                    file: null
                }
            }
        },
        computed: {
            id() {
                return parseInt(this.$route.params.id)
            },
            readonly() {
                return this.user.permissions.indexOf('student-edit') == -1
            }
        },
        async mounted() {
            this.types = await parameterController.getStudentTypes();
            this.genders = await parameterController.getGender();
            if (this.id > 0) {
                let one = await studentController.getOne(this.id);
                this.name = one.name;
                this.code = one.code;
                this.nativePlace = one.nativePlace;
                this.identityCode = one.identityCode;
                this.cardCode = one.cardCode;
                this.startTime = one.startTime;
                this.endTime = one.endTime;
                this.typeId = one.typeId;
                this.departmentId = one.departmentId;
                this.genderId = one.genderId;
                this.photo.url = studentController.getImage(this.id);
            }
        },
        methods: {
            async onSubmit() {
                if (!this.name) {
                    return this.$$ui.showError('请填写姓名');
                }
                if (!this.genderId) {
                    return this.$$ui.showError('请选择性别');
                }
                if (!this.typeId) {
                    return this.$$ui.showError('请选择类型');
                }
                if (!this.departmentId) {
                    return this.$$ui.showError('请选择所属部门');
                }
                if (!this.code) {
                    return this.$$ui.showError('请填写学籍号');
                }
                if (!this.cardCode) {
                    return this.$$ui.showError('请填写一卡通号');
                }
                if (!this.startTime) {
                    return this.$$ui.showError('请填写入学时间');
                }
                if (!this.endTime) {
                    return this.$$ui.showError('请填写毕业时间');
                }
                let params = {
                    name: this.name,
                    genderId: this.genderId,
                    typeId: this.typeId,
                    departmentId: this.departmentId,
                    code: this.code,
                    cardCode: this.cardCode,
                    startTime: this.startTime,
                    endTime: this.endTime,
                    nativePlace: this.nativePlace,
                    identityCode: this.identityCode
                };
                if (this.id) {
                    params.id = this.id;
                    await studentController.update(params);
                    this.$$ui.showSuccess('更新成功');
                } else {
                    let result = await studentController.add(params);
                    params.id = result.id
                    this.$$ui.showSuccess('添加成功');
                }
                if (this.photo && this.photo.file) {
                    await studentController.updateImage(params.id, this.photo.file);
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