package creeperpookie.coalchest.blocks;

import net.minecraft.block.BlockChest;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CoalChestBlocks
{
	public static BlockCoalChest blockCoalChest;

	public static void registerBlocks()
	{
		// Register blocks here
		blockCoalChest = new BlockCoalChest(BlockChest.Type.BASIC);
		ForgeRegistries.BLOCKS.register(blockCoalChest);
	}
}
