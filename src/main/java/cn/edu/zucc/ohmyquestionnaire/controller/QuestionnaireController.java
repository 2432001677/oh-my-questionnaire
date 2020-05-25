package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.form.QuestionForm;
import cn.edu.zucc.ohmyquestionnaire.form.QuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.ResultPageBean;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/{uid}")
    public ResultPageBean<QuestionnaireForm, BeanQuestionnaire> pageQuestionnaire(@PathVariable("uid") Integer uid, @RequestParam("page") Integer page) {
        Page<BeanQuestionnaire> questionnaires = questionnaireService.pageQuestionnaire(uid, page);
        List<QuestionnaireForm> data = new ArrayList<>();
        for (BeanQuestionnaire q : questionnaires.getContent()) {
            QuestionnaireForm form = new QuestionnaireForm();
            BeanUtils.copyProperties(q, form);
            data.add(form);
        }

        ResultPageBean<QuestionnaireForm, BeanQuestionnaire> rtVal = new ResultPageBean<>(page, data, questionnaires);

        return rtVal;
    }

    @PostMapping("/{uid}/create")
    public ResultBean<QuestionnaireForm> createQuestionnaire(@PathVariable("uid") Integer uid, @RequestBody QuestionnaireForm questionnaireForm) {
        ResultBean<QuestionnaireForm> rtVal = new ResultBean<>();
        BeanQuestionnaire beanQuestionnaire = BeanQuestionnaire.builder()
                .uid(uid)
                .status(questionnaireForm.getStatus())
                .createTime(questionnaireForm.getCreateTime())
                .title(questionnaireForm.getTitle())
                .build();
        List<Question> questions = new ArrayList<>();
        for (QuestionForm form : questionnaireForm.getQuestions()) {
            Map<String, String> options = new LinkedHashMap<>();
            for (int i = 0; i < form.getOptions().size(); i++)
                options.put(Integer.toString(i), form.getOptions().get(i));
            Question question = Question.builder()
                    .qtitle(form.getQtitle())
                    .qtype(form.getQtype())
                    .require(form.getRequire())
                    .options(options)
                    .build();
            questions.add(question);
        }
        beanQuestionnaire.setQuestions(questions);
        questionnaireService.addQuestionnaire(beanQuestionnaire);
        return rtVal;
    }

    @GetMapping
    public ResultBean<List<BeanQuestionnaire>> allQuestionnaire() {
        return new ResultBean<>(questionnaireService.allQuestionnaire());
    }
}
