package com.brightspark.bitsandbobs.command;

import com.brightspark.bitsandbobs.reference.Config;
import com.brightspark.bitsandbobs.util.CommonUtils;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class CommandKill extends CommandBase
{
    @Override
    public String getName()
    {
        return Config.commandKillName;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "Usage:\n" +
                " " + getName() + " [radius] [filter]\n" +
                " Filter can be 'aggressive' (or 'a'), 'passive' (or 'p'), 'item' (or 'i'), or an entity ID.";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        List<String> filter = null;
        int radius = 0;
        if(args.length > 0)
        {
            if(args[0].equals("help"))
            {
                sender.sendMessage(new TextComponentString(getUsage(sender)));
                return;
            }

            int filterStart = 0;
            //Try get the radius, if specified
            try
            {
                radius = Math.abs(Integer.parseInt(args[0]));
                filterStart = 1;
            }
            catch(NumberFormatException e) {}

            //Get filters if there are any
            if(filterStart == 0 || args.length > 1)
                filter = Lists.newArrayList(Arrays.copyOfRange(args, filterStart, args.length));
        }

        int numKilled = 0;
        World world = server.getEntityWorld();
        BlockPos senderPos = sender.getPosition();
        for(int i = 0; i < world.loadedEntityList.size(); i++)
        {
            Entity entity = world.loadedEntityList.get(i);

            //Don't kill players or self
            if(entity instanceof EntityPlayer || entity.equals(sender))
                continue;

            //If radius defined, make sure entity is within the radius
            if(radius != 0 && senderPos.getDistance((int) entity.posX, (int) entity.posY, (int) entity.posZ) > radius)
                continue;

            //If no filter, then just kill all mobs
            if(filter == null)
            {
                //With no filter, only kill living mobs
                if(!(entity instanceof EntityLivingBase))
                    continue;
                entity.setDead();
                numKilled++;
            }
            //Kill mobs according to the filter
            else
            {
                String entityName = EntityList.getEntityString(entity);
                //Skip entities without a name
                if(entityName == null)
                    continue;
                entityName = entityName.toLowerCase();
                for(String filterName : filter)
                {
                    filterName = filterName.toLowerCase();
                    if(((filterName.equals("passive") || filterName.equals("p")) && entity instanceof IAnimals && !(entity instanceof IMob)) ||
                            ((filterName.equals("aggressive") || filterName.equals("a")) && entity instanceof IMob) ||
                            ((filterName.equals("item") || filterName.equals("i")) && entity instanceof EntityItem) ||
                            filterName.equals(entityName))
                    {
                        entity.setDead();
                        numKilled++;
                        break;
                    }
                }
            }
        }

        //Print to sender how many entities were killed
        if(!world.isRemote)
        {
            String[] resultText = Config.commandKillMessages;
            String message = resultText[world.rand.nextInt(resultText.length)];
            sender.sendMessage(new TextComponentString(String.format(message, numKilled)));
        }
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        List<String> list = Lists.newArrayList("help", "passive", "p", "aggressive", "a", "item", "i");
        List<String> entityNames = EntityList.getEntityNameList();
        CommonUtils.sortStringList(entityNames);
        list.addAll(entityNames);
        return getListOfStringsMatchingLastWord(args, list);
    }
}
