package com.github.ustc_zzzz.fmltutor.worldstorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class PositionWorldSavedData extends WorldSavedData
{
    private List<Vec3> positions = new ArrayList<Vec3>();
    private List<UUID> players = new ArrayList<UUID>();

    public PositionWorldSavedData(String name)
    {
        super(name);
    }

    public int size()
    {
        return players.size();
    }

    public Vec3 getPosition(int index)
    {
        return positions.get(index);
    }

    public UUID getPlayerUUID(int index)
    {
        return players.get(index);
    }

    public void add(Vec3 position, UUID player)
    {
        positions.add(position);
        players.add(player);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        positions.clear();
        players.clear();
        NBTTagList list = (NBTTagList) nbt.getTag("positions");
        if (list == null)
        {
            list = new NBTTagList();
        }
        for (int i = list.tagCount() - 1; i >= 0; --i)
        {
            NBTTagCompound compound = (NBTTagCompound) list.get(i);
            positions.add(new Vec3(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z")));
            players.add(UUID.fromString(compound.getString("player")));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList list = new NBTTagList();
        for (int i = players.size() - 1; i >= 0; --i)
        {
            Vec3 position = positions.get(i);
            UUID player = players.get(i);
            NBTTagCompound compound = new NBTTagCompound();
            compound.setDouble("x", position.xCoord);
            compound.setDouble("y", position.yCoord);
            compound.setDouble("z", position.zCoord);
            compound.setString("player", player.toString());
            list.appendTag(compound);
        }
        nbt.setTag("positions", list);
    }

    public static PositionWorldSavedData get(World world)
    {
        WorldSavedData data = world.getPerWorldStorage().loadData(PositionWorldSavedData.class, "FMLTutorPositions");
        if (data == null)
        {
            data = new PositionWorldSavedData("FMLTutorPositions");
            world.getPerWorldStorage().setData("FMLTutorPositions", data);
        }
        return (PositionWorldSavedData) data;
    }

    public static PositionWorldSavedData getGlobal(World world)
    {
        WorldSavedData data = world.getMapStorage().loadData(PositionWorldSavedData.class, "FMLTutorPositionsGlobal");
        if (data == null)
        {
            data = new PositionWorldSavedData("FMLTutorPositionsGlobal");
            world.getMapStorage().setData("FMLTutorPositionsGlobal", data);
        }
        return (PositionWorldSavedData) data;
    }
}
