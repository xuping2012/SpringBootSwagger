package com.xp.test.boot.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import com.xp.test.base.Application;

@SpringBootTest(classes = Application.class)
public class BaseServer extends AbstractTestNGSpringContextTests {

}
