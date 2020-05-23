package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.QuestionnaireDao;
import cn.edu.zucc.ohmyquestionnaire.service.IQuestionnaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireService implements IQuestionnaire {
    private final QuestionnaireDao questionnaireDao;

    public QuestionnaireService(QuestionnaireDao questionnaireDao) {
        this.questionnaireDao = questionnaireDao;
    }

    @Override
    public List<BeanQuestionnaire> allQuestionnaire() {
        return questionnaireDao.findAll();
    }

    public Page<BeanQuestionnaire> pageQuestionnaire(int uid, int page) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by(Sort.Direction.ASC,"createTime"));
        return questionnaireDao.findAllByUid(uid, pageable);
    }
}
