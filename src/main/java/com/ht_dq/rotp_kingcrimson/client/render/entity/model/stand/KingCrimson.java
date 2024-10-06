package com.ht_dq.rotp_kingcrimson.client.render.entity.model.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.ht_dq.rotp_kingcrimson.action.KingCrimsonChop;
import com.ht_dq.rotp_kingcrimson.action.KingCrimsonImpale;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransitionMultiple;
import com.github.standobyte.jojo.entity.stand.StandPose;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class KingCrimson extends HumanoidStandModel<KingCrimsonEntity> {
	private final ModelRenderer epitaph;
	private final ModelRenderer head_r1;
	private final ModelRenderer head_r2;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone8;
	private final ModelRenderer bone4;
	private final ModelRenderer leftArm_r1;
	private final ModelRenderer bone6;
	private final ModelRenderer leftForeArm_r1;
	private final ModelRenderer bone9;
	private final ModelRenderer rightArm_r1;
	private final ModelRenderer bone5;
	private final ModelRenderer bone7;
	private final ModelRenderer rightForeArm_r1;
	private final ModelRenderer bone12;
	private final ModelRenderer leftArm_r2;
	private final ModelRenderer bone10;
	private final ModelRenderer leftLowerLeg_r1;
	private final ModelRenderer bone13;
	private final ModelRenderer rightArm_r2;
	private final ModelRenderer bone11;
	private final ModelRenderer rightLowerLeg_r1;

	public KingCrimson() {
		super();
		addHumanoidBaseBoxes(null);

		texWidth = 128;
		texHeight = 128;

		head.texOffs(52, 0).addBox(-3.5F, -8.0F, -3.7F, 7.0F, 8.0F, 7.0F, 0.0F, false);
		head.texOffs(100, 0).addBox(-3.0F, -7.0F, -3.9F, 6.0F, 7.0F, 2.0F, 0.0F, false);

		epitaph = new ModelRenderer(this);
		epitaph.setPos(0.0F, -6.2F, -3.4F);
		head.addChild(epitaph);

		head_r1 = new ModelRenderer(this);
		head_r1.setPos(0.5F, -0.6F, -0.1F);
		epitaph.addChild(head_r1);
		setRotationAngle(head_r1, -0.2182F, 0.0F, 0.0F);
		head_r1.texOffs(112, 15).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);

		head_r2 = new ModelRenderer(this);
		head_r2.setPos(0.0F, 0.0F, 0.0F);
		epitaph.addChild(head_r2);
		setRotationAngle(head_r2, -0.2182F, 0.0F, 0.0F);
		head_r2.texOffs(52, 60).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		torso.texOffs(112, 10).addBox(-3.5F, 0.6F, -2.0F, 7.0F, 3.0F, 1.0F, 0.4F, false);
		torso.texOffs(75, 21).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 6.0F, 4.0F, 0.0F, false);
		torso.texOffs(52, 16).addBox(-4.0F, -2.8F, -1.2F, 8.0F, 3.0F, 5.0F, 0.0F, false);
		torso.texOffs(67, 32).addBox(-3.0F, -2.8F, -1.2F, 6.0F, 2.0F, 4.0F, 0.0F, false);
		torso.texOffs(52, 25).addBox(-3.0F, 5.0F, -1.5F, 6.0F, 6.0F, 3.0F, 0.0F, false);
		torso.texOffs(65, 56).addBox(-2.0F, 10.6F, -2.0F, 4.0F, 2.0F, 4.0F, 0.25F, false);
		torso.texOffs(78, 70).addBox(-3.5F, 11.3F, -1.5F, 1.0F, 1.0F, 3.0F, 0.25F, false);
		torso.texOffs(86, 68).addBox(2.5F, 11.3F, -1.5F, 1.0F, 1.0F, 3.0F, 0.25F, true);

		bone2 = new ModelRenderer(this);
		bone2.setPos(2.0F, 11.0F, 0.0F);
		torso.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, -0.2618F);
		bone2.texOffs(95, 53).addBox(-3.0F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setPos(-2.0F, 11.0F, 0.0F);
		torso.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, 0.2618F);
		bone3.texOffs(100, 21).addBox(-2.0F, -1.0F, -2.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);

		leftArm.texOffs(52, 49).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(76, 11).addBox(-1.5F, -2.6F, -2.4F, 4.0F, 4.0F, 5.0F, 0.0F, false);

		leftArmJoint.texOffs(114, 28).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

		bone8 = new ModelRenderer(this);
		bone8.setPos(0.0F, 1.0F, 1.0F);
		leftArmJoint.addChild(bone8);
		setRotationAngle(bone8, 0.0F, 0.0F, -0.7854F);
		bone8.texOffs(72, 66).addBox(-0.2F, -1.7F, -0.3F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setPos(0.0F, 5.0F, 1.0F);
		leftForeArm.addChild(bone4);
		setRotationAngle(bone4, 0.0F, 0.0F, -0.7854F);

		leftArm_r1 = new ModelRenderer(this);
		leftArm_r1.setPos(1.1F, 0.1F, -1.0F);
		bone4.addChild(leftArm_r1);
		setRotationAngle(leftArm_r1, -1.5708F, 0.7854F, -0.7854F);
		leftArm_r1.texOffs(65, 63).addBox(-0.4F, -1.6F, -0.3F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		leftForeArm.texOffs(78, 49).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		bone6 = new ModelRenderer(this);
		bone6.setPos(1.8F, 5.8F, 0.0F);
		leftForeArm.addChild(bone6);


		leftForeArm_r1 = new ModelRenderer(this);
		leftForeArm_r1.setPos(0.0F, 0.0F, 0.0F);
		bone6.addChild(leftForeArm_r1);
		setRotationAngle(leftForeArm_r1, 0.0F, 0.0F, -0.7854F);
		leftForeArm_r1.texOffs(52, 68).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		rightArm.texOffs(52, 35).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		rightArm.texOffs(81, 0).addBox(-2.5F, -2.6F, -2.4F, 4.0F, 4.0F, 5.0F, 0.0F, false);

		rightArmJoint.texOffs(111, 57).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

		bone9 = new ModelRenderer(this);
		bone9.setPos(0.0F, 5.0F, 1.0F);
		rightForeArm.addChild(bone9);
		setRotationAngle(bone9, 0.0F, 0.0F, 0.7854F);

		rightArm_r1 = new ModelRenderer(this);
		rightArm_r1.setPos(-1.1F, 0.1F, -1.0F);
		bone9.addChild(rightArm_r1);
		setRotationAngle(rightArm_r1, -1.5708F, -0.7854F, 0.7854F);
		rightArm_r1.texOffs(114, 64).addBox(-1.6F, -1.6F, -0.3F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setPos(0.0F, 1.0F, 1.0F);
		rightArmJoint.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, 0.7854F);
		bone5.texOffs(117, 0).addBox(-1.8F, -1.7F, -0.3F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		rightForeArm.texOffs(84, 35).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setPos(-1.8F, 5.8F, 0.0F);
		rightForeArm.addChild(bone7);

		rightForeArm_r1 = new ModelRenderer(this);
		rightForeArm_r1.setPos(0.0F, 0.0F, 0.0F);
		bone7.addChild(rightForeArm_r1);
		setRotationAngle(rightForeArm_r1, 0.0F, 0.0F, 0.7854F);
		rightForeArm_r1.texOffs(52, 68).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, true);

		leftLeg.texOffs(97, 42).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		leftLegJoint.texOffs(92, 60).addBox(-1.6F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

		bone12 = new ModelRenderer(this);
		bone12.setPos(-0.2449F, -0.2037F, -2.5494F);
		leftLegJoint.addChild(bone12);
		setRotationAngle(bone12, -1.4383F, 1.5239F, -2.2495F);

		leftArm_r2 = new ModelRenderer(this);
		leftArm_r2.setPos(0.0F, 0.3F, -0.1F);
		bone12.addChild(leftArm_r2);
		setRotationAngle(leftArm_r2, -0.0436F, 0.0019F, 0.0436F);
		leftArm_r2.texOffs(105, 64).addBox(-1.8F, -1.0F, -0.8F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		leftLowerLeg.texOffs(84, 32).addBox(-2.3F, 2.8F, -0.1F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		leftLowerLeg.texOffs(69, 39).addBox(-1.9F, 2.8F, -0.1F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		leftLowerLeg.texOffs(65, 42).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftLowerLeg.texOffs(110, 49).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.25F, false);

		bone10 = new ModelRenderer(this);
		bone10.setPos(-0.1F, 3.0F, -2.0F);
		leftLowerLeg.addChild(bone10);
		setRotationAngle(bone10, 0.8727F, 0.0F, 0.0F);

		leftLowerLeg_r1 = new ModelRenderer(this);
		leftLowerLeg_r1.setPos(0.1F, 0.0F, 0.0F);
		bone10.addChild(leftLowerLeg_r1);
		setRotationAngle(leftLowerLeg_r1, -0.3927F, 0.0F, 0.0F);
		leftLowerLeg_r1.texOffs(114, 42).addBox(-2.0F, -0.9804F, -0.5F, 4.0F, 3.0F, 1.0F, 0.0F, false);

		rightLeg.texOffs(97, 28).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		rightLegJoint.texOffs(79, 60).addBox(-1.4F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

		bone13 = new ModelRenderer(this);
		bone13.setPos(0.2449F, -0.2037F, -2.5494F);
		rightLegJoint.addChild(bone13);
		setRotationAngle(bone13, -1.5255F, -1.5239F, 2.2495F);

		rightArm_r2 = new ModelRenderer(this);
		rightArm_r2.setPos(0.0F, 0.2F, -0.2F);
		bone13.addChild(rightArm_r2);
		setRotationAngle(rightArm_r2, 0.0523F, 0.0023F, -0.0436F);
		rightArm_r2.texOffs(117, 5).addBox(-0.2F, -1.0F, -0.8F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		rightLowerLeg.texOffs(95, 10).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		rightLowerLeg.texOffs(110, 35).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.25F, false);
		rightLowerLeg.texOffs(82, 46).addBox(-2.1F, 2.8F, -0.1F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		rightLowerLeg.texOffs(52, 46).addBox(-1.7F, 2.8F, -0.1F, 4.0F, 1.0F, 1.0F, 0.0F, false);

		bone11 = new ModelRenderer(this);
		bone11.setPos(0.1F, 3.0F, -2.0F);
		rightLowerLeg.addChild(bone11);
		setRotationAngle(bone11, 0.8727F, 0.0F, 0.0F);

		rightLowerLeg_r1 = new ModelRenderer(this);
		rightLowerLeg_r1.setPos(-0.1F, 0.0F, 0.0F);
		bone11.addChild(rightLowerLeg_r1);
		setRotationAngle(rightLowerLeg_r1, -0.3927F, 0.0F, 0.0F);
		rightLowerLeg_r1.texOffs(74, 0).addBox(-2.0F, -0.9678F, -0.477F, 4.0F, 3.0F, 1.0F, 0.0F, false);
	}

	@Override
	protected RotationAngle[][] initSummonPoseRotations() {
		return new RotationAngle[][] {
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, -11.1553, -30.0297, 25.5843),
						RotationAngle.fromDegrees(body, 29.6302, 74.9828, 1.9407),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 69.5675, 7.6443, -6.4664),
						RotationAngle.fromDegrees(leftForeArm, -77.1602, -48.9735, 78.3079),
						RotationAngle.fromDegrees(rightArm, 68.5769, 0.5995, 372.6044),
						RotationAngle.fromDegrees(rightForeArm, -90, 42.5, -90),
						RotationAngle.fromDegrees(leftLeg, -67.5, -12.5, 0),
						RotationAngle.fromDegrees(leftLowerLeg, 35, 0, 0),
						RotationAngle.fromDegrees(rightLeg, -49.6019, 11.9128, 3.814),
						RotationAngle.fromDegrees(rightLowerLeg, 65, 0, 0)
				},
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, -20, 22.5, 0),
						RotationAngle.fromDegrees(body, -27.5, 65, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 35, 0, 0),
						RotationAngle.fromDegrees(leftForeArm, -87.4785, 7.4928, -89.671),
						RotationAngle.fromDegrees(rightArm, -42.5, -42.5, 0),
						RotationAngle.fromDegrees(rightForeArm, -90, -62.5, 75),
						RotationAngle.fromDegrees(leftLeg, -74.0847, 19.291, 5.3815),
						RotationAngle.fromDegrees(leftLowerLeg, 92.5, 0, 0),
						RotationAngle.fromDegrees(rightLeg, -113.0688, -21.1278, -26.6558),
						RotationAngle.fromDegrees(rightLowerLeg, 77.5, 0, 0)
				},
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, -15.1229, -14.8081, -11.5219),
						RotationAngle.fromDegrees(body, 0, 0, 0),
						RotationAngle.fromDegrees(upperPart, -8.4531, -34.8804, 1.9735),
						RotationAngle.fromDegrees(leftArm, 0, -20, -22.5),
						RotationAngle.fromDegrees(leftForeArm, -142.5, -360, -282.5),
						RotationAngle.fromDegrees(rightArm, -0.4344, 2.462, -10.0093),
						RotationAngle.fromDegrees(rightForeArm, -142.6758, -1.6189, -77.6036),
						RotationAngle.fromDegrees(leftLeg, -14.5773, -55.1057, -25.1599),
						RotationAngle.fromDegrees(leftLowerLeg, 82.2979, -6.6485, 3.4787),
						RotationAngle.fromDegrees(rightLeg, -16.8086, -26.4883, 7.6734),
						RotationAngle.fromDegrees(rightLowerLeg, 25, 0, 0)
				},
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 12.4953, 0.2178, -2.4905),
						RotationAngle.fromDegrees(body, -7.5, -12.5, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 5.8448, -11.0686, -28.0668),
						RotationAngle.fromDegrees(leftForeArm, 0, 0, 20),
						RotationAngle.fromDegrees(rightArm, -42.5, 0, 82.5),
						RotationAngle.fromDegrees(rightForeArm, -46.731, -15.7851, 2.3726),
						RotationAngle.fromDegrees(leftLeg, 16.2926, -25.7503, -20.1631),
						RotationAngle.fromDegrees(leftLowerLeg, 17.5, 0, 0),
						RotationAngle.fromDegrees(rightLeg, 11.2696, 29.8307, 18.3475),
						RotationAngle.fromDegrees(rightLowerLeg, 30, 0, 0)
				},
				new RotationAngle[]{
						RotationAngle.fromDegrees(head, -35, -30, 0),
						RotationAngle.fromDegrees(body, 0, 0, 0),
						RotationAngle.fromDegrees(torso, 0, 0, 2.5),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 0, 0, -10),
						RotationAngle.fromDegrees(leftForeArm, -32.1928, -9.566, 26.4052),
						RotationAngle.fromDegrees(rightArm, 0, 0, 0),
						RotationAngle.fromDegrees(rightForeArm, -119.7904, -28.9317, -85.364),
						RotationAngle.fromDegrees(leftLeg, 0, 0, -5),
						RotationAngle.fromDegrees(leftLowerLeg, 10, 0, -7.5),
						RotationAngle.fromDegrees(rightLeg, 0, 0, 10),
						RotationAngle.fromDegrees(rightLowerLeg, 15, 0, -15)
				},
		};
	}

	@Override
	protected void initActionPoses() {
		ModelPose<KingCrimsonEntity> chop0 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0, 0, 0),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, 2.3357, -0.4043, 0.239),
				RotationAngle.fromDegrees(rightForeArm, -27.3599, 88.8724, -27.3059),
				RotationAngle.fromDegrees(leftArm, 0F, 0F, 0),
				RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0, 0, 10),
				RotationAngle.fromDegrees(leftLeg, 2.5125, -2.9316, -5.0716),
				RotationAngle.fromDegrees(leftLowerLeg, 2.5125, -2.9316, -5.0716),
				RotationAngle.fromDegrees(rightLowerLeg, 17.5, 0, 0)
		});
		ModelPose<KingCrimsonEntity> chop1 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, -0.02, 0.1, 0.06),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, -30.02, 15.19, -6.21),
				RotationAngle.fromDegrees(rightForeArm, -77.98, 19.86, -66.99),
				RotationAngle.fromDegrees(leftArm, -118.61, -3.3, -0.89),
				RotationAngle.fromDegrees(leftForeArm, -25, 0, 0),
				RotationAngle.fromDegrees(torso, -3.63F, -4.84F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0F, -2.43F, 10F),
				RotationAngle.fromDegrees(leftLeg, 2.6F, -11.4F, -5.46F),
				RotationAngle.fromDegrees(leftLowerLeg, 23.48F, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 25.98F, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> chop2 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0, 0, 0),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, -31.1704, 15.7725, -6.4568),
				RotationAngle.fromDegrees(rightForeArm, -80.26, 16.82, -68.78),
				RotationAngle.fromDegrees(leftArm, -123.94, -3.45, -0.93),
				RotationAngle.fromDegrees(leftForeArm, -29.21, 0, 0),
				RotationAngle.fromDegrees(torso, -3.8F, -5.07F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0F, -2.54F, 10F),
				RotationAngle.fromDegrees(leftLeg, 2.6F, -11.79F, -5.48F),
				RotationAngle.fromDegrees(leftLowerLeg, 23.87F, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 26.37F, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> chop3 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0.62, -3.66, -2.12),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, -31.1, 16.96, -6.95),
				RotationAngle.fromDegrees(rightForeArm, -97.7789, -4.7856, -82.8046),
				RotationAngle.fromDegrees(leftArm, -164.9453, -4.8292, -1.2972),
				RotationAngle.fromDegrees(leftForeArm, -62.5, 0, 0),
				RotationAngle.fromDegrees(torso, -5.28F, -7.04F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0F, -3.52F, 10F),
				RotationAngle.fromDegrees(leftLeg, 2.63F, -15.23F, -5.64F),
				RotationAngle.fromDegrees(leftLowerLeg, 27.31F, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 29.81F, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> chop4 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 1.8394, -10.8476, -6.2765),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, -41.1704, 15.7725, -6.4568),
				RotationAngle.fromDegrees(rightForeArm, -97.78, -4.79, -82.8),
				RotationAngle.fromDegrees(leftArm, -164.8765, -7.243, -1.9516),
				RotationAngle.fromDegrees(leftForeArm, -82.5, 0, 0),
				RotationAngle.fromDegrees(torso, -7.5F, -10F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0F, -5F, 10F),
				RotationAngle.fromDegrees(leftLeg, 2.6775F, -20.4139F, -5.8776F),
				RotationAngle.fromDegrees(leftLowerLeg, 32.5F, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 35F, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> chop5 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 12.81, -7.93, -13.25),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, -37.1704, 15.77, -6.46),
				RotationAngle.fromDegrees(rightForeArm, -97.78, -4.79, -82.8),
				RotationAngle.fromDegrees(leftArm, -99.94, -3.62, -0.98),
				RotationAngle.fromDegrees(leftForeArm, -55, 0, 0),
				RotationAngle.fromDegrees(torso, 2.41, -6.34, 0.81),
				RotationAngle.fromDegrees(rightLeg, 7.5, -5, 10),
				RotationAngle.fromDegrees(leftLeg, 7.13, -11.86, -3.98),
				RotationAngle.fromDegrees(leftLowerLeg, 32.5F, 0, 0),
				RotationAngle.fromDegrees(rightLowerLeg, 43.75, 0, 0)
		});
		ModelPose<KingCrimsonEntity> chop6 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 23.7756, -5.0038, -20.2317),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, -34.1704, 15.7725, -6.4568),
				RotationAngle.fromDegrees(rightForeArm, -97.78, -4.79, -82.8),
				RotationAngle.fromDegrees(leftArm, -35, 0, 0),
				RotationAngle.fromDegrees(leftForeArm, -27.5, 0, 0),
				RotationAngle.fromDegrees(torso, 2.3588, -2.6745, 1.6207),
				RotationAngle.fromDegrees(rightLeg, 15, -5, 10),
				RotationAngle.fromDegrees(leftLeg, 11.8894, -3.3082, -2.0873),
				RotationAngle.fromDegrees(leftLowerLeg, 32.5F, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 35F, 0F, 0F)
		});

		actionAnim.put(KingCrimsonChop.CHOP, new PosedActionAnimation.Builder<KingCrimsonEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(idlePose)
						.addPose(0.0F, chop0)
						.addPose(0.47F, chop1)
						.addPose(0.51F, chop2)
						.addPose(0.7F, chop3)
						.addPose(0.85F, chop4)
						.addPose(0.95F, chop5)
						.build(chop6))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(chop6)
						.addPose(1.2F, chop6)
						.build(idlePose))
				.build(idlePose));



		ModelPose<KingCrimsonEntity> impale0 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0F, 0F, 0F),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 0F, 0F, 0F),
				RotationAngle.fromDegrees(leftArm, 0F, 0F, 0),
				RotationAngle.fromDegrees(rightForeArm, 0F, 0F, 0F),
				RotationAngle.fromDegrees(leftForeArm, 0F, 0F, 0F),
				RotationAngle.fromDegrees(rightLeg, 0F, 0F, 0F),
				RotationAngle.fromDegrees(leftLeg, 0F, 0F, 0F),
				RotationAngle.fromDegrees(leftLowerLeg, 0F, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 0F, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> impale1 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, -4.71F, -0.01F, -0.85F),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0F, 2.26, 0F),
				RotationAngle.fromDegrees(rightArm, -1, 9.1F, 0F),
				RotationAngle.fromDegrees(leftArm, 7.5F, 0F, 0F),
				RotationAngle.fromDegrees(rightForeArm, -9.52, 0.02, -0.01F),
				RotationAngle.fromDegrees(leftForeArm, -48.31F, -41.6F, 48.31F),
				RotationAngle.fromDegrees(rightLeg, 0F, -0.24F, 0F),
				RotationAngle.fromDegrees(leftLeg, 0F, 0F, 0F),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> impale2 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, -9.21F, -0.19F, -1.65F),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0F, 4.73, 0F),
				RotationAngle.fromDegrees(rightArm, -2.03, 18.93, 0F),
				RotationAngle.fromDegrees(leftArm, 15.24F, -0.98F, -3.7F),
				RotationAngle.fromDegrees(rightForeArm, -18.53F, 0.03F, -0.01F),
				RotationAngle.fromDegrees(leftForeArm, -90F, -77.5F, 90F),
				RotationAngle.fromDegrees(rightLeg, 0F, -0.42F, 0F),
				RotationAngle.fromDegrees(leftLeg, 0.68F, -2.27F, 1.57F),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> impale3 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, -12.7412, -0.19, -2.1983),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0F, 7.5, 0F),
				RotationAngle.fromDegrees(rightArm, 0F, 25F, 0F),
				RotationAngle.fromDegrees(leftArm, 22.8266F, -1.8117F, -6.8315F),
				RotationAngle.fromDegrees(rightForeArm, -25F, 0F, 0F),
				RotationAngle.fromDegrees(leftForeArm, -90.8F, -78.82F, 90.8F),
				RotationAngle.fromDegrees(rightLeg, 0F, 3.33F, 0F),
				RotationAngle.fromDegrees(leftLeg, 0F, 0F, 0F),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> impale4 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, -14.64, -0.33F, -1.77),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0F, 8.56, 0F),
				RotationAngle.fromDegrees(rightArm, 3.26F, 24.07F, 0F),
				RotationAngle.fromDegrees(leftArm, 28.81, -1.87F, -7.05F),
				RotationAngle.fromDegrees(rightForeArm, -28.49F, -0.35F, 0.08F),
				RotationAngle.fromDegrees(leftForeArm, -90F, -77.5F, 90F),
				RotationAngle.fromDegrees(rightLeg, 0F, 7.36F, 0F),
				RotationAngle.fromDegrees(leftLeg, -1.27F, 4.25F, -2.94F),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> impale5 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, -17.7412F, -0.2495F, -2.1983F),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0F, 12.63, 0F),
				RotationAngle.fromDegrees(rightArm, 16.14, 17.96, 0F),
				RotationAngle.fromDegrees(leftArm, 37.8266, -1.8117, -6.8315),
				RotationAngle.fromDegrees(rightForeArm, -32.5F, 0F, 0F),
				RotationAngle.fromDegrees(leftForeArm, -90F, -68.14F, 90F),
				RotationAngle.fromDegrees(rightLeg, 0F, 24.8F, 0F),
				RotationAngle.fromDegrees(leftLeg, -6.9F, 23.13F, -16.02F),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0F, 0F)
		});
		ModelPose<KingCrimsonEntity> impale6 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 9.2649, 3.0886, -21.9267),
				RotationAngle.fromDegrees(body, 0F, 0F, 0F),
				RotationAngle.fromDegrees(upperPart, 0F, 0F, 0F),
				RotationAngle.fromDegrees(torso, 0F, 15, 0F),
				RotationAngle.fromDegrees(rightArm, 22.8266, 14.6712, 0F),
				RotationAngle.fromDegrees(leftArm, -64.6734, -1.8117, -6.8315),
				RotationAngle.fromDegrees(rightForeArm, -23.4043, 14.778, -3.5949),
				RotationAngle.fromDegrees(leftForeArm, -90, -62.5, 90),
				RotationAngle.fromDegrees(rightLeg, -37.5, 35, 0F),
				RotationAngle.fromDegrees(leftLeg, -10.1964, 34.1761, -23.6814),
				RotationAngle.fromDegrees(leftLowerLeg, 17.5, 0F, 0F),
				RotationAngle.fromDegrees(rightLowerLeg, 85, 0F, 0F)
		});

		actionAnim.put(KingCrimsonImpale.IMPALE, new PosedActionAnimation.Builder<KingCrimsonEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(idlePose)
						.addPose(0.0F, impale0)
						.addPose(0.10F, impale1)
						.addPose(0.20F, impale2)
						.addPose(0.30F, impale3)
						.addPose(0.40F, impale4)
						.addPose(0.90F, impale5)
						.build(impale6))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(impale6)
						.addPose(1.00F, impale6)
						.build(idlePose))
				.build(idlePose));

			ModelPose<KingCrimsonEntity> groundpunch0 = new ModelPose<>(new RotationAngle[]{
					RotationAngle.fromDegrees(head, 0, 0, 0),
					RotationAngle.fromDegrees(body, 0, 0, 0),
					RotationAngle.fromDegrees(upperPart, 0, 0, 0),
					RotationAngle.fromDegrees(rightArm, 0, 0, 0),
					RotationAngle.fromDegrees(rightForeArm, 0, 0, 0),
					RotationAngle.fromDegrees(leftArm, 0F, 0F, 0),
					RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
					RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
					RotationAngle.fromDegrees(rightLeg, 0, 0, 0),
					RotationAngle.fromDegrees(leftLeg, 0, 0, 0),
					RotationAngle.fromDegrees(leftLowerLeg, 0, 0, 0),
					RotationAngle.fromDegrees(rightLowerLeg, 0, 0, 0)
			});
			ModelPose<KingCrimsonEntity> groundpunch1 = new ModelPose<>(new RotationAngle[]{
					RotationAngle.fromDegrees(head, 11.27, 61.14, 24.03),
					RotationAngle.fromDegrees(body, -64.3083, 78.4174, -62.0472),
					RotationAngle.fromDegrees(upperPart, 0, 0, 0),
					RotationAngle.fromDegrees(rightArm, 91.3237, 66.25, 61.239),
					RotationAngle.fromDegrees(rightForeArm, -80.2484, 39.5899, -83.7496),
					RotationAngle.fromDegrees(leftArm, 102.7285, -57.8143, -79.9671),
					RotationAngle.fromDegrees(leftForeArm, -78.4251, -49.4193, 81.1582),
					RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
					RotationAngle.fromDegrees(rightLeg, -4.2638, -1.4254, 12.7349),
					RotationAngle.fromDegrees(leftLeg, 8.6778, -27.1298, -17.4237),
					RotationAngle.fromDegrees(leftLowerLeg, 30, 0, 0),
					RotationAngle.fromDegrees(rightLowerLeg, 42.5, 0, 0)
			});
			ModelPose<KingCrimsonEntity> groundpunch2 = new ModelPose<>(new RotationAngle[]{
					RotationAngle.fromDegrees(head, 11.034, 63.7082, 25.5639),
					RotationAngle.fromDegrees(body, -54.68, 80.07, -52.33),
					RotationAngle.fromDegrees(upperPart, 0, 0, 0),
					RotationAngle.fromDegrees(rightArm, 91.55, 72.2, 67.77),
					RotationAngle.fromDegrees(rightForeArm, -90.36, 33.26, -92.95),
					RotationAngle.fromDegrees(leftArm, 111.23F, -63.88F, -86.97),
					RotationAngle.fromDegrees(leftForeArm, -83.75, -54.69, 89.29),
					RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
					RotationAngle.fromDegrees(rightLeg, -5.02, -1.59, 3.73),
					RotationAngle.fromDegrees(leftLeg, 12.76, -30.12, -18.84),
					RotationAngle.fromDegrees(leftLowerLeg, 29.93, 0, 0),
					RotationAngle.fromDegrees(rightLowerLeg, 45.23, 0, 0)
			});
			ModelPose<KingCrimsonEntity> groundpunch3 = new ModelPose<>(new RotationAngle[]{
					RotationAngle.fromDegrees(head, -12.3094, 26.5188, 22.9667),
					RotationAngle.fromDegrees(body, -30.6184, 84.1948, -28.029),
					RotationAngle.fromDegrees(upperPart, 0, 0, 0),
					RotationAngle.fromDegrees(rightArm, 48.8237, 66.25, 61.239),
					RotationAngle.fromDegrees(rightForeArm, -82.436, -7.4355, -90.9845),
					RotationAngle.fromDegrees(leftArm, 102.7285, -57.8143, -79.9671),
					RotationAngle.fromDegrees(leftForeArm, -78.4251, -49.4193, 81.1582),
					RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
					RotationAngle.fromDegrees(rightLeg, -4.2638, -1.4254, 12.7349),
					RotationAngle.fromDegrees(leftLeg, 8.6778, -27.1298, -17.4237),
					RotationAngle.fromDegrees(leftLowerLeg, 30, 0, 0),
					RotationAngle.fromDegrees(rightLowerLeg, 42.5, 0, 0)
			});
			ModelPose<KingCrimsonEntity> groundpunch4 = new ModelPose<>(new RotationAngle[]{
					RotationAngle.fromDegrees(head, 30.3772, -0.3687, -8.9305),
					RotationAngle.fromDegrees(body, 43.6571, -37.8156, -36.8225),
					RotationAngle.fromDegrees(upperPart, 0, 0, 0),
					RotationAngle.fromDegrees(rightArm, -83.5132, 27.6911, -9.65),
					RotationAngle.fromDegrees(rightForeArm, 45.771, -81.7337, -44.9427),
					RotationAngle.fromDegrees(leftArm, 67.908, 5.7229, -39.5428),
					RotationAngle.fromDegrees(leftForeArm, -91.8334, 7.6892, 5.0407),
					RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
					RotationAngle.fromDegrees(rightLeg, 11.0785, 0.7983, 353.3195),
					RotationAngle.fromDegrees(leftLeg, -109.7155, 7.5324, -12.4158),
					RotationAngle.fromDegrees(leftLowerLeg, 107.5, 0, 0),
					RotationAngle.fromDegrees(rightLowerLeg, 55, 0, 0)
			});


		actionAnim.put(StandPose.HEAVY_ATTACK_FINISHER, new PosedActionAnimation.Builder<KingCrimsonEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(idlePose)
						.addPose(0.0F, groundpunch0)
						.addPose(0.50F, groundpunch1)
						.addPose(0.58F, groundpunch2)
						.addPose(0.79F, groundpunch3)
						.build(groundpunch4))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(groundpunch4)
						.addPose(0.88F, groundpunch4)
						.build(idlePose))
				.build(idlePose));

		super.initActionPoses();
	}


	@Override
	protected ModelPose<KingCrimsonEntity> initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, -13.5302, 44.8719, -1.5253),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, -16.105, 17.7209, 24.6098),
				RotationAngle.fromDegrees(leftForeArm, -416.2011, 51.0051, -373.8841),
				RotationAngle.fromDegrees(rightArm, -73.3146, -18.8895, 47.2051),
				RotationAngle.fromDegrees(rightForeArm, -84.8246, -14.9416, -1.3378),
				RotationAngle.fromDegrees(leftLeg, 21.8802, -23.399, -9.0616),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0, 0),
				RotationAngle.fromDegrees(rightLeg, 18.4854, 7.9768, 7.8431),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0, 0)
		});
	}

	@Override
	protected ModelPose<KingCrimsonEntity> initIdlePose2Loop() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, -13.5302, 44.8719, -1.5253),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, -0.5),
				RotationAngle.fromDegrees(leftArm, -13.287, 12.8578, 25.8147),
				RotationAngle.fromDegrees(leftForeArm, -408.7011, 51.0051, -373.8841),
				RotationAngle.fromDegrees(rightArm, -65.8146, -18.8895, 47.2051),
				RotationAngle.fromDegrees(rightForeArm, -92.3246, -14.9416, -1.3378),
				RotationAngle.fromDegrees(leftLeg, 21.8802, -23.399, -9.0616),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0, 0),
				RotationAngle.fromDegrees(rightLeg, 20.9854, 7.9768, 7.8431),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0, 0)
		});
	}
}



