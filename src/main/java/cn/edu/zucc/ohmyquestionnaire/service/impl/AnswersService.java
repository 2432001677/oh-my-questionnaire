package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.form.AnswerForm;
import cn.edu.zucc.ohmyquestionnaire.form.AnswersForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanAnswers;
import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Answer;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.AnswersDao;
import cn.edu.zucc.ohmyquestionnaire.service.IAnswer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnswersService implements IAnswer {
    private final AnswersDao answersDao;

    public AnswersService(AnswersDao answersDao) {
        this.answersDao = answersDao;
    }

    @Override
    public List<BeanAnswers> getAllAnswers(String qid) {
        return answersDao.findByQid(qid);
    }

    @Override
    public void deleteAllQuestionnaireAnswers(String qid) {
        answersDao.deleteAllByQid(qid);
    }

    @Override
    public BeanAnswers addAnswers(BeanAnswers beanAnswers) {
        beanAnswers.setId(null);
        answersDao.insert(beanAnswers);
        return beanAnswers;
    }

    @Override
    public BeanAnswers convertToBean(AnswersForm answersForm) {
        BeanAnswers beanAnswers = BeanAnswers.builder()
                .qid(answersForm.getQid())
                .title(answersForm.getTitle())
                .answerTime(new Date())
                .build();
        beanAnswers.setAnswers(convertToAnswerList(answersForm.getAnswers()));
        return beanAnswers;
    }

    public List<Answer> convertToAnswerList(List<AnswerForm> answerFormList) {
        List<Answer> answerList = new ArrayList<>();
        answerFormList.forEach(answerForm -> {
            Map<String, String> options = new LinkedHashMap<>();
            for (int i = 0; i < answerForm.getOptions().size(); i++)
                options.put(Integer.toString(i), answerForm.getOptions().get(i));
            Answer answer = Answer.builder()
                    .selected(answerForm.getSelected())
                    .require(answerForm.getRequire())
                    .qtitle(answerForm.getQtitle())
                    .qtype(answerForm.getQtype())
                    .options(options)
                    .build();
            answerList.add(answer);
        });

        return answerList;
    }
}
