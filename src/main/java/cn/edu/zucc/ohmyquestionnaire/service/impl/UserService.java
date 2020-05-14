package cn.edu.zucc.ohmyquestionnaire.service.impl;

import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.mysql.mapper.IUserMapper;
import cn.edu.zucc.ohmyquestionnaire.service.ILogin;
import cn.edu.zucc.ohmyquestionnaire.service.IUser;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
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
    public void addUser() {
        BeanUser user = BeanUser.builder().userName("a").password("123").build();
        userMapper.insert(user);
    }
}
