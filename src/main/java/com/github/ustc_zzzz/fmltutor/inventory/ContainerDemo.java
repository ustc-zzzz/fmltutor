package com.github.ustc_zzzz.fmltutor.inventory;

import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDemo extends Container
{
    private ItemStackHandler items = new ItemStackHandler(4);

    protected Slot goldSlot;
    protected Slot diamondSlot;
    protected Slot emeraldSlot;
    protected Slot ironSlot;

    public ContainerDemo(EntityPlayer player)
    {
        super();

        this.addSlotToContainer(this.goldSlot = new SlotItemHandler(items, 0, 38 + 0 * 32, 20)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack != null && stack.getItem() == Items.gold_ingot && super.isItemValid(stack);
            }

            @Override
            public int getItemStackLimit(ItemStack stack)
            {
                return 16;
            }
        });

        this.addSlotToContainer(this.diamondSlot = new SlotItemHandler(items, 1, 38 + 1 * 32, 20)
        {
            {
                this.putStack(new ItemStack(Items.diamond, 64));
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return false;
            }
        });

        this.addSlotToContainer(this.emeraldSlot = new SlotItemHandler(items, 2, 38 + 2 * 32, 20)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return stack != null && stack.getItem() == Items.emerald && super.isItemValid(stack);
            }

            @Override
            public void onSlotChanged()
            {
                ItemStack stack = this.getStack();
                int amount = stack == null ? 64 : 64 - stack.stackSize;
                ContainerDemo.this.diamondSlot.putStack(amount == 0 ? null : new ItemStack(Items.diamond, amount));
                super.onSlotChanged();
            }
        });

        this.addSlotToContainer(this.ironSlot = new SlotItemHandler(items, 3, 38 + 3 * 32, 20)
        {
            {
                this.putStack(new ItemStack(Items.iron_ingot, 64));
            }

            @Override
            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 109));
        }
    }

    public Slot getIronSlot()
    {
        return this.ironSlot;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (playerIn.isServerWorld())
        {
            ItemStack goldStack = this.goldSlot.getStack();
            if (goldStack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(goldStack, false);
                this.goldSlot.putStack(null);
            }
            ItemStack emeraldStack = this.emeraldSlot.getStack();
            if (emeraldStack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(emeraldStack, false);
                this.emeraldSlot.putStack(null);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
        {
            return null;
        }

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        boolean isMerged = false;

        if (index == 0 || index == 2)
        {
            isMerged = mergeItemStack(newStack, 4, 40, true);
        }
        else if (index >= 4 && index < 31)
        {
            isMerged = !goldSlot.getHasStack() && newStack.stackSize <= 16 && mergeItemStack(newStack, 0, 1, false)
                    || !emeraldSlot.getHasStack() && mergeItemStack(newStack, 2, 3, false)
                    || mergeItemStack(newStack, 31, 40, false);
        }
        else if (index >= 31 && index < 40)
        {
            isMerged = !goldSlot.getHasStack() && newStack.stackSize <= 16 && mergeItemStack(newStack, 0, 1, false)
                    || !emeraldSlot.getHasStack() && mergeItemStack(newStack, 2, 3, false)
                    || mergeItemStack(newStack, 4, 31, false);
        }

        if (!isMerged)
        {
            return null;
        }

        if (newStack.stackSize == 0)
        {
            slot.putStack(null);
        }
        else
        {
            slot.onSlotChanged();
        }

        slot.onPickupFromSlot(playerIn, newStack);

        return oldStack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return new ItemStack(ItemLoader.goldenEgg).isItemEqual(playerIn.getCurrentEquippedItem());
    }
}
