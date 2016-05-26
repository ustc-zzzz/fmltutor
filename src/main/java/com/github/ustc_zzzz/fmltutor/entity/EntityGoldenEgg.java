package com.github.ustc_zzzz.fmltutor.entity;

import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGoldenEgg extends EntityThrowable
{
    public EntityGoldenEgg(World worldIn)
    {
        super(worldIn);
    }

    public EntityGoldenEgg(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityGoldenEgg(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (!this.worldObj.isRemote)
        {
            if (movingObjectPosition.entityHit instanceof EntityChicken)
            {
                EntityChicken originalChicken = (EntityChicken) movingObjectPosition.entityHit;
                EntityGoldenChicken goldenChicken = new EntityGoldenChicken(this.worldObj);
                goldenChicken.setGrowingAge(originalChicken.getGrowingAge());
                goldenChicken.setLocationAndAngles(originalChicken.posX, originalChicken.posY, originalChicken.posZ,
                        originalChicken.rotationYaw, originalChicken.rotationPitch);
                originalChicken.setDead();
                this.worldObj.spawnEntityInWorld(goldenChicken);
            }
            else
            {
                this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ,
                        new ItemStack(ItemLoader.goldenEgg)));
            }
            this.setDead();
        }
    }
}
