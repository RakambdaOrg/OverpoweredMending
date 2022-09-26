package fr.raksrinana.overpoweredmending.forge.event;

import fr.raksrinana.overpoweredmending.common.OverpoweredMendingCommon;
import fr.raksrinana.overpoweredmending.forge.wrapper.PlayerWrapper;
import fr.raksrinana.overpoweredmending.forge.wrapper.XpOrbWrapper;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@RequiredArgsConstructor
public final class PlayerXpPickupEventHandler{
	private final OverpoweredMendingCommon mod;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerPickupXpEvent(PlayerXpEvent.PickupXp e){
		if(e.isCanceled()){
			return;
		}
		if(mod.onXpPickedUp(new PlayerWrapper(e.getPlayer()), new XpOrbWrapper(e.getOrb()))){
			e.setCanceled(true);
		}
	}
}
