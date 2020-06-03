package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.form.AnswersForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;

public interface IAnswer {
    BeanAnswers convertToBean(AnswersForm answersForm);

    BeanAnswers addAnswers(BeanAnswers beanAnswers);
}
