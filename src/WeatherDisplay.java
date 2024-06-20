import javax.swing.*;
import java.util.List;
import java.util.Map;

public class WeatherDisplay {
    public void displayWeather(JLabel locationLabel, JLabel tempLabel, JLabel descriptionLabel, JLabel[] forecastLabels,
                               Map<String, Object> currentWeather, List<Map<String, Object>> forecast) {
        locationLabel.setText(String.valueOf(currentWeather.get("location")));
        tempLabel.setText(String.format("%.1f°C", currentWeather.get("temperature")));
        descriptionLabel.setText(String.valueOf(currentWeather.get("description")));

        for (int i = 0; i < forecastLabels.length && i < forecast.size(); i++) {
            Map<String, Object> dailyForecast = forecast.get(i);
            forecastLabels[i].setText(String.format("<html>%s<br/>%.1f°C<br/>%s</html>",
                    dailyForecast.get("date"),
                    dailyForecast.get("temperature"),
                    dailyForecast.get("description")));
        }
    }
}
