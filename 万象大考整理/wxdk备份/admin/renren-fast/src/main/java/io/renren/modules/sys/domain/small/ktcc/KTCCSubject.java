package io.renren.modules.sys.domain.small.ktcc;

import java.util.List;

public class KTCCSubject {
    private int id;
    private String name;
    private String description;
    private int optionSortId;
    private String answer;
    private List<KTCCOption> optionList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<KTCCOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<KTCCOption> optionList) {
        this.optionList = optionList;
    }

    public int getOptionSortId() {
        return optionSortId;
    }

    public void setOptionSortId(int optionSortId) {
        this.optionSortId = optionSortId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
