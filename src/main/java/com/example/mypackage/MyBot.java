package com.example.mypackage;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.getProperty;


public class MyBot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            MyBot bot = new MyBot();
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage lastSendMessage;
    boolean isMenuButtonClicked = false;
    boolean isDictionaryClicked = false;
    boolean isRestartClicked = false;


    private static final String BOT_USERNAME = "polish_memo_card_bot";
    private static final String BOT_TOKEN = "6234850632:AAFz1jWgT5X0wrC5j5zd0_uKSYBtGs1mSJU";
    private GameState gameState;
    private boolean isTestClicked = false;
    private Map<Long, GameState> gameStateMap = new HashMap<>();
    private Map<Long, String> chatWordSets = new HashMap<>();
    Map<Long, String> dictionaryMap = new HashMap<Long, String>();
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            int messageId = callbackQuery.getMessage().getMessageId();
            long chatId = callbackQuery.getMessage().getChatId();
            long userId = callbackQuery.getFrom().getId();
            User user = new User();
            String username = user.getUserName();

            gameState = gameStateMap.get(userId); // получаем состояние игры для данного чата
            if (gameState == null) {
                gameState = new GameState(chatId, "set1"); // если состояние игры не создано для данного пользователя, создаем новое состояние
                gameStateMap.put(userId, gameState); // добавляем созданное состояние в карту
                setTimerForPlayer(userId);
                System.out.println(gameStateMap);
                sendMessage(887447806, "Пользователь: " + username + " воспользовался ботом. \n" +
                                            "Индентификатор чата - " + userId);
            }

            if (data.equals("Игра") || data.equals("Повторить") && !gameState.isGameStarted) {
                if (!gameState.isGameStarted) {
                    gameState.setGameStartedById(chatId);
                    gameState.setGameStarted(true);
                    createGameButtons(chatId, "Добро пожаловать в Memo Card раздел. \n\n" +
                            "ПОЖАЛУЙСТА, прежде чем начать проверку знания слов, ознакомьтесь с темой в разделе Słownik в главном меню! \n\n" +
                            "Переводы принимаются как на украинском, так и на русском. \n\n" +
                            "/res - чтобы перезапустить игру; \n\n" +
                            "/stop - чтобы остановить игру и вернуться в меню \n\n" +
                            "/left - чтобы посмотреть, сколько слов ещё осталось \n\n" +
                            "Выберите тему для проверки слов: ");
                    deleteMessage(chatId, messageId);
                } else {
                    sendMessage(chatId, "Упс. Кажется эта кнопка уже сработала, попробуйте /start");
                }
            }
            else if (data.equals("Квартира")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set1");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Mieszkanie (Квартира)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                sendMessageWithMarkdown(chatId, "*Текста и аудио на эту тему можно найти тут:* \n" +
                        "[Text1](https://t.me/c/1613707957/7)\n" +
                        "[Text2](https://t.me/c/1613707957/34)\n" +
                        "[Audio1](https://t.me/c/1613707957/19)");
            deleteMessage(chatId,messageId);

            }
            else if (data.equals("Жизнь")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set2");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Rutyna (Повседневная жизнь)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                sendMessageWithMarkdown(chatId, "*Текста и аудио на эту тему можно найти тут:* \n" +
                        "[Text1](https://t.me/c/1613707957/52)\n" +
                        "[Text2](https://t.me/c/1613707957/65)\n" +
                        "[Text3](https://t.me/c/1613707957/69)");
                deleteMessage(chatId,messageId);

            }
            else if (data.equals("ТЦ")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set3");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Galeria Handlowa (Торговый центр)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                deleteMessage(chatId,messageId);
            }
            else if (data.equals("Природа")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set4");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Przyroda (Природа)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                deleteMessage(chatId,messageId);
            }
            else if (data.equals("Глаголы")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set5");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Czasowniki (Глаголы)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                deleteMessage(chatId,messageId);
            }
            else if (data.equals("Прилагательные")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set6");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Przymiotniki (Прилагательные)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                deleteMessage(chatId,messageId);
            }
            else if (data.equals("Проверка")) {
                gameState.setGameStarted(true);
                gameState.setDictionary("set7");
                gameState.updateWordSet(chatId, gameState.getDictionary());
                sendMessage(chatId, "Вы выбрали категорию \"Sprawdź swój poziom wiedzy (проверь свой уровень знаний)\" \n\n" +
                        "Нажмите, чтобы начать игру -> /startgame \n\n" +
                        "Нажмите, чтобы вернуться в меню -> /back \n\n" +
                        "Всего слов на проверку по этой теме - " + gameState.getWORDS().size());
                sendMessageWithMarkdown(chatId, "*ВНИМАНИЕ!* Этот тест *не может* гарантировать ваш реальный " +
                        "уровень знаний в польском языке, поскольку для точного определения уровня нужно проверить так же :" +
                        "*грамматику, письмо, говорение*. \n\n" +
                        "Для точного определения уровня напишите cюда -> \n" + "[Ваш польский порадник](https://t.me/ca5ual)");
            }
            else if (data.equals("Словарь") && !gameState.isBookClicked && !gameState.isGameStarted) {
                gameState.setGameStarted(false);
                gameState.setBookClicked(true);
                sendMessageWithMarkdown(chatId,
                        "*Добро пожаловать в словарь!* \n\n ");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Memo_Słownik_Mieszkanie.pdf", "Słownik \"Mieszkanie\" (квартира) \uD83C\uDFDA");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Memo_Słownik_Rutyna.pdf", "Słownik \"Rutyna\" (повседневная жизнь) \uD83D\uDEB6\uD83C\uDFFC");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Memo_Słownik_Galeria_Handlowa.pdf", "Słownik \"Galeria Handlowa\" (торговый центр) \uD83C\uDFEF");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Memo_Słownik_Przyroda.pdf", "Słownik \"Przyroda\" (природа) \uD83C\uDFD5");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Memo_Słownik_Czasownik.pdf", "Słownik \"Сzasownik\" (глагол) + примеры предложений \uD83D\uDCD7");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Memo_Słownik_Przymiotnik.pdf", "Słownik \"Przymiotnik\" (прилагательное) + примеры предложений \uD83D\uDCD8");
                sendMessageMenu(chatId,
                        "Если у вас есть какие-либо предложения по усовершенствованию словаря, пожалуйста, " +
                                "пишите на телеграм @ca5ual_off \n\n" +
                                "А пока, если вы закончили свои дела, то предлагаю вернуться В меню");
                deleteMessage(chatId, messageId);
            }
            else if (data.equals("Menu") && !gameState.isGameStarted) {
                gameState.setGameStarted(false);
                gameState.setBookClicked(false);
                gameState.setDictionaryClicked(false);
                gameState.setTestClicked(false);
                gameState.setCoursesClicked(false);
                sendMessageWithButtons(chatId, "Вы перешли в меню!\n" +
                                                    "Выберите одну из опций: ");
                deleteMessage(chatId, messageId);
            } else if (data.equals("yes")) {
                if (lastSendMessage != null && lastSendMessage.getText().contains("Вы хотите перезагрузить игру?")) {
                    endGame(chatId);
                    sendMessage(chatId, "Вы перезапустили игру.");
                    gameState.setGameStarted(true);
                    sendNavigationButtons(chatId);
                    sendNextWord(chatId);
                    deleteMessage(chatId, messageId);
                }

            }
            else if (data.equals("no")) {
                if (lastSendMessage != null && lastSendMessage.getReplyMarkup() != null) {
                    deleteMessage(chatId, messageId);
                } else {
                    sendMessage(chatId, "Кнопки уже удалены.");
                }
            }
            else if (data.equals("Тесты") && !gameState.isTestClicked && !gameState.isGameStarted) {
                createInlineButtons(chatId,
                        "Добро пожаловать в раздел с тестами! \n " +
                                "Тесты идут сразу вместе с аудированием и ответами. \n\n" +
                                "\uD83D\uDD0A - послушай аудио \n" +
                                "‼️- ответь на тест \n" +
                                "\uD83D\uDC40 - посмотри ответ \n\n" +
                                "В некоторых тестах отствуют аудирования," +
                                "поскольку они очень объемны.");
                gameState.setGameStarted(false);
                gameState.setTestClicked(true);
                deleteMessage(chatId, messageId);
            }
            else if (data.equals("Test1")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST A2*, _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/A2_przyklad.mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/t_a2.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/tr_klucz_a2.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test2")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B1 (1)* , _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/Egzamin-Przykładowy-B1-gru2016 (1).mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/5_B1_test.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/5_B1_tr-klucz.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test3")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B1 (2) BEZ AUDIO*");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/B1_test.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/src/main/resources/CorrectFiles/B1_przykladowy_transkrypcje_i_klucz_2020_03.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test4")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B1 (3) BEZ AUDIO*");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/B1_przykladowy_test_2020_03.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/B1_przykladowy_transkrypcje_i_klucz_2020_03.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test5")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B2 (1)*, _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/Egzamin-przykładowy-B2-listopad-2016-poprawiony.mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/5_B2_test.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/5_B2_tr-klucz.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test6")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B2 (2) BEZ AUDIO*");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/B2_test.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/B2_klucz.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test7")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B2 (3) BEZ AUDIO*");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/B2_przykladowy_test_03_2020.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/B2_przykladowy_transkrypcje-i-klucz_03_2020.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test8")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST C1 (1)*, _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/egzaminy_C1__test_calosc.mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/5_C1_test.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/5_C1_tr-klucz.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);

            }
            else if (data.equals("Test9")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST C1 (2)*, _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/C1_nagrania_-marzec-2020.mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/C1_test.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/C1_klucz.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test10")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST C2 BEZ AUDIO*");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/arkusz_c2_2.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/trans_c2.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test11")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B1 DLA DZIECI ORAZ MŁODZIEŻY*, _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/Egzaminy-kwiecień-PL-B1-młodzież-PRZYKŁADOWY.mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/B1_DM_test-przykladowy.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/B1_DM_TRANS-KLUCZ_test-przykladowy.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Test12")) {
                sendMessageWithMarkdown(chatId, "Вы выбрали *TEST B2 DLA DZIECI ORAZ MŁODZIEŻY*, _аудиофайл загружается..._");
                sendAudio(chatId, "/root/bot/bot_memo_polish/target/classes/Audio/Egzaminy-B2DM-przykładowy-24_04_20181.mp3", "\uD83D\uDD0A AUDIO");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/B2_DiM-test-przykładowy_-scalony-27.04-do-publikacji1.pdf", "‼️TEST");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/CorrectFiles/B2_DiM_TRANS_KLUCZ-test-przykładowy-do-publikacji1.pdf", "\uD83D\uDC40 ODPOWIEDZI");
                sendMessageWithTestButton(chatId, "Нажмите на кнопку ниже, чтобы \n высветить весь список снова!");
                gameState.setTestClicked(false);
            }
            else if (data.equals("Delete")) {
                gameState.setGameStarted(false);
                gameState.setBookClicked(false);
                gameState.setDictionaryClicked(false);
                gameState.setCoursesClicked(false);
                gameState.setTestClicked(false);
                deleteMessage(chatId, messageId);
                sendMessageWithButtons(chatId, "Вы перешли в меню! \n" +
                                                    "Выберите одну из опций: ");
            }
            else if (data.equals("Книги") && !gameState.isBookClicked && !gameState.isGameStarted) {
                sendMessageFreeNot(chatId, "Добро пожаловать в книжный отдел. \n\n" +
                        "Тут вы сможете посмотреть для себя книги для изучения польского языка. \n" +
                        "Есть как бесплатные, так и платные варианты. Какие вас интересуют?");
                gameState.setBookClicked(true);
                deleteMessage(chatId, messageId);
            }
            else if (data.equals("not")) {
                sendMessageWithMarkdown(chatId, "*Платные книги* (_предосмотр_):");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Iwona_Stempek_ksiazka.pdf", "Предосмотр книги Iwona Stempek (Krok po Kroku) (A1)");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Iwona_Stempek_zeszyt.pdf", "Предосмотр тетради Iwona Stempek (Krok po Kroku) (A1)");
                sendMessageWithMarkdown(chatId, "Можно приобрестити в" + " [Empik](https://www.empik.com/polski-krok-po-kroku-podrecznik-cd-opracowanie-zbiorowe,p1048154418,ksiazka-p?mpShopId=0&cq_src=google_ads&cq_cmp=19584714442&cq_term=&cq_plac=&cq_net=x&cq_plt=gp&gclid=Cj0KCQjw2v-gBhC1ARIsAOQdKY0STVcxiHuYlv-I655npQcAPkzUgUKI7Zx3V-z6k51P-2-pld7a-6caAoHSEALw_wcB&gclsrc=aw.ds)");
                sendPhoto(chatId, "/root/bot/bot_memo_polish/target/classes/photo/np-3d-komplet-pl-www (1).jpg", "Polski. Bez problemu!");
                sendMessageWithMarkdown(chatId, "Можно приобрестити на" + " [SuperMemo](https://www.supermemo.pl/polski-no-problem)");
                sendMessageMenu(chatId, "Если хотите поделиться материалом с другими, пожалуйста, свяжитесь со мной -> @ca5ual_off");
                deleteMessage(chatId, messageId);
            }
            else if (data.equals("free")) {
                sendMessageWithMarkdown(chatId, "*Беслатные книги* (_файлы загружаются, пожалуйста, подождите_):");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Hurra 1 (A1).pdf", "Книга по польскому языку (Hurra 1). \n" +
                        "Для получения полной версии напишите сюда -> @ca5ual_off");
                sendDocument(chatId, "/root/bot/bot_memo_polish/target/classes/Documents/Hurra 2 (A2).pdf", "Книга по польскому языку (Hurra 2)");
                sendMessageMenu(chatId, "Если хотите поделиться материалом с другими, пожалуйста, свяжитесь со мной -> @ca5ual_off");
                deleteMessage(chatId, messageId);
            }
            else if (data.equals("Курсы") && !gameState.isCoursesClicked && !gameState.isGameStarted) {
                sendMessageWithMarkdown(chatId, "Вы перешли в раздел *записи на куры полького языка*. " + "\n" +
                        "Я дам вам всю информацию, *которой владею*." + "\n\n" +
                        "_Выберите_, что бы вы хотели узнать! \n\n" +
                        "*/cena* - цена на групповые занятия и индивидуальные занятия \n" +
                        "*/czas* - на данный момент доступные дни и время\n" +
                        "*/poziom* - узнайте с какого уровня можно со мной заниматься\n" +
                        "*/wiecej* - узнать больше обо мне, как о преподавателе польского языка\n" +
                        "*/wpisacsie* - записаться\n" +
                        "*/back* - вернуться обратно");
                gameState.setGameStarted(false);
                gameState.setCoursesClicked(true);
                deleteMessage(chatId, messageId);
            } else if (data.equals("записаться")) {
                sendMessageWithMarkdown(chatId, "[Ваш польский порадник](https://t.me/ca5ual)");
                deleteMessage(chatId,messageId);
            }
        }

        else if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().toLowerCase();
            long chatId = update.getMessage().getChatId();
            long userId = update.getMessage().getFrom().getId();
            int messageId = update.getMessage().getMessageId();
            User user = update.getMessage().getFrom();
            String firstName = user.getFirstName();
            String username = user.getUserName();

            gameState = gameStateMap.get(userId); // получаем состояние игры для данного чата
            if (gameState == null) {
                gameState = new GameState(chatId, "set1"); // если состояние игры не создано для данного пользователя, создаем новое состояние
                gameStateMap.put(userId, gameState); // добавляем созданное состояние в карту
                setTimerForPlayer(userId);
                sendMessage(chatId, "Привет :) Рад тебя видеть, " + firstName + " (@" + username + ").");
                sendMessage(887447806, "Пользователь: " + username + " воспользовался ботом. \n" +
                        "Индентификатор чата - " + userId);
            }

            System.out.println(gameStateMap);
            System.out.println("For chat " + chatId + " set dictionary + " + gameState.getDictionary());
            gameState.setLastInteractionTime(System.currentTimeMillis());
            if (messageText.equals("/start")) {
                gameState.setGameStarted(false);
                gameState.setBookClicked(false);
                gameState.setDictionaryClicked(false);
                gameState.setTestClicked(false);
                gameState.setCoursesClicked(false);
                sendMessageWithButtons(chatId,
                        "Siema, меня зовут DARY/O (Дарьйо). \n\n" +
                                "Я твой искуственный помощник в изучении польского языка.\n" +
                                "Хоть я и робот, но смогу вас кое-чему научить. \n\n" +
                                "Если у Вас имеются идеи как меня можно улучшить, то, пожалуйста, напишите моему менеджеру -> @ca5ual_off");
                deleteNavigationButtons(chatId);
            } else if (messageText.equals("/startgame")) {
                sendNavigationButtons(chatId);
                sendNextWord(chatId);
                deleteMessage(chatId, messageId);
            }
            else if (messageText.equals("/czas")) {
                sendMessageWithMarkdown(chatId, "*Актуальное время для записи на занятия:*\n\n" +
                        "Группа (вечер) - _Понедельник_ (17:00-18:15)\n" +
                        "Группа (вечер) - _Среда_ (16:45-18:00)\n" +
                        "Группа (вечер) - _Пятница_ (17:00-18:15)\n" +
                        "Группа (вечер) - _Суббота_ (16:00-17:15)\n\n" +
                        "Группа (день) - _Понедельник_ (14:00-15:15)\n" +
                        "Группа (день)- _Среда_ (14:00-15:15)\n" +
                        "Группа (день)- _Пятница_ (14:00-15:15)\n" +
                        "Группа (день)- _Суббота_ (15:00-15:15)\n\n");
                sendMessageWithMarkdown(chatId, "*Индивидуальное* - _время устанавливается индивидуально_");
            } else if (messageText.equals("/back")) {
                sendMessageWithButtons(chatId, "Вы перешли в меню! \n" +
                                                    "Выберите одну из опций:");
                deleteMessage(chatId, messageId);
                gameState.setGameStarted(false);
                gameState.setBookClicked(false);
                gameState.setDictionaryClicked(false);
                gameState.setCoursesClicked(false);
                gameState.setTestClicked(false);
                deleteNavigationButtons(chatId);
            }
            else if(messageText.equals("/poziom")) {
                sendMessageWithMarkdown(chatId, "_Первое занятие - стоит половину от полного._\n" +
                        "На нём мы знакомимся и я определяю ваш примерный уровень полького языка.\n\n" +
                        "*Заниматься со мной можно с любого уровня. Однако учитывайте, что придётся много работать!*)");
            } else if (messageText.equals("/wiecej")) {
                sendMessageWithMarkdown(chatId, "Меня зовут Денис. Проживаю в Польше больше 3 лет, изучал польский" +
                        " в польской школе _Kinga_ 5 лет тому, пребывал в _польском лагере_ в течении 2-х месяцев, дополнительное" +
                        " изучение освоил в университете, где уровень был _B2_.\n\n" +
                        "На данный момент прошло уже *год* с того момента, как я начал учить людей польскому языку. \n" +
                        "*Если вам хочется узнать ещё больше - напишите мне*");
            } else if (messageText.equals("/wpisacsie")) {
                sendMessageZapis(chatId, "Мне приятно, что вы хотите записаться.\n Пожалуйста, нажмите на кнопку ниже!");
            }
            else if (messageText.equals("/cena")) {
                sendMessageWithMarkdown(chatId, "*Групповые занятия* _(1 час 15 минут, 2 занятия в неделю)_ : *240 zł (2050 грн.)* - месяц (от 4  до 6 человек) \n\n" +
                                                    "*Индивидуальные занятия* (1 час, 2 занятия в неделю) - *320 zł (2750 грн.)* - месяц (1 человек)");
            } else if (gameState.isGameStarted) {
                if (messageText.equals("/res")) {
                    sendMessageYesNo(chatId, "Вы хотите перезагрузить игру?");
                } else if (messageText.equals("/stop")) {
                    gameState.setGameStarted(false); // выходим из режима игры
                    endGame(chatId);
                    gameStateMap.remove(chatId);
                    deleteNavigationButtons(chatId);
                    sendMessageWithButtons(chatId, "Вы вернулись в меню. Выберите одну из опций: ");
                    System.out.println(gameState.isGameStarted);
                } else if (messageText.equals("/left")) {
                    int remainingWordsCount = gameState.getWORDS().size() - gameState.getCorrectAnswersCount() - gameState.getIncorrectAnswersCount();
                    sendMessage(chatId, "Осталось ответить на " + remainingWordsCount + " слов(а)");
                } else {
                    playGame(chatId, messageText, update);
                }
                // проверить ответ пользователя
            } else {
                sendMessageWithButtons(chatId,
                        "Видимо, у тебя появились какие-то проблемы. Что ж, я тебе помогу.");
            }
        }
    }
    public void sendPhoto(Long chatId, String photoFilePath, String caption) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        try {
            // Открываем входной поток к файлу
            FileInputStream fileInputStream = new FileInputStream(photoFilePath);
            sendPhoto.setPhoto(new InputFile(fileInputStream, photoFilePath));
            sendPhoto.setCaption(caption);
            execute(sendPhoto);
            // Закрываем поток
            fileInputStream.close();
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDocument(Long chatId, String pdfFilePath, String caption) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        try {
            // Открываем входной поток к файлу
            FileInputStream fileInputStream = new FileInputStream(pdfFilePath);
            sendDocument.setDocument(new InputFile(fileInputStream, pdfFilePath));
            sendDocument.setCaption(caption);
            execute(sendDocument);
            // Закрываем поток
            fileInputStream.close();
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAudio(Long chatId, String audioFilePath, String caption) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(chatId);
        try {
            // Открываем входной поток к файлу
            FileInputStream fileInputStream = new FileInputStream(audioFilePath);
            sendAudio.setAudio(new InputFile(fileInputStream, audioFilePath));
            sendAudio.setCaption(caption);
            execute(sendAudio);
            // Закрываем поток
            fileInputStream.close();
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup createInlineButtons(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(InlineKeyboardButtons.createButtons());
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createGameButtons(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(GameButtonsLevel.createButtonsGame());
        message.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return inlineKeyboardMarkup;
    }

    private void setTimerForPlayer(long userId) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long timeoutMillis = 20 * 60 * 1000; // 30 мин
                long now = System.currentTimeMillis();
                Set<Long> inactiveUsers = new HashSet<>(); //inactiveusers
                for (Map.Entry<Long, GameState> entry : gameStateMap.entrySet()) {
                    long lastInteractionTime = entry.getValue().getLastInteractionTime();
                    if (now - lastInteractionTime > timeoutMillis) {
                        inactiveUsers.add(entry.getKey());
                    }
                }
                for (Long userId : inactiveUsers) {
                    gameStateMap.remove(userId);
                    sendMessage(userId, "Я пойду пока поваляюсь. Позови, когда понадоблюсь. " +
                            "Используй /daryHello или что тебе захочется, но пиши!");
                    deleteNavigationButtons(userId);
                }
            }
        }, 0, 1000);
    }

    private void playGame(long chatId, String messageText, Update update) {
        messageText = update.getMessage().getText().toLowerCase();
        int remainingWordsCount = gameState.getWORDS().size() - gameState.getAnsweredWords().size() - gameState.getIncorrectlyAnsweredWords().size();
        String word = getWordByTranslation(messageText, gameState.currentWord);
        if (word != null && !gameState.getAnsweredWords().contains(word)) {
            if (word.equals(gameState.getLastTranslationAttempt())) {
            } else {
                sendMessage(chatId, "Верно!");
                gameState.getAnsweredWords().add(word);
                if (gameState.getIncorrectlyAnsweredWords().contains(gameState.getCurrentWord())) {
                } else {
                    gameState.setCorrectAnswersCount(gameState.getCorrectAnswersCount() + 1);
                }
                sendNextWord(chatId);
            }
            gameState.setLastTranslationAttempt(word);
            gameState.setLastAnsweredWord(gameState.getCurrentWord());
        } else if ((word == null || !word.equals(gameState.getCurrentWord())) && !gameState.getAnsweredWords().contains(messageText) && !gameState.getIncorrectlyAnsweredWords().contains(gameState.getCurrentWord()) &&
                gameState.getWORDS().containsKey(gameState.getCurrentWord())) {
            if (messageText.equals(gameState.getLastTranslationAttempt()) || messageText.equals(gameState.getAnsweredWords())) {
                sendMessage(chatId, "Прошу не загрязнять меня повторяющимися словами! Это неверно, попробуйте другой перевод!!!");
            } else {
                String correctAnswer = getTranslation(gameState.getCurrentWord());
                if (!gameState.getIncorrectlyAnsweredWords().contains(gameState.getCurrentWord())) {
                    gameState.getIncorrectlyAnsweredWords().add(gameState.getCurrentWord());
                    String correctAnswerInAllLanguages = gameState.getWORDS().get(gameState.getCurrentWord()).stream().collect(Collectors.joining(", "));
                    gameState.getIncorrectlyAnsweredWordsWithTranslations().add(gameState.getCurrentWord() + " - " + correctAnswerInAllLanguages);
                }
                if (correctAnswer != null) {
                    String correctAnswerInAllLanguages = gameState.getWORDS().get(gameState.getCurrentWord()).stream().collect(Collectors.joining(", "));
                    String message = "Неверно! Правильный ответ: (" + correctAnswerInAllLanguages + "). Польский: " + gameState.getCurrentWord();
                    gameState.getAnsweredWords().add(gameState.currentWord);
                    sendMessage(chatId, message);
                } else {
                    sendMessage(chatId, "Неверно! Извините, правильный ответ не найден.");
                }
                gameState.setIncorrectAnswersCount(gameState.getIncorrectAnswersCount() + 1);
                sendNextWord(chatId);
            }
            gameState.setLastTranslationAttempt(messageText);
        }
    }

    public static boolean isSimilar(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        // Создаем матрицу расстояний
        int[][] d = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Вычисляем расстояние Левенштейна
        for (int j = 1; j <= m; j++) {
            for (int i = 1; i <= n; i++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + cost);
            }
        }

        // Сравниваем расстояние с порогом, если расстояние меньше или равно порогу, то строки похожи
        int threshold = 2; // Порог можно выбрать на свое усмотрение
        return d[n][m] <= threshold;
    }

    private void endGame(long chatId) {
        String message = "Игра окончена! Количество правильных ответов: " + gameState.getCorrectAnswersCount() + "\n" +
                "Количество неправильных ответов: " + gameState.getIncorrectAnswersCount() + "\n\n" + "Неверно отвеченные слова:" + "\n" + String.join("\n", gameState.getIncorrectlyAnsweredWordsWithTranslations());
        sendMessage(chatId, message);
        gameState.setCurrentWord(null);
        gameState.setAnsweredWords(new HashSet<>());
        gameState.setIncorrectlyAnsweredWords(new HashSet<>());
        gameState.setCorrectAnswersCount(0);
        gameState.setIncorrectAnswersCount(0);
        gameState.setIncorrectlyAnsweredWordsWithTranslations(new ArrayList<>());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            lastSendMessage = message;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageZapis(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        // создание объекта InlineKeyboardMarkup для кнопок
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // создание кнопок и добавление их в список rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Записаться");
        button1.setCallbackData("записаться");
        row1.add(button1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("<< Назад");
        button3.setCallbackData("Delete");
        row2.add(button3);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        // установка списка rows в объект InlineKeyboardMarkup
        markupKeyboard.setKeyboard(rows);

        // добавление InlineKeyboardMarkup в SendMessage
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageFreeNot(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        // создание объекта InlineKeyboardMarkup для кнопок
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // создание кнопок и добавление их в список rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Бесплатные");
        button1.setCallbackData("free");
        row1.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Платные");
        button2.setCallbackData("not");
        row1.add(button2);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("<< Назад");
        button3.setCallbackData("Delete");
        row2.add(button3);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        // установка списка rows в объект InlineKeyboardMarkup
        markupKeyboard.setKeyboard(rows);

        // добавление InlineKeyboardMarkup в SendMessage
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageYesNo(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        // создание объекта InlineKeyboardMarkup для кнопок
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // создание кнопок и добавление их в список rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Да");
        button1.setCallbackData("yes");
        row1.add(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Нет");
        button2.setCallbackData("no");
        row1.add(button2);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        // установка списка rows в объект InlineKeyboardMarkup
        markupKeyboard.setKeyboard(rows);

        // добавление InlineKeyboardMarkup в SendMessage
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
            lastSendMessage = message;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageRestart(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        // создание объекта InlineKeyboardMarkup для кнопок
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // создание кнопок и добавление их в список rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Повторить");
        button1.setCallbackData("Повторить");
        row1.add(button1);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        // установка списка rows в объект InlineKeyboardMarkup
        markupKeyboard.setKeyboard(rows);

        // добавление InlineKeyboardMarkup в SendMessage
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageMenu(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        // создание объекта InlineKeyboardMarkup для кнопок
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // создание кнопок и добавление их в список rows
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("В меню");
        button1.setCallbackData("Menu");
        row1.add(button1);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        // установка списка rows в объект InlineKeyboardMarkup
        markupKeyboard.setKeyboard(rows);

        // добавление InlineKeyboardMarkup в SendMessage
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageWithTestButton(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Create the first row
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Próbne testy");
        button1.setCallbackData("Тесты");
        row1.add(button1);

        // Add the first row to the list of rows
        rows.add(row1);

        markupKeyboard.setKeyboard(rows);
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageWithButtons(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Create the first row
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Memo - Gra \uD83C\uDFAE");
        button1.setCallbackData("Игра");
        row1.add(button1);

        rows.add(row1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Słownictwo \uD83D\uDDC2");
        button2.setCallbackData("Словарь");
        row2.add(button2);

        // Add the first row to the list of rows
        rows.add(row2);

        // Create the second row
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Próbne testy \uD83D\uDCDD");
        button3.setCallbackData("Тесты");
        row3.add(button3);

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Ksiązki \uD83D\uDCDA");
        button4.setCallbackData("Книги");
        row3.add(button4);

        rows.add(row3);
        List<InlineKeyboardButton> row4 = new ArrayList<>();
        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText("Zapisz się na kursy \uD83D\uDCC8");
        button5.setCallbackData("Курсы");
        row4.add(button5);


        rows.add(row4);
        markupKeyboard.setKeyboard(rows);
        message.setReplyMarkup(markupKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void deleteNavigationButtons(long chatId) {
        ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
        keyboardMarkup.setRemoveKeyboard(true);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("\uD83E\uDD16");
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendNavigationButtons(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Вы можете использовать команды Перезапуска и Стоп вручную или нажимая на кнопки снизу.");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setSelective(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("/res"));
        row.add(new KeyboardButton("/stop"));
        row.add(new KeyboardButton("/left"));
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(String.valueOf(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithMarkdown(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendNextWord(long chatId) {
        Set<String> unansweredWords = new HashSet<>(gameState.getWORDS().keySet());
        unansweredWords.removeAll(gameState.getAnsweredWords());
        int incorrect = gameState.getIncorrectAnswersCount();
        int correct = gameState.getCorrectAnswersCount();
        if (unansweredWords.isEmpty()) {
            endGame(chatId);
            gameStateMap.remove(chatId);
            if (gameState.getDictionary().equals("set7")) {
                if (correct >= 0 && correct <= 10) {
                    sendMessageMenu(chatId, "Ваш уровень А0. Попробуйте пройти игру снова, ваши знания увеличатся!");
                    sendMessageRestart(chatId, "Я бы вам порекомендовал пройти этот тест ещё раз!");
                    deleteNavigationButtons(chatId);
                    gameState.setGameStarted(true);
                    gameStateMap.remove(chatId);
                } else if (correct >= 11 && correct <= 21) {
                    sendMessageMenu(chatId, "Ваш уровень А1. У вас уже есть какая-то база, но пора двигаться дальше. Всё получится, не останавливайтесь!");
                    sendMessageRestart(chatId, "Я бы вам порекомендовал пройти этот тест ещё раз!");
                    deleteNavigationButtons(chatId);
                    gameState.setGameStarted(true);
                    gameStateMap.remove(chatId);
                } else if (correct >= 22 && correct <= 32) {
                    sendMessageMenu(chatId, "Ваш уровень А2. На данном уровне вы уже способны базово разговаривать на польском языке, вы молодец!");
                    sendMessageRestart(chatId, "Вы можете пройти игру ещё раз, если хотите увеличить словарный запас.");
                    deleteNavigationButtons(chatId);
                    gameState.setGameStarted(true);
                    gameStateMap.remove(chatId);
                } else if (correct >= 33 && correct <= 43) {
                    sendMessageMenu(chatId, "Ваш уровень B1. Это уровень свидетельствует о вашей продвинутости в польском языке. Я очень рад, что у вас такие успехи, но рано останавливаться!");
                    sendMessageRestart(chatId, "Думаю, это кнопка вам уже не нужна, но вдруг захотите.");
                    deleteNavigationButtons(chatId);
                    gameState.setGameStarted(true);
                    gameStateMap.remove(chatId);
                } else if (correct >= 44 && correct <= 55) {
                    sendMessageMenu(chatId, "Ваш уровень B2. Мне, как работу, тяжело осознать, что у меня есть соперник по знаниям в польском языке, однако, признаю, вы сильный соперник. Спасибо!");
                    deleteNavigationButtons(chatId);
                    gameState.setGameStarted(true);
                    gameStateMap.remove(chatId);
                }
            } else if (incorrect > correct) {
                sendMessageRestart(chatId, "У вас было слишком много ошибок. Пожалуйста, повторите попытку, нажимая \"Повторить\" ");
                deleteNavigationButtons(chatId);
                gameState.setGameStarted(true);
                gameStateMap.remove(chatId);
            } else {
                sendMessageMenu(chatId, "Поздравляю, вы прошли данную тему! Нажмите \"Меню\", чтобы перейти в меню.");
                deleteNavigationButtons(chatId);
                gameStateMap.remove(chatId);
            }
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(unansweredWords.size());
            String polishWord = (String) unansweredWords.toArray()[randomIndex];
            sendMessage(chatId, "Переведите это слово на русский (украинский): ");
            gameState.setCurrentWord(polishWord);
            sendMessage(chatId, polishWord);
        }
    }

    public String getWordByTranslation(String translation, String currentWord) {
        List<String> translations = gameState.getWORDS().get(currentWord);
        if (translations != null) {
            for (String t : translations) {
                if (t.replaceAll("[^\\p{L}\\p{N}]", "").equalsIgnoreCase(translation.replaceAll("[^\\p{L}\\p{N}]", ""))) {
                    return currentWord;
                }
                if (isSimilar(t, translation)) {
                    return currentWord;
                }
            }
        }
        return null;
    }

    private String getTranslation(String word) {
        List<String> translationList = gameState.getWORDS().get(word);
        if (translationList != null && !translationList.isEmpty()) {
            return translationList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}