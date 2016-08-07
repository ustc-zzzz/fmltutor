package com.github.ustc_zzzz.fmltutor.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CapabilityLoader
{
    @CapabilityInject(IPositionHistory.class)
    public static Capability<IPositionHistory> positionHistory;

    public CapabilityLoader(FMLPreInitializationEvent event)
    {
        CapabilityManager.INSTANCE.register(IPositionHistory.class, new CapabilityPositionHistory.Storage(),
                CapabilityPositionHistory.Implementation.class);
    }
}
