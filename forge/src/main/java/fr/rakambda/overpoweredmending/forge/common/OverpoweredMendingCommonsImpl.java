package fr.rakambda.overpoweredmending.forge.common;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.forge.client.event.PlayerLeaveListener;
import fr.rakambda.overpoweredmending.forge.common.wrapper.ItemStackWrapper;
import fr.rakambda.overpoweredmending.forge.event.PlayerXpPickupEventHandler;
import fr.rakambda.overpoweredmending.forge.network.ForgePacketHandler;
import fr.rakambda.overpoweredmending.forge.network.PlayerJoinListener;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jspecify.annotations.NonNull;

public class OverpoweredMendingCommonsImpl extends OverpoweredMendingCommon{
	private final ForgePacketHandler packetHandler;
	
	public OverpoweredMendingCommonsImpl(){
		packetHandler = new ForgePacketHandler();
	}
	
	public void registerForge(){
		getServerPacketHandler().registerServer();
		
		var playerXpPickupEventHandler = new PlayerXpPickupEventHandler(this);
		PlayerXpEvent.PickupXp.BUS.addListener(playerXpPickupEventHandler::onPlayerPickupXpEvent);
		
		var playerJoinListener = new PlayerJoinListener(this);
		PlayerEvent.PlayerLoggedInEvent.BUS.addListener(playerJoinListener::onPlayerLoggedInEvent);
		
		var playerLeaveListener = new PlayerLeaveListener(this);
		if(FMLEnvironment.dist == Dist.CLIENT){
			ClientPlayerNetworkEvent.LoggingOut.BUS.addListener(playerLeaveListener::onPlayerLoggedOutEvent);
		}
	}
	
	@Override
	@NonNull
	protected IItemStack getEmptyItemStack(){
		return new ItemStackWrapper(ItemStack.EMPTY);
	}
	
	@Override
	@NonNull
	public ServerPacketHandler getServerPacketHandler(){
		return packetHandler;
	}
}
