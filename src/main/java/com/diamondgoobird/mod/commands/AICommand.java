package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.TestConfig;
import com.diamondgoobird.mod.TestName;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.diamondgoobird.mod.Test.toPrompt;
import static com.diamondgoobird.mod.Test.print;

public class AICommand extends BaseCommand {
    public static Queue<String> messages = new LinkedList<>();
    private static JsonArray context = new JsonArray();

    @Override
    public String getCommandName() {
        return "ai";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Interacts with the Chatbot through ollama, configure with /test";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "!resetcontext", "!help", "!getcontext", "!updatecontext", "!send") : (args.length == 2 ? getListOfStringsMatchingLastWord(args, "yes", "no", "view") : null);
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
                    print(sender, "getcontext, updatecontext, resetcontext");
                    break;
                case "getcontext":
                    print(sender, context.toString());
                    break;
                case "updatecontext":
                    context = new JsonParser().parse(args[1]).getAsJsonArray();
                    print(sender, "yeah we contexted it");
                    break;
                case "send":
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("y")) {
                            modifyQueue(true);
                        }
                        else if (args[1].equalsIgnoreCase("reject") || args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("n")) {
                            modifyQueue(false);
                        }
                        else if (args[1].equalsIgnoreCase("view")) {
                            printQueue();
                        }
                    }
                    else {
                        modifyQueue(true);
                    }
                    break;
            }
            return;
        }
        promptAi(args);
    }

    public static void promptAi(String... args) {
        ICommandSender sender = Minecraft.getMinecraft().thePlayer;
        String prompt = toPrompt(args);
        print(sender, EnumChatFormatting.LIGHT_PURPLE + "Prompt: " + EnumChatFormatting.AQUA + "\"" + EnumChatFormatting.DARK_AQUA + prompt + EnumChatFormatting.AQUA + "\"");
        new Thread( () -> {
            String aiResponse = getResponse(prompt);
            TestName.log.info("prompt: " + prompt);
            TestName.log.info("aiResponse: " + aiResponse);
            aiResponse = aiResponse.replaceAll(Minecraft.getMinecraft().thePlayer.getName(), "").toLowerCase().replaceAll("kirkcatcat", "kirkratrat").replaceAll("andycatcat", "andybadcat").replaceAll("[^\\sa-zA-Z0-9]","");
            aiResponse = aiResponse.replaceAll("null", "").replaceAll("\n", " ");
            aiResponse = reconstruct(aiResponse);
            TestName.log.info("filtered response: " + aiResponse);
            if (TestConfig.instance.aiAutoSend) {
                try {
                    long duration = (long) (1000 * (TestConfig.instance.aiMinimumDelay + Math.random() * (TestConfig.instance.aiMaximumDelay - TestConfig.instance.aiMinimumDelay)));
                    Thread.sleep(duration);
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(aiResponse);
                } catch (InterruptedException e) {

                }
            }
            else {
                print(sender, EnumChatFormatting.LIGHT_PURPLE + "AI: " + aiResponse, aiResponse);
                AICommand.messages.add(aiResponse);
            }
        }).start();
    }

    private static String reconstruct(String aiResponse) {
        StringBuilder response = new StringBuilder();
        for (String pieces : aiResponse.split(" ")) {
            if (!pieces.isEmpty()) {
                response.append(pieces.trim() + " ");
            }
        }
        return response.toString().trim();
    }

    private void modifyQueue(boolean send) {
        if (!messages.isEmpty()) {
            String message = messages.remove();
            print(Minecraft.getMinecraft().thePlayer, (send ? EnumChatFormatting.GREEN + "Sent" : EnumChatFormatting.RED + "Rejected") + EnumChatFormatting.AQUA + ": " + message);
            if (send) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
            }
            print(Minecraft.getMinecraft().thePlayer, EnumChatFormatting.LIGHT_PURPLE + "New Queue" + EnumChatFormatting.DARK_PURPLE + ":");
        }
        printQueue();
    }

    private void printQueue() {
        StringBuilder sb = new StringBuilder();
        if (messages.isEmpty()) {
            sb.append(EnumChatFormatting.AQUA).append("Empty queue.").append("\n");
        }
        else {
            for (String message : messages) {
                sb.append(message).append("\n");
            }
        }
        int max = Minecraft.getMinecraft().fontRendererObj.getStringWidth(Arrays.stream(sb.toString().split("\n")).max(Comparator.comparingInt(o -> Minecraft.getMinecraft().fontRendererObj.getStringWidth(o))).get());
        max = (int) Math.ceil((double) max / Minecraft.getMinecraft().fontRendererObj.getCharWidth('='));
        max = Math.min(max, 35);
        String sb1 = EnumChatFormatting.LIGHT_PURPLE + repeat("=", max) + "\n" +
                sb +
                EnumChatFormatting.LIGHT_PURPLE + repeat("=", max);
        print(Minecraft.getMinecraft().thePlayer, sb1);
    }

    private String repeat(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    private static String getResponse(String prompt) {
        try {
            JsonObject json = new JsonObject();

            json.addProperty("model", TestConfig.instance.aiModelName);
            json.addProperty("prompt", prompt);
            if (context.size() >= 1) {
                json.add("context", context);
            }
            json.addProperty("system", TestConfig.instance.aiSystemPrompt.replaceAll("%player%", Minecraft.getMinecraft().thePlayer.getName()));

            if (TestConfig.instance.aiOptions) {
                JsonObject options = new JsonObject();
                options.addProperty("temperature", TestConfig.instance.aiTemperature);
                options.addProperty("num_predict", TestConfig.instance.aiMaximumTokens);
                options.addProperty("top_k", TestConfig.instance.aiTopK);
                json.add("options", options);
            }

            URL url = new URL(TestConfig.instance.aiOllamaAPI + "/api/generate");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(json.toString());
            out.close();

            int responseCode = connection.getResponseCode();

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
                return response.toString();
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
