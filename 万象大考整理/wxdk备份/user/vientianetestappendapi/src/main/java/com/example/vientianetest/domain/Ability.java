package com.example.vientianetest.domain;

import com.alibaba.fastjson.annotation.JSONField;

//能力值
public class Ability implements Cloneable{
    @JSONField(serialize=false)
    private int type; //难度 1:新手,2:中手,3:高手
    @JSONField(serialize=false)
    private int totalNum;//总场数
    private int winNum;//胜场
    private double winRatio;//胜率
    private int score; //得分
    private int continueWinNum;//连胜纪录
    private int continueLoseNum;//连败纪录
    @JSONField(serialize=false)
    private int realParagraph;  //实际段位
    @JSONField(serialize=false)
    private int realStar;           //星星数
    private int paragraph;      //段位
    private String paragraphName;//段位名称
    private String paragraphImage; //段位图片
    private int star;           //星星数
    @JSONField(serialize=false)
    private double mmr;         //mmr值(最强王者用)
    @JSONField(serialize=false)
    private int levelTotalNum;  //试炼的总盘数
    @JSONField(serialize=false)
    private int levelWinNum;    //试炼的胜利盘数
    @JSONField(serialize=false)
    private String play;           //试炼胜利明细
    @JSONField(serialize=false)
    private int receiveStar;    //领取过的星星
    @JSONField(serialize=false)
    private int isReceive;      //当前领取状态
    @JSONField(serialize=false)
    private int isRefine;       //试炼状态
    private double expValue;    //经验值
    private int grade;          //等级
    private double money;       //金币
    @JSONField(serialize=false)
    private int levelType;//1:新手2:一般3:高手
    private int rightNum;//答对次数
    @JSONField(serialize=false)
    private long createTime;//开始玩排位的时间
    private int rewardStrength;//奖励体力数
    private int combat;//战斗力
    private int bookTotalScore;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getWinNum() {
        return winNum;
    }

    public void setWinNum(int winNum) {
        this.winNum = winNum;
    }

    public double getWinRatio() {
        return winRatio;
    }

    public void setWinRatio(double winRatio) {
        this.winRatio = winRatio;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getContinueWinNum() {
        return continueWinNum;
    }

    public void setContinueWinNum(int continueWinNum) {
        this.continueWinNum = continueWinNum;
    }

    public int getContinueLoseNum() {
        return continueLoseNum;
    }

    public void setContinueLoseNum(int continueLoseNum) {
        this.continueLoseNum = continueLoseNum;
    }

    public int getRealParagraph() {
        return realParagraph;
    }

    public void setRealParagraph(int realParagraph) {
        this.realParagraph = realParagraph;
    }

    public int getRealStar() {
        return realStar;
    }

    public void setRealStar(int realStar) {
        this.realStar = realStar;
    }

    public int getParagraph() {
        return paragraph;
    }

    public void setParagraph(int paragraph) {
        this.paragraph = paragraph;
    }

    public String getParagraphName() {
        return paragraphName;
    }

    public void setParagraphName(String paragraphName) {
        this.paragraphName = paragraphName;
    }

    public String getParagraphImage() {
        return paragraphImage;
    }

    public void setParagraphImage(String getParagraphImage) {
        this.paragraphImage = getParagraphImage;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public double getMmr() {
        return mmr;
    }

    public void setMmr(double mmr) {
        this.mmr = mmr;
    }

    public int getLevelTotalNum() {
        return levelTotalNum;
    }

    public void setLevelTotalNum(int levelTotalNum) {
        this.levelTotalNum = levelTotalNum;
    }

    public int getLevelWinNum() {
        return levelWinNum;
    }

    public void setLevelWinNum(int levelWinNum) {
        this.levelWinNum = levelWinNum;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public int getReceiveStar() {
        return receiveStar;
    }

    public void setReceiveStar(int receiveStar) {
        this.receiveStar = receiveStar;
    }

    public double getExpValue() {
        return expValue;
    }

    public void setExpValue(double expValue) {
        this.expValue = expValue;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public int getRightNum() {
        return rightNum;
    }

    public void setRightNum(int rightNum) {
        this.rightNum = rightNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(int isReceive) {
        this.isReceive = isReceive;
    }

    public int getIsRefine() {
        return isRefine;
    }

    public void setIsRefine(int isRefine) {
        this.isRefine = isRefine;
    }

    public int getRewardStrength() {
        return rewardStrength;
    }

    public void setRewardStrength(int rewardStrength) {
        this.rewardStrength = rewardStrength;
    }

    public int getCombat() {
        return combat;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }

    public int getBookTotalScore() {
        return bookTotalScore;
    }

    public void setBookTotalScore(int bookTotalScore) {
        this.bookTotalScore = bookTotalScore;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
