package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.AnalysisForm;
import cn.edu.zucc.ohmyquestionnaire.form.Option;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.StatisticForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnalyzeService;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnswersService;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("analyze")
public class AnalyzeController {
    private final AnalyzeService analyzeService;
    private final QuestionnaireService questionnaireService;
    private final AnswersService answersService;

    public AnalyzeController(AnalyzeService analyzeService, QuestionnaireService questionnaireService, AnswersService answersService) {
        this.analyzeService = analyzeService;
        this.questionnaireService = questionnaireService;
        this.answersService = answersService;
    }

    @GetMapping("/{id}")
    public ResultBean<StatisticForm> statisticAnalyze(@PathVariable("id") String id) {
        ResultBean<StatisticForm> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(id);
        if (questionnaire == null) {
            rtVal.setMsg("问卷不存在");
            rtVal.setCode(StatusCode.FAIL.getCode());
            return rtVal;
        }

        List<BeanAnswers> beanAnswersList = answersService.getAllAnswers(id);
        StatisticForm statisticForm = StatisticForm.builder()
                .title(questionnaire.getTitle())
                .build();

        if (beanAnswersList == null || beanAnswersList.size() == 0) {
            statisticForm = statisticForm.toBuilder()
                    .analysisFormList(new ArrayList<>())
                    .answerCount(0)
                    .build();
            rtVal.setData(statisticForm);
            rtVal.setMsg("没有答卷");
        } else {
            List<AnalysisForm> analysisFormList = new ArrayList<>();
            // 初始化问卷的每个问题
            questionnaire.getQuestions().forEach(question -> {
                List<Option> analyzeOptions = new ArrayList<>();
                // 加入单个问题的每个选项信息
                question.getOptions().forEach((k, v) -> analyzeOptions.add(Option.builder().selectedCount(0.0).text(v).build()));
                // 创建单个问题
                AnalysisForm analysisForm = AnalysisForm.builder()
                        .answerCount(beanAnswersList.size())
                        .require(question.getRequire())
                        .qtitle(question.getQtitle())
                        .qtype(question.getQtype())
                        .options(analyzeOptions)
                        .build();
                analysisFormList.add(analysisForm);
            });
            for (int i = 0; i < analysisFormList.size(); i++) {
                // 单个问题分析
                AnalysisForm analysisForm = analysisFormList.get(i);
                // 问题选项集合
                Map<String, String> questionOptions = questionnaire.getQuestions().get(i).getOptions();
                log.debug(questionOptions.toString());
                // 回答选项分析集合
                List<Option> analyzeOptions = analysisFormList.get(i).getOptions();
                for (BeanAnswers answers : beanAnswersList) {
                    // 单题类别
                    String qtype = answers.getAnswers().get(i).getQtype();
                    // 单题选项
                    Map<String, String> answerOptions = answers.getAnswers().get(i).getOptions();
                    // 单题回答
                    List<String> selectedList = answers.getAnswers().get(i).getSelected();
                    switch (qtype) {
                        case "下拉":
                        case "单选": {
                            String selected = selectedList.get(0);
                            for (String key : questionOptions.keySet()) {
                                if (selected.equals(answerOptions.get(key))) {
                                    int index = Integer.parseInt(key);
                                    analyzeOptions.get(index).setSelectedCount(analyzeOptions.get(index).getSelectedCount() + 1);
                                    break;
                                }
                            }
                            break;
                        }
                        case "评分": {
                            if (analyzeOptions.size() == 0) {
                                for (int j = 1; j < 6; j++) {
                                    analyzeOptions.add(Option.builder().selectedCount(0.0).text(Integer.toString(j)).build());
                                }
                            }
                            int score = Integer.parseInt(selectedList.get(0));
                            score = score <= 0 ? 1 : score;
                            analyzeOptions.get(score - 1).setSelectedCount(analyzeOptions.get(score - 1).getSelectedCount() + 1);
                            break;
                        }
                        case "比重": {
                            for (int j = 0; j < answerOptions.size(); j++) {
                                double percent = analyzeOptions.get(j).getSelectedCount();
                                analyzeOptions.get(j).setSelectedCount(percent + Double.parseDouble(selectedList.get(j)) / beanAnswersList.size());
                            }
                            break;
                        }
                        case "填空": {
                            analyzeOptions.add(Option.builder().text(selectedList.get(0)).build());
                            break;
                        }
                        case "多选": {
                            for (String selected : selectedList) {
                                for (String key : questionOptions.keySet()) {
                                    if (selected.equals(answerOptions.get(key))) {
                                        int index = Integer.parseInt(key);
                                        analyzeOptions.get(index).setSelectedCount(analyzeOptions.get(index).getSelectedCount() + 1);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
            statisticForm = statisticForm.toBuilder()
                    .answerCount(beanAnswersList.size())
                    .analysisFormList(analysisFormList)
                    .build();
            rtVal.setData(statisticForm);
        }

        return rtVal;
    }
}
