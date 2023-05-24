package com.zyc;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

/**
 * @author zyc
 * @version 1.0
 */
@QuarkusTest
public class MqttTest {
    @Inject
    MqttClient mqttClient;

    @Test
    public void test1() {
        System.out.println(mqttClient);

    }
}
