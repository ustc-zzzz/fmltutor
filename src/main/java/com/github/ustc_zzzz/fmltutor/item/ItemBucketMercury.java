package com.github.ustc_zzzz.fmltutor.item;

import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;
import com.github.ustc_zzzz.fmltutor.fluid.FluidLoader;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemBucketMercury extends ItemBucket
{
    public ItemBucketMercury()
    {
        super(BlockLoader.fluidMercury);
        this.setContainerItem(Items.bucket);
        this.setUnlocalizedName("bucketMercury");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        FluidContainerRegistry.registerFluidContainer(FluidLoader.fluidMercury, new ItemStack(this),
                FluidContainerRegistry.EMPTY_BUCKET);
    }
}
