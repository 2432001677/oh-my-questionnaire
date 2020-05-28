package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.QuestionnaireCode;
import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.*;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping("/{id}")
    public ResultBean<QuestionnaireForm> userQuestionnaire(@PathVariable("id") String id) {
        ResultBean<QuestionnaireForm> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(id);
        if (questionnaire != null) {
            if (questionnaire.getStatus().equals(QuestionnaireCode.OPENING.getCode())) {
                List<QuestionForm> questionFormList = new ArrayList<>();
                // Question的Map转为QuestionForm的List
                for (Question question : questionnaire.getQuestions()) {
                    Map<String, String> oldOptions = question.getOptions();
                    QuestionForm questionForm = QuestionForm.builder().build();
                    List<String> newOptions = new ArrayList<>(oldOptions.size());
                    oldOptions.keySet().forEach((key) -> newOptions.add(Integer.parseInt(key), oldOptions.get(key)));

                    BeanUtils.copyProperties(question, questionForm);
                    questionForm.setOptions(newOptions);
                    questionFormList.add(questionForm);
                }
                QuestionnaireForm questionnaireForm = QuestionnaireForm.builder()
                        .description(questionnaire.getDescription())
                        .createTime(questionnaire.getCreateTime())
                        .status(questionnaire.getStatus())
                        .title(questionnaire.getTitle())
                        .questions(questionFormList)
                        .id(questionnaire.getId())
                        .build();

                rtVal.setData(questionnaireForm);
            } else {
                rtVal.setMsg("该问卷已关闭或被删除");
                rtVal.setCode(StatusCode.NO_PERMISSION.getCode());
            }
        } else {
            rtVal.setMsg("不存在");
            rtVal.setCode(StatusCode.FAIL.getCode());
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

    @PostMapping("/delete")
    public ResultBean<Object> deleteQuestionnaire(@RequestBody DeleteForm deleteForm) {
        ResultBean<Object> rtVal = new ResultBean<>();

        return rtVal;
    }
}
