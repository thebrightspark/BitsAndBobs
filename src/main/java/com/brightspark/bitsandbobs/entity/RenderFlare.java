package com.brightspark.bitsandbobs.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderFlare extends Render<EntityFlare>
{
    private static final ResourceLocation PARTICLES = new ResourceLocation("textures/particle/particles.png");
    public static final Factory FACTORY = new Factory();

    protected RenderFlare(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFlare entity)
    {
        return PARTICLES;
    }

    @Override
    public void doRender(EntityFlare entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        //GlStateManager.disableTexture2D();
        GlStateManager.enableRescaleNormal();
        GlStateManager.depthMask(false);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        bindEntityTexture(entity);

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.alphaFunc(516, 0.003921569F);

        //Most of this has been taken from Particle#renderParticle
        float u = (float) (144 % 16) / 16f;
        float uu = u + 0.0624375F;
        float v = (float) (144 / 16) / 16f;
        float vv = v + 0.0624375F;
        float scale = 0.15f;
        float actualX = (float) (entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks);
        float actualY = (float) (entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks);
        float actualZ = (float) (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks);


        World world = entity.world;
        BlockPos pos = new BlockPos(x, y, z);
        int brightness = world.isBlockLoaded(pos) ? world.getCombinedLight(pos, 0) : 0;
        int bright1 = brightness >> 16 & 65535;
        int bright2 = brightness & 65535;
        float rotX = ActiveRenderInfo.getRotationX();
        float rotZ = ActiveRenderInfo.getRotationZ();
        float rotYZ = ActiveRenderInfo.getRotationYZ();
        float rotXY = ActiveRenderInfo.getRotationXY();
        float rotXZ = ActiveRenderInfo.getRotationXZ();
        Vec3d[] vec = new Vec3d[] {
                new Vec3d((double) (-rotX * scale - rotXY * scale), (double) (-rotZ * scale), (double) (-rotYZ * scale - rotXZ * scale)),
                new Vec3d((double) (-rotX * scale + rotXY * scale), (double) (rotZ * scale), (double) (-rotYZ * scale + rotXZ * scale)),
                new Vec3d((double) (rotX * scale + rotXY * scale), (double) (rotZ * scale), (double) (rotYZ * scale + rotXZ * scale)),
                new Vec3d((double) (rotX * scale - rotXY * scale), (double) (-rotZ * scale), (double) (rotYZ * scale - rotXZ * scale))
        };


        Tessellator tes = Tessellator.getInstance();
        VertexBuffer buf = tes.getBuffer();
        //buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        /*
        buf.pos(-0.5f, 0.6f, 0f).tex(u, vv);
        buf.pos(0.5f, 0.6f, 0f).tex(uu, vv);
        buf.pos(0.5f, -0.4f, 0f).tex(uu, v);
        buf.pos(-0.5f, -0.4f, 0f).tex(u, v);
        */

        buf.pos((double) actualX + vec[0].x, (double) actualY + vec[0].y, (double) actualZ + vec[0].z).tex((double) uu, (double) vv).color(1f, 0f, 0, 1f).endVertex(); //.lightmap(bright1, bright2).endVertex();
        buf.pos((double) actualX + vec[1].x, (double) actualY + vec[1].y, (double) actualZ + vec[1].z).tex((double) uu, (double) v).color(1f, 0f, 0, 1f).endVertex(); //.lightmap(bright1, bright2).endVertex();
        buf.pos((double) actualX + vec[2].x, (double) actualY + vec[2].y, (double) actualZ + vec[2].z).tex((double) u, (double) v).color(1f, 0f, 0, 1f).endVertex(); //.lightmap(bright1, bright2).endVertex();
        buf.pos((double) actualX + vec[3].x, (double) actualY + vec[3].y, (double) actualZ + vec[3].z).tex((double) u, (double) vv).color(1f, 0f, 0, 1f).endVertex(); //.lightmap(bright1, bright2).endVertex();

        tes.draw();

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        //GlStateManager.enableTexture2D();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    public static class Factory implements IRenderFactory<EntityFlare>
    {
        @Override
        public Render<? super EntityFlare> createRenderFor(RenderManager manager)
        {
            return new RenderFlare(manager);
        }
    }
}
