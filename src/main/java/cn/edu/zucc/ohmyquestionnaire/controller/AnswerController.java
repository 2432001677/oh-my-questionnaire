package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.form.AnswersForm;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("answer")
public class AnswerController {
    @PostMapping("/")
    public ResultBean<AnswersForm> answerQuestionnaire() {
        ResultBean<AnswersForm> rtVal = new ResultBean<>();

        return rtVal;
    }

}
