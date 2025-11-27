package fr.rakambda.overpoweredmending.forge.client.event;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import org.jspecify.annotations.NonNull;
import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class PlayerLeaveListener{
	@NonNull
	private final OverpoweredMendingCommon mod;
	
	@OnlyIn(Dist.CLIENT)
	public void onPlayerLoggedOutEvent(@Nonnull ClientPlayerNetworkEvent.LoggingOut event){
		mod.getPacketUtils().onClientDisconnect();
	}
}
