package cn.edu.zucc.ohmyquestionnaire.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class StatisticForm {
    private String title;
    @JsonAlias("answer_count")
    private Integer answerCount;
    private List<AnalysisForm> analysisFormList;
}
