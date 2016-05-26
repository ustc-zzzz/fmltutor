package com.github.ustc_zzzz.fmltutor.entity;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.client.entity.EntityRenderFactory;
import com.github.ustc_zzzz.fmltutor.client.entity.render.RenderGoldenChicken;
import com.github.ustc_zzzz.fmltutor.client.entity.render.RenderGoldenEgg;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLoader
{
    private static int nextID = 0;

    public EntityLoader(FMLPreInitializationEvent event)
    {
        registerEntity(EntityGoldenChicken.class, "GoldenChicken", 80, 3, true);
        registerEntityEgg(EntityGoldenChicken.class, 0xffff66, 0x660000);
        registerEntitySpawn(EntityGoldenChicken.class, 8, 2, 4, EnumCreatureType.CREATURE, BiomeGenBase.plains,
                BiomeGenBase.desert);
        registerEntity(EntityGoldenEgg.class, "GoldenEgg", 64, 10, true);
    }

    private static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary)
    {
        EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
            int updateFrequency, boolean sendsVelocityUpdates)
    {
        EntityRegistry.registerModEntity(entityClass, name, nextID++, FMLTutor.instance, trackingRange, updateFrequency,
                sendsVelocityUpdates);
    }

    private static void registerEntitySpawn(Class<? extends Entity> entityClass, int spawnWeight, int min,
            int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
    {
        if (EntityLiving.class.isAssignableFrom(entityClass))
        {
            Class<? extends EntityLiving> entityLivingClass = entityClass.asSubclass(EntityLiving.class);
            EntityRegistry.addSpawn(entityLivingClass, spawnWeight, min, max, typeOfCreature, biomes);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerEntityRender(EntityGoldenChicken.class, RenderGoldenChicken.class);
        registerEntityRender(EntityGoldenEgg.class, RenderGoldenEgg.class);
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class<? extends Render<T>> render)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }
}
