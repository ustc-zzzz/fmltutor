package com.github.ustc_zzzz.fmltutor.item;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;
import com.github.ustc_zzzz.fmltutor.entity.EntityGoldenEgg;
import com.github.ustc_zzzz.fmltutor.inventory.GuiElementLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemGoldenEgg extends Item
{
    public ItemGoldenEgg()
    {
        super();
        this.setUnlocalizedName("goldenEgg");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }
        if (!worldIn.isRemote)
        {
            if (playerIn.isSneaking())
            {
                BlockPos pos = playerIn.getPosition();
                int id = GuiElementLoader.GUI_DEMO;
                playerIn.openGui(FMLTutor.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            worldIn.spawnEntityInWorld(new EntityGoldenEgg(worldIn, playerIn));
        }
        return itemStackIn;
    }
}
