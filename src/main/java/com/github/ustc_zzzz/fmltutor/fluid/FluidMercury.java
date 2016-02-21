package com.github.ustc_zzzz.fmltutor.fluid;

import com.github.ustc_zzzz.fmltutor.FMLTutor;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidMercury extends Fluid
{
    public static final ResourceLocation still = new ResourceLocation(FMLTutor.MODID + ":" + "fluid/mercury_still");
    public static final ResourceLocation flowing = new ResourceLocation(FMLTutor.MODID + ":" + "fluid/mercury_flow");

    public FluidMercury()
    {
        super("mercury", FluidMercury.still, FluidMercury.flowing);
        this.setUnlocalizedName("fluidMercury");
        this.setDensity(13600);
        this.setViscosity(750);
        this.setLuminosity(0);
        this.setTemperature(300);
    }
}
