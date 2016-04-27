package com.github.ustc_zzzz.fmltutor.entity;

import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGoldenChicken extends EntityChicken
{
    public static final IAttribute wingSpeed = new RangedAttribute(null, "fmltutor.GoldenChicken.wingSpeed", 1.5D, 0.0D,
            4.0D).setDescription("Wing Speed").setShouldWatch(true);

    public EntityGoldenChicken(World worldIn)
    {
        super(worldIn);
        this.setSize(1.2F, 1.8F);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("WingSpeedMultiplier", this.dataWatcher.getWatchableObjectByte(16));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.dataWatcher.updateObject(16, tagCompund.getByte("WingSpeedMultiplier"));
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (!super.interact(player))
        {
            byte b = this.dataWatcher.getWatchableObjectByte(16);
            this.dataWatcher.updateObject(16, new Byte((byte) ((b + 1) % 5)));
        }
        return true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getAttributeMap().registerAttribute(EntityGoldenChicken.wingSpeed);

        this.getEntityAttribute(EntityGoldenChicken.wingSpeed).setBaseValue(1 + this.rand.nextDouble());
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
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
