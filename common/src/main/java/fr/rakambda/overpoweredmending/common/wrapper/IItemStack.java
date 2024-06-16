package fr.rakambda.overpoweredmending.common.wrapper;

public interface IItemStack extends IWrapper{
	boolean hasMendingEnchant();
	
	int getDamageValue();
	
	void setDamageValue(int value);
	
	boolean isDamaged();
	
	boolean isDamageableItem();
	
	boolean isEmpty();
}
