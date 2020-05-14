package cn.edu.zucc.ohmyquestionnaire.mysql.bean;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@Slf4j  // 在bean中提供log变量
@Table(name = "user")
public class BeanUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid",nullable = false)
    private Integer uid;
    @Column(name = "user_name",nullable = false)
    private String userName;
    @Column(name = "password",nullable = false)
    private String password;
}
