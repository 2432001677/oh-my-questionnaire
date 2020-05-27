package cn.edu.zucc.ohmyquestionnaire.interceptor;

import cn.edu.zucc.ohmyquestionnaire.enums.StatusCode;
import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("x-token");
        if (token != null) {
            log.debug(token);
            boolean result = TokenUtil.verify(token);
            if (result) {
                log.debug("passed login interceptor");
                return true;
            }
        }
        response.setContentType("application/json; charset=utf-8");
        try {
            ResultBean<?> resultBean = new ResultBean<>();
            resultBean.setMsg("未登录");
            resultBean.setCode(StatusCode.NO_LOGIN.getCode());
            response.getWriter().append(new ObjectMapper().writeValueAsString(resultBean));
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(500);
            return false;
        }

        return false;
    }
}
