package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<BeanUser> getAllUser(){
        return userService.allUser();
    }
    @GetMapping("/add")
    public void addAllUser(){
        userService.addUser();
    }
}
