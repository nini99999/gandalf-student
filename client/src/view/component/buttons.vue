<template>
    <div class="buttons">
        <a v-for="(item,i) in source" :key="i" :class="{on:value==i}" @click="$emit('input',i)">{{item}}</a>
    </div>
</template>

<script>
    export default {
        props: {
            source: {},
            value: {}
        }
    }
</script>

<style scoped lang="scss">
    .buttons {
        border-top: 1px solid $color-blue;
        border-left: 1px solid $color-blue;

        a {
            display: inline-block;
            height: 32px;
            line-height: 32px;
            padding: 0 24px;
            color: $color-blue;
            border-bottom: 1px solid $color-blue;
            border-right: 1px solid $color-blue;

            &.on {
                color: white;
                background-color: $color-blue;
            }
        }
    }
</style>