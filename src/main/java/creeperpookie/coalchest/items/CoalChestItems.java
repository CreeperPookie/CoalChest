package creeperpookie.coalchest.items;

import creeperpookie.coalchest.blocks.CoalChestBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CoalChestItems
{
	public static ItemBlock coalChestItem;

	public static void registerItems()
	{
		// Register items here
		coalChestItem = new CoalChestItem(CoalChestBlocks.blockCoalChest);
		ForgeRegistries.ITEMS.register(coalChestItem);
	}
}
