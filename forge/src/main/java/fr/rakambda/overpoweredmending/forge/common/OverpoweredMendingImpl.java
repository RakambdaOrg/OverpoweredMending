package fr.rakambda.overpoweredmending.forge.common;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.forge.event.PlayerXpPickupEventHandler;
import fr.rakambda.overpoweredmending.forge.wrapper.ItemStackWrapper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.jspecify.annotations.NonNull;

public class OverpoweredMendingImpl extends OverpoweredMendingCommon{
	public void registerForge(){
		var playerXpPickupEventHandler = new PlayerXpPickupEventHandler(this);
		PlayerXpEvent.PickupXp.BUS.addListener(playerXpPickupEventHandler::onPlayerPickupXpEvent);
	}
	
	@Override
	@NonNull
	protected IItemStack getEmptyItemStack(){
		return new ItemStackWrapper(ItemStack.EMPTY);
	}
}
