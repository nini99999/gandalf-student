<template>
    <div class="panel">
        <slot></slot>
    </div>
</template>

<script>
    export default {}
</script>

<style scoped lang="scss">
    .panel {
        background-color: white;
        box-shadow: 0px 2px 10px 0px rgba(191, 192, 204, 0.2);
        border-radius: 2px;

        .content {
            padding: 0 32px 50px;
        }

        .form {
            padding: 20px 0 50px;
        }
    }
</style>