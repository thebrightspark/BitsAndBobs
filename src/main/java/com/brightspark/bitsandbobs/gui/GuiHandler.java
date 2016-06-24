package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.container.ContainerBlockHealing;
import com.brightspark.bitsandbobs.init.BABBlocks;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        //Server side - returns instance of the container
        if(block instanceof BlockHealing)
            return new ContainerBlockHealing(player.inventory, (TileHealing) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        //Client side - returns intance of the gui
        if(block instanceof BlockHealing)
            return new GuiBlockHealing(player.inventory, world, x, y, z);
        return null;
    }
}
