<template>
    <div>
        <div class="content">
            <svg ref="canvas"></svg>
        </div>
    </div>
</template>

<script>
    import * as d3 from 'd3'

    export default {
        mounted() {
            const min = 0
            const max = 1000
            let svg = this.$refs.canvas;
            let width = svg.clientWidth;
            let height = svg.clientHeight;
            this.canvas = d3.select(svg);
            this.scaleX = d3.scaleLinear().range([0, width]).domain([0, 10])
            this.scaleY = d3.scaleLinear().range([height, 0]).domain([min, max])

            this.canvas.append("g")
                .attr("transform", "translate(50, 350)")
                .call(d3.axisBottom(this.scaleX).tickSize(10));

            this.canvas.append("g")
                .attr("transform", "translate(50,-50)")
                .call(d3.axisLeft(this.scaleY).tickSize(-700));

            this.canvas.selectAll('.domain').attr('stroke', '#E9EBF0');
            this.canvas.selectAll('line').attr('stroke', '#E9EBF0');
            this.canvas.selectAll('text').attr('color', '#808695')

            this.canvas.append('linearGradient')
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

            let source = [
                {x: 3, y: 300}, {x: 5, y: 800}, {x: 8, y: 650}
            ]
            this.canvas.selectAll('c-rect')
                .data(source)
                .enter().append('rect')
                .attr('class', 'c-rect')
                .attr('x', d => this.scaleX(d.x) + 50 - 5)
                .attr('width', 10)
                .attr('y', d => this.scaleY(d.y) - 50)
                .attr('height', d => this.scaleY(max - d.y))
                // .attr('rx', 5)
                .attr('fill', 'url(#blue)');
        }
    }
</script>

<style scoped lang="scss">
    .content {
        margin-left: 100px;
        margin-top: 100px;
        width: 800px;
        height: 400px;
        background-color: white;

        svg {
            width: 100%;
            height: 100%;
        }
    }
</style>