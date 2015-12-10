package com.github.ustc_zzzz.fmltutor.common;

import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.crafting.CraftingLoader;
import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;
import com.github.ustc_zzzz.fmltutor.enchantment.EnchantmentLoader;
import com.github.ustc_zzzz.fmltutor.item.ItemLoader;
import com.github.ustc_zzzz.fmltutor.potion.PotionLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        new ConfigLoader(event);
        new CreativeTabsLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
        new PotionLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        new CraftingLoader();
        new EnchantmentLoader();
        new EventLoader();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
