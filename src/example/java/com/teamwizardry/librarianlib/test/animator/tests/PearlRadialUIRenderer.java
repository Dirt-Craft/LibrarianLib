package com.teamwizardry.librarianlib.test.animator.tests;

import com.teamwizardry.librarianlib.features.animator.Animation;
import com.teamwizardry.librarianlib.features.animator.Animator;
import com.teamwizardry.librarianlib.features.animator.Easing;
import com.teamwizardry.librarianlib.features.animator.animations.StaticAnimation;
import com.teamwizardry.librarianlib.features.helpers.NBTHelper;
import com.teamwizardry.librarianlib.test.animator.AnimatorItems;
import com.teamwizardry.librarianlib.test.testcore.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Random;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TestMod.MODID)
public class PearlRadialUIRenderer {

	private static final int SELECTOR_RADIUS = 40;
	private static final int SELECTOR_WIDTH = 25;
	private static final int SELECTOR_SHIFT = 5;
	public static float[] slotRadii = new float[20];
	public static Animation[] slotAnimations = new Animation[20];
	public static Animator ANIMATOR = new Animator();

	private static int getScrollSlot(MouseEvent event, int count, int scrollSlot) {
		if (event.getDwheel() < 0) {
			if (scrollSlot == count) scrollSlot = 0;
			else scrollSlot = MathHelper.clamp(scrollSlot + 1, 0, count);
		} else {
			if (scrollSlot == 0) scrollSlot = count;
			else scrollSlot = MathHelper.clamp(scrollSlot - 1, 0, count);
		}
		return scrollSlot;
	}

	@SubscribeEvent
	public static void onScroll(MouseEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null) return;

		if (Keyboard.isCreated() && event.getDwheel() != 0 && player.isSneaking()) {

			ItemStack stack = player.getHeldItemMainhand();

			if (stack.getItem() == AnimatorItems.INSTANCE.getAnimatoritem()) {

				int count = 5;

				int scrollSlot = NBTHelper.getInt(stack, "scroll_slot", -1);
				int lastSlot = scrollSlot;

				scrollSlot = getScrollSlot(event, count, scrollSlot);

				if (lastSlot != scrollSlot && scrollSlot >= 0) {
					NBTHelper.setInt(stack, "scroll_slot", scrollSlot);

					for (int i = 0; i < slotAnimations.length; i++) {
						Animation animation = slotAnimations[i];
						if (animation != null)
							ANIMATOR.removeAnimations(animation);

						if (i == scrollSlot) continue;
						StaticAnimation newAnimation = new StaticAnimation<>(PearlRadialUIRenderer.class, "slotRadii[" + i + "]");
						newAnimation.setTo(0);
						newAnimation.setEasing(Easing.easeInQuint);
						newAnimation.setDuration(50f);
						ANIMATOR.add(newAnimation);

						slotAnimations[i] = newAnimation;
					}

					StaticAnimation animation = new StaticAnimation<>(PearlRadialUIRenderer.class, "slotRadii[" + scrollSlot + "]");
					animation.setTo(SELECTOR_SHIFT * 2);
					animation.setEasing(Easing.easeOutQuint);
					animation.setDuration(50f);
					ANIMATOR.add(animation);

					slotAnimations[scrollSlot] = animation;

					event.setCanceled(true);
				}

			}
		}
	}

	@SubscribeEvent
	public static void renderHud(RenderGameOverlayEvent.Post event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			ItemStack stack = player.getHeldItemMainhand();

			if (stack.getItem() == AnimatorItems.INSTANCE.getAnimatoritem()) {

				ScaledResolution resolution = event.getResolution();
				int width = resolution.getScaledWidth();
				int height = resolution.getScaledHeight();

				int numSegmentsPerArc = (int) Math.ceil(360d / 5);
				float anglePerColor = (float) (2 * Math.PI / 5);
				float anglePerSegment = anglePerColor / (numSegmentsPerArc);
				float angle = 0;

				int scrollSlot = NBTHelper.getInt(stack, "scroll_slot", -1);

				Tessellator tess = Tessellator.getInstance();
				BufferBuilder bb = tess.getBuffer();
				for (int j = 0; j < 5; j++) {

					Random random = new Random(j);

					Color color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1.0f);

					double innerRadius = SELECTOR_RADIUS - SELECTOR_WIDTH / 2.0;
					double outerRadius = SELECTOR_RADIUS + SELECTOR_WIDTH / 2.0 + (slotRadii[j]);// + (scrollSlot == j ? SELECTOR_SHIFT : 0);

					GlStateManager.pushMatrix();
					GlStateManager.translate(width / 2.0, height / 2.0, 0);

					GlStateManager.enableBlend();
					GlStateManager.disableTexture2D();
					GlStateManager.shadeModel(GL11.GL_SMOOTH);

					bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
					for (int i = 0; i < numSegmentsPerArc; i++) {
						float currentAngle = i * anglePerSegment + angle;
						bb.pos(innerRadius * MathHelper.cos(currentAngle), innerRadius * MathHelper.sin(currentAngle), 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
						bb.pos(innerRadius * MathHelper.cos(currentAngle + anglePerSegment), innerRadius * MathHelper.sin(currentAngle + anglePerSegment), 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
						bb.pos(outerRadius * MathHelper.cos(currentAngle + anglePerSegment), outerRadius * MathHelper.sin(currentAngle + anglePerSegment), 0).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
						bb.pos(outerRadius * MathHelper.cos(currentAngle), outerRadius * MathHelper.sin(currentAngle), 0).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
					}
					tess.draw();

					GlStateManager.disableRescaleNormal();
					RenderHelper.disableStandardItemLighting();
					GlStateManager.disableBlend();
					GlStateManager.disableAlpha();

					GlStateManager.translate(-width / 2.0, -height / 2.0, 0);
					GlStateManager.popMatrix();

					angle += anglePerColor;
				}
			}
		}
	}
}
