<template>
    <div class="login">
        <div class="dialog">
            <div class="title">海军军医大学学员(士兵)<br>请销假管理系统</div>
            <div class="option error" v-if="error">
                密码错误，请重新输入
            </div>
            <div class="option">
                <input class="input" v-model="name" type="text" placeholder="请输入用户名"
                       @keydown.enter="onEnter('password')">
            </div>
            <div class="option">
                <input class="input" ref="password" v-model="password" type="password" placeholder="请输入用户密码"
                       @keydown.enter="onEnter()">
            </div>
            <div class="submit"><a class="btn" :class="{on:canSubmit}" @click="onSubmit">登录</a></div>
            <checkbox v-model="remember">记住密码</checkbox>
            <div class="tip">推荐使用以下浏览器：</div>
            <div class="download">
                <a href="http://192.168.30.241/Firefox_Setup_52.0_32.exe">
                    <img src="./assets/firefox.png">firefox 32位
                </a>
                <a href="http://192.168.30.241/Firefox_Setup_52.0_64.exe">
                    <img src="./assets/firefox.png">firefox
                    64位</a>
                <a href="http://192.168.30.241/ChromeStandaloneSetup64.exe">
                    <img src="./assets/chrome.png">chrome</a>
            </div>
        </div>
    </div>
</template>

<script>
    import cform from "../../component/cform"
    import cinput from "../../component/cinput"
    import checkbox from "../../component/checkbox"
    import user from '../../../model/AdminUser'

    export default {
        components: {cform, cinput, checkbox},
        data() {
            return {
                name: '',
                password: '',
                remember: false,
                error: false
            }
        },
        computed: {
            canSubmit() {
                return this.name && this.password
            }
        },
        methods: {
            onEnter(next) {
                if (next) {
                    this.$refs[next].focus();
                } else {
                    this.onSubmit();
                }
            },
            async onSubmit() {
                if (this.canSubmit) {
                    try {
                        await user.login(this.name, this.password, this.remember);
                        this.$router.replace('/')
                    } catch (e) {
                        this.error = true
                    }
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .login {
        height: 100vh;
        background-size: cover;
        background-image: url("./assets/bg.jpg");
    }

    .dialog {
        width: 416px;
        min-height: 560px;
        padding: 72px 48px 40px;
        box-shadow: 0px 10px 10px 0px rgba(23, 36, 61, 0.1);
        border-radius: 2px;

        .title {
            font-size: 24px;
            font-weight: 600;
            line-height: 40px;
            margin-bottom: 48px;
        }

        .option {
            margin-top: 16px;

            .input {
                width: 100%;
                height: 48px;
                padding: 0 16px;
                font-size: 14px;
            }
        }

        .error {
            padding: 0 16px;
            color: #ED4013;
            height: 40px;
            line-height: 40px;
            background: rgba(233, 44, 44, 0.1);
            border-radius: 2px;
            border: 1px solid #ED4013;
        }

        .submit {
            margin-top: 32px;
            margin-bottom: 24px;

            .btn {
                height: 48px;
                font-size: 16px;
                color: #B6BFD1;
                background-color: #E9EBF0;
                border: 0;

                &.on {
                    color: $color-white;
                    background-color: $color-blue;
                }
            }
        }

        .tip {
            margin-top: 20px;
            font-size: 12px;
            color: $color-off;
        }

        .download {
            display: flex;
            align-items: center;
            justify-content: space-between;
            font-size: 12px;

            a {
                color: $color-off;
            }

            img {
                left: 0;
                right: 0;
                margin: auto;
                display: block;
                width: 50px;
                height: 50px;
            }
        }
    }
</style>