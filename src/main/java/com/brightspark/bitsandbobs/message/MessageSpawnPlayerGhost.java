package com.brightspark.bitsandbobs.message;

import com.brightspark.bitsandbobs.entity.EntityPlayerGhost;
import com.brightspark.bitsandbobs.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mark on 19/07/2016.
 */
public class MessageSpawnPlayerGhost implements IMessage
{
    private String playerName;
    private ResourceLocation resourceLocation;

    public MessageSpawnPlayerGhost() {}

    public MessageSpawnPlayerGhost(String playerDisplayName, ResourceLocation resLoc)
    {
        playerName = playerDisplayName;
        resourceLocation = resLoc;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerName = ByteBufUtils.readUTF8String(buf);
        resourceLocation = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, playerName);
        ByteBufUtils.writeUTF8String(buf, resourceLocation.toString());
    }

    public static class Handler implements IMessageHandler<MessageSpawnPlayerGhost, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSpawnPlayerGhost message, final MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    LogHelper.info("Spawning player ghost for " + message.playerName);

                    WorldServer server = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
                    EntityPlayer player = server.getPlayerEntityByName(message.playerName);
                    if(player == null)
                    {
                        LogHelper.warn("Player not found when trying to spawn ghost!");
                        return;
                    }

                    //Create ghost
                    EntityPlayerGhost ghost = new EntityPlayerGhost(server, player);
                    ghost.playerSkin = message.resourceLocation;
                    server.spawnEntityInWorld(ghost);

                    //Get nearby mobs
                    BlockPos playerPos = player.getPosition();
                    List<Entity> entities = server.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(playerPos).expandXyz(20));

                    //Set nearby attacking mobs to attack the ghost
                    for(Entity e : entities)
                    {
                        if(e instanceof EntityLiving && ((EntityLiving)e).getAttackTarget() != null && ((EntityLiving)e).getAttackTarget().equals(player))
                            //Set attacking target to the ghost
                            ((EntityLiving)e).setAttackTarget(ghost);
                    }
                }
            });
            return null; //No response
        }
    }
}