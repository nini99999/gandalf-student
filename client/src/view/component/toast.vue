<template>
    <div class="toast">
        <template v-for="(item,i) in data">
            <transition name="bounce">
                <div class="toast-item" v-if="item.visible">{{item.text}}<i class="icon" :class="'icon-'+item.type"></i>
                </div>
            </transition>
        </template>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                data: []
            }
        },
        methods: {
            show(text, type) {
                let item = {text, type, visible: true};
                this.data.push(item);
                setTimeout(() => {
                    item.visible = false;
                    let i = this.data.indexOf(item);
                    this.data.splice(i, 1);
                }, 3000)
            },
            showSuccess(text) {
                this.show(text, 'success')
            },
            showError(text) {
                this.show(text, 'error')
            }
        }
    }
</script>

<style scoped lang="scss">
    .toast {
        position: fixed;
        top: 30px;
        left: 50%;
    }

    .toast-item {
        display: inline-block;
        padding: 10px 30px;
        margin-bottom: 10px;
        border-radius: 4px;
        font-size: 16px;
        box-shadow: 0 2px 4px 0px rgba(0, 0, 0, 0.12);
        background-color: white;
        white-space: nowrap;
        transform: translate(-50%, 0);

        i {
            position: absolute;
            left: 12px;
            top: 12px;
        }

        .icon-success {
            color: $color-green;
        }

        .icon-error {
            color: $color-red;
        }
    }

    .bounce-enter-active {
        animation: bounce-in .3s;
    }

    .bounce-leave-active {
        animation: bounce-out .1s;
    }

    @keyframes bounce-in {
        0% {
            opacity: 0;
            transform: translate(-50%, 0) scale(0);
        }
        10% {
            opacity: 1;
            transform: translate(-50%, 0) scale(1.5);
        }
        100% {
            transform: translate(-50%, 0) scale(1);
        }
    }

    @keyframes bounce-out {
        0% {
            opacity: 1;
            transform: translate(-50%, 0) scale(1);
        }
        100% {
            opacity: 0;
            transform: translate(-50%, 0) scale(0);
        }
    }
</style>