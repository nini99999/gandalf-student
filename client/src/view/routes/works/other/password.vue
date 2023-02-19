<template>
    <div>
        <panel>
            <panel-title>
                <div class="title">修改密码
                    <span class="error">首次登陆，请修改您的登录密码</span>
                </div>
            </panel-title>
            <cform>
                <div class="option">
                    <span class="name require">旧密码</span>
                    <div class="container">
                        <div class="value"><input class="input" type="password" v-model="old"
                                                  @keydown.enter="onEnter('pass')"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">新密码</span>
                    <div class="container">
                        <div class="value"><input ref="pass" class="input" type="password" v-model="pass"
                                                  @keydown.enter="onEnter('word')"></div>
                    </div>
                </div>
                <div class="option">
                    <span class="name require">新密码确认</span>
                    <div class="container">
                        <div class="value"><input ref="word" class="input" type="password" v-model="word"
                                                  @keydown.enter="onEnter()"></div>
                    </div>
                </div>
                <div class="submit">
                    <a class="btn normal" @click="onCancel" v-if="!user.isNew">取消</a>
                    <a class="btn ok normal" @click="onSubmit">提交</a>
                </div>
            </cform>
        </panel>
    </div>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import cform from '../../../component/cform'
    import adminUser from '../../../../model/AdminUser'

    export default {
        components: {panel, panelTitle, cform},
        props: {
            user: {}
        },
        data() {
            return {
                old: '',
                pass: '',
                word: ''
            }
        },
        methods: {
            async onEnter(next) {
                if (next) {
                    this.$refs[next].focus();
                    this.$refs[next].select();
                } else {
                    this.onSubmit();
                }
            },
            async onSubmit() {
                if (!this.old) {
                    return this.$$ui.showError('请输入旧密码');
                }
                if (!this.pass) {
                    return this.$$ui.showError('请输入新密码');
                }
                if (!this.word) {
                    return this.$$ui.showError('请输入新密码确认');
                }
                if (this.pass != this.word) {
                    return this.$$ui.showError('两次输入的新密码不一致');
                }
                await adminUser.changePassword(this.pass, this.old);
                this.$$ui.showSuccess('修改成功');
                this.$router.push('/');
            },
            onCancel() {
                this.$router.back()
            }
        }
    }
</script>

<style scoped lang="scss">
    .error {
        color: $color-red;
        font-size: 14px;
        margin-left: 50px;
    }
</style>