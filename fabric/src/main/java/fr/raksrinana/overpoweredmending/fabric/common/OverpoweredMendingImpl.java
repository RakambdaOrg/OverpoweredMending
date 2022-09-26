package fr.raksrinana.overpoweredmending.fabric.common;

import fr.raksrinana.overpoweredmending.common.OverpoweredMendingCommon;
import fr.raksrinana.overpoweredmending.common.wrapper.IEnchantment;
import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import fr.raksrinana.overpoweredmending.fabric.wrapper.EnchantmentWrapper;
import fr.raksrinana.overpoweredmending.fabric.wrapper.ItemStackWrapper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OverpoweredMendingImpl extends OverpoweredMendingCommon{
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
