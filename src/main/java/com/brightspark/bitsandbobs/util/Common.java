package com.brightspark.bitsandbobs.util;

import com.brightspark.bitsandbobs.entity.ParticleFlare;
import com.brightspark.bitsandbobs.entity.ParticleTrailing;
import com.brightspark.bitsandbobs.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;

public class Common
{
    private static Minecraft mc = Minecraft.getMinecraft();

    //Register a model
    public static void regModel(Item item)
    {
        regModel(item, 0);
    }
    public static void regModel(Block block)
    {
        regModel(Item.getItemFromBlock(block), 0);
    }

    //Register a model with meta
    public static void regModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    //Register a fluid model
    public static void regFluidModel(IFluidBlock fluidBlock)
    {
        Item item = Item.getItemFromBlock((Block) fluidBlock);
        if(item == null)
        {
            LogHelper.fatal("Fluid " + ((Block) fluidBlock).getRegistryName() + " gave a null Item!");
            return;
        }
        ModelBakery.registerItemVariants(item);
        final ModelResourceLocation modelLoc = new ModelResourceLocation(Reference.MOD_ID + ":fluid"); //, fluidBlock.getFluid().getName());
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return modelLoc;
            }
        });
        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return modelLoc;
            }
        });
    }

    /**
     * Spawns a particle in the world
     */
    public static void spawnEffect(Particle particle)
    {
        mc.effectRenderer.addEffect(particle);
    }

    /**
     * Spawns a trailing particle which twirls upwards around the entity
     */
    public static void spawnTwirlEffect(World world, Entity entity)
    {
        spawnEffect(new ParticleTrailing(world, entity, 6, 82, 160).setChildRGBColour(0.6f, 1f, 0.6f));
    }

    /**
     * Spawns a trailing particle which shoots out from the entity in the direction it's facing
     */
    public static void spawnFlareEffect(World world, Entity entity)
    {
        spawnEffect(new ParticleFlare(world, entity.posX - 0.5d, entity.posY + 1d, entity.posZ - 0.5d, entity.getLookVec()));
    }
}
