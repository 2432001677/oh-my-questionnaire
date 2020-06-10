package cn.edu.zucc.ohmyquestionnaire.aop;

import cn.edu.zucc.ohmyquestionnaire.form.ResultBean;
import cn.edu.zucc.ohmyquestionnaire.form.ResultPageBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class ResponseAspect {
    @Pointcut("execution(* cn.edu.zucc.ohmyquestionnaire.controller.*.*(..))")
    public void responseData() {
    }

    @Around("responseData()")
    public Object aroundResponse(ProceedingJoinPoint pjp) {
        Object r;
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Object target = pjp.getTarget();
        Method method = null;
        try {
            method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            Object[] args = pjp.getArgs();
            r = pjp.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            assert method != null;
            return method.getReturnType().equals(ResultBean.class) ? new ResultBean<>(throwable) : new ResultPageBean<>(throwable);
        } finally {
            log.debug("最终");
        }
        return r;
    }
}
