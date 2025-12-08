package fr.rakambda.overpoweredmending.fabric.client;

import fr.rakambda.overpoweredmending.fabric.OverpoweredMending;
import fr.rakambda.overpoweredmending.fabric.client.event.PlayerLeaveListener;
import fr.rakambda.overpoweredmending.fabric.client.network.FabricClientPacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class OverpoweredMendingClient implements ClientModInitializer{
	@Override
	public void onInitializeClient(){
		var mod = OverpoweredMending.getMod();
		new FabricClientPacketHandler(mod).registerClient();
		
		ClientPlayConnectionEvents.DISCONNECT.register(new PlayerLeaveListener(mod));
	}
}
