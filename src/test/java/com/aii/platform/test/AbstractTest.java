package com.aii.platform.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aii.platform.Application;
import com.aii.platform.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=AppConfig.class)
@ContextConfiguration(classes=AppConfig.class)
public class AbstractTest {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
