package cn.edu.zucc.ohmyquestionnaire.enums;

public enum QuestionType {
    SINGLE_SELECT("单选"),
    MULTI_SELECT("多选"),
    BLANK("填空"),
    SCORE("评分"),
    CASCADE("级联"),
    DROPDOWN("下拉"),
    PROPORTION("比重"),
    ANNEX("附件");

    private final String type;

    QuestionType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
