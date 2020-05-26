package cn.edu.zucc.ohmyquestionnaire.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionnaireForm {
    private Integer status;
    private String title;
    @JsonAlias("create_time")
    private Date createTime;
    private List<QuestionForm> questions;
}
