package com.github.ustc_zzzz.fmltutor;

import com.github.ustc_zzzz.fmltutor.api.FMLTutorRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.LinkedList;

public class FMLTutorRecipeManagerImpl extends FMLTutorRecipeManager
{
    public static final FMLTutorRecipeManagerImpl INSTANCE = new FMLTutorRecipeManagerImpl();

    private final LinkedList<Recipe> recipes = new LinkedList<Recipe>();

    @Override
    public void addRecipe(ItemStack input, ItemStack output)
    {
        this.recipes.addFirst(new Recipe(input, output));
    }

    @Override
    public ItemStack getResult(ItemStack input)
    {
        for (Recipe recipe : recipes)
        {
            if (recipe.input.isItemEqual(input))
            {
                return recipe.output;
            }
        }
        return FurnaceRecipes.instance().getSmeltingResult(input);
    }

    private static class Recipe
    {
        private final ItemStack input;
        private final ItemStack output;

        private Recipe(ItemStack input, ItemStack output)
        {
            this.input = input;
            this.output = output;
        }
    }
}
