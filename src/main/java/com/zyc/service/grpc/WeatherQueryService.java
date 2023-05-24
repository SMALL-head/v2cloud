package com.zyc.service.grpc;

import com.zyc.common.enums.ExceptionEnum;
import com.zyc.common.exception.BizException;
import com.zyc.proto.WeatherGrpc;
import com.zyc.proto.WeatherReply;
import com.zyc.proto.WeatherRequest;
import com.zyc.thridparty.client.WeatherClient;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * 提供天气情况的grpc服务
 *
 * @author zyc
 * @version 1.0
 */
@GrpcService
public class WeatherQueryService implements WeatherGrpc {
    @Inject
    @RestClient
    WeatherClient weatherClient;

    @Override
    public Uni<WeatherReply> getWeatherInfo(WeatherRequest request) {
        // request不会为空，因此不需要校验
        String city = request.getCity();
        String key = request.getKey();
        if (ObjectUtils.isEmpty(city) || ObjectUtils.isEmpty(key)) {
            throw new BizException("[weatherClient.getWeather]request中city或key字段为空", ExceptionEnum.REQUEST_UNCORRECT);
        }
        Map result = weatherClient.getWeather(city, key);
        if (result == null || !StringUtils.equals(result.get("infocode").toString(), "10000")) {
            // 请求结果异常
            throw new BizException("[weatherClient.getWeather]请求失败", ExceptionEnum.HTTP_CALL_EXCEPTION);
        }
        List lives = (List) result.get("lives");
        if (CollectionUtils.isEmpty(lives)) {
            throw new BizException("[weatherClient.getWeather]返回结果缺少\"lives\"字段", ExceptionEnum.RESULT_UNCORRECT);
        }
        Map live = (Map) lives.get(0);
        // 将result中的live信息转化为WeatherReply
        return Uni.createFrom().item(live)
            .map(this::createFromMap);

    }

    private WeatherReply createFromMap(Map info) {
        return WeatherReply.newBuilder()
            .setProvince(info.get("province").toString())
            .setCity(info.get("city").toString())
            .setWeather(info.get("weather").toString())
            .setTemperature(info.get("temperature").toString())
            .setWinddirection(info.get("winddirection").toString())
            .setWindpower(info.get("windpower").toString())
            .setHumidity(info.get("humidity").toString())
            .setReporttime(info.get("reporttime").toString())
            .setTemperatureFloat(info.get("temperature_float").toString())
            .setHumidityFloat(info.get("humidity_float").toString())
            .build();

    }
}
