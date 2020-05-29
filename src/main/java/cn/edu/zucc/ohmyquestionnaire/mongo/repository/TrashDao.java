package cn.edu.zucc.ohmyquestionnaire.mongo.repository;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrashDao extends MongoRepository<BeanTrashQuestionnaire, String> {
    Page<BeanTrashQuestionnaire> findAllByUid(Integer uid, Pageable pageable);

    void deleteAllByUid(Integer uid);
}
