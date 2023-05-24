package com.zyc.thridparty.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * 获取天气情况的客户端
 * @author zyc
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "https://restapi.amap.com")
@Path("/v3/weather")
public interface WeatherClient {
    /**
     * 通过@RegisterRestClient和@Path("/v3/weather")和@Path("/weatherInfo")共同决定了请求url为
     * <a href="https://restapi.amap.com/v3/weather/weatherInfo">https://restapi.amap.com/v3/weather/weatherInfo</a>
     * 的一个http的GET请求方法
     *
     * @param city 城市code，要么通过实时获取，要么去网上查对应的code
     * @param key 服务key，用于高德地图的api鉴权。可以去高德地图开放平台申请
     *            详见：<a href="https://developer.amap.com/api/webservice/guide/api/weatherinfo/">高德地图天气</a>
     * @return json数据的map，例如如下格式的map将被返回<br/>
     * {<br/>
     *     "status": "1",<br/>
     *     "count": "1",<br/>
     *     "info": "OK",<br/>
     *     "infocode": "10000",<br/>
     *     "lives": [{<br/>
     *             "province": "北京",<br/>
     *             "city": "东城区",<br/>
     *             "adcode": "110101",<br/>
     *             "weather": "晴",<br/>
     *             "temperature": "16",<br/>
     *             "winddirection": "西",<br/>
     *             "windpower": "≤3",<br/>
     *             "humidity": "20",<br/>
     *             "reporttime": "2023-04-12 10:39:02",<br/>
     *             "temperature_float": "16.0",<br/>
     *             "humidity_float": "20.0"<br/>
     *         }]<br/>
     * }<br/>
     */
    @GET
    @Path("/weatherInfo")
    Map getWeather(@QueryParam("city") String city, @QueryParam("key") String key);
}
