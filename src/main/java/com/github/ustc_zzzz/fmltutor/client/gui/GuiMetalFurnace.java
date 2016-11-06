package com.github.ustc_zzzz.fmltutor.client.gui;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.inventory.ContainerMetalFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMetalFurnace extends GuiContainer
{
    private static final String TEXTURE_PATH = FMLTutor.MODID + ":" + "textures/gui/container/gui_metal_furnace.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);

    protected ContainerMetalFurnace inventory;

    private int totalBurnTime;

    public GuiMetalFurnace(ContainerMetalFurnace inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 156;
        this.inventory = inventorySlotsIn;
        this.totalBurnTime = inventorySlotsIn.getTotalBurnTime();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        int burnTime = this.inventory.getBurnTime();
        int textureWidth = 1 + (int) Math.ceil(22.0 * burnTime / this.totalBurnTime);
        this.drawTexturedModalRect(offsetX + 79, offsetY + 30, 0, 156, textureWidth, 17);
    }
}
