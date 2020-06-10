package cn.edu.zucc.ohmyquestionnaire.form;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msg = "success";
    private int code = StatusCode.SUCCESS.getCode();
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(Throwable err) {
        super();
        this.msg = err.toString();
        this.code = StatusCode.FAIL.getCode();
    }
}
