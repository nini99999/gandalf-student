<template>
    <div>
        <div class="row" :class="type">
            <span class="id" :style="level|padding">
                <a v-if="type!='head'" @click="expend=!expend">
                    <i class="icon icon-nodeexpand" v-if="expend"></i>
                    <i class="icon icon-nodecollapse" v-else></i>
                </a>
                {{source.id}}
            </span>
            <span>{{source.name}}</span>
            <span>{{source.code}}</span>
            <span>{{source.memo}}</span>
            <span v-if="type=='head'">操作</span>
            <span v-else>
                <a @click="onAdd" v-if="canEdit">添加子部门</a>
                <!--                <a @click="onEdit">编辑</a>-->
            </span>
        </div>
        <div class="children" :style="height" v-if="source.child && source.child.length>0">
            <department-render v-for="(child,i) in source.child" :key="i"
                               :source="child" :level="level+1"
                               @size="onSize"></department-render>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'department-render',
        props: {
            source: {},
            level: {
                type: Number,
                default: 0
            },
            type: {
                type: String,
                default: 'item'
            }
        },
        data() {
            return {
                expend: true,
                childSize: 0
            }
        },
        computed: {
            height() {
                let value = 0;
                if (this.expend) {
                    value = this.childSize * 48
                }
                return {
                    height: value + 'px'
                }
            },
            canEdit() {
                return this.source.id == 1 || this.source.id > 5
            }
        },
        filters: {
            padding(value) {
                if (value > 0) {
                    return {
                        paddingLeft: (value * 16) + 'px'
                    }
                }
            }
        },
        watch: {
            expend() {
                if (this.expend) {
                    this.$emit('size', this.childSize);
                } else {
                    this.$emit('size', -this.childSize);
                }
            }
        },
        mounted() {
            this.$emit('size', this.childSize + 1);
        },
        methods: {
            onSize(size) {
                this.childSize += size;
            },
            onAdd() {
                this.$router.push({
                    name: 'permission/department-info',
                    params: {
                        id: 0
                    },
                    query: {
                        pid: this.source.id,
                        pname: this.source.name
                    }
                })
            },
            onEdit() {
                this.$router.push({
                    name: 'permission/department-info',
                    params: {
                        id: this.source.id
                    },
                    query: {
                        pid: this.source.id,
                        pname: this.source.name
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">
    .row {
        display: flex;
        transition: all .3s ease;
        padding: 0 50px;
        border-bottom: 1px solid $color-line;

        > span {
            flex: 1;
            display: flex;
            align-items: center;
            word-break: break-all;

            a {
                color: $color-blue;
                display: inline-block;
                margin-right: 10px;
            }
        }

        &.item {
            height: 48px;

            &:hover {
                background-color: #ECF2FF;
            }
        }

        &.head {
            color: #919CB3;
            height: 38px;
        }


        .id {
            width: 300px;
            flex: 0 1 auto;
        }
    }

    .children {
        transition: all .3s ease;
        overflow: hidden;
    }
</style>