package com.brightspark.bitsandbobs.block;

import com.brightspark.bitsandbobs.tileentity.TileChatter;
import com.brightspark.bitsandbobs.util.CommonUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockChatter extends BABBlockContainer
{
    public BlockChatter()
    {
        super("chatter");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileChatter();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        //TODO: Only send the message when a redstone pulse is received
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);
            if(te instanceof TileChatter)
                ((TileChatter) te).sendMessage();
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote && !player.isSneaking())
            CommonUtils.openGui(player, world, pos);
        return true;
    }
}
