package cn.edu.zucc.ohmyquestionnaire.mongo.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Slf4j
public class Question {
    private String qtitle;
    private String qtype;
    private Boolean require;
    private Map<String, String> options;
}
