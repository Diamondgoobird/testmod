package com.diamondgoobird.mod.listeners;

import com.diamondgoobird.mod.TestConfig;
import com.diamondgoobird.mod.commands.AICommand;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AIListener {
    private static final Pattern CHAT_PATTERN = Pattern.compile(".*?(?<rank>\\[[a-zA-Z]{2,}\\+{0,2}] )?(?<name>[a-zA-Z0-9_]{3,16}): (?<message>.+)");
    private final Set<String> usercache = new TreeSet<>();
    private static final Set<String> ignoreNames = new TreeSet<>();

    static {
        ignoreNames.add(Minecraft.getMinecraft().thePlayer.getName());
        ignoreNames.add("Moderators");
    }

    @SubscribeEvent
    public void onMessageReceived(ClientChatReceivedEvent event) {
        if (event.type == 2) {
            return;
        }
        new Thread( () -> {
            String text = event.message.getFormattedText();
            String strippedText = EnumChatFormatting.getTextWithoutFormattingCodes(text);
            Matcher m1 = CHAT_PATTERN.matcher(strippedText);
            if (m1.matches()) {
                String keyword = Minecraft.getMinecraft().thePlayer.getName().substring(0, 4).toLowerCase();
                if (TestConfig.instance.aiKeyword != null && !TestConfig.instance.aiKeyword.isEmpty()) {
                    keyword = TestConfig.instance.aiKeyword;
                }
                if (!shouldIgnoreName(m1.group("name")) && strippedText.toLowerCase().contains(keyword.toLowerCase())) {
                    AICommand.promptAi(m1.group("name") + ": " + m1.group("message"));
                }
            }
        }).start();
    }

    private boolean shouldIgnoreName(String name) {
        return ignoreNames.contains(name) || !isUsername(name);
    }

    private boolean isUsername(String name) {
        if (usercache.contains(name)) {
            return true;
        }
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
        usercache.add(name);
        return true;
    }
}
