<template>

  <div class="mod-demo-echarts">
      <div style="margin:10px;text-align: right;">
        <!-- <el-input v-model="keyWords" placeholder="关键字" style="width:200px"></el-input>
        <el-button @click="getDataList()">查询</el-button> -->
      </div>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartBarBox" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import echarts from 'echarts'
  export default {
    data () {
      return {
        chartBar: null,
        keyWords:'',
        option:{
          'title': {
            'text': '柱状图'
          },
          'tooltip': {
            'trigger': 'axis',            
          },
          'legend': {
            'data': []
          },
          'grid': {
            'left': '3%',
            'right': '4%',
            'bottom': '3%',
            'containLabel': true
          },
          'toolbox': {
            'feature': {
              'saveAsImage': { }
            }
          },
          'xAxis': {
            'type': 'category',
            //'boundaryGap': false,
            'data': ['简单占有率','一般占有率','困难占有率'],
            'axisLabel': {
                interval: 0,
                formatter:function(value)
                {
                    return value;
                }
            }
          },
          'yAxis': {
                type:'value',
                min:0,
                max:100,
                splitNumber:10,
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    formatter: '{value} %'
                },
                show: true
          },
          'series': [
            {
              'name': '百分比',
              'type': 'bar',
              //'stack': '总量',
              'data': []
            },
          ]
        }
      }
    },
    mounted () {
    
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartBar) {
        this.chartBar.resize()
      }
    },
    methods: {
      init(data){
        if(data == "img[preview]"){return ;}
        debugger;
        //console.log(data);
        var simpleRatio = [];
        var commonRatio = [];
        var hardRatio = [];
        for(var i =0 ;i< data.length;i++){
            var obj = data[i];
            simpleRatio.push(obj.simpleRatio);
            simpleRatio.push(obj.commonRatio);
            simpleRatio.push(obj.hardRatio); 
        }
        this.option.series[0].data = simpleRatio;
        this.initChartBar();
      },
      // 圆柱
      initChartBar () { 
        this.chartBar = echarts.init(document.getElementById('J_chartBarBox'));   
        this.chartBar.resize();
        this.chartBar.setOption(this.option)
        window.addEventListener('resize', () => {
          this.chartBar.resize();
        })
      },
      getDataList () {
        debugger;
        this.dataListLoading = true;
        this.$http({
          url: this.$http.adornUrl('/smallVientianeTest/subjectLevelTypeRatio/get'),
          method: 'get',
          params: this.$http.adornParams({
          
          })
        }).then(({data}) => {
           debugger
          if (data && data.Tag === 1) {
            this.init(data.Result); 
          } else {
            //this.$message.error('没有查找到数据');
            this.totalPage = 0;
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  .mod-demo-echarts {
    > .el-alert {
      margin-bottom: 10px;
    }
    > .el-row {
      margin-top: -10px;
      margin-bottom: -10px;
      .el-col {
        padding-top: 10px;
        padding-bottom: 10px;
      }
    }
    .chart-box {
      min-height: 400px;
    }
  }
</style>
