package fr.rakambda.overpoweredmending.fabric.client.event;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public class PlayerLeaveListener implements ClientPlayConnectionEvents.Disconnect{
	private final OverpoweredMendingCommon mod;
	
	public PlayerLeaveListener(OverpoweredMendingCommon mod){
		this.mod = mod;
	}
	
	@Override
	public void onPlayDisconnect(ClientPacketListener handler, Minecraft client){
		mod.getPacketUtils().onClientDisconnect();
	}
}
