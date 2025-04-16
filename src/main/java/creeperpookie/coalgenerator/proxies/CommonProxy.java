package creeperpookie.coalgenerator.proxies;

import creeperpookie.coalgenerator.CoalGeneratorMod;
import creeperpookie.coalgenerator.blocks.CoalGeneratorBlocks;
import creeperpookie.coalgenerator.items.CoalGeneratorItems;
import creeperpookie.coalgenerator.tile.TileEntityCoalChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void preInit()
	{
		// Register blocks and items here
		CoalGeneratorBlocks.registerBlocks();
		CoalGeneratorItems.registerItems();
		GameRegistry.registerTileEntity(TileEntityCoalChest.class, new ResourceLocation(CoalGeneratorMod.MODID, "coal_chest_tile_entity"));
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
