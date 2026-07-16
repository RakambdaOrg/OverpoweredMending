package fr.rakambda.overpoweredmending.common.wrapper;

public interface IXpOrb extends IWrapper{
	int getExperienceAmount();
	
	int getCount();
	
	void setCount(int count);
	
	void discard();
}
