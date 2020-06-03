package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.AnswersForm;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnswersService;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("answer")
public class AnswerController {
    private final AnswersService answersService;
    private final QuestionnaireService questionnaireService;

    public AnswerController(QuestionnaireService questionnaireService, AnswersService answersService) {
        this.questionnaireService = questionnaireService;
        this.answersService = answersService;
    }

    @PostMapping
    public ResultBean<AnswersForm> answerQuestionnaire(@RequestBody AnswersForm answersForm) {
        ResultBean<AnswersForm> rtVal = new ResultBean<>();

        BeanQuestionnaire beanQuestionnaire = questionnaireService.getQuestionnaire(answersForm.getQid());
        if (beanQuestionnaire == null) {
            rtVal.setMsg("问卷已被删除");
            rtVal.setCode(StatusCode.FAIL.getCode());
        } else {
            if (beanQuestionnaire.getStatus().equals(QuestionnaireCode.CLOSED.getCode())) {
                rtVal.setMsg("问卷已关闭");
                rtVal.setCode(StatusCode.FAIL.getCode());
            } else {
                BeanAnswers beanAnswers = answersService.convertToBean(answersForm);
                answersService.addAnswers(beanAnswers);
                rtVal.setMsg("提交成功");
            }
        }
        return rtVal;
    }

}
