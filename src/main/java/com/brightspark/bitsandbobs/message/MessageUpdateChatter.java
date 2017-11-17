package com.brightspark.bitsandbobs.message;

import com.brightspark.bitsandbobs.tileentity.TileChatter;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateChatter implements IMessage
{
    private BlockPos pos;
    private String message;

    public MessageUpdateChatter() {}

    public MessageUpdateChatter(BlockPos pos, String message)
    {
        this.pos = pos;
        this.message = message;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        message = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        ByteBufUtils.writeUTF8String(buf, message);
    }

    public static class Handler implements IMessageHandler<MessageUpdateChatter, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageUpdateChatter message, final MessageContext ctx)
        {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().player.world;
            mainThread.addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    WorldServer server = (WorldServer) ctx.getServerHandler().player.world;
                    TileEntity te = server.getTileEntity(message.pos);
                    if(te instanceof TileChatter)
                        ((TileChatter) te).setMessage(message.message);
                }
            });
            return null;
        }
    }
}
