<template>
    <form class="form">
        <slot></slot>
    </form>
</template>

<script>
    export default {}
</script>

<style scoped lang="scss">
    .form {
        margin: 20px 0;

        > div {
            margin-bottom: 20px;
        }

        .option {
            display: flex;

            .name {
                width: 146px;
                height: 40px;
                line-height: 40px;
                text-align: right;
                padding-right: 16px;

                &:after {
                    content: ':';
                }

                &.require {
                    &:before {
                        content: '*';
                        color: $color-red;
                        margin-right: 2px;
                    }
                }
            }

            .container {
                flex: 1;

                .value {
                    display: flex;
                    align-items: center;

                    > .input, > .component {
                        min-width: 488px;
                        width: 50%;
                    }

                    .text {
                        padding: 0 6px;
                        line-height: 40px;
                    }

                    .tip {
                        margin-left: 10px;
                        color: $color-tip;
                    }
                }
            }
        }

        .submit {
            margin-top: 100px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
</style>