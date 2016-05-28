package com.github.ustc_zzzz.fmltutor.entity;

import java.lang.ref.WeakReference;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class FakePlayerLoader
{
    private static GameProfile gameProfile;
    private static WeakReference<EntityPlayerMP> fakePlayer;

    public FakePlayerLoader()
    {
        gameProfile = new GameProfile(UUID.fromString("C3F2EF82-E759-53EA-9D69-0D6E394A00B8"), "[FMLTutor]");
        fakePlayer = new WeakReference<EntityPlayerMP>(null);
    }

    public static WeakReference<EntityPlayerMP> getFakePlayer(WorldServer server)
    {
        if (fakePlayer.get() == null)
        {
            fakePlayer = new WeakReference<EntityPlayerMP>(FakePlayerFactory.get(server, gameProfile));
        }
        else
        {
            fakePlayer.get().worldObj = server;
        }
        return fakePlayer;
    }
}
