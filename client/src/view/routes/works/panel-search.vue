<template>
    <div class="search-bar">
        <slot></slot>
    </div>
</template>

<script>
    export default {}
</script>

<style scoped lang="scss">
    .search-bar {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        min-height: 110px;
        border-bottom: 1px solid $color-line;

        > * {
            margin-right: 32px;
        }
    }
</style>