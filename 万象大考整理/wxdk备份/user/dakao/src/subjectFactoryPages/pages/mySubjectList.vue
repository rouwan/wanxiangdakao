<template>
  <div class="m-subject-list sky-bg-2">
    <div class="u-head">
      <div v-for="(item,index) in tabList"
           :key="index"
           :class="{active:index==current}"
           @click="changeList(index)">
        {{item}}
      </div>
    </div>
    <scroll-view class="u-list"
          scroll-y="true"
          scroll-with-animation="true"
          @scrolltolower="loadNextPage"
         v-if="dataReady&&subjectList[current].length>0">
      <div class="u-item"
           v-for="(item,index) in subjectList[current]"
           :key="index"
           @click="handleRoute(item.id)"
           :class="{'u-type-1':item.status==0,'u-type-2':item.status==2}">
        <div class="u-title"><div>【{{typeMap[item.parentType]}}】</div>{{item.title}}</div>
        <div class="u-info">
          <div class="u-status"
               v-if="current!=0">
            <div class="u-ball"></div>
            {{subjectType[item.status]}}
          </div>
          <div class="u-num"
               v-if="current==0">
            <div class="th">正确率:</div>
            <div class="txt">{{item.rightRatio}}%</div>
            <div class="th">答题人数:</div>
            <div class="txt">{{item.replyNum}}</div>
          </div>
          <div class="btn"
               v-if="current==0">
            <img class="get"
                 @click.stop="getGiftFn(item,index)"
                 v-if="statusList[index]==0"
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/getBook.png">
            <img class="receved"
                 v-else
                 src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/day-gift/receved-txt.png">
          </div>
        </div>
      </div>
    </scroll-view>
    <div class="u-empty"
         v-if="subjectList[current].length==0">
      <img src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/list-empty.png">
      <div v-if="!hasQues&&subjectList[current].length==0"
           class="u-empty-word">您尚未参与出题</div>
      <div v-if="hasQues&&subjectList[current].length==0"
           class="u-empty-word">暂无题目</div>
      <div v-if="!hasQues&&subjectList[current].length==0"
           class="u-empty-btn"
           @click="toSelectSubject">我要出题</div>
    </div>
        <get-gift v-if="showGetGift"
              :giftList="gift"
              @close="showGetGift=false"></get-gift>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import subjectApi from "@/api/subjectApi";
import getGift from "@/components/gift/get-gift";

export default {
  components:{
    "get-gift":getGift,
  },
  data() {
    return {
      showGetGift:false,
      dataReady: false,
      current: 0,
      hasQues: false,
      gift:[{name:"book",url:"",num:1,}],
      subjectList: [[], [], []],
      tabList: ["已通过", "审核中", "未通过"],
      subjectType: { 0: "审核中", 1: "已通过", 2: "未通过" },
      indexMap: { 0: 1, 1: 0, 2: 2 },
      pageSize:10,
      pageIndex:0,
      statusList:[],
      typeMap:{
        2:"商",
        3:"文",
        4:"理",
        5:"艺",
        6:"体",
        7:"综",
      },
      bookMap: {
        2:{img:"/static/icon/shang.png",name:"商科"},
        3:{img:"/static/icon/wen.png",name:"文科"},
        4:{img:"/static/icon/li.png",name:"理科"},
        5:{img:"/static/icon/yi.png",name:"艺科"},
        6:{img:"/static/icon/ti.png",name:"体科"},
        7:{img:"/static/icon/zong.png",name:"综科"},
      },
    };
  },
  computed: {
    ...mapState(["appInfo",])
  },
  methods: {
    // getList(){
    //   subjectApi.getSubjectList({pageIndex:0,pageSize:100}).then(res=>{
    //     this.subjectList[0]=res.Result;
    //     if(this.subjectList[0].length>0){
    //       this.hasQues=true;
    //     }
    //     this.dataReady=true;
    //     wx.hideLoading();
    //   })
    // },
    getGiftFn(item,index) {
      this.fly.post("subject/receiveGood",{
        subjectId:item.id
      }).then(res=>{
        console.log(res);
        this.statusList[index]=1;
        for(let key in res.data.Result){
          this.gift = [{
            name:this.bookMap[key].name,
            url:this.bookMap[key].img,
            num:res.data.Result[key],
          }]
        }
        setTimeout(() => {
          this.showGetGift=true;
        }, 500);
      })
     
    },
    changeList(index) {
        if(index==0){
          this.pageIndex = Math.ceil(this.subjectList[0].length/this.pageSize)-1;
        }
      if (this.subjectList[index].length == 0) {
        wx.showLoading({
          title: "加载中",
          mask: true
        });
        this.pageIndex=0;
        subjectApi
          .getSubjectListByStatus({
            status: this.indexMap[index],
            pageIndex: this.pageIndex,
            pageSize: this.pageSize
          })
          .then(res => {
            this.subjectList[index] = res.Result;
            this.current = index;
            this.dataReady = true;
            if (this.subjectList[index].length > 0) {
              this.hasQues = true;
            }
            if(index==0){
              this.statusList=[];
              for(let i=0;i<this.subjectList[index].length;i++){
                this.statusList.push(this.subjectList[index][i].isReceive);
              }
            }
            wx.hideLoading();
          });
      } else {
        this.current = index;
      }
    },
    loadNextPage(){
     
      if(this.subjectList[this.current].length>=this.pageSize*(this.pageIndex+1)){
           wx.showLoading({
          title: "加载中",
          mask: true
        });
        this.pageIndex++;
        subjectApi.getSubjectListByStatus({
          status:this.indexMap[this.current],
          pageIndex:this.pageIndex,
          pageSize:this.pageSize
        }).then(res=>{
          for(let i=0;i<res.Result.length;i++){
            this.subjectList[this.current].push(res.Result[i]);
          }
          if(this.current==0){
             this.statusList=[];
              for(let i=0;i<this.subjectList[this.current].length;i++){
                this.statusList.push(this.subjectList[this.current][i].isReceive);
              }
          }
          wx.hideLoading();
        })
      }
    },
    handleRoute(id) {
      this.$router.push(
        `/subjectFactoryPages/pages/lookSubject?subjectId=${id}`
      );
    },
    toSelectSubject() {
      this.$router.push("/subjectFactoryPages/pages/selectSubject");
    }
  },
  mounted() {
    this.changeList(0);
  },
  onUnload() {
    this.current = 0;
    this.dataReady = false;
    this.subjectList = [[], [], [], []];
  }
};
</script>

<style lang="scss" scoped>
.m-subject-list {
  width: 100%;
  height: 100%;
  padding: 200rpx 40rpx 0 40rpx;
  box-sizing: border-box;
}
.u-head {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background: #28235c;
  border-radius: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #7c74b9;
  font-size: 34rpx;
  padding: 70rpx 0;
  z-index: 1;
  div {
    position: relative;
    margin-right: 60rpx;
    &:last-child {
      margin: 0;
      &::after {
        display: none;
      }
    }
    &::after {
      content: "";
      position: absolute;
      top: 0;
      right: -25rpx;
      height: 34rpx;
      width: 2rpx;
      background: #7c74b9;
    }
  }
  .active {
    color: #fff;
  }
}
.u-list {
  height: 98%;
  .u-item {
    width: 664rpx;
    padding: 30rpx 30rpx 0 30rpx;
    background: #fff;
    border-radius: 10rpx;
    margin-bottom: 20rpx;
    font-size: 28rpx;
    color: #28235c;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    box-sizing: border-box;
    & > div {
      margin: 0 auto;
      width: 100%;
      height: 100rpx;
    }
    .u-title {
      border-bottom: 2rpx solid #d6d6d6;
      box-sizing: border-box;
      div{
        color: #ff217b;
        font-weight: bold;
        display: inline;
      }
    }
    .u-info {
      display: flex;
      justify-content: space-between;
      align-items: center;
      position: relative;
    }
    .btn {
      position: absolute;
      right: 0;
      top: 50%;
      transform: translateY(-50%);
      width: 130rpx;
      height: 60rpx;
      display: flex;
      justify-content: center;
      align-items: center;
      .get {
        width: 140rpx;
        height: 63rpx;
      }
      .receved {
        width: 119rpx;
        height: 55rpx;
      }
    }
    .u-num {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      font-size: 28rpx;
      .th {
        color: #7870b4;
        margin-right: 20rpx;
      }
      .txt {
        color: #85d85d;
        margin-right: 50rpx;
      }
    }
    .u-status {
      display: flex;
      align-items: center;
      color: #85d85d;
      .u-ball {
        width: 30rpx;
        height: 30rpx;
        border-radius: 50%;
        background: #85d85d;
        display: inline-block;
        vertical-align: middle;
        margin-right: 10rpx;
      }
    }
    .u-btn {
      width: 176rpx;
      height: 65rpx;
      line-height: 65rpx;
      text-align: center;
      background: #85d85d;
      color: #fff;
      border-radius: 10rpx;
    }
  }
  .u-type-1 {
    .u-status {
      color: #ffb800;
      .u-ball {
        background: #ffb800;
      }
    }
  }
  .u-type-2 {
    .u-status {
      color: #ff217b;
      .u-ball {
        background: #ff217b;
      }
    }
    .u-btn {
      background: #ff217b;
    }
  }
}
.u-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin-top: 100rpx;
  img {
    width: 360rpx;
    height: 386rpx;
    margin-bottom: 45rpx;
  }
  .u-empty-word {
    font-size: 54rpx;
    color: #a69ee3;
    margin-bottom: 55rpx;
  }
  .u-empty-btn {
    width: 300rpx;
    height: 90rpx;
    line-height: 90rpx;
    text-align: center;
    border-radius: 24rpx;
    background: #ff217b;
    color: #fff;
  }
}
</style>
