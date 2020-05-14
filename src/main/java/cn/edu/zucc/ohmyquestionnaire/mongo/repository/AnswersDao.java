package cn.edu.zucc.ohmyquestionnaire.mongo.repository;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswersDao extends MongoRepository<BeanAnswers,String > {
}
