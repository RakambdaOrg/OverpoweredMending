package fr.rakambda.overpoweredmending.common.wrapper;

public interface IItemStack extends IWrapper{
	int getEnchantmentLevel(IEnchantment enchantment);
	
	int getDamageValue();
	
	void setDamageValue(int value);
	
	boolean isDamaged();
	
	boolean isDamageableItem();
	
	boolean isEmpty();
}
