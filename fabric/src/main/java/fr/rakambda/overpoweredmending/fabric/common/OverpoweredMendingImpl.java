package fr.rakambda.overpoweredmending.fabric.common;

import fr.rakambda.overpoweredmending.common.OverpoweredMendingCommon;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.fabric.wrapper.ItemStackWrapper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OverpoweredMendingImpl extends OverpoweredMendingCommon{
	@Override
	@NotNull
	protected IItemStack getEmptyItemStack(){
		return new ItemStackWrapper(ItemStack.EMPTY);
	}
}
