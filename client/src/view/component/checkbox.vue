<template>
    <div class="checkbox" :class="{on:value}" @click="$emit('input',!value)">
        <span class="box">
            <i class="icon icon-gou" v-if="value"></i>
        </span>
        <slot></slot>
    </div>
</template>

<script>
    export default {
        props: {
            value: {}
        }
    }
</script>

<style scoped lang="scss">
    .checkbox {
        display: flex;
        align-items: center;
        cursor: default;

        .box {
            display: inline-block;
            width: 16px;
            height: 16px;
            line-height: 16px;
            border-radius: 3px;
            border: $style-border;
            margin-right: 8px;
            transition: all .3s ease-out;
        }

        .icon {
            color: $color-white;
        }

        &:hover {
            .box {
                border-color: $color-blue;
                background-color: #e2edff;
            }
        }

        &.on {
            .box {
                border-color: $color-blue;
                background-color: $color-blue;
            }
        }
    }
</style>