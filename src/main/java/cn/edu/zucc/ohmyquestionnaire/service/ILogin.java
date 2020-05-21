package cn.edu.zucc.ohmyquestionnaire.service;

import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;

public interface ILogin {
    BeanUser findUser(UserLoginForm loginForm);
}
