<template>
    <panel>
        <panel-title mode="small">
            <div class="title">
                <slot></slot>
            </div>
            <buttons :source="isCarMode?carButtons:vacationButtons" v-model="btn"></buttons>
        </panel-title>
        <div class="canvas">
            <svg ref="canvas"></svg>
        </div>
    </panel>
</template>

<script>
    import panel from '../panel'
    import panelTitle from '../panel-title'
    import buttons from '../../../component/buttons'
    import * as d3 from 'd3'
    import biController from '../../../../controller/bi'

    export default {
        components: {panel, panelTitle, buttons},
        props: {
            type: {},
            month: {},
            refresh: {},
        },
        data() {
            return {
                carButtons: ['每日派车数', '各单位派车数'],
                vacationButtons: ['每日学生请假数', '学院学生请假'],
                btn: 0,
                source: []
            }
        },
        watch: {
            month() {
                this.doRefresh()
            },
            btn() {
                this.doRefresh()
            },
            refresh() {
                this.doRefresh()
            }
        },
        computed: {
            isCarMode() {
                return this.type == 'car'
            }
        },
        mounted() {
            this.initCanvas();
            this.doRefresh()
        },
        methods: {
            async doRefresh() {
                if (this.isCarMode) {
                    await this.loadCarCount();
                } else {
                    await this.loadStudentCount();
                }
                this.draw();
            },
            getParams() {
                let month = biController.months[this.month];
                let start = new Date(`${month.year}-${month.month}-1`);
                start.setDate(start.getDate() - 1);
                let end = new Date(`${month.year}-${month.month + 1}-1`);
                end.setDate(end.getDate() - 1);
                return {minTime: start.getTime(), maxTime: end.getTime()};
            },
            async loadStudentCount() {
                if (this.btn == 0) {
                    this.source = await biController.getCountStudentDaily(this.getParams());
                } else {
                    this.source = await biController.getCountStudentDepartment(this.getParams());
                }
            },
            async loadCarCount() {
                if (this.btn == 0) {
                    this.source = await biController.getCountCarControlDaily(this.getParams());
                } else {
                    this.source = await biController.getCountCarControlDepartment(this.getParams());
                }
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
                        .call(d3.axisLeft(scaleY).tickSize(-1000)),

                    canvas.selectAll('c-rect')
                        .data(this.source)
                        .enter().append('rect')
                        .attr('class', 'c-rect')
                        .attr('x', (d, i) => scaleX(i) - 3)
                        .attr('width', 6)
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
    .panel {
        .canvas {
            padding: 0 32px;

            svg {
                width: 100%;
                height: 260px;
            }
        }
    }
</style>