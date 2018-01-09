package com.github.ustc_zzzz.fmltutor.api;

import net.minecraft.item.ItemStack;

public abstract class FMLTutorRecipeManager
{
    public static final FMLTutorRecipeManager INSTANCE;

    static
    {
        try
        {
            Class<?> implClass = Class.forName("com.github.ustc_zzzz.fmltutor.FMLTutorRecipeManagerImpl");
            INSTANCE = (FMLTutorRecipeManager) implClass.getDeclaredField("INSTANCE").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot find implementation", e);
        }
    }

    public abstract void addRecipe(ItemStack input, ItemStack output);

    public abstract ItemStack getResult(ItemStack input);
}
