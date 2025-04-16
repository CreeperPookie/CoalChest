package creeperpookie.coalchest.tile;

import creeperpookie.coalchest.blocks.BlockCoalChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileEntityCoalChest extends TileEntityChest
{
	private BlockChest.Type cachedChestType;
	private int tickCount = 0;

	public TileEntityCoalChest()
	{
		super(BlockChest.Type.BASIC);
	}

	public TileEntityCoalChest(BlockChest.Type type)
	{
		super(type);
		this.cachedChestType = type;
	}

	@Nullable
	@Override
	protected TileEntityChest getAdjacentChest(EnumFacing side)
	{
		BlockPos blockpos = this.pos.offset(side);
		if (this.isChestAt(blockpos))
		{
			TileEntity tileentity = this.world.getTileEntity(blockpos);

			if (tileentity instanceof TileEntityCoalChest)
			{
				TileEntityCoalChest tileentitychest = (TileEntityCoalChest) tileentity;
				setNeighbor(this, side.getOpposite());
				return tileentitychest;
			}
		}
		return null;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(pos.add(-1, 0, -1), pos.add(2, 2, 2));
	}

	@Override
	public void update()
	{
		for (int i = 0; i < this.getSizeInventory(); i++)
		{
			ItemStack itemstack = this.getStackInSlot(i);
			int increment = tickCount % 3 != 0 ? 3 : 4;
			if (itemstack.isEmpty())
			{
				this.setInventorySlotContents(i, new ItemStack(Blocks.COAL_BLOCK, increment));
				break;
			}
			else if (isWhitelistedForDupe(itemstack.getItem()) && itemstack.getCount() < itemstack.getMaxStackSize())
			{
				getStackInSlot(i).grow(increment);
				break;
			}
		}
		super.update();
		tickCount++;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		return parentNBTTagCompound;
	}

	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location
		for (int i = 0; i < this.getSizeInventory(); i++)
		{
			ItemStack itemstack = this.getStackInSlot(i);
			if (!itemstack.isEmpty() && (itemstack.getItem() != Items.COAL || itemstack.getItem() != Item.getItemFromBlock(Blocks.COAL_BLOCK)))
			{
				//this.setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}

		// important rule: never trust the data you read from NBT, make sure it can't cause a crash
	}

	@Override
	public String getGuiID()
	{
		return "coalchest:coal_chest";
	}

	private boolean isWhitelistedForDupe(Item item)
	{
		return item == Items.COAL || item == Item.getItemFromBlock(Blocks.COAL_BLOCK) || item == Item.getItemFromBlock(Blocks.DIRT) || item == Item.getItemFromBlock(Blocks.GRAVEL) || item == Item.getItemFromBlock(Blocks.SAND) || item == Item.getItemFromBlock(Blocks.COBBLESTONE) || item == Item.getItemFromBlock(Blocks.STONE) || item == Item.getItemFromBlock(Blocks.STONEBRICK) || item == Item.getItemFromBlock(Blocks.CLAY) || item == Item.getItemFromBlock(Blocks.SANDSTONE) || item == Item.getItemFromBlock(Blocks.RED_SANDSTONE) || item == Item.getItemFromBlock(Blocks.NETHERRACK) || item == Item.getItemFromBlock(Blocks.SOUL_SAND) || item == Item.getItemFromBlock(Blocks.END_STONE) || item == Item.getItemFromBlock(Blocks.OBSIDIAN) || item == Item.getItemFromBlock(Blocks.MYCELIUM) || item == Item.getItemFromBlock(Blocks.GRASS) || item == Item.getItemFromBlock(Blocks.HARDENED_CLAY) || item == Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY) || item == Item.getItemFromBlock(Blocks.BRICK_BLOCK) || item == Item.getItemFromBlock(Blocks.MOSSY_COBBLESTONE) || item == Item.getItemFromBlock(Blocks.GLASS);
	}

	private void setNeighbor(TileEntityCoalChest chestTe, EnumFacing side)
	{
		if (chestTe.isInvalid())
		{
			this.adjacentChestChecked = false;
		}
		else if (this.adjacentChestChecked)
		{
			switch (side)
			{
				case NORTH:

					if (this.adjacentChestZNeg != chestTe)
					{
						this.adjacentChestChecked = false;
					}

					break;
				case SOUTH:

					if (this.adjacentChestZPos != chestTe)
					{
						this.adjacentChestChecked = false;
					}

					break;
				case EAST:

					if (this.adjacentChestXPos != chestTe)
					{
						this.adjacentChestChecked = false;
					}

					break;
				case WEST:

					if (this.adjacentChestXNeg != chestTe)
					{
						this.adjacentChestChecked = false;
					}
			}
		}
	}

	public BlockChest.Type getChestType()
	{
		if (this.cachedChestType == null)
		{
			if (this.world == null || !(this.getBlockType() instanceof BlockCoalChest))
			{
				return BlockChest.Type.BASIC;
			}

			this.cachedChestType = ((BlockCoalChest) this.getBlockType()).chestType;
		}

		return this.cachedChestType;
	}

	private boolean isChestAt(BlockPos posIn)
	{
		if (this.world == null)
		{
			return false;
		}
		else
		{
			Block block = this.world.getBlockState(posIn).getBlock();
			return block instanceof BlockCoalChest && ((BlockCoalChest) block).chestType == this.getChestType();
		}
	}
}
