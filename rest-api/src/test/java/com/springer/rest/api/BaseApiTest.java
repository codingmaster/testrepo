package com.springer.rest.api;

import com.springer.rest.WatermarkApplication;
import com.springer.rest.config.TestDataSetup;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WatermarkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = { TestDataSetup.class })

public abstract class BaseApiTest
{
	@Autowired
	TestDataSetup testDataSetup;
	
	@Value("${local.server.port}")
	protected int port;
}
