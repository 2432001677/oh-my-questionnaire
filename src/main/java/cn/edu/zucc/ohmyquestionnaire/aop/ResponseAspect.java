package cn.edu.zucc.ohmyquestionnaire.aop;

import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseAspect {
    @Pointcut("execution(* cn.edu.zucc.ohmyquestionnaire.controller.*.*(..))")
    public void responseData() {
    }

    @Around("responseData()")
    public Object aroundResponse(ProceedingJoinPoint pjp) {
        Object retValue = null;
        ResultBean r = null;
        try {
            System.out.println("前置");
            Object[] args = pjp.getArgs();
            retValue = pjp.proceed(args);
            System.out.println("后置");
            r = (ResultBean) retValue;
        } catch (Throwable throwable) {
            r.setCode(ResultBean.FAIL);
            throwable.printStackTrace();
        } finally {
            System.out.println("最终");
        }
        return retValue;
    }
}
