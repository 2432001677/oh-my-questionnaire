package cn.edu.zucc.ohmyquestionnaire.mongo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Answer {
    private String qtype;
    private Boolean require;
    private String qtitle;
    private List<Object> selected;
}
