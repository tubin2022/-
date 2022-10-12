export const mapOption = {
    basicOption:{
        tooltip: {
            trigger: 'item',
            formatter: '{b}:20'
        },
        geo: {
            top:100
            // layoutCenter: ['30%', '30%'],
            // // 如果宽高比大于 1 则宽度为 100，如果小于 1 则高度为 100，保证了不超过 100x100 的区域
            // layoutSize: 100
            // 设置这两个值后 left/right/top/bottom/width/height 无效。
        }
    },
    seriesOption:{
        type: 'map',
        roam: false,
        nameMap:{
            'china':'中国'
        },
        label: {
            normal:{
                show:false,
                textStyle:{
                    color:'#fff',
                }
            },
            emphasis: {
                show: true,
                textStyle:{
                    color:'#fff',
                }
            }
        },
        itemStyle: {
            normal: {
                color: '#fff',
                areaColor: '#6ED8FE',
                borderColor: '#0E94EB',
                borderWidth: 0.5,
                label: {
                    show: false,
                    textStyle: {
                        color: "rgb(249, 249, 249)",
                    }
                },
            },
            emphasis: {
                areaColor: 'green',
            }
        },
    }
}
