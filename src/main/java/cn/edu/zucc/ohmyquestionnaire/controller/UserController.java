package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import cn.edu.zucc.ohmyquestionnaire.form.UserForm;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.service.impl.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "登录并返回uid,userName", notes = "无账号则创建新的账号并返回")
    @PostMapping("/login")
    public ResultBean<UserForm> login(@RequestBody UserLoginForm userLoginForm) {
        BeanUser beanUser = userService.findUser(userLoginForm);
        ResultBean<UserForm> rtVal = new ResultBean<>();
        if (beanUser == null) {
            rtVal.setData(userService.addUser(userLoginForm));
            rtVal.setMsg("创建成功");
        } else {
            rtVal.setData(UserForm.builder().uid(beanUser.getUid()).userName(beanUser.getUserName()).build());
            rtVal.setMsg("登录成功");
            if (!(beanUser.getPassword().equals(userLoginForm.getPassword()))) {
                rtVal.setMsg("密码错误");
                rtVal.setCode(StatusCode.FAIL.getCode());
            }
        }
        return rtVal;
    }
}
