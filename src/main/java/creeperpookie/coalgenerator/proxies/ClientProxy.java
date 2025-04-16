package creeperpookie.coalgenerator.proxies;

import creeperpookie.coalgenerator.CoalGeneratorMod;
import creeperpookie.coalgenerator.items.CoalGeneratorItems;
import creeperpookie.coalgenerator.renderers.TileEntityCoalChestItemRenderer;
import creeperpookie.coalgenerator.renderers.TileEntityCoalChestRenderer;
import creeperpookie.coalgenerator.tile.TileEntityCoalChest;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(CoalGeneratorMod.MODID + ":coal_chest", "normal");
		ModelLoader.setCustomModelResourceLocation(CoalGeneratorItems.coalChestItem, 0 /* default item subtype */, itemModelResourceLocation);
	}

	@Override
	public void init()
	{
		super.init();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoalChest.class, new TileEntityCoalChestRenderer());
		CoalGeneratorItems.coalChestItem.setTileEntityItemStackRenderer(TileEntityCoalChestItemRenderer.getInstance());
	}

	@Override
	public void postInit()
	{
		super.postInit();
	}
}
