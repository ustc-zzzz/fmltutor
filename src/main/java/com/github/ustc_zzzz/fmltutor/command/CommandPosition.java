package com.github.ustc_zzzz.fmltutor.command;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.github.ustc_zzzz.fmltutor.capability.CapabilityLoader;
import com.github.ustc_zzzz.fmltutor.capability.IPositionHistory;
import com.github.ustc_zzzz.fmltutor.network.MessagePositionHistory;
import com.github.ustc_zzzz.fmltutor.network.NetworkLoader;
import com.github.ustc_zzzz.fmltutor.worldstorage.PositionWorldSavedData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CommandPosition extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "position";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.position.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length == 2 && "mark".equals(args[0]))
        {
            EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
            if ("list".equals(args[1]))
            {
                PositionWorldSavedData data;
                sender.addChatMessage(new ChatComponentText("Global: "));
                data = PositionWorldSavedData.getGlobal(entityPlayerMP.worldObj);
                for (int i = data.size() - 1; i >= 0; --i)
                {
                    sender.addChatMessage(new ChatComponentTranslation("commands.position.mark", data.getPosition(i),
                            entityPlayerMP.worldObj.getPlayerEntityByUUID(data.getPlayerUUID(i)).getName()));
                }
                sender.addChatMessage(new ChatComponentText("Dimension: "));
                data = PositionWorldSavedData.get(entityPlayerMP.worldObj);
                for (int i = data.size() - 1; i >= 0; --i)
                {
                    sender.addChatMessage(new ChatComponentTranslation("commands.position.mark", data.getPosition(i),
                            entityPlayerMP.worldObj.getPlayerEntityByUUID(data.getPlayerUUID(i)).getName()));
                }
            }
            else if ("global".equals(args[1]))
            {
                Vec3 pos = entityPlayerMP.getPositionVector();
                UUID uuid = entityPlayerMP.getUniqueID();
                PositionWorldSavedData.getGlobal(entityPlayerMP.worldObj).add(pos, uuid);
                sender.addChatMessage(new ChatComponentTranslation("commands.position.marked", pos));
            }
            else if ("dimension".equals(args[1]))
            {
                Vec3 pos = entityPlayerMP.getPositionVector();
                UUID uuid = entityPlayerMP.getUniqueID();
                PositionWorldSavedData.get(entityPlayerMP.worldObj).add(pos, uuid);
                sender.addChatMessage(new ChatComponentTranslation("commands.position.marked", pos));
            }
            return;
        }
        if (args.length > 1)
        {
            throw new WrongUsageException("commands.position.usage");
        }
        else
        {
            EntityPlayerMP entityPlayerMP = args.length > 0 ? CommandBase.getPlayer(sender, args[0])
                    : CommandBase.getCommandSenderAsPlayer(sender);
            Vec3 pos = entityPlayerMP.getPositionVector();

            if (entityPlayerMP == sender && entityPlayerMP.hasCapability(CapabilityLoader.positionHistory, null))
            {
                sender.addChatMessage(new ChatComponentTranslation("commands.position.history"));
                IPositionHistory histories = entityPlayerMP.getCapability(CapabilityLoader.positionHistory, null);
                for (Vec3 vec3 : histories.getHistories())
                {
                    if (vec3 != null)
                    {
                        sender.addChatMessage(new ChatComponentText(vec3.toString()));
                    }
                }
                histories.pushHistory(pos);

                MessagePositionHistory message = new MessagePositionHistory();
                IStorage<IPositionHistory> storage = CapabilityLoader.positionHistory.getStorage();

                message.nbt = new NBTTagCompound();
                message.nbt.setTag("histories", storage.writeNBT(CapabilityLoader.positionHistory, histories, null));

                NetworkLoader.instance.sendTo(message, entityPlayerMP);
            }

            sender.addChatMessage(new ChatComponentTranslation("commands.position.success", entityPlayerMP.getName(),
                    pos, entityPlayerMP.worldObj.provider.getDimensionName()));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            String[] names = MinecraftServer.getServer().getAllUsernames();
            names = Arrays.copyOf(names, names.length + 1);
            names[names.length - 1] = "mark";
            return CommandBase.getListOfStringsMatchingLastWord(args, names);
        }
        else if (args.length == 2 && "mark".equals(args[0]))
        {
            return CommandBase.getListOfStringsMatchingLastWord(args, "list", "global", "dimension");
        }
        return null;
    }
}
