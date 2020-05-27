package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IQuestionnaire {
    List<BeanQuestionnaire> allQuestionnaire();

    BeanQuestionnaire getQuestionnaire(String id);

    BeanQuestionnaire addQuestionnaire(BeanQuestionnaire beanQuestionnaire);

    Page<BeanTrashQuestionnaire> trashPageQuestionnaire(int uid, int page);

    Page<BeanQuestionnaire> pageQuestionnaire(int uid, int page);
}
