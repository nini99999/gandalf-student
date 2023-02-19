<template>
    <div class="search-input">
        <input class="input" :placeholder="placeholder" v-model="search" :readonly="readonly">
        <div class="drop popup" :class="{scroll:size>=7,up:upDir,down:!upDir}" v-if="dropVisible">
            <div class="content">
                <a v-for="(item,i) in result" :key="i" @mousedown="onSelected(item)">{{item[valueLabel]}}</a>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            placeholder: {},
            searchFunction: {},
            value: {},
            valueLabel: {},
            readonly: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                search: '',
                dropVisible: false,
                upDir: false,
                result: [],
                ignore: false
            }
        },
        computed: {
            size() {
                return this.result.length
            }
        },
        watch: {
            value() {
                if (this.value) {
                    this.onValue()
                }
            },
            search() {
                if (this.timer) {
                    clearTimeout(this.timer);
                    delete this.timer;
                }
                if (this.search) {
                    this.timer = setTimeout(this.doSearch, 100);
                }
            }
        },
        mounted() {
            if (this.value) {
                this.onValue()
            }
        },
        methods: {
            onValue() {
                this.ignore = true;
                this.search = this.value[this.valueLabel];
            },
            async doSearch() {
                if (this.searchFunction) {
                    this.result = await this.searchFunction(this.search);
                    if (this.ignore) {
                        this.ignore = false
                    } else {
                        this.showDrop();
                    }
                }
            },
            showDrop() {
                this.dropVisible = true;
                this.upDir = window.innerHeight - this.$el.getBoundingClientRect().bottom < 250;
                document.onmousedown = this.onCancel
            },
            onSelected(item) {
                this.$emit('input', item);
            },
            onCancel() {
                document.onmousedown = null;
                this.dropVisible = false
            }
        }
    }
</script>

<style scoped lang="scss">
    .search-input {
        position: relative;

        .input {
            width: 100%;
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
                    background-color: $color-back-hover;
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