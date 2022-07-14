# sunbot-telegram
Plugin to turn your Suneidesis Chatbot into a Telegram Bot

## How to get your token
- Create a chat with the @BotFather a select the option /newbot

- Pick a name
- Pick an username ending with _bot (*_bot)
- Done!

https://www.siteguarding.com/en/how-to-get-telegram-bot-api-token

## How to use it

```
    Parser bot = new EchoBox(); // Use your own parser

    String token = "123456:ABCDE123456";

    Telegram telegram = new Telegram(token);
    telegram.init();
    telegram.addParser(bot);
```