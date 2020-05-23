package cn.edu.zucc.ohmyquestionnaire.mongo.repository;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersDao extends MongoRepository<BeanAnswers,String > {
}
