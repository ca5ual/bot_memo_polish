package com.example.mypackage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardButtons {

    public static List<List<InlineKeyboardButton>> createButtons() {

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("A2 \uD83D\uDFE2");
        button1.setCallbackData("Test1");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("B1 (1) \uD83D\uDFE1");
        button2.setCallbackData("Test2");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("B1 (2) \uD83D\uDFE1");
        button3.setCallbackData("Test3");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("B1 (3) \uD83D\uDFE1");
        button4.setCallbackData("Test4");

        InlineKeyboardButton button5 = new InlineKeyboardButton();
        button5.setText("B2 (1) \uD83D\uDFE0");
        button5.setCallbackData("Test5");

        InlineKeyboardButton button6 = new InlineKeyboardButton();
        button6.setText("B2 (2) \uD83D\uDFE0");
        button6.setCallbackData("Test6");

        InlineKeyboardButton button7 = new InlineKeyboardButton();
        button7.setText("B2 (3) \uD83D\uDFE0");
        button7.setCallbackData("Test7");

        InlineKeyboardButton button8 = new InlineKeyboardButton();
        button8.setText("C1 (1) \uD83D\uDD34");
        button8.setCallbackData("Test8");

        InlineKeyboardButton button9 = new InlineKeyboardButton();
        button9.setText("C1 (2) \uD83D\uDD34");
        button9.setCallbackData("Test9");

        InlineKeyboardButton button10 = new InlineKeyboardButton();
        button10.setText("C2 \uD83D\uDD35");
        button10.setCallbackData("Test10");

        InlineKeyboardButton button11 = new InlineKeyboardButton();
        button11.setText("B1 (dzieci oraz młodzież) \uD83D\uDFE1");
        button11.setCallbackData("Test11");

        InlineKeyboardButton button12 = new InlineKeyboardButton();
        button12.setText("B2 (dzieci oraz młodzież) \uD83D\uDFE0");
        button12.setCallbackData("Test12");

        InlineKeyboardButton button13 = new InlineKeyboardButton();
        button13.setText("<< Назад");
        button13.setCallbackData("Delete");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(button1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(button2);
        keyboardButtonsRow2.add(button3);
        keyboardButtonsRow2.add(button4);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(button5);
        keyboardButtonsRow3.add(button6);
        keyboardButtonsRow3.add(button7);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(button8);
        keyboardButtonsRow4.add(button9);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        keyboardButtonsRow5.add(button10);

        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(button11);

        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        keyboardButtonsRow7.add(button12);

        List<InlineKeyboardButton> keyboardButtonsRow8 = new ArrayList<>();
        keyboardButtonsRow8.add(button13);

        List<List<InlineKeyboardButton>> row = new ArrayList<>();
        row.add(keyboardButtonsRow1);
        row.add(keyboardButtonsRow2);
        row.add(keyboardButtonsRow3);
        row.add(keyboardButtonsRow4);
        row.add(keyboardButtonsRow5);
        row.add(keyboardButtonsRow6);
        row.add(keyboardButtonsRow7);
        row.add(keyboardButtonsRow8);
        return row;
    }
}