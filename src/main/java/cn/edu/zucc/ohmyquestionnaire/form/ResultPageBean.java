package cn.edu.zucc.ohmyquestionnaire.form;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
public class ResultPageBean<T, V> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String msg = "success";
    private int code = StatusCode.SUCCESS.getCode();
    private boolean last = false;
    private long totalElements;
    private int size;
    private int totalPages;
    private boolean empty = false;
    private long offset;
    private int page;
    private List<T> data;

    public ResultPageBean() {
        super();
    }

    public ResultPageBean(int page, List<T> data, Page<V> tPage) {
        super();
        this.page = page;
        this.data = data;
        this.totalElements = tPage.getTotalElements();
        this.totalPages = tPage.getTotalPages();
        this.empty = tPage.isEmpty();
        this.last = tPage.isLast();
        this.offset = tPage.getPageable().getOffset();
        this.size = tPage.getSize();
    }

    public ResultPageBean(Throwable err) {
        super();
        this.msg = err.toString();
        this.code = StatusCode.FAIL.getCode();
    }
}
