package com.brightspark.bitsandbobs.message;

import com.brightspark.bitsandbobs.entity.EntityPlayerGhost;
import com.brightspark.bitsandbobs.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Mark on 20/07/2016.
 */
public class MessageSetClientGhostData implements IMessage
{
    private int ghostId;
    private float limbSwing;
    private float limbSwingAmount;

    public MessageSetClientGhostData() {}

    public MessageSetClientGhostData(int ghostId, float limbSwing, float limbSwingAmount)
    {
        this.ghostId = ghostId;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        ghostId = buf.readInt();
        limbSwing = buf.readFloat();
        limbSwingAmount = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(ghostId);
        buf.writeFloat(limbSwing);
        buf.writeFloat(limbSwingAmount);
    }

    public static class Handler implements IMessageHandler<MessageSetClientGhostData, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSetClientGhostData message, MessageContext ctx)
        {
            final IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    LogHelper.info("Setting ghost limb swing");
                    World world = Minecraft.getMinecraft().theWorld;
                    Entity entity = world.getEntityByID(message.ghostId);
                    if(!(entity instanceof EntityPlayerGhost))
                        return;
                    ((EntityPlayerGhost)entity).setLimbSwing(message.limbSwing, message.limbSwingAmount);
                }
            });
            return null;
        }
    }
}
