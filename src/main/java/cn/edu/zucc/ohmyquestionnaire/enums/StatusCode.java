package cn.edu.zucc.ohmyquestionnaire.enums;

public enum StatusCode {
    NO_LOGIN(-1),
    SUCCESS(0),
    FAIL(1),
    NO_PERMISSION(2);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
