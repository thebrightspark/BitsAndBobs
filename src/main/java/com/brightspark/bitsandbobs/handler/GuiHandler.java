package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.block.BlockTrash;
import com.brightspark.bitsandbobs.gui.*;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID >= 0 && ID < EnumGuiID.values().length)
        {
            //Item GUI
            switch(EnumGuiID.values()[ID])
            {
                case AMMO_BELT:
                    return new ContainerAmmoBelt(player);
            }
        }
        else
        {
            //Block GUI
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

            //Server side - returns instance of the container
            if(block instanceof BlockHealing)
                return new ContainerBlockHealing(player.inventory, (TileHealing) world.getTileEntity(new BlockPos(x, y, z)));
            else if(block instanceof BlockTrash)
                return new ContainerBlockTrash(player.inventory, (TileTrash) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID >= 0 && ID < EnumGuiID.values().length)
        {
            //Item GUI
            switch(EnumGuiID.values()[ID])
            {
                case AMMO_BELT:
                    return new GuiAmmoBelt(player);
            }
        }
        else
        {
            //Block GUI
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

            //Client side - returns instance of the gui
            if(block instanceof BlockHealing)
                return new GuiBlockHealing(player.inventory, world, x, y, z);
            else if(block instanceof BlockTrash)
                return new GuiBlockTrash(player.inventory, world, x, y, z);
        }
        return null;
    }
}
