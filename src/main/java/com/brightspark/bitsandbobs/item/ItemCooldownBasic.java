package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCooldownBasic extends ItemBasic
{
    protected final int MAX_COOLDOWN;
    protected static final String KEY_COOLDOWN = "cooldown";
    private final boolean durabilityBar;

    public ItemCooldownBasic(String itemName, int maxCooldown)
    {
        this(itemName, maxCooldown, true);
    }

    public ItemCooldownBasic(String itemName, int maxCooldown, boolean useDurabilityBar)
    {
        super(itemName);
        setMaxStackSize(1);
        MAX_COOLDOWN = maxCooldown;
        durabilityBar = useDurabilityBar;
    }

    public static boolean isActive(ItemStack stack)
    {
        return NBTHelper.getInt(stack, KEY_COOLDOWN) > 0;
    }

    /**
     * This will get called by onItemRightClick when the cooldown is 0
     * @return True if the cooldown should be started.
     */
    protected boolean doRightClickAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        int cooldown = NBTHelper.getInt(stack, KEY_COOLDOWN);
        if(cooldown > 0)
            //Item currently active
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);

        boolean startCooldown = doRightClickAction(stack, world, player, hand);

        //Set cooldown
        if(!player.isCreative() && startCooldown)
            NBTHelper.setInteger(stack, KEY_COOLDOWN, MAX_COOLDOWN);

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        //Reduces cooldown every tick
        int cooldown = NBTHelper.getInt(stack, KEY_COOLDOWN);
        if(cooldown > 0)
            NBTHelper.setInteger(stack, KEY_COOLDOWN, --cooldown);
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
        //This change will stop the animation playing due to the cooldown changing
        return !oldStack.isItemEqual(newStack) || (ItemMirageOrb.isActive(oldStack) != ItemMirageOrb.isActive(newStack));
    }

    public boolean showDurabilityBar(ItemStack stack)
    {
        return durabilityBar && NBTHelper.getInt(stack, KEY_COOLDOWN) > 0;
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (float) NBTHelper.getInt(stack, KEY_COOLDOWN) / (float) MAX_COOLDOWN;
    }
}
