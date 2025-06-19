package fr.rakambda.overpoweredmending.forge.event;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.forge.wrapper.PlayerWrapper;
import fr.rakambda.overpoweredmending.forge.wrapper.XpOrbWrapper;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.event.entity.player.PlayerXpEvent;

@RequiredArgsConstructor
public final class PlayerXpPickupEventHandler{
	private final OverpoweredMendingCommon mod;
	
	public boolean onPlayerPickupXpEvent(PlayerXpEvent.PickupXp e){
		return mod.onXpPickedUp(new PlayerWrapper(e.getEntity()), new XpOrbWrapper(e.getOrb()));
	}
}
