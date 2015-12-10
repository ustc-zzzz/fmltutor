package com.github.ustc_zzzz.fmltutor.enchantment;

import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;

import net.minecraft.enchantment.Enchantment;

public class EnchantmentLoader
{
    public static Enchantment fireBurn;

    public EnchantmentLoader()
    {
        try
        {
            fireBurn = new EnchantmentFireBurn();
            Enchantment.addToBookList(fireBurn);
        }
        catch (Exception e)
        {
            ConfigLoader.logger().error(
                    "Duplicate or illegal enchantment id: {}, the registry of class '{}' will be skipped. ",
                    ConfigLoader.enchantmentFireBurn, EnchantmentFireBurn.class.getName());
        }
    }
}
