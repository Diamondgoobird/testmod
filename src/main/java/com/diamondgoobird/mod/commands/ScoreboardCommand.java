package com.diamondgoobird.mod.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ScoreboardCommand extends BaseCommand {

    @Override
    public String getCommandName() {
        return "getscoreboard";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
        print(scoreboard.getObjectiveInDisplaySlot(1).getDisplayName());
        Collection<Score> scores = scoreboard.getSortedScores(scoreboard.getObjectiveInDisplaySlot(1));
        Collections.reverse((List<?>) scores);
        scores.forEach(
                s -> {
                    ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(s.getPlayerName());
                    String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, s.getPlayerName());
                    print(s1);
                }
        );
    }

    private static void print(String s) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
    }
}
