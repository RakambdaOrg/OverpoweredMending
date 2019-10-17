package fr.raksrinana.overpoweredmending;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = OverpoweredMending.MOD_ID, version = OverpoweredMending.VERSION)
public class OverpoweredMending{
	public static final String MOD_ID = "overpowered_mending";
	public static final String MOD_NAME = "Overpowered Mending";
	public static final String VERSION = "1.0.1";
	
	public OverpoweredMending(){
	}
	
	@EventHandler
	public void onInit(final FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(ForgeEventSubscriber.class);
	}
}
