package com.xp.test.boot.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.xp.test.base.Application;

/**
 * 测试springboot框架基类
 * 
 * @author qguan
 *
 */
@SpringBootTest(classes = Application.class)
public class BaseServer extends AbstractTestNGSpringContextTests {

}
