package cn.edu.zucc.ohmyquestionnaire.controller;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.UserLoginForm;
import cn.edu.zucc.ohmyquestionnaire.form.UserForm;
import cn.edu.zucc.ohmyquestionnaire.mysql.bean.BeanUser;
import cn.edu.zucc.ohmyquestionnaire.service.impl.UserService;
import cn.edu.zucc.ohmyquestionnaire.utils.TokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ApiOperation(value = "登录并返回uid,userName", notes = "无账号则创建新的账号并返回,response header带token")
    @PostMapping("/login")
    public ResponseEntity<ResultBean<UserForm>> login(@RequestBody UserLoginForm userLoginForm) {
        BeanUser beanUser = userService.findUser(userLoginForm);
        ResultBean<UserForm> rtVal = new ResultBean<>();
        HttpHeaders responseHeaders = new HttpHeaders();
        String token;
        if (beanUser == null) {
            rtVal.setData(userService.addUser(userLoginForm));
            rtVal.setMsg("创建成功");
            token = TokenUtil.sign(userLoginForm);
            responseHeaders.set("token", token);
        } else {
            if (!(beanUser.getPassword().equals(userLoginForm.getPassword()))) {
                rtVal.setMsg("密码错误");
                rtVal.setCode(StatusCode.FAIL.getCode());
            } else {
                rtVal.setData(UserForm.builder().uid(beanUser.getUid()).userName(beanUser.getUserName()).build());
                rtVal.setMsg("登录成功");
                token = TokenUtil.sign(userLoginForm);
                responseHeaders.set("x-token", token);
            }
        }
        List<String> header = new ArrayList<>();
        header.add("x-token");
        responseHeaders.setAccessControlExposeHeaders(header);

        return new ResponseEntity<>(rtVal, responseHeaders, HttpStatus.OK);
    }
}
