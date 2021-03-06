package com.brightspark.bitsandbobs.entity;

import com.brightspark.bitsandbobs.util.LogHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
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
        super(renderManagerIn, new ModelPlayerGhost(), 0.5f);
    }

    @Override
    public ModelPlayerGhost getMainModel()
    {
        return (ModelPlayerGhost)super.getMainModel();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPlayerGhost entity)
    {
        return entity.playerSkin == null || entity.playerSkin.getResourcePath().equals("") ? DEFAULT_RES_LOC : entity.playerSkin;
    }

    @Override
    protected boolean canRenderName(EntityPlayerGhost entity)
    {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }

    @Override
    public void doRender(EntityPlayerGhost entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<EntityPlayerGhost>(entity, this, x, y, z))) return;
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();

        mainModel.swingProgress = getSwingProgress(entity, partialTicks);
        boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        mainModel.isRiding = shouldSit;
        mainModel.isChild = entity.isChild();

        try
        {
            float renderYawOffset = entity.renderYawOffset;
            float rotationYawHead = entity.rotationYawHead;
            float headYaw = rotationYawHead - renderYawOffset;

            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase)
            {
                float f3 = MathHelper.wrapDegrees(headYaw);

                if (f3 < -85.0F)
                {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F)
                {
                    f3 = 85.0F;
                }

                renderYawOffset = rotationYawHead - f3;

                if (f3 * f3 > 2500.0F)
                {
                    renderYawOffset += f3 * 0.2F;
                }
            }

            renderLivingAt(entity, x, y, z);
            applyRotations(entity, 0, renderYawOffset, partialTicks);
            float scale = prepareScale(entity, partialTicks);
            float swingAmount = 0.0F;
            float swing = 0.0F;

            if (!entity.isRiding())
            {
                swingAmount = entity.limbSwingAmount;
                swing = entity.limbSwing;

                if (entity.isChild())
                {
                    swing *= 3.0F;
                }

                if (swingAmount > 1.0F)
                {
                    swingAmount = 1.0F;
                }
            }

            GlStateManager.enableAlpha();
            mainModel.setLivingAnimations(entity, swing, swingAmount, partialTicks);
            mainModel.setRotationAngles(swing, swingAmount, 0, headYaw, entity.rotationPitch, scale, entity);

            if (renderOutlines)
            {
                boolean flag1 = setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(getTeamColor(entity));

                if (!renderMarker)
                {
                    renderModel(entity, swing, swingAmount, 0, headYaw, entity.rotationPitch, scale);
                }

                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();

                if (flag1)
                {
                    unsetScoreTeamColor();
                }
            }
            else
            {
                boolean flag = setDoRenderBrightness(entity, partialTicks);
                renderModel(entity, swing, swingAmount, 0, headYaw, entity.rotationPitch, scale);

                if (flag)
                {
                    unsetBrightness();
                }

                GlStateManager.depthMask(true);
            }

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception)
        {
            LogHelper.error("Couldn\'t render entity\n" + exception.getMessage());
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<EntityPlayerGhost>(entity, this, x, y, z));
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
