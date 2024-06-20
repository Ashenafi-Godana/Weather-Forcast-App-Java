import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class WeatherFetcher {
    private static final String API_KEY = "12cf45c81b8b0b9e4b38b41bd12afae3";
    private static final String API_URL_CURRENT = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_URL_FORECAST = "https://api.openweathermap.org/data/2.5/forecast";

    protected String getWeatherData(String location, boolean isCurrent) throws Exception {
        String apiUrl = (isCurrent ? API_URL_CURRENT : API_URL_FORECAST) + "?q=" + location + "&appid=" + API_KEY + "&units=metric";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return content.toString();
    }
}
