<template>

  <div class="mod-demo-echarts">
      <div style="margin:10px;text-align: right;">
        <el-input v-model="keyWords" placeholder="关键字" style="width:200px"></el-input>
        <el-button @click="getDataList()">查询</el-button>
      </div>
    <!-- <el-alert
      title="提示："
      type="warning"
      :closable="false">
      <div slot-scope="description">
        <p class="el-alert__description">1. 此Demo只提供ECharts官方使用文档，入门部署和体验功能。具体使用请参考：http://echarts.baidu.com/index.html</p>
      </div>
    </el-alert> -->

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartLineBox" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>
<script>
  import echarts from 'echarts'
  export default {
    data () {
      return {
        chartLine: null,
        keyWords:'',
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        option:{
          'title': {
            'text': '折线图'
          },
          'tooltip': {
            'trigger': 'axis',
            formatter: function(datas){
                var dataArr = datas[0].name.split("\\n");
                var res = dataArr[0]+"&nbsp;&nbsp;"+dataArr[1] + '<br/>', val;
                res += '答题总数：'+dataArr[2]+'<br/>';
                res += '正确数：'+dataArr[3]+'<br/>';
                res += '退出数：'+dataArr[4]+'<br/>';
                for(var i = 0, length = datas.length; i < length; i++) {
                    val = (datas[i].value) + '%';
                    res += '<span style="color:'+datas[i].color+';">●</span>';
                    res += datas[i].seriesName + '：' + val + '<br/>';
                }
                return res;
            }
          },
          'legend': {
            'data': [ '退出率', '正确率', '失败率']
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
            'boundaryGap': false,
            'data': [],
            'axisLabel': {
                interval: 0,
                formatter:function(value)
                {
                    return value.split("\\n")[1];
                }
            }
            //'subject':['剑桥大学是在哪个国家？\n英国', '电影《霸王别姬》中，\n张国荣扮演的人物是？\n程蝶衣', '以下图片科学家的名字是？\n爱因斯坦', '周星驰的影片《功夫》中，\n是哪个帮派在电影里跳了一段舞蹈？\n斧头帮', '电影《速度与激情》系列截至2018年一共有多少部？\n八部', '以下图片政治家的名字是？\n奥巴马', '电视剧《西游记》里火焰山的火是谁放的？\n孙悟空', '八戒为什么长得像个猪？\n投错胎了' , '以下图片公主的名字是？\n白雪公主', '以下音乐的常用于？\n新闻联播']
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
              'name': '退出率',
              'type': 'line',
              //'stack': '总量',
              'data': []
            },
            {
              'name': '正确率',
              'type': 'line',
              //'stack': '总量',
              'data': []
            },
            {
              'name': '失败率',
              'type': 'line',
              //'stack': '总量',
              'data': []
            }
          ]
        }
      }
    },
    mounted () {
    
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartLine) {
        this.chartLine.resize()
      }
    },
    methods: {
      init(data,pageIndex,pageSize,totalPage){
        if(data == "img[preview]"){return ;}
        debugger;
        console.log(data);
        var answer = [];
        var subject= [];
        var exitRatio = [];
        var rightRatio = [];
        var errorRatio = [];
        for(var i =0 ;i< data.length;i++){
            var obj = data[i];
            answer.push(obj.promptWord+"\\n"+obj.answer+"\\n"+obj.replyAftNumber+"\\n"+obj.rightNumber+"\\n"+obj.exitNumber); 
            // answer.push(obj.promptWord+"\\n"+obj.answer+"\\n"+obj.replyAftNumber+"\\n"+obj.rightNumber+"\\n"+obj.likeNumber+"\\n"+obj.exitNumber);  
            subject.push(obj.promptWord);
            exitRatio.push(Math.floor(obj.exitRatio*100));
            rightRatio.push(Math.floor(obj.rightRatio*100));
            errorRatio.push(Math.floor(obj.errorRatio*100));     
        }
        this.option.xAxis.data = answer;
        this.option.series[0].data = exitRatio;
        this.option.series[1].data = rightRatio;
        this.option.series[2].data = errorRatio;  
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.initChartLine();
      },
      // 折线图
      initChartLine () { 
        this.chartLine = echarts.init(document.getElementById('J_chartLineBox'));   
        this.chartLine.resize();
        this.chartLine.setOption(this.option)
        window.addEventListener('resize', () => {
          this.chartLine.resize();
        })
      },
      getDataList () {
        debugger;
        this.dataListLoading = true;
        this.$http({
          url: this.$http.adornUrl('/smallLookGuess/getSubjectRatio'),
          method: 'get',
          params: this.$http.adornParams({
            'orderType': -1,
            'keyWords': this.keyWords ,
            'pageIndex': this.pageIndex,
            'pageSize': this.pageSize
          })
        }).then(({data}) => {
           debugger
          if (data && data.Tag === 1) {
            this.init(data.Result,this.pageIndex,this.pageSize,data.TotalCount); 
          } else {
            //this.$message.error('没有查找到数据');
            this.totalPage = 0;
          }
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val;
        this.pageIndex = 1;
        this.getDataList();
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val;
        this.getDataList();
      
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
