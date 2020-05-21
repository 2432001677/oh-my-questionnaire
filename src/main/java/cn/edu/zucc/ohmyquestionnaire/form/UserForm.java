package cn.edu.zucc.ohmyquestionnaire.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserForm {
    private Integer uid;
    private String userName;
}
