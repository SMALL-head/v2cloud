package com.zyc;

import com.zyc.service.IncrementInRedisService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

/**
 * @author zyc
 * @version 1.0
 */
@QuarkusTest
public class IncrementServiceTest {
    @Inject
    IncrementInRedisService incrementService;

    @Test
    public void testRedis() {
        incrementService.increment("key1", 1L);

    }
}
