package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.form.QuestionForm;
import cn.edu.zucc.ohmyquestionnaire.form.QuestionnaireForm;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanTrashQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.mongo.pojo.Question;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.QuestionnaireDao;
import cn.edu.zucc.ohmyquestionnaire.mongo.repository.TrashDao;
import cn.edu.zucc.ohmyquestionnaire.service.IQuestion;
import cn.edu.zucc.ohmyquestionnaire.service.IQuestionnaire;
import cn.edu.zucc.ohmyquestionnaire.service.ITrash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class QuestionnaireService implements IQuestionnaire, IQuestion, ITrash {
    private final QuestionnaireDao questionnaireDao;
    private final TrashDao trashDao;

    public QuestionnaireService(QuestionnaireDao questionnaireDao, TrashDao trashDao) {
        this.questionnaireDao = questionnaireDao;
        this.trashDao = trashDao;
    }

    @Override
    public BeanQuestionnaire addQuestionnaire(BeanQuestionnaire beanQuestionnaire) {
        beanQuestionnaire.setId(null);
        questionnaireDao.insert(beanQuestionnaire);
        return beanQuestionnaire;
    }

    @Override
    public void deleteQuestionnaire(BeanQuestionnaire beanQuestionnaire) {
        questionnaireDao.delete(beanQuestionnaire);
    }

    @Override
    public BeanQuestionnaire updateQuestionnaire(BeanQuestionnaire beanQuestionnaire) {
        beanQuestionnaire = questionnaireDao.save(beanQuestionnaire);
        return beanQuestionnaire;
    }

    @Override
    public BeanQuestionnaire getQuestionnaire(String id) {
        return questionnaireDao.findById(id).orElse(null);
    }

    @Override
    public BeanTrashQuestionnaire trashQuestionnaire(String id) {
        return trashDao.findById(id).orElse(null);
    }

    @Override
    public Page<BeanQuestionnaire> pageQuestionnaire(int uid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createTime"));
        return questionnaireDao.findAllByUid(uid, pageable);
    }

    @Override
    public Page<BeanTrashQuestionnaire> trashPageQuestionnaire(int uid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createTime"));
        return trashDao.findAllByUid(uid, pageable);
    }

    @Override
    public void deleteTrashQuestionnaire(String id) {
        trashDao.deleteById(id);
    }

    @Override
    public BeanTrashQuestionnaire addToTrash(BeanTrashQuestionnaire trashQuestionnaire) {
        trashDao.insert(trashQuestionnaire);
        return trashQuestionnaire;
    }

    @Override
    public BeanQuestionnaire convertToBean(QuestionnaireForm questionnaireForm) {
        BeanQuestionnaire beanQuestionnaire = BeanQuestionnaire.builder()
                .id(questionnaireForm.getId())
                .title(questionnaireForm.getTitle())
                .status(questionnaireForm.getStatus())
                .createTime(questionnaireForm.getCreateTime())
                .description(questionnaireForm.getDescription())
                .build();

        beanQuestionnaire.setQuestions(convertToQuestionList(questionnaireForm.getQuestions()));
        return beanQuestionnaire;
    }

    @Override
    public List<Question> convertToQuestionList(List<QuestionForm> questionForms) {
        List<Question> questions = new ArrayList<>();
        questionForms.forEach(form -> {
            Map<String, String> options = new LinkedHashMap<>();
            // 遍历List代入下标转成map
            for (int i = 0; i < form.getOptions().size(); i++)
                options.put(Integer.toString(i), form.getOptions().get(i));
            // 给Question赋值
            Question question = Question.builder()
                    .require(form.getRequire())
                    .qtitle(form.getQtitle())
                    .qtype(form.getQtype())
                    .options(options)
                    .build();
            questions.add(question);
        });

        return questions;
    }

    @Override
    public QuestionnaireForm convertToForm(BeanQuestionnaire questionnaire) {
        QuestionnaireForm questionForm = QuestionnaireForm.builder()
                .title(questionnaire.getTitle())
                .status(questionnaire.getStatus())
                .description(questionnaire.getDescription())
                .createTime(questionnaire.getCreateTime())
                .id(questionnaire.getId())
                .build();

        questionForm.setQuestions(convertToQuestionFormList(questionnaire.getQuestions()));
        return questionForm;
    }

    @Override
    public List<QuestionForm> convertToQuestionFormList(List<Question> questions) {
        List<QuestionForm> questionForms = new ArrayList<>();
        questions.forEach(question -> {
            QuestionForm form = QuestionForm.builder()
                    .require(question.getRequire())
                    .qtitle(question.getQtitle())
                    .qtype(question.getQtype())
                    .build();

            // Question的Map转为QuestionForm的List
            Map<String, String> oldOptions = question.getOptions();
            List<String> newOptions = new ArrayList<>(oldOptions.size());
            oldOptions.keySet().forEach((key) -> newOptions.add(Integer.parseInt(key), oldOptions.get(key)));
            form.setOptions(newOptions);
            questionForms.add(form);
        });
        return questionForms;
    }
}
