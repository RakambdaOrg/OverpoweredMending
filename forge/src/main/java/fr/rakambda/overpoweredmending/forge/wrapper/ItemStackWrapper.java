package fr.rakambda.overpoweredmending.forge.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.IEnchantment;
import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class ItemStackWrapper implements IItemStack{
	@NotNull
	@Getter
	private final ItemStack raw;
	
	@Override
	public int getEnchantmentLevel(IEnchantment enchantment){
		return raw.getEnchantmentLevel((Enchantment) enchantment.getRaw());
	}
	
	@Override
	public int getDamageValue(){
		return raw.getDamageValue();
	}
	
	@Override
	public void setDamageValue(int value){
		raw.setDamageValue(value);
	}
	
	@Override
	public boolean isDamaged(){
		return raw.isDamaged();
	}
	
	@Override
	public boolean isDamageableItem(){
		return raw.isDamageableItem();
	}
	
	@Override
	public boolean isEmpty(){
		return raw.isEmpty();
	}
}
