package cn.edu.zucc.ohmyquestionnaire.mongo.repository;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionnaireDao extends MongoRepository<BeanQuestionnaire, String> {
}
