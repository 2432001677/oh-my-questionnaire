package cn.edu.zucc.ohmyquestionnaire.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class TrashQuestionnaireForm {
    private String id;
    private Integer status;
    private String title;
    private String description;
    @JsonAlias("create_time")
    private Date createTime;
    @JsonAlias("delete_time")
    private Date deleteTime;
    private List<QuestionForm> questions;
}
