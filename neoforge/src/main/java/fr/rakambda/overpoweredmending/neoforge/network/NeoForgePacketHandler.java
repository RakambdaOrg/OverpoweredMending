package fr.rakambda.overpoweredmending.neoforge.network;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ClientPacketHandler;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import fr.rakambda.overpoweredmending.neoforge.OverpoweredMending;
import lombok.RequiredArgsConstructor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class NeoForgePacketHandler implements ClientPacketHandler, ServerPacketHandler{
	@SubscribeEvent
	public void register(@NonNull RegisterPayloadHandlersEvent event){
		var registrar = event.registrar(OverpoweredMending.MOD_ID).optional();
		
		registrar.configurationToClient(OverpoweredMendingConfigPacket.TYPE, OverpoweredMendingConfigPacket.CODEC,
				(packet, sender) -> mod.getPacketUtils().onClientConfigurationPacket(packet.getPacket()));
	}
	
	private final OverpoweredMendingCommon mod;
	
	@Override
	public void registerServer(){
	}
	
	@Override
	public void registerClient(){
	}
}
