package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.QuestionnaireDao;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.TrashDao;
import cn.edu.zucc.ohmyquestionnaire.service.IQuestionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireService implements IQuestionnaire {
    private final QuestionnaireDao questionnaireDao;
    private final TrashDao trashDao;

    public QuestionnaireService(QuestionnaireDao questionnaireDao, TrashDao trashDao) {
        this.questionnaireDao = questionnaireDao;
        this.trashDao = trashDao;
    }

    @Override
    public List<BeanQuestionnaire> allQuestionnaire() {
        return questionnaireDao.findAll();
    }

    @Override
    public BeanQuestionnaire addQuestionnaire(BeanQuestionnaire beanQuestionnaire) {
        questionnaireDao.save(beanQuestionnaire);
        return beanQuestionnaire;
    }

    @Override
    public BeanQuestionnaire getQuestionnaire(String id) {
        return questionnaireDao.findById(id).orElse(null);
    }

    @Override
    public Page<BeanQuestionnaire> pageQuestionnaire(int uid, int page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by(Sort.Direction.ASC, "createTime"));
        return questionnaireDao.findAllByUid(uid, pageable);
    }

    @Override
    public Page<BeanTrashQuestionnaire> trashPageQuestionnaire(int uid, int page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by(Sort.Direction.ASC, "createTime"));
        return trashDao.findAllByUid(uid, pageable);
    }

}
