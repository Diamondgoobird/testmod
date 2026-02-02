package com.diamondgoobird.mod.listeners;

import java.util.Map;
import java.util.TreeMap;

public class EmoteHandler {
    public static final Map<String, String> emotes = new TreeMap<>();

    static {
        emotes.put("<3", "❤");
        emotes.put(":star:", "✮");
        emotes.put(":yes:", "✔");
        emotes.put(":no:", "✖");
        emotes.put(":java:", "☕");
        emotes.put(":arrow:", "➜");
        emotes.put(":shrug:", "¯\\_(ツ)_/¯");
        emotes.put(":tableflip:", "(╯°□°）╯︵ ┻━┻");
        emotes.put("o/", "( ﾟ◡ﾟ)/");
        emotes.put(":123:", "123");
        emotes.put(":totem:", "☉_☉");
        emotes.put(":typing:", "✎...");
        emotes.put(":maths:", "√(π+x)=L");
        emotes.put(":snail:", "@'-'");
        emotes.put(":thinking:", "(0.o?)");
        emotes.put(":gimme:", "༼つ◕_◕༽つ");
        emotes.put(":wizard:", "('-')⊃━☆ﾟ.*･｡ﾟ");
        emotes.put(":pvp:", "⚔");
        emotes.put(":peace:", "✌");
        emotes.put(":oof:", "OOF");
        emotes.put(":puffer:", "<('O')>");
        emotes.put(":cute:", "(✿◠‿◠)");
        emotes.put(":cat:", "= ＾● ⋏ ●＾ =");
        emotes.put(":dj:", "ヽ(⌐■_■)ノ♬");
        emotes.put(":sloth:", "(・⊝・)");
        emotes.put(":yey:", "ヽ (◕◡◕) ﾉ");
        emotes.put(":dog:", "(ᵔᴥᵔ)");
        emotes.put(":dab:", "<o/");
        emotes.put("h/", "ヽ(^◇^*)/");
        emotes.put(":snow:", "☃");
    }

    public static String getEmote(String messageIn) {
        String message = messageIn;
        for (Map.Entry<String, String> entry : emotes.entrySet()) {
            message = message.replaceAll(entry.getKey(), entry.getValue());
        }
        return message;
    }
}
