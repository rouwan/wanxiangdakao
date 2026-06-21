package com.example.vientianetest.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Subject implements Comparable<Subject>,Cloneable {
    public static final int ANSWER_TIMEOUT = -1;
    public static final int ANSWER_NONE = -2;
    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;

    private int id;
    @JSONField(serialize=false)
    private int index;
    private String image;
    private String audio;
    private String title;
    private String answer;
    private String description;
    private int modeType;
    @JSONField(serialize=false)
    private int strengthCount;
    @JSONField(serialize=false)
    private int promptCount;
    private int optionSortId;
    private int type;
    private int typeId;        //小类
    private int parentType;   //大类
    @JSONField(serialize=false)
    private int sortId;
    @JSONField(serialize=false)
    private int cardNumber;
    @JSONField(serialize=false)
    private String randomStr;
    private String createUserName;
    private String createUserUrl;
    private List<Object> likeStatusList; //-1:没有点赞过,0:不喜欢,1:喜欢
    private int likeNum;
    private int noLikeNum;
    //@JSONField(serialize=false)
    private double rightRatio; //正确率
    private List<Answer> answerList;
    private List<SubjectOption> optionList;
    public Subject(){}

    public Subject(int id, String title, String answer, String description, int modeType, int optionSortId, int type, int typeId, int parentType,int sortId,String createUserName, String createUserUrl) {
        this.id = id;
        this.title = title;
        this.answer = answer;
        this.description = description;
        this.modeType = modeType;
        this.optionSortId = optionSortId;
        this.type = type;
        this.typeId = typeId;
        this.parentType = parentType;
        this.sortId = sortId;
        this.createUserName = createUserName;
        this.createUserUrl = createUserUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public int getStrengthCount() {
        return strengthCount;
    }

    public void setStrengthCount(int strengthCount) {
        this.strengthCount = strengthCount;
    }

    public int getPromptCount() {
        return promptCount;
    }

    public void setPromptCount(int promptCount) {
        this.promptCount = promptCount;
    }

    public int getOptionSortId() {
        return optionSortId;
    }

    public void setOptionSortId(int optionSortId) {
        this.optionSortId = optionSortId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserUrl() {
        return createUserUrl;
    }

    public void setCreateUserUrl(String createUserUrl) {
        this.createUserUrl = createUserUrl;
    }

    public List<Object> getLikeStatusList() {
        return likeStatusList;
    }

    public void setLikeStatusList(List<Object> likeStatusList) {
        this.likeStatusList = likeStatusList;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getNoLikeNum() {
        return noLikeNum;
    }

    public void setNoLikeNum(int noLikeNum) {
        this.noLikeNum = noLikeNum;
    }

    public double getRightRatio() {
        return rightRatio;
    }

    public void setRightRatio(double rightRatio) {
        this.rightRatio = rightRatio;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public List<SubjectOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SubjectOption> optionList) {
        this.optionList = optionList;
    }

    public class Answer implements Comparable<Answer>{

        private int uid = 0 ;
        private int answer = ANSWER_NONE;
        private int score = 0;          //单题分数
        private int appendScore = 0;    //前面累加分数
        private int bookScore = 0;      //知识书分数（每题）
        private boolean right = false;  //是否正确

        public Answer(){}

        public Answer(int uid, int answer, int score, int appendScore, boolean right) {
            this.uid = uid;
            this.answer = answer;
            this.score = score;
            this.appendScore = appendScore;
            this.right = right;
        }

        public Answer(int uid, int answer, int score, int appendScore,int bookScore ,boolean right) {
            this.uid = uid;
            this.answer = answer;
            this.score = score;
            this.appendScore = appendScore;
            this.bookScore = bookScore;
            this.right = right;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getAnswer() {
            return answer;
        }

        public void setAnswer(int answer) {
            this.answer = answer;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getAppendScore() {
            return appendScore;
        }

        public void setAppendScore(int appendScore) {
            this.appendScore = appendScore;
        }

        public int getBookScore() {
            return bookScore;
        }

        public void setBookScore(int bookScore) {
            this.bookScore = bookScore;
        }

        public boolean isRight() {
            return right;
        }

        public void setRight(boolean right) {
            this.right = right;
        }

        @Override
        public int compareTo(Answer o) {
            double i = o.appendScore - this.appendScore;//胜率排序
            return (int)i;
        }

    }

    @Override
    public int compareTo(Subject o) {
        double i = o.getRightRatio() - this.rightRatio;//胜率排序
        return (int)i;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Subject o = null;
        try {
            o = (Subject) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

}



