package com.zyc.service.mqtt;

import com.zyc.proto.MQTTData;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author zyc
 * @version 1.0
 */
@Slf4j
public class SampleCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        byte[] payload = message.getPayload();
        MQTTData mqttData = MQTTData.parseFrom(payload);

        String data = mqttData.getData();
        log.info("[messageArrived]-接收到来自topic={}消息, data = {}", topic , data);
        System.out.printf("receive from topic:%s, data=%s\n", topic, data);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
