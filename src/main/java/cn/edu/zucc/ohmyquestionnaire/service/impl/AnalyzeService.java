package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.form.StatisticForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.AnswersDao;
import cn.edu.zucc.ohmyquestionnaire.service.IAnalyze;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyzeService implements IAnalyze {
    private final AnswersDao answersDao;

    public AnalyzeService(AnswersDao answersDao) {
        this.answersDao = answersDao;
    }

}
