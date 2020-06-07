package cn.edu.zucc.ohmyquestionnaire.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class QuestionnaireForm {
    private String id;
    private Integer status;
    private String title;
    private Integer answerNum;
    private String description;
    @JsonAlias("create_time")
    private Date createTime;
    private List<QuestionForm> questions;
}
