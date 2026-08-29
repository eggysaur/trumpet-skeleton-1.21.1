package com.eggy.trumpetskeleton;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = TrumpetSkeleton.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TrumpetSkeleton.MODID, value = Dist.CLIENT)
public class TrumpetSkeletonClient {
    public TrumpetSkeletonClient(ModContainer container) {

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(TrumpetSkeleton.TRUMPET.get(), ResourceLocation.fromNamespaceAndPath("trumpetskeleton", "tooting"),
                    (stack, level, entity, seed) -> {
                        return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
                    });
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TrumpetSkeleton.TRUMPET_SKELETON.get(), TrumpetSkeletonRenderer::new);
    }
}
