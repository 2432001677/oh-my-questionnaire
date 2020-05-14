package cn.edu.zucc.ohmyquestionnaire;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.edu.zucc.ohmyquestionnaire.mysql.mapper")
@EnableMongoAuditing
@Slf4j
public class OhMyQuestionnaireApplication {

    public static void main(String[] args) {
        SpringApplication.run(OhMyQuestionnaireApplication.class, args);
    }

}
