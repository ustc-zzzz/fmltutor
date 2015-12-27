package com.github.ustc_zzzz.fmltutor.achievement;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.block.BlockLoader;
import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.init.Blocks;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

public class AchievementLoader
{
    public static Achievement worseThanPig = new Achievement("achievement.fmltutor.worseThanPig",
            "fmltutor.worseThanPig", 5, -4, ItemLoader.goldenEgg, AchievementList.buildSword);
    public static Achievement buildGrassBlock = new Achievement("achievement.fmltutor.buildGrassBlock",
            "fmltutor.buildGrassBlock", 0, 0, Blocks.vine, null);
    public static Achievement explosionFromGrassBlock = new Achievement("achievement.fmltutor.explosionFromGrassBlock",
            "fmltutor.explosionFromGrassBlock", 2, -1, BlockLoader.grassBlock, buildGrassBlock);

    public static AchievementPage pageFMLTutor = new AchievementPage(FMLTutor.NAME, buildGrassBlock,
            explosionFromGrassBlock);

    public AchievementLoader()
    {
        worseThanPig.setSpecial().registerStat();
        buildGrassBlock.initIndependentStat().registerStat();
        explosionFromGrassBlock.setSpecial().registerStat();

        AchievementPage.registerAchievementPage(pageFMLTutor);
    }
}
