package com.jst.common.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {

	private static ApplicationContext context;

	public static Object getService(String serviceName) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return context.getBean(serviceName);
	}

	public static <T> T getService(String serviceName, Class<T> requiredType) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return context.getBean(serviceName, requiredType);
	}
}
