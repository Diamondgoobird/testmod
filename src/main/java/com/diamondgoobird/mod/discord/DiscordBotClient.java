package com.diamondgoobird.mod.discord;

import com.diamondgoobird.mod.TestConfig;
import com.diamondgoobird.mod.TestName;
// import net.dv8tion.jda.api.JDA;
// import net.dv8tion.jda.api.JDABuilder;
// import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

public class DiscordBotClient {
    // private JDA client;
    // private GuildMessageChannel channel;
    // public static DiscordBotClient instance = new DiscordBotClient();
//
    // public DiscordBotClient() {
    //     new Thread( () -> {
    //         init();
    //     }).start();
    // }
//
    // public void init() {
    //         try {
    //             String token = TestConfig.instance.discordToken;
    //             String serverId = TestConfig.instance.discordServerId;
    //             String channelId = TestConfig.instance.discordChannelId;
    //             client = JDABuilder.createLight(token).build();
    //             channel = client.getGuildById(serverId).getChannelById(GuildMessageChannel.class, channelId);
    //         }
    //         catch (Exception h) {
    //             TestName.log.warning("Discord failed to initialize.");
    //         }
    // }
//
    // public void sendMessage(String message) {
    //     new Thread( () -> {
    //         if (client == null || channel == null) {
    //             init();
    //         }
    //         channel.sendMessage(message);
    //     }).start();
    // }
}
