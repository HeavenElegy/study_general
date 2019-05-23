package com.heaven.elegy.study.agent.aspectJ;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面定义
 * @author li.xiaoxi
 * @date 2019/05/23 11:53
 */
@Aspect
public class AspectDefinition {

	/**
	 * 切入点
	 * TODO execution规则
	 */
	@Pointcut("execution(* com.heaven.elegy.study.agent.aspectJ.BusinessService.hello(..))")
	public void endpoint() {}

	@Before("endpoint()")
	public void before() {
		System.out.println("before...");
	}


	@After("endpoint()")
	public void after() {
		System.out.println("after...");
	}





}
