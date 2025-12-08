package fr.rakambda.overpoweredmending.fabric.client.network;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.network.ClientPacketHandler;
import fr.rakambda.overpoweredmending.fabric.network.OverpoweredMendingConfigPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;

public class FabricClientPacketHandler implements ClientPacketHandler{
	private final OverpoweredMendingCommon mod;
	
	public FabricClientPacketHandler(OverpoweredMendingCommon mod){
		this.mod = mod;
	}
	
	@Override
	public void registerClient(){
		ClientConfigurationNetworking.registerGlobalReceiver(OverpoweredMendingConfigPacket.TYPE,
				(packet, sender) -> mod.getPacketUtils().onClientConfigurationPacket(packet.getPacket()));
	}
}
