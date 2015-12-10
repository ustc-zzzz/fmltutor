package com.github.ustc_zzzz.fmltutor.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PotionLoader
{
    public static Potion potionFallProtection;

    public PotionLoader(FMLPreInitializationEvent event)
    {
        potionFallProtection = new PotionFallProtection();
    }
}
