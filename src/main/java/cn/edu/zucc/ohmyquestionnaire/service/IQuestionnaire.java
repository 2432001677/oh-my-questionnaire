package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.form.QuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import org.springframework.data.domain.Page;


public interface IQuestionnaire {
    BeanQuestionnaire convertToBean(QuestionnaireForm questionnaireForm);

    QuestionnaireForm convertToForm(BeanQuestionnaire questionnaire);

    BeanQuestionnaire getQuestionnaire(String id);

    BeanQuestionnaire addQuestionnaire(BeanQuestionnaire beanQuestionnaire);

    BeanQuestionnaire updateQuestionnaire(BeanQuestionnaire beanQuestionnaire);

    Page<BeanQuestionnaire> pageQuestionnaire(int uid, int page, int size);
}
