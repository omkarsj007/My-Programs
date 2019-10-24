
import java.io.IOException;

import org.jibble.pircbot.*;

public class testMyBot {

	public static void main(String[] args) throws IOException, IrcException  {
		MyBot bot = new MyBot();
		bot.start();
	}
}
