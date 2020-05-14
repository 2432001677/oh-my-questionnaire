package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;

import java.util.List;

public interface IUser {
    List<BeanUser> allUser();
    void addUser();
}
