package com.github.ustc_zzzz.fmltutor.worldgen;

import net.minecraft.util.BlockPos;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldGeneratorLoader
{
    private static WorldGenerator generatorGlowstone = new WorldGeneratorGlowstone();

    private BlockPos pos;

    public WorldGeneratorLoader()
    {
        MinecraftForge.ORE_GEN_BUS.register(this);
    }

    @SubscribeEvent
    public void onOreGenPost(OreGenEvent.Post event)
    {
        if (!event.pos.equals(this.pos))
        {
            this.pos = event.pos;
            generatorGlowstone.generate(event.world, event.rand, event.pos);
        }
    }

    @SubscribeEvent
    public void onOreGenGenerateMinable(OreGenEvent.GenerateMinable event)
    {
        if (event.type == OreGenEvent.GenerateMinable.EventType.ANDESITE)
        {
            event.setResult(Event.Result.DENY);
        }
    }
}
