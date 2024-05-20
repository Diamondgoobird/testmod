package com.diamondgoobird.mod.listeners;

import com.diamondgoobird.mod.TestName;
import com.diamondgoobird.mod.commands.AICommand;
import com.diamondgoobird.mod.commands.DownloadSkinCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.collection.parallel.ParIterableLike;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestListener {
    private static final String GOOD_FORMAT = EnumChatFormatting.BLUE + "Found new skin: %s";
    private static final String BAD_FORMAT = EnumChatFormatting.RED + "Skin found again: %s";
    private static final Pattern SKIN_PATTERN = Pattern.compile("Your skin has been set to (?<skin>\\S+).");
    private static final Pattern GUILD_PATTERN = Pattern.compile("Guild > (?<name>[a-zA-Z0-9_]{3,16}) joined.");
    private static final Pattern CHAT_PATTERN = Pattern.compile("(?<rank>\\[[a-zA-Z]{2,}\\+{0,2}] )?(?<name>[a-zA-Z0-9_]{3,16}): (?<message>.+)");

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String text = event.message.getFormattedText();
        Matcher m1 = CHAT_PATTERN.matcher(EnumChatFormatting.getTextWithoutFormattingCodes(text));
        if (m1.matches()) {
            String keyword = Minecraft.getMinecraft().thePlayer.getName().substring(0, 4);
            if (AICommand.keyword != null) {
                keyword = AICommand.keyword;
            }
            if (m1.group("message").toLowerCase().contains(keyword) && !m1.group("name").equals(Minecraft.getMinecraft().thePlayer.getName())) {
                try {
                    new AICommand().processCommand(Minecraft.getMinecraft().thePlayer, new String[]{m1.group("name") + ": " + m1.group("message")});
                }
                catch (Exception h) {
                    h.printStackTrace();
                }
            }
        }
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
        Matcher gm = GUILD_PATTERN.matcher(text);
        if (gm.matches()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/gc Welcome back " + gm.group("name") + "!");
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
