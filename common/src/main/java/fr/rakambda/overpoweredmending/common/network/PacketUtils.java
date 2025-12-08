package fr.rakambda.overpoweredmending.common.network;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;

@Log4j2
@RequiredArgsConstructor
public class PacketUtils{
	private final OverpoweredMendingCommon mod;
	
	public void onClientConfigurationPacket(@NonNull ConfigurationPacket packet){
		if(!packet.isDedicated()){
			log.info("Received OM configuration packet from own server, skipping");
			return;
		}
		log.info("Received OM configuration packet from server, setting up proxy config values");
		mod.getProxyConfiguration().setSelectionMode(packet.getSelectionMode());
	}
	
	public void onClientDisconnect(){
		log.info("Disconnected from server, resetting proxy config values");
		mod.getProxyConfiguration().reset();
	}
}
