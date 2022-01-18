/*
 * This class establishes a connection with the openweathermap api. It passes the city/zipcode and extracts
 * the temperature and current weather from the information returned by the api. This information is then 
 * returned to the onMessage() function of the MyBot() class.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;


public class Weather {
	
	final double kelvin = 274.15;
	final int firstElement = 0;
	final int secondElement = 1;
	public String[] parsingForTemp(String s)
	{
		JsonObject obj = new JsonParser().parse(s).getAsJsonObject();
		JsonArray elements = obj.getAsJsonArray("weather");
		String[] weather = new String[2];
		for(int i = 0; i < elements.size(); i++) {
			weather[1] = elements.get(i).getAsJsonObject().get("description").getAsString();
		}
		 weather[0] = obj.getAsJsonObject("main").get("temp").getAsString(); 
		 
		return weather;
		
	}
	
	public double[] returnTemperature(double k) {
		double [] d = new double[2];
		
		double celsius = k - kelvin;
		double fahrenheit = (celsius * 1.8) + 32; // applying formula for celsius to fahrenheit
		celsius = Math.round(celsius * 1000) / 1000; // restricting decimal places to 3 places
		fahrenheit = Math.round(fahrenheit * 1000) / 1000;
		d[firstElement] = celsius;
		d[secondElement] = fahrenheit;
		return d;
		
	}

	public String getJsonInput(String city) throws IOException, Exception {
		URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=26aa1d90a24c98fad4beaac70ddbf274");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line = "";
		String whole = "";
		while((line = br.readLine()) != null)
		{
			whole += line;
		}
		return whole;
	}
}

