<template>
    <div class="works">
        <div class="content">
            <router-view :user="user" :month="month" :showNotice="showNotice"></router-view>
        </div>
        <div class="side">
            <menus :user="user" v-if="!user.isNew"></menus>
        </div>
        <div class="top">
            <div class="logo">
                <img src="./assets/logo.png">
                海军军医大学学员(士兵)请销假管理系统
            </div>
            <div class="title">
                <combobox class="month" color="white" :no-border="true" :source="months" v-model="month"
                          v-if="isHome"></combobox>
            </div>
            <div class="welcome">欢迎回来，{{user.userName}}</div>
            <a class="link" @click="quit">退出</a>
            <a class="link" @click="modifyPassword">修改密码</a>
        </div>
        <notice ref="notice"></notice>
    </div>
</template>

<script>
    import adminUser from '../../../model/AdminUser'
    import menus from './menus'
    import combobox from '../../component/combobox'
    import notice from './notice'
    import websocketApi from '../../../api/websocket'
    import biController from '../../../controller/bi'

    export default {
        components: {menus, notice, combobox},
        data() {
            return {
                months: biController.months,
                month: biController.months.length - 1,
                user: {
                    isNew: true
                }
            }
        },
        computed: {
            isHome() {
                return this.$route.name == 'dashboard'
            }
        },
        created() {
            if (adminUser.data) {
                this.user = adminUser.data;
                if (this.user.isNew) {
                    this.$router.replace({
                        name: 'password'
                    })
                }
            } else {
                this.$router.replace('/login')
            }
        },
        mounted() {
            websocketApi.addListener('carControl', this.showNotice);
        },
        methods: {
            modifyPassword() {
                this.$router.push({
                    name: 'password'
                })
            },
            quit() {
                adminUser.quit();
                this.$router.replace('/login')
            },
            showNotice(item = {}) {
                this.$refs.notice.show(item);
            }
        }
    }
</script>

<style scoped lang="scss">
    .works {
        padding: 64px 0 0 230px;
    }

    .content {
        width: 100%;
        padding: 24px;
    }

    .side {
        position: fixed;
        left: 0;
        top: 80px;
        width: 230px;
        height: 100%;
        overflow: auto;
        background-color: white;
        box-shadow: 0px 0px 10px 0px rgba(47, 53, 60, 0.06);


    }

    .top {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        height: 80px;
        padding: 0 20px;
        display: flex;
        align-items: center;
        color: white;
        background-image: url("./assets/banner.png");
        background-size: cover;
        box-shadow: 0px 2px 10px 0px rgba(145, 156, 179, 0.1);

        .logo {
            display: flex;
            align-items: center;
            padding: 0 20px;
            height: 64px;
            font-weight: 500;
            font-size: 16px;
            color: white;

            img {
                margin-right: 20px;
            }
        }

        .title {
            flex: 1;

            .month {
                min-width: 140px;
            }
        }

        .welcome {
            font-size: 16px;
            padding-right: 16px;
            border-right: 1px solid #E1E5EC;
            margin-right: 16px;
        }

        .link {
            color: white;
            margin: 0 16px;
        }
    }
</style>