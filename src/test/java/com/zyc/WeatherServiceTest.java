package com.zyc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyc.proto.WeatherGrpc;
import com.zyc.proto.WeatherReply;
import com.zyc.proto.WeatherRequest;
import com.zyc.thridparty.client.WeatherClient;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Map;

/**
 * @author zyc
 * @version 1.0
 */
@QuarkusTest
public class WeatherServiceTest {
    @Inject
    @RestClient
    WeatherClient weatherClient;

    @GrpcClient
    WeatherGrpc weatherGrpc;

    @Test
    void testWeatherClient() throws JsonProcessingException {
        Map weather = weatherClient.getWeather("110101", "0ef856e43f236d96231fc5acef9917b1");
        System.out.println(new ObjectMapper().writeValueAsString(weather));
    }

    @Test
    void testWeatherGrpc() {
        WeatherRequest weatherRequest = WeatherRequest.newBuilder()
            .setCity("110101")
            .setKey("0ef856e43f236d96231fc5acef9917b1").build();
        Uni<WeatherReply> weatherInfo =
            weatherGrpc.getWeatherInfo(weatherRequest);
        WeatherReply weatherReply = weatherInfo.await().atMost(Duration.ofSeconds(5));
        System.out.println(weatherReply);
    }
}
