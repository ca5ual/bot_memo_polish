package com.example.mypackage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GameButtonsLevel {

    public static List<List<InlineKeyboardButton>> createButtonsGame() {

            InlineKeyboardButton button1 = new InlineKeyboardButton();
            button1.setText("Mieszkanie \uD83C\uDFDA");
            button1.setCallbackData("Квартира");

            InlineKeyboardButton button2 = new InlineKeyboardButton();
            button2.setText("Rutyna \uD83D\uDEB6\uD83C\uDFFC");
            button2.setCallbackData("Жизнь");

            InlineKeyboardButton button3 = new InlineKeyboardButton();
            button3.setText("Galeria Handlowa \uD83C\uDFEF");
            button3.setCallbackData("ТЦ");

            InlineKeyboardButton button4 = new InlineKeyboardButton();
            button4.setText("Przyroda \uD83C\uDFD5");
            button4.setCallbackData("Природа");

            InlineKeyboardButton button5 = new InlineKeyboardButton();
            button5.setText("Czasowniki \uD83D\uDCD7");
            button5.setCallbackData("Глаголы");

            InlineKeyboardButton button6 = new InlineKeyboardButton();
            button6.setText("Przymiotniki \uD83D\uDCD8");
            button6.setCallbackData("Прилагательные");

            InlineKeyboardButton button7 = new InlineKeyboardButton();
            button7.setText("Sprawdź swój poziom wiedzy \uD83D\uDCA1");
            button7.setCallbackData("Проверка");

            InlineKeyboardButton button8 = new InlineKeyboardButton();
            button8.setText("<< Назад ");
            button8.setCallbackData("Delete");

            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(button1);
            keyboardButtonsRow1.add(button2);

            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            keyboardButtonsRow2.add(button3);
            keyboardButtonsRow2.add(button4);

            List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
            keyboardButtonsRow3.add(button5);
            keyboardButtonsRow3.add(button6);

            List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
            keyboardButtonsRow4.add(button7);

            List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
            keyboardButtonsRow5.add(button8);

            List<List<InlineKeyboardButton>> row = new ArrayList<>();
            row.add(keyboardButtonsRow1);
            row.add(keyboardButtonsRow2);
            row.add(keyboardButtonsRow3);
            row.add(keyboardButtonsRow4);
            row.add(keyboardButtonsRow5);
            return row;
        }
    }
