package fr.rakambda.overpoweredmending.neoforge.common;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.neoforge.client.event.PlayerLeaveListener;
import fr.rakambda.overpoweredmending.neoforge.common.wrapper.ItemStackWrapper;
import fr.rakambda.overpoweredmending.neoforge.event.PlayerXpPickupEventHandler;
import fr.rakambda.overpoweredmending.neoforge.network.NeoForgePacketHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import org.jspecify.annotations.NonNull;

public class OverpoweredMendingCommonsImpl extends OverpoweredMendingCommon{
	private final IEventBus modEventBus;
	
	private final NeoForgePacketHandler packetHandler;
	
	public OverpoweredMendingCommonsImpl(@NonNull IEventBus modEventBus){
		this.modEventBus = modEventBus;
		
		packetHandler = new NeoForgePacketHandler(this);
	}
	
	public void registerNeoForge(@NonNull IEventBus bus){
		getServerPacketHandler().registerServer();
		
		bus.register(new PlayerXpPickupEventHandler(this));
		bus.register(new PlayerLeaveListener(this));
		
		modEventBus.register(packetHandler);
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
