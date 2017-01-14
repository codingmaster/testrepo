package com.springer.core;

import com.springer.core.config.TestConfiguration;
import com.springer.core.config.TestDataSetup;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@ContextConfiguration(classes = { TestConfiguration.class, TestDataSetup.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class BaseTest
{
	@Autowired
	protected TestDataSetup testDataSetup;
}
