package com.zyc.configuration;

import apollo.perception.PerceptionObstacleOuterClass;
import com.zyc.proto.basic_msgs.Header;
import com.zyc.proto.basic_msgs.Point3D;
import io.quarkus.runtime.Startup;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zyc
 * @version 1.0
 */
@Configuration
@Slf4j
public class MQTTBeans {
    /**
     * PerceptionObstacles发送帧序号
     */
    static int seq = 1;
    MqttClient mqttClient;

    @Bean
    @Startup // 该注解的作用是标记bean启动应用时就创建。默认是采用懒加载的模式
    public MqttClient mqttClient() throws MqttException {
        String broker = "tcp://s400cadc.cn-hangzhou.emqx.cloud:15597";
        String clientId = "test_client";
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName("root");
        connOpts.setPassword("123456".toCharArray());
        MqttClient client = null;
        try {
            client = new MqttClient(broker, clientId, persistence);
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected to broker: " + broker);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
        log.info("成功创建mqttClient");
        mqttClient = client;
        return client;
    }

    @Bean
    @Startup
    public ScheduledExecutorService scheduledExecutorService() {
        log.info("[MQTTBeans]-[scheduledExecutorService]-发送障碍物定时任务开启");
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::scheduledExecutorServiceTask,
            1000,
            800,
            TimeUnit.MILLISECONDS);
        return scheduledThreadPoolExecutor;
    }

    private void scheduledExecutorServiceTask() {
        log.info("[scheduledExecutorServiceTask]-触发定时发送任务");
        Header header = Header.newBuilder().setTimestampSec(System.currentTimeMillis()).setModuleName("server_module").setSequenceNum(seq++).build();
        Point3D XYZPosition = Point3D.newBuilder().setX(586843.965038527).setY(4140740.896292135).setZ(0.850000000).build();
        Point3D XYZPosition2 = Point3D.newBuilder().setX(586840.641613236).setY(4140738.237551900).setZ(1.0).build();

        Point3D velocity = Point3D.newBuilder().setX(0).setY(0).build();
        Point3D polygonPoint1 = Point3D.newBuilder().setX(586843.562546291).setY(4140741.477669809).build();
        Point3D polygonPoint2 = Point3D.newBuilder().setX(586844.546416201).setY(4140741.298784371).build();
        Point3D polygonPoint3 = Point3D.newBuilder().setX(586844.367530763).setY(4140740.314914461).build();
        Point3D polygonPoint4 = Point3D.newBuilder().setX(586843.383660853).setY(4140740.493799899).build();

        Point3D polygonPoint5 = Point3D.newBuilder().setX(586841.773434453).setY(4140739.957720709).build();
        Point3D polygonPoint6 = Point3D.newBuilder().setX(586841.504190639).setY(4140736.367803194).build();
        Point3D polygonPoint7 = Point3D.newBuilder().setX(586839.509792019).setY(4140736.517383091).build();
        Point3D polygonPoint8 = Point3D.newBuilder().setX(586839.779035833).setY(4140740.107300606).build();


        PerceptionObstacleOuterClass.PerceptionObstacle obstacle1 = PerceptionObstacleOuterClass.PerceptionObstacle.newBuilder()
            .setId(3613)
            .setPosition(XYZPosition)
            .setTheta(2.961739154)
            .setVelocity(velocity)
            .setLength(1.0)
            .setWidth(1.0)
            .setHeight(1.7)
            .setType(PerceptionObstacleOuterClass.PerceptionObstacle.Type.PEDESTRIAN)
            .setTimestamp(System.currentTimeMillis())
            .addPolygonPoint(polygonPoint1).addPolygonPoint(polygonPoint2)
            .addPolygonPoint(polygonPoint3).addPolygonPoint(polygonPoint4)
            .build();

        PerceptionObstacleOuterClass.PerceptionObstacle obstacle2 = PerceptionObstacleOuterClass.PerceptionObstacle.newBuilder()
            .setId(6791)
            .setPosition(XYZPosition2)
            .setTheta(1.495936479)
            .setVelocity(velocity)
            .setLength(3.6)
            .setWidth(2.0)
            .setHeight(2.0)
            .setType(PerceptionObstacleOuterClass.PerceptionObstacle.Type.UNKNOWN_UNMOVABLE)
            .setTimestamp(System.currentTimeMillis())
            .addPolygonPoint(polygonPoint5).addPolygonPoint(polygonPoint6)
            .addPolygonPoint(polygonPoint7).addPolygonPoint(polygonPoint8)
            .build();

        PerceptionObstacleOuterClass.PerceptionObstacles perceptionObstacles
            = PerceptionObstacleOuterClass.PerceptionObstacles.newBuilder()
            .setHeader(header)
            .addPerceptionObstacle(obstacle1)
            .addPerceptionObstacle(obstacle2)
            .build();
        try {
            mqttClient.publish("apollo/mqtt/perception_obstacles", perceptionObstacles.toByteArray(), 2, false);
        } catch (MqttException e) {
            String message = e.getMessage();
            int reasonCode = e.getReasonCode();
            log.warn("发送失败--errorCode:{}errorMsg:{}", reasonCode, message);
        }
    }

}
