package com.zyc.utils;

import java.util.Map;

/**
 * @author zyc
 * @version 1.0
 */
public class HttpRequestGenerator {
    /**
     * get请求类型的url生成
     * @param url 结尾不要带'/'
     * @param paramMap 参数
     * @return xxxx/yyyy?param1=value1&param2=value2
     */
    public static String generateHttp(String url, Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return url;
        }
        StringBuilder requestBuilder = new StringBuilder(url);
        if (url.charAt(url.length()-1) != '/') {
            requestBuilder.append('/');
        }
        for (String key : paramMap.keySet()) {
            requestBuilder.append(key).append("=").append(paramMap.get(key)).append('&');
        }
        requestBuilder.deleteCharAt(requestBuilder.length()-1); // 删除最后一个&
        return requestBuilder.toString();
    }
}
