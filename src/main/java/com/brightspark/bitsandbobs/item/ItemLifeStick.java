package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.util.NBTHelper;
import com.brightspark.bitsandbobs.reference.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemLifeStick extends ItemBasic
{
    private static final String STORAGE = "storage";
    private static final float STORAGE_MAX = 40;

    public ItemLifeStick()
    {
        super(Names.Items.LIFE_STICK);
        setMaxStackSize(1);
    }

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

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityplayer)
    {
        float storage = NBTHelper.getFloat(stack, STORAGE);
        if(entityplayer.isSneaking())
        {
            if(storage < STORAGE_MAX)
            {
                //Store health
                if(entityplayer.attackEntityFrom(DamageSource.generic, 2))
                {
                    addToStorage(stack, 1);
                    float rand = (float) ((Math.random() / 2) + 1.75);
                    entityplayer.worldObj.playSoundAtEntity(entityplayer, "random.orb", 0.5F, rand);
                }
            }
        }
        else
        {
            if(NBTHelper.getFloat(stack, STORAGE) > 0)
            {
                //Get health
                float health = entityplayer.getHealth();
                float maxHealth = entityplayer.getMaxHealth();
                if(health < maxHealth && storage > 0)
                {
                    entityplayer.worldObj.playSoundAtEntity(entityplayer, "random.levelup", 0.5F, 2.0F);
                    float toHeal = maxHealth - health; //Amount to heal
                    if(storage < toHeal)
                    {
                        //If less storage than health
                        entityplayer.heal(storage);
                        NBTHelper.setFloat(stack, STORAGE, 0);
                    }
                    else
                    {
                        //If enough storage to heal
                        entityplayer.heal(toHeal);
                        NBTHelper.setFloat(stack, STORAGE, storage - toHeal);
                    }
                }
            }
        }
        return stack;
    }



    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack is)
    {
        return true;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
    {
        String tooltip = getUnlocalizedName() + ".tooltip.";
        float hearts = NBTHelper.getFloat(itemStack, STORAGE);
        hearts = hearts / 2;
        String text = StatCollector.translateToLocal(tooltip + "1") + " ";
        if(hearts >= STORAGE_MAX/2)
        {
            text = text + EnumChatFormatting.GREEN;
        }
        else if(hearts <= STORAGE_MAX/2)
        {
            text = text + EnumChatFormatting.RED;
        }
        else
        {
            text = text + EnumChatFormatting.GOLD;
        }
        list.add(text + Float.toString(hearts));
        list.add("");
        if(player.getDisplayNameString().equals("alxnns1"))
            list.add(StatCollector.translateToLocal(tooltip + "disco"));
        else
        {
            list.add(StatCollector.translateToLocal(tooltip + "2"));
            list.add(StatCollector.translateToLocal(tooltip + "3"));
        }
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).getDisplayNameString().equals("alxnns1"))
            stack.setStackDisplayName(EnumChatFormatting.GOLD + StatCollector.translateToLocal(getUnlocalizedName() + ".disco"));
        else if(stack.hasDisplayName())
            stack.clearCustomName();
    }
}
