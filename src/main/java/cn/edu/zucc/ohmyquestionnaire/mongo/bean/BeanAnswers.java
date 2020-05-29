package cn.edu.zucc.ohmyquestionnaire.mongo.bean;

import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Slf4j
@Document(collection = "answer")
@Builder(toBuilder = true)
public class BeanAnswers {
    @Id
    private String id;
    @Field("qid")
    private String qid;
    @Field("answer_time")
    @JsonAlias("answer_time")
    private Date answerTime;
    @Field("score")
    private Double score;
    @Field("questions")
    private List<Question> questions;
}
