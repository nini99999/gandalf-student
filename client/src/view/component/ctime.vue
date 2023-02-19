<template>
    <div class="ctime input" :class="{on:timeVisible,readonly: readonly}" @click.stop>
        <input :value="text" :readonly="readonly" @input="onInputText" @focus="onFocus">
        <img src="./assets/time.png" @click="onFocus">
        <div class="time popup" :class="upDir?'up':'down'" v-if="timeVisible">
            <scroll>
                <div class="item">
                    <a v-for="n in 24" :key="'h'+n" :class="{on:hour===n-1}"
                       @click="onSelectedHour(n-1)">{{n-1|num}}</a>
                </div>
            </scroll>
            <scroll>
                <div class="item">
                    <a v-for="n in 60" :key="'m'+n" :class="{on:minute===n-1}"
                       @click="onSelectedMinute(n-1)">{{n-1|num}}</a>
                </div>
            </scroll>
            <scroll>
                <div class="item">
                    <a v-for="n in 60" :key="'s'+n" :class="{on:second===n-1}"
                       @click="onSelectedSecond(n-1)">{{n-1|num}}</a>
                </div>
            </scroll>
        </div>
    </div>
</template>

<script>
    import scroll from './scroll'
    import Vue from 'vue'

    export default {
        components: {scroll},
        props: {
            value: {},
            readonly: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                text: '',
                timeVisible: false,
                upDir: false,
                hour: 0,
                minute: 0,
                second: 0,
                target: null
            }
        },
        watch: {
            value() {
                this.setText()
            }
        },
        mounted() {
            this.target = new Date(this.value);
            this.setText();
            this.refresh()
        },
        methods: {
            setText() {
                if (this.value) {
                    this.target.setTime(this.value);
                    this.text = Vue.filter('time')(this.value)
                } else {
                    this.text = ''
                }
            },
            onFocus() {
                if (this.readonly) {
                    return
                }
                if (!this.timeVisible) {
                    this.timeVisible = true;
                    this.upDir = window.innerHeight - this.$el.getBoundingClientRect().bottom < 260;
                    if (document.onclick) {
                        document.onclick()
                    }
                    document.onclick = this.onBlur
                }
            },
            onBlur() {
                let dt = new Date(this.text);
                if (dt) {
                    let time = dt.getTime();
                    if (!isNaN(time)) {
                        this.onInput(time)
                    }
                }
                this.timeVisible = false;
                document.onclick = null
            },
            onInputText(e) {
                this.text = e.target.value
                let text = `${this.target.getFullYear()}-${this.target.getMonth() + 1}-${this.target.getDate()} ${this.text}`
                let dt = new Date(text)
                if (dt) {
                    let time = dt.getTime()
                    if (!isNaN(time)) {
                        this.target.setTime(time)
                        this.refresh()
                    }
                }
            },
            onSelectedHour(v) {
                this.hour = v
                this.target.setHours(v)
                this.onInput()
            },
            onSelectedMinute(v) {
                this.minute = v
                this.target.setMinutes(v)
                this.onInput()
            },
            onSelectedSecond(v) {
                this.second = v
                this.target.setSeconds(v)
                this.onInput()
            },
            onInput() {
                this.$emit('input', this.target.getTime())
            },
            refresh() {
                this.hour = this.target.getHours()
                this.minute = this.target.getMinutes()
                this.second = this.target.getSeconds()
            }
        }
    }
</script>

<style scoped lang="scss">
    .ctime {
        position: relative;
        display: flex;
        align-items: center;
        padding-right: 10px;

        input {
            flex: 1;
            border: 0;
            height: 100%;
        }

        .time {
            position: absolute;
            right: 0;
            box-shadow: 1px 2px 10px $color-tip;
            background-color: $color-white;
            border-radius: 4px;
            display: flex;
            width: 180px;
            height: 220px;
            overflow: hidden;

            &.up {
                bottom: 40px;
                box-shadow: 1px 2px 10px rgba(191, 192, 204, 0.6);
            }

            &.down {
                top: 40px;
            }

            .scroll {
                flex: 1;
            }

            .item {
                > a {
                    display: block;
                    text-align: center;
                    line-height: 32px;

                    &:hover {
                        background-color: $color-back-hover;
                    }

                    &.on {
                        color: $color-blue;
                        background-color: $color-back;
                    }
                }
            }
        }
    }
</style>