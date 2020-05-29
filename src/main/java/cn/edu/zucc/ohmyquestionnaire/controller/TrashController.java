package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.form.QuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.ResultPageBean;
import cn.edu.zucc.ohmyquestionnaire.form.TrashQuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @ApiOperation(value = "分页返回用户垃圾箱问卷")
    @GetMapping("/user/{uid}")
    public ResultPageBean<QuestionnaireForm, BeanTrashQuestionnaire> userTrashPageQuestionnaire(@PathVariable("uid") Integer uid, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<BeanTrashQuestionnaire> questionnaires = questionnaireService.trashPageQuestionnaire(uid, page, size);
        List<QuestionnaireForm> data = new ArrayList<>();
        for (BeanTrashQuestionnaire t : questionnaires.getContent()) {
            QuestionnaireForm form = QuestionnaireForm.builder().build();
            BeanUtils.copyProperties(t, form);
            form.setQuestions(null);
            data.add(form);
        }
        return new ResultPageBean<>(page, data, questionnaires);
    }

    @ApiOperation(value = "永久删除一个垃圾箱问卷")
    @PostMapping("/delete/{id}")
    public ResultBean<Object> deleteOneTrash(@PathVariable("id") String id) {
        ResultBean<Object> rtVal = new ResultBean<>();
        questionnaireService.deleteTrashQuestionnaire(id);
        rtVal.setData(null);
        return rtVal;
    }

    @ApiOperation(value = "清空用户垃圾箱问卷")
    @PostMapping("/clear/user/{uid}")
    public ResultBean<Object> clearTrash(@PathVariable("uid") Integer uid) {
        ResultBean<Object> rtVal = new ResultBean<>();
        questionnaireService.clearTrash(uid);
        rtVal.setData(null);
        return rtVal;
    }
}
