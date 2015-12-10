package com.github.ustc_zzzz.fmltutor.enchantment;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFireBurn extends Enchantment
{
    public EnchantmentFireBurn()
    {
        super(ConfigLoader.enchantmentFireBurn, new ResourceLocation(FMLTutor.MODID + ":" + "fire_burn"), 1,
                EnumEnchantmentType.DIGGER);
        this.setName("fireBurn");
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return super.canApplyTogether(ench) && ench.effectId != silkTouch.effectId && ench.effectId != fortune.effectId;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() == Items.shears ? true : super.canApply(stack);
    }
}
