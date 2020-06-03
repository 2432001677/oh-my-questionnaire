package cn.edu.zucc.ohmyquestionnaire.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class AnswersForm {
    private String qid;
    private Integer status;
    private String title;
    private String description;
    @JsonAlias("create_time")
    private Date createTime;
    private List<AnswerForm> answers;
}
