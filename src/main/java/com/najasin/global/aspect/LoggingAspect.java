package com.najasin.global.aspect;

import static java.util.Objects.isNull;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger("API CALL LOGGER");

	@Pointcut("execution(* com.najasin.domain..*Controller.*(..)) || execution(* com.najasin.security.oauth.common.handler.*.*(..))")
	public void cut() {
	}

	@Before("cut()")
	public void beforeParameterLog(JoinPoint joinPoint) {
		Method method = getMethod(joinPoint);
		log.info("======= method name = {} =======", method.getName());

		Object[] args = joinPoint.getArgs();
		if (!isNull(args)) {
			for (Object arg : args) {
				log.info("parameter type = {}", arg.getClass().getSimpleName());
				log.info("parameter value = {}", arg);
			}
		}
	}

	@AfterReturning(value = "cut()", returning = "returnObj")
	public void afterReturnLog(JoinPoint joinPoint, Object returnObj) {
		Method method = getMethod(joinPoint);

		if (!isNull(returnObj)) {
			log.info("======= method name = {} =======", method.getName());
			log.info("return type = {}", returnObj.getClass().getSimpleName());
			log.info("return value = {}", returnObj);
		}
	}

	private Method getMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		return signature.getMethod();
	}
}

