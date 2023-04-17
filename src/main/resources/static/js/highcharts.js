$(document).ready(
    function() {
        $.ajax({
            url : "/get-data",
            success : function(result) {
                var date = JSON.parse(result).date;
                var weight = JSON.parse(result).weight;
                drawLineChart(date, weight);
            }
        });
    });
function drawLineChart(date, weight) {
    Highcharts.chart('container-bar', {
        chart : {
            type : 'line',
            styledMode : true,
            width: 500
        },
        title : {
            text : 'Weight history'
        },
        xAxis : [ {
            title : {
                text : 'Date'
            },
            categories : date
        } ],
        yAxis : [ {
            title : {
                text : 'Weight'
            }
        } ],
        series : [ {
            data : weight
        } ]
    });
}