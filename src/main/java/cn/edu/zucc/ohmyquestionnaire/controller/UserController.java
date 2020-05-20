package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResultBean<List<BeanUser>> getAllUser() {
        return new ResultBean<>(userService.allUser());
    }

    @GetMapping("/add")
    public ResultBean<BeanUser> addAllUser() {
        userService.addUser();
        return new ResultBean<>(BeanUser.builder().uid(2).userName("1").build());
    }

    @PostMapping("/login")
    public ResultBean<BeanUser> login(UserLoginForm userLoginForm) {
        return null;
    }
}
