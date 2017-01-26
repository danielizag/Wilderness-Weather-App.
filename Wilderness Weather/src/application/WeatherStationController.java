package application;

import java.awt.Desktop.Action;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class WeatherStationController {
	
	@FXML private Label currentLabel;
	@FXML private Label label;
	@FXML private Button forecastButton;
	@FXML private Button historicalWeatherButton;
	@FXML private TextField myTextField;
	@FXML private ImageView myImageView;
	
	private static final int HISTORICAL_DAYS = 5; // Number of days back for historical weather
	private List<Weather> weatherList = new ArrayList<>(); // List to store weather objects
	
	// Test action listeners
	@FXML
	void onForecastButtonClick(ActionEvent event){
		getCurrentWeather();
		getForecast();
	}
	
	@FXML
	void onHistoricalWeatherClick(ActionEvent event){
		getHistoricalWeather();
	}
	
	// Test methods
	private void getCurrentWeather(){
		
		String location = myTextField.getText(); // Get user input from text field
		
		if(isValidLocation(location)){ // Validates user input
			
			// Populate weatherList with appropriate data
			weatherList = WeatherService.getCurrentWeather(location);
			
			// Setting the text of a label
			currentLabel.setText("Todays Current Weather: " + weatherList.get(0).currentTemp);
			
			// Setting an image
			Image icon = makeImage(weatherList.get(0).iconURL);
			myImageView.setImage(icon);
		}
		else{
			label.setText("Invalid Entry");
		}
	}

	private void getHistoricalWeather(){
		
		String location = myTextField.getText();
		
		if(isValidLocation(location)){
		
			weatherList = WeatherService.getHistoricalWeather(HISTORICAL_DAYS, myTextField.getText());
			
			String historicalInfo = "Past " + HISTORICAL_DAYS + " days:\n";
			
			for(int i = 0; i < weatherList.size(); i++){
				historicalInfo += weatherList.get(i).date +
						" " + weatherList.get(i).description + "\n";
			}
			label.setWrapText(true);
			label.setText(historicalInfo);
		}
		else{
			label.setText("Invalid Entry");
		}
	}
	
	private void getForecast(){
		
		String location = myTextField.getText();
		
		if(isValidLocation(location)){
		
			weatherList = WeatherService.getForecast(myTextField.getText());
			
			String forecastInfo = "Five Day Forecast:\n";
			
			for(int i = 0; i < 5; i++){
				forecastInfo += weatherList.get(i).date + 
						": " + weatherList.get(i).maxTemp + 
						" " + weatherList.get(i).description + "\n";
			}
			label.setWrapText(true);
			label.setText(forecastInfo);
			}
		else{
			label.setText("Invalid Entry");
		}
	}
	
	// Method to simplify making image icons
	private Image makeImage(String imageURL){
		
		BufferedImage bufferedImage = null;
		Image image = null;
		URL iconURL = null;
		
		try {
			iconURL = new URL(imageURL);
			bufferedImage = ImageIO.read(iconURL);
			image = SwingFXUtils.toFXImage(bufferedImage, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return image;		
	}
	// Method to validate user input
	private boolean isValidLocation(String location) {
		String cityRegex = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$"; 
		String zipRegex = "^[0-9]{5}$"; 
		
		boolean validCity = Pattern.matches(cityRegex, location);
		boolean validZipcode = Pattern.matches(zipRegex, location);
		
		if(validCity || validZipcode){
			return true;
		}

		return false;
	}

}
