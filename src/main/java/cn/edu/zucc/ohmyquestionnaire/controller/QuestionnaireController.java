package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.*;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnswersService;
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
    private final AnswersService answersService;

    public QuestionnaireController(QuestionnaireService questionnaireService, AnswersService answersService) {
        this.questionnaireService = questionnaireService;
        this.answersService = answersService;
    }

    @ApiOperation(value = "获取分享的已发布问卷", notes = "无登录验证")
    @GetMapping("share/{id}")
    public ResultBean<QuestionnaireForm> questionnaire(@PathVariable("id") String id) {
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
    public ResultPageBean<QuestionnaireForm, BeanQuestionnaire> userPageQuestionnaire(@PathVariable("uid") Integer uid, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Page<BeanQuestionnaire> questionnaires = questionnaireService.pageQuestionnaire(uid, page, size);
        List<QuestionnaireForm> data = new ArrayList<>();

        for (BeanQuestionnaire t : questionnaires.getContent()) {
            QuestionnaireForm form = QuestionnaireForm.builder().build();
            BeanUtils.copyProperties(t, form);
            form.setQuestions(null);
            form.setAnswerNum(answersService.getAllAnswers(t.getId()).size());
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
            if (!questionnaire.getUid().equals(deleteForm.getUid())) {
                rtVal.setCode(StatusCode.NO_PERMISSION.getCode());
                rtVal.setMsg("权限不足");
            } else {
                BeanTrashQuestionnaire trashQuestionnaire = BeanTrashQuestionnaire.builder().build();
                BeanUtils.copyProperties(questionnaire, trashQuestionnaire);
                try {
                    questionnaireService.deleteQuestionnaire(questionnaire);
                    // 删除答卷
                    answersService.deleteAllQuestionnaireAnswers(deleteForm.getId());
                    trashQuestionnaire.setDeleteTime(new Date());
                    trashQuestionnaire.setStatus(QuestionnaireCode.DELETED.getCode());
                    questionnaireService.addToTrash(trashQuestionnaire);
                } catch (Exception e) {
                    e.printStackTrace();
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        } else {
            rtVal.setCode(StatusCode.FAIL.getCode());
            rtVal.setMsg("问卷不存在");
        }

        return rtVal;
    }

    @PostMapping("/public")
    public ResultBean<Object> openQuestionnaire(@RequestBody PublicForm publicForm) {
        ResultBean<Object> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(publicForm.getId());
        if (questionnaire != null) {
            questionnaire.setStatus(QuestionnaireCode.OPENING.getCode());
            questionnaireService.updateQuestionnaire(questionnaire);
            rtVal.setMsg("发布成功");
        } else {
            rtVal.setMsg("问卷不存在");
            rtVal.setCode(StatusCode.FAIL.getCode());
        }

        return rtVal;
    }

    @PostMapping("/close")
    public ResultBean<Object> closeQuestionnaire(@RequestBody PublicForm publicForm) {
        ResultBean<Object> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(publicForm.getId());
        if (questionnaire != null) {
            questionnaire.setStatus(QuestionnaireCode.CLOSED.getCode());
            questionnaireService.updateQuestionnaire(questionnaire);
            rtVal.setMsg("已关闭");
        } else {
            rtVal.setMsg("问卷不存在");
            rtVal.setCode(StatusCode.FAIL.getCode());
        }

        return rtVal;
    }
}
