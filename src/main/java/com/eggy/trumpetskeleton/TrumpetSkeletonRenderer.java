package com.eggy.trumpetskeleton;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class TrumpetSkeletonRenderer extends SkeletonRenderer {
    private static final ResourceLocation SKELETON_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/skeleton/skeleton.png");

    public TrumpetSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);

        this.model = new TrumpetSkeletonModel(context.bakeLayer(ModelLayers.SKELETON)); ///replaces with custom adjustments
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
        return SKELETON_LOCATION;
    }
}
