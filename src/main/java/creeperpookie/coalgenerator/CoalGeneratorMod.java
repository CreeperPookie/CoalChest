package creeperpookie.coalgenerator;

import creeperpookie.coalgenerator.proxies.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = CoalGeneratorMod.MODID, name = CoalGeneratorMod.NAME, version = CoalGeneratorMod.VERSION)
public class CoalGeneratorMod
{
	public static final String MODID = "coalgenerator";
	public static final String NAME = "Coal Generator";
	public static final String VERSION = "1.0.1";

	private static Logger logger;

	@SidedProxy(clientSide = "creeperpookie.coalgenerator.proxies.ClientProxy", serverSide = "creeperpookie.coalgenerator.proxies.ServerProxy")
	public static CommonProxy proxy;
	private static final Random RANDOM = new Random();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		// This is where you can register your event handlers, commands, etc.
		logger.info("Initializing Coal Generator");
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		// This is where you can do any post-initialization tasks
		proxy.postInit();
	}

	public static Logger getLogger()
	{
		return logger;
	}

	public static Random getRandom()
	{
		return RANDOM;
	}
}
