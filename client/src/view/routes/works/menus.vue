<template>
    <div class="menus">
        <div v-for="(item,i) in display" :key="i">
            <a class="item" :class="{on:item==current}" @click="onClick(item)">
                <span class="img" :class="item.icon"></span>
                <span class="text">{{item.name}}</span>
                <i class="icon icon-right" :class="{open:item.open}" v-if="item.children && item.children.length>0"></i>
            </a>
            <div v-if="item.children && item.children.length>0" class="sub-menu"
                 :style="item.open|height(item.children.length)">
                <a class="item" :class="{on:sub==current}" v-for="(sub,j) in item.children"
                   :key="j" @click="onClick(sub)">{{sub.name}}
                    <span class="badge" v-if="sub.link=='car-operation' && waitCount>0">{{waitCount}}</span>
                </a>
            </div>
        </div>
    </div>
</template>
<script>
    import carController from '../../../controller/car'

    export default {
        props: {
            user: {}
        },
        data() {
            return {
                source: [
                    {
                        name: '工作台', icon: 'home', link: 'dashboard', permission: 'home', open: false, children: [
                            {name: '请假审批', link: 'vacation-request', permission: 'home_vacation_request'},
                            {name: '用车申请', link: 'car-request', permission: 'home_car_request'},
                        ]
                    },
                    {name: '请销假管理', icon: 'vacation', link: 'vacation', permission: 'vacation'},
                    {
                        name: '车辆运营管理', icon: 'car', open: false,
                        children: [
                            {name: '用车申请管理', link: 'car-request-list', permission: 'car_request'},
                            {name: '派车管理', link: 'car-operation', permission: 'car_send'},
                            {name: '数据统计', link: 'car-statistics', permission: 'car_total'}
                        ]
                    },
                    {name: '车辆信息维护', icon: 'info', link: 'car', permission: 'car'},
                    {name: '学员(士兵)档案管理', icon: 'student', link: 'student', permission: 'student'},
                    {
                        name: '权限管理', icon: 'permission', open: false,
                        children: [
                            {name: '人员管理', link: 'permission/person', permission: 'user'},
                            {name: '部门管理', link: 'permission/department', permission: 'department'},
                            {name: '部门请假限制', link: 'permission/department-limit', permission: 'department-limit'},
                            {name: '角色管理', link: 'permission/role', permission: 'role'},
                        ]
                    },
                ],
                display: [],
                current: false,
                watchUnread: false,
                waitCount: 0
            }
        },
        filters: {
            height(open, n) {
                let value = open ? n * 64 : 0;
                return {
                    height: value + 'px'
                }
            }
        },
        watch: {
            current() {
                if (this.watchUnread) {
                    this.loadCarUnread()
                }
            }
        },
        mounted() {
            this.display = this.source.filter(item => {
                if (item.permission && this.user.permissions.indexOf(item.permission) >= 0) {
                    return true
                }
                if (item.children) {
                    item.children = item.children.filter(child => {
                        let result = this.user.permissions.indexOf(child.permission) >= 0;
                        if (child.link == 'car-operation') {
                            this.watchUnread = result
                        }
                        return result
                    });
                    if (item.children.length > 0) {
                        return true;
                    }
                    if (item.permission == 'home') {
                        return true;
                    }
                }
            });
            if (this.watchUnread) {
                this.loadCarUnread()
            }
        },
        methods: {
            onClick(item) {
                if (item.children) {
                    item.open = !item.open
                }
                if (item.link) {
                    this.current = item;
                    this.$router.push({name: item.link})
                }
            },
            async loadCarUnread() {
                let {dataCount} = await carController.getCarApplicants({pageSize: 1, pageCount: 0, status: 2});
                this.waitCount = dataCount;
            }
        }
    }
</script>

<style scoped lang="scss">
    .menus {
        .item {
            display: flex;
            align-items: center;
            height: 64px;
            padding: 0 20px;
            color: #808695;
            font-size: 16px;
            border-left: 4px solid transparent;

            .img {
                display: inline-block;
                width: 20px;
                height: 20px;
                margin-right: 20px;
                background-size: cover;
            }

            .text {
                flex: 1;
            }

            .icon {
                transition: all 0.3s;
                transform: rotate(90deg);

                &.open {
                    transform: rotate(270deg);
                }
            }

            .home {
                background-image: url("./assets/icon-home.png");
            }

            .vacation {
                background-image: url("./assets/icon-vacation.png");
            }

            .car {
                background-image: url("./assets/icon-car.png");
            }

            .info {
                background-image: url("./assets/icon-info.png");
            }

            .student {
                background-image: url("./assets/icon-student.png");
            }

            .permission {
                background-image: url("./assets/icon-permission.png");
            }

            &:hover {
                color: $color-blue;
            }

            &.on {
                color: #2D8CF0;
                background-color: rgba(#2D8CF0, 0.1);
                border-color: #2D8CF0;

                .home {
                    background-image: url("./assets/icon-home-2.png");
                }

                .vacation {
                    background-image: url("./assets/icon-vacation-2.png");
                }

                .car {
                    background-image: url("./assets/icon-car-2.png");
                }

                .info {
                    background-image: url("./assets/icon-info-2.png");
                }

                .student {
                    background-image: url("./assets/icon-student-2.png");
                }

                .permission {
                    background-image: url("./assets/icon-permission-2.png");
                }
            }
        }

        .sub-menu {
            transition: all 0.3s;
            overflow: hidden;

            > .item {
                padding-left: 70px;
            }
        }


    }
</style>