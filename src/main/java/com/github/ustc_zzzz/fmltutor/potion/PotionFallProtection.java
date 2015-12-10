package com.github.ustc_zzzz.fmltutor.potion;

import com.github.ustc_zzzz.fmltutor.FMLTutor;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionFallProtection extends Potion
{
    private static final ResourceLocation res = new ResourceLocation(FMLTutor.MODID + ":" + "textures/gui/potion.png");

    public PotionFallProtection()
    {
        super(new ResourceLocation(FMLTutor.MODID + ":" + "fall_protection"), false, 0x7F0000);
        this.setPotionName("potion.fallProtection");
        // this.setIconIndex(0, 0);
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        mc.getTextureManager().bindTexture(PotionFallProtection.res);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }
}
