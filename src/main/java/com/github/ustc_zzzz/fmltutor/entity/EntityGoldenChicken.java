package com.github.ustc_zzzz.fmltutor.entity;

import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

public class EntityGoldenChicken extends EntityChicken
{
    public EntityGoldenChicken(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    @Override
    protected void dropFewItems(boolean arg1, int arg2)
    {
        if (this.rand.nextInt(10) == 0)
        {
            this.dropItem(ItemLoader.goldenEgg, 1);
        }
        super.dropFewItems(arg1, arg2);
    }
}
