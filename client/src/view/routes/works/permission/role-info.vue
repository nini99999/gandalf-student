<template>
    <panel>
        <panel-title>
            <div class="title">编辑角色权限</div>
            <a class="btn normal" @click="onCancel">返回</a>
        </panel-title>
        <cform>
            <div class="option">
                <span class="name">角色</span>
                <div class="container">
                    <div class="value"><span class="text">{{name}}</span></div>
                </div>
            </div>
            <div class="option">
                <span class="name">权限</span>
                <div class="container">
                    <div class="value" v-for="(item,i) in permissions" :key="i">
                        <span class="text">
                            <checkbox v-model="data[item.key]">{{item.name}}</checkbox>
                        </span>
                    </div>
                </div>
            </div>
            <div class="submit">
                <a class="btn normal" @click="onCancel">取消</a>
                <a class="btn normal ok" @click="onSubmit">提交</a>
            </div>
        </cform>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import cform from '../../../component/cform'
    import checkbox from '../../../component/checkbox'
    import {permissions, mgr} from '../../../../model/permission'

    export default {
        components: {panel, panelTitle, cform, checkbox},
        data() {
            return {
                permissions,
                data: {}
            }
        },
        computed: {
            key() {
                return this.$route.params.key
            },
            name() {
                return this.$route.query.name
            }
        },
        mounted() {
            let data = {};
            (mgr.get(this.key) || []).forEach(name => {
                data[name] = true;
            });
            this.data = data;
        },
        methods: {
            onCancel() {
                this.$router.back()
            },
            onSubmit() {
                mgr.set(this.key, Object.keys(this.data).filter(name => this.data[name]));
                this.onCancel();
            }
        }
    }
</script>

<style scoped lang="scss">

</style>