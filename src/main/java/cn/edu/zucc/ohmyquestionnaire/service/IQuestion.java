package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.form.QuestionForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;

import java.util.List;

public interface IQuestion {
    List<Question> convertToQuestionList(List<QuestionForm> questionForms);

    List<QuestionForm> convertToQuestionFormList(List<Question> questions);
}
