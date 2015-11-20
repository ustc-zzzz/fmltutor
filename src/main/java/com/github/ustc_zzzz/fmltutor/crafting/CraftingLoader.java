package com.github.ustc_zzzz.fmltutor.crafting;

import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;
import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingLoader
{
    public CraftingLoader()
    {
        registerRecipe();
        registerSmelting();
        registerFuel();
    }

    private static void registerRecipe()
    {
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.goldenEgg), new Object[]
        {
                "###", "#*#", "###", '#', Items.gold_ingot, '*', Items.egg
        });
        GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.grassBlock), new Object[]
        {
                "##", "##", '#', Blocks.vine
        });
        GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.redstonePickaxe), new Object[]
        {
                "###", " * ", " * ", '#', Items.redstone, '*', Items.stick
        });
        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.vine, 4), BlockLoader.grassBlock);
    }

    private static void registerSmelting()
    {
        GameRegistry.addSmelting(BlockLoader.grassBlock, new ItemStack(Items.coal), 0.5F);
    }

    private static void registerFuel()
    {
        GameRegistry.registerFuelHandler(new IFuelHandler()
        {
            @Override
            public int getBurnTime(ItemStack fuel)
            {
                return Items.diamond != fuel.getItem() ? 0 : Math.max(0, ConfigLoader.diamondBurnTime) * 20;
            }
        });
    }
}
