import java.util.List;
import java.util.Map;

public interface WeatherService {
    Map<String, Object> getCurrentWeather(String location) throws Exception;
    List<Map<String, Object>> getWeatherForecast(String location) throws Exception;
}
