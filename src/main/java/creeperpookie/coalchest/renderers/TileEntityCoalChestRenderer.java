package creeperpookie.coalchest.renderers;

import creeperpookie.coalchest.CoalChestMod;
import creeperpookie.coalchest.blocks.BlockCoalChest;
import creeperpookie.coalchest.tile.TileEntityCoalChest;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityCoalChestRenderer extends TileEntitySpecialRenderer<TileEntityCoalChest>
{
	private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation(CoalChestMod.MODID, "textures/entity/coal_chest.png");
	private static final ResourceLocation TEXTURE_NORMAL_DOUBLE = new ResourceLocation(CoalChestMod.MODID, "textures/entity/coal_chest_double.png");
	private final ModelChest simpleChest = new ModelChest();
	private final ModelChest largeChest = new ModelLargeChest();

	public void render(TileEntityCoalChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int i;

		if (te.hasWorld())
		{
			Block block = te.getBlockType();
			i = te.getBlockMetadata();

			if (block instanceof BlockCoalChest && i == 0)
			{
				((BlockCoalChest) block).checkForSurroundingChests(te.getWorld(), te.getPos(), te.getWorld().getBlockState(te.getPos()));
				i = te.getBlockMetadata();
			}

			te.checkForAdjacentChests();
		}
		else
		{
			i = 0;
		}

		if (te.adjacentChestZNeg == null && te.adjacentChestXNeg == null)
		{
			ModelChest modelchest;

			if (te.adjacentChestXPos == null && te.adjacentChestZPos == null)
			{
				modelchest = this.simpleChest;

				if (destroyStage >= 0)
				{
					this.bindTexture(DESTROY_STAGES[destroyStage]);

					GlStateManager.pushMatrix();
					GlStateManager.matrixMode(5890);
					GlStateManager.scale(4.0F, 4.0F, 1.0F);
					GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
					GlStateManager.matrixMode(5888);
				}
				this.bindTexture(TEXTURE_NORMAL);
			}
			else
			{
				modelchest = this.largeChest;

				if (destroyStage >= 0)
				{
					this.bindTexture(DESTROY_STAGES[destroyStage]);
					GlStateManager.matrixMode(5890);
					GlStateManager.pushMatrix();
					GlStateManager.scale(8.0F, 4.0F, 1.0F);
					GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
					GlStateManager.matrixMode(5888);
				}
				this.bindTexture(TEXTURE_NORMAL_DOUBLE);
			}

			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();

			if (destroyStage < 0)
			{
				GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
			}

			GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
			GlStateManager.scale(1.0F, -1.0F, -1.0F);
			GlStateManager.translate(0.5F, 0.5F, 0.5F);
			int j = 0;

			if (i == 2)
			{
				j = 180;
			}

			if (i == 3)
			{
				j = 0;
			}

			if (i == 4)
			{
				j = 90;
			}

			if (i == 5)
			{
				j = -90;
			}

			if (i == 2 && te.adjacentChestXPos != null)
			{
				GlStateManager.translate(1.0F, 0.0F, 0.0F);
			}

			if (i == 5 && te.adjacentChestZPos != null)
			{
				GlStateManager.translate(0.0F, 0.0F, -1.0F);
			}

			GlStateManager.rotate((float)j, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);
			float f = getAngle(te, partialTicks);
			modelchest.chestLid.rotateAngleX = -(f * ((float)Math.PI / 2F));
			modelchest.renderAll();
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

			if (destroyStage >= 0)
			{
				GlStateManager.matrixMode(5890);
				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
			}
		}
	}

	private static float getAngle(TileEntityCoalChest te, float partialTicks)
	{
		float angle = te.prevLidAngle + (te.lidAngle - te.prevLidAngle) * partialTicks;
		if (te.adjacentChestZNeg != null)
		{
			float zAngle = te.adjacentChestZNeg.prevLidAngle + (te.adjacentChestZNeg.lidAngle - te.adjacentChestZNeg.prevLidAngle) * partialTicks;
			if (zAngle > angle)
			{
				angle = zAngle;
			}
		}

		if (te.adjacentChestXNeg != null)
		{
			float xAngle = te.adjacentChestXNeg.prevLidAngle + (te.adjacentChestXNeg.lidAngle - te.adjacentChestXNeg.prevLidAngle) * partialTicks;

			if (xAngle > angle)
			{
				angle = xAngle;
			}
		}

		angle = 1.0F - angle;
		angle = 1.0F - (float) (Math.pow(angle, 3));
		return angle;
	}
}
