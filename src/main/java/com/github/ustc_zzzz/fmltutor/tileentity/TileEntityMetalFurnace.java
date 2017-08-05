package com.github.ustc_zzzz.fmltutor.tileentity;

import com.github.ustc_zzzz.fmltutor.block.BlockMetalFurnace;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")
public class TileEntityMetalFurnace extends TileEntity implements ITickable, IEnergySink
{
    protected double rotationDegree = 0;

    private boolean updated = false;

    protected double receivedEnergyUnit = 0;
    protected int burnTime = 0;

    protected ItemStackHandler upInventory = new ItemStackHandler();
    protected ItemStackHandler downInventory = new ItemStackHandler();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            @SuppressWarnings("unchecked")
            T result = (T) (facing == EnumFacing.DOWN ? downInventory : upInventory);
            return result;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.upInventory.deserializeNBT(compound.getCompoundTag("UpInventory"));
        this.downInventory.deserializeNBT(compound.getCompoundTag("DownInventory"));
        this.receivedEnergyUnit = compound.getDouble("ReceivedEnergyUnit");
        this.burnTime = compound.getInteger("BurnTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("UpInventory", this.upInventory.serializeNBT());
        compound.setTag("DownInventory", this.downInventory.serializeNBT());
        compound.setDouble("ReceivedEnergyUnit", this.receivedEnergyUnit);
        compound.setInteger("BurnTime", this.burnTime);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        if (!this.worldObj.isRemote && Loader.isModLoaded("IC2"))
        {
            this.onIC2MachineUnloaded();
        }
    }

    @Override
    public void update()
    {
        if (!this.worldObj.isRemote)
        {
            if (!this.updated && Loader.isModLoaded("IC2"))
            {
                this.onIC2MachineLoaded();
                this.updated = true;
            }

            ItemStack itemStack = upInventory.extractItem(0, 1, true);
            IBlockState state = this.worldObj.getBlockState(pos);

            if (itemStack != null)
            {
                ItemStack furnaceRecipeResult = FurnaceRecipes.instance().getSmeltingResult(itemStack);
                if (furnaceRecipeResult != null && downInventory.insertItem(0, furnaceRecipeResult, true) == null)
                {
                    double requiredEnergyPerTick = this.getRequiredEnergyPerTick();
                    if (this.receivedEnergyUnit >= requiredEnergyPerTick)
                    {
                        this.receivedEnergyUnit -= requiredEnergyPerTick;

                        this.worldObj.setBlockState(pos, state.withProperty(BlockMetalFurnace.BURNING, Boolean.TRUE));

                        int burnTotalTime = this.getTotalBurnTime();

                        if (++this.burnTime >= burnTotalTime)
                        {
                            this.burnTime = 0;
                            itemStack = upInventory.extractItem(0, 1, false);
                            furnaceRecipeResult = FurnaceRecipes.instance().getSmeltingResult(itemStack).copy();
                            downInventory.insertItem(0, furnaceRecipeResult, false);
                            this.markDirty();
                        }
                    }
                }
            }
            else
            {
                this.burnTime = 0;
                this.worldObj.setBlockState(pos, state.withProperty(BlockMetalFurnace.BURNING, Boolean.FALSE));
            }
        }
        else
        {
            IBlockState blockState = this.worldObj.getBlockState(this.pos);
            boolean burning = blockState.getProperties().containsKey(BlockMetalFurnace.BURNING)
                    && blockState.getValue(BlockMetalFurnace.BURNING).booleanValue();
            if (burning || this.rotationDegree > 0)
            {
                this.rotationDegree += 11.25;
                if (this.rotationDegree >= 360.0)
                {
                    this.rotationDegree -= 360.0;
                }
                this.worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
            }
        }
    }

    public float getRotation()
    {
        return (float) (this.rotationDegree * Math.PI / 180);
    }

    public int getBurnTime()
    {
        return this.burnTime;
    }

    public int getTotalBurnTime()
    {
        IBlockState state = this.worldObj.getBlockState(this.pos);
        switch (state.getValue(BlockMetalFurnace.MATERIAL))
        {
        case GOLD:
            return 100;
        case IRON:
            return 150;
        default:
            return 200;
        }
    }

    public double getRequiredEnergyPerTick()
    {
        return 4.5;
    }

    public double getEnergyCapacity()
    {
        return 4096;
    }

    @Override
    public double getDemandedEnergy()
    {
        return Math.max(0, this.getEnergyCapacity() - this.receivedEnergyUnit);
    }

    @Override
    public int getSinkTier()
    {
        return 2;
    }

    @Override
    public double injectEnergy(EnumFacing directionFrom, double amount, double voltage)
    {
        this.receivedEnergyUnit += amount;
        return 0;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing enumFacing)
    {
        return true;
    }

    @Optional.Method(modid = "IC2")
    private void onIC2MachineLoaded()
    {
        MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
    }

    @Optional.Method(modid = "IC2")
    private void onIC2MachineUnloaded()
    {
        MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
    }
}
