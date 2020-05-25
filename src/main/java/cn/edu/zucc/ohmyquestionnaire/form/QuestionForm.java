package cn.edu.zucc.ohmyquestionnaire.form;

import lombok.Data;

import java.util.List;

@Data
public class QuestionForm {
    private String qtitle;
    private String qtype;
    private Boolean require;
    private List<String> options;
}
