package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/all")
    public List<BeanQuestionnaire> allQuestion(){
        return questionnaireService.allQuestionnaire();
    }
}
