package com.eggy.trumpetskeleton;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class TrumpetAttackGoal extends Goal {
    private final TrumpetSkeletonEntity skeleton;
    private long nextDootTime = 0;
    private int useTime = 0;
    private double kbrange = 4;


    public TrumpetAttackGoal(TrumpetSkeletonEntity skeleton) {
        this.skeleton = skeleton;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.skeleton.getTarget();
        if (target != null && target.isAlive() && this.skeleton.isHolding(TrumpetSkeleton.TRUMPET.get())) {
            // Only trigger if cooldown is finished, target is within 8 blocks (64 sqr), and is visible
            if (this.skeleton.level().getGameTime() >= this.nextDootTime) {
                return this.skeleton.distanceToSqr(target) < (kbrange*kbrange) && this.skeleton.getSensing().hasLineOfSight(target);
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        /// time after doot
        return this.useTime > -10 && this.skeleton.getTarget() != null;
    }

    @Override
    public void start() {
        this.useTime = 10; ///charge up time
        this.skeleton.startUsingItem(InteractionHand.MAIN_HAND);
    }

    @Override
    public void tick() {
        LivingEntity target = this.skeleton.getTarget();
        if (target != null) {
            this.skeleton.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.skeleton.getNavigation().moveTo(target, 1.2D);
        }

        this.useTime--;
        if (this.useTime == 0) {
            performTrumpetAttack();

            this.nextDootTime = this.skeleton.level().getGameTime() + 100;
        }
    }

    @Override
    public void stop() {
        this.skeleton.stopUsingItem();
    }

    private void performTrumpetAttack() {
        float randomPitch = (float) (1.0D + this.skeleton.getRandom().nextGaussian() * 0.05D);
        this.skeleton.level().playSound(null, this.skeleton.getX(), this.skeleton.getY(), this.skeleton.getZ(),
                TrumpetSkeleton.TRUMPET_DOOT.get(), SoundSource.HOSTILE, 1.5F, randomPitch);

        AABB area = this.skeleton.getBoundingBox().inflate(kbrange);
        List<LivingEntity> targets = this.skeleton.level().getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity t : targets) {
            if (t != this.skeleton) {
                t.hurt(this.skeleton.level().damageSources().mobAttack(this.skeleton), 0.1F);

                double dx = this.skeleton.getX() - t.getX();
                double dz = this.skeleton.getZ() - t.getZ();

                t.knockback(1D, dx, dz);
            }
        }
    }
}