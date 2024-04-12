package com.diamondgoobird.mod.listeners;

import com.diamondgoobird.mod.TestName;
import com.diamondgoobird.mod.commands.DownloadSkinCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestListener {
    private static final String GOOD_FORMAT = EnumChatFormatting.BLUE + "Found new skin: %s";
    private static final String BAD_FORMAT = EnumChatFormatting.RED + "Skin found again: %s";
    private static final Pattern SKIN_PATTERN = Pattern.compile("Your skin has been set to (?<skin>\\S+).");

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String text = event.message.getFormattedText();
        if (text.contains("unclaimed leveling reward")) {
            event.setCanceled(true);
            return;
        }
        else if (text.startsWith("\u00a7eClick here")) {
            TestName.cancelNext = false;
            event.setCanceled(true);
            return;
        }
        text = EnumChatFormatting.getTextWithoutFormattingCodes(text);
        Matcher m = SKIN_PATTERN.matcher(text);
        if (m.matches()) {
            System.out.print(m.group("skin"));
            DownloadSkinCommand.setCurrentFileName(m.group("skin"));
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(getMessage(m.group("skin"))));
        }
    }

    private static String getMessage(String group) {
        File directory = Paths.get("./skins/").toFile();
        if (!directory.exists() || directory.listFiles() == null) {
            return String.format(GOOD_FORMAT, group);
        }
        for (File file : directory.listFiles()) {
            if (file.getName().equalsIgnoreCase(group + ".png")) {
                return String.format(BAD_FORMAT, group);
            }
        }
        return String.format(GOOD_FORMAT, group);
    }
}
