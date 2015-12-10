package com.github.ustc_zzzz.fmltutor.common;

import com.github.ustc_zzzz.fmltutor.enchantment.EnchantmentLoader;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventLoader
{
    public static final EventBus EVENT_BUS = new EventBus();

    public EventLoader()
    {
        MinecraftForge.EVENT_BUS.register(this);
        EventLoader.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        if (player.isServerWorld() && event.target instanceof EntityPig)
        {
            EntityPig pig = (EntityPig) event.target;
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && (stack.getItem() == Items.wheat || stack.getItem() == Items.wheat_seeds))
            {
                player.attackEntityFrom((new DamageSource("byPig")).setDifficultyScaled().setExplosion(), 8.0F);
                player.worldObj.createExplosion(pig, pig.posX, pig.posY, pig.posZ, 2.0F, false);
                pig.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
        if (event.player.isServerWorld())
        {
            String info = String.format("%s picks up: %s", event.player.getName(), event.pickedUp.getEntityItem());
            ConfigLoader.logger().info(info);
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!event.world.isRemote)
        {
            String info = String.format("%s interacts with: %s", event.entityPlayer.getName(), event.pos);
            ConfigLoader.logger().info(info);
        }
    }

    @SubscribeEvent
    public void onPlayerClickGrassBlock(PlayerRightClickGrassBlockEvent event)
    {
        if (!event.world.isRemote)
        {
            BlockPos pos = event.pos;
            Entity tnt = new EntityTNTPrimed(event.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, null);
            event.world.spawnEntityInWorld(tnt);
        }
    }

    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (!event.world.isRemote && event.harvester != null)
        {
            ItemStack itemStack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireBurn.effectId, itemStack) > 0
                    && itemStack.getItem() != Items.shears)
            {
                for (int i = 0; i < event.drops.size(); ++i)
                {
                    ItemStack stack = event.drops.get(i);
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null)
                    {
                        newStack = newStack.copy();
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(i, newStack);
                    }
                    else if (stack != null)
                    {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b && (block.isFlammable(event.world, event.pos, EnumFacing.DOWN)
                                || block.isFlammable(event.world, event.pos, EnumFacing.EAST)
                                || block.isFlammable(event.world, event.pos, EnumFacing.NORTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.SOUTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.UP)
                                || block.isFlammable(event.world, event.pos, EnumFacing.WEST)))
                        {
                            event.drops.remove(i);
                        }
                    }
                }
            }
        }
    }

    @Cancelable
    public static class PlayerRightClickGrassBlockEvent extends net.minecraftforge.event.entity.player.PlayerEvent
    {
        public final BlockPos pos;
        public final World world;

        public PlayerRightClickGrassBlockEvent(EntityPlayer player, BlockPos pos, World world)
        {
            super(player);
            this.pos = pos;
            this.world = world;
        }
    }
}
