package cn.edu.zucc.ohmyquestionnaire.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {
    public static final int NO_LOGIN = -1;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = 2;
    private static final long serialVersionUID = 1L;

    private String msg = "success";
    private int code = SUCCESS;
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable err) {
        super();
        this.msg = err.toString();
        this.code = FAIL;
    }
}
