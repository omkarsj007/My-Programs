/* This class controls the conversation with the irc freenode interface
 * As the user asks for weather/time, the methods of this class access methods
 * of other classes to provide the information asked for. This class returns the values
 * asked for and handles any incorrect inputs provided.
 * *
 */
import java.io.IOException;
import org.jibble.pircbot.*;
public class MyBot extends PircBot {
	
	int key1 = -1; // this variable is used to determine which api to call
	
	public MyBot()  {
		this.setName("omkarsj007");
		
	}
	
	public void start()throws IOException, IrcException {
		setVerbose(true);
		connect("irc.freenode.net");
		joinChannel("#omkarsj_007");
		sendMessage("#omkarsj_007", "Hello :-D I am a Time and Weather bot. How can I help you?");
	}
	

	public void onMessage(String channel, String sender, String login, String hostname, String message){
		if(message.contains("weather") || message.contains("Weather")){
			sendMessage(channel, "No problem :D ");
			sendMessage(channel, "Enter the city or zipcode you want the weather for.");
			key1 = 0;
		}
		else if(message.contains("sunset") || message.contains("sunrise") || 
				message.contains("duration") || message.contains("Time")||message.contains("time"))
		{
			sendMessage(channel, "Sure :D ");
			sendMessage(channel, "What city or zipcode would you like to check?");
			key1 = 1;
		}
		else if(message.contains("Bye") || message.contains("bye")) {
			sendMessage(channel, "See ya");
			System.exit(0);
		}
		else
		{
			Weather w = new Weather();
			try {
				
				if(key1 == 0) { // This block is executed if the user asks for weather
					String s = w.getJsonInput(message);
					String[] weather = w.parsingForTemp(s);
					double[] d = w.returnTemperature(Double.parseDouble(weather[0]));
					sendMessage(channel, " The temperature for " + message + " is " + d[0] + " C or " + d[1] + " F");
					sendMessage(channel, "Current Weather: " + weather[1]);
					key1 = -1;
				}
				/* The second api requires the coordinates of a city to return the time for sunset, sunrise, and duration.
				 * The second api returns the times according to UTC. So i extract the coordinates and timezone from the first api
				 * Then I pass the coordinates to the second API. I extract the times returned by the second API and adjust the time using 
				 * the timezone value
				 *  */
				
				else if(key1 == 1) { // This block is executed if the user asks for time/sunset/sunrise/duration
					String s = w.getJsonInput(message);
					sunriseSunset o = new sunriseSunset(); 
					double[] llt = o.extractLatitudeLongitude(s); // the array stores, latitude, longitude, timezone
					String[] info = o.extractSunriseSunset(o.getInfoFromAPI(llt));
					info[0] = o.adjustTimeZone(info[0], llt[2]); // sunrise time
					info[1] = o.adjustTimeZone(info[1], llt[2]); // sunset time
					String localTime = o.getLocalTime(llt[2]); // local time 
					sendMessage(channel, "The Local Time is: " + localTime);
					sendMessage(channel, "The Time for Sunrise is: " + info[0] + " AM");
					sendMessage(channel, "The Time for Sunset is: " + info[1] + " PM");
					sendMessage(channel, "The Duration for the day is: " + info[2]);
					sendMessage(channel, "Have a great day!");
					key1 = -1;
				}
				else {
					sendMessage(channel, "Sorry I can't help you with that. I am a Time and Weather Bot. Please try again ");
					sendMessage(channel, "Try keywords like ;) : time, weather, sunrise, sunset, duration");
				}
				
			} catch (IOException e) {
				sendMessage(channel, "Please enter a valid city");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
