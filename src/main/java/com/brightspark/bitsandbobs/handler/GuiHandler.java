package com.brightspark.bitsandbobs.handler;

import com.brightspark.bitsandbobs.block.BlockChatter;
import com.brightspark.bitsandbobs.block.BlockHealing;
import com.brightspark.bitsandbobs.block.BlockTrash;
import com.brightspark.bitsandbobs.gui.*;
import com.brightspark.bitsandbobs.tileentity.TileChatter;
import com.brightspark.bitsandbobs.tileentity.TileHealing;
import com.brightspark.bitsandbobs.tileentity.TileTrash;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
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
            BlockPos pos = new BlockPos(x, y, z);
            Block block = world.getBlockState(pos).getBlock();
            TileEntity te = world.getTileEntity(pos);

            //Server side - returns instance of the container
            if(block instanceof BlockHealing)
                return new ContainerBlockHealing(player.inventory, (TileHealing) te);
            else if(block instanceof BlockTrash)
                return new ContainerBlockTrash(player.inventory, (TileTrash) te);
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
            BlockPos pos = new BlockPos(x, y, z);
            Block block = world.getBlockState(pos).getBlock();
            TileEntity te = world.getTileEntity(pos);

            //Client side - returns instance of the gui
            if(block instanceof BlockHealing)
                return new GuiBlockHealing(player.inventory, (TileHealing) te);
            else if(block instanceof BlockTrash)
                return new GuiBlockTrash(player.inventory, (TileTrash) te);
            else if(block instanceof BlockChatter)
                return new GuiBlockChatter((TileChatter) te);
        }
        return null;
    }
}
