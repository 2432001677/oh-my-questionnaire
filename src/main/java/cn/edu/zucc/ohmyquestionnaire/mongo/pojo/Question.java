package cn.edu.zucc.ohmyquestionnaire.mongo.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@Slf4j
public class Question {
    private String qtype;
    private Boolean require;
    private String qtitle;
    private Map<String, String> options;
}
