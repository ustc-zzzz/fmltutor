package com.github.ustc_zzzz.fmltutor.network;

import com.github.ustc_zzzz.fmltutor.capability.CapabilityLoader;
import com.github.ustc_zzzz.fmltutor.capability.IPositionHistory;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePositionHistory implements IMessage
{
    public NBTTagCompound nbt;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<MessagePositionHistory, IMessage>
    {
        @Override
        public IMessage onMessage(MessagePositionHistory message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                final NBTBase nbt = message.nbt.getTag("histories");
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        if (player.hasCapability(CapabilityLoader.positionHistory, null))
                        {
                            IPositionHistory histories = player.getCapability(CapabilityLoader.positionHistory, null);
                            IStorage<IPositionHistory> storage = CapabilityLoader.positionHistory.getStorage();
                            storage.readNBT(CapabilityLoader.positionHistory, histories, null, nbt);
                        }
                    }
                });
            }
            return null;
        }
    }
}
