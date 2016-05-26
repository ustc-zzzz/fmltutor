package com.github.ustc_zzzz.fmltutor.item;

import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;
import com.github.ustc_zzzz.fmltutor.entity.EntityGoldenEgg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
            worldIn.spawnEntityInWorld(new EntityGoldenEgg(worldIn, playerIn));
        }
        return itemStackIn;
    }
}
