package com.github.ustc_zzzz.fmltutor.worldgen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class WorldGeneratorGlowstone extends WorldGenerator
{
    private final WorldGenerator glowstoneGenerator = new WorldGenMinable(Blocks.glowstone.getDefaultState(), 16);

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (TerrainGen.generateOre(world, rand, this, pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
        {
            for (int i = 0; i < 4; ++i)
            {
                int posX = pos.getX() + rand.nextInt(16);
                int posY = 16 + rand.nextInt(16);
                int posZ = pos.getZ() + rand.nextInt(16);
                BlockPos blockpos = new BlockPos(posX, posY, posZ);
                BiomeGenBase biomeGenBase = world.getBiomeGenForCoords(blockpos);
                if (biomeGenBase.getIntRainfall() < rand.nextInt(65536))
                {
                    glowstoneGenerator.generate(world, rand, blockpos);
                }
            }
        }
        return true;
    }
}
