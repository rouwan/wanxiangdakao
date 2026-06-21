package io.renren.modules.sys.domain.small.wxdk;

import java.util.List;

public class Subject {
    private int id;
    private String image;
    private String audio;
    private String promptWord;
    private String answer;
    private String description;
    private int optionSortId;//答案选项
    private int typeId;// 父类类型
    private int type;// 1:选项型,2:选字型
    private int sortId;
    private int status;// 0:未审核,1:已审核,2:已驳回,3:已更改
    private int modeType;// 1:图加文,2:歌曲加文,3:纯文字,4:判断,5:成语,6:单词,7:五言标题上,8:五言标题下,9:七言标题上,10:七言标题下
    private String createUserUrl;
    private String createUserName;
    private int levelType;//1:简单 2:一般 3:困难
    private String typeName; //导入单词题的词性用，与数据库无关
    private List<SubjectOption> subjectOptionList;

    public Subject(){}

    public Subject(int id, String image, String audio, String promptWord, String answer, String description, int optionSortId, int typeId, int type, int sortId, int status, int modeType, String createUserName, List<SubjectOption> subjectOptionList) {
        this.id = id;
        this.image = image;
        this.audio = audio;
        this.promptWord = promptWord;
        this.answer = answer;
        this.description = description;
        this.optionSortId = optionSortId;
        this.typeId = typeId;
        this.type = type;
        this.sortId = sortId;
        this.status = status;
        this.modeType = modeType;
        this.createUserName = createUserName;
        this.subjectOptionList = subjectOptionList;
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

    public String getPromptWord() {
        return promptWord;
    }

    public void setPromptWord(String promptWord) {
        this.promptWord = promptWord;
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

    public int getOptionSortId() {
        return optionSortId;
    }

    public void setOptionSortId(int optionSortId) {
        this.optionSortId = optionSortId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getModeType() {
        return modeType;
    }

    public void setModeType(int modeType) {
        this.modeType = modeType;
    }

    public String getCreateUserUrl() {
        return createUserUrl;
    }

    public void setCreateUserUrl(String createUserUrl) {
        this.createUserUrl = createUserUrl;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<SubjectOption> getSubjectOptionList() {
        return subjectOptionList;
    }

    public void setSubjectOptionList(List<SubjectOption> subjectOptionList) {
        this.subjectOptionList = subjectOptionList;
    }
}
