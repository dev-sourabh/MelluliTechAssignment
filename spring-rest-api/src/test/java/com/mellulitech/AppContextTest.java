package com.mellulitech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AppContextTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testContextLoads() {
        assertNotNull("DataSource should be loaded", dataSource);
    }
}