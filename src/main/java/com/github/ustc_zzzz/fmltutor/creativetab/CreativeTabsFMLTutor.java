package com.github.ustc_zzzz.fmltutor.creativetab;

import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabsFMLTutor extends CreativeTabs
{
    public CreativeTabsFMLTutor()
    {
        super("fmltutor");
        this.setBackgroundImageName("fmltutor.png");
    }

    @Override
    public Item getTabIconItem()
    {
        return ItemLoader.goldenEgg;
    }

    @Override
    public boolean hasSearchBar()
    {
        return true;
    }
}
