package com.brightspark.bitsandbobs.item;

import com.brightspark.bitsandbobs.reference.Names;

/**
 * Created by Mark on 12/02/2017.
 */
public class ItemAmmoBelt extends ItemBasic
{
    public ItemAmmoBelt()
    {
        super(Names.Items.AMMO_BELT);
        setMaxStackSize(1);
    }

    //TODO: Make ammo belt. It'll be able to store 9 (1 row) of ammo clips.
    //TODO: Make ammo belt be able to be put in the belt bauble slot
}
