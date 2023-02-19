<template>
    <div class="combobox">
        <a class="current" :class="{input:!noBorder,value:hasValue,on:dropVisible,readonly:readonly}"
           @click="showDrop">
            <span :style="style">{{current?current[label]:placeholder}}</span>
            <i class="icon icon-right" :style="style" v-if="!readonly"></i>
        </a>
        <div class="drop popup" :class="{scroll:size>=7,up:upDir,down:!upDir}" v-if="dropVisible" @mousedown.stop>
            <div class="content" v-if="mode=='list'">
                <a v-for="(item,i) in source" :key="i" :class="{on:value==item.id}"
                   @mousedown="onSelected(item)">{{item[label]}}
                    <i class="icon icon-gou" v-if="value==item.id"></i></a>
            </div>
            <div class="content" v-if=" mode=='tree'">
                <a v-for="(item,i) in list" :key="i"
                   :class="{on:value==item.data.id}" :style="item.level|padding"
                   @mousedown="onSelected(item.data)">{{item.data[label]}}
                    <i class="icon icon-gou" v-if="value==item.data.id"></i></a>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            source: {},
            placeholder: {},
            value: {},
            label: {
                type: String,
                default: 'name'
            },
            mode: {
                type: String,
                default: 'list'
            },
            noBorder: {
                type: Boolean,
                default: false
            },
            readonly: {
                type: Boolean,
                default: false
            },
            color: {}
        },
        data() {
            return {
                dropVisible: false,
                upDir: false,
                list: []
            }
        },
        computed: {
            style() {
                if (this.color) {
                    return {color: this.color}
                }
            },
            hasValue() {
                return this.value != undefined
            },
            current() {
                if (this.mode == 'tree') {
                    let item = this.list.find(item => item.data.id == this.value);
                    if (item) {
                        return item.data
                    }
                } else {
                    return this.source.find(item => item.id == this.value)
                }
            },
            size() {
                return this.mode == 'tree' ? this.list.length : this.source.length
            }
        },
        filters: {
            padding(value) {
                if (value > 0) {
                    return {
                        paddingLeft: (value * 16 + 20) + 'px'
                    }
                }
            }
        },
        watch: {
            source() {
                this.checkTree()
            }
        },
        mounted() {
            this.checkTree()
        },
        methods: {
            checkTree() {
                if (this.mode == 'tree' && this.source) {
                    this.list = this.transform(this.source, 0, [])
                }
            },
            transform(source, level, list) {
                for (let i = 0; i < source.length; i++) {
                    let data = source[i];
                    list.push({level, data});
                    if (data.child && data.child.length > 0) {
                        this.transform(data.child, level + 1, list);
                    }
                }
                return list;
            },
            showDrop() {
                if (this.readonly) {
                    return;
                }
                this.dropVisible = true;
                this.upDir = window.innerHeight - this.$el.getBoundingClientRect().bottom < 250;
                document.onmousedown = this.onCancel
            },
            onSelected(item) {
                this.$emit('input', item.id)
                this.onCancel()
            },
            onCancel() {
                document.onmousedown = null;
                this.dropVisible = false
            }
        }
    }
</script>

<style scoped lang="scss">
    .combobox {
        position: relative;
        display: inline-block;
        min-width: 200px;

        .current {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 10px 0 16px;
            color: $color-tip;

            &.value {
                color: $color-title;
            }

            i {
                color: $color-tip;
                transition: all 0.3s;
                transform: rotate(90deg);
            }

            &.on {
                i {
                    transform: rotate(270deg);
                }
            }
        }

        .drop {
            position: absolute;
            left: 0;
            width: 100%;
            max-height: 232px;
            overflow-x: hidden;
            overflow-y: auto;
            background-color: white;
            border: $style-border;

            &.up {
                bottom: 40px;
            }

            &.down {
                top: 40px;
            }

            .content {
                padding: 4px 0;
            }

            a {
                position: relative;
                display: flex;
                align-items: center;
                height: 32px;
                padding: 0 10px 0 20px;

                &:hover, &.on {
                    background-color: #ECF2FF;
                }

                .icon-gou {
                    position: absolute;
                    left: 4px;
                    font-weight: bold;
                    color: $color-blue;
                }
            }
        }
    }
</style>