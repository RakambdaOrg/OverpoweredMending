package fr.rakambda.overpoweredmending.neoforge.common;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.neoforge.event.PlayerXpPickupEventHandler;
import fr.rakambda.overpoweredmending.neoforge.wrapper.ItemStackWrapper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public class OverpoweredMendingImpl extends OverpoweredMendingCommon{
	public void registerForge(@NotNull IEventBus bus){
		bus.register(new PlayerXpPickupEventHandler(this));
	}
	
	@Override
	@NotNull
	protected IItemStack getEmptyItemStack(){
		return new ItemStackWrapper(ItemStack.EMPTY);
	}
}
