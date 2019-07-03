package com.heaven.elegy.common;

import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author lixiaoxi
 */
public class JarTest {

    @Test
    public void test01() throws IOException {
        JarFile jarFile = new JarFile("/home/lixiaoxi/桌面/api/test_jar.jar");
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            System.out.println(jarEntry.getAttributes());
        }
    }

    @Test
    public void test02() throws IOException {
        JarFile jarFile = new JarFile("/home/lixiaoxi/桌面/api/api.jar");
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            System.out.println(jarEntry.getName());
        }
    }
}
