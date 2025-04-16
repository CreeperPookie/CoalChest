package creeperpookie.coalchest.proxies;

import creeperpookie.coalchest.CoalChestMod;
import creeperpookie.coalchest.blocks.CoalChestBlocks;
import creeperpookie.coalchest.items.CoalChestItems;
import creeperpookie.coalchest.tile.TileEntityCoalChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void preInit()
	{
		// Register blocks and items here
		CoalChestBlocks.registerBlocks();
		CoalChestItems.registerItems();
		GameRegistry.registerTileEntity(TileEntityCoalChest.class, new ResourceLocation(CoalChestMod.MODID, "coal_chest_tile_entity"));
	}

	public void init()
	{
		// Initialize the mod here
	}

	public void postInit()
	{
		// Post-initialization tasks here
	}
}
