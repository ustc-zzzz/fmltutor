package com.github.ustc_zzzz.fmltutor.client.entity.render;

import com.github.ustc_zzzz.fmltutor.entity.EntityGoldenEgg;
import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class RenderGoldenEgg extends RenderSnowball<EntityGoldenEgg>
{
    public RenderGoldenEgg(RenderManager renderManagerIn)
    {
        super(renderManagerIn, ItemLoader.goldenEgg, Minecraft.getMinecraft().getRenderItem());
    }
}
