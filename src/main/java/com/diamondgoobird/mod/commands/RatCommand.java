package com.diamondgoobird.mod.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.diamondgoobird.mod.Test.toPrompt;
import static jdk.nashorn.internal.objects.Global.print;

public class RatCommand extends BaseCommand {
    private static boolean automessage = false;
    public static String keyword = null;
    private static JsonArray context = new JsonArray();
    private static int currentPrompts = 0;

    @Override
    public String getCommandName() {
        return "rat";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "enable ratrat behavior";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1 && args[0].startsWith("!")) {
            String command = args[0].substring(1);
            switch (command) {
                case "resetcontext":
                    context = new JsonArray();
                    print(sender, "reset context");
                    break;
                case "help":
                    print(sender, "getcontext, updatecontext, resetcontext, automessage");
                    break;
                case "automessage":
                    automessage = !automessage;
                    print(sender, automessage ? "Automatically sends messages" : "No longer automatically sends messages");
                    break;
                case "keyword":
                    keyword = args[1];
                    print(sender, "keyword set to: " + keyword);
                    break;
            }
            return;
        }
        String prompt = toPrompt(args);
        print(sender, EnumChatFormatting.LIGHT_PURPLE + "Prompt: " + EnumChatFormatting.AQUA + "\"" + EnumChatFormatting.DARK_AQUA + prompt + EnumChatFormatting.AQUA + "\"");
        new Thread( () -> {
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }
            while (currentPrompts >= 1);
            currentPrompts++;
            String llama3 = getResponse(prompt);
            llama3 = llama3.toLowerCase().replaceAll("kirkcatcat", "kirkratrat").replaceAll("andycatcat", "andybadcat").replaceAll("[^\\sa-zA-Z0-9]","");
            if (automessage) {
                String finalLlama = llama3;
                new Thread( () -> {
                    try {
                        Thread.sleep((long) (2000 * (Math.random() * 1 + 1)));
                        Minecraft.getMinecraft().thePlayer.sendChatMessage(finalLlama);
                        currentPrompts--;
                    } catch (InterruptedException e) {

                    }
                }).start();
            }
            else {
                print(sender, "Llama3: " + llama3, llama3);
            }
        }).start();
    }

    private static String getResponse(String prompt) {
        try {
            JsonObject json = new JsonObject();

            json.addProperty("model", "llama3");
            json.addProperty("prompt", prompt);
            if (context.size() >= 1) {
                json.add("context", context);
            }
            json.addProperty("system", "play the role of " + Minecraft.getMinecraft().thePlayer.getName() + ", a chatter on the hypixel server who acts young, innocent and lives in vermont. speak in short messages (6-10 words), with words missing from your sentences but still readable and clearly spoken. do not claim to be an ai or bot, never reveal your age, if asked, say you're old. keep your messages neutral, not happy or kind, however be somewhat snarky and rude. keep your messages only slightly incoherent/childlike while still spoken clearly. your friends are kirkcatcat (who you are kind to), andycatcat (who you are jokingly rude to), spacebyte, and soxie_.");

            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(json.toString());
            out.close();

            int responseCode = connection.getResponseCode();

            System.out.println(responseCode);

            BufferedReader in = null;
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                JsonParser parser = new JsonParser();
                while ((inputLine = in.readLine()) != null) {
                    JsonObject element = parser.parse(inputLine).getAsJsonObject();
                    if (element.get("done").getAsBoolean()) {
                        context = element.get("context").getAsJsonArray();
                    }
                    else {
                        response.append(element.get("response").getAsString());
                    }
                }
                in.close();
                return response.toString() + "\n";
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return "Error Response: " + response.toString();
            }
        }
        catch (Exception h) {
            h.printStackTrace();
        }
        return "Big error";
    }
}
