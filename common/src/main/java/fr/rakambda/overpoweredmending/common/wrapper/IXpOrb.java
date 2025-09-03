package fr.rakambda.overpoweredmending.common.wrapper;

import org.jspecify.annotations.NonNull;

public interface IXpOrb extends IWrapper{
	int getExperienceAmount();
	
	void setExperienceAmount(int amount);
	
	void discard();
	
	@NonNull
	ILevel getLevel();
}
