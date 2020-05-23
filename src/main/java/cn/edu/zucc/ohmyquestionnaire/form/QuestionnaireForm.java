package cn.edu.zucc.ohmyquestionnaire.form;

import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionnaireForm {
    private String status;
    private String title;
    private Date createTime;
    private List<Question> questions;
}
