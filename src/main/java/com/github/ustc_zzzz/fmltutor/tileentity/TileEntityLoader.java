package com.github.ustc_zzzz.fmltutor.tileentity;

import com.github.ustc_zzzz.fmltutor.FMLTutor;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityLoader
{
    public TileEntityLoader(FMLPreInitializationEvent event)
    {
        registerTileEntity(TileEntityMetalFurnace.class, "MetalFurnace");
    }

    public void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, FMLTutor.MODID + ":" + id);
    }
}
