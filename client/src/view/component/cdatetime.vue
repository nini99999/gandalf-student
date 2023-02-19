<template>
    <div class="datetime input">
        <cdate :value="value" @input="onInputDate" :readonly="readonly" :limit="limit"></cdate>
        <span class="sep"></span>
        <ctime :value="value" @input="onInputTime" :readonly="readonly"></ctime>
    </div>
</template>

<script>
    import cdate from './cdate'
    import ctime from './ctime'

    export default {
        components: {cdate, ctime},
        props: {
            value: {},
            readonly: {
                type: Boolean,
                default: false
            },
            limit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                target: null
            }
        },
        mounted() {
            this.target = new Date(this.value)
        },
        methods: {
            onInputDate(date) {
                let dt = new Date(date)
                this.target.setFullYear(dt.getFullYear())
                this.target.setMonth(dt.getMonth())
                this.target.setDate(dt.getDate())
                this.$emit('input', this.target.getTime())
            },
            onInputTime(time) {
                let dt = new Date(time)
                this.target.setHours(dt.getHours())
                this.target.setMinutes(dt.getMinutes())
                this.target.setSeconds(dt.getSeconds())
                this.$emit('input', this.target.getTime())
            }
        }
    }
</script>

<style scoped lang="scss">
    .datetime {
        display: flex;
        align-items: center;
        border: 0;
        padding: 0;

        .sep {
            width: 20px;
        }

        .ctime,
        .cdate {
            flex: 1;
        }
    }
</style>