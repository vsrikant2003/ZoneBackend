package com.zone.zissa.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class used to load test configuration file
 * 
 *
 */

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class ZissaApplicationTest {

    @Test
    public void contextLoads() {

        // No implementation

    }
}
