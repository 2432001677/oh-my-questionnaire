package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.QuestionnaireDao;
import cn.edu.zucc.ohmyquestionnaire.service.IQuestionnaire;
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
}
