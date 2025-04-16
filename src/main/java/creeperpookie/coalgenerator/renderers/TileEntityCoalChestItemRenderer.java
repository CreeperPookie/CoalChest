package creeperpookie.coalgenerator.renderers;

import creeperpookie.coalgenerator.items.CoalGeneratorItems;
import creeperpookie.coalgenerator.tile.TileEntityCoalChest;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class TileEntityCoalChestItemRenderer extends TileEntityItemStackRenderer
{
	private static final TileEntityCoalChestItemRenderer INSTANCE;
	private final TileEntityCoalChest chest = new TileEntityCoalChest();

	@Override
	public void renderByItem(ItemStack itemStackIn, float partialTicks)
	{
		super.renderByItem(itemStackIn, partialTicks);
		if (itemStackIn.getItem() == CoalGeneratorItems.coalChestItem)
		{
			TileEntityRendererDispatcher.instance.render(chest, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
		}
	}

	public static TileEntityCoalChestItemRenderer getInstance()
	{
		return INSTANCE;
	}

	static
	{
		INSTANCE = new TileEntityCoalChestItemRenderer();
	}
}
