package creeperpookie.coalchest.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class CoalChestItem extends ItemBlock
{
	public CoalChestItem(Block block)
	{
		super(block);
		setRegistryName(block.getRegistryName());
		setUnlocalizedName(block.getUnlocalizedName());
	}
}
