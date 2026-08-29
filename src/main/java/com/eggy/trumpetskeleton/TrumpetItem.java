package com.eggy.trumpetskeleton;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TrumpetItem extends Item {
    public TrumpetItem(Properties properties){
        super(properties);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 50;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack itemStack = player.getItemInHand(hand);
        float pitch = (float) (1.0D + level.random.nextGaussian() * 0.05D);
        level.playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                TrumpetSkeleton.TRUMPET_DOOT.value(),
                SoundSource.PLAYERS,
                1.5f, pitch
        );

        if (!level.isClientSide()) {
            double range = 4.0D;
            AABB area = player.getBoundingBox().inflate(range);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity target : targets) {

                if (target != player) {
                    Vec3 knockbackVec = target.position().subtract(player.position()).normalize();
                    double knockbackStrength = 1.2D;


                    target.push(
                            knockbackVec.x * knockbackStrength,
                            0.35D,
                            knockbackVec.z * knockbackStrength
                    );
                    target.hurtMarked = true;
                }
            }

            // Apply a 30-tick (1.5s) cooldown
            player.getCooldowns().addCooldown(this, 30);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

}
