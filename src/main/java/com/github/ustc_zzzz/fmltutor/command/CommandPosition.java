package com.github.ustc_zzzz.fmltutor.command;

import java.util.List;

import com.github.ustc_zzzz.fmltutor.capability.CapabilityLoader;
import com.github.ustc_zzzz.fmltutor.capability.IPositionHistory;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Vec3;

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
            return CommandBase.getListOfStringsMatchingLastWord(args, names);
        }
        return null;
    }
}
