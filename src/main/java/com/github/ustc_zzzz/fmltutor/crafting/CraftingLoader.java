package com.github.ustc_zzzz.fmltutor.crafting;

import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;
import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

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
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.goldenEgg), new Object[]
        {
                "###", "#*#", "###", '#', "ingotGold", '*', Items.egg
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.redstonePickaxe), new Object[]
        {
                "###", " * ", " * ", '#', "dustRedstone", '*', "stickWood"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.redstoneApple), new Object[]
        {
                "###", "#*#", "###", '#', "dustRedstone", '*', Items.apple
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.redstoneHelmet), new Object[]
        {
                "###", "# #", '#', "dustRedstone"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.redstoneChestplate), new Object[]
        {
                "# #", "###", "###", '#', "dustRedstone"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.redstoneLeggings), new Object[]
        {
                "###", "# #", "# #", '#', "dustRedstone"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemLoader.redstoneBoots), new Object[]
        {
                "# #", "# #", '#', "dustRedstone"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockLoader.metalFurnace, 1, 0), new Object[]
        {
                "###", "# #", "###", '#', "blockIron"
        }));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockLoader.metalFurnace, 1, 8), new Object[]
        {
                "###", "# #", "###", '#', "blockGold"
        }));
        GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.grassBlock), new Object[]
        {
                "##", "##", '#', Blocks.vine
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
