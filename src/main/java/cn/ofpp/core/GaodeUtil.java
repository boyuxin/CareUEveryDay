package cn.ofpp.core;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.util.List;

import static cn.hutool.json.JSONUtil.parseObj;

/**
 * <a href="https://lbs.amap.com">使用的高德</a>
 * @author boyuxin
 * Date 2022-08-22
 */
public class GaodeUtil {
    // 自己的账号
    private static final String key = "359f9f0023393a4f16e14e5152ba28f1";

    private static final String GEO_API = "https://restapi.amap.com/v3/geocode/geo?key=%s&address=%s&city=%s";
    private static final String WEATHER_API = "https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%d&extensions=%s";

    public static Integer getAdcCode(String province, String city) {
        HttpResponse response = HttpUtil.createGet(String.format(GEO_API, key, province, city))
                .setConnectionTimeout(300000)
                .setReadTimeout(400000)
                .execute();
        return parseObj(parseObj(response.body()).getJSONArray("geocodes").get(0)).getInt("adcode");
    }

    public static WeatherInfo getNowWeatherInfo(Integer adcCode) {
        HttpResponse response = HttpUtil.createGet(String.format(WEATHER_API, key, adcCode, "base"))
                .setConnectionTimeout(300000)
                .setReadTimeout(400000)
                .execute();
        List<WeatherInfo> lives =
                parseObj(response.body()).getJSONArray("lives").toList(WeatherInfo.class);
        return lives.get(0);
    }

}
