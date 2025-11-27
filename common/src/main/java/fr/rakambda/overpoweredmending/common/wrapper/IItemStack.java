package fr.rakambda.overpoweredmending.common.wrapper;

public interface IItemStack extends IWrapper{
	boolean hasMendingEnchant();
	
	int getDamageValue();
	
	int getMaxDamage();
	
	void setDamageValue(int value);
	
	boolean isDamaged();
	
	boolean isDamageableItem();
	
	boolean isEmpty();
	
	default double getDamagePercentage(){
		return ((double) getDamageValue()) / getMaxDamage();
	}
}
