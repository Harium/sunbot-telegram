package examples;

import com.harium.suneidesis.chat.Parser;
import com.harium.suneidesis.chat.box.EchoBox;
import com.harium.suneidesis.chat.telegram.Telegram;

public class BasicExample {

    public static void main(String[] args) {
        // Use your own box
        Parser bot = new EchoBox();

        String token = "MY_TOKEN";

        Telegram telegram = new Telegram(token);
        telegram.init();
        telegram.addParser(bot);
    }

}
