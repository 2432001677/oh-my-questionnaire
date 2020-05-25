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
@Builder(toBuilder = true)
@Document(collection = "questionnaire")
public class BeanQuestionnaire {
    @Id
    private String id;
    @Field("uid")
    private Integer uid;
    @Field("status")
    private String status;
    @Field("title")
    private String title;
    @Field("create_time")
    @JsonAlias("create_time")
    private Date createTime;
    @Field("questions")
    private List<Question> questions;
}
