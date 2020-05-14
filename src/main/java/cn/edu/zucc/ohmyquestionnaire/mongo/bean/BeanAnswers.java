package cn.edu.zucc.ohmyquestionnaire.mongo.bean;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Slf4j
@Document(collection = "answer")
@Builder(toBuilder = true)
public class BeanAnswers {
    @Id
    private String id;
    @Field("qid")
    private String qid;
    @Field("answers")
    private Map<String, Object> answers;
}
