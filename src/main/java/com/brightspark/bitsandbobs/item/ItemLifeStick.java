package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.util.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemLifeStick extends ItemBasic
{
    private final String TOOLTIP;
    private static final String STORAGE = "storage";
    private static final float STORAGE_MAX = 40;

    public ItemLifeStick()
    {
        super("itemLifeStick");
        TOOLTIP = getUnlocalizedName() + ".tooltip.";
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Adds health to the storage of the Life Stick.
     * @param stack The Life Stick's ItemStack
     * @param amount The amount of health to add
     */
    public void addToStorage(ItemStack stack, float amount)
    {
        float storage = NBTHelper.getFloat(stack, STORAGE);
        if((storage + amount) > STORAGE_MAX)
            NBTHelper.setFloat(stack, STORAGE, STORAGE_MAX);
        else
            NBTHelper.setFloat(stack, STORAGE, storage + amount);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        float storage = NBTHelper.getFloat(stack, STORAGE);
        if(player.isSneaking())
        {
            if(storage < STORAGE_MAX)
            {
                //Store health
                if(player.attackEntityFrom(DamageSource.generic, 2))
                {
                    addToStorage(stack, 1);
                    float rand = (float) ((Math.random() / 2f) + 1.5f);
                    world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.5f, rand);
                }
            }
        }
        else
        {
            if(storage > 0)
            {
                //Get health
                float health = player.getHealth();
                float maxHealth = player.getMaxHealth();
                if(health < maxHealth && storage > 0)
                {
                    world.playSound(null, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 0.5f, 2.0f);
                    float toHeal = maxHealth - health; //Amount to heal
                    if(storage < toHeal)
                    {
                        //If less storage than health
                        player.heal(storage);
                        NBTHelper.setFloat(stack, STORAGE, 0);
                    }
                    else
                    {
                        //If enough storage to heal
                        player.heal(toHeal);
                        NBTHelper.setFloat(stack, STORAGE, storage - toHeal);
                    }
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack is)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean par4)
    {
        float hearts = NBTHelper.getFloat(itemStack, STORAGE);
        hearts = hearts / 2;
        String text = I18n.format(TOOLTIP + "1") + " ";
        if(hearts >= STORAGE_MAX/2)
        {
            text = text + TextFormatting.GREEN;
        }
        else if(hearts <= STORAGE_MAX/2)
        {
            text = text + TextFormatting.RED;
        }
        else
        {
            text = text + TextFormatting.GOLD;
        }
        list.add(text + Float.toString(hearts));
        list.add("");
        if(player.getDisplayNameString().equals("alxnns1"))
            list.add(TextFormatting.GOLD + "" + TextFormatting.ITALIC + I18n.format(TOOLTIP + "disco"));
        else
        {
            list.add(TextFormatting.DARK_GREEN + I18n.format(TOOLTIP + "2.1") + TextFormatting.RESET + I18n.format(TOOLTIP + "2.2"));
            list.add(TextFormatting.YELLOW + I18n.format(TOOLTIP + "3.1") + TextFormatting.RESET + I18n.format(TOOLTIP + "3.2"));
        }
        if(player.getDisplayNameString().equals("8BrickDMG"))
            list.add(TextFormatting.BLACK + I18n.format(TOOLTIP + "watching"));
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).getDisplayNameString().equals("alxnns1"))
            stack.setStackDisplayName(TextFormatting.GOLD + I18n.format(getUnlocalizedName() + ".disco"));
        else if(stack.hasDisplayName())
            stack.clearCustomName();
    }
}
