$(document).ready(
    function () {
        $.ajax({
            url: "/get-data",
            success: function (result) {
                const weightingDate = JSON.parse(result).weightingDate;
                const weight = JSON.parse(result).weight;
                const dreamWeight = JSON.parse(result).dreamWeight;
                drawLineChart(weightingDate, weight, dreamWeight);
            }
        });
    });

function drawLineChart(weightingDate, weight, dreamWeight) {
    Highcharts.chart('container-bar', {
        chart: {
            type: 'line',
            styledMode: true,
        },
        title: {
            text: 'Weight history chart'
        },
        xAxis: [{
            title: {
                text: 'Weighting Date'
            },
            categories: weightingDate
        }],
        yAxis: [{
            title: {
                text: 'Weight [kg]'
            },
            plotLines: [{
                width: 3,
                value: dreamWeight,
                zIndex: 3,
                label: {
                    text: 'Dream weight',
                    style: {
                        fontWeight: 'bold'
                    }
                }
            }]
        }],
        series: [{
            data: weight,
            showInLegend: false
        }],
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true
                }
            }
        },

    });
}