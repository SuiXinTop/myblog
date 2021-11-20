package com.spring.blog.common.advice;

import com.spring.blog.common.annotation.Log;
import com.spring.common.constant.Topic;
import com.spring.common.enmu.BusinessStatus;
import com.spring.common.entity.SysLog;
import com.spring.common.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xxx
 * @create 2021-10-16
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAdvice {
    private final HttpServletRequest request;
    private final RocketMQTemplate rocketMqTemplate;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog) {
        handleLog(joinPoint, controllerLog, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e) {
        try {
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            SysLog operLog = SysLog.builder()
                    .operIp(RequestUtil.getIpAddress(request))
                    .operUrl(request.getRequestURI())
                    .operStatus(BusinessStatus.SUCCESS.ordinal())
                    .methodName(className + "." + methodName + "()")
                    .businessType(controllerLog.businessType().ordinal())
                    .logName(controllerLog.logName())
                    .operType(controllerLog.operatorType().ordinal())
                    .requestMethod("")
                    .build();

            log.info("日志对象已加载");
            if (e != null) {
                operLog.setOperStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(e.getMessage());
            }

            // 保存数据库
           rocketMqTemplate.sendOneWay(Topic.SYS_OPER_LOG,operLog);
            log.info("日志消息已发送");
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }
}
