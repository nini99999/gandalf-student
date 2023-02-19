<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">车辆信息</div>
                <a class="btn normal" @click="onCancel">返回</a>
            </panel-title>
            <cform>
                <div class="option">
                    <span class="name require">车牌号</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="carNO" :readonly="readonly"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">类型</span>
                    <div class="container">
                        <div class="value">
                            <combobox class="component" :source="carTypes" v-model="carTypeId"
                                      :readonly="readonly"></combobox>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">采购时间</span>
                    <div class="container">
                        <div class="value">
                            <cdate v-model="buyTime" :readonly="readonly"></cdate>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">上次保险时间</span>
                    <div class="container">
                        <div class="value">
                            <cdate v-model="insuranceTime" :readonly="readonly"></cdate>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">上次保养时间</span>
                    <div class="container">
                        <div class="value">
                            <cdate v-model="maintenanceTime" :readonly="readonly"></cdate>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">上次年检时间</span>
                    <div class="container">
                        <div class="value">
                            <cdate v-model="checkTime" :readonly="readonly"></cdate>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">行驶历程数</span>
                    <div class="container">
                        <div class="value"><input class="input" v-model="mileage" :readonly="readonly"></div>
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
                    <span class="name require">车辆责任人</span>
                    <div class="container">
                        <div class="value">
                            <search class="component" placeholder="请输入姓名搜索..." :searchFunction="searchUser"
                                    v-model="user" valueLabel="realName" :readonly="readonly"></search>
                        </div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">车辆相关证件上传</span>
                    <div class="container">
                        <div class="value">
                            <file class="component" v-model="image" :readonly="readonly"></file>
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
    import combobox from '../../../component/combobox'
    import cdate from '../../../component/cdate'
    import file from '../../../component/file'
    import search from '../../../component/search'
    import departmentCombobox from '../combobox-department'
    import parameterController from '../../../../controller/parameter'
    import personController from '../../../../controller/person'
    import carController from '../../../../controller/car'

    export default {
        components: {toolbar, panel, panelTitle, cform, combobox, cdate, departmentCombobox, file, search},
        props: {
            user: {}
        },
        data() {
            return {
                carTypes: [],
                carNO: '',
                carTypeId: '',
                buyTime: '',
                insuranceTime: '',
                maintenanceTime: '',
                checkTime: '',
                mileage: '',
                departmentId: '',
                user: null,
                image: {
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
                return this.user.permissions.indexOf('car-edit') == -1
            }
        },
        async mounted() {
            this.carTypes = await parameterController.getCarTypes();
            if (this.id > 0) {
                let one = await carController.getCarById(this.id);
                this.carNO = one.carNO;
                this.carTypeId = one.carTypeId;
                this.buyTime = one.buyTime;
                this.insuranceTime = one.insuranceTime;
                this.maintenanceTime = one.maintenanceTime;
                this.checkTime = one.checkTime;
                this.mileage = one.mileage;
                this.departmentId = one.departmentId;
                if (one.userId) {
                    this.user = await personController.getUserById(one.userId);
                }
                this.image.url = carController.getImage(this.id);
            }
        },
        methods: {
            async searchUser(realName) {
                let params = {pageCount: 0, pageSize: 10, realName};
                let {data} = await personController.getUserList(params);
                return data;
            },
            async onSubmit() {
                if (!this.carNO) {
                    return this.$$ui.showError('请输入车牌号');
                }
                if (!this.carTypeId) {
                    return this.$$ui.showError('请选择类型');
                }
                if (!this.buyTime) {
                    return this.$$ui.showError('请输入采购时间');
                }
                if (!this.insuranceTime) {
                    return this.$$ui.showError('请输入上次保险时间');
                }
                if (!this.maintenanceTime) {
                    return this.$$ui.showError('请输入上次保养时间');
                }
                if (!this.checkTime) {
                    return this.$$ui.showError('请输入上次年检时间');
                }
                if (!this.mileage) {
                    return this.$$ui.showError('请输入行驶历程数');
                }
                if (!this.departmentId) {
                    return this.$$ui.showError('请选择所属部门');
                }
                if (!this.user) {
                    return this.$$ui.showError('请输入车辆责任人');
                }
                if (!this.image.url && !this.image.file) {
                    return this.$$ui.showError('请选择车辆相关证件');
                }
                let params = {
                    carNO: this.carNO,
                    carTypeId: this.carTypeId,
                    buyTime: this.buyTime,
                    insuranceTime: this.insuranceTime,
                    maintenanceTime: this.maintenanceTime,
                    checkTime: this.checkTime,
                    mileage: this.mileage,
                    departmentId: this.departmentId,
                    userId: this.user.id
                };
                if (this.id) {
                    params.id = this.id;
                    await carController.update(params);
                    this.$$ui.showError('更新成功');
                } else {
                    let result = await carController.add(params);
                    params.id = result.id;
                    this.$$ui.showError('添加成功');
                }
                if (this.image.file) {
                    await carController.updateImage(params.id, this.image.file);
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