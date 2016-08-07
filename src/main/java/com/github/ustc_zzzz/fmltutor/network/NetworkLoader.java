package com.github.ustc_zzzz.fmltutor.network;

import com.github.ustc_zzzz.fmltutor.FMLTutor;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkLoader
{
    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(FMLTutor.MODID);

    private static int nextID = 0;

    public NetworkLoader(FMLPreInitializationEvent event)
    {
        registerMessage(MessagePositionHistory.Handler.class, MessagePositionHistory.class, Side.CLIENT);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        instance.registerMessage(messageHandler, requestMessageType, nextID++, side);
    }
}
