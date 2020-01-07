option2 = {
    tooltip: {//鼠标指上时的标线
        trigger: 'axis',
        axisPointer: {
            lineStyle: {
                color: '#fff'
            }
        }
    },
    legend: {
        icon: 'rect',
        itemWidth: 14,
        itemHeight: 5,
        itemGap: 13,
        data: ['会员车', '临时车'],
        right: '10px',
        top: '0px',
        textStyle: {
            fontSize: 12,
            color: '#fff'
        }
    },
    grid: {
        x: 55,
        y: 35,
        x2: 25,
        y2: 25,
    },
    xAxis: [{
        type: 'category',
        boundaryGap: false,
        axisLine: {
            lineStyle: {
                color: '#57617B'
            }
        },
        axisLabel: {
            textStyle: {
                color: '#fff',
            },
        },
        data: ['2010', '2011', '2012', '2013', '2014', '2015', '2016']
    }],
    yAxis: [{
        type: 'value',
        axisTick: {
            show: false
        },
        axisLine: {
            lineStyle: {
                color: '#57617B'
            }
        },
        axisLabel: {
            margin: 10,
            textStyle: {
                fontSize: 14
            },
            textStyle: {
                color: '#fff',
            },
        },
        splitLine: {
            lineStyle: {
                color: '#57617B'
            }
        }
    }],
    series: [{
        name: '会员车',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 1
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(137, 189, 27, 0.3)'
                }, {
                    offset: 0.8,
                    color: 'rgba(137, 189, 27, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        },
        itemStyle: {
            normal: {
                color: 'rgb(137,189,27)'
            }
        },
        data: [48853, 148544, 170177, 178062, 211649, 243867, 282175]
    }, {
        name: '临时车',
        type: 'line',
        smooth: true,
        lineStyle: {
            normal: {
                width: 1
            }
        },
        areaStyle: {
            normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(0, 136, 212, 0.3)'
                }, {
                    offset: 0.8,
                    color: 'rgba(0, 136, 212, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
            }
        },
        itemStyle: {
            normal: {
                color: 'rgb(0,136,212)'
            }
        },
        data: [52521, 69352, 104403, 130714, 203697, 239361, 150096]
    },]
};
$.ajax({
    url: "/rideweb/main22",
    type: "POST",
    dataType: "json",
    success: function (data) {
        // console.log(data);
        option2 = {
            series: [{
                name: '会员车',

                data: data[0]
            }, {
                name: '临时车',

                data: data[1]
            },]
        };
        myChart2.setOption(option2);
    },
    error: function (err) {
        console.log(err);
    }
});

$.ajax({
    url: "/rideweb/sum",
    type: "POST",
    dataType: "json",
    success: function (data) {
        // console.log(data);
        $("body > div.visual > div.visual_con > div.visual_conTop > div:nth-child(1) > div > p").html(data[0]);
        $("body > div.visual > div.visual_con > div.visual_conTop > div:nth-child(2) > div > p").html(data[1]);
    },
    error: function (err) {
        console.log(err);
    }
});

$.ajax({
    url: "/rideweb/map",
    type: "POST",
    dataType: "json",
    success: function (data) {
        // console.log(data);
        aa = eval(data[0]);
        option8 = {

            title: {
                text: '',
                left: 'center',
                textStyle: {
                    color: '#fff'
                }
            },
            legend: {
                show: false,
                orient: 'vertical',
                top: 'bottom',
                left: 'right',
                data: ['地点', '线路'],
                textStyle: {
                    color: '#fff'
                }
            },
            geo: {
                map: 'world',
                label: {
                    emphasis: {
                        show: false
                    }
                },
                roam: true,
                itemStyle: {
                    normal: {
                        areaColor: 'rgba(7,21,57,0.5)',
                        borderColor: '#0177ff'
                    },
                    emphasis: {
                        areaColor: '#071537'//鼠标指上市时的颜色
                    }
                }
            },
            series: [{
                name: '地点',
                type: 'effectScatter',
                coordinateSystem: 'geo',
                zlevel: 2,
                rippleEffect: {
                    brushType: 'stroke'
                },
                label: {
                    emphasis: {
                        show: true,
                        position: 'right',
                        formatter: '{b}'
                    }
                },
                symbolSize: 2,
                itemStyle: {
                    showEffectOn: 'render',
                    normal: {
                        color: '#46bee9'
                    }
                },
                data: aa
            }

            ]
        };
        myChart8.setOption(option8);
    },
    error: function (err) {
        console.log(err);
    }
});
