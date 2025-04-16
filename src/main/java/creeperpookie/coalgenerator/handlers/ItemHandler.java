package creeperpookie.coalgenerator.handlers;

import creeperpookie.coalgenerator.CoalGeneratorMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

@Deprecated
@Mod.EventBusSubscriber
public class ItemHandler
{
	private static final HashSet<EntityItem> LOG_ITEMS = new HashSet<>();

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event)
	{
		Set<EntityItem> toRemove = new HashSet<>();
		if (!LOG_ITEMS.isEmpty() && event.phase == TickEvent.Phase.START)
		{
			LOG_ITEMS.stream().filter(entityItem -> entityItem.isInWater() && (entityItem.getEntityWorld().getBlockState(entityItem.getPosition()).getBlock() == Blocks.SAND || entityItem.getEntityWorld().getBlockState(entityItem.getPosition().down()).getBlock() == Blocks.SAND)).forEach(entityItem ->
			{
				entityItem.setItem(new ItemStack(Items.COAL, entityItem.getItem().getCount()));
				entityItem.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + CoalGeneratorMod.getRandom().nextFloat() * 0.4F);
				ObfuscationReflectionHelper.setPrivateValue(EntityItem.class, entityItem, 0, 2); // age
				for (int i = 0; i < 5; i++) entityItem.getEntityWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, Math.random() / 10, 0.25, Math.random() / 10);
				entityItem.motionX = (Math.random() / 4) - 0.125;
				entityItem.motionY = 0.35;
				entityItem.motionZ = (Math.random() / 4) - 0.125;
				entityItem.velocityChanged = true;
				toRemove.add(entityItem);
			});
		}
		if (!toRemove.isEmpty()) toRemove.forEach(LOG_ITEMS::remove);
	}

	@SubscribeEvent
	public static void onItemDrop(ItemTossEvent event)
	{
		EntityItem entityItem = event.getEntityItem();
		ItemStack stack = entityItem.getItem();
		if (stack.getItem() == getItem(Blocks.LOG) || stack.getItem() == getItem(Blocks.LOG2))
		{
			LOG_ITEMS.add(entityItem);
		}
	}

	@Nullable
	private static Item getItem(ItemBlock itemBlock)
	{
		return getItem(itemBlock.getBlock());
	}

	@Nullable
	private static Item getItem(Block block)
	{
		return net.minecraftforge.registries.GameData.getBlockItemMap().get(block);
	}
}
