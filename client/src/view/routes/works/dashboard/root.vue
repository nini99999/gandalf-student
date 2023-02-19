<template>
    <div>
        <total v-if="hasPermission('home_total')" :refresh="refresh"></total>
        <div class="options">
            <my-request class="option" type="car" :user="user" :refresh="refresh"
                        @car="onCar"
                        v-if="hasPermission('home_car_my')">
                我的用车申请
            </my-request>
            <my-request class="option" type="vacation" :user="user" :refresh="refresh"
                        @vacation="onVacation"
                        v-if="hasPermission('home_vacation_my')">我的请假审批
            </my-request>
            <request-list class="option" type="car" :user="user" :refresh="refresh"
                          @car="onCar"
                          v-if="hasPermission('home_car_mgr')">用车申请管理
            </request-list>
            <request-list class="option" type="vacation" :user="user" :refresh="refresh"
                          @vacation="onVacation"
                          v-if="hasPermission('home_vacation_mgr')">请销假管理
            </request-list>
            <statistics class="option" type="car" :user="user" :month="month" :refresh="refresh"
                        v-if="hasPermission('home_car_data')">
                派车每日关键指标统计
            </statistics>
            <statistics class="option" type="vacation" :user="user" :refresh="refresh" :month="month"
                        v-if="hasPermission('home_vacation_data')">
                人员考勤每日关键指标统计
            </statistics>
        </div>
        <div class="mask" v-if="vacation">
            <vacation :source="vacation" @close="vacation=null"></vacation>
        </div>
        <div class="mask" v-if="car">
            <car-info :user="user" :source="car" @close="car=null"></car-info>
        </div>
    </div>
</template>

<script>
    import total from './total'
    import requestList from './request-list'
    import statistics from './statistics'
    import myRequest from './my-request'
    import vacation from './vacation'
    import CarInfo from "./car";

    export default {
        components: {CarInfo, total, requestList, statistics, myRequest, vacation},
        props: {
            user: {},
            month: {}
        },
        data() {
            return {
                refresh: 0,
                vacation: null,
                car: null
            }
        },
        mounted() {
            this.timer = setInterval(() => {
                this.refresh++;
            }, 30000)
        },
        beforeDestroy() {
            clearInterval(this.timer);
        },
        methods: {
            hasPermission(key) {
                return this.user.permissions.indexOf(key) >= 0;
            },
            onVacation(item) {
                this.vacation = item;
                console.info('----set vacation', item)
            },
            onCar(item) {
                this.car = item
                console.info('---set car', item)
            }
        }
    }
</script>

<style scoped lang="scss">
    .totals {
        margin-bottom: 24px;
    }

    .options {
        display: flex;
        flex-wrap: wrap;
    }

    .option {
        width: calc(50% - 12px);
        height: 364px;
        margin-bottom: 24px;

        &:nth-child(2n+1) {
            margin-right: 24px;
        }
    }
</style>