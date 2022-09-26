package fr.raksrinana.overpoweredmending.forge.common;

import fr.raksrinana.overpoweredmending.common.OverpoweredMendingCommon;
import fr.raksrinana.overpoweredmending.common.wrapper.IEnchantment;
import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import fr.raksrinana.overpoweredmending.forge.event.PlayerXpPickupEventHandler;
import fr.raksrinana.overpoweredmending.forge.wrapper.EnchantmentWrapper;
import fr.raksrinana.overpoweredmending.forge.wrapper.ItemStackWrapper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

public class OverpoweredMendingImpl extends OverpoweredMendingCommon{
	public void registerForge(@NotNull IEventBus bus){
		bus.register(new PlayerXpPickupEventHandler(this));
	}
	
	@Override
	@NotNull
	protected IEnchantment getMendingEnchantment(){
		return new EnchantmentWrapper(Enchantments.MENDING);
	}
	
	@Override
	@NotNull
	protected IItemStack getEmptyItemStack(){
		return new ItemStackWrapper(ItemStack.EMPTY);
	}
}
