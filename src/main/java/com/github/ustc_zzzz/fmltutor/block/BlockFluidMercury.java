package com.github.ustc_zzzz.fmltutor.block;

import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;
import com.github.ustc_zzzz.fmltutor.fluid.FluidLoader;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidMercury extends BlockFluidClassic
{
    public BlockFluidMercury()
    {
        super(FluidLoader.fluidMercury, Material.water);
        this.setUnlocalizedName("fluidMercury");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }
}
