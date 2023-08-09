
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class MyAmazingBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update: updates) {
            if (update.hasMessage() && update.getMessage().hasText()) {

                Huefication huefication = new Huefication(update.getMessage().getText());
                huefication.run();

                SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
                message.setChatId(update.getMessage().getChatId().toString());

                message.setText(huefication.getRespond());

                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
//        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {

        return getConfig().getProperty("RobotName");
    }

    @Override
    public String getBotToken() {
        return getConfig().getProperty("BotToken");
    }

    private Properties getConfig() {
        try (InputStream input = MyAmazingBot.class.getClassLoader().getResourceAsStream("keys.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find keys.properties\n" + "You must add resources\\keys.properties file with parameters: RobotName=yourname\n" +
                        "BotToken=yourtoken");
                return null;
            }
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}