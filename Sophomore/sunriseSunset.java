/* This class establishes a connection with the SunriseSunset api. It passes the coordinates of the city
 * to the api and parses the time returned by the api. The time returned by the api is according to UTC so 
 * this timezone is adjusted to the local timezone and returned to the onMessage() function of the MyBot class
 * The coodinates and timezone values are recieved from the first api
 * 
 */
import java.io.BufferedReader;

import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.*;

public class sunriseSunset {
	
	final int localTimeZone = 5; // Dallas is 5 hours away from UTC
	final int firstElement = 0;
	final int secondElement = 1;
	final int thirdElement = 2;
	public double[] extractLatitudeLongitude(String line) {
		
		JsonObject obj = new JsonParser().parse(line).getAsJsonObject();
		String latitude = obj.getAsJsonObject("coord").get("lat").getAsString(); // from the coord object extract the latitude attribute
		String longitude = obj.getAsJsonObject("coord").get("lon").getAsString(); // from the coord object extract the longitude attribute
		String timeZone = obj.get("timezone").getAsString(); // extract the timezone parameter
		double [] coordinates = new double[thirdElement+1];
		coordinates[firstElement] = Double.parseDouble(latitude);
		coordinates[secondElement] = Double.parseDouble(longitude);
		coordinates[thirdElement] = Double.parseDouble(timeZone);
		return coordinates;
	}
	
	public String[] extractSunriseSunset(String line) {
		JsonObject obj = new JsonParser().parse(line).getAsJsonObject();
		String[] info = new String[thirdElement+1]; 
		
		// extracting attributes from the JSON object "results" and store them as a string
		info[firstElement] = obj.getAsJsonObject("results").get("sunrise").getAsString();
		info[secondElement] = obj.getAsJsonObject("results").get("sunset").getAsString();
		info[thirdElement] = obj.getAsJsonObject("results").get("day_length").getAsString();
		return info;
	}
	
	public String adjustTimeZone(String info, double adjustment) {        
        String d = info.substring(firstElement,info.indexOf(' '));
        int adjust = (int)adjustment;
        Calendar c = Calendar.getInstance();
        java.sql.Time time = java.sql.Time.valueOf(d); // create a time object 
        c.setTime(time);
        
        long m = c.getTimeInMillis();
        m += (adjust*1000); // adjust in in seconds, change to milliseconds
        c.setTimeInMillis(m); // set the time in milliseconds 

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); //setting the format for time
        java.util.Date date = c.getTime();
    	String x = dateFormat.format(date).toString(); // formatting the date to the defined format and storing in String
	    return x;
	    }
	
	
	public String getLocalTime(double adjustment) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, (int)(adjustment/3600) + localTimeZone); // Dallas is five hours behind UTC
		adjustment %= 3600;
		c.add(Calendar.MINUTE, (int)(adjustment/60));
		adjustment %= 60;
		c.add(Calendar.SECOND, (int)(adjustment));
		String hour = c.get(Calendar.HOUR_OF_DAY) + "";
		
		// Assigning a zero before a single digit to make sure the format hh:mm:ss is intact
        String minute = (c.get(Calendar.MINUTE) < 10) ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE) + "";
        String second = (c.get(Calendar.SECOND) < 10) ? "0" + c.get(Calendar.SECOND) : c.get(Calendar.SECOND) + "";
        String amPm = (c.get(Calendar.AM_PM) == 1)? " PM" : " AM";
        hour = (c.get(Calendar.HOUR) < 10) ? "0" + c.get(Calendar.HOUR) : c.get(Calendar.HOUR) + "";
        String x = hour + ":" + minute + ":" + second + amPm;
		return x;
		
	}
		

	public String getInfoFromAPI(double [] coordinates) {
		
		try {
		URL url = new URL("https://api.sunrise-sunset.org/json?lat=" + coordinates[0] + "&lng=" + coordinates[1]);
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
		catch(IOException e) {
			
		}
		return "";
		
	}

	
}
