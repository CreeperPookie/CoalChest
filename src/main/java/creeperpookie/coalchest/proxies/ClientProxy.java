package creeperpookie.coalchest.proxies;

import creeperpookie.coalchest.CoalChestMod;
import creeperpookie.coalchest.items.CoalChestItems;
import creeperpookie.coalchest.renderers.TileEntityCoalChestItemRenderer;
import creeperpookie.coalchest.renderers.TileEntityCoalChestRenderer;
import creeperpookie.coalchest.tile.TileEntityCoalChest;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(CoalChestMod.MODID + ":coal_chest", "normal");
		ModelLoader.setCustomModelResourceLocation(CoalChestItems.coalChestItem, 0 /* default item subtype */, itemModelResourceLocation);
	}

	@Override
	public void init()
	{
		super.init();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoalChest.class, new TileEntityCoalChestRenderer());
		CoalChestItems.coalChestItem.setTileEntityItemStackRenderer(TileEntityCoalChestItemRenderer.getInstance());
	}

	@Override
	public void postInit()
	{
		super.postInit();
	}
}
