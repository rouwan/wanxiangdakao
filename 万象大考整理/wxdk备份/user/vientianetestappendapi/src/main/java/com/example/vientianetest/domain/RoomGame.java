package com.example.vientianetest.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.common.utils.Rand;
import com.example.vientianetest.observer.RoomObserver;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomGame {
    //被观察者维护的一个观察者对象列表
    public static final int STATUS_NEW = 0;
    public static final int STATUS_PLAYING = 1;
    public static final int STATUS_END = 2;
    public static final int STATUS_FULL = 3;//人数已满

    @JSONField(name = "room")
    private RoomSub roomSub;
    @JSONField(serialize=false)
    private String leftGroupList; //左边排序用户Id
    @JSONField(serialize=false)
    private String rightGroupList; //右边排序用户Id
    @JSONField(serialize=false)
    private String exitUserList; //退出玩家用户Id
    @JSONField(serialize=false)
    private int firstUserId; //第一名
    @JSONField(serialize=false)
    private int isFlat; //是否平局
    private Game game;

    public RoomGame(){}

    public RoomGame(String roomId, int roomMaster, int userNum, CopyOnWriteArrayList<User> userList, int roomStatus,long restSecond, Game game) {
        RoomSub roomSub = new RoomSub();
        roomSub.setRoomId(roomId);
        roomSub.setRoomMaster(roomMaster);
        roomSub.setUserNum(userNum);
        roomSub.setUserList(userList);
        roomSub.setRoomStatus(roomStatus);
        roomSub.setRestSecond(restSecond);
        this.roomSub = roomSub;
        this.game = game;
    }

    public RoomSub getRoomSub() {
        return roomSub;
    }

    public void setRoomSub(RoomSub roomSub) {
        this.roomSub = roomSub;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getIsFlat() {
        return isFlat;
    }

    public void setIsFlat(int isFlat) {
        this.isFlat = isFlat;
    }

    public String getLeftGroupList() {
        return leftGroupList;
    }

    public void setLeftGroupList(String leftGroupList) {
        this.leftGroupList = leftGroupList;
    }

    public String getRightGroupList() {
        return rightGroupList;
    }

    public void setRightGroupList(String rightGroupList) {
        this.rightGroupList = rightGroupList;
    }

    public String getExitUserList() {
        return exitUserList;
    }

    public void setExitUserList(String exitUserList) {
        this.exitUserList = exitUserList;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    //选手进入房间
    public void addUserToRoom(User user){
        if(this.roomSub.getRoomMaster() == user.getUser().getUserId()){//房主
            if(this.roomSub.getUserList().size() <= 0){
                this.roomSub.getUserList().add(user);
            }
            this.roomSub.getUserList().get(0).setStatus(1);
            this.roomSub.getUserList().get(0).setSession(user.getSession());
        }else{
            //选手
            boolean isAgain = false;
            for(int i=0;i<this.roomSub.getUserList().size();i++){
                if(this.roomSub.getUserList().get(i).getUser().getUserId() == user.getUser().getUserId()){
                    //this.roomSub.getUserList().get(i).setIsReady(0);
                    this.roomSub.getUserList().get(i).setStatus(1);
                    this.roomSub.getUserList().get(i).setSession(user.getSession());
                    isAgain = true;
                }
            }
            if(!isAgain){
                if(this.roomSub.getUserList().size() < 4){
                    //System.out.println(new Date().getTime()+":"+user.getUser().getUserId() + ":"+this.roomSub.getUserList().size());
                    user.setIsReady(0);
                    this.roomSub.getUserList().add(user);
                    this.roomSub.setUserNum(this.roomSub.getUserList().size());
                }
            }
        }
    }

    public void kickUser(int kickUserId){
        if(this.roomSub.getRoomMaster() == kickUserId){ //房主退出的话，下一个接替位子
            if(this.roomSub.getUserList().size() > 0){
                this.roomSub.getUserList().remove(0);
            }
            if(this.roomSub.getUserList().size() > 0){
                this.roomSub.setRoomMaster(this.roomSub.getUserList().get(0).getUser().getUserId());
                this.roomSub.getUserList().get(0).setIsReady(1);
            }
            this.roomSub.setUserNum(this.roomSub.getUserList().size());
        }else{
            for(int i=0;i<this.roomSub.getUserList().size();i++){
                if(this.roomSub.getUserList().get(i).getUser().getUserId() == kickUserId){
                    this.roomSub.getUserList().remove(i);
                    this.roomSub.setUserNum(this.roomSub.getUserList().size());
                }
            }
        }

    }

    //准备或取消准备
    public void updateChangeReady(int userId){
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            if(userId == this.roomSub.getUserList().get(i).getUser().getUserId()){
                int status = this.roomSub.getUserList().get(i).getIsReady() == 0 ? 1 : 0;
                this.roomSub.getUserList().get(i).setIsReady(status);
            }
        }
    }

    //改变用户状态
    public void changeUserStatus(int userId,int status){
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            if(userId == this.roomSub.getUserList().get(i).getUser().getUserId()){
                this.roomSub.getUserList().get(i).setStatus(status);
            }
        }
    }

    public boolean allReady(){
        boolean isReady = true;
        for(int i=1;i<this.roomSub.getUserList().size();i++){
            if(this.roomSub.getUserList().get(i).getIsReady() != 1){
                isReady = false;
            }
        }
        return isReady;
    }

    public User minWinRatio(){
        User user = null;
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            if(user == null || user.getAbility().getWinRatio() < this.roomSub.getUserList().get(i).getAbility().getWinRatio()){
                user = this.roomSub.getUserList().get(i);
            }
        }
        return user;
    }

    public String toStringByUid(){
        String userStr = ",";
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            userStr += this.roomSub.getUserList().get(i).getUser().getUserId() + ",";
        }
        return userStr;
    }

    public boolean onAnswer(Answer answer){
        //answer.normalize();
        Subject subject = this.game.getSubjectList().get(answer.getIndex());
        answer.setRightAnswer(subject.getOptionSortId());
        if (answer.isTimeout()) {
            answer.setAnswer(Subject.ANSWER_TIMEOUT);
        }

        boolean isAgain = false;
        for(int i = 0;i<this.roomSub.getUserList().size();i++){
            if(this.roomSub.getUserList().get(i).getUser().getUserId() == answer.getUid()){
                List<Subject.Answer> answerList = new ArrayList<>();
                if(subject.getAnswerList() != null){
                    answerList = subject.getAnswerList();
                    for(int j =0;j<answerList.size();j++){
                        // 防止重复回答
                        if(answerList.get(j).getUid() == answer.getUid() && answerList.get(j).getAnswer() != Subject.ANSWER_NONE){
                            isAgain = true;
                            break;
                        }
                    }
                }

                if(!isAgain){
                    Subject.Answer obj = null;
                    this.roomSub.getUserList().get(i).setIndex(this.roomSub.getUserList().get(i).getIndex() + 1);
                    //机器人是答题时长，用户是剩余时长
                    long seconds = this.roomSub.getUserList().get(i).getBoot() ? Param.GAME_MATCH_SUBJECTSECOND - answer.getSeconds() : answer.getSeconds();
                    this.roomSub.getUserList().get(i).setLastReplyTime(new Date().getTime() + answer.getSeconds() + Param.GAME_MATCH_SUBJECTSECOND);
                    if (subject.getOptionSortId() != answer.getAnswer() || answer.isTimeout()) {
                        obj = subject.new Answer(answer.getUid(),answer.getAnswer(),0,this.roomSub.getUserList().get(i).getAbility().getScore(),false);
                        answerList.add(obj);
                        subject.setAnswerList(answerList);
                        answer.setScoreSub(0);
                        answer.setRight(false);
                        //this.roomSub.getUserList().get(i).getAbility().setContinueRightNum(0);
                    }else{
                        //2018-12-17 连对分数不加了
                        /*this.roomSub.getUserList().get(i).getAbility().setContinueRightNum(this.roomSub.getUserList().get(i).getAbility().getContinueRightNum() + 1);//连对记录
                        if(this.roomSub.getUserList().get(i).getAbility().getContinueRightNum() > this.roomSub.getUserList().get(i).getAbility().getMaxContinueRightNum()){
                            this.roomSub.getUserList().get(i).getAbility().setMaxContinueRightNum(this.roomSub.getUserList().get(i).getAbility().getContinueRightNum());
                        }*/
                        int score = 0; //本题的分数
                        int continueRightScore = 0;//this.getRightNumScore(this.roomSub.getUserList().get(i).getAbility().getContinueRightNum()); //连对额外分数 不用了
                        double secondScore = (float)seconds/1000;
                        if(this.roomSub.getModeType() == 1){
                            //答题序号==最后一题 （备注：最后一题正确分100时间300） 正确分：100
                            if(answer.getIndex() == game.getSubjectList().size() - 1){
                                score = 100 + (int) (secondScore * 30);
                            }else {
                                score = 100 + (int) (secondScore * 10);
                            }
                        }else if(this.roomSub.getModeType() == 2) {//3v3没有保底分
                            //答题序号==最后一题 正确分：100
                            if(answer.getIndex() == game.getSubjectList().size() - 1){
                                score = (int) (secondScore * 20);
                            }else {
                                score = (int) (secondScore * 10);
                            }
                            this.appendScoreByGroupId(score,this.roomSub.getUserList().get(i).getGroupId());//设置分组分数
                        }

                        //this.roomSub.getUserList().get(i).getAbility().setTotalContinueRightScore(this.roomSub.getUserList().get(i).getAbility().getTotalContinueRightScore() + continueRightScore);
                        this.roomSub.getUserList().get(i).getAbility().setScore(this.roomSub.getUserList().get(i).getAbility().getScore() + score + continueRightScore);
                        this.roomSub.getUserList().get(i).getAbility().setRightNum(this.roomSub.getUserList().get(i).getAbility().getRightNum() + 1);
                        obj = subject.new Answer(answer.getUid(),answer.getAnswer(),score + continueRightScore,this.roomSub.getUserList().get(i).getAbility().getScore(),true);
                        answerList.add(obj);
                        subject.setAnswerList(answerList);
                        answer.setScoreSub(score + continueRightScore);
                        answer.setRight(true);
                    }
                    if(this.roomSub.getModeType() == 2) {//3v3
                        answer.setGroupId(this.roomSub.getUserList().get(i).getGroupId());
                        answer.setGroupScore(this.getScoreByGroupId(this.roomSub.getUserList().get(i).getGroupId()));
                    }
                    answer.setScore(this.roomSub.getUserList().get(i).getAbility().getScore());
                    answer.setRightAnswer(subject.getOptionSortId());
                    //answer.setContinueRightNum(this.roomSub.getUserList().get(i).getAbility().getContinueRightNum());
                    //answer.setContinueRightScore(this.getRightNumScore(this.roomSub.getUserList().get(i).getAbility().getContinueRightNum()));
                }
                break;
            }
        }

        return isAgain;
    }

    //连对额外的分数
    public int getRightNumScore(int rightNum){
        int score = 0;
        if(rightNum == 2){
            score = 20;
        }
        else if(rightNum == 3){
            score = 30;
        }
        else if(rightNum == 4){
            score = 40;
        }
        else if(rightNum == 5){
            score = 50;
        }
        return score;
    }

    public Boolean complete(){
        if(this.game.getSubjectList() == null){
            return false;
        }
        Subject lastSubject = this.game.getSubjectList().get(this.game.getSubjectList().size() - 1); // 最后一题
        Boolean result = false;
        if(lastSubject.getAnswerList() != null) {
            result = lastSubject.getAnswerList().size() == this.onLineUserNum();
        }
        return result;
    }

    public boolean isAnswerByIndex(int index){
        if(index < 0)
            return true;
        if(index >= Param.GAME_MATCH_SUBJECTNUM )
            return false;
        if(this.game.getSubjectList() == null || this.game.getSubjectList().size()<=0){return false;}
        Subject subject = this.game.getSubjectList().get(index);
        boolean result = false;
        if(subject.getAnswerList() != null) {
            result = subject.getAnswerList().size() >= this.onLineUserNum();
        }
        return result;
    }

    public boolean isAnswerByIndexSub(int index){
        if(this.game.getSubjectList() == null || this.game.getSubjectList().size()<=0){return false;}
        Subject subject = this.game.getSubjectList().get(index);
        boolean result = false;
        if(subject.getAnswerList() != null) {
            result = subject.getAnswerList().size() >= this.onLineUserNum();
        }
        return result;
    }

    public boolean isExistsUserListByUserId(int userId){
        boolean result = false;
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            if(this.roomSub.getUserList().get(i).getUser().getUserId() == userId){
                result = true;
            }
        }
        return result;
    }

    //在线人数
    public int onLineUserNum(){
        int num = 0;
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            if(this.roomSub.getUserList().get(i).getStatus() == 1){
                num += 1;
            }
        }
        return num;
    }

    //玩家是否都掉线
    public boolean allOutLine(){
        int num = 0;
        for(int i=0;i<this.roomSub.getUserList().size();i++){
            if(this.roomSub.getUserList().get(i).getStatus() == 0){
                num += 1;
            }
        }
        return num == this.roomSub.getUserList().size();
    }

    //玩家退出
    public void userExit(int userId){
        try {
            int index = -1;
            User exitUser = null;
            for (int i = 0; i < this.roomSub.getUserList().size(); i++) {
                if (this.roomSub.getUserList().get(i).getUser().getUserId() == userId) {
                    index = i;
                    exitUser = this.roomSub.getUserList().get(i);
                    break;
                }
            }
            if(index >= 0) {//删除再copy
                this.roomSub.getUserList().get(index).setIsReady(0);
                this.roomSub.getUserList().get(index).setStatus(0);
                if (this.exitUserList == null || this.exitUserList.length() <= 0) {
                    this.exitUserList = "," + userId + ",";
                } else {
                    if(!this.exitUserList.contains(","+userId+",")){
                        this.exitUserList = this.exitUserList + userId + ",";
                    }
                }
                this.roomSub.getUserList().remove(index);
                this.roomSub.getUserList().add(index,(User) exitUser.clone());//退出后默认copy状态*/
                this.roomSub.getUserList().get(index).setSession(null);
            }
        }
        catch (Exception ex){

        }
    }

    //机器人随机答题时间
    public int getRobotRandomSecond(User robot){
        int minSecond = 1 * 1000;
        int maxSecond = 5 * 1000;
        try {
            if (robot.getIndex() > (game.getSubjectList().size() - 1)) {
                return -1;
            }
            Subject subject = this.game.getSubjectList().get(robot.getIndex());
            if (subject.getRightRatio() > 66) {
                minSecond = robot.getRobotSubjectRatio().getSimpleMinTime() * 1000;
                maxSecond = robot.getRobotSubjectRatio().getSimpleMaxTime() * 1000;
            } else if (subject.getRightRatio() >= 33 && subject.getRightRatio() <= 66) {
                minSecond = robot.getRobotSubjectRatio().getCommonMinTime() * 1000;
                maxSecond = robot.getRobotSubjectRatio().getCommonMinTime() * 1000;

            } else if (subject.getRightRatio() >= 0 && subject.getRightRatio() < 33) {
                minSecond = robot.getRobotSubjectRatio().getHardMinTime() * 1000;
                maxSecond = robot.getRobotSubjectRatio().getHardMaxTime() * 1000;
            }
        }
        catch (Exception ex){

        }
        return (int)(minSecond+Math.random()*(maxSecond-minSecond+1));
    }

    //机器人随机选项
    public int getRobotRandomOption(User robot,Subject subject){
        int option = -1;
        double rightRatio = 0;
        try {
            if (subject.getRightRatio() > 66) {
                rightRatio = robot.getRobotSubjectRatio().getSimpleRatio();
            } else if (subject.getRightRatio() >= 33 && subject.getRightRatio() <= 66) {
                rightRatio = robot.getRobotSubjectRatio().getCommonRatio();

            } else if (subject.getRightRatio() >= 0 && subject.getRightRatio() < 33) {
                rightRatio = robot.getRobotSubjectRatio().getHardRatio();
            }
            int randomRightRatio = Rand.nextInt(100 + 1);
            if (randomRightRatio <= rightRatio) {
                option = subject.getOptionSortId();
            } else {
                option = Rand.nextInt(subject.getOptionList().size() + 1);
                while (option == subject.getOptionSortId() || option == 0) {
                    option = Rand.nextInt(subject.getOptionList().size() + 1);
                }
            }
        }
        catch (Exception ex){

        }
        return option;
    }

    @JSONField(serialize=false)
    public boolean isAllRobot(){
        //判断最后一个人是否是机器人即可
        boolean isRobot = false;
        try {
            if(this.roomSub.getUserList().get(this.roomSub.getUserList().size() - 1).getBoot()){
                isRobot = true;
            }
        }
        catch (Exception ex){
            //ex.printStackTrace();
        }
        return isRobot;
    }

    //设置用户正在玩的房间号
    @JSONField(serialize=false)
    public void setUserPlayRoomId(String roomId) {
        for(int i=0;i<this.roomSub.getUserList().size();i++) {
            this.roomSub.getUserList().get(i).setPlayRoomId(roomId);
        }
    }

    //获取第一名
    @JSONField(serialize=false)
    public int getFirstUserId() {
        if(this.leftGroupList != null){
            if(this.roomSub.getModeType() == 1) {
                return this.roomSub.getRankList().get(0).getUser().getUserId();
            }else if(this.roomSub.getModeType() == 2) {
                if(this.roomSub.getGroupList().get(0).getScore() > this.roomSub.getGroupList().get(1).getScore()){//左边
                    return this.roomSub.getLeftRankList().get(0).getUser().getUserId();
                } else if (this.roomSub.getGroupList().get(0).getScore() < this.roomSub.getGroupList().get(1).getScore()) {
                    return this.roomSub.getRightRankList().get(0).getUser().getUserId();
                } else if(this.roomSub.getGroupList().get(0).getScore() == this.roomSub.getGroupList().get(1).getScore()) {
                    if(this.roomSub.getLeftRankList().get(0).getAbility().getScore() >= this.roomSub.getRightRankList().get(0).getAbility().getScore()){
                        return this.roomSub.getLeftRankList().get(0).getUser().getUserId();
                    }else {
                        return this.roomSub.getRightRankList().get(1).getUser().getUserId();
                    }
                }
            }

        }
        return 0;
    }

    public void endGame(int excludeUid) {
        try {
            if(this.roomSub.getModeType() == 1) {
                this.roomSub.setRankList(new CopyOnWriteArrayList<>());
                for (int i = 0; i < this.roomSub.getUserList().size(); i++) {
                    this.roomSub.getRankList().add((User) this.roomSub.getUserList().get(i).clone());
                }
                Collections.sort(this.roomSub.getRankList());
                for (int i = 0; i < this.roomSub.getRankList().size(); i++) {
                    if (this.leftGroupList == null || this.leftGroupList.length() <= 0) {
                        this.leftGroupList = "," + this.roomSub.getRankList().get(i).getUser().getUserId() + ",";
                    } else {
                        this.leftGroupList += this.roomSub.getRankList().get(i).getUser().getUserId() + ",";
                    }
                }
            }else if(this.roomSub.getModeType() == 2) {
                //选出哪组胜利
                this.roomSub.setLeftRankList(new CopyOnWriteArrayList<>());
                this.roomSub.setRightRankList(new CopyOnWriteArrayList<>());
                for (int i = 0; i < this.roomSub.getUserList().size(); i++) {
                    if (this.roomSub.getUserList().get(i).getGroupId() == 1) {
                        this.roomSub.getLeftRankList().add((User) this.roomSub.getUserList().get(i).clone());
                    } else if (this.roomSub.getUserList().get(i).getGroupId() == 2) {
                        this.roomSub.getRightRankList().add((User) this.roomSub.getUserList().get(i).clone());
                    }
                }
                Collections.sort(this.roomSub.getLeftRankList());
                Collections.sort(this.roomSub.getRightRankList());
                for (int i = 0; i < this.roomSub.getLeftRankList().size(); i++) {
                    if (this.leftGroupList == null || this.leftGroupList.length() <= 0) {
                        this.leftGroupList = "," + this.roomSub.getLeftRankList().get(i).getUser().getUserId() + ",";
                    } else {
                        this.leftGroupList += this.roomSub.getLeftRankList().get(i).getUser().getUserId() + ",";
                    }
                }
                for (int i = 0; i < this.roomSub.getRightRankList().size(); i++) {
                    if (this.rightGroupList == null || this.rightGroupList.length() <= 0) {
                        this.rightGroupList = "," + this.roomSub.getRightRankList().get(i).getUser().getUserId() + ",";
                    } else {
                        this.rightGroupList += this.roomSub.getRightRankList().get(i).getUser().getUserId() + ",";
                    }
                }
                if (this.roomSub.getGroupList().get(0).getScore() == this.roomSub.getGroupList().get(1).getScore()) {//平局
                    this.isFlat = 1;
                }
            }

            this.roomSub.setRoomStatus(STATUS_END);
        } catch (Exception ex) {

        }
    }

    @JSONField(serialize=false)
    public List<User> setGroupByUserList(List<User> userList,int groupId) {
        for(int i =0;i<userList.size();i++) {
            userList.get(i).setGroupId(groupId);
        }
        return userList;
    }

    public void appendScoreByGroupId(int score ,int groupId) {
        for(RoomGroup group : this.roomSub.getGroupList()) {
            if(group.getGroupId() == groupId) {
                group.setScore(group.getScore() + score);
            }
        }
    }

    @JSONField(serialize=false)
    public int getScoreByGroupId(int groupId) {
        int score = 0;
        for(RoomGroup group : this.roomSub.getGroupList()) {
            if(group.getGroupId() == groupId) {
                score = group.getScore();
                break;
            }
        }
        return score;
    }
}
