package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.StatisticForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnalyzeService;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnswersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("analyze")
public class AnalyzeController {
    private final AnalyzeService analyzeService;
    private final AnswersService answersService;

    public AnalyzeController(AnalyzeService analyzeService, AnswersService answersService) {
        this.analyzeService = analyzeService;
        this.answersService = answersService;
    }

    @GetMapping("/{id}")
    public ResultBean<StatisticForm> statisticAnalyze(@PathVariable("id") String id) {
        ResultBean<StatisticForm> rtVal = new ResultBean<>();
        List<BeanAnswers> beanAnswersList = answersService.getAllAnswers(id);
        if (beanAnswersList == null || beanAnswersList.size() == 0) {
            rtVal.setData(null);
        } else {
            StatisticForm statisticForm = analyzeService.convertToStatisticForm(beanAnswersList);
        }

        return null;
    }
}
