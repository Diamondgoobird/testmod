package com.diamondgoobird.mod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class onDisconnect {
    @SubscribeEvent
    public void disconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        MinecraftForge.EVENT_BUS.register(new onJoin());
        onJoin.joined = false;
    }
}
