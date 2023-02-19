<template>
    <div class="cinput" :class="{on:active}">
        <div>
            <i class="icon" :class="icon" v-if="icon"></i>
            <input ref="input" :value="value" :placeholder="placeholder" :type="type"
                   @input="$emit('input',$event.target.value)" @focus="active=true" @blur="active=false"
                   @keydown.enter="$emit('enter')">
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            value: {},
            placeholder: {},
            type: {
                type: String,
                default: 'text'
            },
            icon: {}
        },
        data() {
            return {
                active: false
            }
        },
        methods: {
            focus() {
                this.$refs.input.focus();
                this.$refs.input.select();
            }
        }
    }
</script>

<style scoped lang="scss">
    .cinput {
        display: inline-table;
        width: 100%;
        height: 38px;
        border: $style-border;
        border-radius: 3px;
        border-collapse: separate;
        border-spacing: 0;
        transition: all .3s ease-out;

        &:hover {
            border-color: $color-border-hover;
        }

        &.on {
            border-color: $color-blue;
        }

        > div {
            display: flex;
            align-items: center;
            height: 100%;
        }

        .icon {
            width: 30px;
            text-align: center;
            color: $color-desc;
        }

        input[type="text"], input[type="password"] {
            flex: 1;
            height: 100%;
            border: 0;
            outline: 0;
            background: transparent;
        }
    }
</style>