<template>
    <div>
        <div class="table">
            <slot></slot>
            <div class="none" v-if="ready && total==0">暂无记录</div>
        </div>
        <div class="pagination" v-if="total>0 && totalPage>1">
            <a class="btn small dir" @click="onClickPage(value-1)" :class="{off:value<=0}">
                <i class="icon icon-left"></i>
                <!--                <span>上一页</span>-->
            </a>
            <template v-for="i in pages">
                <label v-if="i<0" class="icon icon-more"></label>
                <a class="btn small" :class="{ok2:i===value}"
                   @click="onClickPage(i)" v-else>{{i+1}}</a>
            </template>

            <a class="btn small dir" @click="onClickPage(value+1)" :class="{off:value>=totalPage-1}">
                <!--                <span>下一页</span>-->
                <i class="icon icon-right"></i>
            </a>
            <!--            <div class="text">{{value+1}}/{{totalPage}}</div>-->
            <!--            <span>到第</span>-->
            <!--            <input class="input" v-model="input">-->
            <!--            <span>页</span>-->
            <!--            <a class="btn small" @click="onClickPage(input-1)">确定</a>-->
        </div>
    </div>
</template>

<script>
    const MAX = 5
    export default {
        props: {
            total: {
                type: Number,
                default: 0
            },
            value: {
                type: Number,
                default: 0
            },
            size: {
                type: Number,
                default: 20
            },
            ready: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                input: ''
            }
        },
        computed: {
            totalPage() {
                return Math.ceil(this.total / this.size)
            },
            pages() {
                let arr = []
                let pages = this.totalPage
                if (pages < 9) {
                    for (let i = 0; i < pages; i++) {
                        arr.push(i)
                    }
                } else {
                    let s = this.value - 2
                    if (s < 0) {
                        s = 0
                    }
                    let t = s + MAX
                    if (t > pages) {
                        t = pages
                        s = t - MAX
                    }
                    for (let i = s; i < t; i++) {
                        arr.push(i)
                    }
                    if (s > 1) {
                        arr.unshift(-1)
                    }
                    if (s > 0) {
                        arr.unshift(0)
                    }
                    if (t < pages - 1) {
                        arr.push(-2)
                    }
                    if (t < pages) {
                        arr.push(pages - 1)
                    }
                }
                return arr
            }
        },
        filters: {
            pageType(i, current) {
                if (current + 1 === i) {
                    return 'on'
                }
            }
        },
        methods: {
            onClickPage(value) {
                if (value === -1) {
                    value = this.value - 5
                } else if (value === -2) {
                    value = this.value + 5
                }
                if (value < 0) {
                    value = 0
                }
                let totalPage = this.totalPage
                if (value > totalPage - 1) {
                    value = totalPage - 1
                }
                if (this.value !== value) {
                    this.$emit('input', value)
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .table {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;

        .head {
            border-bottom: 1px solid $color-line;
            color: #919CB3;

            &.row {
                height: 38px;
            }

            span {
                &:last-child {
                    padding-left: 10px;
                }
            }
        }

        .content {
            flex: 1;
            overflow: hidden;

            .row {
                height: 48px;

                &:nth-child(2n+1) {
                    background-color: #F7F9FC;
                }

                &:hover {
                    background-color: #ECF2FF;
                }
            }
        }

        .row {
            display: flex;
            transition: all .3s ease;
            padding: 0 50px;

            > span {
                flex: 1;
                display: flex;
                align-items: center;
                word-break: break-all;

                a {
                    color: $color-blue;
                    display: inline-block;
                    padding: 0 10px;
                    border-right: 1px solid $color-line;

                    &:last-child {
                        border: 0;
                    }
                }

                &.custom {
                    flex: 0 1 auto;
                }
            }
        }

    }

    .pagination {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 12px;
        color: $color-tip;
        margin-top: 32px;

        .text {
            margin: 0 16px;
        }

        .input {
            width: 40px;
            text-align: center;
            margin: 0 4px;
        }

        .btn {
            min-width: 32px;
            font-size: 14px;

            &.dir {
                width: 32px;
            }
        }
    }

    .none {
        text-align: center;
        line-height: 200px;
        color: $color-off;
    }
</style>