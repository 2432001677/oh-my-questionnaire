package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import cn.edu.zucc.ohmyquestionnaire.form.UserForm;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;

import java.util.List;

public interface IUser {
    List<BeanUser> allUser();
    UserForm addUser(UserLoginForm loginForm);
}
