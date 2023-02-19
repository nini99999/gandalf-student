<template>
    <div class="title-bar" :class="mode">
        <slot></slot>
    </div>
</template>

<script>
    export default {
        props: {
            mode: {
                type: String,
                default: ''
            }
        }
    }
</script>

<style scoped lang="scss">
    .title-bar {
        display: flex;
        align-items: center;
        padding: 0 32px;
        height: 86px;
        border-bottom: 1px solid $color-line;

        .title {
            flex: 1;
            font-size: 16px;
            font-weight: bold;
        }

        &.small {
            height: 70px;
            margin-bottom: 10px;
            border: 0;
        }
    }
</style>