package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.mongo.bean.BeanQuestionnaire;

import java.util.List;

public interface IQuestionnaire{
    List<BeanQuestionnaire> allQuestionnaire();
}
