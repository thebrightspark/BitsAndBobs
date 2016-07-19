package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.BitsAndBobs;
import com.brightspark.bitsandbobs.message.MessageSpawnPlayerGhost;
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

/**
 * Created by Mark on 18/07/2016.
 */
public class ItemMirageOrb extends ItemBasic
{
    private final String TOOLTIP;
    private static final int MAX_COOLDOWN = 1200; //60 sec cooldown
    private static final String KEY_COOLDOWN = "cooldown";

    public ItemMirageOrb()
    {
        super(Names.Items.MIRAGE_ORB);
        TOOLTIP = getUnlocalizedName() + ".tooltip.";
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return NBTHelper.getInt(stack, KEY_COOLDOWN) > 0;
    }

    public static boolean isActive(ItemStack stack)
    {
        return NBTHelper.getInt(stack, KEY_COOLDOWN) > 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        int cooldown = NBTHelper.getInt(stack, KEY_COOLDOWN);
        if(cooldown > 0)
            //Item currently active
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);

        //Set cooldown
        NBTHelper.setInteger(stack, KEY_COOLDOWN, MAX_COOLDOWN);

        //Spawn ghost of player on server
        if(world.isRemote && player instanceof AbstractClientPlayer)
            BitsAndBobs.NETWORK_STRING.sendToServer(new MessageSpawnPlayerGhost(player.getName(), ((AbstractClientPlayer)player).getLocationSkin()));

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        int cooldown = NBTHelper.getInt(stack, KEY_COOLDOWN);
        if(cooldown > 0)
        {
            NBTHelper.setInteger(stack, KEY_COOLDOWN, --cooldown);
            LogHelper.info("Cooldown: " + cooldown);
        }
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

    /**
     * Determine if the player switching between these two item stacks
     * @param oldStack The old stack that was equipped
     * @param newStack The new stack
     * @param slotChanged If the current equipped slot was changed,
     *                    Vanilla does not play the animation if you switch between two
     *                    slots that hold the exact same item.
     * @return True to play the item change animation
     */
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return !oldStack.isItemEqual(newStack) || (ItemMirageOrb.isActive(oldStack) != ItemMirageOrb.isActive(newStack));
    }
}
