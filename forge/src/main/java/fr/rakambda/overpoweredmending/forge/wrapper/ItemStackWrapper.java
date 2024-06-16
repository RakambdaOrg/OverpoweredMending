package fr.rakambda.overpoweredmending.forge.wrapper;

import fr.rakambda.overpoweredmending.common.wrapper.IItemStack;
import fr.rakambda.overpoweredmending.forge.OverpoweredMending;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class ItemStackWrapper implements IItemStack{
	@NotNull
	@Getter
	private final ItemStack raw;
	
	@Override
	public boolean hasMendingEnchant(){
		return EnchantmentHelper.hasTag(raw, TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(OverpoweredMending.MOD_ID, "mending")));
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
