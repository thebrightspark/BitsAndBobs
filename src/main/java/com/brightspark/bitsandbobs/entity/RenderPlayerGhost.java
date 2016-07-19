package com.brightspark.bitsandbobs.entity;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * Created by Mark on 18/07/2016.
 */
public class RenderPlayerGhost extends RenderLivingBase<EntityPlayerGhost>
{
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
    public static final Factory FACTORY = new Factory();

    public RenderPlayerGhost(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelPlayer(0.0f, false), 0.5f);
    }

    @Override
    public ModelPlayer getMainModel()
    {
        return (ModelPlayer)super.getMainModel();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPlayerGhost entity)
    {
        return entity.playerSkin != null ? entity.playerSkin : DEFAULT_RES_LOC;
    }

    @Override
    protected boolean canRenderName(EntityPlayerGhost entity)
    {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }

    @Override
    protected void renderModel(EntityPlayerGhost entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1f, 1f, 1f, 0.5f);
        super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        GlStateManager.disableBlend();
    }

    public static class Factory implements IRenderFactory<EntityPlayerGhost>
    {
        @Override
        public Render<? super EntityPlayerGhost> createRenderFor(RenderManager manager)
        {
            return new RenderPlayerGhost(manager);
        }
    }
}
