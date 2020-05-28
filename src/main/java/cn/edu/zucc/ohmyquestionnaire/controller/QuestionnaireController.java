package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.*;
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

import java.util.*;

@Slf4j
@RestController
@RequestMapping("questionnaire")
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping("/{id}")
    public ResultBean<QuestionnaireForm> userQuestionnaire(@PathVariable("id") String id) {
        ResultBean<QuestionnaireForm> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(id);
        if (questionnaire != null) {
            if (questionnaire.getStatus().equals(QuestionnaireCode.OPENING.getCode())) {
                QuestionnaireForm questionnaireForm = questionnaireService.convertToForm(questionnaire);
                rtVal.setData(questionnaireForm);
            } else {
                rtVal.setCode(StatusCode.NO_PERMISSION.getCode());
                rtVal.setMsg("该问卷已关闭或被删除");
            }
        } else {
            rtVal.setCode(StatusCode.FAIL.getCode());
            rtVal.setMsg("不存在");
        }
        return rtVal;
    }

    @ApiOperation(value = "分页返回用户已发布和未发布问卷")
    @GetMapping("/user/{uid}")
    public ResultPageBean<QuestionnaireForm, BeanQuestionnaire> userPageQuestionnaire(@PathVariable("uid") Integer uid, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        if (size == null || size <= 0) size = 5;
        Page<BeanQuestionnaire> questionnaires = questionnaireService.pageQuestionnaire(uid, page, size);
        List<QuestionnaireForm> data = new ArrayList<>();

        for (BeanQuestionnaire t : questionnaires.getContent()) {
            QuestionnaireForm form = QuestionnaireForm.builder().build();
            BeanUtils.copyProperties(t, form);
            form.setQuestions(null);
            data.add(form);
        }
        return new ResultPageBean<>(page, data, questionnaires);
    }

    @ApiOperation(value = "分页返回用户垃圾箱问卷")
    @GetMapping("/user/{uid}/trash")
    public ResultPageBean<QuestionnaireForm, BeanTrashQuestionnaire> userTrashPageQuestionnaire(@PathVariable("uid") Integer uid, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        if (size == null || size <= 0) size = 5;
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

    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/delete")
    public ResultBean<Object> deleteQuestionnaire(@RequestBody DeleteForm deleteForm) {
        ResultBean<Object> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(deleteForm.getId());
        if (questionnaire != null) {
            BeanTrashQuestionnaire trashQuestionnaire = BeanTrashQuestionnaire.builder().build();
            BeanUtils.copyProperties(questionnaire, trashQuestionnaire);
            try {
                questionnaireService.deleteQuestionnaire(questionnaire);
                trashQuestionnaire.setDeleteTime(new Date());
                trashQuestionnaire.setStatus(QuestionnaireCode.DELETED.getCode());
                questionnaireService.addToTrash(trashQuestionnaire);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } else {
            rtVal.setCode(StatusCode.FAIL.getCode());
            rtVal.setMsg("问卷不存在");
        }

        return rtVal;
    }
}
