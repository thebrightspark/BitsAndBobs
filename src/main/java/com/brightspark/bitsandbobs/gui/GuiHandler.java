package com.brightspark.bitsandbobs.gui;

import com.brightspark.bitsandbobs.init.BABBlocks;
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
        if(block == BABBlocks.blockHealing)
            return new ContainerHammerCraft(player.inventory, world, x, y, z);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

        //Client side - returns intance of the gui
        if(block == BABBlocks.blockHealing)
            return new GuiHammerCraft(player.inventory, world, x, y, z);
        return null;
    }
}
