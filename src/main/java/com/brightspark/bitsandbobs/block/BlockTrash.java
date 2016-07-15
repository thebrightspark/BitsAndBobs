package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Mark on 28/06/2016.
 */
public class BlockTrash extends BABBlockContainer
{
    public BlockTrash()
    {
        super(Material.ROCK, Names.Blocks.TRASH);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        TileTrash te = new TileTrash();
        te.setDefaultName(getRegistryName().getResourcePath());
        return te;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Check if the face of a block should block rendering.
     *
     * Faces which are fully opaque should return true, faces with transparency
     * or faces which do not span the full size of the block should return false.
     *
     * @param state The current block state
     * @param world The current world
     * @param pos Block position in world
     * @param face The side to check
     * @return True if the block is opaque on the specified side.
     */
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return face == EnumFacing.UP || face == EnumFacing.DOWN;
    }
}
