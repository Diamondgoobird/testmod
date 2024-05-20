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

public class AICommand extends BaseCommand {

    public static String keyword = null;
    private static JsonObject context = new JsonObject();
    private static boolean updateContext = true;
    private static int currentPrompts = 0;
    private static String systemPrompt = "";

    @Override
    public String getCommandName() {
        return "ai";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "talks to ai";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length >= 1 && args[0].startsWith("!")) {
            String command = args[0].substring(1);
            switch (command) {
                case "getcontext":
                    print(sender, context.toString());
                    break;
                case "updatecontext":
                    updateContext = !updateContext;
                    print(sender, updateContext ? "Context will now dynamically update." : "Context is now locked as is.");
                    break;
                case "resetcontext":
                    context.add("llama3", new JsonArray());
                    print(sender, "reset context");
                    break;
                case "system":
                    systemPrompt = args[1];
                    print(sender, "set system prompt to: " + systemPrompt);
                    break;
                case "help":
                    print(sender, "getcontext, updatecontext, resetcontext, system, keyword");
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
            String llama3 = getResponse(prompt, "llama3");
            print(sender, "Llama3: " + llama3, llama3);
        }).start();
    }

    private static String getResponse(String prompt, String model) {
        try {
            JsonObject json = new JsonObject();

            json.addProperty("model", model);
            json.addProperty("prompt", prompt);
            if (context.entrySet().size() >= 1) {
                json.add("context", context.get(model));
            }
            if (!systemPrompt.equals("")) {
                json.addProperty("system", systemPrompt);
            }

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
                        context.add(model, element.get("context").getAsJsonArray());
                    }
                    else {
                        String s = element.get("response").getAsString();

                        response.append(s);
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
