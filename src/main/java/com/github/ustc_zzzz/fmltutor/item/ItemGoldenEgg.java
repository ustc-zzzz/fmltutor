package com.github.ustc_zzzz.fmltutor.item;

import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemGoldenEgg extends Item
{
    public ItemGoldenEgg()
    {
        super();
        this.setUnlocalizedName("goldenEgg");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }
}
