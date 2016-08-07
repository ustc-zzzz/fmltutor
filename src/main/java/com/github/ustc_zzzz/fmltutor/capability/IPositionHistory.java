package com.github.ustc_zzzz.fmltutor.capability;

import net.minecraft.util.Vec3;

public interface IPositionHistory
{
    public Vec3[] getHistories();

    public void setHistories(Vec3[] position);

    public void pushHistory(Vec3 position);
}
