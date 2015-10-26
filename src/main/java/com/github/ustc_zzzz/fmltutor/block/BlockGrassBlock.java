package com.github.ustc_zzzz.fmltutor.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockGrassBlock extends Block
{
    public BlockGrassBlock()
    {
        super(Material.ground);
        this.setUnlocalizedName("grassBlock");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
