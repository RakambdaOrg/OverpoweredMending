package fr.raksrinana.overpoweredmending.fabric.wrapper;

import fr.raksrinana.overpoweredmending.common.wrapper.IEnchantment;
import fr.raksrinana.overpoweredmending.common.wrapper.IItemStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class ItemStackWrapper implements IItemStack{
	@NotNull
	@Getter
	private final ItemStack raw;
	
	@Override
	public int getEnchantmentLevel(IEnchantment enchantment){
		return EnchantmentHelper.getLevel((Enchantment) enchantment.getRaw(), raw);
	}
	
	@Override
	public int getDamageValue(){
		return raw.getDamage();
	}
	
	@Override
	public void setDamageValue(int value){
		raw.setDamage(value);
	}
	
	@Override
	public boolean isDamaged(){
		return raw.isDamaged();
	}
	
	@Override
	public boolean isDamageableItem(){
		return raw.isDamageable();
	}
	
	@Override
	public boolean isEmpty(){
		return raw.isEmpty();
	}
}
