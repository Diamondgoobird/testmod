package com.diamondgoobird.mod.commands;

import com.diamondgoobird.mod.TestName;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DownloadSkinCommand extends BaseCommand {
    @Override
    public String getCommandName() {
        return "downloadskin";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 2) {
            TestName.downloadSkin(args[0], args[1]);
        }
        else {
            sender.addChatMessage(new ChatComponentText("Invalid argument amount."));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, getPlayerNames());
        }
        else if (args.length == 2) {
            return Lists.newArrayList(args[0]);
        }
        return null;
    }

    private String[] getPlayerNames() {
        LinkedList<String> names = new LinkedList<>();
        for (NetworkPlayerInfo networkPlayerInfo : Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()) {
            names.add(networkPlayerInfo.getGameProfile().getName());
        }
        return names.toArray(new String[0]);
    }
}
