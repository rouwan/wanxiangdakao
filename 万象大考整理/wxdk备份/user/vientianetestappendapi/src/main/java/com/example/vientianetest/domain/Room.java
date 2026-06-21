package com.example.vientianetest.domain;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {
    private String id;      //房间Id
    private int roomMaster; //房主Id
    private String roomMasterName; //房主名称
    private String roomMasterImage;//房主图片
    private int userNum;    //人数
    private List<Integer> userList; //选手
    private int status;     //0:游戏未开始 1:开始游戏 2:结束游戏
    private long createTime;

    public Room(){}

    public Room(String id, int roomMaster,String roomMasterName,String roomMasterImage, int userNum, List<Integer> userList, int status, long createTime) {
        this.id = id;
        this.roomMaster = roomMaster;
        this.roomMasterName = roomMasterName;
        this.roomMasterImage = roomMasterImage;
        this.userNum = userNum;
        this.userList = userList;
        this.status = status;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRoomMaster() {
        return roomMaster;
    }

    public void setRoomMaster(int roomMaster) {
        this.roomMaster = roomMaster;
    }

    public String getRoomMasterName() {
        return roomMasterName;
    }

    public void setRoomMasterName(String roomMasterName) {
        this.roomMasterName = roomMasterName;
    }

    public String getRoomMasterImage() {
        return roomMasterImage;
    }

    public void setRoomMasterImage(String roomMasterImage) {
        this.roomMasterImage = roomMasterImage;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public List<Integer> getUserList() {
        return userList;
    }

    public void setUserList(List<Integer> userList) {
        this.userList = userList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


    public Room getRoom(JSONObject map){
        this.id = map.get("id").toString();
        this.roomMaster = Integer.parseInt(map.get("roomMaster").toString());
        this.userNum = Integer.parseInt(map.get("userNum").toString());
        //this.userList = (List)JSONObject.parse(map.get("userList").toString());
        this.status = Integer.parseInt(map.get("status").toString());
        this.createTime = Long.parseLong(map.get("createTime").toString());
        return this;
    }

    public void addUserToRoom(int userId){
        if(this.roomMaster == userId){//房主
            if(this.userList.size() <= 0){
                this.userList.add(userId);
            }
            this.setUserNum(this.userList.size());
        }else{
            //选手
            if(!this.userList.contains(userId)){
                this.userList.add(userId);
                this.setUserNum(this.userList.size());
            }
        }
    }

    public void kickUser(int kickUserId){
        if(this.roomMaster == kickUserId){ //房主
            if(this.userList.size() > 0){
                this.userList.remove(0);
            }
            if(this.userList.size() > 0){
                this.setRoomMaster(this.userList.get(0));
            }
            this.setUserNum(this.userList.size());
        }else{
            for(int i=0;i<this.userList.size();i++){
                if(this.userList.get(i) == kickUserId){
                    this.userList.remove(i);
                    this.setUserNum(this.userList.size());
                }
            }
        }
    }


/*public void addUserToRoom(int userId){
        if(this.id == userId && this.userId <= 0){//房主
            this.userId = userId;
        }else{
            if(this.withUserId <=0 ){
                if(!this.isExistsLookUserList(userId)){
                    this.withUserId = userId;
                }
            } else{
                if(!this.lookUserList.contains(userId)){
                    this.lookUserList.add(userId);
                }
            }
        }
    }

    public void lookUserToWithUser(int userId){
        for(int i =0;i<this.lookUserList.size();i++){
            if(this.lookUserList.get(i) == userId){
                this.lookUserList.remove(i);
                if(this.withUserId <=0 ){
                    this.setWithUserId(userId);
                }
            }
        }
    }

    public void addLookUser(int userId){
        if(!this.lookUserList.contains(userId)){
            this.lookUserList.add(userId);
        }
    }

    public boolean isExistsLookUserList(int userId){
        boolean isExists = false;
        for(int i =0;i<this.lookUserList.size();i++){
            if(this.lookUserList.get(i) == userId){
                isExists = true;
            }
        }
        return isExists;
    }

    public boolean isExpireRoom(){
        if(new Date().getTime() - this.getCreateTime()>10*60*1000){
            return true;
        }
        return false;
    }
    public Room getRoom(JSONObject map){
        this.id = Integer.parseInt(map.get("id").toString());
        this.userId = Integer.parseInt(map.get("userId").toString());
        this.withUserId = Integer.parseInt(map.get("withUserId").toString());
        this.lookUserList = (List)JSONObject.parse(map.get("lookUserList").toString());
        this.status =Integer.parseInt(map.get("status").toString());
        this.createTime = Long.parseLong(map.get("createTime").toString());
        return this;
    }*/

    //@Override
    /*public String toString() {
        return "Room{" +
                "id=" + id +
                ", userId=" + userId +
                ", withUserId=" + withUserId +
                ", lookUserList=" + lookUserList +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }*/
}
