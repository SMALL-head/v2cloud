package com.zyc.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 创建一个定时服务
 * @author zyc
 * @version 1.0
 */
@ApplicationScoped
public class MQTTService {
//    @Inject
//    MqttClient mqttClient;

    @Inject
    ScheduledExecutorService executorService;

}
