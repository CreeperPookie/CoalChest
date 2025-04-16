package creeperpookie.coalchest.blocks;

import creeperpookie.coalchest.CoalChestMod;
import creeperpookie.coalchest.tile.TileEntityCoalChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCoalChest extends BlockChest
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	//private static final AxisAlignedBB ENDER_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

	public BlockCoalChest(BlockChest.Type type)
	{
		super(type);
		this.setTickRandomly(true);
		setUnlocalizedName(CoalChestMod.MODID + ".coal_chest");
		setRegistryName(CoalChestMod.MODID + ":coal_chest");
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityCoalChest)
			{
				((TileEntityCoalChest) tileentity).update();
			}
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCoalChest();
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityCoalChest)
		{
			tileentity.updateContainingBlockInfo();
		}
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
		state = state.withProperty(FACING, enumfacing);
		BlockPos blockpos = pos.north();
		BlockPos blockpos1 = pos.south();
		BlockPos blockpos2 = pos.west();
		BlockPos blockpos3 = pos.east();
		boolean flag = this == worldIn.getBlockState(blockpos).getBlock();
		boolean flag1 = this == worldIn.getBlockState(blockpos1).getBlock();
		boolean flag2 = this == worldIn.getBlockState(blockpos2).getBlock();
		boolean flag3 = this == worldIn.getBlockState(blockpos3).getBlock();

		if (!flag && !flag1 && !flag2 && !flag3)
		{
			worldIn.setBlockState(pos, state, 3);
		}
		else if (enumfacing.getAxis() != EnumFacing.Axis.X || !flag && !flag1)
		{
			if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3))
			{
				if (flag2)
				{
					worldIn.setBlockState(blockpos2, state, 3);
				}
				else
				{
					worldIn.setBlockState(blockpos3, state, 3);
				}

				worldIn.setBlockState(pos, state, 3);
			}
		}
		else
		{
			if (flag)
			{
				worldIn.setBlockState(blockpos, state, 3);
			}
			else
			{
				worldIn.setBlockState(blockpos1, state, 3);
			}

			worldIn.setBlockState(pos, state, 3);
		}

		if (stack.hasDisplayName())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityCoalChest)
			{
				((TileEntityCoalChest) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Nullable
	public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (!(tileentity instanceof TileEntityChest))
		{
			return null;
		}
		else
		{
			ILockableContainer ilockablecontainer = (TileEntityChest)tileentity;

			if (!allowBlocking && this.isBlocked(worldIn, pos))
			{
				return null;
			}
			else
			{
				for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
				{
					BlockPos blockpos = pos.offset(enumfacing);
					Block block = worldIn.getBlockState(blockpos).getBlock();

					if (block == this)
					{
						if (!allowBlocking && this.isBlocked(worldIn, blockpos)) // Forge: fix MC-99321
						{
							return null;
						}

						TileEntity tileentity1 = worldIn.getTileEntity(blockpos);

						if (tileentity1 instanceof TileEntityCoalChest)
						{
							if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH)
							{
								ilockablecontainer = new InventoryLargeChest("container.chestDouble", ilockablecontainer, (TileEntityChest)tileentity1);
							}
							else
							{
								ilockablecontainer = new InventoryLargeChest("container.chestDouble", (TileEntityCoalChest) tileentity1, ilockablecontainer);
							}
						}
					}
				}
				return ilockablecontainer;
			}
		}
	}

	private boolean isBlocked(World worldIn, BlockPos pos)
	{
		return isBelowSolidBlock(worldIn, pos) || isOcelotSittingOnChest(worldIn, pos);
	}

	private boolean isBelowSolidBlock(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), EnumFacing.DOWN);
	}

	private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos)
	{
		for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB((double)pos.getX(), (double)(pos.getY() + 1), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 2), (double)(pos.getZ() + 1))))
		{
			EntityOcelot entityocelot = (EntityOcelot) entity;

			if (entityocelot.isSitting())
			{
				return true;
			}
		}

		return false;
	}
}
