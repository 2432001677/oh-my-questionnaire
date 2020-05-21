package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import cn.edu.zucc.ohmyquestionnaire.form.UserForm;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.mysql.mapper.IUserMapper;
import cn.edu.zucc.ohmyquestionnaire.service.ILogin;
import cn.edu.zucc.ohmyquestionnaire.service.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserService implements IUser, ILogin {
    private final IUserMapper userMapper;

    public UserService(IUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<BeanUser> allUser() {
        return userMapper.selectAll();
    }

    @Override
    public UserForm addUser(UserLoginForm loginForm) {
        userMapper.insert(BeanUser.builder().userName(loginForm.getUserName()).password(loginForm.getPassword()).build());
        return UserForm.builder().userName(loginForm.getUserName()).build();
    }

    @Override
    public BeanUser findUser(UserLoginForm loginForm) {
        return userMapper.selectOne(BeanUser.builder().userName(loginForm.getUserName()).build());
    }
}
