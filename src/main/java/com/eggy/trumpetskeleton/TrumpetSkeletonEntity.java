package com.eggy.trumpetskeleton;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TrumpetSkeletonEntity extends Skeleton {

    public TrumpetSkeletonEntity(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Skeleton.createAttributes();
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TrumpetSkeleton.TRUMPET.get()));
    }

    @Override
    public void reassessWeaponGoal() {
        // no bow 4 u
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new TrumpetAttackGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(5, new PassiveDootGoal(this));


        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }
}