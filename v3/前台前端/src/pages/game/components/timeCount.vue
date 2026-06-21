<template>
  <div class="canvas-box" :class="{small:small}">
    <canvas canvas-id="canvas">
      <cover-view class="time" :class="{heart:showHeart,fff:empty}">{{time}}</cover-view>
    </canvas>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  props: ["stopBtn","initBtn","startBtn","small","initTime","empty"],
  data() {
    return {
      timer: null,
      time: 10,
      cxt_arc: null,
      rangRun: 1,
      cuntDownCir: null,
      countdownNum: 10,
      allowDo: true,
      initNum: 10,
      spaceNum: 1000,
      space: 30,
      begin: -(2.5 * Math.PI),
      pai2: 2 * Math.PI,
      playType:false,
      showHeart:false,
    };
  },
  watch: {
    initBtn() {
      this.stopTimer();
      clearInterval(this.cuntDownCir);
      // this.countdownNum = 10;
      // this.time = 10;
      // this.drawRang(1000);
      this.countdownNum = 10;
      this.time = this.initTime;
      this.drawRang(1-(this.time/10));

      this.playType = false;
      this.showHeart = false;
      console.log('initTime')
    },
    startBtn() {
      this.stopTimer();
      this.allowDo = true;
      this.countdown();
      this.startTimer();
    },
    initTime(){
      this.time=this.initTime;
    },
    stopBtn() {
      this.allowDo = false;
      clearInterval(this.cuntDownCir);
      this.stopTimer();
    },
    /** 倒计时 ≤3 秒时播放警示音（globalSoundPlayer 可能未初始化，加空检查） */
    time(val){
      if(val<=3&&!this.playType){
        if (this.globalSoundPlayer) {
          try {
            this.globalSoundPlayer.src='https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/audio/time.mp3';
            this.globalSoundPlayer.play();
          } catch(e) {}
        }
        this.playType = true;
        this.showHeart = true;
      }
      if(val<=0){
        this.time=0;
        this.playType = false;
        this.showHeart = false;
        this.$emit('timeout');
      }
    },
  },
  computed:{
    ...mapState(["globalSoundPlayer","gamePageType"]),
    poaitionX(){
      if(this.small){
      return (this.store.state.deviceInfo.width/2)*.7;
      }else{
        return this.store.state.deviceInfo.width/2;
      }
    },
  },
  methods: {
    initFn() {
      this.stopTimer();
      clearInterval(this.cuntDownCir);
      this.countdownNum = 10;
      this.time = this.initTime;
      this.drawRang(1-this.initTime/10);
    },
    /** 初始化倒计时音效（globalSoundPlayer 可能因 CDN 宕而未初始化，加空检查） */
    initAudio(){
      if (this.globalSoundPlayer) {
        try { this.globalSoundPlayer.src = '/static/audio/time.mp3'; } catch(e) {}
      }
    },
    startTimer() {
      this.timer = setInterval(() => {
        this.time--;
      }, 1000);
        // this.timer = setTimeout(()=>{
        //    this.time--;
        // },1000)
    },
    stopTimer() {
      clearInterval(this.timer);
    },
    drawRang(precent) {
      this.cxt_arc.setLineWidth(1);
      if(this.empty&&this.gamePageType==0){
        this.cxt_arc.setFillStyle("#7efce4");
       }else if(this.empty&&this.gamePageType==1){
        this.cxt_arc.setFillStyle("#fa9c4d");
       }else{
        this.cxt_arc.setFillStyle("#372f5b");
       }
      if(!this.empty){
        this.cxt_arc.setLineCap("round");
      }else{
        this.cxt_arc.setLineCap("square");
      }
      this.cxt_arc.beginPath(); //开始一个新的路径
      this.cxt_arc.arc(
        0.215 * this.poaitionX,
        0.215 * this.poaitionX,
        0.19 * this.poaitionX,
        0,
        this.pai2,
        false
      ); //设置一个原点(106,106)，半径为100的圆的路径到当前路径
      this.cxt_arc.fill(); //对当前路径进行描边
      this.cxt_arc.closePath(); //开始一个新的路径

      
        this.cxt_arc.beginPath(); //开始一个新的路径
        if(this.empty&&this.gamePageType==0){
          this.cxt_arc.setFillStyle("#28235c");
        }else if(this.empty&&this.gamePageType==1){
          this.cxt_arc.setFillStyle("#3f86fc");
        }else{
          this.cxt_arc.setFillStyle("#fff");
        }
        this.cxt_arc.arc(
          0.215 * this.poaitionX,
          0.215 * this.poaitionX,
          0.140 * this.poaitionX,
          0,
          this.pai2,
          false
        ); //设置一个原点(106,106)，半径为100的圆的路径到当前路径
        this.cxt_arc.fill(); //对当前路径进行描边
      


      var end = this.pai2 * precent + this.begin;
      if (precent == 0) {
        end = this.pai2 * +this.begin;
      } else if (1 == precent) {
        this.cxt_arc.draw();
        return;
      }
      this.cxt_arc.setLineWidth(7);
      // this.cxt_arc.setStrokeStyle("#67c29f");
    if(this.time<=3){
      this.cxt_arc.setStrokeStyle("#fb5b5b");
     }else{
       if(this.empty&&this.gamePageType==0){
        this.cxt_arc.setStrokeStyle("#3c358b");
       }else if(this.empty&&this.gamePageType==1){
        this.cxt_arc.setStrokeStyle("#ffffff");
       }else{
         this.cxt_arc.setStrokeStyle("#0071bb");
       }
     }
     if(!this.empty){
       this.cxt_arc.setLineCap("round");
     }
     
      this.cxt_arc.beginPath(); //开始一个新的路径
      this.cxt_arc.arc(
        0.215 * this.poaitionX,
        0.215 * this.poaitionX,
        0.164 * this.poaitionX,
        this.begin,
        end,
        true
      );
      this.cxt_arc.stroke(); //对当前路径进行描边
      this.cxt_arc.draw();
    },
    countdown() {
      // this.rangRun = 0;
      this.rangRun=10000-this.initTime*1000;
      clearInterval(this.cuntDownCir);
      this.cuntDownCir = setInterval(() => {
        var n = this.initNum - Math.floor(this.rangRun / this.spaceNum);//1000
        var precent = this.rangRun / (this.initNum * this.spaceNum);//1000
        this.countdownNum = n;
        if (this.allowDo) {
          this.drawRang(precent);
          this.rangRun = this.rangRun + this.space;
        } else {
          clearInterval(this.cuntDownCir);
        }
        if (precent >= 1) {
          this.allowDo = false;
          clearInterval(this.cuntDownCir);
          clearInterval(this.timer);
          this.drawRang(1);
        }
      }, this.space);//30
    }
  },
  mounted() {
    this.cxt_arc = wx.createCanvasContext("canvas");
    this.initFn();
    this.initAudio();
  }
};
</script>

<style lang="scss" scoped>
.heart{
  color:#fb5b5b!important;
  font-weight: bold;
  animation:heart 3s ease;
}
@keyframes heart {
  0%{
    transform:translate(-50%,-50%) scale(1.4);
  }
  33%{
    transform:(-50%,-50%) scale(1);
  }
  34%{
    transform:(-50%,-50%) scale(1.4);
  }
  66%{
    transform:(-50%,-50%) scale(1);
  }
  67%{
    transform:(-50%,-50%) scale(1.4);
  }
  100%{
    transform:(-50%,-50%) scale(1);
  }

}

.canvas-box {
  width: 22vw;
  height: 22vw;
  position: relative;
}
.time {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
  font-size: 40rpx;
  color: #000;
  z-index: 100;
}
canvas {
  width: 100%;
  height: 100%;
}
.init {
  position: absolute;
  bottom: 200rpx;
}
.start {
  position: absolute;
  bottom: 100rpx;
}
.stop {
  position: absolute;
  bottom: 0;
}
.small{
  width: 15.16vw;
  height: 15.16vw;
 .time{
   font-size: 30rpx;
 }

}
.fff{
  color: #fff;
}
</style>
