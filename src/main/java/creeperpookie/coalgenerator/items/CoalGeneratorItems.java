package creeperpookie.coalgenerator.items;

import creeperpookie.coalgenerator.blocks.CoalGeneratorBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CoalGeneratorItems
{
	public static ItemBlock coalChestItem;

	public static void registerItems()
	{
		// Register items here
		coalChestItem = new CoalChestItem(CoalGeneratorBlocks.blockCoalChest);
		ForgeRegistries.ITEMS.register(coalChestItem);
	}
}
