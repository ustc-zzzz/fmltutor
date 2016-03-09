package com.github.ustc_zzzz.fmltutor.entity;

import com.github.ustc_zzzz.fmltutor.FMLTutor;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityLoader
{
    private static int nextID = 0;

    public EntityLoader(FMLPreInitializationEvent event)
    {
        registerEntity(EntityGoldenChicken.class, "GoldenChicken", 80, 3, true);
        registerEntityEgg(EntityGoldenChicken.class, 0xffff66, 0x660000);
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
}
