<template>
    <div class="totals">
        <panel>
            <div class="option">
                <div>学生总数</div>
                <div>{{studentCount}}</div>
            </div>
        </panel>
        <panel>
            <div class="option">
                <div>学生外出总数</div>
                <div>{{outStudentCount}}</div>
            </div>
        </panel>
    </div>
</template>

<script>
    import panel from '../panel'
    import biController from '../../../../controller/bi'

    export default {
        components: {panel},
        props: {
            refresh: {}
        },
        data() {
            return {
                studentCount: 0,
                outStudentCount: 0,
                outCarCount: 0,
                opCarCount: 0
            }
        },
        watch: {
            refresh() {
                this.doRefresh()
            }
        },
        mounted() {
            this.doRefresh()
        },
        methods: {
            async doRefresh() {
                let [studentCount, outStudentCount, outCarCount, opCarCount] = await biController.getTotal();
                this.studentCount = studentCount;
                this.outStudentCount = outStudentCount;
                this.outCarCount = outCarCount;
                this.opCarCount = opCarCount;
            }
        }
    }
</script>

<style scoped lang="scss">
    .totals {
        display: flex;
        align-items: center;

        > .panel {
            flex: 1;
            height: 214px;
            margin-right: 24px;
            background-size: 150px;
            background-position: right bottom;
            background-repeat: no-repeat;

            &:last-child {
                margin-right: 0;
            }

            &:nth-child(1) {
                background-image: url("./assets/img-1.png");
            }

            &:nth-child(2) {
                background-image: url("./assets/img-2.png");
            }

            &:nth-child(3) {
                background-image: url("./assets/img-3.png");
            }

            &:nth-child(4) {
                background-image: url("./assets/img-4.png");
            }
        }

        .option {
            padding: 32px 0 0 32px;

            > div {
                &:first-child {
                    color: $color-title;
                    font-size: 16px;
                    font-weight: bold;
                }

                &:last-child {
                    color: #17243D;
                    font-weight: bold;
                    font-size: 48px;
                }
            }
        }
    }
</style>