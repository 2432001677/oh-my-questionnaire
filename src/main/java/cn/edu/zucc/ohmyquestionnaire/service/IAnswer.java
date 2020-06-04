package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.form.AnswersForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;

import java.util.List;

public interface IAnswer {
    BeanAnswers convertToBean(AnswersForm answersForm);

    BeanAnswers addAnswers(BeanAnswers beanAnswers);

    List<BeanAnswers> getAllAnswers(String qid);
}
