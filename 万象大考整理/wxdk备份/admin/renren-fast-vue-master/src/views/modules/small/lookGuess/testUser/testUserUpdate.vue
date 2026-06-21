<template>
  <el-dialog
    title='修改' 
    :close-on-click-modal="false"
    :visible.sync="visible"
    :before-close="closeDialog"
    >
    <!-- <el-alert
      title="提示："
      type="warning"
      :closable="false">
      <div slot-scope="description">
        <p class="el-alert__description">先搜索昵称</p>
      </div>
    </el-alert> -->
    <el-form :model="basicData" :rules="dataRule" ref="testUser"  label-width="100px">
       <!-- <el-form-item label="搜索昵称">
          <el-input v-model="name" :value="name" class="title-input" style=" border:0px;"></el-input><i class="el-icon-search" @click="search()"></i>
      </el-form-item> -->
      <el-form-item label="昵称">
        <el-input v-model="basicData.nickname" :value="basicData.nickname" class="title-input" style=" border:0px;"></el-input>
      </el-form-item>
      <el-form-item label="头像">
        <div style="border: 1px solid #ccc;width: 102px;height: 102px;margin-left: 15px;">
          <img :src="basicData.headImgUrl" alt=""  style="width: 100px;height: 100px;"/>
        </div>
      </el-form-item>
      <el-form-item label="体力" prop="strengthCount">
          <el-input v-model="basicData.strengthCount" :value="basicData.strengthCount" class="title-input" style=" border:0px;"></el-input>
      </el-form-item>
      <el-form-item label="关卡数" prop="cardNumber">
          <el-input v-model="basicData.cardNumber" :value="basicData.cardNumber" class="title-input" style=" border:0px;"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog()">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { treeDataTranslate } from "@/utils";
import Icon from "@/icons";
export default {
  data() {
    return {
      visible: false,
      basicData: {
        userId: 0,
        nickname: "",
        headImgUrl: "",
        cardNumber: 0,
        strengthCount: 0
      },
      name: "",
      dataRule: {
        strengthCount: [
          { required: true, message: "体力不能为空", trigger: "blur" }
        ],
        cardNumber: [
          { required: true, message: "关卡数不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.iconList = Icon.getNameList();
  },
  destroyed() {
    console.log("over!!!");
  },
  methods: {
    search(name) {
      this.$nextTick(() => {
        this.$refs["testUser"].resetFields();
        this.$http({
          url: this.$http.adornUrl(
            `/smallLookGuess/testUser/getUserInfoByName`
          ),
          method: "get",
          params: this.$http.adornParams({
            name: name
          })
        }).then(({ data }) => {
          if (data && data.Tag === 1) {
            this.basicData.userId = data.Result.id;
            this.basicData.nickname = data.Result.nickname;
            this.basicData.headImgUrl = data.Result.headImgUrl;
            this.basicData.cardNumber = data.Result.cardNumber;
            this.basicData.strengthCount = data.Result.strengthCount;
          }
        });
      });
    },
    init(name) {
      debugger;
      if (name == "img[preview]") {
        return;
      }
      this.visible = true;
      this.search(name);
    },
    // 表单提交
    dataFormSubmit() {
      debugger;

      this.$refs["testUser"].validate(valid => {
        if (valid) {
          // 新增
          var thiz = this;
          this.$http({
            url: this.$http.adornUrl("/smallLookGuess/testUser/update"),
            method: "post",
            data: {
              userId: this.basicData.userId,
              strengthCount: this.basicData.strengthCount,
              cardNumber: this.basicData.cardNumber,
              type:0 // 增加
            }
          }).then(({ data }) => {
            if (data && data.Tag === 1) {
              this.$message({
                message: "恭喜你，修改成功！",
                type: "success"
              });
              thiz.closeDialog();
              thiz.$emit("refreshDataList");
            } else {
              this.$message.error("修改错误，" + data.Message);
            }
            //debugger
          });
        }
      });
    },
    closeDialog() {
      this.$emit("close");
    }
  }
};
</script>
<style lang="scss">
.title-input {
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 43%;
  margin-bottom: 5px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  margin-left: 15px;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 140px;
  height: 140px;
  line-height: 140px;
  text-align: center;
}
.avatar {
  width: 140px;
  height: 140px;
  display: block;
}
</style>