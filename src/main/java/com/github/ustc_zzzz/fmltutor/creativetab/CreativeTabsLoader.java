package com.github.ustc_zzzz.fmltutor.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsLoader
{
    public static CreativeTabs tabFMLTutor;

    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
        tabFMLTutor = new CreativeTabsFMLTutor();
    }
}
