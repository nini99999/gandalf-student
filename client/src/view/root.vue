<template>
    <div>
        <router-view></router-view>
        <toast ref="toast"></toast>
    </div>
</template>

<script>
    import Vue from 'vue'
    import toast from './component/toast'
    import BaseApi from '../api/BaseApi'

    export default {
        components: {toast},
        async mounted() {
            Vue.prototype.$$ui = this;
            BaseApi.toast = this.showError;
        },
        methods: {
            showSuccess(text) {
                this.$refs.toast.showSuccess(text);
            },
            showError(text) {
                this.$refs.toast.showError(text);
            }
        }
    }
</script>

<style scoped lang="scss">

</style>