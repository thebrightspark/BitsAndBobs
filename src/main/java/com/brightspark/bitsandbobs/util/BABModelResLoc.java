package com.brightspark.bitsandbobs.util;

import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class BABModelResLoc extends ModelResourceLocation
{
    public BABModelResLoc(String itemName)
    {
        super(Reference.ITEM_TEXTURE_DIR + itemName, "inventory");
    }
}
