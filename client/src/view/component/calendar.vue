<template>
    <div class="calendar">
        <div class="top">
            <a class="icon icon-left2" @click="onChangeYear(-1)"></a>
            <a class="icon icon-left" @click="onChangeMonth(-1)"></a>
            <span>
        <b>{{year}}</b>年
        <b>{{month+1}}</b>月
      </span>
            <a class="icon icon-right" @click="onChangeMonth(1)"></a>
            <a class="icon icon-right2" @click="onChangeYear(1)"></a>
        </div>
        <div class="week">
            <span>日</span>
            <span>一</span>
            <span>二</span>
            <span>三</span>
            <span>四</span>
            <span>五</span>
            <span>六</span>
        </div>
        <div class="date">
            <span v-for="n in offset" :key="'s'+n"></span>
            <a
                    v-for="n in size[month]"
                    :key="'d'+n"
                    :class="{on:isToday(n), off:isLimit(n)}"
                    @click="onChoose(n)"
            >{{n}}</a>
        </div>
        <div></div>
    </div>
</template>
<script>
    export default {
        props: {
            value: {},
            limit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                size: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
                today: new Date(),
                target: new Date()
            }
        },
        computed: {
            year() {
                return this.target.getFullYear()
            },
            month() {
                return this.target.getMonth()
            },
            date() {
                return this.target.getDate()
            },
            weekDay() {
                return this.target.getDay()
            },
            offset() {
                return (this.weekDay + 7 - (this.date - 1) % 7) % 7
            }
        },
        watch: {
            value() {
                this.refresh(this.value)
            }
        },
        mounted() {
            this.init()
        },
        methods: {
            init() {
                let dt = new Date()
                dt.setHours(0)
                dt.setMinutes(0)
                dt.setSeconds(0)
                dt.setMilliseconds(0)
                this.today = dt
                this.refresh(this.value || this.today.getTime())
            },
            onChangeYear(offset) {
                this.target.setFullYear(this.year + offset)
                this.refresh(this.target.getTime())
            },
            onChangeMonth(offset) {
                this.target.setMonth(this.target.getMonth() + offset)
                this.refresh(this.target.getTime())
            },
            refresh(value) {
                this.target = value ? new Date(value) : new Date();
                if ((this.year % 4 === 0 && this.year % 100 !== 0) || this.year % 400 === 0) {
                    this.size[1] = 29
                } else {
                    this.size[1] = 28
                }
            },
            isToday(date) {
                return this.today.getFullYear() === this.year && this.today.getMonth() === this.month && this.today.getDate() === date
            },
            isLimit(date) {
                if (!this.limit) {
                    return false
                }
                if (this.today.getFullYear() > this.year) {
                    return true
                }
                if (this.today.getFullYear() == this.year && this.today.getMonth() > this.month) {
                    return true
                }
                if (this.today.getFullYear() == this.year && this.today.getMonth() == this.month && this.today.getDate() > date) {
                    return true
                }
            },
            onChoose(date) {
                if (this.isLimit(date)) {
                    return false
                }
                this.target.setDate(date)
                this.$emit('input', this.target.getTime())
            }
        }
    }
</script>
<style scoped lang='scss'>
    .calendar {
        width: 280px;
        background-color: $color-white;
        border-radius: 4px;

        .top {
            display: flex;
            align-items: center;
            height: 40px;

            > span {
                flex: 1;
                text-align: center;
                color: $color-tip;

                > b {
                    color: $color-title;
                }
            }

            > a {
                width: 40px;
                line-height: 40px;
                text-align: center;

                &:hover {
                    background-color: $color-back-hover;
                }
            }
        }

        .week {
            display: flex;
            align-items: center;
            background-color: $color-back-hover;

            > span {
                width: 40px;
                line-height: 20px;
                text-align: center;
                font-size: 12px;
                color: $color-tip;
            }
        }

        .date {
            display: flex;
            flex-wrap: wrap;

            > a {
                width: 40px;
                height: 30px;
                line-height: 30px;
                text-align: center;

                &:hover {
                    background-color: $color-back-hover;
                }

                &.on {
                    color: $color-blue;
                }

                &.off {
                    color: #dddddd;
                    background-color: white;
                    cursor: default;
                }
            }

            > span {
                width: 40px;
                height: 30px;
            }
        }
    }
</style>
