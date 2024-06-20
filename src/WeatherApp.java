import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class WeatherApp extends JFrame {
    private JTextField locationField;
    private JLabel locationLabel;
    private JLabel tempLabel;
    private JLabel descriptionLabel;
    private JLabel[] forecastLabels;
    private WeatherService weatherService;
    private WeatherDisplay weatherDisplay;

    public WeatherApp() {
        weatherService = new OpenWeatherMapService();
        weatherDisplay = new WeatherDisplay();

        setTitle("Weather Forecast App");
        setSize(800, 600); // Increased the size for better readability
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        locationField = new JTextField();
        locationField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        JButton fetchButton = new JButton("Get Weather");
        fetchButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        fetchButton.addActionListener(this::fetchWeather);

        topPanel.add(locationField, BorderLayout.CENTER);
        topPanel.add(fetchButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        locationLabel = new JLabel("", SwingConstants.CENTER);
        locationLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Increased font size
        tempLabel = new JLabel("", SwingConstants.CENTER);
        tempLabel.setFont(new Font("Arial", Font.BOLD, 60)); // Increased font size
        descriptionLabel = new JLabel("", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size

        mainPanel.add(locationLabel);
        mainPanel.add(tempLabel);
        mainPanel.add(descriptionLabel);
        add(mainPanel, BorderLayout.CENTER);

        JPanel forecastPanel = new JPanel(new GridLayout(1, 5));
        forecastLabels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            forecastLabels[i] = new JLabel("", SwingConstants.CENTER);
            forecastLabels[i].setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
            forecastLabels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            forecastPanel.add(forecastLabels[i]);
        }
        add(forecastPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void fetchWeather(ActionEvent e) {
        String location = locationField.getText();
        new Thread(() -> {
            try {
                Map<String, Object> currentWeather = weatherService.getCurrentWeather(location);
                List<Map<String, Object>> forecast = weatherService.getWeatherForecast(location);

                SwingUtilities.invokeLater(() -> weatherDisplay.displayWeather(locationLabel, tempLabel, descriptionLabel, forecastLabels, currentWeather, forecast));
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Unable to fetch weather data.", "Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherApp::new);
    }
}
