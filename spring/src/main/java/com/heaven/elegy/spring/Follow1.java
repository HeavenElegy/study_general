package com.heaven.elegy.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author lixiaoxi
 */
public class Follow1 {
    public static void main(String[] args) {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("application.xml"));
        MyBean myBean = beanFactory.getBean(MyBean.class);
        myBean.say();
    }
}
