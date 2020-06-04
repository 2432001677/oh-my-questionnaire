package cn.edu.zucc.ohmyquestionnaire.form;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class AnalysisForm {
    private String qtitle;
    private String qtype;
    private Boolean require;
    @JsonAlias("answer_count")
    private Integer answerCount;
    private List<Option> options;
}
