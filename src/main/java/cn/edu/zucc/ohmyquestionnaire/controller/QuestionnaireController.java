package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.form.QuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.ResultPageBean;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/page")
    public ResultPageBean<QuestionnaireForm, BeanQuestionnaire> pageQuestionnaire(@RequestParam("uid") Integer uid, @RequestParam("page") Integer page) {
        Page<BeanQuestionnaire> questionnaires = questionnaireService.pageQuestionnaire(uid, page);
        List<QuestionnaireForm> data = new ArrayList<>();
        for (BeanQuestionnaire q : questionnaires.getContent()) {
            QuestionnaireForm form = new QuestionnaireForm();
            BeanUtils.copyProperties(q,form);
            data.add(form);
        }

        ResultPageBean<QuestionnaireForm, BeanQuestionnaire> rtVal = new ResultPageBean<>(page, data, questionnaires);

        return rtVal;
    }

    @GetMapping("/all")
    public ResultBean<List<BeanQuestionnaire>> allQuestionnaire() {
        return new ResultBean<>(questionnaireService.allQuestionnaire());
    }
}
