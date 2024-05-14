package fr.rakambda.overpoweredmending.neoforge.event;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.neoforge.wrapper.PlayerWrapper;
import fr.rakambda.overpoweredmending.neoforge.wrapper.XpOrbWrapper;
import lombok.RequiredArgsConstructor;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

@RequiredArgsConstructor
public final class PlayerXpPickupEventHandler{
	private final OverpoweredMendingCommon mod;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerPickupXpEvent(PlayerXpEvent.PickupXp e){
		if(e.isCanceled()){
			return;
		}
		if(mod.onXpPickedUp(new PlayerWrapper(e.getEntity()), new XpOrbWrapper(e.getOrb()))){
			e.setCanceled(true);
		}
	}
}
