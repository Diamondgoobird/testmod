package com.diamondgoobird.mod.listeners;

import com.diamondgoobird.mod.TestConfig;
import com.diamondgoobird.mod.TestName;
import com.diamondgoobird.mod.commands.DownloadSkinCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestListener {
    private static final String GOOD_FORMAT = EnumChatFormatting.BLUE + "Found new skin: %s";
    private static final String BAD_FORMAT = EnumChatFormatting.RED + "Skin found again: %s";
    private static final Pattern SKIN_PATTERN = Pattern.compile("Your skin has been set to (?<skin>\\S+).");
    private static final Pattern GUILD_PATTERN = Pattern.compile("Guild > (?<name>[a-zA-Z0-9_]{3,16}) joined.");
    private static final Pattern CHAT_PATTERN = Pattern.compile(".*?(?<rank>\\[[a-zA-Z]{2,}\\+{0,2}] )?(?<name>[a-zA-Z0-9_]{3,16}): (?<message>.+)");

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (event.type == 2) {
            return;
        }
        String text = event.message.getFormattedText();
        String strippedText = EnumChatFormatting.getTextWithoutFormattingCodes(text);
        Matcher m1 = CHAT_PATTERN.matcher(strippedText);
        if (m1.matches()) {
            String keyword = Minecraft.getMinecraft().thePlayer.getName().substring(0, 4).toLowerCase();
            if (TestConfig.instance.aiKeyword != null && !TestConfig.instance.aiKeyword.isEmpty()) {
                keyword = TestConfig.instance.aiKeyword;
            }
            if (!shouldIgnoreName(m1.group("name")) && strippedText.toLowerCase().contains(keyword.toLowerCase())) {
                try {
                    ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/rat " + m1.group("name") + ": " + m1.group("message"));
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

    private boolean shouldIgnoreName(String name) {
        return name.equals(Minecraft.getMinecraft().thePlayer.getName()) || !isUsername(name);
    }

    private boolean isUsername(String name) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("https://api.mojang.com/users/profiles/minecraft/" + name);
            request.addHeader("content-type", "application/json");
            CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)request);
            if (closeableHttpResponse.toString().contains("No Content")) {
                return false;
            }
        }
        catch (IOException e) {
            return false;
        }
        return true;
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
