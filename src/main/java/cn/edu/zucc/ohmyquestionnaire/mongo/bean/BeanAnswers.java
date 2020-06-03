package cn.edu.zucc.ohmyquestionnaire.mongo.bean;

import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Answer;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "answer")
@Builder(toBuilder = true)
public class BeanAnswers {
    @Id
    private String id;
    @Field("qid")
    private String qid;
    @Field("title")
    private String title;
    @Field("answer_time")
    @JsonAlias("answer_time")
    private Date answerTime;
    @Field("questions")
    private List<Answer> answers;
}
