package com.teic.trueris.menu.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.TextColor.Indexed;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import com.teic.trueris.LogType;
import com.teic.trueris.Logging;
import com.teic.trueris.menu.MenuContext;
import com.teic.trueris.sound.Sound;
import com.teic.trueris.sound.SoundManager;

public class EditableItem extends Item {
    private final Supplier<Double> GET;
    private final Function<Double, Boolean> SET;
    private final String SEPARATOR = " = ";

    public EditableItem(String label, Supplier<Double> get, Function<Double, Boolean> set) {
        super(label, true);
        this.GET = get;
        this.SET = set;
    }

    public Double getValue() { return GET.get(); }
    public boolean setValue(Double value) { return SET.apply(value); }
    public String getSEPARATOR() { return SEPARATOR; }

    @Override
    public String getDisplayName() {
        return LABEL + SEPARATOR + getValue();
    }

    @Override
    public boolean onSelect(MenuContext mc) throws IOException {
        Terminal tm = mc.tm();
        TextGraphics tg = mc.tg();

        int valueLength = ("" + getValue()).length();
        int nameOffset = mc.colPos()
            + LABEL.length()
            + SEPARATOR.length();

        tg.setBackgroundColor(ANSI.WHITE);
        tg.setForegroundColor(ANSI.BLACK);
        tg.putString(mc.colPos(), mc.rowPos(), LABEL);
        tg.setBackgroundColor(ANSI.DEFAULT);
        tg.setForegroundColor(ANSI.DEFAULT);

        for (int i = 0; i < valueLength; i++) {
            tg.putString(nameOffset + i, 
                    mc.rowPos(), " ");
        }
        
        int cursorPos = nameOffset;

        List<Character> charInp = new ArrayList<>();
        StringBuilder sBuilder = new StringBuilder();
        
        loop:
        while (true) {
            tm.flush();

            KeyStroke keyStroke = tm.readInput();
            KeyType keyType = keyStroke.getKeyType();
            Character gCharacter = null;

            switch (keyType) {
                case Escape -> {
                    Logging.writeLog(LogType.INFO, 
                            "Item exit: " + LABEL);
                    SoundManager.playSFX(Sound.MENU_BACK);
                    break loop;
                }
                case Character -> {
                    gCharacter = keyStroke.getCharacter();
                    tg.putString(cursorPos, mc.rowPos(), Character.toString(gCharacter));
                    cursorPos++;

                    charInp.add(gCharacter);
                }
                case Backspace -> {
                    gCharacter = ' ';
                    cursorPos--;
                    if (cursorPos < nameOffset) cursorPos = nameOffset;
                    tg.putString(cursorPos, mc.rowPos(), Character.toString(gCharacter));

                    if (!charInp.isEmpty())
                        charInp.removeLast();
                }
                case Enter -> {
                    for (Character ch : charInp) {
                        sBuilder.append(ch);
                    }
                    
                    String userInp = sBuilder.toString();
                    
                    try {
                        double doubleInp = Double.parseDouble(userInp);
                        if (setValue(doubleInp))
                            Logging.writeLog(LogType.DEBUG, LABEL + " set to " + doubleInp);
                        else {
                            throwUserError(mc, nameOffset);
                        }
                    }
                    catch (NumberFormatException e) {
                        throwUserError(mc, nameOffset);
                    }

                    break loop;
                }
                default -> {} 
            }
        }

        return true;
    }

    private void throwUserError(MenuContext mc, int nameOffset) throws IOException {
        Terminal tm = mc.tm();
        TextGraphics tg = mc.tg();

        tg.enableModifiers(SGR.UNDERLINE);
        tg.setForegroundColor(Indexed.fromRGB(255, 70, 70));
        tg.putString(
                nameOffset, 
                mc.rowPos(), 
                "Invalid Value"
                );
        tg.disableModifiers(SGR.UNDERLINE);
        tg.setForegroundColor(ANSI.DEFAULT);
    
        tm.readInput();
    }
}

