import java.util.List;
import java.util.Map;

public class OpenWeatherMapService extends WeatherFetcher implements WeatherService {
    @Override
    public Map<String, Object> getCurrentWeather(String location) throws Exception {
        String data = getWeatherData(location, true);
        return WeatherParser.parseCurrentWeather(data);
    }

    @Override
    public List<Map<String, Object>> getWeatherForecast(String location) throws Exception {
        String data = getWeatherData(location, false);
        return WeatherParser.parseForecast(data);
    }
}
