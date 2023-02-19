<template>
    <div class="scroll">
        <div class="area" @scroll="onScroll" @mouseover="onMouseOver">
            <div class="content">
                <slot></slot>
            </div>
        </div>
        <div class="bar h" :style="{left:x+'px'}" v-if="hVisible"></div>
        <div class="bar v" :style="{top:y+'px'}" v-if="vVisible"></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                x: 0,
                y: 0,
                hVisible: false,
                vVisible: false
            }
        },
        methods: {
            onMouseOver(e) {
                let area = e.currentTarget
                this.hVisible = area.scrollWidth > area.clientWidth
                this.vVisible = area.scrollHeight > area.clientHeight
            },
            onScroll(e) {
                let area = e.currentTarget
                this.y = area.scrollTop / (area.scrollHeight - area.clientHeight) * (area.clientHeight - 30)
                this.x = area.scrollLeft / (area.scrollWidth - area.clientWidth) * (area.clientWidth - 30)
            }
        }
    }
</script>

<style scoped lang="scss">
    .scroll {
        position: relative;
        width: 100%;
        height: 100%;
        overflow: hidden;

        .area {
            position: relative;
            height: calc(100% + 16px);
            margin-right: -17px;
            margin-bottom: -16px;
            overflow: scroll;
        }

        .bar {
            position: absolute;
            border-radius: 100px;
            background-color: rgba(0, 0, 0, 0.1);
            transition: all 0.3s;
            opacity: 0;
        }

        .v {
            right: 1px;
            width: 10px;
            height: 30px;
        }

        .h {
            bottom: 1px;
            width: 30px;
            height: 10px;
        }

        &:hover {
            .bar {
                opacity: 1;
            }
        }
    }
</style>