<template>
    <div class="cdata input" :class="{on:calendarVisible,readonly: readonly}" @click.stop>
        <input
                :value="text"
                :placeholder="placeholder"
                :readonly="readonly"
                @input="onInputText"
                @focus="onFocus"
        />
        <img src="./assets/date.png" @click="onFocus"/>
        <calendar
                class="popup"
                :class="upDir?'up':'down'"
                ref="calendar"
                :value="value"
                :limit="limit"
                @input="onInput"
                v-if="calendarVisible"
        ></calendar>
    </div>
</template>

<script>
    import calendar from './calendar'
    import Vue from 'vue'

    export default {
        components: {calendar},
        props: {
            value: {},
            placeholder: {},
            readonly: {
                type: Boolean,
                default: false
            },
            limit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                text: '',
                calendarVisible: false,
                upDir: false
            }
        },
        watch: {
            value() {
                this.setText()
            }
        },
        mounted() {
            this.setText()
        },
        methods: {
            setText() {
                if (this.value) {
                    this.text = Vue.filter('date')(this.value)
                } else {
                    this.text = ''
                }
            },
            onFocus() {
                if (this.readonly) {
                    return
                }
                if (!this.calendarVisible) {
                    this.calendarVisible = true;
                    this.upDir = window.innerHeight - this.$el.getBoundingClientRect().bottom < 260;
                    if (document.onclick) {
                        document.onclick()
                    }
                    document.onclick = this.onBlur
                }
            },
            onBlur() {
                if (this.text) {
                    let dt = new Date(this.text);
                    if (dt) {
                        let time = dt.getTime();
                        if (!isNaN(time)) {
                            this.onInput(time)
                        }
                    }
                } else {
                    this.$emit('input', '');
                }
                this.calendarVisible = false;
                document.onclick = null;
            },
            onInput(time) {
                this.$emit('input', time);
                this.calendarVisible = false;
                document.onclick = null;
            },
            onInputText(e) {
                this.text = e.target.value;
                if (this.text) {
                    let dt = new Date(this.text);
                    if (dt) {
                        let time = dt.getTime();
                        if (!isNaN(time)) {
                            this.$refs.calendar.refresh(time)
                        }
                    }
                } else {
                    this.$emit('input', '');
                }
            }
        }
    }
</script>

<style scoped lang="scss">
    .cdata {
        position: relative;
        display: flex;
        align-items: center;
        padding-right: 10px;

        input {
            flex: 1;
            border: 0;
            height: 100%;
        }

        img {
            opacity: 0.3;
        }

        .calendar {
            position: absolute;
            right: 0;

            &.up {
                bottom: 40px;
                box-shadow: 1px 2px 10px rgba(191, 192, 204, 0.6);
            }

            &.down {
                top: 40px;
            }
        }
    }
</style>