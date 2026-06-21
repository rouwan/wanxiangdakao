package com.example.vientianetest.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.common.domain.BasicUserInfo;
import com.example.common.utils.Rand;
import org.springframework.web.socket.WebSocketSession;

import java.math.RoundingMode;
import java.util.*;

public class User implements Comparable<User>,Cloneable{

    @JSONField(name = "basicInfo")
    private BasicUserInfo user;
    @JSONField(serialize=false)
    private long joinTime;
    @JSONField(serialize=false)
    private WebSocketSession session;
    private Boolean isBoot;
    @JSONField(serialize=false)
    private long autoTime; // 虚拟答题时间
    @JSONField(serialize=false)
    private int seconds; // 虚拟答题秒数
    @JSONField(serialize=false)
    private long keepTime;//心跳时间
    //@JSONField(serialize=false)
    private int index; // 题目序号
    private long restSecond; //剩余时间(毫秒)
    //@JSONField(serialize=false)
    private int status; //0:离线,1:在线,2:最小化(点击home键或者左上角的按钮)
    @JSONField(serialize=false)
    private List<Integer> randomSubjectIndexList; //机器人的话，随机题目的序号
    @JSONField(serialize=false)
    private long lastReplyTime; //上一次答题的时间
    @JSONField(serialize=false)
    private int gameId; //当前玩的游戏
    @JSONField(serialize=false)
    private String roomId; //房间Id
    @JSONField(serialize=false)
    private String playRoomId; //正在开始房间Id(给混合模式用)
    @JSONField(name = "abilityInfo")
    private Ability ability;
    @JSONField(serialize=false)
    private int randWaitSecond; //等待游戏的时长，随机匹配的秒数
    //@JSONField(serialize=false)
    private int isReady;        //是否准备
    private int groupId;        //分组
    @JSONField(serialize=false)
    private boolean isObserver; //是否在大厅
    @JSONField(serialize=false)
    private RobotSubjectRatio robotSubjectRatio;
    @JSONField(name = "combatInfo")
    private List<CombatSub> combatSubList;
    @JSONField(serialize=false)
    private List<Book> bookList;

    public long getAutoTime() {
        return autoTime;
    }

    public void setAutoTime(long autoTime) {
        this.autoTime = autoTime;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public long getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(long keepTime) {
        this.keepTime = keepTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getRestSecond() {
        return restSecond;
    }

    public void setRestSecond(long restSecond) {
        this.restSecond = restSecond;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BasicUserInfo getUser() {
        return user;
    }

    public void setUser(BasicUserInfo user) {
        this.user = new BasicUserInfo(user.getUserId(),user.getNickName(),user.getGender(),user.getAvatarUrl(),user.getCity(),user.getProvince(),user.getCountry());
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public Boolean getBoot() {
        return isBoot;
    }

    public void setBoot(Boolean boot) {
        isBoot = boot;
    }

    public List<Integer> getRandomSubjectIndexList() {
        return randomSubjectIndexList;
    }

    public void setRandomSubjectIndexList(List<Integer> randomSubjectIndexList) {
        this.randomSubjectIndexList = randomSubjectIndexList;
    }
    public long getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(long lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayRoomId() {
        return playRoomId;
    }

    public void setPlayRoomId(String playRoomId) {
        this.playRoomId = playRoomId;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public int getRandWaitSecond() {
        return randWaitSecond;
    }

    public void setRandWaitSecond(int randWaitSecond) {
        this.randWaitSecond = randWaitSecond;
    }

    public int getIsReady() {
        return isReady;
    }

    public void setIsReady(int isReady) {
        this.isReady = isReady;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isObserver() {
        return isObserver;
    }

    public void setObserver(boolean observer) {
        isObserver = observer;
    }

    public RobotSubjectRatio getRobotSubjectRatio() {
        return robotSubjectRatio;
    }

    public void setRobotSubjectRatio(RobotSubjectRatio robotSubjectRatio) {
        this.robotSubjectRatio = robotSubjectRatio;
    }

    public List<CombatSub> getCombatSubList() {
        return combatSubList;
    }

    public void setCombatSubList(List<CombatSub> combatSub) {
        this.combatSubList = combatSub;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public boolean onAnswer(Game game, Answer answer){
        //answer.normalize();
        Subject subject = game.getSubjectList().get(answer.getIndex());
        answer.setRightAnswer(subject.getOptionSortId());
        if (answer.isTimeout()) {
            answer.setAnswer(Subject.ANSWER_TIMEOUT);
        }
        if(!this.getBoot()){
            answer.setBoot(false);
        }
        boolean isAgain = false;
        List<User> userList = game.getUserList();
        for(int i = 0;i<userList.size();i++) {

            if (userList.get(i).getUser().getUserId() == this.getUser().getUserId()) {
                List<Subject.Answer> answerList = new ArrayList<>();
                if (subject.getAnswerList() != null) {
                    answerList = subject.getAnswerList();
                    for (int j = 0; j < answerList.size(); j++) {
                        // 防止重复回答
                        if (answerList.get(j).getUid() == this.getUser().getUserId() && answerList.get(j).getAnswer() != Subject.ANSWER_NONE) {
                            isAgain = true;
                            break;
                        }
                    }
                }

                if (!isAgain) {
                    Subject.Answer obj = null;
                    userList.get(i).setIndex(userList.get(i).getIndex() + 1);
                    //机器人是答题时长，用户是剩余时长
                    long seconds = this.getBoot() ? Param.GAME_MATCH_SUBJECTSECOND - answer.getSeconds() : answer.getSeconds();
                    userList.get(i).setLastReplyTime(new Date().getTime() + seconds + Param.GAME_MATCH_SUBJECTSECOND);
                    if (subject.getOptionSortId() != answer.getAnswer() || answer.isTimeout()) {
                        obj = subject.new Answer(answer.getUid(), answer.getAnswer(), 0, this.ability.getScore(),0, false);
                        answerList.add(obj);
                        subject.setAnswerList(answerList);
                        answer.setScoreSub(0);
                        answer.setRight(false);
                        answer.setBookScore(0);
                        //this.ability.setContinueRightNum(0);
                        //answer.setScore(this.ability.getScore());
                    } else {
                        //每题获得体力
                        this.answerAppendCombat(subject.getParentType(), 1);
                        //2018-12-17 连对分数不加了 设置连对记录
                        /*this.ability.setContinueRightNum(this.ability.getContinueRightNum() + 1);
                        if (this.ability.getContinueRightNum() > this.ability.getMaxContinueRightNum()) {
                            this.ability.setMaxContinueRightNum(this.ability.getContinueRightNum());
                        }*/
                        int score = 0; //本题的分数
                        int continueRightScore = 0 ;//this.getRightNumScore(this.ability.getContinueRightNum()); //连对额外分数 不用了
                        double secondScore = (float) seconds / 1000;
                        if (answer.getIndex() == game.getSubjectList().size() - 1) {
                            score = 100 + (int) (secondScore * 30);
                        } else {
                            score = 100 + (int) (secondScore * 10);
                        }
                        //知识书加成
                        Map<String, Object> bookMap = this.getBookScore(score, subject.getParentType());
                        int bookScore = Integer.parseInt(bookMap.get("score").toString());
                        int bookLevel = Integer.parseInt(bookMap.get("level").toString());
                        this.ability.setScore(this.ability.getScore() + score + continueRightScore + bookScore);
                        this.ability.setRightNum(this.ability.getRightNum() + 1);
                        this.ability.setBookTotalScore(this.ability.getBookTotalScore() + bookScore);
                        //this.ability.setTotalContinueRightScore(this.ability.getTotalContinueRightScore() + continueRightScore);
                        //用户答题记录
                        obj = subject.new Answer(answer.getUid(), answer.getAnswer(), score + continueRightScore , this.ability.getScore(), bookScore,true);
                        answerList.add(obj);
                        subject.setAnswerList(answerList);
                        answer.setScoreSub(score + continueRightScore + bookScore);
                        answer.setRight(true);
                        answer.setBookLevel(bookLevel);
                        answer.setBookScore(bookScore);
                    }

                    answer.setBookType(subject.getParentType());
                    answer.setScore(this.ability.getScore());
                    answer.setRightAnswer(subject.getOptionSortId());
                    //answer.setContinueRightNum(this.ability.getContinueRightNum());
                    //answer.setContinueRightScore(this.getRightNumScore(this.ability.getContinueRightNum()));
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

    //知识书加成
    public Map<String, Object> getBookScore(int score ,int type) {
        double addition = 0;
        int level = 0;
        if (!this.isBoot) {
            for (int i = 0; i < this.bookList.size(); i++) {
                if (this.bookList.get(i).getType() == type) {
                    if (this.bookList.get(i).getLevel() > 0) {
                        level = this.bookList.get(i).getLevel();
                        addition = this.bookList.get(i).getAddition();
                    }
                    break;
                }
            }
        } else { //机器人10%概率随机
            int randomRatio = (int) (1 + Math.random() * (100 - 1 + 1));
            if (randomRatio <= 10) {
                level = (int) (1 + Math.random() * (10 - 1 + 1));
                addition = level % 2 == 0 ? level/2 : level/2 - 0.5;
            }
        }
        //System.out.println("是否机器人 : "+this.isBoot+" 知识书加成 : " + (int) Math.ceil(score * (double) level / 100) );
        Map<String, Object> resultMap = new HashMap<>();
        int newScore = level > 0 ? (int) Math.round(score * (addition / 100)) : 0;
        resultMap.put("score", Math.abs(newScore));
        resultMap.put("level", level);
        return resultMap;
    }

    //用户逃跑（计算前面的）
    public void endGame(Game game, int excludeUid) {

    }

    public void answerAppendCombat(int parentType,int count){
        int index = -1;
        if(this.combatSubList != null && this.combatSubList.size() > 0) {
            for(int i=0;i<this.combatSubList.size();i++){
                if(this.combatSubList.get(i).getParentType() == parentType){
                    index = i;
                    break;
                }
            }
        }
        if(this.combatSubList == null){
            this.combatSubList = new ArrayList<>();
        }
        if(index >= 0){
            this.combatSubList.get(index).setCombat(this.combatSubList.get(index).getCombat() + count);
        }else {
            this.combatSubList.add(new CombatSub(parentType,count));
        }
    }

    public static String toStringByUid(List<User> userList){
        String uidStr = "";
        for(int i=0 ;i<userList.size();i++){
            int isRobot = userList.get(i).getBoot() ? 1:0;
            uidStr += "{\"uid\":"+userList.get(i).getUser().getUserId()+",\"isRobot\":"+isRobot+"},";
        }
        if(uidStr.endsWith(",")){
            uidStr = "["+uidStr.substring(0,uidStr.length()-1)+"]";
        }
        return uidStr;
    }

    //机器人随机答题时间
    public int getRobotRandomSecond(Game game){
        if(game.getAnswerIndex() > 4) { return -1;}
        Subject subject = game.getSubjectList().get(game.getAnswerIndex());
        int minSecond = 1*1000;
        int maxSecond = 5*1000;
        if(subject.getRightRatio() > 66){
            minSecond = this.robotSubjectRatio.getSimpleMinTime()*1000;
            maxSecond = this.robotSubjectRatio.getSimpleMaxTime()*1000;
        }
        else if(subject.getRightRatio() >= 33 && subject.getRightRatio() <= 66){
            minSecond = this.robotSubjectRatio.getCommonMinTime()*1000;
            maxSecond = this.robotSubjectRatio.getCommonMinTime()*1000;

        }else if(subject.getRightRatio() >=0 && subject.getRightRatio() < 33){
            minSecond = this.robotSubjectRatio.getHardMinTime()*1000;
            maxSecond = this.robotSubjectRatio.getHardMaxTime()*1000;
        }
        return (int)(minSecond+Math.random()*(maxSecond-minSecond+1));
    }

    //机器人随机选项
    public int getRobotRandomOption(Subject subject){
        int option = -1;
        double rightRatio = 0;
        if(subject.getRightRatio() > 66){
            rightRatio = this.robotSubjectRatio.getSimpleRatio();
        }
        else if(subject.getRightRatio() >= 33 && subject.getRightRatio() <= 66){
            rightRatio = this.robotSubjectRatio.getCommonRatio();

        }else if(subject.getRightRatio() >=0 && subject.getRightRatio() < 33){
            rightRatio = this.robotSubjectRatio.getHardRatio();
        }
        int randomRightRatio = Rand.nextInt(100+1);
        if(randomRightRatio <=  rightRatio){
            option = subject.getOptionSortId();
        }else{
            option = Rand.nextInt(subject.getOptionList().size()+1);
            while (option == subject.getOptionSortId() || option == 0 ) {
                option = Rand.nextInt(subject.getOptionList().size()+1);
            }
        }
        return option;
    }


    @Override
    public int compareTo(User o) {
        double i = o.getAbility().getScore() - this.ability.getScore();//得分排序
        return (int)i;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        User user = new User();
        user = (User)super.clone();
        user.setAbility((Ability)this.ability.clone());
        BasicUserInfo basicUserInfo = new BasicUserInfo(this.user.getUserId(),this.user.getNickName(),this.user.getGender(),this.user.getAvatarUrl(),this.user.getCity(),this.user.getProvince(),this.user.getCountry());
        user.setUser(basicUserInfo);
        return user;
    }

}
