<template>
    <combobox mode="tree" :source="source" :placeholder="placeholder" :readonly="readonly"
              :value="value" @input="onInput"></combobox>
</template>

<script>
    import combobox from '../../component/combobox'
    import personController from '../../../controller/person'

    export default {
        components: {combobox},
        props: {
            value: {},
            all: {
                type: Boolean,
                default: false
            },
            placeholder: {},
            readonly: {}
        },
        data() {
            return {
                source: []
            }
        },
        async mounted() {
            let root = await personController.getDepartmentList();
            if (this.all) {
                this.source.push({name: '请选择部门查询'});
            }
            this.source.push(root);
        },
        methods: {
            onInput(value) {
                this.$emit('input', value)
            }
        }
    }
</script>

<style scoped lang="scss">

</style>