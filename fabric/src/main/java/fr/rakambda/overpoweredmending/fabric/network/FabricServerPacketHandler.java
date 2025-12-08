package fr.rakambda.overpoweredmending.fabric.network;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ConfigurationPacket;
import fr.rakambda.overpoweredmending.common.network.ServerPacketHandler;
import lombok.RequiredArgsConstructor;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;

@RequiredArgsConstructor
public class FabricServerPacketHandler implements ServerPacketHandler{
	private final OverpoweredMendingCommon mod;
	
	@Override
	public void registerServer(){
		PayloadTypeRegistry.configurationS2C().register(OverpoweredMendingConfigPacket.TYPE, OverpoweredMendingConfigPacket.CODEC);
		ServerConfigurationConnectionEvents.CONFIGURE.register(((handler, server) -> {
			var packet = ConfigurationPacket.get(server.isDedicatedServer(), mod.getConfiguration());
			ServerConfigurationNetworking.send(handler, new OverpoweredMendingConfigPacket(packet));
		}));
	}
}
