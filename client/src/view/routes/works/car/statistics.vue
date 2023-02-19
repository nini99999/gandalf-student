<template>
    <div>
        <panel class="dash">
            <panel-title>
                <div class="title">当日车辆运转情况</div>
            </panel-title>
            <div class="content">
                <div class="name">当日出勤车辆</div>
                <div class="options o1">
                    <div class="option" v-for="(item,i) in control" :key="i">
                        <div class="car">{{item.carNO}}</div>
                        <div>用车人：{{item.userName}}</div>
                        <div class="driver">司机：{{item.driverName}}</div>
                    </div>
                    <div class="none" v-if="control.length==0">暂无记录</div>
                </div>
                <div class="name">当日保养情况</div>
                <div class="options o2">
                    <div class="option" v-for="(item,i) in maintain" :key="i">
                        <div class="car">{{item.carNO}}</div>
                        <div>用车人：{{item.userName}}</div>
                    </div>
                    <div class="none" v-if="maintain.length==0">暂无记录</div>
                </div>
                <div class="name">当日维修情况</div>
                <div class="options o3">
                    <div class="option" v-for="(item,i) in repair" :key="i">
                        <div class="car">{{item.carNO}}</div>
                        <div>用车人：{{item.userName}}</div>
                    </div>
                    <div class="none" v-if="repair.length==0">暂无记录</div>
                </div>
                <div class="name">当日待命车辆</div>
                <div class="options o3">
                    <div class="option" v-for="(item,i) in stand" :key="i">
                        <div class="car">{{item.carNO}}</div>
                        <div>用车人：{{item.userName}}</div>
                    </div>
                    <div class="none" v-if="stand.length==0">暂无记录</div>
                </div>
            </div>
        </panel>
        <panel class="statistics">
            <panel-title mode="small">
                <div class="title">
                    车辆运营指标统计
                    <combobox class="month" :source="months" v-model="month"></combobox>
                </div>
                <buttons :source="btnSource" v-model="btn"></buttons>
            </panel-title>
            <div class="canvas">
                <svg ref="canvas" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"></svg>
            </div>
        </panel>
    </div>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import buttons from '../../../component/buttons'
    import combobox from '../../../component/combobox'
    import * as d3 from 'd3'
    import carController from '../../../../controller/car'
    import biController from '../../../../controller/bi'

    export default {
        components: {panel, panelTitle, combobox, buttons},
        data() {
            return {
                btnSource: ['每日用车统计', '部门用车量统计'],
                btn: 0,
                months: biController.months,
                month: biController.months.length - 1,
                control: [],
                stand: [],
                maintain: [],
                repair: [],
                source: []
            }
        },
        watch: {
            month() {
                this.refresh();
            },
            btn() {
                this.refresh();
            }
        },
        mounted() {
            this.initCanvas();
            this.getNowControlCar();
            this.getNowStandByCar();
            this.getMaintainCar();
            this.getRepairCar();
            this.refresh();
        },
        methods: {
            async getNowControlCar() {
                this.control = await carController.getNowControlCar();
            },
            async getNowStandByCar() {
                this.stand = await carController.getNowStandByCar();
            },
            async getMaintainCar() {
                this.maintain = await carController.getMaintainCar();
            },
            async getRepairCar() {
                this.repair = await carController.getRepairCar();
            },
            async refresh() {
                let month = biController.months[this.month];
                let start = new Date(`${month.year}-${month.month}-1`);
                start.setDate(start.getDate() - 1);
                let end = new Date(`${month.year}-${month.month + 1}-1`);
                end.setDate(end.getDate() - 1);
                let params = {minTime: start.getTime(), maxTime: end.getTime()};
                if (this.btn == 0) {
                    this.source = await biController.getCountCarControlDaily(params);
                } else {
                    this.source = await biController.getCountCarControlDepartment(params);
                }
                this.draw();
            },
            initCanvas() {
                let svg = this.$refs.canvas;
                let canvas = d3.select(svg);
                canvas.append('linearGradient')
                    .attr('id', 'blue')
                    .attr('gradientTransform', 'rotate(90)')
                    .selectAll('stop')
                    .data([
                        {offset: '10%', color: '#2D8CF0'},
                        {offset: '90%', color: '#7FBEFE'}
                    ])
                    .enter().append('stop')
                    .attr('offset', d => d.offset)
                    .attr('stop-color', d => d.color)
            },
            draw() {
                let max = this.source.reduce((max, item) => {
                    item.value = parseInt(item.value);
                    if (this.btn == 0) {
                        item.name = item.name.substr(item.name.length - 2)
                    }
                    return Math.max(max, item.value)
                }, 0);
                max = max * 1.3;
                if (max < 10) {
                    max = 10;
                }
                let svg = this.$refs.canvas;
                let {width, height} = svg.getBoundingClientRect();
                let canvas = d3.select(svg);
                let scaleX = d3.scaleLinear().range([20, width]).domain([-1, this.source.length]);
                let scaleY = d3.scaleLinear().range([height - 16, 10]).domain([0, max]);
                if (this.views) {
                    this.views.forEach(view => {
                        view.remove();
                    })
                }

                this.views = [
                    canvas.append("g")
                        .attr("transform", "translate(20,0)")
                        .call(d3.axisLeft(scaleY).tickSize(-2000)),

                    canvas.selectAll('c-rect')
                        .data(this.source)
                        .enter().append('rect')
                        .attr('class', 'c-rect')
                        .attr('x', (d, i) => scaleX(i) - 5)
                        .attr('width', 10)
                        .attr('y', d => scaleY(d.value))
                        .attr('height', d => scaleY(0) - scaleY(d.value))
                        // .attr('rx', 5)
                        .attr('fill', 'url(#blue)'),
                    canvas.selectAll('c-value')
                        .data(this.source)
                        .enter().append('text')
                        .attr('class', 'c-value')
                        .attr('x', (d, i) => scaleX(i))
                        .attr('y', d => scaleY(d.value) - 6)
                        .attr('color', '#808695')
                        .attr('font-size', 10)
                        .attr('text-anchor', 'middle')
                        .text(d => d.value > 0 ? d.value : ''),
                    canvas.selectAll('c-label')
                        .data(this.source)
                        .enter().append('text')
                        .attr('class', 'c-label')
                        .attr('x', (d, i) => scaleX(i))
                        .attr('y', scaleY(0) + 12)
                        .attr('fill', 'currentColor')
                        .attr('font-size', 10)
                        .attr('text-anchor', 'middle')
                        .text(d => d.name)
                ];
                canvas.selectAll('.domain').attr('stroke', '#E9EBF0');
                canvas.selectAll('line').attr('stroke', '#E9EBF0');
                canvas.selectAll('text').attr('color', '#808695');
            }
        }
    }
</script>

<style scoped lang="scss">
    .dash {
        margin-bottom: 24px;

        .name {
            font-weight: bold;
            font-size: 16px;
            line-height: 70px;
        }

        .option {
            display: inline-block;
            width: 176px;
            padding: 32px 0;
            margin-right: 20px;
            margin-bottom: 20px;
            background-color: #F7F9FC;
            text-align: center;
            margin-bottom: 14px;

            &.blank {
                background-color: transparent;
            }

            .car {
                display: inline-block;
                width: 112px;
                line-height: 22px;
                font-size: 16px;
                font-weight: bold;
                margin-bottom: 24px;
            }

            .driver {
                margin-top: 16px;
            }

            .num {
                font-size: 40px;
                font-weight: bold;
                margin-bottom: 20px;
            }
        }

        .none {
            line-height: 100px;
            text-align: center;
            color: $color-tip;
        }

        .o1 {
            .car {
                background-color: rgba($color-blue, 0.2);
            }
        }

        .o2 {
            .car {
                background-color: rgba($color-tip-yellow, 0.2);
            }
        }

        .o3 {
            .car {
                background-color: rgba($color-tip-red, 0.2);
            }
        }
    }

    .statistics {
        height: 362px;

        .month {
            width: 150px;
            min-width: 100px;
        }

        .canvas {
            padding: 0 32px;

            svg {
                width: 100%;
                height: 260px;
            }
        }
    }

</style>