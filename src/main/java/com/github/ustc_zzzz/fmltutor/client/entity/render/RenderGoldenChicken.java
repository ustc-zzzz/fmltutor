package com.github.ustc_zzzz.fmltutor.client.entity.render;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.client.entity.model.ModelGoldenChicken;
import com.github.ustc_zzzz.fmltutor.entity.EntityGoldenChicken;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGoldenChicken extends RenderLiving<EntityGoldenChicken>
{
    private static final ResourceLocation GOLDEN_CHICKEN_TEXTURE = new ResourceLocation(
            FMLTutor.MODID + ":" + "textures/entity/golden_chicken.png");

    public RenderGoldenChicken(RenderManager renderManager)
    {
        super(renderManager, new ModelGoldenChicken(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityGoldenChicken entity, float partialTickTime)
    {
        GlStateManager.scale(2.5F, 2.5F, 2.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGoldenChicken entity)
    {
        return RenderGoldenChicken.GOLDEN_CHICKEN_TEXTURE;
    }

    @Override
    public void doRender(EntityGoldenChicken entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}