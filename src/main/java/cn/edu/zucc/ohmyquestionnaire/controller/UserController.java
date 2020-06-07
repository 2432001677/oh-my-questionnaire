package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.*;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.service.impl.AnswersService;
import cn.edu.zucc.ohmyquestionnaire.service.impl.QuestionnaireService;
import cn.edu.zucc.ohmyquestionnaire.service.impl.UserService;
import cn.edu.zucc.ohmyquestionnaire.utils.TokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final AnswersService answersService;
    private final QuestionnaireService questionnaireService;

    public UserController(UserService userService, AnswersService answersService, QuestionnaireService questionnaireService) {
        this.userService = userService;
        this.answersService = answersService;
        this.questionnaireService = questionnaireService;
    }

    @ApiOperation(value = "返回用户下的某个问卷")
    @GetMapping("{uid}/questionnaire/{id}")
    public ResultBean<QuestionnaireForm> userQuestionnaire(@PathVariable("uid") Integer uid, @PathVariable("id") String id) {
        ResultBean<QuestionnaireForm> rtVal = new ResultBean<>();
        BeanQuestionnaire questionnaire = questionnaireService.getQuestionnaire(id);
        if (questionnaire != null) {
            if (!questionnaire.getUid().equals(uid)) {
                rtVal.setCode(StatusCode.NO_PERMISSION.getCode());
                rtVal.setMsg("权限不足");
            } else {
                QuestionnaireForm questionnaireForm = questionnaireService.convertToForm(questionnaire);
                rtVal.setData(questionnaireForm);
            }
        } else {
            rtVal.setCode(StatusCode.FAIL.getCode());
            rtVal.setMsg("不存在");
        }
        return rtVal;
    }

    @ApiOperation(value = "创建问卷,有id则表示修改")
    @Transactional(rollbackFor = {Exception.class})
    @PostMapping("/{uid}/questionnaire")
    public ResultBean<QuestionnaireForm> createOneQuestionnaire(@PathVariable("uid") Integer uid, @RequestBody QuestionnaireForm questionnaireForm) {
        ResultBean<QuestionnaireForm> rtVal = new ResultBean<>();

        BeanQuestionnaire beanQuestionnaire;
        if (!"".equals(questionnaireForm.getId()) && questionnaireForm.getId() != null) {
            beanQuestionnaire = questionnaireService.getQuestionnaire(questionnaireForm.getId());
            if (beanQuestionnaire == null) {
                rtVal.setMsg("问卷不存在");
                rtVal.setCode(StatusCode.FAIL.getCode());
                return rtVal;
            }
            try {
                beanQuestionnaire = questionnaireService.convertToBean(questionnaireForm);
                beanQuestionnaire.setUid(uid);
                beanQuestionnaire = questionnaireService.updateQuestionnaire(beanQuestionnaire);
                // 修改就删除答卷
                answersService.deleteAllQuestionnaireAnswers(questionnaireForm.getId());
                rtVal.setMsg("修改成功");
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } else {
            beanQuestionnaire = questionnaireService.convertToBean(questionnaireForm);
            beanQuestionnaire.setUid(uid);
            beanQuestionnaire = questionnaireService.addQuestionnaire(beanQuestionnaire);
            rtVal.setMsg("添加成功");
        }
        rtVal.setData(questionnaireService.convertToForm(beanQuestionnaire));

        return rtVal;
    }

    @ApiOperation(value = "登录并返回uid,userName", notes = "无账号则创建新的账号并返回,response header带token")
    @PostMapping("/login")
    public ResponseEntity<ResultBean<UserForm>> login(@RequestBody UserLoginForm userLoginForm) {
        BeanUser beanUser = userService.findUser(userLoginForm);
        ResultBean<UserForm> rtVal = new ResultBean<>();
        HttpHeaders responseHeaders = new HttpHeaders();
        String token;
        if (beanUser == null) {
            rtVal.setData(userService.addUser(userLoginForm));
            rtVal.setMsg("创建成功");
            token = TokenUtil.sign(userLoginForm);
            responseHeaders.set("token", token);
        } else {
            if (!(beanUser.getPassword().equals(userLoginForm.getPassword()))) {
                rtVal.setMsg("密码错误");
                rtVal.setCode(StatusCode.FAIL.getCode());
            } else {
                rtVal.setData(UserForm.builder().uid(beanUser.getUid()).userName(beanUser.getUserName()).build());
                rtVal.setMsg("登录成功");
                token = TokenUtil.sign(userLoginForm);
                responseHeaders.set("x-token", token);
            }
        }
        List<String> header = new ArrayList<>();
        header.add("x-token");
        responseHeaders.setAccessControlExposeHeaders(header);

        return new ResponseEntity<>(rtVal, responseHeaders, HttpStatus.OK);
    }
}
