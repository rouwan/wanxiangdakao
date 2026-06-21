<template>
  <div class="out-box">
    <img class="entry-bg"
         src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/entry-test.png">
    <div class="body"
         v-if="dataReady">
      <div class="top  flex" :class="{'friend-type':query=='friend'}">
        <div v-for="(item,index) in tabList"
             :key="index"
             @click="changeRank(item.type)"
             :class="{'selected':currentType==item.type}">{{item.name}}</div>
        <img class="tip-box"
              v-if="query=='friend'"
              @click="showGiftTip=true"
             src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/box.png">
      </div>
      <div class="bigMiddle">
        <div class="bigRankList"
             @click="toUserInfo(data)"
             v-for="(data,index) in allList[currentType].dataList"
             :key="index">
          <div class="rankList"
               :class="{big:currentType==3}">
            <div class="rank">
              <div v-if="index==0"
                   class="rank-num rank-icon rank-icon-num-1"></div>
              <div v-else-if="index==1"
                   class="rank-num rank-icon rank-icon-num-2"></div>
              <div v-else-if="index==2"
                   class="rank-num rank-icon rank-icon-num-3"></div>
              <div v-else-if="index==3"
                   class="rank-num rank-icon rank-icon-num-4"></div>
              <div v-else-if="index==4"
                   class="rank-num rank-icon rank-icon-num-5"></div>
              <span v-else>{{index+1}}</span>
            </div>

            <div v-if="currentType!=3"
                 class="headImg"><img :src="data.headImgUrl" /></div>
            <div v-if="currentType!=3"
                 class="u-info">
              <div class="ninckName">{{data.nickName}}</div>
              <div class="place">{{data.province||'宇宙'}} {{data.city||'火星'}}</div>
            </div>

            <div v-if="currentType==1||currentType==5"
                 class="paraghImg"><img v-if="data.paragraphImage"
                   :src="IMGHEAD+data.paragraphImage" /></div>
            <div v-if="currentType==1||currentType==5"
                 class="paraghStar">
              <div class="star"><img src="/static/star.png" />
                <span>{{data.star}}</span>
              </div>
              <div class="paraghName">{{data.paragraphName}}</div>
            </div>

            <div v-if="currentType==2||currentType==6||currentType==7||currentType==8"
                 class="rank-spec">
              <div class="s-t"
                   v-if="currentType==2||currentType==6">战斗力</div>
              <div class="s-t"
                   v-if="currentType==7||currentType==8">胜场</div>
              <div class="s-b">
                <img :src="iconPos[item]"
                     v-for="(item,iconIndex) in allList[currentType].numList[index]"
                     :key="iconIndex">
              </div>
            </div>
            <div v-if="currentType==4"
                 class="rank-spec">
              <div class="s-t">最高分</div>
              <div class="s-b">
                <img :src="iconPos[item]"
                     v-for="(item,iconIndex) in allList[currentType].numList[index]"
                     :key="iconIndex">
              </div>
            </div>

            <div class="c-l"
                 v-if="currentType==3">
              <img class="c-head"
                   :src="data.headImgUrl">
              <div class="c-name">{{data.nickName}}</div>
              <div class="c-type">连胜者</div>
            </div>

            <div class="c-m"
                 v-if="currentType==3">
              <div class="c-img">
                <img :src="iconPos[item]"
                     v-for="(item,iconIndex) in allList[currentType].numList[index]"
                     :key="iconIndex">
              </div>
              <div class="c-tag">连胜</div>
              <div class="c-status stoped"
                   v-if="data.endWinUserId!=0">已终结</div>
              <div class="c-status"
                   v-else>未终结</div>

            </div>

            <div class="c-r"
                 v-if="currentType==3">
              <img v-if="data.endWinUserId<0"
                   class="c-head"
                   :src="IMGHEAD+data.endHeadImgUrl">
              <img v-else-if="data.endWinUserId>0"
                   class="c-head"
                   :src="data.endHeadImgUrl">
              <img v-else
                   class="c-head"
                   src="/static/empty-head.png">
              <div v-if="data.endWinUserId!=0"
                   class="c-name">{{data.endNickName}}</div>
              <div v-else
                   class="c-name">暂无终结者</div>
              <div class="c-type">终结者</div>
            </div>

          </div>
        </div>
      </div>

      <div class="bigBottom"
           v-if="allList[currentType].selfRank&&allList[currentType].selfRank.index">
        <div class="rank">{{allList[currentType].selfRank.index< 0 ? "-":allList[currentType].selfRank.index}}</div>
            <div class="headImg"><img :src="allList[currentType].selfRank.headImgUrl" /></div>
            <div class="ninckName">{{allList[currentType].selfRank.nickName}}</div>
            <div class="paraghImg"
                 v-if="currentType==1||currentType==5"><img v-if="allList[currentType].selfRank.paragraphImage"
                   :src="IMGHEAD+ allList[currentType].selfRank.paragraphImage" /></div>
            <div class="paraghStar"
                 v-if="currentType==1||currentType==5">
              <div class="star"><img src="/static/star.png" />
                <span>{{allList[currentType].selfRank.star}}</span>
              </div>
              <div class="paraghName">{{allList[currentType].selfRank.paragraphName}}</div>
            </div>

            <div class="b-fight"
                 v-if="currentType==2||currentType==6">
              <div class="b-l">战斗力</div>
              <div class="b-r">{{allList[currentType].selfRank.combat}}</div>
            </div>
            <div class="b-fight"
                 v-if="currentType==4">
              <div class="b-l">最高分</div>
              <div class="b-r">{{allList[currentType].selfRank.maxSingleScore}}</div>
            </div>
            <div class="b-fight"
                 v-if="currentType==3">
              <div class="b-l">连胜</div>
              <div class="b-r">{{allList[currentType].selfRank.maxContinueWinNum}}场</div>
            </div>
            <div class="b-fight"
                 v-if="currentType==7||currentType==8">
              <div class="b-l">胜场</div>
              <div class="b-r">{{allList[currentType].selfRank.winNum}}场</div>
            </div>
        </div>
      </div>

      <div class="mask-bg" v-if="showGiftTip">
        <div class="gift-modal pos-a left-center">
          <img class="modal-head"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/public/mvp-gift.png">
          <div class="line flex"
               v-for="(item,index) in giftList"
               :key="index">
            {{item.name}}
            <img :src="item.url"> x{{item.num}}
          </div>
          <div class="text">好友榜、MVP榜每周一凌晨2点更新</div>
          <img class="close-btn pos-a"
               src="https://ggbsq.xingpanwang.com/renren-fast/smallUploadSource/vientianeTestMobileAsset/images/invateBox/close-btn.png"
               @click="showGiftTip=false">
        </div>
      </div>
    </div>

</template>
<script>
import { mapState, mapMutations } from "vuex";
import { iconPos } from "@/config/dataFile.js";
export default {
  data() {
    return {
      allList: { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {}, 8: {} },
      dataList: [],
      selfRank: {},
      dataReady: false,
      currentType: 1,
      iconPos: iconPos, //数字 map
      tabList: [],
      giftList: [
        {
          name: "第一名",
          num: 1,
          url: "/static/icon/b-box.png"
        },
        {
          name: "第二名",
          num: 1,
          url: "/static/icon/m-box.png"
        },
        {
          name: "第三名",
          num: 1,
          url: "/static/icon/m-box.png"
        },
        {
          name: "第四名",
          num: 1,
          url: "/static/icon/s-box.png"
        },
        {
          name: "第五名",
          num: 1,
          url: "/static/icon/s-box.png"
        }
      ],
      showGiftTip:false,
      query:'',
    };
  },
  computed: {
    ...mapState(["IMGHEAD", "appInfo"])
  },
  methods: {
    toUserInfo(data) {
      this.$router.push(`/playerPages/pages/index?userId=${data.userId}`);
    },
    changeRank(type) {
      this.currentType = type;
      if (!this.allList[type].dataList) {
        wx.showLoading({ title: "加载中", mask: true });
        this.getList(type);
      }
    },
    getNumList(list, type) {
      let arr = [];
      for (let i = 0; i < list.length; i++) {
        let l = [];
        l = list[i][type].toString().split("");
        arr.push(l);
      }
      return arr;
    },
    getList(type) {
      return new Promise((resolve, reject) => {
        this.fly.post("rank/getRankList", { type: type }).then(res => {
          let arr = [];
          let list = res.data.Result || [];
          let selfRankInfo = res.data.Result2;
          if (type == 2 || type == 6) {
            arr = this.getNumList(list, "combat");
          }
          if (type == 3) {
            arr = this.getNumList(list, "maxContinueWinNum");
          }
          if (type == 4) {
            arr = this.getNumList(list, "maxSingleScore");
          }
          if (type == 5) {
            for (let i = 0; i < list.length; i++) {
              if (list[i].userId == this.appInfo.userId) {
                selfRankInfo = list[i];
                selfRankInfo.index = i + 1;
              }
            }
          }
          if (type == 7 || type == 8) {
            arr = this.getNumList(list, "winNum");
          }
          this.$set(this.allList, type, {
            dataList: res.data.Result,
            selfRank: selfRankInfo,
            numList: arr || []
          });
          this.dataReady = true;
          wx.hideLoading();
          resolve();
        });
      });
    }
  },
  mounted() {
    wx.showLoading({
      title: "加载中",
      icon: "none"
    });
    if (this.$root.$mp.query.god) {
      this.query='god';
      this.currentType = 2;
      this.tabList = [
        { name: "战力榜", type: 2 },
        { name: "连胜榜", type: 3 },
        { name: "高分榜", type: 4 }
      ];
    } else if (this.$root.$mp.query.friend) {
      this.query='friend';
      this.currentType = 7;
      this.tabList = [{ name: "好友榜", type: 7 }, { name: "胜场榜", type: 8 }];
    } else {
      this.currentType = 1;
      this.tabList = [
        { name: "段位榜", type: 1 },
        { name: "每日战力榜", type: 6 },
        { name: "好友榜", type: 5 }
      ];
    }
    this.getList(this.currentType);
  },

  onUnload() {
    this.dataReady = false;
    this.query='';
    this.currentType = 1;
    this.allList = { 1: {}, 2: {}, 3: {}, 4: {}, 5: {}, 6: {}, 7: {}, 8: {} };
  }
};
</script>
<style lang="scss" scoped>
@import url("../../scss/icon.scss");
@import "../../scss/layout.scss";
.out-box {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.entry-bg {
  width: 750rpx;
  // height: 1624rpx;
  height: 100%;
  // margin-top: -120rpx;
}
.body {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  padding-top: 120rpx;
  box-sizing: border-box;
  overflow: scroll;
}
.top {
  position: fixed;
  justify-content: space-around;
  height: 120rpx;
  left: 0;
  top: 0;
  width: 100%;
  padding: 0 54rpx;
  box-sizing: border-box;
  div {
    height: 60rpx;
    box-sizing: border-box;
    color: #fff;
    font-size: 36rpx;
  }
  .selected {
    color: #7efce4;
    border-bottom: 4rpx solid #7efce4;
  }
}

.bigMiddle {
  height: 100%;
  overflow: scroll;
  padding-bottom: 120rpx;
  box-sizing: border-box;
  .bigRankList {
    display: flex;
    justify-content: center;
    align-items: center;
    .rankList {
      display: flex;
      align-items: center;
      width: 708rpx;
      height: 156rpx;
      padding-bottom: 10rpx;
      box-sizing: border-box;
      background-size: 100%;
      background-repeat: no-repeat;
      background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAsQAAACcCAMAAABPyU0CAAADAFBMVEW+mv4/No0/N49mWdUAAABnW9hvYuSAQ4mAQ4mAQ4mAQ4mAQ4lhVcxSSbVRSLNBOpVlWNNKQaVIQKJeUshAOJNbUMNXTb1QR7JgVMtCOpdANo1ZTsBVS7lTSrdPRrBiVs5fU8pEN5FkV9FCNo9KOZZNO5poXNlpXNprXt+AQ4mAQ4mAQ4mAQ4lNRKlHP59dUsZGPp1DO5hcUcRaT8FYTr5WTLpFPZxEPZpGOJJjV89ORa1MQ6dkV9BmWtY/N5FWTLtEPJpVP6FcUcVNRKtPPJtTPp9RPZ1IOJSAQ4lrTLdZQaZgRa1XQKRxZOVpXdtmSLNeRKt8b+1+cO5pSrZbQqhzZueAQ4ltYOGDdvB7bex1VMGBc+9qXdxuTrtjRrCAQ4l4V8R5a+x1aOlxUb6AQ4l3aet0Zujq6P+AQ4mAQ4mAQ4mAQ4mAQ4mAQ4lsYNv///+AQ4mAQ4lHPJSAQ4mAQ4lDOpJYTriAQ4mAQ4mAQ4mAQ4lhVsOAQ4mAQ4lLQpuAQ4leU8BKP5doXM9mWs1MRKBVS7VyZd1lWciAQ4mAQ4loW9NjV8xhVshaULxcRKmAQ4lvYt53auRqXdddU7tORqOAQ4l4aulvULuAQ4mAQ4lVRKFuYdhHPpiAcu5mTbGAQ4lzZuFxZNpRSahjSa9ZRqVANo+AQ4mAQ4mAQ4prX9VrXtJyUr9OQJmAQ4qAQ4mAQ4p7bupUS61RSKy7lviAQ4pSQ52AQ4mykfhWTbFhS6xRQJy8lvq4kfOBRIuAQ4mAQ4mAQ4mni/eJbtCAQ4peSqm9mPttYdRrULaZg/B6beejgua4lvzY1PiRf/KVjeZZULSAQ4m+t/aMeu6EdOS0i+u4kfKgh/ecfOGAYsltWbaVYLGug+CNVKCgl/C2sO+ug+CrgNuLUZywhuSqftiXY7Wro/Dh3v739/2siu+Ydt6meNCdbMGicsm4kvOwhuSZZbe1ju6hcceTXa2ITZebaLuGS5SGTJWITZfLxfmDdOn4+P2Tctl3WMGoe9SfbsSLU56Ntk6KAAABAHRSTlPy8vLyAPLyAwodEi3y8vLy8vLy8vLy8vLy8vLy8vLy8vLy8vLy8vLy8lRrP4fy8vLy8vLy8vLy8vLy8vLy8vLy8vLy8vLy8vKe8vLy8vLy8vLy8vLy8sXy8vLy8vLy8rPy8vLy1fLy8g0GFSEoEPLyCDbyGgfy8jsmQRjySjHygvLy8vLy8vLydWby8vLy8pfy8vLy8nzy8l9Z8vLy8vJR8vLy8vLykU5G8vLy8qZwYvLy8t6r8ivy8vLy7+zNsIm/8vK58ujy8vLy8vLy8vLyjfLy8uvT8vLy8tyHOPLy6dfFoGta8vLy8vLm4srDwrixkU3SzcSeV/Ly8vLysYl1JqIppQAAEgtJREFUeNrs2TFro3AYx/H/cPQu+lLutXQwUDDc1EKbVA1YdLBCXkSWbt07FoRkuClDINI6RIiLyZK02QIhU2+75/n/bfRQuYZqujxfXHX68OMB2beP952ijtYBMNmH+V5fX9u2af6gqJozTdsGbqiuIsTcrw2f7nQ63W5XoqiaA2aADcjZXPInEaNgBAx8JU1zXatBUfVnWa6raRJQBsjo+BOIhWAEDHx13TGMwWAgU1StDQaG4Ti63rBchCwcH4o4HWETBSNgx5iv4uXGG48ZRdXb77E32yzj1dxwEDI6NnGOD0csCEsaCAbAyxmjqCM3WwJkcKxJgvFhiFPCuiOvtjS/1Bc13q5kR/8PY1ZySdhIGEc49hhFfWFejHOMjO2Sm4KVzXBXs5BwOsLDURQEoUpRdReGQRCNhukcI2NL6+bGuByxmGG3AYeE9w44ClToF0UdIxUeKIjeIXtwVDRcMcZliPMz3NDl3YbxnqKA872HmhRVe/dNtIbmwPET4212st4oGWOWN8xn2OjF44SwinzRr5JLVZsKRdURWOaU1YTxOO4ZfIzzilmRYUuX51vGi1T0KwC3sgWL6XMbWj9PJy2KqjoBGSWrEeNt57JuFSlmecMaXMO7GcNGoQDcws73NRfTdTvT8+ScoiquhQnI4Yhhsx1cxlpeMSsy3HvxGDbBG2LP9zIpSARne72kqGrbU8bLYsIw76VXpJjlDRuJ4WGoKI+t1uMdBh+94IVv7aIWFxRVaUCO00ODihIOE8VGorgMsTB8IwyPlEfoDrvArniLdnHruyuKqjTOTjiGlJFQfCMUFyJODe+E4YSw8HuWBIZLWpxRVNUJyQljoXiXV8wyx4TZkeAefpgJw74P7/bPRKeiP+tSxNNTiqq6hF8fKPq+UDx7gLtY6piZg4Jl/9NJltO73XLDvt/v91O+0An02i7t7YSiKu0fysDR97ni7W3PsaTsvzuWOSa6ri7/jPeG3wWfpE0JMfWXXfNXaSQKo/iAZDdhmn2GLTKJvsLmFaa1mReIf24QhJAlaS9rqiSVwxTOVE4aU6S0slAEx4E1WC2mEEMqhVRBQd3vXCfOKCtbxUzx/cBGnO7H4Xzn+tnMPH61+M+Kvv5zM1EotESZWMvp1Ztj7BIqhpMC/1/iqy8MMy/gogpjbBTHN1U9t4ZC8U5ilAk66vxbjbinP3ddF1+vvmH0cSVeZZh5ARNJSNLyXiNufTruUChiieMy0ahNNOK87RKtVmtVUYi5+MjhYavAMHOBFCQTSUcXHp9rxKTWSBYK7U2ZmPZRiGOHC2EwHgdhYUbr979jeFBgmHkh6Ce2GLW4P40LRSxxGcvEln+tEQPXlfSFEMIaVhRD65Xw/ZtzMAylxTBzhnQkKaXrDjTi2t/CQlFOSowgXtdrU1x1p66MHDbDSkRoJpCD8Go0Gg5Ho6twIEyG+Qwii6V7ittuWtPXKYpjiRHEuOrqCOKztiSJBTCDSkRgMMwCgcSAJJbtM0RxHbddFMVaIoiLfQRx5LBlGca4EjE2GGbBWFZkMaK4X0xEsTZrxBTETyqIoxw2jXw+ljjPMAvGMFUWR1H8RFGsWvFM4u2XIL5LBLFp0mdxncgzzKIxzUQU371E8farxNlNmibqvUNME9IThIGPMvFhl2GYxaLCWBCexEBx2KvTQLGZVRJHZ51e3ZvgwVl6niNEp9PJgNnElmGYxUNaCuF4nsTj82SvqkennabaBD3W+bZqEySxgMT5jCIMHh4CzmEmFeQhsfC8lz5h+/Rsp/qEptoEzrpeH22CgthROVximJShstihKEaf6Pdw2qFPQGLVJlaKz9gm4LDTIUqlJYZJFaUSzHRgMfaJ5+KK6hOQ+GuZtolGzUYlPoLErDCTUpTGkPgIpdiuNWifoFKsqUpM28TuIyoxOdztdprNJYZJIc1mp9sli1GKH3dpn1ClWEMlzm1U93dw11145DAk/sYwKQQSk8XeBS67nf3qRg6lGBKrSrx8ibsOQdxsssRMOoGbiGJcdpfLqhQrictZPNfZBxgn2s4JSQyHvzNM2vhBFkPiE6eNeeLAxqNdtgyJcdf59q9j+r102GEmxUQWOxL/jvmXXTtWbRuKwjieLQSaQAlxDa0bkzHEBFp7cGswtE+TIRAIaNEQY2NkamsSGTqFdGq0WUPQE2jVK/X7dC263evxBL7/KM7448K9R6vff3mzA+LDG3evW3Hp/Fy95ER8MVHKYBdEnL9Uz1w8r9zN7ubwgI8TXHU8HqCqyvP5r/lkMlbKYJMJeOZ5VVHrI9cdeJ4A4rvbez5OtIjn8zkJnytlLjIG0BYxnyfub+8axEevQPzQIM6BeLEYr9fnSplrvR4vFkCcN4gfgPj16D/ipxYxDYPwO6UMBsZU3CJ+ahG7/zAvG8S5ECvLtYjzBvGl+xuTiN2u44qfd4Yx3VXKXIC5U0ytV9h2CLF6Y+2JmIZJuK+UuciYir2I3UG8kWFls253445iD2IaBuJN/1Qpg/U3QAzFAcQJDG9OlTLZBoqTEOIkaQxfK2WwRnGSeBE7wyT8QylzkbFTHESM0S9KGQw0g4gTIm4InyllroYxEScBxHF8LcPKZkAcx0HEcRyT8FelDEbGIBpGPCPhj0qZi4xnQcQ0PDuTYWUzIqZiL2IanoHwUCmDgTGA+hHHDjGmO0qZCzAd4tiLGCNpKsPKZsNhmoJoADENg/BUKYOBcZoGELuDOO1MR0oZbNoBTyANIKZhTH9SylyAScUhxBlmIhlWNhuNIgDNvIhnWZZGEQj3lDIYGEdRmmWzIOJo2Vv2lDIXYEZBxDvDmD1WylyAuVMcRizCymh7IaZhIj5RylxETMX7ID4WYmUxwNwDcUrDFPxBKXPRMRWnXsSRECu7tYijAGIaxvhnpcwFmFQcQFzXy+XJiQgro+EwXi7r2oe4rssShItvSpmsAOOyrGsPYhgutwUN/1TKXFRcbIHUj3gLw8VAKaMVULz1Ii6B2Bn+rpS5nGIgLn2InWGMv1fKXIDpFPsRF38GAxFWVvvHTh2bMAwDURgGL2AQabKEsRqrC5opoPYgcCO4VCXIAA5kv9zJSXtKecX761d+PFXcXibi42ht33cZB4TcJTCFZ2vHYSFuihiEkddUsSC1EHfDHMKCkMtC4K7YRPwzvCLkrp9iG7EY5qCEN4TcpYwDi2IbMTN3wgkhd3XGQnSI+PzhjJC7zi8eIBbDtKxbyjki5K6c07YuJIoHiMVwivGKkLtiTKL4H8RiGIiRx6IqHiMmqv2HZ4Tc1b+4EpmImanWGkEYOe0aBSgxW4iJvoYvCLnrq5hoiPgOwshr83wfI66KWMY3hNwlMBVxNRH3Iy5FCE8IuUsYl9Kv2ET8UMRlQshlRRE/TMTvE/Ht+ZwQ+rBn96qNA0EAxy9guJi8gTuhVZMn2OoqEeVRJH9ALBucGEdiO7tyZYhVnIuTnBRKUikmkiAQLk2qFBdIOpcpDOlc38xuFLjjuHqL+ZdT/xhmJe1Kkm//R/wA4zgQPiBOkmSHorQLYAJiXwQRYC3+gbiE+U9CTGncJ+IYsD78jXixH/6C+XeJGBQ3dihKuxpgWCL+AVjLPxHfDBaT8Bnmr4EQCjEpprSroRALEbwC1udwfzH4QHzUUYjXMH9JCTGlbZ+I0xfAug4ni8FNvXMkEffag/NJuIH5fSDUUUz3BKVfjY+TWAT3gHUTTs4rxH2FePYO88cglYhNs0FR2mWaEnEaPALW9xkgbvcUYnfY7o6uZ1uYr7JUKTaIMaVbpmmoRZxmK8C6nV2Puu2h2wfEnjs87o7OxssSX3YSMeeGYVKUZhkG57iIU3zXlcvx2ah7PHS9r19qnttsnV7Mx09rPIrVKgbExJjSKwPiahHjSbx+Gs8vTltN16sB4t1ma+9kfnWHR/FbluEHCmRMUZrFfWU4e8OT+O5qfrLXau4i4uqXXbgt8J7I8lQw5nDOyTGlURxyGBNpnuE1UWzD6l+HRNypy88TU3VPoGLBfIdTlFY5PhNoWF0TU/lxot5RiPvVyw7vidVtBIoti2EORWkSwywLDEe3K7wmqnddXyKWLzs8ig9LXMURKAbGglGUVgkgDIYjXMTlIZ7E8l2HiGtedRRPN2oVR1Ge27aFMYrSIAuz7TyPIrWIN9PqJPZqgBiP4p783bG8LHAVx7FcxgfgmKI0ybYP5BqOY1zExeXyd3t2z5pWGIZx/IyJfpJov0SgBtsGGqg4FFID6VB8AUVBDQZXXZJJPC46NHiEoC6SYH2JaCBglwyC1sVmy0u3NFM79LqfR/vQVyn06HL/d7cfN9d5FH91hDGJJWKMYrwU0574pKEPR+DucGxA8WOGzC0/AXFjw+EAzKMPGvqENUGvxDSJJWKxJ6IFvE/UaBUPz+kWrzsc7zaoxxy31ATDdw7HOt3h8yEt4hreJgpRsSYEYrknxPvEpfOzhrpQTIzhmNrguCUmFa4TYRjuauiz81K8Tcg1AcRiT9CfdvRpV5uIQXF+fnB0tLm5Ps3BcUtqZnBz8+jo4PxcjIlJjT7r6O+6NxGJWO6JsF+e4iv6tuvX64Lx0020znFLjRQ+FYTr9T54Nq7kIfaHsSYkYlIcVKdYDIp+XjA+cLmeTtvkuIU30+dyHQjC+b4YE+oQB2FYIpafduIUnznbHzU0zOff1utbWwfIJXvKcQvNJSODW1v1+tt8fqihj23nmTjE4rNuhlid4jTeinsDqRiM4fjZlpQsc3HcQlLmAPAZBIOwNDzo4Y04rQ6xQkyrOBbCWzEGxUNTLIqLvHD89ploi+MWnrQHhHl0IbZE8wFjAm/Eodh0ESvEdIrxVmxUaVBcTRVfSMeyZxy34KQ8KfhiaviKxkTVwBsxHWKFWJ7iAP62E4PC6ZGKr0f45Qv0XPSK4xaadEcCAXF0LQ17nGJM4M+6gDzECjHeisW3XSlnSxbLnquBRn3JZi+eICGZ4xYcuSN/F9nsF40aXHnKxaQtVxJfdREyrBCrQZHQMYvLvQepuD/KZrPb29tPZC84bkFNyQEfCI760vBDr4xBrCfUmFCI1aCw2g099QiK3bea6ObrbpbaJsoct7BIXJba/XqjiW7dMPwopRt2qxoTPyKmQRGO7ccLVVLscY8bGnV6s3N8fLyLslIzx5lcVuJFoLdzc6pRjbHbQ4arhfh+LExj4gfEalBYVpXizv1EE70fdl+i41m7HGdix7NIXXf4XhNN7jvK8KplOiYUYjUoMIv9pLiSLDrbHfe4qclOh92dnZ2XHLewAK47PNVkzbG703YWkxUy7McgVmNCIf5JsV7BS1ut527dNbRZ1/2b7mi0w3GmNxp1b/rX2qzGXcvdq+FtraL/bPhXxN8VG7otc3ZYbhPjpsZxS6xJhNvlw7OMTTe+G/49YqU4th9N5NKpJI6xp+NujScNjeOWUmMybrk7nhqmRCqdS0T3Y8rwbxErxdZoaa1awTGWjFvj24HGcQtucDtuScI4w5XqWilqVYZ/i1gptoR9J6G4Qcf4khj3yPH9+O52MmjyUeZMr9EcTG7vxvckuEeEL+kMG/HQiS9skYb/iFgp3sMwxjHWbYJxuU2O3ZDces1xCwjUAA6C22VB2KbjDGMO7ynDf0FMioMrAe+qzxqKJ4hx5qwIxzVA7nU6HTfHmVzHDWg9AK5BcPEsQ4QT8ZDVt+oNrATJ8DzExDgijnFMMk5XMkly7CyXa7U28nCcibVRrVYuO0lwMlNJS8IxcYYjRHguYnWMwz4/MS7kqrZKCpAvi8XDQyfHmd7hYbF4CcCpiq2aKxBhvy+szvB8xKRYHmPvKjGOluBYTwNyKpNJJpOPOM7UgCyTSQFwWofgUhSEsSTkGSbD8xGrY0yMw76YdT9kjyeMtVxOr6bTaRvHmRyYVfVcbs1IxO2hfWvMFwZhdYbnIv6VMc4xHEft8VIiYRiFwhrHmVqhYBiJRCluj0IwjvAfCM9HDMWSccCyB8cxvxWSQ1Fk5zhTi6IQ/Fr9MQjeswQEYRj+d8QIjCMr5NhLkCHZf3JyYuU4UwMyP/wSYC8JXomoK/zviImxdEyQvXugzHHmB757XgIsBRPheYjnOwZkSAblQMDCcSYHZsC2QoCV4LmI50OGZFCOBIMrHGdywWAEfOFXAv4fiBVljltY/wDzG466/t6Z5VFYAAAAAElFTkSuQmCC");
      .rank {
        width: 140rpx;
        display: flex;
        justify-content: center;
        align-items: center;
        padding-left: 20rpx;
        box-sizing: border-box;
        div {
          width: 60rpx;
          height: 73rpx;
          transform: scale(0.66);
        }
        span {
          color: white;
        }
      }
      .headImg {
        margin-right: 30rpx;
        img {
          width: 100rpx;
          height: 100rpx;
          border-radius: 50%;
          vertical-align: middle;
        }
      }
      .u-info {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        flex-direction: column;
        font-weight: bold;
        .ninckName {
          width: 200rpx;
          font-size: 28rpx;
          color: #fff;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          line-height: 1;
          margin-bottom: 30rpx;
        }
        .place {
          font-size: 24rpx;
          color: #a397fd;
          line-height: 1;
        }
      }

      .paraghImg {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100rpx;
        height: 100rpx;
        img {
          width: 100rpx;
          height: 100rpx;
          //margin-left: 145rpx;
        }
      }
      .paraghStar {
        width: 180rpx;
        margin-left: 10rpx;
        .star {
          height: 40rpx;
          img {
            width: 32rpx;
            height: 32rpx;
          }
          span {
            color: #fff;
            font-size: 25rpx;
            margin-left: 5rpx;
          }
        }
        .paraghName {
          color: white;
          font-size: 22rpx;
          padding-top: 5rpx;
          margin-bottom: 10rpx;
        }
      }
    }
    .big {
      height: 184rpx;
      background-size: 708rpx 184rpx;
    }
  }
}
.bigBottom {
  position: fixed;
  display: flex;
  align-items: center;
  height: 82rpx;
  width: 730rpx;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 1000rpx;
  background-color: #fff;
  bottom: 32rpx;
  .rank {
    width: 60rpx;
    height: 60rpx;
    border-radius: 50%;
    background: #9c94ff;
    text-align: center;
    color: #fff;
    font-size: 34rpx;
    font-weight: bold;
    margin: 0 30rpx 0 50rpx;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .headImg {
    width: 72rpx;
    height: 72rpx;
    margin-right: 30rpx;
    img {
      width: 72rpx;
      height: 72rpx;
      border-radius: 50%;
    }
  }
  .ninckName {
    width: 200rpx;
    font-size: 28rpx;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    color: #9c94ff;
    font-weight: bold;
    margin-right: 30rpx;
  }
  .paraghImg,
  .paraghImg img {
    width: 80rpx;
    height: 80rpx;
  }

  .paraghStar {
    font-weight: bold;
    .star {
      height: 40rpx;
      img {
        width: 24rpx;
        height: 24rpx;
      }
      span {
        color: #6760ba;
        font-size: 24rpx;
        margin-left: 5rpx;
      }
    }
    .paraghName {
      color: #6760ba;
      font-size: 24rpx;
      padding-top: 5rpx;
      margin-bottom: 10rpx;
    }
  }
}

.rank-spec {
  width: 306rpx;
  height: 100rpx;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: center;
  padding-right: 40rpx;
  box-sizing: border-box;
  .s-t {
    font-size: 30rpx;
    font-weight: bold;
    color: #fff;
    margin-bottom: 20rpx;
  }
  .s-b {
    display: flex;
    justify-content: center;
    img {
      width: 24rpx;
      height: 30rpx;
    }
  }
}

.b-fight {
  display: flex;
  align-items: center;
  font-weight: bold;
  margin-left: 20rpx;
  .b-l {
    width: 3em;
    font-size: 28rpx;
    color: #9c94ff;
    margin-right: 20rpx;
  }
  .b-r {
    font-size: 36rpx;
    color: #6760ba;
  }
}

.c-l,
.c-r,
.c-m {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  text-align: center;
}
.c-l,
.c-r {
  width: 200rpx;
}
.c-head {
  width: 72rpx;
  height: 72rpx;
  border: 4rpx solid #a69ee3;
  box-sizing: border-box;
  border-radius: 50%;
}
.c-name {
  width: 100%;
  text-align: center;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  font-size: 24rpx;
  font-weight: bold;
  color: #fff;
}
.c-type {
  font-size: 20rpx;
  font-weight: bold;
  color: #7efce4;
}

.c-img {
  display: flex;
  justify-content: center;
  img {
    width: 24rpx;
    height: 30rpx;
  }
}
.c-tag {
  font-size: 30rpx;
  font-weight: bold;
  color: #fff;
}
.c-status {
  font-size: 20rpx;
  color: #a397fd;
}
.stoped {
  color: #ff217b;
}
.friend-type {
  justify-content: flex-start;
  div {
    position: relative;
    &:first-child {
      margin: 0 50rpx;
      &::after {
        content: "";
        width: 2rpx;
        height: 40rpx;
        position: absolute;
        right: -25rpx;
        top: 50%;
        transform: translateY(-50%);
        background: #a397fd;
      }
    }
  }
  .tip-box {
    position: absolute;
    right: 60rpx;
    top: 50%;
    transform: translateY(-50%);
    width: 91rpx;
    height: 81rpx;
  }
}
.gift-modal {
  width: 612rpx;
  height: 688rpx;
  top: 190rpx;
  background: linear-gradient(180deg, rgba(#fff, 0.75) 70%, rgba(#5887ff, 0.8));
  border-radius: 32rpx;
  color: #fff;
  .modal-head {
    display: block;
    width: 468rpx;
    height: 64rpx;
    margin: 50rpx auto 30rpx auto;
  }
  .line {
    width: 490rpx;
    height: 70rpx;
    margin: 0 auto 24rpx auto;
    border-radius: 1000rpx;
    background: #706e6e;
    font-size: 32rpx;
    img {
      margin: 0 50rpx;
      width: 70rpx;
      height: 70rpx;
    }
  }
  .text {
    text-align: center;
    font-size: 26rpx;
    margin-top: 34rpx;
  }
  .close-btn {
    width: 92rpx;
    height: 92rpx;
    top: -30rpx;
    right: -30rpx;
  }
}
</style>


