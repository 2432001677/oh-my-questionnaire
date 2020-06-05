package cn.edu.zucc.ohmyquestionnaire.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Option {
    private String text;
    private Double selectedCount;
}
