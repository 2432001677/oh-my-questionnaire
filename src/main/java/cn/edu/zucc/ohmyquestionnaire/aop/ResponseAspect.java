package cn.edu.zucc.ohmyquestionnaire.aop;

import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ResponseAspect {
    @Pointcut("execution(* cn.edu.zucc.ohmyquestionnaire.controller.*.*(..))")
    public void responseData() {
    }

    @Around("responseData()")
    public Object aroundResponse(ProceedingJoinPoint pjp) {
        ResultBean<?> r;
        try {
            log.debug("前置");
            Object[] args = pjp.getArgs();
            r= (ResultBean<?>) pjp.proceed(args);
            log.debug("后置");
        } catch (Throwable throwable) {
            return new ResultBean<>(throwable);
        } finally {
            log.debug("最终");
        }
        return r;
    }
}
