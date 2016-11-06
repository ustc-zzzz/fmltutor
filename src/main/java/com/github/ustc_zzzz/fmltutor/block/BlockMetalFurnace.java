package com.github.ustc_zzzz.fmltutor.block;

import java.util.List;

import javax.vecmath.Matrix4f;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.creativetab.CreativeTabsLoader;
import com.github.ustc_zzzz.fmltutor.inventory.GuiElementLoader;
import com.github.ustc_zzzz.fmltutor.tileentity.TileEntityMetalFurnace;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.client.model.obj.OBJModel.OBJState;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class BlockMetalFurnace extends BlockContainer
{
    public static enum EnumMaterial implements IStringSerializable
    {
        IRON("iron"), GOLD("gold");

        private String name;

        private EnumMaterial(String material)
        {
            this.name = material;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        @Override
        public String toString()
        {
            return this.name;
        }
    }

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool BURNING = PropertyBool.create("burning");
    public static final PropertyEnum<EnumMaterial> MATERIAL = PropertyEnum.create("material", EnumMaterial.class);

    public BlockMetalFurnace()
    {
        super(Material.iron);
        this.setUnlocalizedName("metalFurnace");
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(BURNING, Boolean.FALSE).withProperty(MATERIAL, EnumMaterial.IRON));
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            int id = GuiElementLoader.GUI_METAL_FURNACE;
            playerIn.openGui(FMLTutor.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(MATERIAL).ordinal() << 3;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        IBlockState origin = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 8));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        Boolean burning = Boolean.valueOf((meta & 4) != 0);
        EnumMaterial material = EnumMaterial.values()[meta >> 3];
        return this.getDefaultState().withProperty(FACING, facing).withProperty(BURNING, burning).withProperty(MATERIAL,
                material);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int burning = state.getValue(BURNING).booleanValue() ? 4 : 0;
        int material = state.getValue(MATERIAL).ordinal() << 3;
        return facing | burning | material;
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        IExtendedBlockState oldState = (IExtendedBlockState) state;
        TRSRTransformation transform = new TRSRTransformation(state.getValue(BlockMetalFurnace.FACING));
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityMetalFurnace)
        {
            Matrix4f matrix = new Matrix4f();
            matrix.rotY(((TileEntityMetalFurnace) te).getRotation());
            transform = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(matrix)).compose(transform);
        }
        OBJState objState = new OBJState(Lists.newArrayList(OBJModel.Group.ALL), true, transform);
        return oldState.withProperty(OBJModel.OBJProperty.instance, objState);
    }

    @Override
    protected BlockState createBlockState()
    {
        return new ExtendedBlockState(this, new IProperty<?>[]
        { FACING, BURNING, MATERIAL }, new IUnlistedProperty<?>[]
        { OBJModel.OBJProperty.instance });
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMetalFurnace();
    }

    @Override
    public int getRenderType()
    {
        return 3;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityMetalFurnace te = (TileEntityMetalFurnace) worldIn.getTileEntity(pos);

        IItemHandler up = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        IItemHandler down = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        for (int i = up.getSlots() - 1; i >= 0; --i)
        {
            if (up.getStackInSlot(i) != null)
            {
                Block.spawnAsEntity(worldIn, pos, up.getStackInSlot(i));
                ((IItemHandlerModifiable) up).setStackInSlot(i, null);
            }
        }

        for (int i = down.getSlots() - 1; i >= 0; --i)
        {
            if (down.getStackInSlot(i) != null)
            {
                Block.spawnAsEntity(worldIn, pos, down.getStackInSlot(i));
                ((IItemHandlerModifiable) down).setStackInSlot(i, null);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
}
