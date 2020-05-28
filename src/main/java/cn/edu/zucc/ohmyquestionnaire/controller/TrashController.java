package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.TrashQuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("trash")
public class TrashController {
    private final QuestionnaireService questionnaireService;

    public TrashController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @ApiOperation(value = "移出垃圾箱")
    @Transactional(rollbackFor = {Exception.class})
    @GetMapping("/recover/{id}")
    public ResultBean<TrashQuestionnaireForm> userTrashQuestionnaire(@PathVariable("id") String id) {
        ResultBean<TrashQuestionnaireForm> rtVal = new ResultBean<>();
        BeanTrashQuestionnaire trashQuestionnaire = questionnaireService.trashQuestionnaire(id);
        if (trashQuestionnaire != null) {
            BeanQuestionnaire questionnaire = BeanQuestionnaire.builder().build();
            BeanUtils.copyProperties(trashQuestionnaire, questionnaire);
            questionnaire.setStatus(QuestionnaireCode.CLOSED.getCode());
            // 删除垃圾箱内document
            try {
                questionnaireService.deleteTrashQuestionnaire(id);
                questionnaireService.addQuestionnaire(questionnaire);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //回滚
            }
        }

        return rtVal;
    }
}
