package com.brightspark.bitsandbobs.tileentity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class TileChatter extends BABTileEntity
{
    private static final int range = 5;
    private String message = "";

    public void setMessage(@Nonnull String message)
    {
        this.message = message;
    }

    public void sendMessage()
    {
        if(!world.isRemote)
        {
            MinecraftServer server = world.getMinecraftServer();
            if(server != null)
                for(EntityPlayerMP player : server.getPlayerList().getPlayers())
                    if(player.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= range)
                        player.sendMessage(new TextComponentString(message));
        }
    }

    public String getMessage()
    {
        return message;
    }
}
