import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.SwingUtilities;

public class APICall {
    private static final String apiKey = "ea9e4ca73b9bca23e33ed90b383481f8";
    private WeatherGUI gui;

    public APICall(WeatherGUI gui) {
        this.gui = gui;
        gui.addSearchButtonListener(new SearchButtonListener());
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String location = gui.getCity();
            String city = capitalizeFirstLetter(location);

            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey + "&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();

                    // Process the response data
                    String responseData = response.toString();

                    // Extract temperature
                    int temperatureIndex = responseData.indexOf("\"temp\":");
                    int temperatureEndIndex = responseData.indexOf(",", temperatureIndex);
                    String temperatureString = responseData.substring(temperatureIndex + 7, temperatureEndIndex);
                    double temperature = Double.parseDouble(temperatureString);

                    // Extract weather description
	                    int descriptionIndex = responseData.indexOf("\"description\":\"") + 15;
	                    int descriptionEndIndex = responseData.indexOf("\"", descriptionIndex);
	                    String weatherDescription = responseData.substring(descriptionIndex, descriptionEndIndex);
	                    String description = capitalizeFirstLetter(weatherDescription);

	                 // Extract humidity
	                    int humidityIndex = responseData.indexOf("\"humidity\":", descriptionEndIndex);
	                    int humidityEndIndex = Math.min(responseData.indexOf(",", humidityIndex), responseData.indexOf("}", humidityIndex));
	                    String humidityString = responseData.substring(humidityIndex + 11, humidityEndIndex);
	                    int humidity = Integer.parseInt(humidityString.trim());



                    // Extract country
                    int countryIndex = responseData.indexOf("\"country\":\"") + 11;
                    int countryEndIndex = responseData.indexOf("\"", countryIndex);
                    String country = responseData.substring(countryIndex, countryEndIndex);

                    // Display the retrieved information
                    String resultText = "City: " + city +
                            "\nCountry: " + country +
                            "\nTemperature: " + temperature + "°C" +
                            "\nWeather Description: " + description +
                            "\nHumidity: " + humidity + "%";

                    gui.setResultText(resultText);
                } else {
                    gui.setResultText("City does not exist or is not provided by the API.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                gui.setResultText("An error occurred while retrieving data.");
            }
        }
    }
    // Class for making the first letter of a word capital
    private static String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
    // Running the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WeatherGUI gui = new WeatherGUI();
                new APICall(gui);
                gui.setVisible(true);
            }
        });
    }
}
