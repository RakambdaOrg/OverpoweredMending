package fr.rakambda.overpoweredmending.common.wrapper;

import org.jetbrains.annotations.NotNull;

public interface IXpOrb extends IWrapper{
	int getExperienceAmount();
	
	void setExperienceAmount(int amount);
	
	void discard();
	
	@NotNull
	ILevel getLevel();
}
