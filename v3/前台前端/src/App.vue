<script>
import { mapState, mapMutations } from "vuex";
export default {
  mounted() {
    this.updateSet(); //APP 更新通用方法
    this.checkAuthorize();//检查授权状态
  },
  onShow(){
    if(this.bgStatus=='pause' && this.globalBgPlayer){
      try { this.globalBgPlayer.play(); } catch(e) {}
    }
    console.log(this.$root.$mp)
  },
  computed:{
    ...mapState(["bgStatus","globalBgPlayer"]),
  },
  methods: {
    ...mapMutations(["setBgStatus"]),
    updateSet() {
      //通用app更新 提示 更新
      const updateManager = wx.getUpdateManager();
      updateManager.onCheckForUpdate(function(res) {
        // 请求完新版本信息的回调
      });
      updateManager.onUpdateReady(function() {
        wx.showModal({
          title: "更新提示",
          content: "新版本已经准备好，是否重启应用？",
          success: function(res) {
            if (res.confirm) {
              // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
              updateManager.applyUpdate();
            }
          }
        });
      });
      updateManager.onUpdateFailed(function() {
        // 新的版本下载失败
        wx.showModal({
          title: "更新提示",
          content: "新版本下载失败",
          showCancel: false
        });
      });
    },
    checkAuthorize() {
      try {
        var val = wx.getStorageSync('isAuthorize');
        this.store.commit("setAuthorize", val);
      } catch(e) {
        try { wx.setStorageSync('isAuthorize', '0'); } catch(e2) {}
        this.store.commit("setAuthorize", 0);
      }
    },
  
  }
};
</script>

<style lang="scss">
@import "scss/public.scss";

page {
  height: 100%;
  box-sizing: border-box;
  font-family: "Helvetica Neue", Helvetica, Arial, "PingFang SC",
    "Hiragino Sans GB", "Heiti SC", "Microsoft YaHei", "WenQuanYi Micro Hei";
    background: #28235d;
}
button{
  border: none;
}
</style>
