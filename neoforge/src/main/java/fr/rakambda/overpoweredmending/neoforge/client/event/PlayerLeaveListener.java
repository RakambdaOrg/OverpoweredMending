package fr.rakambda.overpoweredmending.neoforge.client.event;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import lombok.RequiredArgsConstructor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.jspecify.annotations.NonNull;
import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class PlayerLeaveListener{
	@NonNull
	private final OverpoweredMendingCommon mod;
	
	@SubscribeEvent
	public void onPlayerLoggedOutEvent(@Nonnull ClientPlayerNetworkEvent.LoggingOut event){
		mod.getPacketUtils().onClientDisconnect();
	}
}
