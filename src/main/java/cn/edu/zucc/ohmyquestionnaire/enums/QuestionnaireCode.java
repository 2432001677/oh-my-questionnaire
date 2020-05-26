package cn.edu.zucc.ohmyquestionnaire.enums;

public enum QuestionnaireCode {
    DELETED(0),
    CLOSED(1),
    OPENING(2);

    private final int code;

    QuestionnaireCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
