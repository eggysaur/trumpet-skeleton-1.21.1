package com.eggy.trumpetskeleton;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class PassiveDootGoal extends Goal {
    private final TrumpetSkeletonEntity skeleton;
    private int useTime = 0;

    public PassiveDootGoal(TrumpetSkeletonEntity skeleton) {
        this.skeleton = skeleton;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // 1 in 200 chance per tick to doot when bored
        return this.skeleton.getTarget() == null
                && this.skeleton.getRandom().nextInt(200) == 0
                && this.skeleton.isHolding(TrumpetSkeleton.TRUMPET.get());
    }

    @Override
    public boolean canContinueToUse() {
        return this.useTime > 0 && this.skeleton.getTarget() == null;
    }

    @Override
    public void start() {
        this.useTime = 30;
        this.skeleton.startUsingItem(InteractionHand.MAIN_HAND);
        this.skeleton.getNavigation().stop(); // Stand still while doing it
    }

    @Override
    public void tick() {

        this.useTime--;
        if (this.useTime <= 0) {
            float randomPitch = (float) (1.0D + this.skeleton.getRandom().nextGaussian() * 0.05D);
            // Just play the sound, no knockback for passive doots
            this.skeleton.level().playSound(null, this.skeleton.getX(), this.skeleton.getY(), this.skeleton.getZ(),
                    TrumpetSkeleton.TRUMPET_DOOT.get(), SoundSource.HOSTILE, 1.0F, randomPitch);
        }
    }

    @Override
    public void stop() {
        this.skeleton.stopUsingItem();
    }
}