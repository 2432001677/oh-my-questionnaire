package cn.edu.zucc.ohmyquestionnaire.mongo.repository;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireDao extends MongoRepository<BeanQuestionnaire, String> {
    Page<BeanQuestionnaire> findAllByUid(Integer uid, Pageable pageable);
}
