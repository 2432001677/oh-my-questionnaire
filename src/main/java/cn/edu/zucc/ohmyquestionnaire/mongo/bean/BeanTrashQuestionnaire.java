package cn.edu.zucc.ohmyquestionnaire.mongo.bean;

import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
@Document(collection = "trash_questionnaire")
public class BeanTrashQuestionnaire {
    @Id
    private String id;
    @Field("uid")
    private Integer uid;
    @Field("status")
    private Integer status;
    @Field("title")
    private String title;
    @Field("description")
    private String description;
    @Field("create_time")
    @JsonAlias("create_time")
    private Date createTime;
    @Field("delete_time")
    @JsonAlias("delete_time")
    private Date deleteTime;
    @Field("questions")
    private List<Question> questions;
}
