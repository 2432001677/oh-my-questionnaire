package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import org.springframework.data.domain.Page;

public interface ITrash {
    BeanTrashQuestionnaire trashQuestionnaire(String id);

    Page<BeanTrashQuestionnaire> trashPageQuestionnaire(int uid, int page,int size);

    void deleteTrashQuestionnaire(String id);
}
