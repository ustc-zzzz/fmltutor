package com.github.ustc_zzzz.fmltutor.fluid;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.common.ConfigLoader;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidLoader
{
    public static Fluid fluidMercury = new FluidMercury();

    public FluidLoader(FMLPreInitializationEvent event)
    {
        if (FluidRegistry.isFluidRegistered(fluidMercury))
        {
            ConfigLoader.logger().info("Found fluid {}, the registration is canceled. ", fluidMercury.getName());
            fluidMercury = FluidRegistry.getFluid(fluidMercury.getName());
        }
        else
        {
            FluidRegistry.registerFluid(fluidMercury);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerFluidRender((BlockFluidBase) BlockLoader.fluidMercury, "fluid_mercury");
    }

    @SideOnly(Side.CLIENT)
    private static void registerFluidRender(BlockFluidBase blockFluid, String blockStateName)
    {
        final String location = FMLTutor.MODID + ":" + blockStateName;
        final Item itemFluid = Item.getItemFromBlock(blockFluid);
        ModelLoader.setCustomMeshDefinition(itemFluid, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(location, "fluid");
            }
        });
        ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return new ModelResourceLocation(location, "fluid");
            }
        });
    }
}
