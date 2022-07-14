package com.harium.suneidesis.chat.telegram;

import com.harium.suneidesis.chat.box.BaseChatBox;
import com.harium.suneidesis.chat.input.InputContext;
import com.harium.suneidesis.chat.output.Output;
import com.harium.suneidesis.chat.output.OutputContext;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.IOException;
import java.util.List;

public class Telegram  extends BaseChatBox {

    private final String token;

    private TelegramBot app;

    public Telegram(String token) {
        this.token = token;
    }

    @Override
    public void init() {
        app = new TelegramBot(token);
        // Register for updates
        app.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update : list) {
                    InputContext inputContext = buildContext(update);
                    if (output == null) {
                        Output output = new TelegramOutput(update);
                        parse(inputContext, output);
                    } else {
                        parse(inputContext, output);
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    private InputContext buildContext(Update update) {
        InputContext context = new InputContext();
        context.setSentence(update.message().text());

        // User
        User user = update.message().from();
        context.getProperties().put(InputContext.USER_ID, user.id());
        context.getProperties().put(InputContext.USER_USERNAME, user.username());
        context.getProperties().put(InputContext.USER_NAME, user.firstName()+" "+user.lastName());

        // Message
        Chat chat = update.message().chat();
        String chatId = Long.toString(chat.id());
        // Chat / Group
        context.getProperties().put(InputContext.CHANNEL_ID, chatId);
        context.getProperties().put(InputContext.CHANNEL_NAME, chat.title());

        return context;
    }

    /**
     * @param chatId - chat id
     * @param message - the text to be sent in the channel
     */
    @Override
    public void sendMessage(String chatId, String message) {
        Long id = Long.parseLong(chatId);

        SendResponse response = app.execute(new SendMessage(id, message));
    }

    public TelegramBot getApp() {
        return app;
    }

    private class TelegramOutput implements Output {

        private final String chatId;

        public TelegramOutput(Update update) {
            chatId = Long.toString(update.message().chat().id());
        }

        @Override
        public void print(String sentence) {
            sendMessage(chatId, sentence);
        }

        @Override
        public void print(String sentence, OutputContext context) {
            sendMessage(chatId, sentence);
        }

        @Override
        public void produceFile(String path, String description) {
            // The call changes depending on the file
        }

        @Override
        public void produceFile(byte[] data, String description) {
            // The call changes depending on the file
        }
    }
}
