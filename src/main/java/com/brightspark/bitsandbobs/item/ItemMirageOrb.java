package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.message.MessageSpawnGhostOnServer;
import com.brightspark.bitsandbobs.reference.Names;
import com.brightspark.bitsandbobs.util.LogHelper;
import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMirageOrb extends ItemCooldownBasic
{
    public ItemMirageOrb()
    {
        super(Names.Items.MIRAGE_ORB, 1200);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return NBTHelper.getInt(stack, KEY_COOLDOWN) > 0;
    }

    @Override
    public boolean doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        //Spawn ghost of player on server
        if(world.isRemote && player instanceof AbstractClientPlayer)
        {
            MessageSpawnGhostOnServer message = new MessageSpawnGhostOnServer();
            message.playerName = player.getName();
            message.resourceLocation = ((AbstractClientPlayer) player).getLocationSkin();
            message.rotationYaw = player.rotationYaw;
            message.rotationPitch = player.rotationPitch;
            message.rotationYawHead = player.rotationYawHead;
            message.renderYawOffset = player.renderYawOffset;
            message.swingProgress = player.swingProgress;
            message.limbSwing = player.limbSwing;
            message.limbSwingAmount = player.limbSwingAmount;
            message.isSneaking = player.isSneaking();
            message.isSwingInProgress = player.isSwingInProgress;
            message.swingProgressInt = player.swingProgressInt;
            message.swingingHand = player.swingingHand;
            message.handSide = player.getPrimaryHand();
            BitsAndBobs.NETWORK.sendToServer(message);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(I18n.format(TOOLTIP + "1"));
        list.add(I18n.format(TOOLTIP + "2"));
        int cooldown = NBTHelper.getInt(stack, KEY_COOLDOWN);
        if(cooldown > 0)
            list.add(Math.round((float) cooldown / 20f) + I18n.format(TOOLTIP + "3"));
    }
}
