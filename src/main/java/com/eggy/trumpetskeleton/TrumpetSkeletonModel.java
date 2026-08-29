package com.eggy.trumpetskeleton;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class TrumpetSkeletonModel extends SkeletonModel<AbstractSkeleton> {

    public TrumpetSkeletonModel (ModelPart root){
        super(root);
    }

    @Override
    public void setupAnim(AbstractSkeleton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean isLeftHanded = entity.isLeftHanded();


        if (entity.isUsingItem()) {
            if (isLeftHanded) {
                this.leftArmPose = HumanoidModel.ArmPose.TOOT_HORN;
                this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
            } else {
                this.rightArmPose = HumanoidModel.ArmPose.TOOT_HORN;
                this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
            }
        } else {
            if (isLeftHanded) {
                this.leftArmPose = HumanoidModel.ArmPose.ITEM;
                this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
            } else {
                this.rightArmPose = HumanoidModel.ArmPose.ITEM;
                this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
            }
        }


        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);


        if (entity.isUsingItem()) {
            if (isLeftHanded) {
                this.rightArm.xRot = this.leftArm.xRot;
                this.rightArm.yRot = -this.leftArm.yRot;
                this.rightArm.zRot = -this.leftArm.zRot;
            } else {
                this.leftArm.xRot = this.rightArm.xRot;
                this.leftArm.yRot = -this.rightArm.yRot;
                this.leftArm.zRot = -this.rightArm.zRot;
            }
        } else {
            // Apply the custom idle tilt when walking normally
            this.rightArm.xRot = (float) -(Math.PI / 10) + this.rightArm.xRot * 0.5f;
            this.leftArm.xRot = (float) -(Math.PI / 10) + this.leftArm.xRot * 0.5f;
        }
    }

}
