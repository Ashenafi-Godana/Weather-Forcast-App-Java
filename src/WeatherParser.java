import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherParser {
    public static Map<String, Object> parseCurrentWeather(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        Map<String, Object> weatherData = new HashMap<>();
        weatherData.put("location", json.getString("name"));
        weatherData.put("temperature", json.getJSONObject("main").getDouble("temp"));
        weatherData.put("description", json.getJSONArray("weather").getJSONObject(0).getString("description"));
        return weatherData;
    }

    public static List<Map<String, Object>> parseForecast(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        List<Map<String, Object>> forecastList = new ArrayList<>();
        JSONArray list = json.getJSONArray("list");
        for (int i = 0; i < list.length(); i += 8) {
            JSONObject entry = list.getJSONObject(i);
            Map<String, Object> dailyForecast = new HashMap<>();
            dailyForecast.put("date", Instant.ofEpochSecond(entry.getInt("dt"))
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            dailyForecast.put("temperature", entry.getJSONObject("main").getDouble("temp"));
            dailyForecast.put("description", entry.getJSONArray("weather").getJSONObject(0).getString("description"));
            forecastList.add(dailyForecast);
        }
        return forecastList;
    }
}
