package com.ht_dq.rotp_kingcrimson.client.render.entity.model.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.ht_dq.rotp_kingcrimson.entity.stand.stands.KingCrimsonEntity;
import net.minecraft.client.renderer.model.ModelRenderer;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransitionMultiple;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;

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
						RotationAngle.fromDegrees(body, -9.578, -46.099, -0.498),
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
		};
	}

	@Override
	protected void initActionPoses() {
		ModelPose<KingCrimsonEntity> HPPose1 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0, 0, 0),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, 0, 0, 0),
				RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, 0, 0, 0),
				RotationAngle.fromDegrees(rightForeArm, 0, 0, 0),
				RotationAngle.fromDegrees(leftLeg, 0, 0, 0),
				RotationAngle.fromDegrees(leftLowerLeg, 0, 0, 0),
				RotationAngle.fromDegrees(rightLeg, 0, 0, 0),
				RotationAngle.fromDegrees(rightLowerLeg, 0, 0.0F, 0.0F)
		});
		ModelPose<KingCrimsonEntity> HPPose2 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0, 0, 0),
				RotationAngle.fromDegrees(body, 13.2109, -71.435, 11.432),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, -84.0776, -32.3612, -3.178),
				RotationAngle.fromDegrees(leftForeArm, -10.3328, 47.9844, 24.4739),
				RotationAngle.fromDegrees(rightArm, 119.9792, -6.7643, 13.3623),
				RotationAngle.fromDegrees(rightForeArm, -72.7688, -10.0648, -88.1349),
				RotationAngle.fromDegrees(leftLeg, -112.015, -17.4632, -8.6621),
				RotationAngle.fromDegrees(leftLowerLeg, 122.5, 0, 0),
				RotationAngle.fromDegrees(rightLeg, -27.5, 0, 0),
				RotationAngle.fromDegrees(rightLowerLeg, 60, 0.0F, 0.0F)
		});
		ModelPose<KingCrimsonEntity> HPPose3 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0, 0, 0),
				RotationAngle.fromDegrees(body, -128.2992, -72.5313, 132.1864),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, 86.739, -73.0234, -69.8634),
				RotationAngle.fromDegrees(leftForeArm, -82.3335, 4.4198, 100.438),
				RotationAngle.fromDegrees(rightArm, 125.3991, 59.4826, 125.8625),
				RotationAngle.fromDegrees(rightForeArm, -79.6301, 23.5399, -99.9355),
				RotationAngle.fromDegrees(leftLeg, -45.4638, -41.453, -32.3155),
				RotationAngle.fromDegrees(leftLowerLeg, 57.1012, 8.4215, -5.4121),
				RotationAngle.fromDegrees(rightLeg, -110, 0, 0),
				RotationAngle.fromDegrees(rightLowerLeg, 110.4458, 11.7351, 4.3361)
		});

		actionAnim.put(StandPose.HEAVY_ATTACK_FINISHER, new PosedActionAnimation.Builder<KingCrimsonEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(idlePose)
						.addPose(0.4F, HPPose1)
						.addPose(0.3F, HPPose2)
						.addPose(0.8F, HPPose3)
						.build(HPPose3))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(HPPose3)
						.addPose(0.10F, HPPose3)
						.build(HPPose3))
				.build(idlePose));





		actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<KingCrimsonEntity>()
				.addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPose<>(new RotationAngle[] {
						new RotationAngle(body, 0.0F, -0.48F, 0.0F),
						new RotationAngle(leftArm, 0.0F, 0.0F, 0.0F),
						new RotationAngle(leftForeArm, 0.0F, 0.0F, 0.0F),
						new RotationAngle(rightArm, -1.0908F, 0.0F, 1.5708F),
						new RotationAngle(rightForeArm, 0.0F, 0.0F, 0.0F)



				}))
				.build(idlePose));

		super.initActionPoses();
	}

	protected void initActionPoses_Chop() {
		ModelPose<KingCrimsonEntity> chop0 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 0, 0, 0),
				RotationAngle.fromDegrees(body, 0, 0, 0),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, 0F, 0F, 0),
				RotationAngle.fromDegrees(leftForeArm, 0, 0, 0),
				RotationAngle.fromDegrees(rightArm, 2.3357, -0.4043, 0.239),
				RotationAngle.fromDegrees(rightForeArm, -27.3599, 88.8724, -27.3059),
				RotationAngle.fromDegrees(leftLeg, 2.5125, -2.9316, -5.0716),
				RotationAngle.fromDegrees(leftLowerLeg, 2.5125, -2.9316, -5.0716),
				RotationAngle.fromDegrees(rightLeg, 0, 0, 10),
				RotationAngle.fromDegrees(rightLowerLeg, 17.5, 0, 0)
		});
		ModelPose<KingCrimsonEntity> chop1 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(leftForeArm, -25, 0, 0),
		});
		ModelPose<KingCrimsonEntity> chop2 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(rightArm, -31.1704, 15.7725, -6.4568),
		});
		ModelPose<KingCrimsonEntity> chop3 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(leftArm, -164.9453, -4.8292, -1.2972),
				RotationAngle.fromDegrees(leftForeArm, -62.5, 0, 0),
				RotationAngle.fromDegrees(rightForeArm, -97.7789, -4.7856, -82.8046),
		});
		ModelPose<KingCrimsonEntity> chop4 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 1.8394, -10.8476, -6.2765),
				RotationAngle.fromDegrees(torso, -7.5, -10, 0.0F),
				RotationAngle.fromDegrees(leftArm, -164.8765, -7.243, -1.9516),
				RotationAngle.fromDegrees(leftForeArm, -82.5, 0, 0),
				RotationAngle.fromDegrees(rightArm, -23.6704, 15.7725, -6.4568),
				RotationAngle.fromDegrees(leftLeg, 2.6775, -20.4139, -5.8776),
				RotationAngle.fromDegrees(leftLowerLeg, 32.5, 0, 0),
				RotationAngle.fromDegrees(rightLeg, 0, -5, 10),
				RotationAngle.fromDegrees(rightLowerLeg, 35, 0, 0)
		});
		ModelPose<KingCrimsonEntity> chop5 = new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(head, 23.7756, -5.0038, -20.2317),
				RotationAngle.fromDegrees(torso, 12.3207, -2.6748, 1.6206),
				RotationAngle.fromDegrees(leftArm, -35, 0, 0),
				RotationAngle.fromDegrees(leftForeArm, -27.5, 0, 0),
				RotationAngle.fromDegrees(rightArm, -41.1704, 15.7725, -6.4568),
				RotationAngle.fromDegrees(leftLeg, 11.8894, -3.3082, -2.0873),
				RotationAngle.fromDegrees(leftLowerLeg, 42.5, 0, 0),
				RotationAngle.fromDegrees(rightLeg, 15, -5, 10),
				RotationAngle.fromDegrees(rightLowerLeg, 52.5, 0, 0)
		});


		actionAnim.put(StandPose.HEAVY_ATTACK, new PosedActionAnimation.Builder<KingCrimsonEntity>()
				.addPose(StandEntityAction.Phase.WINDUP, new ModelPoseTransitionMultiple.Builder<>(idlePose)
						.addPose(0.0F, chop0)
						.addPose(0.67F, chop1)
						.addPose(0.71F, chop2)
						.addPose(1.0F, chop3)
						.addPose(1.0F, chop4)
						.build(chop4))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(chop3)
						.addPose(1.5F, chop5)
						.build(chop5))
				.build(idlePose));
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



