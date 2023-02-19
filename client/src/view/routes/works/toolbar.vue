<template>
    <div class="toolbar">
        <div class="main">
            <slot></slot>
        </div>
        <div class="more">
            <slot name="more"></slot>
        </div>
    </div>
</template>

<script>
    export default {}
</script>

<style scoped lang="scss">
    .toolbar {
        display: flex;
        align-items: center;
        background-color: white;
        height: 60px;
        padding: 15px 20px;
        font-size: 20px;

        .main {
            height: 100%;
            flex: 1;
            padding-left: 15px;
            border-left: 5px solid $color-blue;
        }
    }
</style>